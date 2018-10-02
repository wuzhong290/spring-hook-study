package com.demo.javabase.nio.impl;

import com.demo.javabase.nio.AbstractConnection;
import com.demo.javabase.nio.ErrorCode;
import com.demo.javabase.nio.NIOHandler;
import com.demo.javabase.nio.NIOProcessor;
import com.demo.javabase.nio.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class DemoConnection extends AbstractConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoConnection.class);
    protected NIOHandler handler;
    protected long idleTimeout;
    protected String charset;

    public DemoConnection(SocketChannel channel) {
        super(channel);
        handler = new NIOHandler() {
            @Override
            public void handle(byte[] data) {
                LOGGER.info("handle data:{}",new String(data));
                ByteBuffer buffer = allocate();
                buffer.put("hello".getBytes());
                write(buffer);
            }
        };
    }

    @Override
    protected void idleCheck() {
        if (isIdleTimeout()) {
            LOGGER.warn(toString() + " idle timeout");
            close();
        }
    }

    @Override
    public void handle(byte[] data) {
        processor.getHandler().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.handle(data);
                } catch (Throwable t) {
                    error(ErrorCode.ERR_HANDLE_DATA, t);
                }
            }
        });
    }

    @Override
    public void error(int errCode, Throwable t) {
        LOGGER.error("handle errCode:{},message:{}", errCode, t.getMessage());
        close();
    }

    @Override
    public void register(Selector selector) throws IOException {
        super.register(selector);
        ByteBuffer buffer = allocate();
        buffer.put((this.getChannel().getLocalAddress() +"hello").getBytes());
        write(buffer);
    }

    @Override
    protected int getPacketLength(ByteBuffer buffer, int offset){
        if (buffer.position() < offset + packetHeaderSize) {
            return -1;
        } else {
            byte[] packetHeader = new byte[4];
            for (int i = offset; i < offset + 4 ; i++) {
                packetHeader[i] = buffer.get(i);
            }
            String ph = new String(packetHeader);
            if(StringUtils.isNumeric(ph)){
                int length = Integer.valueOf(ph);
                return length + packetHeaderSize;
            }else{
                return buffer.position();
            }
        }
    }

    public void setProcessor(NIOProcessor processor) {
        this.processor = processor;
        this.readBuffer = processor.getBufferPool().allocate();
        //把接收到的SocketChannel放入NIOProcessor的Frontend池中,用户连接检查
        processor.addFrontend(this);
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
    public boolean isIdleTimeout() {
        return TimeUtil.currentTimeMillis() > Math.max(lastWriteTime, lastReadTime) + idleTimeout;
    }
}
