package com.goldou.getsha1;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class AppInfo {
    private String pkgName;
    private String launchClassName;
    private String appName;
    private Drawable appIcon;

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getLaunchClassName() {
        return launchClassName;
    }

    public void setLaunchClassName(String launchClassName) {
        this.launchClassName = launchClassName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
