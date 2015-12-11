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

import com.baidu.android.common.logging.Log;
import com.baidu.location.BDLocation;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yx.android.copyuc.R;
import com.yx.android.copyuc.bean.WeatherInfoDto;
import com.yx.android.copyuc.config.Constants;
import com.yx.android.copyuc.manager.DriverLocationClient;
import com.yx.android.copyuc.protocol.GetTodayWeatherProtocol;
import com.yx.android.copyuc.ui.activtiy.MipcaActivityCapture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx on 2015/12/11 0011.
 */
public class MainFragment extends Fragment {
    private DriverLocationClient client;
    private ImageView mPic;
    private ImageLoader imageLoader;
    private TextView mArea, mPm, mSheShiDu, mStatu, mZhiL;
    private DialogRecognitionListener mRecognitionListener;
    private BaiduASRDigitalDialog mDialog = null;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private ImageView mShowDialog;

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

        mShowDialog = (ImageView) view.findViewById(R.id.iv_show_dialog);


        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }

                Bundle params = new Bundle();
                //设置开放平台 API Key
                params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, Constants.API_KEY);
                //设置开放平台 Secret Key
                params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, Constants.SECRET_KEY);
                //设置识别领域：搜索、输入、地图、音乐……，可选。默认为输入。
                params.putInt(BaiduASRDigitalDialog.PARAM_PROP, VoiceRecognitionConfig.PROP_INPUT);
                //设置语种类型：中文普通话，中文粤语，英文，可选。默认为中文普通话
                params.putString(BaiduASRDigitalDialog.PARAM_LANGUAGE, VoiceRecognitionConfig.LANGUAGE_CHINESE);
                //如果需要语义解析，设置下方参数。领域为输入不支持
                params.putBoolean(BaiduASRDigitalDialog.PARAM_NLU_ENABLE, true);
                // 设置对话框主题，可选。BaiduASRDigitalDialog 提供了蓝、暗、红、绿、橙四中颜色，每种颜色又分亮、暗两种色调。共 8 种主题，开发者可以按需选择，取值参考 BaiduASRDigitalDialog 中前缀为 THEME_的常量。默认为亮蓝色
                params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, Constants.Config.DIALOG_THEME);
                Log.e("DEBUG", "Config.PLAY_START_SOUND = " + Constants.Config.PLAY_START_SOUND);
                params.putBoolean(BaiduASRDigitalDialog.PARAM_START_TONE_ENABLE, Constants.Config.PLAY_START_SOUND);
                params.putBoolean(BaiduASRDigitalDialog.PARAM_END_TONE_ENABLE, Constants.Config.PLAY_END_SOUND);
                params.putBoolean(BaiduASRDigitalDialog.PARAM_TIPS_TONE_ENABLE, Constants.Config.DIALOG_TIPS_SOUND);
                mDialog = new BaiduASRDigitalDialog(getActivity(), params);
                mDialog.setDialogRecognitionListener(mRecognitionListener);
                mDialog.show();

            }
        });


        mRecognitionListener = new DialogRecognitionListener() {
            @Override
            public void onResults(Bundle bundle) {
                // 在Results中获取Key 为DialogRecognitionListener .RESULTS_RECOGNITION的StringArrayList，
                // 可能为空。获取到识别结果后执行相应的业务逻辑即可，此回调会在主线程调用。
                ArrayList<String> rs = bundle != null ? bundle
                        .getStringArrayList(RESULTS_RECOGNITION) : null;
                if (rs != null && rs.size() > 0) {
                    //此处处理识别结果，识别结果可能有多个，按置信度从高到低排列，第一个元素是置信度最高的结果。
                }
            }
        };


    }

    /**
     * 设置定位
     */
    private void setLocationCity() {
        //开始百度定位
        client = new DriverLocationClient() {
            @Override
            public void onLocationReceived(final BDLocation location) {
                String city = location.getCity();
                final String district = location.getDistrict();
                client.stop();//停止定位
                GetTodayWeatherProtocol protocol = new GetTodayWeatherProtocol(city) {

                    @Override
                    protected void onRefreshView(List<WeatherInfoDto.WeatherInfo> list) {
                        List<WeatherInfoDto.WeatherInfo.WeatherDataEntity> weatherDataEntityList = list.get(0).getWeather_data();
                        WeatherInfoDto.WeatherInfo.WeatherDataEntity info = weatherDataEntityList.get(0);
                        imageLoader.displayImage(info.getDayPictureUrl(), mPic);
                        mArea.setText(district);
                        mPm.setText(list.get(0).getPm25());
                        mSheShiDu.setText(info.getTemperature());
                        mZhiL.setText(info.getWeather());
                        mStatu.setText(info.getWind());

                    }
                };
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
