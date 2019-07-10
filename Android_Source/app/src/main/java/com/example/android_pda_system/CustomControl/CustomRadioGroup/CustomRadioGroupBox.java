package com.example.android_pda_system.CustomControl.CustomRadioGroup;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android_pda_system.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue;
import com.example.android_pda_system.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue_Callback;
import com.example.android_pda_system.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue_ItemObject;

public class CustomRadioGroupBox extends RadioGroup {

    public Context _Context;
    static String _Value = null;
    static String _Key = null;
    static String _Group_Id = null;
    static String _Group_Param = "";
    static int Font_Size = 15;  //Default 15로 지정

    public CustomRadioGroupBox(Context context) {
        super(context);
        this._Context = context;
        init();
    }

    public CustomRadioGroupBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._Context = context;
        init();
    }

    void init() {
        this.setGravity(Gravity.CENTER);
    }

    //todo 선택된 Text값 리턴
    public String GetValue() {

        return _Value;
    }

    //todo 선택된 Key값 리턴
    public String GetKey() {

        return _Key;
    }

    // todo GroupID 정의
    public void GroupID(String group_id) {

        this._Group_Id = group_id;
    }

    // todo GroupID 에대한 조건 파라미터 입력
    public void GroupParam(String group_param) {
        if (group_param == null)
            group_param = "";
        this._Group_Param = group_param;
    }

    // todo 폰트사이즈 정의
    public void SetTextSize(int font_size) {

        this.Font_Size = font_size;
    }

    //todo 통신하여 데이터를 들고와 라디오 버튼을 생성 메서드를 실행...
    public void SetData() {
        //  region  컨트롤 Data 정의 부분 ///////////////////////////////////////////////////////////////////////
        //todo    콤보박스 데이터 가져오는 부분 생성 및 초기화.
        // 콤보박스 비동기 처리(공통코드 CODE_ID와 구분자 VALUE로 공통 코드 정보 들고옴.)
        // json parsing 된 문자를 가져올 URL 설정.

        try {
            CustomKeyValue CKG = new CustomKeyValue();
            CKG.SetValue(new CustomKeyValue_Callback() {
                @Override
                public void onSuccess(CustomKeyValue_ItemObject[] result) {
                    //todo  성공시 result에 결과 배열을 가지고 와 CustomComboBox_Adapter를 통해
                    // SpinControl에 입력한다.
                    DataBinding(result);

                }

                @Override
                public void onFail(String result) {
                    Toast.makeText(_Context, result, Toast.LENGTH_LONG).show();
                }
            }, _Context, _Group_Id, _Group_Param);

        } catch (Exception ex) {
            Toast.makeText(_Context, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        //endregion
    }

    //todo 통신하여 데이터를 들고온 라디오 버튼을 생성해주고 클릭이벤트 를 생성해준다.
    public void DataBinding(CustomKeyValue_ItemObject[] _data) {
        RadioButton radio_btn[] = new RadioButton[_data.length];
        for (int iRow = 0; iRow < _data.length; iRow++) {
            radio_btn[iRow] = new RadioButton(_Context);

            radio_btn[iRow].setId(iRow);
            radio_btn[iRow].setText(_data[iRow].get_Value());
            radio_btn[iRow].setTag(_data[iRow].get_Key());
            radio_btn[iRow].setTextColor(Color.rgb(0, 0, 0));
            radio_btn[iRow].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            radio_btn[iRow].setTextSize(Font_Size);

            this.addView(radio_btn[iRow]);
            //버튼클릭 이벤트 ...
            SetClick(radio_btn[iRow]);
        }
    }

    //todo 각각 라디오 버튼 클릭 시  Key,Value를 저장하고 가지고 올 수 있도록 한다.
    public void SetClick(final RadioButton _button) {
        _button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                _Key = _button.getTag().toString();
                _Value = _button.getText().toString();
            }
        });

    }
}
