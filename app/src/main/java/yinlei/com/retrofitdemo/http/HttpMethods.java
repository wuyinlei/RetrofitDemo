package yinlei.com.retrofitdemo.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yinlei.com.retrofitdemo.constant.Constant;
import yinlei.com.retrofitdemo.http.server.MyServerInterface;
import yinlei.com.retrofitdemo.bean.User;

/**
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.http.HttpMethods.java
 * @author: myName
 * @date: 2016-07-24 12:04
 */

public class HttpMethods {

    private static final String TAG = HttpMethods.class.getSimpleName();

    private Retrofit mRetrofit;
    private static final  int DEFAULT_TIME_OUT=5;

    private MyServerInterface mInterface;

    //在访问HttpMethods时候创建单例
    private static class SingleMethod{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //对外提供获取单例的方法
    public static HttpMethods getInstance(){
        return SingleMethod.INSTANCE;
    }

    private HttpMethods(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.USER_URL)
                .build();

        mInterface = mRetrofit.create(MyServerInterface.class);
    }

    public void getTopUser (Subscriber<User> subscriber) {

        mInterface.getUserDataObservable().subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
