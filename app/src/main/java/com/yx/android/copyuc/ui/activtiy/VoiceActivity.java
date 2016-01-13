package com.yx.android.copyuc.ui.activtiy;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.android.common.logging.Log;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.yx.android.copyuc.R;
import com.yx.android.copyuc.config.Constants;

import java.util.ArrayList;

/**
 * Created by yx on 2015/12/14 0014.
 */
public class VoiceActivity extends BaseActivity implements View.OnClickListener {
    private DialogRecognitionListener mRecognitionListener;
    private BaiduASRDigitalDialog mDialog = null;
    private ImageView mBack, mSpeek;

    @Override
    protected void initViewsAndEvents() {
        mBack = (ImageView) findViewById(R.id.iv_close);
        mSpeek = (ImageView) findViewById(R.id.iv_speek);

        setClickListener();


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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_voice;
    }

    private void setClickListener() {
        mBack.setOnClickListener(this);
        mSpeek.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_speek:
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
                mDialog = new BaiduASRDigitalDialog(this, params);
                mDialog.setDialogRecognitionListener(mRecognitionListener);
                mDialog.show();

                break;
        }

    }
}
