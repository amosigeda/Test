package com.lewic.bracelet.module.me.device;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lewic.bracelet.R;
import com.lewic.bracelet.base.MVPBaseActivity;
import com.lewic.bracelet.constant.Params;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.model.DeviceModel;
import com.lewic.bracelet.util.DateUtil;
import com.lewic.bracelet.util.ToastUtil;

/**
 * Created on 2017/9/24.
 */

public class DeviceManagerActivity extends MVPBaseActivity<DeviceManagerInterface,DeviceManagerPresenter> implements DeviceManagerInterface{
    private RelativeLayout deviceRL;
    private TextView tv_device_name;
    private TextView tv_device_time;
    private TextView tv_device_state;
    private ImageView iv_device_icon;
    private TextView bindTV;
    private DeviceModel deviceModel;
    private final static int code_unbind=1110;

    @Override
    protected DeviceManagerPresenter createPresenter() {
        return new DeviceManagerPresenter();
    }

    @Override
    protected void loadXML() {
        setContentView(R.layout.activity_device_manager);
    }

    @Override
    protected void initSelfView() {
        deviceRL= (RelativeLayout) findViewById(R.id.deviceRL);
        tv_device_name= (TextView) findViewById(R.id.tv_device_name);
        tv_device_time= (TextView) findViewById(R.id.tv_device_time);
        tv_device_state= (TextView) findViewById(R.id.tv_device_state);
        iv_device_icon= (ImageView) findViewById(R.id.iv_device_icon);
        bindTV= (TextView) findViewById(R.id.bindTV);
    }

    @Override
    protected void initListener() {
        bindTV.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {
        showProgressDialog();
        mPresenter.getDevice(SharedPrefHelper.getInstance().getToken());
    }

    @Override
    public void onClickEvent(View view) {
        Intent intent=null;
        switch (view.getId()){
            case R.id.bindTV:
                if(deviceModel!=null){
                    intent=new Intent();
                    intent.setClass(this,UnBindDeviceActivity.class);
                    intent.putExtra(Params.imei,deviceModel.getImei());
                    startActivityForResult(intent,code_unbind);
                }else{
                    intent=new Intent();
                    intent.setClass(this,BindDeviceActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case code_unbind:
                if(resultCode==RESULT_OK){
                    deviceModel=null;
                    showProgressDialog();
                    mPresenter.getDevice(SharedPrefHelper.getInstance().getToken());
                }
                break;
        }
    }

    @Override
    public void getDeviceSuccess(DeviceModel deviceModel) {
        if(deviceModel!=null&&!TextUtils.isEmpty(deviceModel.getImei())){
            this.deviceModel=deviceModel;
            tv_device_name.setText(String.format(getString(R.string.text_device_name),deviceModel.getImei()));
            tv_device_time.setText(String.format(getString(R.string.text_device_time), DateUtil.getyyyyMMddHHmmStringDateFromMilli(deviceModel.getBindingtime())));
            tv_device_state.setText(String.format(getString(R.string.text_device_state),getString(R.string.text_normal)));
            iv_device_icon.setVisibility(View.VISIBLE);
            bindTV.setText(R.string.text_unbind);
            bindTV.setBackgroundResource(R.drawable.button_unbind);
            bindTV.setTextColor(getResources().getColor(R.color.color_ffffff));
        }else{
            tv_device_name.setText(String.format(getString(R.string.text_device_name),""));
            tv_device_time.setText(String.format(getString(R.string.text_device_time), "－－"));
            tv_device_state.setText(String.format(getString(R.string.text_device_state),getString(R.string.text_device_unbounded)));
            iv_device_icon.setVisibility(View.GONE);
            bindTV.setText(R.string.text_bind);
            bindTV.setBackgroundResource(R.drawable.button_bind);
            bindTV.setTextColor(getResources().getColor(R.color.color_1f82d0));
        }
        deviceRL.setVisibility(View.VISIBLE);
        dismissProgressDialog();
    }

    @Override
    public void getDeviceFailed(int errorNo, String strMsg) {
        dismissProgressDialog();
        ToastUtil.showToast(strMsg);
//        tv_device_name.setText(String.format(getString(R.string.text_device_name),""));
//        tv_device_time.setText(String.format(getString(R.string.text_device_time), "－－"));
//        tv_device_state.setText(String.format(getString(R.string.text_device_state),getString(R.string.text_device_unbounded)));
//        iv_device_icon.setVisibility(View.GONE);
//        bindTV.setText(R.string.text_bind);
//        bindTV.setBackgroundResource(R.drawable.button_bind);
//        bindTV.setTextColor(getResources().getColor(R.color.color_1f82d0));
        deviceRL.setVisibility(View.GONE);
        Log.w("ATG","errorNo:"+errorNo);
        Log.w("ATG","strMsg:"+strMsg);
    }
}
