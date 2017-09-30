package com.lewic.bracelet.module.me.device;

import com.lewic.bracelet.base.BaseViewInterface;
import com.lewic.bracelet.model.DeviceModel;

/**
 * @Description
 *
 * @date 2017/8/17 15:07
*/
public interface BindDeviceInterface extends BaseViewInterface {
    void bindDeviceSuccess();
    void bindDeviceFailed(int errorNo, String strMsg);
}
