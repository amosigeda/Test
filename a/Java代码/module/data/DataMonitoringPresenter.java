package com.lewic.bracelet.module.data;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.model.HomePageModel;
import com.lewic.bracelet.network.NetWorkManager;

import java.util.ArrayList;

/**
 *
 * @Description
 * @date 2017/8/17 14:58
 */
public class DataMonitoringPresenter extends BasePresenter<DataMonitoringInterface> {
    public void getLatestData(String token) {
        NetWorkManager.getLatestData(token, new RequestHttpCallback<HomePageModel>(new
                TypeToken<IResponse<HomePageModel>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                DataMonitoringInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.getLatestDataFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<HomePageModel> response) {
                if (response != null) {
                    DataMonitoringInterface demoInterface = getViewInterface();
                    HomePageModel homePageModel = response.getData();
                    if (demoInterface != null) {
                        demoInterface.getLatestDataSuccess(homePageModel);
                    }
                }
            }
        });
    }

}
