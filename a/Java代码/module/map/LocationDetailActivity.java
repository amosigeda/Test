package com.lewic.bracelet.module.map;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.lewic.bracelet.R;
import com.lewic.bracelet.base.BaseActivity;
import com.lewic.bracelet.constant.Params;
import com.lewic.bracelet.model.LocationModel;
import com.lewic.bracelet.util.DateUtil;

/**
 * Created on 2017/9/23.
 */

public class LocationDetailActivity extends BaseActivity{
    private MapView mMapView = null;
    private AMap aMap;
    private LocationModel location;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        location= (LocationModel) getIntent().getSerializableExtra(Params.location);
        if(location!=null){
            showGaodeMap(location);
            tv_time.setText(String.format(getString(R.string.text_sos_time), DateUtil.getStringDateMMDDHHmmss1FromMilli(location.getTimestamp())));
        }
    }

    @Override
    protected void loadXML() {
        setContentView(R.layout.acticity_location);
    }

    @Override
    protected void initSelfView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.mapView);
        tv_time= (TextView) findViewById(R.id.tv_time);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void doSelf() {

    }

    @Override
    public void onClickEvent(View view) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    private void showGaodeMap(LocationModel location){
        LatLng latLng = new LatLng(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLng()));
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.sos_marker_icon);
        aMap.addMarker(new MarkerOptions().position(latLng).icon(bitmap));
        //final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
        //将地图移动到定位点
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }
}
