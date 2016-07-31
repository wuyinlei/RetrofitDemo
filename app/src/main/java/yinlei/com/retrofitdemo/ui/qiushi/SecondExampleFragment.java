package yinlei.com.retrofitdemo.ui.qiushi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.functions.Func1;
import yinlei.com.retrofitdemo.ApiException;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.bean.HttpResult;
import yinlei.com.retrofitdemo.bean.ItemBeans;
import yinlei.com.retrofitdemo.http.HttpMethods;
import yinlei.com.retrofitdemo.subscribers.ProgressSubscriber;
import yinlei.com.retrofitdemo.subscribers.SubscriberOnNextListener;

public class SecondExampleFragment extends Fragment {

    private List<ItemBeans> mItemsBeen = new ArrayList<>();

    private QiushiAdapter mAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SubscriberOnNextListener getItemBeans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_qiushi, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();

        getItemBeans = new SubscriberOnNextListener<List<ItemBeans>>() {
            @Override
            public void onNext(List<ItemBeans> itemBeen) {
                Log.d("SecondExampleFragment", "itemBeen.size():" + itemBeen.size());
               /* for (int i = 0; i < itemBeen.size(); i++) {
                    Log.d("SecondExampleFragment", itemBeen.get(i).getUser().getLogin().toString());
                }*/
                mItemsBeen.addAll(itemBeen);
                mAdapter = new QiushiAdapter(getActivity());
                mAdapter.addItemBean(mItemsBeen);
                mRecyclerView.setAdapter(mAdapter);
            }
        };

        getItems();

       //initData1();
         //initData();
        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }



    //进行网络请求
    private void getItems() {
        HttpMethods.getInstance().getItemBean(new ProgressSubscriber(getItemBeans,getActivity()));
    }
    /**
     * 请求网络数据
     */
    public void initData() {
       /* MyServerInterface serverInterface = ServerFactory.createServiceFactory(MyServerInterface.class, Constant.BASE_URL);

        serverInterface.getQiuShiJsonString()
                .map(new HttpResultFunc<List<ItemBeans>>())
                .subscribe(new Subscriber<List<ItemBeans>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ItemBeans> itemBeen) {
                        Log.d("SecondExampleFragment", "itemBeen.size():" + itemBeen.size());
                    }
                });*/
       /* HttpMethods.getInstance().getItemBean(new Subscriber<List<ItemBeans>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ItemBeans> itemBeen) {

            }
        });*/


        /*serverInterface.getQiushiResult()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(ResponseBody responseBody) {
                                   JSONObject resultObject = null;
                                   try {
                                       if (responseBody.string() != null) {
                                           try {
                                               resultObject = new JSONObject(responseBody.string());

                                               int errCode = resultObject.getInt("err");
                                               if (errCode == 0) {
                                                   JSONArray items = resultObject.getJSONArray("items");
                                                   // Toast.makeText(MainActivity.this, items.toString(), Toast.LENGTH_SHORT).show();
                                                   for (int i = 0; i < items.length(); i++) {
                                                       ItemBeans item = new Gson().fromJson(items.getString(i), new TypeToken<ItemBeans>() {
                                                       }.getType());
                                                       mItemsBeen.add(item);
                                                   }
                                               }
                                           } catch (JSONException e) {
                                               e.printStackTrace();
                                           }
                                       }
                                   } catch (Exception e) {
                                       e.printStackTrace();
                                   }

                                   mAdapter.addData(mItemsBeen);
                               }
                           }
                );*/
/*
        serverInterface.getQiuShiJsonString()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<List<ItemBeans>>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "www", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "eerr", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(HttpResult<List<ItemBeans>> listHttpResult) {

                        mItemsBeen = listHttpResult.getSubjects();
                        Log.d("SecondExampleFragment", "mItemsBeen.size():" + mItemsBeen.size());
                        //  mAdapter.addData(mItemsBeen);
                    }
                });*/
    }

   /* private void parseJson(String string) {
        JSONObject resultObject = null;
        if (string != null) {
            try {
                resultObject = new JSONObject(string);

                int errCode = resultObject.getInt("err");
                if (errCode == 0) {
                    JSONArray items = resultObject.getJSONArray("items");
                    // Toast.makeText(MainActivity.this, items.toString(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < items.length(); i++) {
                        PageBean.ItemsBean item = new Gson().fromJson(items.getString(i), new TypeToken<PageBean.ItemsBean>() {
                        }.getType());
                        mItemsBeen.add(item);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCount() == 0) {
                try {
                    throw new ApiException(100);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
            return httpResult.getSubjects();
        }

    }

   /* private void initData1() {
        MyServerInterface serverInterface = ServerFactory.createServiceFactory(MyServerInterface.class, Constant.BASE_URL);
        Call<ResponseBody> call = serverInterface.getLatestJsonString();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // I/yinlei: -------ThreadId------>1  证明返回来的数据是在主线程中的
                Log.i("yinlei", "-------ThreadId------>" + Thread.currentThread().getId());
                if (response.isSuccess()) {
                    String result = null;
                    try {
                        result = response.body().string();
                        try {
                            JSONObject resultObject = new JSONObject(result);
                            int errCode = resultObject.getInt("err");
                            if (errCode == 0) {
                                JSONArray items = resultObject.getJSONArray("items");
                                //Toast.makeText(getActivity(), items.toString(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject object = items.getJSONObject(i);
                                    Log.d("SecondExampleFragment", object.toString());
                                    PageBean.ItemsBean itemsBean = new PageBean.ItemsBean();
                                    itemsBean.setPublished_at(object.getLong("published_at"));
                                    JSONObject userObject = object.getJSONObject("user");
                                    itemsBean.setContent(object.getString("content"));
                                    PageBean.ItemsBean.UserBean userBean = new PageBean.ItemsBean.UserBean();
                                    userBean.setLogin(userObject.getString("login"));
                                    userBean.setState(userObject.getString("state"));
                                    itemsBean.setUser(userBean);

                                  mItemsBeen.add(itemsBean);
                                }
                                mAdapter = new QiushiAdapter(mItemsBeen,getActivity());
                                mAdapter.addData(mItemsBeen);
                                Log.d("SecondExampleFragment", "mItemsBeen.size():" + mItemsBeen.size());
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }*/

}
