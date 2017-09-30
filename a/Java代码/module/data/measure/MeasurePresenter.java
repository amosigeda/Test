package com.lewic.bracelet.module.data.measure;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.model.PulseModel;
import com.lewic.bracelet.module.data.detail.pulse.PulseInterface;
import com.lewic.bracelet.network.NetWorkManager;

/**
 *
 * @Description
 * @date 2017/8/17 14:58
 */
public class MeasurePresenter extends BasePresenter<MeasureInterface> {
    public void uploadData(String token,int maxHeartPressure, int minHeartPressure, int heartRate) {
        NetWorkManager.uploadData(token,maxHeartPressure, minHeartPressure, heartRate, new RequestHttpCallback<String>(new
                TypeToken<IResponse<String>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                MeasureInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.uploadDataFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<String> response) {
                if (response != null) {
                    MeasureInterface demoInterface = getViewInterface();
                    if (demoInterface != null) {
                        demoInterface.uploadDataSuccess();
                    }
                }
            }
        });
    }

}
