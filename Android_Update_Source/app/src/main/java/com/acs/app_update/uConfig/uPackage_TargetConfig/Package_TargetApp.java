package com.acs.app_update.uConfig.uPackage_TargetConfig;

import android.content.Context;
import android.content.Intent;

// 실행시킬 앱 실행하는 Class...
public class Package_TargetApp {
    public Context _Context;

    public Package_TargetApp(Context context) {
        this._Context = context;
    }

    public void run() {
        Intent intent = _Context.getPackageManager().getLaunchIntentForPackage(Package_TargetName.TargetPackage);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _Context.startActivity(intent);
    }
}
