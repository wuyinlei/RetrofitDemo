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
import rx.joins.Pattern2;
import rx.joins.Plan0;
import rx.observables.JoinObservable;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.ui.first.AppInfo;
import yinlei.com.retrofitdemo.ui.first.ApplicationAdapter;
import yinlei.com.retrofitdemo.ui.first.ApplicationsList;

/**
 * 在”异步的世界“中经常会创建这样的场景，我们有多个来源但是又只想有一个结果
 * 多输入，单输出。RxJava的merge()方法将帮助你把两个甚至更多的Observables合
 * 并到他们发射的数据项里。
 * <p>
 * 注意错误时的toast消息，你可以认为每个Observable抛出的错误都将会打断合并。
 * 如果你需要避免这种情况，RxJava提供了mergeDelayError()，它能从一个
 * Observable中继续发射数据即便是其中有一个抛出了错误。
 * 当所有的Observables都完成时，mergeDelayError()将会发射onError()
 */
public class AndThenFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_and_then, container, false);
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

        Observable<AppInfo> appInfoObservable = Observable.from(apps);

        Observable<Long> ticto = Observable.interval(1, TimeUnit.SECONDS);

        Pattern2<AppInfo, Long> pattern2 =
                JoinObservable.from(appInfoObservable).and(ticto);

        Plan0<AppInfo> plan = pattern2.then(this::updateTitle);

        JoinObservable.when(plan)
                .toObservable()
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
