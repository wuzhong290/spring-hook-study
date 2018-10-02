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

import com.demo.javabase.nio.buffer.BufferPool;
import com.demo.javabase.nio.util.ExecutorUtil;
import com.demo.javabase.nio.util.NameableExecutor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xianmao.hexm
 */
public final class NIOProcessor {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024 * 16;
    private static final int DEFAULT_BUFFER_CHUNK_SIZE = 4096;
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private final String name;
    private final NIOReactor reactor;
    private final BufferPool bufferPool;
    private final NameableExecutor handler;
    private final NameableExecutor executor;
    private final NameableExecutor committer;
    private final ConcurrentMap<Long, AbstractConnection> frontends;
    private final ConcurrentMap<Long, BackendConnection> backends;
    private long netInBytes;
    private long netOutBytes;

    public NIOProcessor(String name) throws IOException {
        this(name, DEFAULT_BUFFER_SIZE, DEFAULT_BUFFER_CHUNK_SIZE, AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS);
    }

    public NIOProcessor(String name, int handler, int executor, int committer) throws IOException {
        this(name, DEFAULT_BUFFER_SIZE, DEFAULT_BUFFER_CHUNK_SIZE, handler, executor, committer);
    }

    public NIOProcessor(String name, int buffer, int chunk, int handler, int executor, int committer) throws IOException {
        this.name = name;
        this.reactor = new NIOReactor(name);
        this.bufferPool = new BufferPool(buffer, chunk);
        this.handler = (handler > 0) ? ExecutorUtil.create(name + "-H", handler) : null;
        this.executor = (executor > 0) ? ExecutorUtil.create(name + "-E", executor) : null;
        this.committer = (committer > 0) ? ExecutorUtil.create(name + "-C", committer) : null;
        this.frontends = new ConcurrentHashMap<Long, AbstractConnection>();
        this.backends = new ConcurrentHashMap<Long, BackendConnection>();
    }

    public String getName() {
        return name;
    }

    public BufferPool getBufferPool() {
        return bufferPool;
    }

    public int getRegisterQueueSize() {
        return reactor.getRegisterQueue().size();
    }

    public int getWriteQueueSize() {
        return reactor.getWriteQueue().size();
    }

    public NameableExecutor getHandler() {
        return handler;
    }

    public NameableExecutor getExecutor() {
        return executor;
    }
    
	public NameableExecutor getCommitter() {
		return committer;
	}

	public void startup() {
        reactor.startup();
    }

    public void postRegister(NIOConnection c) {
        reactor.postRegister(c);
    }

    public void postWrite(NIOConnection c) {
        reactor.postWrite(c);
    }


    public long getNetInBytes() {
        return netInBytes;
    }

    public void addNetInBytes(long bytes) {
        netInBytes += bytes;
    }

    public long getNetOutBytes() {
        return netOutBytes;
    }

    public void addNetOutBytes(long bytes) {
        netOutBytes += bytes;
    }

    public long getReactCount() {
        return reactor.getReactCount();
    }

    public void addFrontend(AbstractConnection c) {
        frontends.put(c.getId(), c);
    }

    public ConcurrentMap<Long, AbstractConnection> getFrontends() {
        return frontends;
    }

    public void addBackend(BackendConnection c) {
        backends.put(c.getId(), c);
    }

    public ConcurrentMap<Long, BackendConnection> getBackends() {
        return backends;
    }

    /**
     * 定时执行该方法，回收部分资源。
     */
    public void check() {
        frontendCheck();
        backendCheck();
    }

    // 前端连接检查
    private void frontendCheck() {
        Iterator<Entry<Long, AbstractConnection>> it = frontends.entrySet().iterator();
        while (it.hasNext()) {
            AbstractConnection c = it.next().getValue();

            // 删除空连接
            if (c == null) {
                it.remove();
                continue;
            }

            // 清理已关闭连接，否则空闲检查。
            if (c.isClosed()) {
                it.remove();
                c.cleanup();
            } else {
                c.idleCheck();
            }
        }
    }

    // 后端连接检查
    private void backendCheck() {
        Iterator<Entry<Long, BackendConnection>> it = backends.entrySet().iterator();
        while (it.hasNext()) {
            BackendConnection c = it.next().getValue();

            // 删除空连接
            if (c == null) {
                it.remove();
                continue;
            }

            // 清理已关闭连接，否则空闲检查。
            if (c.isClosed()) {
                it.remove();
                c.cleanup();
            } else {
                c.idleCheck();
            }
        }
    }

}
