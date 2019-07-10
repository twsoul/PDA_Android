package com.example.android_pda_system.CustomControl.CustomDialog.Dlg_CNT_UPDATE;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_pda_system.R;

/*******************************************************************************
 *    Description  : PDA Android Dialog 창
 *    Usage        : Recycler 뷰에서 사용되는 Dialog
 *    사용 xml     :  act_popup_insert_cnt.xml
 *    호출시       :
 *      //현재 parent contect 에 custom_dialog를 출력한다.
 *
 *             Dlg_INSERT_CNT PIC = new Dlg_INSERT_CNT(_context);
 *             //dialog에 수량정보를 던져준다. 이후 view를 실행함.
 *             PIC.Insert_Popup_Dialog(_Arr_itemList.get(position).get_WEIGHT_CNT());
 *
 *             //마지막(확인 클릭 시)에 수정한 값을 입력해준다.
 *             //수정버튼 클릭 시 이벤트(Dlg_INSERT_CNT.class)
 *             PIC.setDialogListener(new Dlg_INSERT_CNT.Dlg_PositiveClicked_Listener() {
 *                 @Override
 *                 public void onPositiveClicked(String name) {
 *                     _Arr_itemList.get(position).set_WEIGHT_CNT(name);
 *
 *                     // POPUP_INSER_CNT에서 OK를 누르면 해당 이벤트를 타게되는데..
 *                     // 그때 아래 리스터를 호출해준다(Activity로 전달하기위해(interface))
 *                     CRV_IF_BCR_CNT_AdapterListener.onListItemClicked(position, name);
 *                 }
 *             });
 *
 *
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Dlg_INSERT_CNT {

    private String Result_Text;
    private Context _context;
    private Dlg_PositiveClicked_Listener _Dlg_PositiveClicked_Listener;
    private int _input_type = InputType.TYPE_CLASS_TEXT;


    // region 인터페이스 설정 부분
//todo  인터페이스 설정
    public interface Dlg_PositiveClicked_Listener {
        void onPositiveClicked(String name);
    }

    //todo  호출할 리스너
    public void setDialogListener(Dlg_PositiveClicked_Listener Dlg_PositiveClicked_Listener) {
        this._Dlg_PositiveClicked_Listener = Dlg_PositiveClicked_Listener;
    }

    //endregion

    //todo   처음 생성할때 Context 를 받음.
    public Dlg_INSERT_CNT(Context context, int input_type) {

        this._context = context;
        this._input_type = input_type;
    }

    //region 호출할 다이얼로그 함수를 정의부분.
    public void Insert_Popup_Dialog(final String Param) {

        // region 초기 설정
        final Dialog dlg = new Dialog(_context);
        //todo  타이틀바 없애기
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //todo   커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.act_popup_insert_cnt);

        //endregion
        dlg.show();

        //todo  취소시 사용될 Result_Text
        Result_Text = Param;
        //region function을 사용하여 show 한 화면 이므로 dlg.findViewById를 한다.
        final EditText Text_CNT = dlg.findViewById(R.id.Text_CNT);
        final TextView Text_Close_btn = dlg.findViewById(R.id.btn_close);
        final Button Btn_Insert = dlg.findViewById(R.id.Btn_Insert);

        //  endregion

        //region 수량입력란 초기화
        //todo  전달받은 파라미터 Set
        Text_CNT.setText(Param);
        //todo  전체선택
        Text_CNT.selectAll();
        Text_CNT.requestFocus();
        //todo  / 키보드를 보여준다.
        //todo  키보드 보이게 하는 부분

        //todo  숫자키패드 보이게 하는 부분(InputType.TYPE_CLASS_NUMBER)
        try {
            Text_CNT.setInputType(_input_type);
        } catch (Exception ex) {
            //todo   정의되있지 않은 Inputtype 사용했을 때. 무조건 텍스트로
            Text_CNT.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        InputMethodManager imm = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        //endregion

        //region 컨트롤별 버튼 이벤트
        Text_Close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_context, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                //todo   커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        Btn_Insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //todo  리턴받을결과값을 입력시켜준다
                Result_Text = Text_CNT.getText().toString();
                //todo   커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
                _Dlg_PositiveClicked_Listener.onPositiveClicked(Result_Text);
            }
        });
        //endregion
    }
    //endregion


}
