package com.lewic.bracelet.module.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lewic.bracelet.R;
import com.lewic.bracelet.base.MVPBaseFragment;
import com.lewic.bracelet.constant.Params;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.model.BloodPressureModel;
import com.lewic.bracelet.model.HomePageModel;
import com.lewic.bracelet.model.LocationModel;
import com.lewic.bracelet.model.PulseModel;
import com.lewic.bracelet.module.data.detail.DataDetailActivity;
import com.lewic.bracelet.util.ToastUtil;

/**
 *
 * @Description
 * @date 2017/8/17 14:29
 */

public class DataMonitoringFragment extends MVPBaseFragment<DataMonitoringInterface, DataMonitoringPresenter> implements DataMonitoringInterface {

    private TextView pulseTV, bloodPressureTV, locationTV;
    private RelativeLayout pulseRL, bloodpressureRL, LocationRL;
    private Context context;
    private HomePageModel homePageModel;

    @Override
    protected DataMonitoringPresenter createPresenter() {
        return new DataMonitoringPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    protected void initSelfView(View root) {
        context = this.getActivity();
        pulseTV = (TextView) root.findViewById(R.id.pulseTV);
        bloodPressureTV = (TextView) root.findViewById(R.id.bloodPressureTV);
        locationTV = (TextView) root.findViewById(R.id.locationTV);
        pulseRL = (RelativeLayout) root.findViewById(R.id.pulseRL);
        bloodpressureRL = (RelativeLayout) root.findViewById(R.id.bloodpressureRL);
        LocationRL = (RelativeLayout) root.findViewById(R.id.locationRL);
    }

    @Override
    protected void initListener() {
        pulseRL.setOnClickListener(this);
        bloodpressureRL.setOnClickListener(this);
        LocationRL.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {
        showProgressDialog();
        mPresenter.getLatestData(SharedPrefHelper.getInstance().getToken());
    }

    @Override
    public void onClickEvent(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.pulseRL:
                intent = new Intent(context, DataDetailActivity.class);
                intent.putExtra(Params.index, 0);
                startActivity(intent);
                break;
            case R.id.bloodpressureRL:
                intent = new Intent(context, DataDetailActivity.class);
                intent.putExtra(Params.index, 1);
                startActivity(intent);
                break;
            case R.id.locationRL:
                intent = new Intent(context, DataDetailActivity.class);
                intent.putExtra(Params.index, 2);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getLatestDataSuccess(HomePageModel homePageModel) {
        if(homePageModel!=null){
            this.homePageModel=homePageModel;
            BloodPressureModel heartPressure=homePageModel.getHeartPressure();
            PulseModel heartRate=homePageModel.getHeartRate();
            LocationModel location=homePageModel.getLocation();
            if(heartPressure!=null){
                bloodPressureTV.setText(heartPressure.getMaxHeartPressure()+"/"+heartPressure.getMinHeartPressure());
            }
            if(heartRate!=null){
                pulseTV.setText(heartRate.getHeartRate()+"");
            }
            if(location!=null){
                locationTV.setText(location.getStatus());
            }
        }
        dismissProgressDialog();
    }

    @Override
    public void getLatestDataFailed(int errorNo, String strMsg) {
        dismissProgressDialog();
        ToastUtil.showToast(strMsg);
    }
}
