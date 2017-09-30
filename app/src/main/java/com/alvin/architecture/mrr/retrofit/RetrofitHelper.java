package com.alvin.architecture.mrr.retrofit;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alvin on 2016/12/12.
 */

public class RetrofitHelper {

    private static volatile RetrofitHelper instance = null;
    private static volatile Retrofit mRetrofit = null;

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    private RetrofitHelper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .client(initOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 初始化OkHttpClient
     */
    private OkHttpClient initOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i("RetrofitLog", "retrofitBack = " + message);//打印retrofit日志
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .retryOnConnectionFailure(true)
                .build();
    }


    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            throw new RuntimeException("The retrofit is Null, please call the getInstance() method of RetrofitHelper to initialize");
        }
        return mRetrofit;
    }
}
