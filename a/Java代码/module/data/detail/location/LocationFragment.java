package com.lewic.bracelet.module.data.detail.location;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.lewic.bracelet.R;
import com.lewic.bracelet.base.MVPBaseFragment;
import com.lewic.bracelet.data.spfs.SharedPrefHelper;
import com.lewic.bracelet.model.LocationModel;
import com.lewic.bracelet.util.DateUtil;
import com.lewic.bracelet.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Description
 * @date 2017/8/17 15:02
 */

public class LocationFragment extends MVPBaseFragment<LocationInterface, LocationPresenter> implements LocationInterface {

    //private MapView mMapView = null;
    //private BaiduMap mBaiduMap;
    private MapView mMapView = null;
    private AMap aMap;
    private Button realtimeLocationBtn,oneHourHistoryLocationBtn,oneDayHistoryLocationBtn;
    private View currentView;
    private ImageView refreshIV;
    private final static int getCurrentLocationCode=1110;
    private long startTime;
    private boolean getLocationing;
    private final static String TAG="LocationFragment";
    private Marker marker;
    private Polyline oneHourPolyline;
    private Polyline oneDayPolyline;
    private LinearLayout timeLL;
    private TextView timeTV,stepCountTV;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case getCurrentLocationCode:
                    mPresenter.getCurrentLocation(SharedPrefHelper.getInstance().getToken());
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_location, container, false);
        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.mapView);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        return view;
    }

    @Override
    protected void initSelfView(View root) {
        //获取地图控件引用
        //mMapView = (MapView) root.findViewById(R.id.bmapView);
        //mBaiduMap = mMapView.getMap();

        realtimeLocationBtn= (Button) root.findViewById(R.id.realtimeLocationBtn);
        oneHourHistoryLocationBtn= (Button) root.findViewById(R.id.oneHourHistoryLocationBtn);
        oneDayHistoryLocationBtn= (Button) root.findViewById(R.id.oneDayHistoryLocationBtn);
        refreshIV= (ImageView) root.findViewById(R.id.refreshIV);
        timeLL= (LinearLayout) root.findViewById(R.id.timeLL);
        timeTV= (TextView) root.findViewById(R.id.timeTV);
        stepCountTV= (TextView) root.findViewById(R.id.stepCountTV);
    }

    @Override
    protected void initListener() {
        realtimeLocationBtn.setOnClickListener(this);
        oneHourHistoryLocationBtn.setOnClickListener(this);
        oneDayHistoryLocationBtn.setOnClickListener(this);
        refreshIV.setOnClickListener(this);
    }

    @Override
    protected void doSelf() {
        showProgressDialog();
        getLocationing=true;
        mPresenter.locationRequest(SharedPrefHelper.getInstance().getToken());
        currentView=realtimeLocationBtn;
        currentView.setSelected(true);
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.realtimeLocationBtn:
                if(currentView!=null){
                    if(currentView==realtimeLocationBtn){
                        return;
                    }
                    currentView.setSelected(false);
                }
                refreshIV.setVisibility(View.VISIBLE);
                timeLL.setVisibility(View.GONE);
                if(oneDayPolyline!=null){
                    oneDayPolyline.remove();
                }
                if (oneHourPolyline!=null){
                    oneHourPolyline.remove();
                }
                currentView=realtimeLocationBtn;
                currentView.setSelected(true);
                if(getLocationing){
                    ToastUtil.showToast(R.string.text_locationing);
                    return;
                }
                getLocationing=true;
                showProgressDialog();
                mPresenter.locationRequest(SharedPrefHelper.getInstance().getToken());
                break;
            case R.id.oneHourHistoryLocationBtn:
                if(currentView!=null){
                    if(currentView==oneHourHistoryLocationBtn){
                        return;
                    }
                    currentView.setSelected(false);
                }
                refreshIV.setVisibility(View.GONE);
                timeLL.setVisibility(View.GONE);
                if(marker!=null){
                    marker.remove();
                }
                if (oneDayPolyline!=null){
                    oneDayPolyline.remove();
                }
                currentView=oneHourHistoryLocationBtn;
                currentView.setSelected(true);
                showProgressDialog();
                mPresenter.getTrack(SharedPrefHelper.getInstance().getToken(),1);
                break;
            case R.id.oneDayHistoryLocationBtn:
                if(currentView!=null){
                    if(currentView==oneDayHistoryLocationBtn){
                        return;
                    }
                    currentView.setSelected(false);
                }
                refreshIV.setVisibility(View.GONE);
                timeLL.setVisibility(View.GONE);
                if(marker!=null){
                    marker.remove();
                }
                if (oneHourPolyline!=null){
                    oneHourPolyline.remove();
                }
                currentView=oneDayHistoryLocationBtn;
                currentView.setSelected(true);
                showProgressDialog();
                mPresenter.getTrack(SharedPrefHelper.getInstance().getToken(),2);
                break;
            case R.id.refreshIV:
                if(getLocationing){
                    ToastUtil.showToast(R.string.text_locationing);
                    return;
                }
                if(marker!=null){
                    marker.remove();
                }
                getLocationing=true;
                showProgressDialog();
                mPresenter.locationRequest(SharedPrefHelper.getInstance().getToken());
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        //mMapView.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mMapView.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected LocationPresenter createPresenter() {
        return new LocationPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        //mMapView.onResume();
        mMapView.onResume();
    }

    @Override
    public void getLatestLocationSuccess(LocationModel location) {
        if(location!=null){
            if (!TextUtils.isEmpty(location.getLat()) && !TextUtils.isEmpty(location.getLng())) {
                //showBaiduMap(location);
                showGaodeMap(location);
            }
        }
        dismissProgressDialog();
        getLocationing=false;
    }

    @Override
    public void getLatestLocationFailed(int errorNo, String strMsg) {
        ToastUtil.showToast(strMsg);
        dismissProgressDialog();
        getLocationing=false;
    }

    @Override
    public void locationRequestSuccess() {
        handler.removeMessages(getCurrentLocationCode);
        startTime= SystemClock.elapsedRealtime();
        handler.sendEmptyMessageDelayed(getCurrentLocationCode,100);
    }

    @Override
    public void locationRequestFailed(int errorNo, String strMsg) {
        //ToastUtil.showToast(strMsg);
        mPresenter.getLatestLocation(SharedPrefHelper.getInstance().getToken());
    }

    @Override
    public void getCurrentLocationSuccess(LocationModel location) {
        if(this.getActivity()==null||this.getActivity().isFinishing()){
            return;
        }
        if(location!=null){
            if (!TextUtils.isEmpty(location.getLat()) && !TextUtils.isEmpty(location.getLng())) {
                //showBaiduMap(location);
                showGaodeMap(location);
            }
            getLocationing=false;
            dismissProgressDialog();
        }else{
            if(SystemClock.elapsedRealtime()-startTime<3*1000){
                handler.sendEmptyMessageDelayed(getCurrentLocationCode,100);
            }else{
                mPresenter.getLatestLocation(SharedPrefHelper.getInstance().getToken());
            }
        }
    }

    @Override
    public void getCurrentLocationFailed(int errorNo, String strMsg) {
        //ToastUtil.showToast(strMsg);
        mPresenter.getLatestLocation(SharedPrefHelper.getInstance().getToken());
    }

    @Override
    public void getTrackSuccess(List<LocationModel> locationModels,int type) {
        if(locationModels!=null&&locationModels.size()>0){
            drawTrack(locationModels,type);
            LocationModel model=locationModels.get(0);
            LocationModel model1=locationModels.get(locationModels.size()-1);
            String startTime="";
            String endTime="";
            if(model!=null){
                startTime= DateUtil.getStringDateMMddHHmm1FromMilli(model.getTimestamp());
            }
            if(model1!=null){
                endTime= DateUtil.getHHmmStringDateFromMilli(model1.getTimestamp());
            }
            timeTV.setText(String.format(getString(R.string.text_location_time),startTime,endTime));
            stepCountTV.setText(String.format(getString(R.string.text_step_count),"23004"));
            timeLL.setVisibility(View.VISIBLE);
        }
        dismissProgressDialog();
    }

    @Override
    public void getTrackFailed(int errorNo, String strMsg) {
        ToastUtil.showToast(strMsg);
        dismissProgressDialog();
    }

    /*private void showBaiduMap(LocationModel location){
        //定义Maker坐标点
        LatLng point = new LatLng(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLng()));

        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
        // 移动到某经纬度
        mBaiduMap.animateMapStatus(update);
        //update = MapStatusUpdateFactory.zoomBy(5f);
        // 放大
        //mBaiduMap.animateMapStatus(update);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }*/

    private void showGaodeMap(LocationModel location){
        LatLng latLng = new LatLng(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLng()));
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        marker=aMap.addMarker(new MarkerOptions().position(latLng).icon(bitmap));
        //final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
        //将地图移动到定位点
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }

    private void drawTrack(List<LocationModel> locationModels,int type) {
        List<LatLng> latLngs = new ArrayList<LatLng>();
        for (LocationModel locationModel:locationModels){
            latLngs.add(new LatLng(Double.parseDouble(locationModel.getLat()),Double.parseDouble(locationModel.getLng())));
        }
        // 绘制一个大地曲线
        if(type==1){
            oneHourPolyline=aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(getResources().getColor(R.color.color_1f82d0)));
        }else if(type==2){
            oneDayPolyline=aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.GREEN));
        }
    }
}
