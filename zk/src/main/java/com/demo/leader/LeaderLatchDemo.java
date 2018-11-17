package com.demo.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;

public class LeaderLatchDemo{

    private final static String PATH = "/qg/leader_latch/";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LeaderLatch leaderLatch;
    private final CuratorFramework client;
    private final LeaderLatchListener listener = new LeaderLatchListener() {
        @Override
        public String getSelectionType() {
            return "demo";
        }
    };

    /**
     * final
     *
     * @param client
     */
    public LeaderLatchDemo(CuratorFramework client) {
        this.client = client;
        leaderLatch = new LeaderLatch(client, PATH, listener.getSelectionType());
        leaderLatch.addListener(new org.apache.curator.framework.recipes.leader.LeaderLatchListener() {
            @Override
            public void isLeader() {
                listener.setupLeader(true);
            }

            @Override
            public void notLeader() {
                listener.setupLeader(false);
            }
        });
        try {
            leaderLatch.start();
        } catch (Exception e) {
            logger.error("start latch error", e);
        }
    }


    /**
     * close, called by spring
     */
    public void close() throws IOException {
        leaderLatch.close();
    }

    public void start() throws Exception
    {
        leaderLatch.start();
    }
}
