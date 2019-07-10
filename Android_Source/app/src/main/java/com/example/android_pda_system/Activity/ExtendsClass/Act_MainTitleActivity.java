package com.example.android_pda_system.Activity.ExtendsClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android_pda_system.CustomControl.CustomDialog.Dlg_MessageBox_YN.Dlg_MessageBox;
import com.example.android_pda_system.CustomControl.CustomProgressDialog.Dlg_Load_Dialog.Dlg_Load_Dialog;
import com.example.android_pda_system.R;
import com.example.android_pda_system.ScannerReceiver.ScannerReceiver;
import com.example.android_pda_system.ScannerReceiver.ScannerReceiver_Listner;
import com.example.android_pda_system.uConfig.Preference.Preference_Property;

/*******************************************************************************
 *    Description  : PDA Android 상속받을 메인activity
 *    Usage        :
 *                  상단 타이틀
 *                   로그인정보(Login_Info)  SharedPreferences
 *                   휴대폰 울림기능         Vibrator
 *                   바코드 스캐너 읽음      ScannerReceiver
 *                   메세지박스              Dlg_MessageBox
 *    사용 xml     :
 *    ******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/
public class Act_MainTitleActivity extends AppCompatActivity implements ScannerReceiver_Listner {
    //todo  로그인정보 가져옴.
    public SharedPreferences _Login_Info = null;
    //todo  폰 진동처리하기위한 변수
    public Vibrator _Vibrator;
    //todo  바코드 스캐너 데이터 받아올 클래스
    public ScannerReceiver _ScannerReceiver;
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
        _Login_Info = this.getSharedPreferences(Preference_Property.Login_Info, Activity.MODE_PRIVATE);//todo 상단 타이틀 바 다른 레이아웃
        _Vibrator = (android.os.Vibrator) getSystemService(Context.VIBRATOR_SERVICE);//todo   폰 진동을 주기위해 설정
        //todo  바코드 스캐너 데이터 받아올 클래스 를 리스너에 등록해준뒤
        //todo  OnBarcode 를 override 하여 가져온다.
        _ScannerReceiver = new ScannerReceiver(this);
        _ScannerReceiver.setBroadcaseReceiver_Listner(this);


    }

    //todo  바코드 스캐너 해제
    @Override
    protected void onDestroy() {
        try {
            this.unregisterReceiver(_ScannerReceiver.BarcodeIntentBroadcast);
        } catch (Exception ex) {
            Toast.makeText(this, "바코드 스캐너 설정이 되어있지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();
    }

    @Override
    public void OnBarcode(String _barcode, String _type, String _module, String _rawdata, int _length, int _decCount) {

    }
}
