package com.example.android_pda_system.CustomControl.CustomProgressDialog.Dlg_Load_Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android_pda_system.R;


/*******************************************************************************
 *    Description  : PDA Android 로딩화면 생성
 *    Usage        :  웹서비스에 붇는데 비동기로 실행이 되다보니 화면이 멈추지 않은채로 기능이 진행되어 로딩화면 구성 (190603 현재 로그인 화면에서만 기능 구현)
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Dlg_Load_Dialog {

    public Context _context;
    public Dialog dlg;
    private ImageView _Img_Progress_Loading;
    private Animation _Inim;

    //상태를 알려줄 text 메세지
    private TextView Progress_Message;

    public Dlg_Load_Dialog(Context context) {

        this._context = context;

    }

    // 다이얼로그 초기설정..
    void init() {
        //region 초기 설정
        dlg = new Dialog(_context);
        //todo  타이틀바 없애기
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //레이아웃 설정..
        RelativeLayout RelativeLayout_load_LayOut = (RelativeLayout) View.inflate(_context, R.layout.dlg_load_dialog, null);
        //todo   커스텀 다이얼로그의 레이아웃을 설정한다.

        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(RelativeLayout_load_LayOut);
        //todo  밖에 클릭했을때 종료되지 않게
        dlg.setCancelable(false);


        Progress_Message = dlg.findViewById(R.id.Progress_Message);
        _Img_Progress_Loading = dlg.findViewById(R.id.Img_Progress_Loading);
        _Inim = AnimationUtils.loadAnimation(_context, R.anim.alpha_loading);
        _Img_Progress_Loading.setAnimation(_Inim);
        //endregion
    }

    //region 호출할 다이얼로그 함수를 정의부분.
    // String Param 은 로딩화면에 보여줄 텍스트 문장. (ex) 로그인중... 입력중.. 등등등..
    public void Show_Load(final String Param) {
        init();
        dlg.show();
        //region 수량입력란 초기화
        //todo  전달받은 파라미터 Set 로딩 텍스트 보여주는중..
        Progress_Message.setText(Param);
        //endregion
    }

    //텍스트 입력 안했을때
    // Default Text ==로딩중입니다.
    public void Show_Load() {
        init();
        dlg.show();

        Progress_Message.setText("로딩중입니다.");

    }

    public void Close() {
        if (dlg.isShowing()) {
            dlg.dismiss();
        }
    }
    //endregion
}
