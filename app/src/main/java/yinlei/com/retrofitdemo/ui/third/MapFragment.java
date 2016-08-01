package yinlei.com.retrofitdemo.ui.third;


import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import yinlei.com.retrofitdemo.App;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.Utils;
import yinlei.com.retrofitdemo.ui.first.AppInfo;
import yinlei.com.retrofitdemo.ui.first.AppInfoRich;
import yinlei.com.retrofitdemo.ui.first.ApplicationAdapter;
import yinlei.com.retrofitdemo.ui.first.ApplicationsList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private File mFile;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ApplicationAdapter mAdapter;

    private ArrayList<AppInfo> mAppInfos = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ApplicationAdapter(mAppInfos);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(getResources()
                .getColor(R.color.myPrimaryDarkColor));

        mSwipeRefreshLayout.setProgressViewOffset(false, 0
                , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                        getResources().getDisplayMetrics()));

        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);

        //List<AppInfo> apps = ApplicationsList.getInstance().getList();
        refreshTheList();

    }

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
     * 请求数据
     */
    private void refreshTheList() {
        getApps().toSortedList()
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
                        ApplicationsList.getInstance().setList(appInfos);
                        mAdapter.addData(appInfos);
                        //loadList(appInfos);
                        loadList(appInfos);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                });
    }


    private void loadList(List<AppInfo> apps) {
        mRecyclerView.setVisibility(View.VISIBLE);

        Observable.from(apps)
                //通过map我们追加一个map调用，我们创建一个简单的函数来更新AppInfo对象并提供一个名字小写的新版本给观察者
                .map(appInfo -> {
                    String currentAppName = appInfo.getName();
                    String lowerCaseName = currentAppName.toLowerCase();
                    appInfo.setName(lowerCaseName);
                    return appInfo;
                })
                .subscribe(new Observer<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "Something is worong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        mAppInfos.add(appInfo);
                        mAdapter.addData(mAppInfos.size() - 1, appInfo);
                    }
                });

    }
}
