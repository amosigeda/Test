package com.lewic.bracelet.module.data.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.lewic.bracelet.R;
import com.lewic.bracelet.base.BaseActivity;
import com.lewic.bracelet.constant.Params;
import com.lewic.bracelet.model.HomePageModel;
import com.lewic.bracelet.module.data.detail.bloodpressure.BloodPressureFragment;
import com.lewic.bracelet.module.data.detail.location.LocationFragment;
import com.lewic.bracelet.module.data.detail.pulse.PulseFragment;
import com.lewic.bracelet.widget.DataTopBar;

/**
 *
 * @Description
 * @date 2017/8/17 16:48
 */
public class DataDetailActivity extends BaseActivity{
    private PulseFragment pulseFragment;
    private BloodPressureFragment bloodPressureFragment;
    private LocationFragment locationFragment;
    private DataTopBar topBar;
    private Fragment currentFragment;
    private int index;

    @Override
    protected void loadXML() {
        setContentView(R.layout.activity_data_detail);
    }

    @Override
    protected void initSelfView() {
        topBar = (DataTopBar) findViewById(R.id.ll_top_bar);
        topBar.setOnItemChangedListener(new DataTopBar.OnItemChangedListener() {

            @Override
            public void onItemChanged(int index) {
                showDetails(index);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void doSelf() {
        index = getIntent().getIntExtra(Params.index, 0);
        topBar.setSelectedState(index);
    }

    @Override
    public void onClickEvent(View view) {

    }

    public void showDetails(int index) {
        switch (index) {
            case 0:
                if (pulseFragment == null) {
                    pulseFragment = new PulseFragment();
                }
                switchContent(currentFragment, pulseFragment);
                break;
            case 1:
                if (bloodPressureFragment == null) {
                    bloodPressureFragment = new BloodPressureFragment();
                }
                switchContent(currentFragment, bloodPressureFragment);
                break;
            case 2:
                if (locationFragment == null) {
                    locationFragment = new LocationFragment();
                }
                switchContent(currentFragment, locationFragment);
                break;
        }
    }

    public void switchContent(Fragment from, Fragment to) {
        if (to != currentFragment) {
            currentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out);
            if (!to.isAdded()) { // 先判断是否被add过
                if (from != null) {// 判断from是否为空，空的话直接add to
                    transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
                }
            } else {
                if (from != null) {// 判断from是否为空，空的话直接show to
                    transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
                } else {
                    transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个
                }
            }
        }
    }
}
