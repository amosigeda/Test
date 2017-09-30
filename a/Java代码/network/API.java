package com.lewic.bracelet.network;


import com.lewic.bracelet.constant.AppConfig;

/**
 *
 * @Description:
 * @date 2016-12-5 下午11:18:46
 */
public class API {
    static final String SERVER_URL = "http://114.215.17.137:8080";//正式环境
    static final String TEST_URL = "http://114.215.17.137:8080";//测试环境
    static final String BASE_URL = AppConfig.SERVER_MODE ? TEST_URL : SERVER_URL;
    static final String getLatestData = "/status/search/latest/";
    static final String getLatestBloodPressure = "/heartPressure/search/latest/";
    static final String getLatestPulse = "/heartRate/search/latest/";
    static final String getLatestLocation = "/location/search/latest/";
    static final String uploadData = "/status/upload";
    static final String locationRequest = "/location/ask/location/";
    static final String getCurrentLocation = "/location/search/realtime/";
    static final String getTrack = "/location/search/footprint/";
    static final String getAuthCode = "/user/getAuthCode/";
    static final String regist = "/user/register";
    static final String login = "/user/login";
    static final String getDevice = "/user/device/";
    static final String bindDevice = "/user/device/bind";
    static final String unbindDevice = "/user/device/unbind";
    static final String getSecurityFence = "/security/fence/";
    static final String addWhite = "/sos/whitelist";//增加白名单信息
    static final String delWhite = "/sos/whitelist/del";//删除白名单信息接口
   public static final String selectWhite = "/sos/whitelist/";//查询白名单信息接口/sos/whitelist/del
}
