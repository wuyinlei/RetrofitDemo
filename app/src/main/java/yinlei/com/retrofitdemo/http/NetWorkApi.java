package yinlei.com.retrofitdemo.http;

import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yinlei.com.retrofitdemo.bean.PageBean;
import yinlei.com.retrofitdemo.constant.Constant;
import yinlei.com.retrofitdemo.http.factory.ServerFactory;
import yinlei.com.retrofitdemo.http.factory.ServerFactoryObserver;
import yinlei.com.retrofitdemo.http.server.MyServerInterface;
import yinlei.com.retrofitdemo.ui.user.UserActivity;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.http.NetWorkApi.java
 * @author: myName
 * @date: 2016-07-24 10:44
 */

public class NetWorkApi {


}
