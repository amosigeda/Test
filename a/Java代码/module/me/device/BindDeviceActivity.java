package com.lewic.bracelet.module.me.device;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lewic.bracelet.R;
import com.lewic.bracelet.base.MVPBaseActivity;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.util.ToastUtil;

/**
 * Created on 2017/9/25.
 */

public class BindDeviceActivity extends MVPBaseActivity<BindDeviceInterface,BindDevicePresenter> implements BindDeviceInterface{
    private TextView tv_not_bind;
    private Button bindBtn;
    private EditText bindEdt;
    private TextView tv_welcome_use,tv_please_bind,tv_bind_step1,tv_bind_step2,tv_bind_step2_failed;
    private RelativeLayout rl_bind_success,rl_bind;
    private RelativeLayout rootRL;
    @Override
    protected BindDevicePresenter createPresenter() {
        return new BindDevicePresenter();
    }

    @Override
    protected void loadXML() {
        setContentView(R.layout.activity_bind_device);
    }

    @Override
    protected void initSelfView() {
        tv_not_bind= (TextView) findViewById(R.id.tv_not_bind);
        tv_not_bind.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_not_bind.getPaint().setAntiAlias(true);//抗锯齿
        bindBtn= (Button) findViewById(R.id.bindBtn);
        bindEdt= (EditText) findViewById(R.id.bindEdt);
        tv_please_bind= (TextView) findViewById(R.id.tv_please_bind);
        tv_bind_step2= (TextView) findViewById(R.id.tv_bind_step2);
        rl_bind_success= (RelativeLayout) findViewById(R.id.rl_bind_success);
        rl_bind= (RelativeLayout) findViewById(R.id.rl_bind);
        tv_welcome_use= (TextView) findViewById(R.id.tv_welcome_use);
        tv_bind_step1= (TextView) findViewById(R.id.tv_bind_step1);
        rootRL= (RelativeLayout) findViewById(R.id.rootRL);
        tv_bind_step2_failed= (TextView) findViewById(R.id.tv_bind_step2_failed);
    }

    @Override
    protected void initListener() {
        bindBtn.setOnClickListener(this);
        tv_not_bind.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {

    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()){
            case R.id.tv_not_bind:
                finish();
                break;
            case R.id.bindBtn:
                //bindDeviceSuccess();
                //bindDeviceFailed(1,"ssss");
                if(TextUtils.isEmpty(bindEdt.getText().toString())){
                    ToastUtil.showToast(R.string.text_please_input_imei);
                    return;
                }
                showProgressDialog();
                mPresenter.bindDevice(SharedPrefHelper.getInstance().getToken(),bindEdt.getText().toString());
                break;
        }
    }

    @Override
    public void bindDeviceSuccess() {
        rl_bind.setVisibility(View.GONE);
        rl_bind_success.setVisibility(View.VISIBLE);
        dismissProgressDialog();
    }

    @Override
    public void bindDeviceFailed(int errorNo, String strMsg) {
        tv_welcome_use.setVisibility(View.INVISIBLE);
        tv_bind_step1.setVisibility(View.INVISIBLE);
        tv_bind_step2.setVisibility(View.INVISIBLE);
        tv_bind_step2_failed.setVisibility(View.VISIBLE);
        tv_please_bind.setText(R.string.text_bind_failed);
        bindBtn.setText(R.string.text_sure);
        rootRL.setBackgroundResource(R.drawable.bg_high_list);
        dismissProgressDialog();
        ToastUtil.showToast(strMsg);
    }
}
