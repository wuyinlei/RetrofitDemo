package yinlei.com.retrofitdemo.http.factory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.http.factory.ServerFactory.java
 * @author: myName
 * @date: 2016-07-24 15:22
 */

public class ServerFactory {

    public static <T> T createServiceFactory(final Class<T> serverClass, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(serverClass);
    }
}
