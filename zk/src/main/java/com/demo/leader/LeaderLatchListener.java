package com.demo.leader;

public abstract class LeaderLatchListener {


    protected  volatile boolean isLeader = false;

    public boolean isLeader(){
        return this.isLeader;
    }

    void setupLeader(boolean isLeader){
      this.isLeader = isLeader;
    }

    public abstract  String getSelectionType();
}
