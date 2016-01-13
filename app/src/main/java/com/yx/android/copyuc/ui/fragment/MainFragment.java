package com.yx.android.copyuc.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yx.android.copyuc.R;
import com.yx.android.copyuc.bean.WeatherInfoDto;
import com.yx.android.copyuc.config.Constants;
import com.yx.android.copyuc.config.UrlConstant;
import com.yx.android.copyuc.manager.DriverLocationClient;
import com.yx.android.copyuc.protocol.NetWorkRequest;
import com.yx.android.copyuc.ui.activtiy.MipcaActivityCapture;
import com.yx.android.copyuc.ui.activtiy.VoiceActivity;
import com.yx.android.copyuc.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by yx on 2015/12/11 0011.
 */
public class MainFragment extends Fragment {
    private DriverLocationClient client;
    private ImageView mPic;
    private ImageLoader imageLoader;
    private TextView mArea, mPm, mSheShiDu, mStatu, mZhiL;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private ImageView mSpeek;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        imageLoader = ImageLoader.getInstance();
        mPic = (ImageView) view.findViewById(R.id.iv_pic);
        mArea = (TextView) view.findViewById(R.id.tv_area);
        mPm = (TextView) view.findViewById(R.id.tv_kong_qi);
        mZhiL = (TextView) view.findViewById(R.id.tv_kong_qi_zhi);
        mSheShiDu = (TextView) view.findViewById(R.id.tv_she_shi_du);
        mStatu = (TextView) view.findViewById(R.id.tv_weather);
        setLocationCity();//定位城市

        ImageView mSweep = (ImageView) view.findViewById(R.id.iv_sweep);
        mSweep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MipcaActivityCapture.class);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });

        mSpeek = (ImageView) view.findViewById(R.id.iv_speek);


        mSpeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VoiceActivity.class));
            }
        });


    }

    /**
     * 设置定位
     */
    private void setLocationCity() {
        //开始百度定位
        client = new DriverLocationClient() {
            @Override
            public void onLocationReceived(final BDLocation location) {
                final String city = location.getCity();
                final String district = location.getDistrict();
                if (city.equals("")) {
                    return;
                }
                client.stop();//停止定位
                String city1 = null;
                try {
                    city1 = java.net.URLEncoder.encode(city, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url = UrlConstant.GET_WEATHER + "?location=" + city1 + "&output=" + Constants.Config.JSON
                        + "&mcode=" + Constants.Config.MCODE + "&ak=" + Constants.Config.AK;
                NetWorkRequest request = new NetWorkRequest(url, null, WeatherInfoDto.class, new Response.Listener<WeatherInfoDto>() {
                    @Override
                    public void onResponse(WeatherInfoDto response) {
                        if (response.getError() == 0) {
                            LogUtils.e("获取天气数据返回结果" + response.toString());
                            List<WeatherInfoDto.WeatherInfo> list = response.getResults();
                            List<WeatherInfoDto.WeatherInfo.WeatherDataEntity> weatherDataEntityList = list.get(0).getWeather_data();
                            WeatherInfoDto.WeatherInfo.WeatherDataEntity info = weatherDataEntityList.get(0);
                            mArea.setText(district);
                            mPm.setVisibility(View.VISIBLE);
                            mPm.setText(list.get(0).getPm25());
                            mSheShiDu.setText(info.getDate().substring(info.getDate().length() - 4, info.getDate().length()).substring(0, 2) + "°");
                            mStatu.setText(info.getWeather());
                            if (info.getDayPictureUrl().contains("duoyun")) {
                                Glide.with(getActivity()).load(info.getDayPictureUrl()).error(R.mipmap.notification_weather_cloudy_small).into(mPic);
                            }
                            mZhiL.setText(info.getWind());
                        }
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(getActivity()).add(request);
            }
        };
        client.init(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == getActivity().RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    //显示
                    Bitmap bitmap = (Bitmap) data.getParcelableExtra("bitmap");
                }
                break;
        }
    }
}
