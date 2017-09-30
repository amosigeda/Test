package com.lewic.bracelet.module.data.detail.pulse;

import android.location.Location;

import com.lewic.bracelet.base.BaseViewInterface;
import com.lewic.bracelet.model.PulseModel;

/**
 * @Description
 *
 * @date 2017/8/17 15:07
*/
public interface PulseInterface extends BaseViewInterface {
    void getLatestPulseSuccess(PulseModel pulseModel);
    void getLatestPulseFailed(int errorNo, String strMsg);
}
