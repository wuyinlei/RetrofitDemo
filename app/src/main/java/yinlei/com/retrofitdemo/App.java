package yinlei.com.retrofitdemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.App.java
 * @author: myName
 * @date: 2016-07-30 17:52
 */

public class App extends Application {

    public static Context instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
