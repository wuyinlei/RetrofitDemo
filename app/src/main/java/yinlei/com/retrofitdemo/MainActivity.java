package yinlei.com.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    private TextView tv_result;

    private List<PageBean.ItemsBean> mItemsBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initRetrofit();
    }

    private void initView() {
        tv_result = (TextView) findViewById(R.id.result);
        mItemsBeen = new ArrayList<>();
    }

    

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .build();
        MyServerInterface serverInterface = retrofit.create(MyServerInterface.class);
        Call<ResponseBody> call = serverInterface.getLatestJsonString();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // I/yinlei: -------ThreadId------>1  证明返回来的数据是在主线程中的
                Log.i("yinlei", "-------ThreadId------>" + Thread.currentThread().getId());
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        result = response.body().string();
                        try {
                            JSONObject resultObject = new JSONObject(result);
                            int errCode = resultObject.getInt("err");
                            if (errCode == 0) {
                                JSONArray items = resultObject.getJSONArray("items");
                                // Toast.makeText(MainActivity.this, items.toString(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < items.length(); i++) {
                                    PageBean.ItemsBean item = new Gson().fromJson(items.getString(i), new TypeToken<PageBean.ItemsBean>() {
                                    }.getType());
                                    mItemsBeen.add(item);
                                }
                                Toast.makeText(MainActivity.this, "mItemsBeen.size():" + mItemsBeen.size(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
