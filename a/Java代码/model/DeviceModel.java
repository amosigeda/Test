package com.lewic.bracelet.model;

import java.io.Serializable;
import java.io.SerializablePermission;

/**
 * Created on 2017/9/24.
 */

public class DeviceModel implements Serializable {
    //"imei":"sdf23"
    //"dv":"", //设备固件版本号
    // "sd":"",  //软件版本号
    //"bindingtime":1500123042125         //毫秒
    private String imei;
    private String dv;
    private String sd;
    private long bindingtime;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public long getBindingtime() {
        return bindingtime;
    }

    public void setBindingtime(long bindingtime) {
        this.bindingtime = bindingtime;
    }
}
