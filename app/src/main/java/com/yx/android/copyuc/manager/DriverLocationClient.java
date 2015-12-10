package com.yx.android.copyuc.manager;


import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * @ClassName:  DriverLocationClient
 * @Description: 百度定位
 * @author: Haoxl
 * @date:   2015-4-29 下午4:03:39
 */
public class DriverLocationClient {
	private LocationClient mLocationClient = null;
	private BDLocationListener mLocationListener = new LocationListener();

    private Context mContext;



    public void init(Context con) {
        mContext = con;
//        if (mLocationClient != null) {
//            stop();
//            return;
//        }
        mLocationClient = new LocationClient(con);// 声明LocationClient类
        start();
    }

    public void start() {
        if (mLocationClient == null) {
            return;
        }
        setOption();
        mLocationClient.registerLocationListener(mLocationListener); // 注册监听函数
        mLocationClient.start();// 开启监听
    }

    /**
     * 先设置间隔扫描时间小于1000, 定位模式自动变为只定位一次.
     */
    public void stop() {
        if (mLocationClient == null) {
            return;
        }
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationMode.Device_Sensors);// 设置定位模式
//        option.setCoorType("gcj02");// 返回的定位结果是百度经纬度,默认值gcj02
//        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
//        option.setProdName(mContext.getPackageName());
//        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
//        mLocationClient.setLocOption(option);
//        mLocationClient.requestLocation();
        if (mLocationClient.isStarted()) {
//          return;
          mLocationClient.stop();
          mLocationClient.setLocOption(null);
          mLocationClient.unRegisterLocationListener(mLocationListener);
          mLocationClient = null;
//          LogUtils.d("location stop");
      }
    }

    // 设置定位参数
    public void setOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("gcj02");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5 * 60 * 1000);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setProdName(mContext.getPackageName());
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);

    }

    class LocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
//            LogUtils.d(location.getLocType() + ", " + location.getLatitude() + ", " + location.getLongitude()
//                            + ", " + location.getProvince() + ", " + location.getCity() + ", " + location.getAddrStr());
            if (BDLocation.TypeGpsLocation == location.getLocType() || BDLocation.TypeNetWorkLocation == location.getLocType()) {
            }
            onLocationReceived(location);
        }
    }

    public void onLocationReceived(BDLocation location) {
    }
}
