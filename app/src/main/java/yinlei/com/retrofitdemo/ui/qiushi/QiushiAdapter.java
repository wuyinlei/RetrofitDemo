package yinlei.com.retrofitdemo.ui.qiushi;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yinlei.com.retrofitdemo.R;
import yinlei.com.retrofitdemo.Utils;
import yinlei.com.retrofitdemo.bean.ItemBeans;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: QiushiAdapter.java
 * @author: 若兰明月
 * @date: 2016-07-30 21:18
 */

public class QiushiAdapter extends RecyclerView.Adapter<QiushiAdapter.ViewHolder> {


    private List<ItemBeans> mItemsBeen = new ArrayList<>();
    private static Context mContext;

    public void addItemBean(List<ItemBeans> been) {
        mItemsBeen = been;
    }

    public QiushiAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qiushi_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QiushiAdapter.ViewHolder holder, int position) {
        ItemBeans itemsBean = mItemsBeen.get(position);

       // holder.mLoginName.setText(u.getLogin());

        if (itemsBean.getUser()!=null&&!TextUtils.isEmpty(itemsBean.getUser().toString())) {
            if (itemsBean.getUser().getState().equals("active")) {
                holder.mState.setText("在线");
            } else if (itemsBean.getUser().getState().equals("publish")) {
                holder.mState.setText("离线");
            }
        }
        holder.mPublishTime.setText(Utils.millisToLifeString(itemsBean.getPublished_at()));
        holder.mContent.setText(itemsBean.getContent());
    }

    @Override
    public int getItemCount() {
        return mItemsBeen != null ? mItemsBeen.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView mImage;
        TextView mLoginName;
        TextView mState;
        TextView mPublishTime;
        TextView mContent;
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mLoginName = (TextView) itemView.findViewById(R.id.name);
            mState = (TextView) itemView.findViewById(R.id.state);
            mPublishTime = (TextView) itemView.findViewById(R.id.publish_time);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mCardView.setOnClickListener(view -> Toast.makeText(mContext, "点击我", Toast.LENGTH_SHORT).show());
        }
    }
}
