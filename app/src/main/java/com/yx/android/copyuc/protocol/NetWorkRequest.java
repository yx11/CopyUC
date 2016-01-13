package com.yx.android.copyuc.protocol;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yx.android.copyuc.bean.params.BaseParams;

import java.util.Map;

/**
 * Created by yx on 2016/1/12 0012.
 */
public class NetWorkRequest extends BaseRequest {
    private BaseParams mParams;
    private String mUrl;

    public NetWorkRequest(String url, BaseParams params, Class clz, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, null, clz, listener, errorListener);
        this.mParams = params;
        this.mUrl = url;
        this.setRetryPolicy(
                new DefaultRetryPolicy(
                        5000,//默认超时时间
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
    }


    public NetWorkRequest(String url, Class clz, Response.Listener listener, Response.ErrorListener errorListener) {
        super(url, null, clz, listener, errorListener);
        this.mUrl = url;
        this.setRetryPolicy(
                new DefaultRetryPolicy(
                        5000,//默认超时时间
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams == null ? null : mParams.getMapParams();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }


    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return super.getBody();
    }
}
