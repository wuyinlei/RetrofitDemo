package yinlei.com.retrofitdemo.ui;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import yinlei.com.retrofitdemo.BuildConfig;
import yinlei.com.retrofitdemo.NavigationDrawerFragment;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.navagation.NavigationDrawerCallbacks;
import yinlei.com.retrofitdemo.ui.first.FirstExampleFragment;
import yinlei.com.retrofitdemo.ui.four.AndThenFragment;
import yinlei.com.retrofitdemo.ui.four.CombineLatestFragment;
import yinlei.com.retrofitdemo.ui.four.JoinFragment;
import yinlei.com.retrofitdemo.ui.four.MergeFragment;
import yinlei.com.retrofitdemo.ui.four.ZipFragment;
import yinlei.com.retrofitdemo.ui.qiushi.SecondExampleFragment;
import yinlei.com.retrofitdemo.ui.rx.RxFragment;
import yinlei.com.retrofitdemo.ui.second.DistinctFragment;
import yinlei.com.retrofitdemo.ui.second.FilterFragment;
import yinlei.com.retrofitdemo.ui.second.TakeFragment;
import yinlei.com.retrofitdemo.ui.third.GroupByFragment;
import yinlei.com.retrofitdemo.ui.third.MapFragment;
import yinlei.com.retrofitdemo.ui.third.ScanFragment;
import yinlei.com.retrofitdemo.ui.user.UserFragment;

public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks {


    @Bind(R.id.container)
    FrameLayout mContainer;

    @Bind(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @Bind(R.id.drawer)
    DrawerLayout mDrawer;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    // private TextView tv_result;
/*

    private List<ItemBeans.ItemsBean> mItemsBeen;

    @Bind(R.id.btnCall)
    Button btnCall;

    @Bind(R.id.btnObservable)
    Button btnObservable;

    @Bind(R.id.btnRxjava)
    Button btnRxJava;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Logger.t("mytag").d("hello");

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_drawer);

        mNavigationDrawerFragment.setUp(R.id.fragment_drawer, mDrawer, mToolbar);

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog().build());
        }

        // initRetrofit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (position) {
            case 0:

                fragmentManager.beginTransaction()
                        .replace(R.id.container, new FirstExampleFragment())
                        .commit();
                break;
            case 1:

                fragmentManager.beginTransaction()
                        .replace(R.id.container, new SecondExampleFragment())
                        .commit();
                break;

            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new UserFragment())
                        .commit();
                break;

            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new RxFragment())
                        .commit();
                break;
            case 4:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new TakeFragment())
                        .commit();
                break;
            case 5:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new FilterFragment())
                        .commit();
                break;
            case 6:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new DistinctFragment())
                        .commit();
                break;
            case 7:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MapFragment())
                        .commit();
                break;
            case 8:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ScanFragment())
                        .commit();
                break;
            case 9:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new GroupByFragment())
                        .commit();
                break;
            case 10:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MergeFragment())
                        .commit();
                break;
            case 11:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ZipFragment())
                        .commit();
                break;

            case 12:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new JoinFragment())
                        .commit();

                break;
            case 13:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new CombineLatestFragment())
                        .commit();
                break;
            case 14:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new AndThenFragment())
                        .commit();
                break;
            default:

                break;
        }
    }

    /* @OnClick({R.id.btnCall, R.id.btnObservable, R.id.btnRxjava})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btnCall:
                startActivity(new Intent(MainActivity.this, SecondExampleFragment.class));
                break;
            case R.id.btnObservable:
                startActivity(new Intent(MainActivity.this, UserFragment.class));
                break;
            case R.id.btnRxjava:
                startActivity(new Intent(MainActivity.this, RxFragment.class));
                break;
            default:
                break;
        }
    }
*/

}
