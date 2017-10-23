package com.goldou.getsha1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class AppAdapter extends MyBaseAdapter {
    private List<AppUtils.AppInfo> list;

    public AppAdapter(List<AppUtils.AppInfo> appsInfo) {
        this.list = appsInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).app_icon.setImageDrawable(list.get(position).getIcon());
            ((ViewHolder) holder).app_name.setText(list.get(position).getName());
            ((ViewHolder) holder).app_version.setText(list.get(position).getVersionName());
            ((ViewHolder) holder).app_package.setText(list.get(position).getPackageName());
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int layoutPosition = holder.getLayoutPosition();
                        onItemClickListener.OnItemClick(holder.itemView, layoutPosition);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView app_icon;
        private TextView app_name, app_version, app_package;

        public ViewHolder(View itemView) {
            super(itemView);
            app_icon = (ImageView) itemView.findViewById(R.id.app_icon);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
            app_version = (TextView) itemView.findViewById(R.id.app_version);
            app_package = (TextView) itemView.findViewById(R.id.app_package);
        }
    }
}
