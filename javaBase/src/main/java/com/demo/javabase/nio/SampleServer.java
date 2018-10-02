/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.demo.javabase.nio;

import com.demo.javabase.nio.util.ExecutorUtil;
import com.demo.javabase.nio.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 服务器组装示例
 * 
 * @author xianmao.hexm
 */
public class SampleServer {
    private static final int SERVER_PORT = 8066;
    private static final long TIME_UPDATE_PERIOD = 100L;
    private static final long DEFAULT_PROCESSOR_CHECK_PERIOD = 15 * 1000L;
    private static final SampleServer INSTANCE = new SampleServer();
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleServer.class);

    public static final SampleServer getInstance() {
        return INSTANCE;
    }

    private ScheduledExecutorService timer;
    private NIOProcessor[] processors;
    private NIOAcceptor server;

    private SampleServer() {

    }

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
        //定时执行该方法，回收部分资源。
        timer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for (NIOProcessor p : processors) {
                    p.check();
                }
            }
        }, 0L, DEFAULT_PROCESSOR_CHECK_PERIOD, TimeUnit.MILLISECONDS);
        // startup server
        server = new NIOAcceptor(name + "Server", SERVER_PORT);
        server.setProcessors(processors);
        server.start();
        LOGGER.info(server.getName() + " is started and listening on " + server.getPort());
        // end
        LOGGER.info("===============================================");
    }
    public static void main(String[] args) throws IOException {
        SampleServer.getInstance().startup();
    }
}
