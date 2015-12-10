package com.yx.android.copyuc.ui.activtiy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.android.common.logging.Log;
import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.yx.android.copyuc.R;
import com.yx.android.copyuc.config.Constants;
import com.yx.android.copyuc.ui.adapter.GradViewAdapter;
import com.yx.android.copyuc.ui.fragment.ControlPanelFragment;
import com.yx.android.copyuc.ui.widget.MyGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private DialogRecognitionListener mRecognitionListener;
    private BaiduASRDigitalDialog mDialog = null;
    private int mCurrentTheme = Constants.Config.DIALOG_THEME;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private VoiceRecognitionClient mASREngine;
    private ControlPanelFragment mControlPanel;

    /**
     * 正在识别中
     */
    private boolean isRecognition = false;

    /**
     * 音量更新间隔
     */
    private static final int POWER_UPDATE_INTERVAL = 100;

    /**
     * 识别回调接口
     */
//    private MyVoiceRecogListener mListener = new MyVoiceRecogListener();

    /**
     * 主线程Handler
     */
    private Handler mHandler;


    /**
     * 显示扫描结果
     */
    private TextView mTextView;
    /**
     * 显示扫描拍的图片
     */
    private ImageView mImageView, mShowDialog;

    private List<Map<String, Object>> lists;
    private SimpleAdapter sim_adapter;
    private MyGridView mGradView;
    private String[] iconName = {"通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
            "设置", "语音", "天气", "浏览器", "视频"};
    // 图片封装为一个数组
    private int[] icon = {android.R.drawable.ic_menu_my_calendar, android.R.drawable.ic_menu_my_calendar,
            android.R.drawable.ic_menu_camera, android.R.drawable.ic_lock_lock, android.R.drawable.ic_dialog_alert,
            android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_camera};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageView mSweep = (ImageView) findViewById(R.id.iv_sweep);
        mSweep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MipcaActivityCapture.class);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });

        mTextView = (TextView) findViewById(R.id.result);
        mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
        mShowDialog = (ImageView) findViewById(R.id.iv_show_dialog);


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
                mDialog = new BaiduASRDigitalDialog(MainActivity.this, params);
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
                mTextView.setText(rs.toString());
                if (rs != null && rs.size() > 0) {
                    //此处处理识别结果，识别结果可能有多个，按置信度从高到低排列，第一个元素是置信度最高的结果。
                    mTextView.setText(rs.get(0));
                }
            }
        };


//        语音识别API定制自己的语音交互
//        mASREngine = VoiceRecognitionClient.getInstance(this);
//        mASREngine.setTokenApis(Constants.API_KEY, Constants.SECRET_KEY);
//        mHandler = new Handler();
//        mControlPanel = (ControlPanelFragment) (getSupportFragmentManager()
//                .findFragmentById(R.id.control_panel));
//
//        mControlPanel.setOnEventListener(new ControlPanelFragment.OnEventListener() {
//            @Override
//            public boolean onStartListening() {
//                mTextView.setText(null);
//                VoiceRecognitionConfig config = new VoiceRecognitionConfig();
//                config.setProp(Config.CURRENT_PROP);
//                config.setLanguage(Config.getCurrentLanguage());
//                config.enableVoicePower(Config.SHOW_VOL);// 音量反馈
//                if (Config.PLAY_START_SOUND) {
//                    config.enableBeginSoundEffect(R.raw.bdspeech_recognition_start);// 设置识别开始提示音
//                }
//                if (Config.PLAY_END_SOUND) {
//                    config.enableEndSoundEffect(R.raw.bdspeech_speech_end);
//                }
//                config.setSampleRate(VoiceRecognitionConfig.SAMPLE_RATE_8K); // 设置采样率,需要与外部音频一致
//                // 下面发起识别
//                int code = mASREngine.startVoiceRecognition(mListener, config);
//                if (code != VoiceRecognitionClient.START_WORK_RESULT_WORKING) {
//                    mTextView.setText(getString(R.string.error_start, code));
//                }
//
//                return code == VoiceRecognitionClient.START_WORK_RESULT_WORKING;
//            }
//
//            @Override
//            public boolean onStopListening() {
//                mASREngine.speakFinish();
//                return true;
//            }
//
//            @Override
//            public boolean onCancel() {
//                mASREngine.stopVoiceRecognition();
//                return true;
//            }
//        });


        mGradView = (MyGridView) findViewById(R.id.gv_list);
        //新建List
        lists = new ArrayList<>();
        //获取数据
        getData();

        final GradViewAdapter adapter=new GradViewAdapter(this,mGradView,lists);

        mGradView.setAdapter(adapter);

        mGradView.setOnChanageListener(new MyGridView.OnChanageListener() {
            @Override
            public void onChange(int from, int to) {
                Map<String, Object> temp = lists.get(from);
                //这里的处理需要注意下
                if(from < to){
                    for(int i=from; i<to; i++){
                        Collections.swap(lists, i, i + 1);
                    }
                }else if(from > to){
                    for(int i=from; i>to; i--){
                        Collections.swap(lists, i, i-1);
                    }
                }

                lists.set(to, temp);

                adapter.notifyDataSetChanged();
            }
        });
    }


    private List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            lists.add(map);
        }

        return lists;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    mTextView.setText(bundle.getString("result"));
                    //显示
                    Bitmap bitmap = (Bitmap) data.getParcelableExtra("bitmap");
                    mImageView.setImageBitmap(bitmap);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
