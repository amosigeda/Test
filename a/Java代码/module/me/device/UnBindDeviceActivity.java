package com.lewic.bracelet.module.me.device;

import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lewic.bracelet.R;
import com.lewic.bracelet.base.MVPBaseActivity;
import com.lewic.bracelet.constant.Params;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.util.ToastUtil;

/**
 * Created on 2017/9/25.
 */

public class UnBindDeviceActivity extends MVPBaseActivity<UnBindDeviceInterface,UnBindDevicePresenter> implements UnBindDeviceInterface{
    private Button unBindBtn;
    private TextView tv_cancel;
    private String imei;
    @Override
    protected UnBindDevicePresenter createPresenter() {
        return new UnBindDevicePresenter();
    }

    @Override
    protected void loadXML() {
        setContentView(R.layout.activity_unbind_device);
    }

    @Override
    protected void initSelfView() {
        tv_cancel= (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_cancel.getPaint().setAntiAlias(true);//抗锯齿
        unBindBtn= (Button) findViewById(R.id.unBindBtn);
    }

    @Override
    protected void initListener() {
        unBindBtn.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {
        imei=this.getIntent().getStringExtra(Params.imei);
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.unBindBtn:
                //bindDeviceSuccess()
                //bindDeviceFailed(1,"ssss");
                showProgressDialog();
                mPresenter.unBindDevice(SharedPrefHelper.getInstance().getToken(),imei);
                break;
        }
    }

    @Override
    public void unBindDeviceSuccess() {
        dismissProgressDialog();
        ToastUtil.showToast(R.string.text_unbind_success);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void unBindDeviceFailed(int errorNo, String strMsg) {
        dismissProgressDialog();
        ToastUtil.showToast(strMsg);
    }
}
