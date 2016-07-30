package yinlei.com.retrofitdemo.ui.user;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yinlei.com.retrofitdemo.constant.Constant;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.bean.User;
import yinlei.com.retrofitdemo.http.factory.ServerFactoryObserver;
import yinlei.com.retrofitdemo.http.server.MyServerInterface;

public class UserFragment extends Fragment {

    @Bind(R.id.iv_conver)
    ImageView mIvConver;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_email)
    TextView mTvEmail;
    @Bind(R.id.tv_num)
    TextView mTvNum;

    @Bind(R.id.btnRequest)
    Button mBtnRequest;

    ProgressDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        ButterKnife.bind(this,view);
        mDialog = new ProgressDialog(getActivity());
        return view;
    }

    @OnClick(R.id.btnRequest)
    public void btnQuery() {
        mDialog.setTitle("loading。。。");
        mDialog.show();
        MyServerInterface serverInterface = ServerFactoryObserver.createServiceFactory(MyServerInterface.class, Constant.USER_URL);
        serverInterface.getUserDataObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        mDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.d("UserFragment", user.toString());
                        updateUi(user);
                    }
                });

    }

    public void updateUi(User mUser) {
        if (mUser != null) {
            mTvName.setText(mUser.getName());
            mTvEmail.setText(mUser.getEmail());
            mTvNum.setText(mUser.getFollowers() + "");
            Toast.makeText(getActivity(), mUser.getAvatar_url(), Toast.LENGTH_SHORT).show();
            //这个获取JackWharton大神的图片貌似有点问题  地址是获取到了，但是加载不出来
            //通过网页加载url是可以获取的，这里用了一张网络图片地址，是可以正常加载的，暂时不明什么原因
            String conver_url = mUser.getAvatar_url();
            Picasso.with(getActivity()).load("http://pic29.nipic.com/20130508/9252150_163600489317_2.jpg").placeholder(R.mipmap.ic_launcher)
                    .into(mIvConver);
        }
    }
}
