package com.lewic.bracelet.module.data;

import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BaseViewInterface;
import com.lewic.bracelet.model.HomePageModel;

import java.util.ArrayList;

/**
 * @Description
 *
 * @date 2017/8/17 15:07
*/
public interface DataMonitoringInterface extends BaseViewInterface {
    void getLatestDataSuccess(HomePageModel homePageModel);
    void getLatestDataFailed(int errorNo, String strMsg);
}
