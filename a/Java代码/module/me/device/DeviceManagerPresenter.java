package com.lewic.bracelet.module.me.device;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.model.DeviceModel;
import com.lewic.bracelet.module.data.DataMonitoringInterface;
import com.lewic.bracelet.network.NetWorkManager;

/**
 *
 * @Description
 * @date 2017/8/17 14:58
 */
public class DeviceManagerPresenter extends BasePresenter<DeviceManagerInterface> {
    public void getDevice(String token) {
        NetWorkManager.getDevice(token, new RequestHttpCallback<DeviceModel>(new
                TypeToken<IResponse<DeviceModel>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                DeviceManagerInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.getDeviceFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<DeviceModel> response) {
                if (response != null) {
                    DeviceManagerInterface demoInterface = getViewInterface();
                    DeviceModel homePageModel = response.getData();
                    if (demoInterface != null) {
                        demoInterface.getDeviceSuccess(homePageModel);
                    }
                }
            }
        });
    }

}
