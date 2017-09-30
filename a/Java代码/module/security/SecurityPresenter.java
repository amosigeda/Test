package com.lewic.bracelet.module.security;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.model.HomePageModel;
import com.lewic.bracelet.model.SecurityFenceModel;
import com.lewic.bracelet.module.data.DataMonitoringInterface;
import com.lewic.bracelet.network.NetWorkManager;

/**
 *
 * @Description
 * @date 2017/8/17 14:58
 */
public class SecurityPresenter extends BasePresenter<SecurityInterface> {
    public void getSecurityFence(String token) {
        NetWorkManager.getSecurityFence(token, new RequestHttpCallback<SecurityFenceModel>(new
                TypeToken<IResponse<SecurityFenceModel>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                SecurityInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.getSecurityFenceFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<SecurityFenceModel> response) {
                if (response != null) {
                    SecurityInterface demoInterface = getViewInterface();
                    SecurityFenceModel securityFenceModel = response.getData();
                    if (demoInterface != null) {
                        demoInterface.getSecurityFenceSuccess(securityFenceModel);
                    }
                }
            }
        });
    }

}
