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
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.ui.first.AppInfo;
import yinlei.com.retrofitdemo.ui.first.ApplicationAdapter;
import yinlei.com.retrofitdemo.ui.first.ApplicationsList;

/**
 * RxJava的combineLatest()函数有点像zip()函数的特殊形式。
 * 正如我们已经学习的，zip()作用于最近未打包的两个Observables。
 * 相反，combineLatest()作用于最近发射的数据项：如果Observable1
 * 发射了A并且Observable2
 * 发射了B和C，combineLatest()将会分组处理AB和AC
 */
public class CombineLatestFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_combine_latest, container, false);
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

    private void loadList(List<AppInfo> apps) {
        mRecyclerView.setVisibility(View.VISIBLE);


        Observable<AppInfo> appInfoObservable = Observable
                .interval(1000, TimeUnit.MILLISECONDS)
                .map(position -> apps.get(position.intValue()));

        Observable<Long> tictoc = Observable
                .interval(1500, TimeUnit.MILLISECONDS);


        Observable.combineLatest(appInfoObservable, tictoc, this::updateTitle).subscribe(new Subscriber<AppInfo>() {
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
                int position = mAppInfos.size() - 1;
                mAdapter.addData(position, appInfo);
                mRecyclerView.smoothScrollToPosition(position);
            }
        });

    }

    private AppInfo updateTitle(AppInfo appInfo, long time) {
        appInfo.setName(time + appInfo.getName());
        return appInfo;
    }
}
