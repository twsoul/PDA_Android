package com.example.android_pda_system.uCommon.FileDownLoad_APK;

import android.content.Context;
import android.widget.Toast;

import com.example.android_pda_system.uCommon.CustomConvert.ByteArrayToBase64;
import com.example.android_pda_system.uCommon.CustomParameters.CustomParameters;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.VolleyCallback;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_Connected_ErrorType;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_JsonObject_Get;
import com.example.android_pda_system.uConfig.Grobal;

import org.json.JSONArray;

import java.io.File;

public class APK_File_Get {

    public Context _Context;
    public APK_Get_Result_Listener _APK_Get_Result_Listener;
    //다운로드 받은파일
    File _DownloadFile = null;

    public APK_File_Get(Context context) {
        this._Context = context;
    }

    //인터페이스 설정
    public interface APK_Get_Result_Listener {
        void OnGet_Result(boolean get_result, File _DownLoadFile);
    }

    //호출할 리스너
    public void setAPK_Get_Result_Listener(APK_Get_Result_Listener APK_Get_Result_Listener) {
        this._APK_Get_Result_Listener = APK_Get_Result_Listener;
    }

    public void APK_Get() {
        try {

            // json parsing 된 문자를 가져올 URL 설정.
            CustomParameters param = new CustomParameters(Grobal.GetFile_SpName);
            param.add("@P_VAR", "");
            //바코드 정보를 가져와 리스트에 뿌려준다.
            final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(_Context, param.ExcuteStr());

            obj_get.callWCFService(new VolleyCallback() {
                @Override
                public void onSuccess(JSONArray[] result) {
                    // 에러발생시 Error_str 에 담아서 결과값 넘어옴.
                    try {

                        ByteArrayToBase64 CBase64 = new ByteArrayToBase64();
                        //BASE64인지 체크
                        if (CBase64.CheckBase64(result[0].getJSONObject(0).getString("APK_FILE"))) {
                            //BASE64이면 BYTEA배열에 담음.

                            byte[] APK_FILES = null;
                            APK_FILES = CBase64.Decode(result[0].getJSONObject(0).getString("APK_FILE"));
                            FileDownLoad FD = new FileDownLoad(_Context);
                            _DownloadFile = FD.writeResponseBodyToDisk(APK_FILES);
                            if (_DownloadFile != null) {
                                _APK_Get_Result_Listener.OnGet_Result(true, _DownloadFile);
                            } else {
                                _APK_Get_Result_Listener.OnGet_Result(false, _DownloadFile);
                            }

                        } else {
                            _APK_Get_Result_Listener.OnGet_Result(false, _DownloadFile);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(_Context,
                                ex.toString(),
                                Toast.LENGTH_SHORT).show();
                        _APK_Get_Result_Listener.OnGet_Result(false, _DownloadFile);

                    }

                }

                @Override
                public void onFail(String result, Volley_Connected_ErrorType ErrorType) {
                    Toast.makeText(_Context,
                            result,
                            Toast.LENGTH_SHORT).show();
                    _APK_Get_Result_Listener.OnGet_Result(false, _DownloadFile);
                }
            }); // for문의 행 번호를 넘겨준다.


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
