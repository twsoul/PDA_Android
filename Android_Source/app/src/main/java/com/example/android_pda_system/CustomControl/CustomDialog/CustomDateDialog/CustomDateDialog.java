package com.example.android_pda_system.CustomControl.CustomDialog.CustomDateDialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;

import com.example.android_pda_system.uCommon.CustomFormat.CustomDate_Format.datePickerDialog_Format;

import java.util.Calendar;

/*******************************************************************************
 *    Description  : PDA Android CustomDateDialog
 *                          //선언
 *    Usage        :        private CustomDateDialog Dte_Date;
 *                          // 레이아웃 id 설정
 *                           Dte_Date = findViewById(R.id.Dte_Date);
 *                           // 포맷형식 정의(선택)
 *                           Dte_Date.Format("yyyy-MM-dd");
 *                           //클릭이벤트 정의
 *                            Dte_Date.ClickedEvent(new CustomDateDialog.CustomDateDialog_GetDateResult_Listener() {
 *                           @Override
 *                            public void OnGetDate(String DateString) {
 *                                  Dte_Date.setText(DateString);
 *                                }
 *                           });
 *
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.06.19		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class CustomDateDialog extends android.support.v7.widget.AppCompatEditText {

    public Context _Context;
    public String _Date_Type = "yyyy-MM-dd"; //default 포맷

    public CustomDateDialog(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this._Context = context;
        init();
    }

    public CustomDateDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._Context = context;
        init();
    }

    public CustomDateDialog(Context context) {
        super(context);
        this._Context = context;
        init();
    }

    //todo 다이얼로그 초기화
    public void init() {
        //todo  EditText 사용으로 포커싱 제거
        this.setInputType(EditorInfo.TYPE_NULL);
        this.setText(new datePickerDialog_Format().DateTime_Now());
        DefaultClickedEvent();
    }

    //todo 포맷형식 지정
    public void Format(String date_type) {

        this._Date_Type = date_type;
        SetDate(new datePickerDialog_Format().DateTime_Now(_Date_Type));
        DefaultClickedEvent();
    }

    //todo  비동기 데이터 가져올 인터페이스 설정
    public interface CustomDateDialog_GetDateResult_Listener {
        void OnGetDate(String DateString);
    }

    //클릭이벤트 리스너(클릭 이후 text return event;)
    public void ClickedEvent(final CustomDateDialog_GetDateResult_Listener dialogListener) {
        this.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); //todo  년
                int mMonth = c.get(Calendar.MONTH); //todo   월
                int mDay = c.get(Calendar.DAY_OF_MONTH); //todo  일
                //todo   date picker dialog
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(_Context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                dialogListener.OnGetDate(new datePickerDialog_Format().Format(year, month, day, _Date_Type));//todo  datePickerDialog_Format 클래스를 생성하여서 사용,
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    //todo default 선택 이벤트
    public void DefaultClickedEvent() {
        this.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); //todo  년
                int mMonth = c.get(Calendar.MONTH); //todo   월
                int mDay = c.get(Calendar.DAY_OF_MONTH); //todo  일
                //todo   date picker dialog
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(_Context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                SetDate(new datePickerDialog_Format().Format(year, month, day, _Date_Type));
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }


    void SetDate(String _Date_String) {

        this.setText(_Date_String);
    }
}
