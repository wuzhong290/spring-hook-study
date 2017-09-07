package com.demo.designMode.model;

/**
 * Created by wuzhong on 2017/9/7.
 */
public class TaxiRide {
    private Boolean nightSurcharge;
    private Long distanceInMile;

    public Boolean getNightSurcharge() {
        return nightSurcharge;
    }

    public void setNightSurcharge(Boolean nightSurcharge) {
        this.nightSurcharge = nightSurcharge;
    }

    public Long getDistanceInMile() {
        return distanceInMile;
    }

    public void setDistanceInMile(Long distanceInMile) {
        this.distanceInMile = distanceInMile;
    }
}
