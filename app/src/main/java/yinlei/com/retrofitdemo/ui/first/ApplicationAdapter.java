package yinlei.com.retrofitdemo.ui.first;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.navagation.NavigationDrawerAdapter;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.ui.first.ApplicationAdapter.java
 * @author: myName
 * @date: 2016-07-30 17:39
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private List<AppInfo> mApplicaptions;


    public ApplicationAdapter(List<AppInfo> applicaption) {
        mApplicaptions = applicaption;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.applications_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppInfo appInfo = mApplicaptions.get(position);
        holder.name.setText(appInfo.getName());
        getBitmap(appInfo.getIcon())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(holder.image::setImageBitmap);
    }

    /**
     * 获取图片
     *
     * @param icon
     * @return
     */
    private Observable<Bitmap> getBitmap(String icon) {
        return Observable.create(subscriber -> {
            subscriber.onNext(BitmapFactory.decodeFile(icon));
            subscriber.onCompleted();
        });
    }

    @Override
    public int getItemCount() {
        return mApplicaptions != null ? mApplicaptions.size() : 0;
    }

    public void addData(List<AppInfo> appInfos) {
        mApplicaptions.clear();
        mApplicaptions.addAll(appInfos);
        notifyDataSetChanged();
    }

    public void addData(int i, AppInfo appInfo) {
        if (i<0){
            i = 0;
        }
        mApplicaptions.add(i,appInfo);
        notifyItemInserted(i);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
