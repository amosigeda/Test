package com.lewic.bracelet.module.data.detail.location;

import com.lewic.bracelet.base.BaseViewInterface;
import com.lewic.bracelet.model.LocationModel;

import java.util.List;

/**
 * @Description
 *
 * @date 2017/8/17 15:07
*/
public interface LocationInterface extends BaseViewInterface {
    void getLatestLocationSuccess(LocationModel location);
    void getLatestLocationFailed(int errorNo, String strMsg);
    void locationRequestSuccess();
    void locationRequestFailed(int errorNo, String strMsg);
    void getCurrentLocationSuccess(LocationModel location);
    void getCurrentLocationFailed(int errorNo, String strMsg);
    void getTrackSuccess(List<LocationModel> locationModels,int type);
    void getTrackFailed(int errorNo, String strMsg);

}
