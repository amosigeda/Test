package com.lewic.bracelet.module.me.device;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.network.NetWorkManager;

/**
 *
 * @Description
 * @date 2017/8/17 14:58
 */
public class UnBindDevicePresenter extends BasePresenter<UnBindDeviceInterface> {
    public void unBindDevice(String token, String imei) {
        NetWorkManager.unbindDevice(token, imei,new RequestHttpCallback<Object>(new
                TypeToken<IResponse<Object>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                UnBindDeviceInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.unBindDeviceFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<Object> response) {
                if (response != null) {
                    UnBindDeviceInterface demoInterface = getViewInterface();
                    if (demoInterface != null) {
                        demoInterface.unBindDeviceSuccess();
                    }
                }
            }
        });
    }

}
