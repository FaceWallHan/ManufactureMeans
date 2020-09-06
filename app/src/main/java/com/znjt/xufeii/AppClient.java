package com.znjt.xufeii;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by Administrator on 2019/4/26.
 */

public class AppClient extends Application {
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        // 应用程序入口处调用,避免手机内存过小,杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用“,”分隔。
        // 设置你申请的应用appid

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        StringBuffer param = new StringBuffer();
        param.append("appid="+getString(R.string.app_id));
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE+"="+ SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(AppClient.this, param.toString());
        super.onCreate();
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
    }

    public boolean isLogin() {
        return preferences.getBoolean("login",false);
    }

    public void setLogin(boolean login) {
        preferences.edit().putBoolean("login",login).apply();
    }
}
