package com.example.android_pda_system.uConfig.Device_Info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/*******************************************************************************
 *    Description  : PDA Android App_Version_Info
 *    Usage        : 버전정보 가져오는 class <ex>("0.0.0.1") -> gradle에 입력한 version이름.
 *
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.06.19		JH_KIM                            CREATE
 *
 *******************************************************************************/

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
    public String getVersionInfo_ori(Context context) {
        String version = null;
        try {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = i.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return version;
    }
}
