package com.yx.android.copyuc.protocol;

import com.lidroid.xutils.http.RequestParams;
import com.yx.android.copyuc.bean.WeatherInfoDto;
import com.yx.android.copyuc.config.Constants;
import com.yx.android.copyuc.config.UrlConstant;
import com.yx.android.copyuc.utils.GsonUtils;
import com.yx.android.copyuc.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by yx on 2015/12/9 0009.
 */
public abstract class GetTodayWeatherProtocol extends BaseProtocol {
    private RequestParams params;

    public GetTodayWeatherProtocol(String cityCode) {
        params = new RequestParams();
        try {
            String city = java.net.URLEncoder.encode(cityCode, "UTF-8");
//            params.addBodyParameter("?location=", city);
//            params.addBodyParameter("&output=", Constants.Config.JSON);
//            params.addBodyParameter("&mcode=", Constants.Config.MCODE);
//            params.addBodyParameter("&ak=", Constants.Config.AK);
//            request(UrlConstant.GET_WEATHER, params);
            String url = UrlConstant.GET_WEATHER + "?location=" + city + "&output=" + Constants.Config.JSON + "&output=json"
                    + "&mcode=" + Constants.Config.MCODE + "&ak=" + Constants.Config.AK;
            request(url, params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void requestSuccess(String result) {
        LogUtils.e("获取天气数据返回结果"+result);
        WeatherInfoDto weatherInfoDto = GsonUtils.changeJsonToBean(result, WeatherInfoDto.class);
        List<WeatherInfoDto.WeatherInfo> list=new ArrayList<>();
        list=weatherInfoDto.getResults();



//        Toast.makeText(BaseActivity.getForegroundActivity(), result, Toast.LENGTH_LONG).show();
        onRefreshView(list);

    }

    protected abstract void onRefreshView(List<WeatherInfoDto.WeatherInfo> list);
}
