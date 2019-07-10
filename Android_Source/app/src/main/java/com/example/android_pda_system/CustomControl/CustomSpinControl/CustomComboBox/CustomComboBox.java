package com.example.android_pda_system.CustomControl.CustomSpinControl.CustomComboBox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.android_pda_system.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue;
import com.example.android_pda_system.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue_Callback;
import com.example.android_pda_system.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue_ItemObject;

/*******************************************************************************
 *    Description  : PDA Android CustomComboBox
 *    Usage        : Spinner를 통한 CustomComboBox구현.
 *
 *                      private CustomComboBox ccb_emp;
 *
 *                       ccb_emp  = findViewById(R.id.Ccb_Emp_Name);
 *
 *                          // 그룹id 입력
 *                           ccb_emp.GroupID("EMP_INFO");
 *                           // 그룹아이디의 추가 조회주건이 존재하면 (선택)입력
 *                            ccb_emp.GroupParam("");
 *
 *                             // 클릭이벤트 정의
 *                            ccb_emp.SetData(new CustomComboBox.CustomCombobox_Get_Result_Listener() {
 *                                @Override
 *                                public void OnGet_Result(CustomCombo_Adapter CustomCombo_Adapter_result) {
 *                                    ccb_emp.ClickedEvent(CustomCombo_Adapter_result);
 *                                }
 *                            });
 *
 *
 *                            값 가져 오는 법:
 *
 *                            ccb_emp.GetKey() -> 선택된 key값 가져옴
 *                            ccb_emp.GetValue() -> 선택된 Value값 가져옴
 *    사용 xml     :  Spinner Control
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.06.14		JH_KIM                            CREATE
 *
 *******************************************************************************/
public class CustomComboBox extends android.support.v7.widget.AppCompatSpinner {
    static Context _Context = null;
    static String _Value = null;
    static String _Key = null;
    static String _Group_Id = null;
    static String _Group_Param = "";

    public CustomComboBox(Context context) {
        super(context);
        this._Context = context;
    }

    public CustomComboBox(Context context, int mode) {
        super(context, mode);
        this._Context = context;
    }

    public CustomComboBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._Context = context;
    }

    public CustomComboBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this._Context = context;
    }

    public CustomComboBox(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        this._Context = context;
    }

    public String GetValue() {

        return _Value;
    }

    public String GetKey() {

        return _Key;
    }

    // todo 클릭 이벤트 발생시 현재 value를 Data_Set 해주는 기능
    static void Set(String key, String val) {
        _Value = val;
        _Key = key;
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

    //todo KeyValue 데이터를 가지고와 CustomComboBoxAdapter에 정의하는 부분.
    public void SetData() {
        //  region  컨트롤 Data 정의 부분 ///////////////////////////////////////////////////////////////////////
        //todo    콤보박스 데이터 가져오는 부분 생성 및 초기화.
        // 콤보박스 비동기 처리(공통코드 CODE_ID와 구분자 VALUE로 공통 코드 정보 들고옴.)
        // json parsing 된 문자를 가져올 URL 설정.

        try {
            CustomKeyValue CKG = new CustomKeyValue();
            final CustomCombo_Adapter[] _CustomCombo_Adapter1 = new CustomCombo_Adapter[1];
            CKG.SetValue(new CustomKeyValue_Callback() {
                @Override
                public void onSuccess(CustomKeyValue_ItemObject[] result) {
                    //todo  성공시 result에 결과 배열을 가지고 와 CustomComboBox_Adapter를 통해
                    // SpinControl에 입력한다.
                    _CustomCombo_Adapter1[0] = new CustomCombo_Adapter(_Context, android.R.layout.simple_spinner_item, result);
                    _CustomCombo_Adapter1[0].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // CustomCombobox_Get_Result_Listener.OnGet_Result(_CustomCombo_Adapter1[0]);
                    ClickedEvent(_CustomCombo_Adapter1[0]);
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


    //todo   콤보박스 이벤트 설정
    public void ClickedEvent(final CustomCombo_Adapter _CustomComboAdapter) {
        this.setAdapter(_CustomComboAdapter);
        this.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                CustomKeyValue_ItemObject user = _CustomComboAdapter.getItem(position);

                Set(user.get_Key(), user.get_Value());
                Toast.makeText(_Context, user.get_Value() + "가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(_Context,
                        "선택 안됨",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
