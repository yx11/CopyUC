package com.yx.android.copyuc.bean.params;

import android.util.Log;

import com.yx.android.copyuc.config.Constants;
import com.yx.android.copyuc.utils.GsonUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by yx on 2016/1/11 0011.
 */
public class WeartherParams extends BaseParams{
    private String city;
    public WeartherParams(String city) {
        try {
            this.city = java.net.URLEncoder.encode(city, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getMapParams() {
        params.put("location",city);
        params.put("output", Constants.Config.JSON);
        params.put("mcode",Constants.Config.MCODE);
        params.put("ak", Constants.Config.AK);
        Log.d("请求参数", GsonUtils.toJson(params));
        return params;
    }
}
