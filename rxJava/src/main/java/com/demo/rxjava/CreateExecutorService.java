package com.demo.rxjava;

import java.util.concurrent.*;

public final class CreateExecutorService {

    private static class LazyHolder {
        static final ExecutorService INSTANCE = createCustomExecutorService(10,"test");
    }

    public static ExecutorService getInstance() {
        //此处加载LazyHolder，并初始化：new Something()。
        return LazyHolder.INSTANCE;
    }

    private synchronized static ExecutorService createCustomExecutorService(int poolSize, final String method) {
        int coreSize = Runtime.getRuntime().availableProcessors();
        if(poolSize < coreSize) {
            coreSize = poolSize;
        }

        ThreadFactory tf = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "thread created at CobarSqlMapClientTemplate method [" + method + "]");
                t.setDaemon(true);
                return t;
            }
        };
        LinkedBlockingQueue queueToUse = new LinkedBlockingQueue(coreSize);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, poolSize, 60L, TimeUnit.SECONDS, queueToUse, tf, new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
