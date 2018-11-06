package com.demo.javabase.nio.impl;

import com.demo.javabase.nio.AbstractConnection;
import com.demo.javabase.nio.ErrorCode;
import com.demo.javabase.nio.NIOHandler;
import com.demo.javabase.nio.NIOProcessor;
import com.demo.javabase.nio.util.TimeUtil;
import org.apache.commons.lang3.ArrayUtils;
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
                byte[] packetHeader = ArrayUtils.subarray(data, 0, 4);
                byte[] packetBody = ArrayUtils.subarray(data,4, data.length);
                LOGGER.info("handle packetHeader:{},packetBody:{}", new String(packetHeader), new String(packetBody));
                ByteBuffer buffer = allocate();
                buffer.put(packetBody);
                write(buffer);
            }
        };
    }

    /**
     * 定时执行该方法，回收部分资源。
     */
    @Override
    protected void idleCheck() {
        if (isIdleTimeout()) {
            LOGGER.warn(toString() + " idle timeout");
            close();
        }
    }

    /**
     * 重写：处理读过来的数据包：包含：包头 和 包体
     * 具体的执行动作可以放在一个线程池中
     * @param data
     */
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

    /**
     * 重写：出现异常是关闭SocketChannel链接
     * @param errCode
     * @param t
     */
    @Override
    public void error(int errCode, Throwable t) {
        LOGGER.error("handle errCode:{},message:{}", errCode, t.getMessage());
        close();
    }

    /**
     * 重写：把接收到的SocketChannel注册到这个的Selector上，
     * 用于接收SelectionKey.OP_READ事件
     * @param selector
     * @throws IOException
     */
    @Override
    public void register(Selector selector) throws IOException {
        super.register(selector);
        write((this.getChannel().getLocalAddress() +" hello " +this.getChannel().getRemoteAddress()).getBytes());
    }

    /**
     * 重写：获取数据包头部信息（开始4个字节），
     * 开始4个字节必须是数字类型的，直接转换成数字+packetHeaderSize，就是包的长度
     * 开始4个字节必须不是数字类型的，异常数据包，当前缓冲区长度作为包的长度
     * @param buffer
     * @param offset
     * @return
     */
    @Override
    protected int getPacketLength(ByteBuffer buffer, int offset){
        if (buffer.position() < offset + packetHeaderSize) {
            return -1;
        } else {
            byte[] packetHeader = new byte[4];
            for (int i = offset; i < offset + 4 ; i++) {
                packetHeader[i - offset] = buffer.get(i);
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
