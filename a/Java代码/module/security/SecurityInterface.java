package com.lewic.bracelet.module.security;

import com.lewic.bracelet.base.BaseViewInterface;
import com.lewic.bracelet.model.HomePageModel;
import com.lewic.bracelet.model.SecurityFenceModel;

/**
 * @Description
 *
 * @date 2017/8/17 15:07
*/
public interface SecurityInterface extends BaseViewInterface {
    void getSecurityFenceSuccess(SecurityFenceModel securityFenceModel);
    void getSecurityFenceFailed(int errorNo, String strMsg);
}
