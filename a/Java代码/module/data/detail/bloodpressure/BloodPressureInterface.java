package com.lewic.bracelet.module.data.detail.bloodpressure;

import com.lewic.bracelet.base.BaseViewInterface;
import com.lewic.bracelet.model.BloodPressureModel;
import com.lewic.bracelet.model.HomePageModel;

/**
 * @Description
 *
 * @date 2017/8/17 15:07
*/
public interface BloodPressureInterface extends BaseViewInterface {
    void getLatestBloodPressureSuccess(BloodPressureModel bloodPressureModel);
    void getLatestBloodPressureFailed(int errorNo, String strMsg);
}
