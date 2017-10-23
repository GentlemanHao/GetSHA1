package com.goldou.getsha1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class AppInfoAdapter extends MyBaseAdapter {

    private Context context;
    private ArrayList<AppInfo> mAppList;

    public AppInfoAdapter(Context mContext, ArrayList<AppInfo> date) {
        this.mAppList = date;
        this.context = mContext;
    }

    @Override
    public AppInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appinfo_item, viewGroup, false);
        return new AppInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).tv.setText(mAppList.get(position).getAppName());
            ((ViewHolder) holder).img.setBackground(mAppList.get(position).getAppIcon());

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.OnItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemLongClickListener.OnItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView img;

        ViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.text_list_item);
            img = (ImageView) view.findViewById(R.id.img_list_item);
        }
    }
}
