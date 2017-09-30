package com.lewic.bracelet.module.data.detail.pulse;

import android.location.Location;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.model.PulseModel;
import com.lewic.bracelet.module.data.detail.location.LocationInterface;
import com.lewic.bracelet.network.NetWorkManager;

/**
 *
 * @Description
 * @date 2017/8/17 14:58
 */
public class PulsePresenter extends BasePresenter<PulseInterface> {
    public void getLatestPulse(String token) {
        NetWorkManager.getLatestPulse(token, new RequestHttpCallback<PulseModel>(new
                TypeToken<IResponse<PulseModel>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                PulseInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.getLatestPulseFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<PulseModel> response) {
                if (response != null) {
                    PulseInterface demoInterface = getViewInterface();
                    PulseModel pulseModel = response.getData();
                    if (demoInterface != null) {
                        demoInterface.getLatestPulseSuccess(pulseModel);
                    }
                }
            }
        });
    }

}
