package yinlei.com.retrofitdemo;

import android.app.Application;
import android.content.Context;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.App.java
 * @author: myName
 * @date: 2016-07-30 17:52
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    public static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

       // Logger
      //          .init(TAG)                 // default PRETTYLOGGER or use just init()
     //           .methodCount(3)                 // default 2
      //          .hideThreadInfo()               // default shown
       //         .logLevel(LogLevel.NONE)        // default LogLevel.FULL
       //         .methodOffset(2);             // default 0
    }
}
