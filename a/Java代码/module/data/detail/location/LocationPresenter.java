package com.lewic.bracelet.module.data.detail.location;

import com.google.gson.reflect.TypeToken;
import com.letv.net.IResponse;
import com.letv.net.RequestHttpCallback;
import com.lewic.bracelet.base.BasePresenter;
import com.lewic.bracelet.model.LocationModel;
import com.lewic.bracelet.network.NetWorkManager;

import java.util.List;

/**
 *
 * @Description
 * @date 2017/8/17 14:58
 */
public class LocationPresenter extends BasePresenter<LocationInterface> {
    public void getLatestLocation(String token) {
        NetWorkManager.getLatestLocation(token, new RequestHttpCallback<LocationModel>(new
                TypeToken<IResponse<LocationModel>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                LocationInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.getLatestLocationFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<LocationModel> response) {
                if (response != null) {
                    LocationInterface demoInterface = getViewInterface();
                    LocationModel location = response.getData();
                    if (demoInterface != null) {
                        demoInterface.getLatestLocationSuccess(location);
                    }
                }
            }
        });
    }

    public void locationRequest(String token) {
        NetWorkManager.locationRequest(token, new RequestHttpCallback<Object>(new
                TypeToken<IResponse<Object>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                LocationInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.locationRequestFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<Object> response) {
                if (response != null) {
                    LocationInterface demoInterface = getViewInterface();
                    if (demoInterface != null) {
                        demoInterface.locationRequestSuccess();
                    }
                }
            }
        });
    }

    public void getCurrentLocation(String token) {
        NetWorkManager.getCurrentLocation(token, new RequestHttpCallback<LocationModel>(new
                TypeToken<IResponse<LocationModel>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                LocationInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.getCurrentLocationFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<LocationModel> response) {
                if (response != null) {
                    LocationInterface demoInterface = getViewInterface();
                    LocationModel location = response.getData();
                    if (demoInterface != null) {
                        demoInterface.getCurrentLocationSuccess(location);
                    }
                }
            }
        });
    }

    /**
     * 获取轨迹
     * @param token
     * @param type
     */
    public void getTrack(String token, final int type) {
        NetWorkManager.getTrack(token, type,new RequestHttpCallback<List<LocationModel>>(new
                TypeToken<IResponse<List<LocationModel>>>() {
                }.getType()) {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                LocationInterface demoInterface = getViewInterface();
                if (demoInterface != null) {
                    demoInterface.getTrackFailed(errorNo, strMsg);
                }
            }

            @Override
            public void responseSuccess(IResponse<List<LocationModel>> response) {
                if (response != null) {
                    LocationInterface demoInterface = getViewInterface();
                    List<LocationModel> locations = response.getData();
                    if (demoInterface != null) {
                        demoInterface.getTrackSuccess(locations,type);
                    }
                }
            }
        });
    }

}
