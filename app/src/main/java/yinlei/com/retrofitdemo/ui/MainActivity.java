package yinlei.com.retrofitdemo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.bean.PageBean;
import yinlei.com.retrofitdemo.ui.qiushi.QiushiActivity;
import yinlei.com.retrofitdemo.ui.user.UserActivity;

public class MainActivity extends AppCompatActivity {

   // private TextView tv_result;

    private List<PageBean.ItemsBean> mItemsBeen;

    @Bind(R.id.btnCall)
    Button btnCall;

    @Bind(R.id.btnObservable)
    Button btnObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

       // initRetrofit();
    }

    @OnClick({R.id.btnCall,R.id.btnObservable})
    public void click(View v){
        switch (v.getId()){
            case R.id.btnCall:
                startActivity(new Intent(MainActivity.this,QiushiActivity.class));
                break;
            case R.id.btnObservable:
                startActivity(new Intent(MainActivity.this,UserActivity.class));
                break;
        }
    }


}
