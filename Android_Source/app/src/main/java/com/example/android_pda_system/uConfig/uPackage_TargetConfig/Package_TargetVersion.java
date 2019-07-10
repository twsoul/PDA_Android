package com.example.android_pda_system.uConfig.uPackage_TargetConfig;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

// 실행시킬 앱 버전정보 들고오는 로직...
public class Package_TargetVersion {
    public Context _context;

    public Package_TargetVersion(Context context) {
        this._context = context;
    }

    public String get() {

        String Return_Target_Version = "";
        try {
            List<PackageInfo> packs = _context.getPackageManager().getInstalledPackages(PackageManager.PERMISSION_GRANTED);

            Log.i("TAG", "===================================================");

            for (PackageInfo pack : packs) {
                if (pack.packageName.equals(Package_TargetName.TargetPackage)) {
                    Log.d("TAG", "| version : " + pack.versionName);
                    Return_Target_Version = pack.versionName;
                }

            }

            Log.i("TAG", "===================================================");

            return Return_Target_Version;
        } catch (Exception ex) {
            return Return_Target_Version;
        }

    }
}
