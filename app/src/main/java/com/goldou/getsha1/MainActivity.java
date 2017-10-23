package com.goldou.getsha1;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView app_icon;
    private TextView app_name, app_package, app_b_name, app_b_number, app_path, app_sha1;
    private AlertDialog dialog;
    private List<AppUtils.AppInfo> appsInfo;
    private ImageView app_share;
    private BottomSheetDialog shareDialog;
    private String sha1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.init(this);

        RecyclerView rl_app = (RecyclerView) findViewById(R.id.rl_app);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rl_app.setLayoutManager(layoutManager);
        appsInfo = AppUtils.getAppsInfo();
        for (int i = 0; i < appsInfo.size(); i++) {
            if (appsInfo.get(i).isSystem() || appsInfo.get(i).getPackageName().equals(getPackageName())) {
                appsInfo.remove(i);
                i--;
            }
        }
        AppAdapter appInfoAdapter = new AppAdapter(appsInfo);
        appInfoAdapter.setOnItemClickListener(new AppAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int layoutPosition) {
                showAppInfoDialog(layoutPosition);
            }
        });
        rl_app.setAdapter(appInfoAdapter);
    }

    private void showAppInfoDialog(final int i) {
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.custom_dialog);
            View view = View.inflate(MainActivity.this, R.layout.appinfo, null);
            app_icon = (ImageView) view.findViewById(R.id.app_icon);
            app_name = (TextView) view.findViewById(R.id.app_name);
            app_package = (TextView) view.findViewById(R.id.app_package);
            app_b_name = (TextView) view.findViewById(R.id.app_b_name);
            app_path = (TextView) view.findViewById(R.id.app_path);
            app_sha1 = (TextView) view.findViewById(R.id.app_sha1);
            app_share = (ImageView) view.findViewById(R.id.app_share);
            app_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    showShareDialog(i);
                }
            });
            builder.setView(view);
            dialog = builder.create();
        }
        app_icon.setImageDrawable(appsInfo.get(i).getIcon());
        app_name.setText(appsInfo.get(i).getName());
        app_package.setText(appsInfo.get(i).getPackageName());
        app_b_name.setText("版本名称：" + appsInfo.get(i).getVersionName());
        app_path.setText("路径：" + appsInfo.get(i).getPackagePath());
        sha1 = AppUtils.getAppSignatureSHA1(appsInfo.get(i).getPackageName());
        app_sha1.setText("SHA1：" + sha1);
        dialog.show();
    }

    private void showShareDialog(int i) {
        if (shareDialog == null) {
            shareDialog = new BottomSheetDialog(this);
            shareDialog.setContentView(R.layout.share_dialog);
            initShareContent();
        } else {
            shareDialog.show();
        }
    }

    private void initShareContent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "www.github.com");
        final ArrayList<AppInfo> appList = ShareUtil.getShareAppList(this, shareIntent);
        AppInfoAdapter appInfoAdapter = new AppInfoAdapter(this, appList);
        RecyclerView rl_share = (RecyclerView) shareDialog.findViewById(R.id.rl_share);
        rl_share.setLayoutManager(new GridLayoutManager(this, 3));
        rl_share.setAdapter(appInfoAdapter);
        appInfoAdapter.setOnItemClickListener(new MyBaseAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int position) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setComponent(new ComponentName(appList.get(position).getPkgName(), appList.get(position).getLaunchClassName()));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, sha1);
                startActivity(intent);
            }
        });
        appInfoAdapter.setOnItemLongClickListener(new MyBaseAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View itemView, int position) {
                // 打开应用信息界面
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + appList.get(position).getPkgName()));
                startActivity(intent);
            }
        });

        shareDialog.show();
    }
}
