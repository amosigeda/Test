package com.lewic.bracelet.module.me.device;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.model.DeviceModel;
import com.lewic.bracelet.network.NetWorkManager;

/**
 *
 * @Description
 * @date 2017/8/17 14:58
 */
public class BindDevicePresenter extends BasePresenter<BindDeviceInterface> {
    public void bindDevice(String token, String imei) {
        NetWorkManager.bindDevice(token, imei,new RequestHttpCallback<Object>(new
                TypeToken<IResponse<Object>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                BindDeviceInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.bindDeviceFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<Object> response) {
                if (response != null) {
                    BindDeviceInterface demoInterface = getViewInterface();
                    if (demoInterface != null) {
                        demoInterface.bindDeviceSuccess();
                    }
                }
            }
        });
    }

}
