package com.lewic.bracelet.network;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.letv.net.NetManager;
import com.letv.net.RequestHttpCallback;
import com.letv.net.tools.LetvLog;
import com.lewic.bracelet.MyApplication;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.util.DeviceUtil;
import com.lewic.bracelet.util.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class NetRequest {
    public static <T> void get(String url, Map<String, String>
            params, RequestHttpCallback<T> callBack) {
        getInner(getUrl(url), params, callBack);
    }
    public static <T> void get(String baseUrl, String url, Map<String, String>
            params, RequestHttpCallback<T> callBack) {
        getInner(getUrl(baseUrl, url), params, callBack);
    }

    private static <T> void getInner(String url, Map<String, String>
            params, RequestHttpCallback<T> callBack) {
        // 设置公共参数
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.putAll(getCommonParamsMap());
        String urlString = withUrlSuffix(url, params);
        Context context = MyApplication.getInstance();
        NetManager<T> netManager = NetManager.getInstance(context);
        netManager.doGet(context, urlString, getRequestHeader(), callBack);
    }

    /**
     * POST请求，使用jsonObject的形式
     *
     * @param url
     * @param object
     * @param callBack
     * @param <T>
     */
    public static <T> void post(String url, Object object, RequestHttpCallback<T> callBack) {
        Context context = MyApplication.getInstance();
        NetManager<T> netManager = NetManager.getInstance(context);
        netManager.doPost(context, withUrlSuffix(getUrl(url), getCommonParamsMap()), getRequestHeader(), object, callBack,
                true, false);
    }

    public static <T> void post(String url, Map<String, String> urlParams,Object bodyParams, RequestHttpCallback<T> callBack) {
        Context context = MyApplication.getInstance();
        NetManager<T> netManager = NetManager.getInstance(context);
        Map<String, String> params1=getCommonParamsMap();
        params1.putAll(urlParams);
        netManager.doPost(context, withUrlSuffix(getUrl(url), params1), getRequestHeader(), bodyParams, callBack,
                true, false);
    }

    public static void download(Context context, String storeDir, String url, RequestHttpCallback<String> callback) {
        NetManager<String> netManager = NetManager.getInstance(context);
        netManager.download(context, storeDir, url, callback);
    }

    private static String withUrlSuffix(String url, Map<String, String> params) {
        if (null != params && params.size() > 0) {
            try {
                Set<Map.Entry<String, String>> paramsSet = params.entrySet();
                // GET请求
                StringBuilder stringBuilder = new StringBuilder(url);
                if (null != paramsSet) {
                    stringBuilder.append("?");
                    int i = 0;
                    String key;
                    String value;
                    for (Map.Entry<String, String> entry : paramsSet) {
                        if (i > 0) {
                            stringBuilder.append('&');
                        }
                        i++;
                        key = entry.getKey();
                        value = entry.getValue();
                        LetvLog.d("key：" + key + "---value:" + value);
                        stringBuilder.append(key);
                        stringBuilder.append('=');
                        stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
                    }
                    return stringBuilder.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    private static String getUrl(String baseUrl, String suffix) {
        return new StringBuffer(baseUrl).append(suffix + "").toString();
    }

    private static String getUrl(String suffix) {
        return getUrl(API.BASE_URL, suffix);
    }

    public static String getUrl(String suffix, Map<String, String> params) {
        return withUrlSuffix(getUrl(suffix), params);
    }

    /**
     * 根据自身应用需要添加相应的head
     *
     * @return
     */
    private static Map<String, String> getRequestHeader() {
        Map<String, String> headParams = new HashMap<String, String>();
        Set<Map.Entry<String, String>> commonParamsSet = getCommonParamsMap().entrySet();

        for (Map.Entry<String, String> entry : commonParamsSet) {
            try {
                headParams.put(entry.getKey(),
                        URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return headParams;
    }

    /**
     * 获取通用参数
     *
     * @return
     */
    static Map<String, String> getCommonParamsMap() {
        Map<String, String> commonParamsMap = new HashMap<String, String>();
        Application app = MyApplication.getInstance();
        String macString = DeviceUtil.getMacAddress(app);
        String imeiString = DeviceUtil.getIMEI(app);
        //String channelString = ChannelManager.getCpid(app);
        String appVersionString = "" + DeviceUtil.getVersionCode(app);
        String deviceInfoString = DeviceUtil.getDeviceName();
        String osVersionString = DeviceUtil.getOSVersion();
        String timeStampString = "" + System.currentTimeMillis();

        commonParamsMap.put("mac", StringUtils.changeNullToEmpty(macString));
        //commonParamsMap.put("imei", StringUtils.changeNullToEmpty(imeiString));
        commonParamsMap.put("appVersion", StringUtils.changeNullToEmpty(appVersionString));
        commonParamsMap.put("deviceInfo", StringUtils.changeNullToEmpty(deviceInfoString));
        commonParamsMap.put("osVersion", StringUtils.changeNullToEmpty(osVersionString));
        commonParamsMap.put("timeStamp", StringUtils.changeNullToEmpty(timeStampString));
        //commonParamsMap.put("channel",  StringUtils.changeNullToEmpty(channelString));
        /*if(!TextUtils.isEmpty(SharedPrefHelper.getInstance().getToken())){
            commonParamsMap.put("token", SharedPrefHelper.getInstance().getToken());
        }*/
        return commonParamsMap;
    }
}
