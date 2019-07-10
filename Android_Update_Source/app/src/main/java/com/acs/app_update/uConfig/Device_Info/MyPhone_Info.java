package com.acs.app_update.uConfig.Device_Info;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import static android.content.Context.TELEPHONY_SERVICE;

public class MyPhone_Info {
    public Context _Context;
    public MyPhone_Info(Context context) {

        this._Context=context;
    }
    //내 휴대폰 번호 가져오는 부분.
    public String PhoneNumber() {
        ////<uses-permission android:name="android.permission.READ_PHONE_STATE" />
        // 필요

        String PhoneNumber ="";
        try {
            TelephonyManager telManager = (TelephonyManager) _Context.getSystemService(TELEPHONY_SERVICE);
            PhoneNumber = telManager.getLine1Number();
            if (PhoneNumber.startsWith("+82")) {
                PhoneNumber = PhoneNumber.replace("+82", "0");
            }
        }
        catch (Exception ex){
            Toast.makeText(_Context,ex.toString(),Toast.LENGTH_LONG);
        }
        finally {
            return PhoneNumber;
        }
    }

}
