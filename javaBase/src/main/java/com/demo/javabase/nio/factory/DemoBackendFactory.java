package com.demo.javabase.nio.factory;

import com.demo.javabase.nio.NIOConnector;
import com.demo.javabase.nio.impl.DemoBackendConnection;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class DemoBackendFactory extends BackendConnectionFactory {
    public DemoBackendFactory() {
        this.idleTimeout = 120 * 1000L;
    }
    public DemoBackendConnection make(NIOConnector connector) throws IOException {
        SocketChannel channel = openSocketChannel();
        DemoBackendConnection backendConnection = new DemoBackendConnection(channel);
        backendConnection.setHost("127.0.0.1");
        backendConnection.setPort(8066);
        postConnect(backendConnection, connector);
        return backendConnection;
    }
}
