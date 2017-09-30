package com.lewic.bracelet.module.data.detail.bloodpressure;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.model.BloodPressureModel;
import com.lewic.bracelet.network.NetWorkManager;

/**
 *
 * @Description
 * @date 2017/8/17 14:58
 */
public class BloodpressurePresenter extends BasePresenter<BloodPressureInterface> {
    public void getLatestBloodPressure(String token) {
        NetWorkManager.getLatestBloodPressure(token, new RequestHttpCallback<BloodPressureModel>(new
                TypeToken<IResponse<BloodPressureModel>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                BloodPressureInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.getLatestBloodPressureFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<BloodPressureModel> response) {
                if (response != null) {
                    BloodPressureInterface demoInterface = getViewInterface();
                    BloodPressureModel bloodPressureModel = response.getData();
                    if (demoInterface != null) {
                        demoInterface.getLatestBloodPressureSuccess(bloodPressureModel);
                    }
                }
            }
        });
    }

}
