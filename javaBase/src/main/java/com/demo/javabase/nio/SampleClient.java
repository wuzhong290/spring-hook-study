package com.demo.javabase.nio;

import com.demo.javabase.nio.factory.BackendConnectionFactory;
import com.demo.javabase.nio.factory.DemoBackendFactory;
import com.demo.javabase.nio.impl.DemoBackendConnection;
import com.demo.javabase.nio.util.ExecutorUtil;
import com.demo.javabase.nio.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SampleClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleClient.class);
    private static final SampleClient INSTANCE = new SampleClient();
    private ScheduledExecutorService timer;
    private NIOProcessor[] processors;
    private NIOConnector connector;
    private static final int SERVER_PORT = 8066;
    private static final long TIME_UPDATE_PERIOD = 100L;

    public void startup() throws IOException {
        String name = "demo";
        LOGGER.info("===============================================");
        LOGGER.info(name + " is ready to startup ...");

        // schedule timer task
        timer = ExecutorUtil.scheduledExecutorService(name + "Timer", 1, true);
        timer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                TimeUtil.update();
            }
        }, 0L, TIME_UPDATE_PERIOD, TimeUnit.MILLISECONDS);
        LOGGER.info("Task Timer is started ...");
        // startup processors
        processors = new NIOProcessor[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new NIOProcessor(name + "Processor" + i);
            processors[i].startup();
        }
        // startup connector
        LOGGER.info("Startup connector ...");
        connector = new NIOConnector(name + "Connector");
        connector.setProcessors(processors);
        connector.start();
        new DemoBackendFactory().make(connector);
    }
    public static final SampleClient getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) throws IOException {
        SampleClient.getInstance().startup();
    }
}
