package com.lewic.bracelet.module.data.detail.bloodpressure;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lewic.bracelet.R;
import com.lewic.bracelet.base.BaseFragment;
import com.lewic.bracelet.base.MVPBaseFragment;
import com.lewic.bracelet.bluetooth.BluetoothUtil;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.model.BloodPressureModel;
import com.lewic.bracelet.module.data.measure.MeasureActivity;
import com.lewic.bracelet.util.ToastUtil;

/**
 *
 * @Description
 * @date 2017/8/17 15:02
 */

public class BloodPressureFragment extends MVPBaseFragment<BloodPressureInterface, BloodpressurePresenter> implements BloodPressureInterface{

    private ImageView bloodPressureOutIV, bloodPressureInIV;
    private TextView bloodpressureTV, bloodpressureResultTV;
    private Button startMeasureBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bloodpressure, container, false);
    }

    @Override
    protected void initSelfView(View root) {
        bloodPressureOutIV = (ImageView) root.findViewById(R.id.bloodPressureOutIV);
        bloodPressureInIV = (ImageView) root.findViewById(R.id.bloodPressureInIV);
        bloodpressureTV = (TextView) root.findViewById(R.id.bloodpressureTV);
        bloodpressureResultTV = (TextView) root.findViewById(R.id.bloodpressureResultTV);
        startMeasureBtn= (Button) root.findViewById(R.id.startMeasureBtn);
    }

    @Override
    protected void initListener() {
        startMeasureBtn.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {
        showProgressDialog();
        mPresenter.getLatestBloodPressure(SharedPrefHelper.getInstance().getToken());
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()){
            case R.id.startMeasureBtn:
                Intent intent=new Intent();
                intent.setClass(this.getActivity(), MeasureActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected BloodpressurePresenter createPresenter() {
        return new BloodpressurePresenter();
    }

    @Override
    public void getLatestBloodPressureSuccess(BloodPressureModel bloodPressureModel) {

        if(bloodPressureModel!=null){
            bloodpressureTV.setText(bloodPressureModel.getMaxHeartPressure()+"/"+bloodPressureModel.getMinHeartPressure());
            //血压整体状态
            if (BluetoothUtil.getInstance().getPressureState(bloodPressureModel.getMaxHeartPressure(), bloodPressureModel.getMinHeartPressure()) == BluetoothUtil.STATE_NORMAL) {
                bloodpressureResultTV.setText(R.string.text_normal);
                bloodpressureResultTV.setTextColor(getResources().getColor(R.color.color_616690));
            } else if (BluetoothUtil.getInstance().getPressureState(bloodPressureModel.getMaxHeartPressure(), bloodPressureModel.getMinHeartPressure()) == BluetoothUtil.STATE_HIGH) {
                bloodpressureResultTV.setTextColor(getResources().getColor(R.color.color_ff5a8f));
                bloodpressureResultTV.setText(R.string.text_height);
            } else if (BluetoothUtil.getInstance().getPressureState(bloodPressureModel.getMaxHeartPressure(), bloodPressureModel.getMinHeartPressure()) == BluetoothUtil.STATE_LOW) {
                bloodpressureResultTV.setTextColor(getResources().getColor(R.color.color_ff5a8f));
                bloodpressureResultTV.setText(R.string.text_low);
            }
            if (BluetoothUtil.getInstance().getHighPressureState(bloodPressureModel.getMaxHeartPressure()) == BluetoothUtil.STATE_NORMAL) {
                bloodPressureOutIV.setImageResource(R.drawable.dial_out_normal);
            } else if (BluetoothUtil.getInstance().getHighPressureState(bloodPressureModel.getMaxHeartPressure()) == BluetoothUtil.STATE_HIGH) {
                bloodPressureOutIV.setImageResource(R.drawable.dial_out_height);
            } else if (BluetoothUtil.getInstance().getHighPressureState(bloodPressureModel.getMaxHeartPressure()) == BluetoothUtil.STATE_LOW) {
                bloodPressureOutIV.setImageResource(R.drawable.dial_out_low);
            }

            //低压状态
            if (BluetoothUtil.getInstance().getLowPressureState(bloodPressureModel.getMinHeartPressure()) == BluetoothUtil.STATE_NORMAL) {
                bloodPressureInIV.setImageResource(R.drawable.dial_in_normal);
            } else if (BluetoothUtil.getInstance().getLowPressureState(bloodPressureModel.getMinHeartPressure()) == BluetoothUtil.STATE_HIGH) {
                bloodPressureInIV.setImageResource(R.drawable.dial_in_height);
            } else if (BluetoothUtil.getInstance().getLowPressureState(bloodPressureModel.getMinHeartPressure()) == BluetoothUtil.STATE_LOW) {
                bloodPressureInIV.setImageResource(R.drawable.dial_in_low);
            }
        }
        dismissProgressDialog();
    }

    @Override
    public void getLatestBloodPressureFailed(int errorNo, String strMsg) {
        showProgressDialog();
        ToastUtil.showToast(strMsg);
    }
}
