package com.lewic.bracelet.module.security;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lewic.bracelet.R;
import com.lewic.bracelet.base.BaseFragment;
import com.lewic.bracelet.base.MVPBaseFragment;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.model.SecurityFenceModel;
import com.lewic.bracelet.model.SecurityFenceRecord;
import com.lewic.bracelet.util.ToastUtil;

import java.util.List;

/**
 *
 * @Description
 * @date 2017/8/17 15:01
 */

public class SecurityFragment extends MVPBaseFragment<SecurityInterface, SecurityPresenter> implements SecurityInterface {

    private TextView goAwayRecordTV1, goAwayRecordTV2, goAwayRecordTV3, goAwayRecordTitleTV;
    private Button setBtn;
    private RelativeLayout viewRL;
    private SecurityFenceModel securityFenceModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_security, container, false);
    }

    @Override
    protected void initSelfView(View root) {
        goAwayRecordTV1 = (TextView) root.findViewById(R.id.goAwayRecordTV1);
        goAwayRecordTV2 = (TextView) root.findViewById(R.id.goAwayRecordTV2);
        goAwayRecordTV3 = (TextView) root.findViewById(R.id.goAwayRecordTV3);
        setBtn = (Button) root.findViewById(R.id.setBtn);
        viewRL = (RelativeLayout) root.findViewById(R.id.viewRL);
        goAwayRecordTitleTV = (TextView) root.findViewById(R.id.goAwayRecordTitleTV);
    }

    @Override
    protected void initListener() {
        viewRL.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {
        showProgressDialog();
        mPresenter.getSecurityFence(SharedPrefHelper.getInstance().getToken());
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.setBtn:
                break;
        }
    }

    @Override
    protected SecurityPresenter createPresenter() {
        return new SecurityPresenter();
    }

    @Override
    public void getSecurityFenceSuccess(SecurityFenceModel securityFenceModel) {
        if (securityFenceModel != null) {
            this.securityFenceModel=securityFenceModel;
            setBtn.setText(R.string.text_has_set);
            List<SecurityFenceRecord> record=securityFenceModel.getFencelog();
            if(record!=null&&record.size()>0){
                goAwayRecordTitleTV.setVisibility(View.VISIBLE);
                for(int i=0;i<record.size();i++){
                    if(i>2){
                        break;
                    }
                    SecurityFenceRecord securityFenceRecord=record.get(i);
                    if(securityFenceRecord!=null){
                        if(i==0){
                            goAwayRecordTV1.setText(securityFenceRecord.getContent());
                            goAwayRecordTV1.setVisibility(View.VISIBLE);
                        }else if(i==1){
                            goAwayRecordTV2.setText(securityFenceRecord.getContent());
                            goAwayRecordTV2.setVisibility(View.VISIBLE);
                        }else if(i==2){
                            goAwayRecordTV3.setText(securityFenceRecord.getContent());
                            goAwayRecordTV3.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }else{
                goAwayRecordTitleTV.setVisibility(View.GONE);
            }
        }else{
            setBtn.setText(R.string.text_no_set);
        }
        dismissProgressDialog();
    }

    @Override
    public void getSecurityFenceFailed(int errorNo, String strMsg) {
        dismissProgressDialog();
        ToastUtil.showToast(strMsg);
    }
}
