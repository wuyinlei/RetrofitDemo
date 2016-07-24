package yinlei.com.retrofitdemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.MyServerInterface.java
 * @author: myName
 * @date: 2016-07-23 23:40
 */

public interface MyServerInterface {

    @GET("article/list/latest?page=1")
    Call<ResponseBody> getLatestJsonString();
}
