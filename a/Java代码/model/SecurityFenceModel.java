package com.lewic.bracelet.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2017/9/27.
 */

public class SecurityFenceModel implements Serializable{
    private String id;
    private String lat;
    private String lng;
    private int radius;
    private List<SecurityFenceRecord> fencelog;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public List<SecurityFenceRecord> getFencelog() {
        return fencelog;
    }

    public void setFencelog(List<SecurityFenceRecord> fencelog) {
        this.fencelog = fencelog;
    }
}
