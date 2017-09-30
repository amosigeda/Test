package com.lewic.bracelet.module.data.measure;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lewic.bracelet.R;
import com.lewic.bracelet.base.MVPBaseActivity;
import com.lewic.bracelet.bluetooth.BluetoothUtil;
import com.lewic.bracelet.bluetooth.OnMeasureBloodListener;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.model.blood.Data;
import com.lewic.bracelet.model.blood.Error;
import com.lewic.bracelet.model.blood.Head;
import com.lewic.bracelet.model.blood.IBean;
import com.lewic.bracelet.model.blood.Pressure;
import com.lewic.bracelet.util.ToastUtil;

/**
 *
 * @Description
 * @date 2017/9/2 20:04
 */

public class MeasureActivity extends MVPBaseActivity<MeasureInterface, MeasurePresenter> implements MeasureInterface {
    private BleStateReceiver bleStateReceiver;
    private TextView measureStateTV, bloodpressureTV, pulseTV;
    public static final int REQUEST_ENABLE_BT = 2;
    private Button cancelBtn, reMeasureBtn, saveBtn;
    private RelativeLayout measureResultBtnRL;
    private ImageView bloodpressureStateIV, pulseStateIV, bloodPressureOutIV, bloodPressureInIV, pulseIV;
    private int maxHeartPressure, minHeartPressure, heartRate;

    @Override
    protected void loadXML() {
        setContentView(R.layout.activity_measure);
    }

    @Override
    protected void initSelfView() {
        bleStateReceiver = new BleStateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothUtil.ACTION_BLE_STATE_CHANGE);
        intentFilter.addAction(BluetoothUtil.ACTION_MEASURE_STATE);
        registerReceiver(bleStateReceiver, intentFilter);
        measureStateTV = (TextView) findViewById(R.id.measureStateTV);
        bloodpressureTV = (TextView) findViewById(R.id.bloodpressureTV);
        pulseTV = (TextView) findViewById(R.id.pulseTV);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        measureResultBtnRL = (RelativeLayout) findViewById(R.id.measureResultBtnRL);
        bloodpressureStateIV = (ImageView) findViewById(R.id.bloodpressureStateIV);
        pulseStateIV = (ImageView) findViewById(R.id.pulseStateIV);
        bloodPressureOutIV = (ImageView) findViewById(R.id.bloodPressureOutIV);
        bloodPressureInIV = (ImageView) findViewById(R.id.bloodPressureInIV);
        pulseIV = (ImageView) findViewById(R.id.pulseIV);
        reMeasureBtn = (Button) findViewById(R.id.reMeasureBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
    }

    @Override
    protected void initListener() {
        reMeasureBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {
        startMeasure();
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()){
            case R.id.reMeasureBtn:
                startMeasure();
                break;
            case R.id.saveBtn:
                showProgressDialog();
                mPresenter.uploadData(SharedPrefHelper.getInstance().getToken(),maxHeartPressure, minHeartPressure, heartRate);
                break;
        }
    }

    public void startMeasure() {
        if (BluetoothUtil.getInstance().getBleState() == BluetoothUtil.STATE_OFF) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else if (BluetoothUtil.getInstance().getBleState() == BluetoothUtil.STATE_CONNECTED) {
            BluetoothUtil.getInstance().startMeasure(onMeasureBloodListener);
        } else if (BluetoothUtil.getInstance().getBleState() != BluetoothUtil.STATE_CONNECTING) {
            BluetoothUtil.getInstance().startScan();
        } else if (BluetoothUtil.getInstance().getBleState() == BluetoothUtil.STATE_CONNECTING) {
            ToastUtil.showToast(R.string.connecting);
            measureStateTV.setText(R.string.text_connecting);
            reset();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bleStateReceiver != null) {
            unregisterReceiver(bleStateReceiver);
        }
        BluetoothUtil.getInstance().unRigisterMeasureBloodListener();
    }

    @Override
    protected MeasurePresenter createPresenter() {
        return new MeasurePresenter();
    }

    @Override
    public void uploadDataSuccess() {
        dismissProgressDialog();
        ToastUtil.showToast(R.string.text_save_success);
        finish();
    }

    @Override
    public void uploadDataFailed(int errorNo, String strMsg) {
        dismissProgressDialog();
        ToastUtil.showToast(strMsg);
    }

