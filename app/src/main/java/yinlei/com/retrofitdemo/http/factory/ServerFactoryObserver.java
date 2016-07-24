package yinlei.com.retrofitdemo.http.factory;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.http.factory.ServerFactoryObserver.java
 * @author: myName
 * @date: 2016-07-24 10:41
 */

public class ServerFactoryObserver {

    public static <T> T createServiceFactory(final Class<T> serverClass, String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(serverClass);
    }
}
