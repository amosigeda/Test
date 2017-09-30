package com.lewic.bracelet.model;

import java.io.Serializable;

/**
 * @Description
 *
 * @date 2017/7/28 11:36
*/

public class MyDevice implements Serializable {
    private String mac;
    private String name;
    private String model;
    private int version;
    private int type;


    public MyDevice() {
        super();
    }

    public MyDevice(String mac, String name, int type, String model, int version) {
        this.mac = mac;
        this.name = name;
        this.type = type;
        this.model = model;
        this.version = version;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
