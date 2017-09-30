package com.lewic.bracelet.model;

import java.io.Serializable;

/**
 *
 * @Description
 * @date 2017/9/3 17:09
 */

public class BloodPressureModel implements Serializable{
    private int maxHeartPressure;
    private int minHeartPressure;

    public int getMaxHeartPressure() {
        return maxHeartPressure;
    }

    public void setMaxHeartPressure(int maxHeartPressure) {
        this.maxHeartPressure = maxHeartPressure;
    }

    public int getMinHeartPressure() {
        return minHeartPressure;
    }

    public void setMinHeartPressure(int minHeartPressure) {
        this.minHeartPressure = minHeartPressure;
    }
}
