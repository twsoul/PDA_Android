package com.example.android_pda_system.Activity.Act_Main_Screen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_pda_system.Activity.Act_Main_Screen.Login.Act_Login;
import com.example.android_pda_system.Activity.Act_Main_Sub_Menu.Act_Inv;
import com.example.android_pda_system.Activity.Act_Screen.Act_nav_BCR.Act_nav_BCR;
import com.example.android_pda_system.R;
import com.example.android_pda_system.uConfig.Device_Info.App_Version_Info;
import com.example.android_pda_system.uConfig.Preference.Preference_Property;
import com.example.android_pda_system.uConfig.Preference.Preference_Setting;

/*******************************************************************************
 *    Description  : PDA Android 메인화면
 *    Usage        :
 *    사용 xml     :  act_main.xml
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Act_Main extends AppCompatActivity implements View.OnClickListener {
    //현재 Context
    private Context mContext = Act_Main.this;
    private Button Btn_Bottom_Nav;
    private Button Btn_Inv;


    //환경설정버튼
    private Button Btn_Setting;
    //로그아웃 버튼
    private Button Btn_LogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        //todo 위젯에 대한 참조.

        Btn_Bottom_Nav = findViewById(R.id.Btn_Bottom_Nav);
        Btn_Bottom_Nav.setOnClickListener(this);

        Btn_Inv = findViewById(R.id.Btn_Inv);
        Btn_Inv.setOnClickListener(this);

        Btn_Setting = findViewById(R.id.Btn_Setting);
        Btn_Setting.setOnClickListener(this);

        Btn_LogOut = findViewById(R.id.Btn_LogOut);
        Btn_LogOut.setOnClickListener(this);

        //region versionName 출력
        TextView versionName = findViewById(R.id.txt_version);
        versionName.setText(new App_Version_Info().getVersionInfo_ori(mContext));
        //endregion

    }

    @Override
    public void onClick(View v) {
        Intent _intent;
        switch (v.getId()) {
            //todo 탭컨트롤 예제
            case R.id.Btn_Bottom_Nav:
                _intent = new Intent(mContext, Act_nav_BCR.class);
                startActivity(_intent);
                break;
            //todo 재고관리
            case R.id.Btn_Inv:
                _intent = new Intent(mContext, Act_Inv.class);
                startActivity(_intent);
                break;
            //todo 환경설정 버튼
            case R.id.Btn_Setting:
                _intent = new Intent(mContext, Preference_Setting.class);
                mContext.startActivity(_intent);
                break;
            //todo 로그아웃 버튼
            case R.id.Btn_LogOut:

                //todo  SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                //todo  SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                Intent intent = new Intent(mContext, Act_Login.class);
                startActivity(intent);
                SharedPreferences auto = getSharedPreferences(Preference_Property.Auto_Login, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //todo  editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                Toast.makeText(mContext, "로그아웃.", Toast.LENGTH_SHORT).show();
                finish();
                break;


        }
    }

}