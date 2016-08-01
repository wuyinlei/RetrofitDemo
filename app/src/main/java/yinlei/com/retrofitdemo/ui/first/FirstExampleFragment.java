package yinlei.com.retrofitdemo.ui.first;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yinlei.com.retrofitdemo.App;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.Utils;

public class FirstExampleFragment extends Fragment {


    @Bind(R.id.fragment_first_example_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.fragment_first_example_swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ApplicationAdapter mAdapter;

    private File mFile;

    private List<AppInfo> mAppInfos = new ArrayList<>();

    private Handler mHandler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_example, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initRecyclerView(view);

        swipeRefreshSetting();


    }

    private void swipeRefreshSetting() {

        mSwipeRefreshLayout.setColorSchemeColors(getResources()
                .getColor(R.color.myPrimaryDarkColor));

        mSwipeRefreshLayout.setProgressViewOffset(false, 0
                , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                        getResources().getDisplayMetrics()));

        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);

        Utils.getFileDir()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    mFile = file;
                    refreshTheList();
                });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mHandler.postDelayed(() -> {
                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }, 3000);
        });
    }


    private void initRecyclerView(View view) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mAdapter = new ApplicationAdapter(new ArrayList<>());
        //mAdapter = new ApplicationAdapter();
        // mAdapter.addData(mAppInfos);
        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * 请求数据
     */
    private void refreshTheList() {
        getApps().toList()
                .distinct()
                //.toSortedList()
                //返回一个排序后的 list
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "完成了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        storeList(appInfos);
                        ApplicationsList.getInstance().setList(appInfos);
                        mAdapter.addData(appInfos);
                        //loadList(appInfos);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                });
    }

    /**
     * 存储数据
     *
     * @param appInfos
     */
    private void storeList(List<AppInfo> appInfos) {

        Schedulers.io().createWorker().schedule(() -> {
            SharedPreferences prefs = getActivity()
                    .getPreferences(Context.MODE_PRIVATE);
            Type appInfoType = new TypeToken<List<AppInfo>>() {
            }.getType();
            prefs.edit().putString("APPS", new Gson().toJson(appInfos, appInfoType)).apply();
        });
    }

  /*  *//**
     * 获取文件名
     *
     * @return
     *//*
    private Observable<File> getFileDir() {
        return Observable.create(subscriber -> {
            subscriber.onNext(App.instance.getFilesDir());
            subscriber.onCompleted();
        });
    }
*/
    /**
     * 获取数据
     *
     * @return
     */
    private Observable<AppInfo> getApps() {
        return Observable.create(subscriber -> {
            List<AppInfoRich> apps = new ArrayList<>();
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> infos = getActivity().getPackageManager().queryIntentActivities(mainIntent, 0);

            for (ResolveInfo info : infos) {
                apps.add(new AppInfoRich(getActivity(), info));
            }

            for (AppInfoRich app : apps) {
                Bitmap icon = Utils.drawableToBitmap(app.getIcon());
                String name = app.getName();
                String iconPath = mFile + "/" + name;
                Utils.storeBitmap(App.instance, icon, name);
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                subscriber.onNext(new AppInfo(app.getLastUpdateTime(), name, iconPath));
            }
            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }

        });
    }

    /**
     * 过滤系列，我们可以利用filter()方法来过滤我们观测序列中不想要的值例如我们只想要开头是C的应用
     * 我们可以filter(appInfo ->appInfo.getName().startsWith("C"))
     * <p>
     * 如果不使用lambda表达式，是这样写的
     * filter(new Func1<AppInfo, Boolean>() {
     *
     * @param apps
     * @Override public Boolean call(AppInfo appInfo) {
     * return appInfo.getName().startsWith("C");
     * }
     * })
     * 我们传入一个新的Func1对象给filter()函数，Func1有一个AppInfo对象来作为他的参数类型并且返回
     * Boolean对象，只要条件符合filter()函数，就会返回true，此时，值会发出去并且所有的观察者都会接受到
     * <p>
     * 当然我们也可以检测null
     */
    private void loadList(List<AppInfo> apps) {
        mRecyclerView.setVisibility(View.VISIBLE);
        Observable.from(apps)
                /*.filter(appInfo ->
                        appInfo.getName().startsWith("A"))*/
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        mAppInfos.add(appInfo);
                        mAdapter.addData(mAppInfos.size() - 1, appInfo);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
