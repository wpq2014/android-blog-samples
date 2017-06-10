package com.wpq.sample.progress_dialog;

import android.content.Context;

import com.loopj.android.http.*;
import com.loopj.android.http.BuildConfig;

import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * @author wpq
 * @version 1.0
 */
public class MyHttpClient {

    private static final AsyncHttpClient mClient = new AsyncHttpClient();

    static {
        try {
            mClient.setTimeout(20000); // 默认10秒超时
            if(BuildConfig.DEBUG){
                mClient.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory()); //不验证书
            }else{
                mClient.setSSLSocketFactory(SSLSocketFactory.getSocketFactory()); //验证书
            }
            mClient.addHeader("x-app-platform", "Android");
            mClient.addHeader("Content-Type", "application/json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AsyncHttpClient getHttptClient(){
        return mClient;
    }

    /**
     * 取消与当前context关联的所有请求
      * @param context 当前context
     */
    public static void cancelRequests(Context context){
        mClient.cancelRequests(context, true);
    }

    /**
     * 取消所有请求
     */
    public static void cancelAllRequests(){
        mClient.cancelAllRequests(true);
    }


}
