package com.example.android_pda_system.Activity.Act_Main_Screen.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_pda_system.Activity.Act_Main_Screen.Act_Main;
import com.example.android_pda_system.CustomControl.CustomProgressDialog.Dlg_Load_Dialog.Dlg_Load_Dialog;
import com.example.android_pda_system.R;
import com.example.android_pda_system.uCommon.CustomParameters.CustomParameters;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.VolleyCallback;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_Connected_ErrorType;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_JsonObject_Get;
import com.example.android_pda_system.uConfig.Preference.AppPreference;
import com.example.android_pda_system.uConfig.Preference.Preference_Property;
import com.example.android_pda_system.uConfig.Preference.Preference_Setting;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Act_Login extends AppCompatActivity {
    //현재 Context
    private Context mContext = Act_Login.this;

    private EditText Txt_Login_Id;
    private EditText Txt_Login_PW;
    private CheckBox Chk_Auto_Login;
    private CheckBox Chk_Auto_Save_Login;
    private Button Btn_Login;
    //todo 환경설정 버튼
    private Button Btn_Setting;

    //로딩중 폼...
    Dlg_Load_Dialog CLD = new Dlg_Load_Dialog(this);


    //todo  환경변수에서 가져올 값.
    String loginId, loginPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        Txt_Login_Id = findViewById(R.id.Txt_Login_Id);
        Txt_Login_PW = findViewById(R.id.Txt_Login_PW);
        Btn_Login = findViewById(R.id.btn_Login);
        Chk_Auto_Login = findViewById(R.id.Chk_Auto_Login);
        Chk_Auto_Save_Login = findViewById(R.id.Chk_Auto_Save_Login);
        Btn_Setting = findViewById(R.id.Btn_Setting);

        //todo  현재 로그인 정보 저장 기능.
        final SharedPreferences Login_Info = new AppPreference(mContext).Get(Preference_Property.Login_Info);
        //todo  자동로그인 환경정보
        final SharedPreferences auto_Login = new AppPreference(mContext).Get(Preference_Property.Auto_Login);
        //todo  로그인 정보 저장 환경 정보
        final SharedPreferences save_Login_Info = new AppPreference(mContext).Get(Preference_Property.Save_Login_Info);

        //todo  처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        //todo   getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        //todo   첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        loginId = auto_Login.getString("loginId", null);
        loginPwd = auto_Login.getString("loginPwd", null);


        //todo   로그인 정보 존재할 때 로그인 정보 가져옴.

        //todo  처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        //todo   getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        //todo   첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        //todo  로그인정보 저장 환경정보.

        Txt_Login_Id.setText(save_Login_Info.getString("loginId", null));
        Txt_Login_PW.setText(save_Login_Info.getString("loginPwd", null));
        //todo  로그인 정보가 있을 때 로그인 정보 저장 체크박스 표시
        if (Txt_Login_Id != null && Txt_Login_PW != null) {
            Chk_Auto_Save_Login.setChecked(true);
        }

        Btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLD.Show_Load("로그인중......");


                try {
                    //todo  CRV_BCR_CNT_Json_Get 에서 바코드 정보를 조회 한 후 arr_RCV_BCR에 바코드 정보를 담는다.
                    //todo   json parsing 된 문자를 가져올 URL 설정.
                    CustomParameters param = new CustomParameters("SP_ANDROIDPDA_EMP_LOGIN_L");
                    param.add("@P_EMP_ID", Txt_Login_Id.getText().toString());
                    param.add("@P_PW", Txt_Login_PW.getText().toString());

                    final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(mContext, param.ExcuteStr());

                    obj_get.callWCFService(new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONArray[] result) {

                            //todo Login_Object와 조회되는 컬럼명이 일치해야 ArrayList로 가지고 올 수 있음.
                            ArrayList<Login_Object> arr_Login_Info = new Gson().fromJson(result[0].toString(), new TypeToken<List<Login_Object>>() {
                            }.getType());
//                            ArrayList<Login_Object> arr_Login_Info = new Volley_JsonArray_Login_Set().Get_Login_Data(result[0]);

                            if (arr_Login_Info.size() == 1) {
                                //todo   로그인 되었을 때만 로그인정보저장 체크 했을시 정보 저장
                                if (Chk_Auto_Save_Login.isChecked()) {
                                    //todo  로그인정보 저장 환경정보.
                                    SharedPreferences.Editor save_Login_info = save_Login_Info.edit();
                                    save_Login_info.putString("loginId", arr_Login_Info.get(0).get_EMP_ID());
                                    save_Login_info.putString("loginPwd", Txt_Login_PW.getText().toString());
                                    save_Login_info.commit();
                                } else {
                                    //todo  로그인정보 저장 환경정보.
                                    SharedPreferences.Editor save_Login_info = save_Login_Info.edit();
                                    //todo  editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                    save_Login_info.clear();
                                    save_Login_info.commit();
                                }
                                //todo   자동로그인 체크 했을시 정보 저장
                                if (Chk_Auto_Login.isChecked()) {


                                    //todo  auto의 loginId와 loginPwd에 값을 저장해 줍니다.

                                    SharedPreferences.Editor autoLogin = auto_Login.edit();
                                    autoLogin.putString("loginId", arr_Login_Info.get(0).get_EMP_ID());
                                    autoLogin.putString("loginPwd", Txt_Login_PW.getText().toString());

                                    //todo  꼭 commit()을 해줘야 값이 저장됩니다
                                    autoLogin.commit();
                                }
                                //todo  현재 로그인 정보 저장.
                                SharedPreferences.Editor Login_Info_edit = Login_Info.edit();
                                Login_Info_edit.putString("loginId", arr_Login_Info.get(0).get_EMP_ID());
                                Login_Info_edit.putString("loginName", arr_Login_Info.get(0).get_EMP_NAME());
                                Login_Info_edit.putString("login_empno", arr_Login_Info.get(0).get_EMP_NO());
                                Login_Info_edit.commit();


                                CLD.Close();
                                Toast.makeText(mContext, arr_Login_Info.get(0).get_EMP_NAME() + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                                Intent regist = new Intent(mContext, Act_Main.class);
                                mContext.startActivity(regist);
                                finish();


                            } else {
                                CLD.Close();
                                Toast.makeText(mContext, "로그인 정보를 확인하여 주세요.", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFail(String result, Volley_Connected_ErrorType ErrorType) {
                            CLD.Close();

                            Toast.makeText(mContext,
                                    result,
                                    Toast.LENGTH_LONG).show();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //todo  MainActivity로 들어왔을 때 마지막에  loginId와 loginPwd값을 가져와서 null이 아니면 자동로그인 시작.
        //todo  값을 가져와 id가 부르곰이고 pwd가 네이버 이면 자동적으로 액티비티 이동.
        if (loginId != null && loginPwd != null) {
            //todo  값은 textView에 넣고 로그인 버튼 실행.
            Txt_Login_Id.setText(loginId);
            Txt_Login_PW.setText(loginPwd);

            Btn_Login.performClick();

        }


//todo  환경설정 버튼 클릭 이벤트
        Btn_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regist = new Intent(mContext, Preference_Setting.class);
                mContext.startActivity(regist);
            }
        });
    }
}


