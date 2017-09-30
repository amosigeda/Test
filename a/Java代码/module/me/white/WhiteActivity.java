package com.lewic.bracelet.module.me.white;

import android.app.AlertDialog;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lewic.bracelet.R;
import com.lewic.bracelet.adapter.SecurityAdapter;
import com.lewic.bracelet.base.MVPBaseActivity;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.model.SelectWhiteModel;
import com.lewic.bracelet.module.me.device.BindDevicePresenter;
import com.lewic.bracelet.network.API;
import com.lewic.bracelet.util.ToastUtil;
import com.lewic.bracelet.widget.QQListView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/29.
 */

public class WhiteActivity extends MVPBaseActivity<WhiteInterface,WhitePresenter> implements WhiteInterface{

    private QQListView lvWhite;
    ImageView ivAdd,iv_back;
    private AlertDialog dialog;
    private List<String> mDatas;
    private ArrayAdapter<String> mAdapter;
    private List<SelectWhiteModel.Data> datas;
    SecurityAdapter adapter;
    @Override
    protected WhitePresenter createPresenter() {
        return new WhitePresenter();
    }

    @Override
    protected void loadXML() {
        setContentView(R.layout.activity_white);
    }

    @Override
    protected void initSelfView() {
        lvWhite= (QQListView) findViewById(R.id.lv_white);
        ivAdd = (ImageView)findViewById(R.id.iv_add);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        lvWhite.setDelButtonClickListener(new QQListView.DelButtonClickListener() {
            @Override
            public void clickHappend(final int position) {
                /**
                 * 删除白名单信息
                 */
                int id=datas.get(position).id;
                mPresenter.delWhite(SharedPrefHelper.getInstance().getToken(),id);
            }
        });

    }

    @Override
    protected void initListener() {
        ivAdd.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {
        Toast.makeText(getApplicationContext(),""+SharedPrefHelper.getInstance().getToken(),Toast.LENGTH_LONG).show();;
        mPresenter.selectWhite(SharedPrefHelper.getInstance().getToken());
    }

    EditText etPhone;
    EditText etName;
    @Override
    public void onClickEvent(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_add:
                LayoutInflater inflater =getLayoutInflater();
                View views = inflater.inflate(R.layout.dialog_white,null);
                etName = (EditText) views.findViewById(R.id.et_Name);
                etPhone = (EditText) views.findViewById(R.id.et_Phone);
                Button btnComplete = (Button) views.findViewById(R.id.btn_Complete);
                btnComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /**
                         * 增加白名单信息
                         */
                        String Name = etName.getText().toString().trim();
                        String Phone = etPhone.getText().toString().trim();
                        if (TextUtils.isEmpty(Name)){
                            ToastUtil.showToast("名字不能为空");
                            return;
                        }else if(TextUtils.isEmpty(Phone)){
                            ToastUtil.showToast("手机号不能为空");
                            return;
                        }else{
                            mPresenter.addWhite(SharedPrefHelper.getInstance().getToken(),Phone,Name);
                            dialog.dismiss();
                        }
                    }
                });
                dialog = new AlertDialog.Builder(this).create();
                dialog.setCancelable(true);
                dialog.setView(views);
                dialog.show();
                break;
        }
    }

    @Override
    public void addWhiteSuccess() {
        ToastUtil.showToast("添加成功");
        mPresenter.selectWhite(SharedPrefHelper.getInstance().getToken());
        dialog.dismiss();
    }

    @Override
    public void addWhiteFailed(int errorNo, String strMsg) {
        dismissProgressDialog();
        dialog.dismiss();
    }

    @Override
    public void delWhiteSuccess() {
        ToastUtil.showToast("删除成功");
        mPresenter.selectWhite(SharedPrefHelper.getInstance().getToken());
    }

    @Override
    public void delWhiteFailed(int errorNo, String strMsg) {
        dismissProgressDialog();
    }

    @Override
    public void selectWhiteSuccess(SelectWhiteModel selectWhiteModel) {
    }

    @Override
    public void selectWhiteFailed(int errorNo, String strMsg) {
    }
}
