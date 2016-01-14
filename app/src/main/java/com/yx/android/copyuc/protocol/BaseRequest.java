package com.yx.android.copyuc.protocol;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by yx on 2016/1/12 0012.
 */
public class BaseRequest<T> extends Request<T> {
    private final Response.Listener<T> mListener;
    private Gson mGson;
    private Class mClz;
    private final String mRequestBody;

    public BaseRequest(int method, String url, String requestBody, Class clz, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mGson = null;
        this.mClz = null;
        this.mClz = clz;
        this.mListener = listener;
        this.mRequestBody = requestBody;
        this.mGson = new Gson();
    }

    public BaseRequest(String url, String requestBody, Class clz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(0, url, requestBody, (Class) clz, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String e = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            T t = (T) this.mGson.fromJson(e, this.mClz);
            return Response.success(t, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(T t) {
        this.mListener.onResponse(t);
    }


}
