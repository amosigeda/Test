package com.lewic.bracelet.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lewic.bracelet.R;
import com.lewic.bracelet.base.BaseFragment;
import com.lewic.bracelet.module.me.device.DeviceManagerActivity;
import com.lewic.bracelet.module.me.white.WhiteActivity;

/**
 *
 * @Description
 * @date 2017/8/17 15:02
 */

public class MeFragment extends BaseFragment {

    private TextView tv_device_manager,tv_whitelist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    protected void initSelfView(View root) {
        tv_device_manager= (TextView) root.findViewById(R.id.tv_device_manager);
        tv_whitelist = (TextView)root.findViewById(R.id.tv_whitelist);
    }

    @Override
    protected void initListener() {
        tv_device_manager.setOnClickListener(this);
        tv_whitelist.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {

    }

    @Override
    public void onClickEvent(View view) {
        Intent intent=null;
        switch (view.getId()){
            case R.id.tv_device_manager:
                intent=new Intent();
                intent.setClass(this.getActivity(), DeviceManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_whitelist:
//                这里放whiteActivity
                intent=new Intent();
                intent.setClass(this.getActivity(), WhiteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
