package com.lewic.bracelet.module.me.white;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.account.login.LoginInterface;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.model.AddWhiteModel;
import com.lewic.bracelet.model.DeviceModel;
import com.lewic.bracelet.model.HomePageModel;
import com.lewic.bracelet.model.SelectWhiteModel;
import com.lewic.bracelet.model.UserInfoModel;
import com.lewic.bracelet.module.data.DataMonitoringInterface;
import com.lewic.bracelet.module.me.device.DeviceManagerInterface;
import com.lewic.bracelet.network.API;
import com.lewic.bracelet.network.NetWorkManager;
import com.lewic.bracelet.widget.QQListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/29.
 */

public class WhitePresenter extends BasePresenter<WhiteInterface> {
    /**
     * 查询白名单信息接口
     * @param token
     */
    public void selectWhite(final String token ) {
        NetWorkManager.selectWhiteDate(token, new RequestHttpCallback<SelectWhiteModel>(new
                TypeToken<IResponse<SelectWhiteModel>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                WhiteInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.selectWhiteFailed(errorNo, strMsg);
                }
            }
            @Override
            public void responseSuccess(IResponse<SelectWhiteModel> response) {
                if (response != null) {
                    WhiteInterface demoInterface = getViewInterface();
                    SelectWhiteModel selectWhiteModel = response.getData();
                    if (demoInterface != null) {
                        demoInterface.selectWhiteSuccess(selectWhiteModel);
                    }
                }
            }
        });
    }
    /**
     * 增加白名单信息接口
     * @param token
     * @param phone
     * @param name
     */
    public void addWhite(final String token, final String phone,final String name) {
        NetWorkManager.addWhiteDate(token, phone,name, new RequestHttpCallback<Object>(new
                TypeToken<IResponse<Object>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                WhiteInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.addWhiteFailed(errorNo, strMsg);
                }
            }
            @Override
            public void responseSuccess(IResponse<Object> response) {
                if (response != null) {
                    WhiteInterface demoInterface = getViewInterface();
                    if (demoInterface != null) {
                        demoInterface.addWhiteSuccess();
                    }
                }
            }
        });
    }

    /**
     * 删除白名单信息接口
     * @param token
     * @param id
     */
    public void delWhite(final String token, final int id) {
        NetWorkManager.delWhiteDate(token,id, new RequestHttpCallback<Object>(new
                TypeToken<IResponse<Object>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                WhiteInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.delWhiteFailed(errorNo, strMsg);
                }
            }
            @Override
            public void responseSuccess(IResponse<Object> response) {
                if (response != null) {
                    WhiteInterface demoInterface = getViewInterface();
                    if (demoInterface != null) {
                        demoInterface.delWhiteSuccess();
                    }
                }
            }
        });
    }
}
