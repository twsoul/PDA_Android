package com.acs.app_update.CustomControl.CustomProgressDialog.Dlg_Load_Dialog;

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

import com.acs.app_update.R;


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
    private ImageView _Img_Progress_loading;
    private Animation _Inim;

    //상태를 알려줄 text 메세지
    private TextView Progress_message;

    public Dlg_Load_Dialog(Context context) {
        this._context = context;
    }

    //region 호출할 다이얼로그 함수를 정의부분.
    // String Param 은 로딩화면에 보여줄 텍스트 문장. (ex) 로그인중... 입력중.. 등등등..
    public void Show_Load(final String Param) {
        //region 초기 설정
        dlg = new Dialog(_context);
        //타이틀바 없애기
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RelativeLayout RelativeLayout_load_LayOut = (RelativeLayout) View.inflate(_context, R.layout.dlg_load_dialog, null);
        // 커스텀 다이얼로그의 레이아웃을 설정한다.

        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(RelativeLayout_load_LayOut);
        //밖에 클릭했을때 종료되지 않게
        dlg.setCancelable(false);
        //endregion
        dlg.show();

        //취소시 사용될 Result_Text
        //region function을 사용하여 show 한 화면 이므로 dlg.findViewById를 한다.
        Progress_message = dlg.findViewById(R.id.Progress_message);

        _Img_Progress_loading = dlg.findViewById(R.id.Img_Progress_loading);
        _Inim = AnimationUtils.loadAnimation(_context, R.anim.alpha_loading);
        _Img_Progress_loading.setAnimation(_Inim);
        //endregion

        //region 수량입력란 초기화
        //전달받은 파라미터 Set 로딩 텍스트 보여주는중..
        Progress_message.setText(Param);

        //endregion
    }

    public void Close() {
        if (dlg.isShowing()) {
            dlg.dismiss();
        }
    }
    //endregion
}
