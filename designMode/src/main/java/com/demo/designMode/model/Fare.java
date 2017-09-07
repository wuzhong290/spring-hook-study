package com.demo.designMode.model;

/**
 * Created by wuzhong on 2017/9/7.
 */
public class Fare {
    private int nightSurcharge;
    private int rideFare;

    public int getNightSurcharge() {
        return nightSurcharge;
    }

    public void setNightSurcharge(int nightSurcharge) {
        this.nightSurcharge = nightSurcharge;
    }

    public int getRideFare() {
        return rideFare;
    }

    public void setRideFare(int rideFare) {
        this.rideFare = rideFare;
    }

    public Long getTotalFare() {
        return Long.valueOf(nightSurcharge * rideFare);
    }
}
