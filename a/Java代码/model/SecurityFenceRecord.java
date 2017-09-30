package com.lewic.bracelet.model;

import java.io.Serializable;

/**
 * Created on 2017/9/27.
 */

public class SecurityFenceRecord implements Serializable{

//    "id": 123,
//            "imei": "1r12r12r",     // 发生的设备号
//            "lat": "39.1231",       // 设置的电子围栏坐标
//            "lng": "110.1231",      // 设置的电子围栏坐标
//            "radius": 500,          // 设置的电子围栏半径
//            "lat1": "39.1231",      // 设备当前坐标
//            "lng1": "110.1231",     // 设备当前坐标
//            "status": 1,            // 1:电子围栏内，2:电子围栏外
//            "content": "设备离开电子围栏"", // 文字说明
//            "timestamp": 15011237284123
    private String content;
    private String id;
    private String imei;
    private String lat;
    private String lng;
    private int radius;
    private String lat1;
    private String lng1;
    private String status;
    private long timestamp;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public String getLat1() {
        return lat1;
    }

    public void setLat1(String lat1) {
        this.lat1 = lat1;
    }

    public String getLng1() {
        return lng1;
    }

    public void setLng1(String lng1) {
        this.lng1 = lng1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