//        if (mDialog != null) {
//            mDialog.dismiss();
//        }
        super.onDestroy();
    }

    /**
     * 重写用于处理语音识别回调的监听器
     */
//    private class MyVoiceRecogListener implements VoiceRecognitionClient.VoiceClientStatusChangeListener {
//        @Override
//        public void onClientStatusChange(int status, Object obj) {
//            switch (status) {
//                // 语音识别实际开始，这是真正开始识别的时间点，需在界面提示用户说话。
//                case VoiceRecognitionClient.CLIENT_STATUS_START_RECORDING:
//                    isRecognition = true;
//                    mHandler.removeCallbacks(mUpdateVolume);
//                    mHandler.postDelayed(mUpdateVolume, POWER_UPDATE_INTERVAL);
//                    mControlPanel.statusChange(ControlPanelFragment.STATUS_RECORDING_START);
//                    break;
//                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_START: // 检测到语音起点
//                    mControlPanel.statusChange(ControlPanelFragment.STATUS_SPEECH_START);
//                    break;
//                // 已经检测到语音终点，等待网络返回
//                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_END:
//                    mControlPanel.statusChange(ControlPanelFragment.STATUS_SPEECH_END);
//                    break;
//                // 语音识别完成，显示obj中的结果
//                case VoiceRecognitionClient.CLIENT_STATUS_FINISH:
//                    mControlPanel.statusChange(ControlPanelFragment.STATUS_FINISH);
//                    isRecognition = false;
//                    updateRecognitionResult(obj);
//                    break;
//                // 处理连续上屏
//                case VoiceRecognitionClient.CLIENT_STATUS_UPDATE_RESULTS:
//                    updateRecognitionResult(obj);
//                    break;
//                // 用户取消
//                case VoiceRecognitionClient.CLIENT_STATUS_USER_CANCELED:
//                    mControlPanel.statusChange(ControlPanelFragment.STATUS_FINISH);
//                    isRecognition = false;
//                    break;
//                default:
//                    break;
//            }
//
//        }
//
//        @Override
//        public void onNetworkStatusChange(int i, Object o) {
//
//        }
//
//        @Override
//        public void onError(int errorType, int errorCode) {
//            isRecognition = false;
//            mTextView.setText(getString(R.string.error_occur, Integer.toHexString(errorCode)));
//            mControlPanel.statusChange(ControlPanelFragment.STATUS_FINISH);
//        }
//    }
//
//    /**
//     * 音量更新任务
//     */
//    private Runnable mUpdateVolume = new Runnable() {
//        public void run() {
//            if (isRecognition) {
//                long vol = mASREngine.getCurrentDBLevelMeter();
//                mControlPanel.volumeChange((int) vol);
//                mHandler.removeCallbacks(mUpdateVolume);
//                mHandler.postDelayed(mUpdateVolume, POWER_UPDATE_INTERVAL);
//            }
//        }
//    };
//
//    /**
//     * 将识别结果更新到UI上，搜索模式结果类型为List<String>,输入模式结果类型为List<List<Candidate>>
//     *
//     * @param result
//     */
//    private void updateRecognitionResult(Object result) {
//        if (result != null && result instanceof List) {
//            List results = (List) result;
//            if (results.size() > 0) {
//                if (results.get(0) instanceof List) {
//                    List<List<Candidate>> sentences = (List<List<Candidate>>) result;
//                    StringBuffer sb = new StringBuffer();
//                    for (List<Candidate> candidates : sentences) {
//                        if (candidates != null && candidates.size() > 0) {
//                            sb.append(candidates.get(0).getWord());
//                        }
//                    }
//                    mTextView.setText(sb.toString());
//                } else {
//                    mTextView.setText(results.get(0).toString());
//                }
//            }
//        }
//    }
}
