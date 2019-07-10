package com.example.android_pda_system.Activity.ExtendsClass;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.android_pda_system.CustomControl.CustomDialog.Dlg_MessageBox_YN.Dlg_MessageBox;
import com.example.android_pda_system.CustomControl.CustomProgressDialog.Dlg_Load_Dialog.Dlg_Load_Dialog;
import com.example.android_pda_system.ScannerReceiver.ScannerReceiver_Frg;
import com.example.android_pda_system.ScannerReceiver.ScannerReceiver_Listner;
import com.example.android_pda_system.uConfig.Preference.Preference_Property;

/*******************************************************************************
 *    Description  : PDA Android 상속받을 Fragment (nav bar 를 사용하는 하위 Fragment 에서 상속)
 *    Usage        :
 *                      로그인정보
 *                      진동여부
 *                      메세지박스표시 (화면전환시 표시예정)
 *                      로딩중화면
 *                      상단 타이틀 바 표시.
 *                      (+) 바코드 스캔 리스너 ScannerReceiver_frg ( frg = Fragment에서 사용하기때문에 붙임)
 *                      Activity와 다름.
 *                      하위 화면에서 각각 Context를 지정해줘야 함.
 *    사용 xml     :
 *    ******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Frg_MainTitleFragment extends Fragment implements ScannerReceiver_Listner {
    //todo  로그인정보 가져옴.
    public SharedPreferences _Login_Info = null;
    //todo  폰 진동처리하기위한 변수
    public Vibrator _Vibrator;
    //todo  바코드 스캐너 데이터 받아올 클래스
    public ScannerReceiver_Frg _ScannerReceiver_frg;
    //todo  Dialog 창 띄우기 Yes or No
    public Dlg_MessageBox _MsgBox = new Dlg_MessageBox();
    //todo  로딩창
    public Dlg_Load_Dialog _Custom_Load_Dialog = new Dlg_Load_Dialog(getContext());


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _Login_Info = getContext().getSharedPreferences(Preference_Property.Login_Info, getContext().MODE_PRIVATE);//todo  상단 타이틀 바 다른 레이아웃
        _Vibrator = (android.os.Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);//todo   폰 진동을 주기위해 설정

        //todo   nav bar 를 사용하는 경우
        //todo  리스너 생성을 각각 폼에서 해준다. (override 하지 않음)
        _ScannerReceiver_frg = new ScannerReceiver_Frg(getContext());
        _ScannerReceiver_frg.setBroadcaseReceiver_Listner(this);
    }

    //todo  바코드 스캐너 해제
    @Override
    public void onDestroy() {

        try {
            getContext().unregisterReceiver(_ScannerReceiver_frg.BarcodeIntentBroadcast);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "바코드 스캐너 설정이 되어있지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();
    }


    @Override
    public void OnBarcode(String _barcode, String _type, String _module, String _rawdata, int _length, int _decCount) {

    }
}

