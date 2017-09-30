package com.lewic.bracelet.module.data.measure;

import com.lewic.bracelet.base.BaseViewInterface;
import com.lewic.bracelet.model.PulseModel;

/**
 * @Description
 *
 * @date 2017/8/17 15:07
*/
public interface MeasureInterface extends BaseViewInterface {
    void uploadDataSuccess();
    void uploadDataFailed(int errorNo, String strMsg);
}
