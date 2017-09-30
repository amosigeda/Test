package com.lewic.bracelet.model;

import java.io.Serializable;

/**
 *
 * @Description
 * @date 2017/9/3 17:12
 */

public class LocationModel implements Serializable{
    private String lat;
    private String lng;
    private String status;
    private long timestamp;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
