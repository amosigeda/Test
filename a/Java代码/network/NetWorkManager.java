package com.lewic.bracelet.network;

import android.util.Log;
import android.widget.Toast;

import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.model.BloodPressureModel;
import com.lewic.bracelet.model.DeviceModel;
import com.lewic.bracelet.model.HomePageModel;
import com.lewic.bracelet.model.LocationModel;
import com.lewic.bracelet.model.PulseModel;
import com.lewic.bracelet.model.SecurityFenceModel;
import com.lewic.bracelet.model.SelectWhiteModel;
import com.lewic.bracelet.model.UserInfoModel;
import com.lewic.bracelet.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 *
 * @Description:
 * @date 2016-12-5 下午11:44:17
 */
public class NetWorkManager {
    private static final String TAG = "NetWorkManager";


    /**
     * 获取最新的数据
     * @param token
     * @param callback
     */
   public static void getLatestData(String token, RequestHttpCallback<HomePageModel> callback) {
        NetRequest.get(API.getLatestData + token, null, callback);
    }

    /**
     * 查询最近血压
     * @param token
     * @param callback
     */
    public static void getLatestBloodPressure(String token, RequestHttpCallback<BloodPressureModel> callback) {
        NetRequest.get(API.getLatestBloodPressure + token, null, callback);
    }

    /**
     * 获取最近脉搏
     * @param token
     * @param callback
     */
    public static void getLatestPulse(String token, RequestHttpCallback<PulseModel> callback) {
        NetRequest.get(API.getLatestPulse + token, null, callback);
    }

    /**
     *查询最近位置
     * @param token
     * @param callback
     */
    public static void getLatestLocation(String token, RequestHttpCallback<LocationModel> callback) {
        NetRequest.get(API.getLatestLocation + token, null, callback);
    }

    /**
     * 上传数据
     * @param maxHeartPressure
     * @param minHeartPressure
     * @param heartRate
     * @param callback
     */
    public static void uploadData(String token,int maxHeartPressure, int minHeartPressure, int heartRate,
                              RequestHttpCallback<String>
                                      callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("maxHeartPressure", String.valueOf(maxHeartPressure));
        params.put("minHeartPressure", String.valueOf(minHeartPressure));
        params.put("heartRate", String.valueOf(heartRate));
        params.put("token", token);
        NetRequest.post(API.uploadData, params,params,callback);
    }

    public static void getCurrentLocation(String token, RequestHttpCallback<LocationModel> callback) {
        NetRequest.get(API.getCurrentLocation + token, null, callback);
    }
    public static void locationRequest(String token, RequestHttpCallback<Object> callback) {
        NetRequest.get(API.locationRequest + token, null, callback);
    }

    /**
     *
     * @param token
     * @param type type: 【可选】时间类型，1：过去1小时，2：过去一天，不填默认是1
     * @param callback
     */
    public static void getTrack(String token, int type,RequestHttpCallback<List<LocationModel>> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type+"");
        NetRequest.get(API.getTrack + token, params, callback);
    }

    /**
     * 获取验证码
     *
     * @param telephone
     * @param callback
     */
    public static void getAuthCode(String telephone, RequestHttpCallback<Object> callback) {
        NetRequest.get(API.getAuthCode + telephone, null, callback);
    }

    /**
     * 注册
     *
     * @param tel
     * @param code
     * @param callback
     */
    public static void regist(String tel, String code, RequestHttpCallback<UserInfoModel> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("tel", tel);
        params.put("code", code);
        NetRequest.post(API.regist, params, params,callback);
    }

    /**
     * 注册
     *
     * @param tel
     * @param code
     * @param callback
     */
    public static void login(String tel, String code, RequestHttpCallback<UserInfoModel> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("tel", tel);
        params.put("code", code);
        NetRequest.post(API.login, params,params,callback);
    }

    /**
     * 获取绑定的手环设备
     * @param token
     * @param callback
     */
    public static void getDevice(String token, RequestHttpCallback<DeviceModel> callback) {
        NetRequest.get(API.getDevice + token, null, callback);
    }

    /**
     * 绑定设备
     * @param token
     * @param imei
     * @param callback
     */
    public static void bindDevice(String token, String imei, RequestHttpCallback<Object> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("imei", imei);
        NetRequest.post(API.bindDevice, params, params,callback);
    }

    /**
     * 解除绑定
     * @param token
     * @param imei
     * @param callback
     */
    public static void unbindDevice(String token, String imei, RequestHttpCallback<Object> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("imei", imei);
        NetRequest.post(API.unbindDevice, params, params,callback);
    }

    /**
     * 获取电子围栏信息
     * @param token
     * @param callback
     */
    public static void getSecurityFence(String token, RequestHttpCallback<SecurityFenceModel> callback) {
        NetRequest.get(API.selectWhite + token, null, callback);
    }

    /**
     * 添加白名单信息
     * @param token
     * @param phone
     * @param name
     * @param callback
     */
    public static void addWhiteDate(String token,String phone,String name,RequestHttpCallback<Object> callback){
        HashMap<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("phone",phone);
        params.put("name",name);
        NetRequest.post(API.addWhite,params,params,callback);
    }

    /**
     * 删除白名单信息
     * @param token
     * @param id
     * @param callback
     */
    public static void delWhiteDate(String token, int id, RequestHttpCallback<Object> callback){
        HashMap<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("id", String.valueOf(id));
        NetRequest.post(API.delWhite,params,params,callback);
    }

    /**
     * 查询白名单数据
     * @param token
     * @param callback
     */
    public static void selectWhiteDate(String token, RequestHttpCallback<SelectWhiteModel> callback) {
        NetRequest.get(API.selectWhite + token, null, callback);
    }

}