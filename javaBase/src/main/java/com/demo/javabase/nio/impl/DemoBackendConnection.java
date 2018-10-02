package com.demo.javabase.nio.impl;

import com.demo.javabase.nio.BackendConnection;
import com.demo.javabase.nio.NIOHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class DemoBackendConnection extends BackendConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoBackendConnection.class);
    public DemoBackendConnection(SocketChannel channel) {
        super(channel);
        this.handler = new NIOHandler(){
            @Override
            public void handle(byte[] data) {
                LOGGER.info("handle data:{}",new String(data));
                ByteBuffer buffer = allocate();
                buffer.put("hello s".getBytes());
                write(buffer);
            }
        };
    }

    @Override
    protected int getPacketLength(ByteBuffer buffer, int offset) {
        return 1;
    }
    @Override
    public void error(int errCode, Throwable t) {
        LOGGER.error("handle errCode:{},message:{}", errCode, t.getMessage());
    }
}
