package com.yx.android.copyuc.protocol;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.yx.android.copyuc.ui.activtiy.BaseActivity;

import java.io.IOException;

/**
 * @ClassName: BaseProtocol
 * @Description: 所有网络请求的基类
 * @author: yx
 * Created by yx on 2015/12/9 0009.
 */
public abstract class BaseProtocol {
    private boolean isRequestSync = false;
    private BaseActivity activity = BaseActivity.getForegroundActivity();

    /**
     * @param url
     * @param params void
     * @Title: requestSync
     * @Description: 同步请求服务器
     */
    public void requestSync(String url, RequestParams params) {
        isRequestSync = true;
        loadFromNet(url, params);
    }

    /**
     * @param url    要请求的url地址
     * @param params 请求的参数
     * @Title: request
     * @Description: 异步请求服务器
     */
    public void request(String url, RequestParams params) {
        loadFromNet(url, params);
    }

    private void loadFromNet(String url, RequestParams params) {
        HttpUtils httpUtils = new HttpUtils(10 * 1000);//设置超时

        if (isRequestSync) {
            try {
                ResponseStream sendSync = httpUtils.sendSync(HttpRequest.HttpMethod.POST, url, params);
                if (sendSync.getStatusCode() == 200) {
                    requestSuccess(sendSync.readString());
                }
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("BaseProtocol", e.getMessage());
            }

        } else {
            httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    requestSuccess(responseInfo.result);
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    // 请求失败
                    requestError(error, msg);
                }

                @Override
                public void onStart() {
                    // 请求开始
                    LogUtils.d("请求开始");
                }
            });
        }

    }


    /**
     * @param error
     * @param msg   void
     * @Title: requestError
     * @Description: 请求失败，一般作默认处理，需要单独处理时复写此方法
     */
    protected void requestError(HttpException error, String msg) {
        error.printStackTrace();
        LogUtils.d("错误信息：" + error.getMessage() + "||" + msg + "， 错误代码：" + error.getExceptionCode());
        switch (error.getExceptionCode()) {
            case 0://网络异常（连接超时、响应超时）
                if (msg.contains("SocketTimeoutException")) {
//                    Toast.makeText(activity, "服务器响应超时", Toast.LENGTH_SHORT).show();
                } else if (msg.contains("ConnectTimeoutException")) {
//                    Toast.makeText(activity, "服务器连接超时", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(activity, "网络异常", Toast.LENGTH_SHORT).show();
                }
                break;
            case 500:
//                Toast.makeText(activity, "服务器异常", Toast.LENGTH_SHORT).show();
                break;
            case 400:
//                Toast.makeText(activity, "参数错误", Toast.LENGTH_SHORT).show();
                break;
            case 403:
//                Toast.makeText(activity, "访问被拒绝", Toast.LENGTH_SHORT).show();
                break;
            case 404:
//                Toast.makeText(activity, "访问的地址不存在", Toast.LENGTH_SHORT).show();
                break;
            case 406:
//                Toast.makeText(activity, "服务无法接受", Toast.LENGTH_SHORT).show();
                break;
            default:
//                Toast.makeText(activity, "未知异常", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @param result &#x8bf7;&#x6c42;&#x6210;&#x529f;&#x8fd4;&#x56de;&#x7684;json&#x4e32;
     * @Title: requestSuccess
     * @Description: &#x8bf7;&#x6c42;&#x6210;&#x529f;
     */
    protected abstract void requestSuccess(String result);


}
