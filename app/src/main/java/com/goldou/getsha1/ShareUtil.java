package com.goldou.getsha1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class ShareUtil {
    /**
     * 获取手机内所有支持分享的应用列表
     */
    public static ArrayList<AppInfo> getShareAppList(Context context, Intent intent) {
        ArrayList<AppInfo> shareAppInfos = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

        if (null == resolveInfos) {
            return null;
        } else {
            for (ResolveInfo resolveInfo : resolveInfos) {
                AppInfo appInfo = new AppInfo();
                appInfo.setPkgName(resolveInfo.activityInfo.packageName);
                appInfo.setLaunchClassName(resolveInfo.activityInfo.name);
                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));
                shareAppInfos.add(appInfo);
            }
        }
        return shareAppInfos;
    }
}
