package yinlei.com.retrofitdemo.ui.rx;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import yinlei.com.retrofitdemo.R;

public class RxFragment extends Fragment {

    @Bind(R.id.btnCreate)
    Button mBtnCreate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rx, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btnCreate)
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnCreate:
                create();
                break;
            default:
                break;
        }
    }

    private void create() {

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d("RxFragment", "Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("RxFragment", "Observable onError" + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("RxFragment", "integer:" + integer);
            }
        });

    }


}
