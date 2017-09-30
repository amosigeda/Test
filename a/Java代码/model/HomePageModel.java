package com.lewic.bracelet.model;

import java.io.Serializable;

/**
 *
 * @Description
 * @date 2017/9/3 17:08
 */

public class HomePageModel implements Serializable{
    private BloodPressureModel heartPressure;
    private PulseModel heartRate;
    private LocationModel location;

    public BloodPressureModel getHeartPressure() {
        return heartPressure;
    }

    public void setHeartPressure(BloodPressureModel heartPressure) {
        this.heartPressure = heartPressure;
    }

    public PulseModel getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(PulseModel heartRate) {
        this.heartRate = heartRate;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }
}
