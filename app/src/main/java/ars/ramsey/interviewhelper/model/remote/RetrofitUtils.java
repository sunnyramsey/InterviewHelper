package ars.ramsey.interviewhelper.model.remote;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ramsey on 2017/5/9.
 */

public class RetrofitUtils {
    private static Retrofit INSTANCE;

    public static <T> T createApi(Class<T> clazz) {
        if (INSTANCE == null) {
            synchronized (RetrofitUtils.class) {
                if (INSTANCE == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl("http://zhuanlan.zhihu.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                    INSTANCE = builder.build();
                }
            }
        }
        return INSTANCE.create(clazz);
    }
}
