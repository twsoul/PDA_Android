package com.acs.app_update.uConfig.Preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.acs.app_update.uConfig.Grobal;

public class AppPreference {
    static Context _Context;
    public AppPreference(Context context){
        this._Context=context;
    }
    //region todo DBConnection을 return 해주는 메서드
    public static String DBConnectionUrl(Context _context){

        //todo TEXT 만들어서 DB 정보 가져왔는데 수정함...(현재 grobal변수 혹은 환경설정 값)
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(_context);
        return SP.getString(Preference_Property.DBConnectionString, Grobal._DBConnectionString_Path);
    }
    //endregion
    public  SharedPreferences Get(String property){
        //todo Preference에 속해있는 속성들을 가져와주는 메서드
        return _Context.getSharedPreferences(property, Activity.MODE_PRIVATE);
    }


}
