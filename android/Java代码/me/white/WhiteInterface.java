package com.lewic.bracelet.module.me.white;

import com.lewic.bracelet.base.BaseViewInterface;
import com.lewic.bracelet.model.AddWhiteModel;
import com.lewic.bracelet.model.SelectWhiteModel;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/29.
 */

public interface WhiteInterface extends BaseViewInterface {
    void addWhiteSuccess();
    void addWhiteFailed(int errorNo, String strMsg);
    void delWhiteSuccess();
    void delWhiteFailed(int errorNo, String strMsg);
    void selectWhiteSuccess(SelectWhiteModel selectWhiteModel);
    void selectWhiteFailed(int errorNo, String strMsg);

}
