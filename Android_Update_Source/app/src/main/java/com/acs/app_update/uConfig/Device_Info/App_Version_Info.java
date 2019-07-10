package com.acs.app_update.uConfig.Device_Info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class App_Version_Info {

    public String getVersionInfo(Context context) {
        String version = null;
        try {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = i.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return version.replace(".", "");
    }
}
