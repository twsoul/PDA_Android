package com.acs.app_update.uCommon.FileDownLoad_APK;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class APK_InstallCheck {
    public boolean APK_InstallCheck(Context context, String packagename){

        String mpackagename = packagename;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mpackagename.trim(), PackageManager.GET_META_DATA);
            ApplicationInfo appInfo = pi.applicationInfo;
            // 패키지가 있을 경우.
//            Log.d(TAG,"Enabled value = " + appInfo.enabled);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
//            Log.d(TAG,"패키지가 설치 되어 있지 않습니다.");
            return false;
        }
    }
}
