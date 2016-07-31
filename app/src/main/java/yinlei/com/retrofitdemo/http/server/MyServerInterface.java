package yinlei.com.retrofitdemo.http.server;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;
import yinlei.com.retrofitdemo.bean.HttpResult;
import yinlei.com.retrofitdemo.bean.ItemBeans;
import yinlei.com.retrofitdemo.bean.QiushiUsers;
import yinlei.com.retrofitdemo.bean.User;

/**
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MyServerInterface.java
 * @author: myName
 * @date: 2016-07-23 23:40
 */

public interface MyServerInterface {

    @GET("article/list/latest?page=1")
    Call<ResponseBody> getLatestJsonString();

    //获取个人信息
    @GET("/users/JakeWharton")
    Observable<User> getUserDataObservable();

    @GET("/users/JakeWharton")
    Call<User> getUserDataLocal();

    @GET("article/list/latest?page=1")
    Observable<ResponseBody> getQiushiResult();

    @GET("article/list/latest?page=1")
    Observable<HttpResult<List<ItemBeans>>> getQiuShiJsonString();

}
