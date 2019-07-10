package com.example.android_pda_system.Activity.Act_Screen.Act_nav_BCR;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.android_pda_system.Activity.ExtendsClass.Frg_MainTitleActivity;
import com.example.android_pda_system.R;

/*******************************************************************************
 *    Description  : PDA Android 아래 탭 컨트롤 을 사용하기위한 app activity의  메인 화면.(Frg_MainTitleActivity 상속)
 *                       FragmentTransaction == 처음보여지는 화면 view
 *                        FragmentManager    == 하단 버튼 리스트 보여줌.
 *    Usage        :
 *    사용 xml     :
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.27		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Act_nav_BCR extends Frg_MainTitleActivity {
    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private Frg_nav_BCR_IN _Frg_nav_BCR_IN = new Frg_nav_BCR_IN();
    private Frg_nav_BCR_OUT _Frg_nav_BCR_OUT = new Frg_nav_BCR_OUT();
    private Frg_nav_BCR_UPDATE _Frg_nav_BCR_UPDATE = new Frg_nav_BCR_UPDATE();


    // 상단 버튼 클릭했을때 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.btn_Hide:
                //상단 조회조건 들 layout 숨기기(어떤 레이아웃인지 알기위해 직접 작성)
                final LinearLayout _Lay_Search = findViewById(R.id.Lay_Search);
                if (_Lay_Search.getVisibility() == View.GONE) {
                    _Lay_Search.setVisibility(View.VISIBLE);
                } else {
                    _Lay_Search.setVisibility(View.GONE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion 상단 타이틀 바 다른 레이아웃

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_nav_bcr_chk);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        // 첫 화면 지정(하단 navbar View)
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, _Frg_nav_BCR_IN).commitAllowingStateLoss();

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.nav_Insert_menu: {
                        // 상단 타이틀바 text 변경
                        setTitle("바코드 정보 저장");
                        transaction.replace(R.id.frame_layout, _Frg_nav_BCR_IN).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.nav_Delete_menu: {
                        setTitle("바코드 등록정보 삭제");
                        transaction.replace(R.id.frame_layout, _Frg_nav_BCR_OUT).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.nav_Update_menu: {
                        setTitle("바코드 등록정보 수정");
                        transaction.replace(R.id.frame_layout, _Frg_nav_BCR_UPDATE).commitAllowingStateLoss();
                        break;
                    }

                }

                return true;
            }
        });
    }


}