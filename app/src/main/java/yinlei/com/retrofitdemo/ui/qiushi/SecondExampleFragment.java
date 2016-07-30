package yinlei.com.retrofitdemo.ui.qiushi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yinlei.com.retrofitdemo.bean.PageBean;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.constant.Constant;
import yinlei.com.retrofitdemo.http.NetWorkApi;
import yinlei.com.retrofitdemo.http.factory.ServerFactory;
import yinlei.com.retrofitdemo.http.server.MyServerInterface;

public class SecondExampleFragment extends Fragment {

    private List<PageBean.ItemsBean> mItemsBeen;

    @Bind(R.id.tv_result)
    TextView mTvResult;

    @Bind(R.id.btnRequest)
    Button btnRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_qiushi, container, false);
        ButterKnife.bind(this, view);
        mItemsBeen = new ArrayList<>();
       // initData();
        return view;
    }

    @OnClick(R.id.btnRequest)
    public void request(View view){
        initData();
    }

    private void initData() {
        MyServerInterface serverInterface = ServerFactory.createServiceFactory(MyServerInterface.class, Constant.BASE_URL);
        Call<ResponseBody> call = serverInterface.getLatestJsonString();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // I/yinlei: -------ThreadId------>1  证明返回来的数据是在主线程中的
                Log.i("yinlei", "-------ThreadId------>" + Thread.currentThread().getId());
                if (response.isSuccess()) {
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
                            }
                            mTvResult.setText(result);
                            Log.d("SecondExampleFragment", result);
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
            }
        });
    }


}
