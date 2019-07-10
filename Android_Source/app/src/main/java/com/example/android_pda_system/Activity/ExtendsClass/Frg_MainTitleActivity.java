package com.example.android_pda_system.Activity.ExtendsClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android_pda_system.CustomControl.CustomDialog.Dlg_MessageBox_YN.Dlg_MessageBox;
import com.example.android_pda_system.CustomControl.CustomProgressDialog.Dlg_Load_Dialog.Dlg_Load_Dialog;
import com.example.android_pda_system.R;
import com.example.android_pda_system.uConfig.Preference.Preference_Property;

/*******************************************************************************
 *    Description  : PDA Android 상속받을 메인activity (nav bar 를 사용하는 mainActivity에서 상속)
 *    Usage        :
 *                      로그인정보
 *                      진동여부
 *                      메세지박스표시 (화면전환시 표시예정)
 *                      로딩중화면
 *                      상단 타이틀 바 표시.
 *    사용 xml     :
 *    ******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Frg_MainTitleActivity extends AppCompatActivity {
    //todo  로그인정보 가져옴.
    public SharedPreferences _Login_Info = null;
    //todo  폰 진동처리하기위한 변수
    public Vibrator _Vibrator;

    //todo  Dialog 창 띄우기 Yes or No
    public Dlg_MessageBox _MsgBox = new Dlg_MessageBox();
    //todo  로딩창
    public Dlg_Load_Dialog _Custom_Load_Dialog = new Dlg_Load_Dialog(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_menu, menu);
        return true;
    }

    //todo   상단 버튼 클릭했을때 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo   Handle presses on the action bar items
        switch (item.getItemId()) {
//            case R.id.btn_Hide:
//                //상단 조회조건 들 layout 숨기기
//                final LinearLayout _Lay_Search = findViewById(R.id.Lay_Search);
//                if (_Lay_Search.getVisibility() == View.GONE) {
//                    _Lay_Search.setVisibility(View.VISIBLE);
//                } else {
//                    _Lay_Search.setVisibility(View.GONE);
//                }
//                return true;
            case R.id.btn_back:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _Login_Info = this.getSharedPreferences(Preference_Property.Login_Info, Activity.MODE_PRIVATE);//todo  상단 타이틀 바 다른 레이아웃
        _Vibrator = (android.os.Vibrator) getSystemService(Context.VIBRATOR_SERVICE);//todo   폰 진동을 주기위해 설정

        //todo  탭컨트롤의 main화면에서는 바코드 스캐너를 실행시키지 않는다.
    }


}