    class BleStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getAction() == BluetoothUtil.ACTION_BLE_STATE_CHANGE) {
                switch (BluetoothUtil.getInstance().getBleState()) {
                    case BluetoothUtil.STATE_CONNECTED:
                        BluetoothUtil.getInstance().startMeasure(onMeasureBloodListener);
                        break;
                    case BluetoothUtil.STATE_CONNECTING:
                        measureStateTV.setText(R.string.text_connecting);
                        reset();
                        break;
                    case BluetoothUtil.STATE_DISCONNECTED:
                        finish();
                        break;
                }
            } else if (arg1.getAction() == BluetoothUtil.ACTION_MEASURE_STATE) {
                int state = arg1.getIntExtra(BluetoothUtil.MEASURE_STATE, -1);
                switch (state) {
                    case BluetoothUtil.STATE_SCANING:
                        measureStateTV.setText(R.string.text_scaning);
                        reset();
                        break;
                    case BluetoothUtil.STATE_MEASUREING:
                        measureStateTV.setText(R.string.text_measureing);
                        reset();
                        break;
                    case BluetoothUtil.STATE_SCAN_FAILED:
                        finish();
                        break;

                }
            }
        }
    }

    private OnMeasureBloodListener onMeasureBloodListener = new OnMeasureBloodListener() {

        @Override
        public void onReceive(IBean bean) {
            onReceiveData(bean);
        }

        @Override
        public void onError(Error error) {
            switch (error.getError()) {
                case Error.ERROR_EEPROM:
                    onErrorToast("E-E 血压计异常,联系你的经销商!");
                    break;
                case Error.ERROR_HEART:
                    onErrorToast("E-1 人体心跳信号太小或压力突降!");
                    break;
                case Error.ERROR_DISTURB:
                    onErrorToast("E-2 杂讯干扰!");

                    break;
                case Error.ERROR_GASING:
                    onErrorToast("E-3 充气时间过长!");

                    break;
                case Error.ERROR_TEST:
                    onErrorToast("E-5 测得的结果异常!");
                    break;
                case Error.ERROR_REVISE:
                    onErrorToast("E-C 校正异常!");

                    break;
                case Error.ERROR_POWER:
                    onErrorToast("E-B 电源低电压!");
                    break;
            }
        }
    };

    public void onReceiveData(IBean bean) {
        switch (bean.getHead().getType()) {
            case Head.TYPE_PRESSURE:
                //progressbar.setProgress(((Pressure) bean).getPressure());
                //jd.setText(((Pressure) bean).getPressureHL()+"");
                bloodpressureTV.setText(((Pressure) bean).getPressureHL() + "");
                break;
            case Head.TYPE_RESULT:
                Data data = (Data) bean;
                onResultData(data);
                break;
            case Head.TYPE_MESSAGE:

                break;
        }
    }

    public void onErrorToast(String error) {
        ToastUtil.showToast(error);
        finish();
    }

    public void onResultData(Data data) {
        int sys = data.getSys();
        int dia = data.getDia();
        int pul = data.getPul();
        maxHeartPressure=sys;
        minHeartPressure=dia;
        heartRate=pul;
        pulseTV.setText(pul + "");
        bloodpressureTV.setText(sys + "/" + dia);
        //整体状态 正常 异常
        if (BluetoothUtil.getInstance().getPressureState(sys, dia) == BluetoothUtil.STATE_NORMAL && BluetoothUtil.getInstance().getPulseState(pul) == BluetoothUtil.STATE_NORMAL) {
            measureStateTV.setText(R.string.text_normal);
            measureStateTV.setTextColor(getResources().getColor(R.color.color_616690));
        } else {
            measureStateTV.setText(R.string.text_abnormal);
            measureStateTV.setTextColor(getResources().getColor(R.color.color_ff5a8f));
        }
        //血压整体状态
        if (BluetoothUtil.getInstance().getPressureState(sys, dia) == BluetoothUtil.STATE_NORMAL) {
            bloodpressureStateIV.setVisibility(View.GONE);
            bloodpressureTV.setTextColor(getResources().getColor(R.color.color_ffffff));
        } else if (BluetoothUtil.getInstance().getPressureState(sys, dia) == BluetoothUtil.STATE_HIGH) {
            bloodpressureTV.setTextColor(getResources().getColor(R.color.color_ff5a8f));
            bloodpressureStateIV.setImageResource(R.drawable.measure_value_high);
            bloodpressureStateIV.setVisibility(View.VISIBLE);
        } else if (BluetoothUtil.getInstance().getPressureState(sys, dia) == BluetoothUtil.STATE_LOW) {
            bloodpressureTV.setTextColor(getResources().getColor(R.color.color_999999));
            bloodpressureStateIV.setImageResource(R.drawable.measure_value_low);
            bloodpressureStateIV.setVisibility(View.VISIBLE);
        }
        //高压状态
        bloodPressureOutIV.setVisibility(View.VISIBLE);
        if (BluetoothUtil.getInstance().getHighPressureState(sys) == BluetoothUtil.STATE_NORMAL) {
            bloodPressureOutIV.setImageResource(R.drawable.dial_out_normal);
        } else if (BluetoothUtil.getInstance().getHighPressureState(sys) == BluetoothUtil.STATE_HIGH) {
            bloodPressureOutIV.setImageResource(R.drawable.dial_out_height);
        } else if (BluetoothUtil.getInstance().getHighPressureState(sys) == BluetoothUtil.STATE_LOW) {
            bloodPressureOutIV.setImageResource(R.drawable.dial_out_low);
        }

        //低压状态
        bloodPressureInIV.setVisibility(View.VISIBLE);
        if (BluetoothUtil.getInstance().getLowPressureState(dia) == BluetoothUtil.STATE_NORMAL) {
            bloodPressureInIV.setImageResource(R.drawable.dial_in_normal);
        } else if (BluetoothUtil.getInstance().getLowPressureState(dia) == BluetoothUtil.STATE_HIGH) {
            bloodPressureInIV.setImageResource(R.drawable.dial_in_height);
        } else if (BluetoothUtil.getInstance().getLowPressureState(dia) == BluetoothUtil.STATE_LOW) {
            bloodPressureInIV.setImageResource(R.drawable.dial_in_low);
        }

        //脉搏状态
        if (BluetoothUtil.getInstance().getPulseState(pul) == BluetoothUtil.STATE_NORMAL) {
            pulseIV.setImageResource(R.drawable.pulse_measure_low);
            pulseTV.setTextColor(getResources().getColor(R.color.color_ffffff));
            pulseStateIV.setVisibility(View.GONE);
        } else if (BluetoothUtil.getInstance().getPulseState(pul) == BluetoothUtil.STATE_HIGH) {
            pulseIV.setImageResource(R.drawable.pulse_measure_high);
            pulseTV.setTextColor(getResources().getColor(R.color.color_ff5a8f));
            pulseStateIV.setImageResource(R.drawable.measure_value_high);
            pulseStateIV.setVisibility(View.VISIBLE);
        } else if (BluetoothUtil.getInstance().getPulseState(pul) == BluetoothUtil.STATE_LOW) {
            pulseIV.setImageResource(R.drawable.pulse_measure_high);
            pulseTV.setTextColor(getResources().getColor(R.color.color_999999));
            pulseStateIV.setImageResource(R.drawable.measure_value_low);
            pulseStateIV.setVisibility(View.VISIBLE);
        }
        cancelBtn.setVisibility(View.GONE);
        measureResultBtnRL.setVisibility(View.VISIBLE);
    }

    public void reset(){
        pulseTV.setText(R.string.rung);
        bloodpressureTV.setText(R.string.rung);
        pulseTV.setTextColor(getResources().getColor(R.color.color_616690));
        bloodpressureTV.setTextColor(getResources().getColor(R.color.color_616690));
        measureStateTV.setTextColor(getResources().getColor(R.color.color_616690));
        bloodpressureStateIV.setVisibility(View.GONE);
        bloodPressureOutIV.setVisibility(View.GONE);
        bloodPressureInIV.setVisibility(View.GONE);
        pulseStateIV.setVisibility(View.GONE);
        pulseIV.setImageResource(R.drawable.pulse_measure_bg);
        cancelBtn.setVisibility(View.VISIBLE);
        measureResultBtnRL.setVisibility(View.GONE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode != RESULT_OK) {
                    ToastUtil.showToast(R.string.text_open_bluetooth);
                    finish();
                }
                break;
        }
    }
}
