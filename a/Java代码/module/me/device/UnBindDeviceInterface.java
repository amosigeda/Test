package com.lewic.bracelet.module.me.device;

import com.lewic.bracelet.base.BaseViewInterface;

/**
 * @Description
 *
 * @date 2017/8/17 15:07
*/
public interface UnBindDeviceInterface extends BaseViewInterface {
    void unBindDeviceSuccess();
    void unBindDeviceFailed(int errorNo, String strMsg);
}
