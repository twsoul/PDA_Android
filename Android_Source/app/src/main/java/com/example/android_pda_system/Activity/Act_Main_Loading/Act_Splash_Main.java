package com.example.android_pda_system.Activity.Act_Main_Loading;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.android_pda_system.Activity.Act_Main_Screen.Act_Main;
import com.example.android_pda_system.Activity.Act_Main_Screen.Act_Main_Fail;
import com.example.android_pda_system.Activity.Act_Main_Screen.Login.Act_Login;
import com.example.android_pda_system.uCommon.CustomParameters.CustomParameters;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.VolleyCallback;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_Connected_ErrorType;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_JsonObject_Get;
import com.example.android_pda_system.uConfig.Device_Info.Device_GetCheck;
import com.example.android_pda_system.uConfig.Device_Info.Device_IpAddress_Get;
import com.example.android_pda_system.uConfig.Device_Info.Device_Uuid_Get;
import com.example.android_pda_system.uConfig.Grobal;
import com.example.android_pda_system.uConfig.Preference.Preference_Property;

import org.json.JSONArray;

import java.util.ArrayList;


public class Act_Splash_Main extends Activity {
    private Context mContext = Act_Splash_Main.this;

    //todo 현재 사용중인 단말기의 uuid 담을 변수
    static String My_Uuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //todo 권한 체크
        Permission_Check();
    }


    //TODO 권한확인
    public void Permission_Check() {

        //todo  마시멜로우 버전과 같거나 이상이라면 저장소 권한 체크
        //  외부저장소 관련 권한 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(mContext, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }

                //todo 마지막 인자는 체크해야될 권한 갯수
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        2);


            } else {
                Toast.makeText(mContext, "권한 설정 완료", Toast.LENGTH_SHORT).show();
                Run();
            }
        }
    }

    //todo 권한체크했는지 확인....
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //todo 수락 클릭시 실행
                    Run();

                } else {
                    // 거절클릭시 사용자가 직접 권한을 주도록 화면을 띄워줌.
                    Toast.makeText(mContext, "권한이 없어 실행 불가합니다.권한 설정을 해주세요.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:" + mContext.getPackageName()));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(i);
                }
                return;
            }
        }
    }


    //todo DBConnection 정보 체크 이후 환경설정 저장
    // 존재할 경우 .. grobal변수값을 쓰지 않음....(종료후 재시작  하면 됨.)
    public void DBConnectInfo_SaveCheck() {
        //todo 실행시 DB 접속정보 저장
        try {


            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
            SharedPreferences.Editor editor = pref.edit();
            //todo DB연결정보 없을시 저장 함..
            if (pref.getString(Preference_Property.DBConnectionString, "").equals("")) {
                editor.putString(Preference_Property.DBConnectionString, Grobal._DBConnectionString_Path);
                editor.commit();
            }
            Toast.makeText(mContext, "DB설정 완료.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //todo uuid 체크
    public boolean CheckUuid() {

        Boolean Pass_Check = false;

        //todo  디바이스 uuid 체크
        Device_GetCheck Devices = new Device_GetCheck();
        ArrayList<String> arr_Devices = Devices.Get_Device_Info();


        Device_Uuid_Get Uuid = new Device_Uuid_Get(mContext);
        My_Uuid = Uuid.getDeviceUuid().toString();

        for (int iRow = 0; iRow < arr_Devices.size(); iRow++) {
            if (arr_Devices.get(iRow).toUpperCase().trim().equals(My_Uuid.toUpperCase().trim())) {
                //todo  본인 uuid log 기록
                Log.i("uuid", My_Uuid.toUpperCase().trim());
                Pass_Check = true;
                break;
            }

        }
//        return Pass_Check;
        return true;
    }


    public void Run() {

        DBConnectInfo_SaveCheck();

        Boolean Pass_Check = CheckUuid();


        //todo  uuid 일치할 때.
        if (Pass_Check) {

            //todo   APK파일 체크 후 가져오기(버전체크는 Android_Update_Source로 적용....

            Intent regist = new Intent(mContext, Act_Main.class);
            regist.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(regist);
            finish();

        }
        //todo  uuid 일치하지 않을 때..
        else {

            Device_IpAddress_Get local_ip = new Device_IpAddress_Get();
            CustomParameters param = new CustomParameters("SP_ANDROIDPDA_UUID_S");
            param.add("@P_UUID", My_Uuid.toUpperCase());
            param.add("@P_IP_ADDR", local_ip.getLocalIpAddress());
            //todo  바코드 정보를 가져와 리스트에 뿌려준다.
            final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(mContext, param.ExcuteStr());

            final String finalMy_Uuid = My_Uuid;

            //todo   uuid 를 보여준다,
            Toast.makeText(mContext, finalMy_Uuid.toUpperCase(), Toast.LENGTH_LONG);
            Log.d("uuid", My_Uuid.toUpperCase().trim());

            obj_get.callWCFService(new VolleyCallback() {
                @Override
                public void onSuccess(JSONArray[] result) {

                    Toast.makeText(mContext, "미등록 단말기 저장완료", Toast.LENGTH_SHORT);
                    //todo  현재리스트에 추가
                }

                @Override
                public void onFail(String result, Volley_Connected_ErrorType ErrorType) {

                    Toast.makeText(mContext, result, Toast.LENGTH_SHORT);


                }
            });
            //todo  바코드 정보를 가져와 리스트에 뿌려준다.

            Intent regist = new Intent(mContext, Act_Main_Fail.class);
            regist.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(regist);
            finish();
        }
    }


}
