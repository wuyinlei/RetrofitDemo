package yinlei.com.retrofitdemo.ui.four;

import android.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.ui.first.AppInfo;
import yinlei.com.retrofitdemo.ui.first.ApplicationAdapter;
import yinlei.com.retrofitdemo.ui.first.ApplicationsList;

/**
 * 从多个Observables接收数据，处理它们，然后将它们合并成一
 * 个新的可观测序列来使用。RxJava有一个特殊的方法可以完成：
 * zip()合并两个或者多个Observables发射出的数据项，
 * 根据指定的函数Func*变换它们，并发射一个新值。
 */
public class ZipFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_zip, container, false);
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

        List<AppInfo> apps = ApplicationsList.getInstance().getList();

        loadList(apps);
    }

    private AppInfo updateTile(AppInfo appInfo, long time) {
        appInfo.setName(time + appInfo.getName());
        return appInfo;
    }

    private void loadList(List<AppInfo> apps) {
        mRecyclerView.setVisibility(View.VISIBLE);

        Observable<AppInfo> observableApp = Observable.from(apps);

        Observable<Long> tictoc = Observable.interval(1, TimeUnit.SECONDS);


        Observable.zip(observableApp, tictoc, (appInfo, aLong) -> updateTile(appInfo, aLong))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AppInfo>() {
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
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        mAppInfos.add(appInfo);
                        int position = mAppInfos.size() - 1;
                        mAdapter.addData(position, appInfo);
                        mRecyclerView.smoothScrollToPosition(position);
                    }
                });

    }
}
