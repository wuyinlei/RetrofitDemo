package yinlei.com.retrofitdemo.ui.third;


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

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.functions.Func2;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.ui.first.AppInfo;
import yinlei.com.retrofitdemo.ui.first.ApplicationAdapter;
import yinlei.com.retrofitdemo.ui.first.ApplicationsList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
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

        Observable.from(apps)
                //RxJava的scan()函数可以看做是一个累积函数。scan()函数对原始Observable发射的每一项数据都应用一个函数，
                // 计算出函数的结果值，并将该值填充回可观测序列，等待和下一次发射的数据一起使用
                //通过map我们追加一个map调用，我们创建一个简单的函数来更新AppInfo对象并提供一个名字小写
                // 的新版本给观察者
                .scan((appinfo,appinfo2)->{
                    if (appinfo.getName().length()>appinfo2.getName().length()){
                        return appinfo;
                    } else {
                        return appinfo2;
                    }
                })
                .distinct()
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
