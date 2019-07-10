package com.acs.app_update.uConfig.Preference;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.acs.app_update.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue;
import com.acs.app_update.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue_Callback;
import com.acs.app_update.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue_ItemObject;
import com.acs.app_update.uConfig.Grobal;


/*******************************************************************************
 *    Description  : PDA Android Preference_Setting
 *                   환경설정 값 저장 하는부분
 *  createPreferenceHierarchy() 메서드에서 소스로 원하는 항목을 DB에서 가져오거나 직접 설정한다.
 *    사용 xml     :
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.30		JH_KIM                            CREATE
 *
 *******************************************************************************/


public class Preference_Setting extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(createPreferenceHierarchy());
        /**EditText summary init*/

        //  SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        // String strUserName = pref.getString(Preference_Property.DBConnectionString, _DBConnectionString_Path);


    }


    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getApplication()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(getApplication()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference connectionPref = findPreference(key);
        if (connectionPref instanceof EditTextPreference) {
            EditTextPreference EditPref = (EditTextPreference) connectionPref;
            EditPref.setSummary(EditPref.getText());
        } else if (connectionPref instanceof ListPreference) {
            ListPreference ListPref = (ListPreference) connectionPref;
            ListPref.setSummary(ListPref.getValue());
        }
    }


    private PreferenceScreen createPreferenceHierarchy() {

        final PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Preference_Setting.this);

        final PreferenceCategory dialogBasedPrefConn = new PreferenceCategory(Preference_Setting.this);
        dialogBasedPrefConn.setTitle("연결정보");
        root.addPreference(dialogBasedPrefConn);
        EditTextPreference TextPref = new EditTextPreference(Preference_Setting.this);
        TextPref.setDialogTitle("다이얼로그 타이틀");
        //todo   key설정
        TextPref.setKey(Preference_Property.DBConnectionString);
        TextPref.setTitle("DB연결문자열");
        //todo 값이 없을경우 dbconnectionString grobal변수값 가져옴.
        TextPref.setSummary(pref.getString(Preference_Property.DBConnectionString, Grobal._DBConnectionString_Path));
        dialogBasedPrefConn.addPreference(TextPref);

        PreferenceCategory inlinePrefCat = new PreferenceCategory(Preference_Setting.this);
        root.addPreference(dialogBasedPrefConn);


        // region 사용자 정보 칸 리스트 출력
        final PreferenceCategory dialogBasedPrefUser = new PreferenceCategory(Preference_Setting.this);
        dialogBasedPrefUser.setTitle("사용자 정보");

        //todo   공장구분 가지고 오는 부분.
        CustomKeyValue CKG = new CustomKeyValue();
        CKG.SetValue(new CustomKeyValue_Callback() {
            @Override
            public void onSuccess(final CustomKeyValue_ItemObject[] result) {

                CharSequence[] Key = new CharSequence[result.length];
                CharSequence[] Value = new CharSequence[result.length];

                for (int iRow = 0; iRow < result.length; iRow++) {
                    Key[iRow] = result[iRow].get_Key();
                    Value[iRow] = result[iRow].get_Value();

                }

                root.addPreference(dialogBasedPrefUser);
                final ListPreference listPref = new ListPreference(Preference_Setting.this);
                listPref.setEntries(Value);
                listPref.setEntryValues(Key);
                listPref.setDialogTitle("다이얼로그 타이틀");
                //todo   key입력
                listPref.setKey(Preference_Property.PLANT);
                listPref.setTitle("공장구분");
                //todo 환경설정에 값없을때 default값 선택없음으로보여줌.
                listPref.setSummary(GetValue(result,pref.getString(Preference_Property.PLANT, "선택없음")));
                listPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        //todo 값이 변할때 마다 보여지는 summary 값이 value값으로 보일수 있도록.
                        listPref.setValue((String) newValue);
                        listPref.setSummary(GetValue(result,(String) newValue));
                        return false;
                    }
                });
                dialogBasedPrefUser.addPreference(listPref);
                PreferenceCategory inlinePrefCat = new PreferenceCategory(Preference_Setting.this);
                //inlinePrefCat.setTitle("타이틀");
                root.addPreference(inlinePrefCat);

            }

            @Override
            public void onFail(String result) {
            }
        }, this, "PLANT", "");

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //todo   사원정보 가지고 오는부분.
        CKG.SetValue(new CustomKeyValue_Callback() {
            @Override
            public void onSuccess(final CustomKeyValue_ItemObject[] result) {

                CharSequence[] Key = new CharSequence[result.length];
                CharSequence[] Value = new CharSequence[result.length];

                for (int iRow = 0; iRow < result.length; iRow++) {
                    Key[iRow] = result[iRow].get_Key();
                    Value[iRow] = result[iRow].get_Value();

                }

                root.addPreference(dialogBasedPrefUser);
                final ListPreference listPref = new ListPreference(Preference_Setting.this);
                listPref.setEntries(Value);
                listPref.setEntryValues(Key);
                listPref.setDialogTitle("사용자");
                //todo key 입력
                listPref.setKey(Preference_Property.USER_INFO);
                listPref.setTitle("사원정보");
                //todo 환경설정에 값없을때 default값 선택없음으로보여줌.
                listPref.setSummary(pref.getString(Preference_Property.USER_INFO, "선택없음"));
                listPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        //todo 값이 변할때 마다 보여지는 summary 값이 value값으로 보일수 있도록.
                        listPref.setValue((String) newValue);
                        listPref.setSummary(GetValue(result,(String) newValue));

                        return true;
                    }
                });

                dialogBasedPrefUser.addPreference(listPref);
                PreferenceCategory inlinePrefCat = new PreferenceCategory(Preference_Setting.this);
                //inlinePrefCat.setTitle("타이틀");
                root.addPreference(inlinePrefCat);

            }

            @Override
            public void onFail(String result) {
            }
        }, this, "EMP_INFO", "");

        //endregion


        return root;
    }
    public String GetValue(CustomKeyValue_ItemObject[] object, String plant_id){
        String result="";
        for (int iRow = 0 ; iRow<object.length;iRow++){
            if(object[iRow].get_Key().equals(plant_id)){
                result=object[iRow].get_Value();
            }
            else{
                Log.d("key",object[iRow].get_Key());
                Log.d("value",object[iRow].get_Value());
            }
        }
        return result;
    }
}






