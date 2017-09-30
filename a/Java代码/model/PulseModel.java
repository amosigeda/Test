package com.lewic.bracelet.model;

import java.io.Serializable;

/**
 *
 * @Description
 * @date 2017/9/3 17:10
 */

public class PulseModel implements Serializable {
    private int heartRate;

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
}
