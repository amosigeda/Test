package com.lewic.bracelet.module.data.detail.pulse;

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
import com.lewic.bracelet.model.PulseModel;
import com.lewic.bracelet.module.data.detail.location.LocationInterface;
import com.lewic.bracelet.module.data.detail.location.LocationPresenter;
import com.lewic.bracelet.module.data.measure.MeasureActivity;
import com.lewic.bracelet.util.ToastUtil;

/**
 *
 * @Description
 * @date 2017/8/17 15:02
 */

public class PulseFragment extends MVPBaseFragment<PulseInterface, PulsePresenter> implements PulseInterface {

    private ImageView pluseIV;
    private TextView pluseTV;
    private TextView pluseResultTV;
    private Button startMeasureBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pluse, container, false);
    }

    @Override
    protected void initSelfView(View root) {
        pluseIV = (ImageView) root.findViewById(R.id.pluseIV);
        pluseTV = (TextView) root.findViewById(R.id.pluseTV);
        pluseResultTV = (TextView) root.findViewById(R.id.pluseResultTV);
        startMeasureBtn = (Button) root.findViewById(R.id.startMeasureBtn);
    }

    @Override
    protected void initListener() {
        startMeasureBtn.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {
        showProgressDialog();
        mPresenter.getLatestPulse(SharedPrefHelper.getInstance().getToken());
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.startMeasureBtn:
                Intent intent = new Intent();
                intent.setClass(this.getActivity(), MeasureActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected PulsePresenter createPresenter() {
        return new PulsePresenter();
    }

    @Override
    public void getLatestPulseSuccess(PulseModel pulseModel) {
        if(pulseModel!=null){
            pluseTV.setText(pulseModel.getHeartRate()+"");
            //脉搏状态
            if (BluetoothUtil.getInstance().getPulseState(pulseModel.getHeartRate()) == BluetoothUtil.STATE_NORMAL) {
                pluseIV.setImageResource(R.drawable.pulse_normal);
                pluseResultTV.setText(R.string.text_pluse_normal);
                pluseResultTV.setTextColor(getResources().getColor(R.color.color_616690));
            } else if (BluetoothUtil.getInstance().getPulseState(pulseModel.getHeartRate()) == BluetoothUtil.STATE_HIGH) {
                pluseIV.setImageResource(R.drawable.pulse_high);
                pluseResultTV.setText(R.string.text_pluse_height);
                pluseResultTV.setTextColor(getResources().getColor(R.color.color_ff5a8f));
            } else if (BluetoothUtil.getInstance().getPulseState(pulseModel.getHeartRate()) == BluetoothUtil.STATE_LOW) {
                pluseIV.setImageResource(R.drawable.pulse_high);
                pluseResultTV.setText(R.string.text_low);
                pluseResultTV.setTextColor(getResources().getColor(R.color.color_ff5a8f));
            }
        }
        dismissProgressDialog();
    }

    @Override
    public void getLatestPulseFailed(int errorNo, String strMsg) {
        ToastUtil.showToast(strMsg);
        showProgressDialog();
    }
}
