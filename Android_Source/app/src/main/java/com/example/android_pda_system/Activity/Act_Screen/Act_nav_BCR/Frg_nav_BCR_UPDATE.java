package com.example.android_pda_system.Activity.Act_Screen.Act_nav_BCR;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_pda_system.Activity.ExtendsClass.Frg_MainTitleFragment;
import com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_CNT.CRV_IF_BCR_CNT_AdapterListener;
import com.example.android_pda_system.R;
import com.example.android_pda_system.ScannerReceiver.ScannerReceiver_Listner;

/*******************************************************************************
 *    Description  : PDA Android 아래 탭 컨트롤 을 사용하기위한 app activity의  sub 화면.
 *    Usage        :
 *    사용 xml     :  act_nav_bcr_chk.xml(현재 class에서 쓰고있는 main화면 xml에서  menu_bcr_chk_bottom.xml 를 사용 ),
 *                      menu_bcr_chk_bottom.xml(메뉴별 항목설정2~3개 2개 사용시 살짝 깨짐.. 3~5개 권장),
 *                      title_menu.xml ,
 *   일반 activity와의 차이점 **** :
 *
 *   context를 부르기 위해
 *
 *   onCreateView메서드 안에서
 *
 *    final View _view = inflater.inflate(R.layout.act_inv_insp, container, false);
 *    를 선언해 준뒤
 *    _view.getconext() 로 가져온다.
 *
 *    타 메서드 안에서는 (getContext()) 로 가져온다.
 *
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.27		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Frg_nav_BCR_UPDATE extends Frg_MainTitleFragment implements CRV_IF_BCR_CNT_AdapterListener, ScannerReceiver_Listner {
    private View _view = null;
    private Context mContext = null;


    //endregion


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.act_inv_insp, container, false);
        mContext = _view.getContext();
        return _view;
    }


    //region CRV_BCR_CNT_Adapter 의 리스트가 이벤트가 발생할 때 리스트Data를 변경하는 메서드 Override
    @Override
    public void onListItemClicked(int position, String Update_String) {


    }
    //endregion


    //todo 바코드 인식할때 작동하는 Method ExtensClass에서 리스너 등록되어있음. Method
    @Override
    public void OnBarcode(String _barcode, String _type, String _module, String _rawdata, int _length, int _decCount) {

    }


    //endregion

}