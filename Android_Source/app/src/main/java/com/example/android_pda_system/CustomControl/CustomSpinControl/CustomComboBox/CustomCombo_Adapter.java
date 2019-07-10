package com.example.android_pda_system.CustomControl.CustomSpinControl.CustomComboBox;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android_pda_system.uCommon.CustomKeyValue_ReturnCode.CustomKeyValue_ItemObject;

/*******************************************************************************
 *    Description  : PDA Android CustomRecyclerView ComboBox_JSON_GET
 *    Usage        : 웹서비스 에서 가져온 JSON STRING를 가져와서
 *                    Spinner Control 에 Key,Value 성 컨트롤을 사용하기 위해 만든 Adapter
 *
 *                    CustomKeyValue_ItemObject [] 배열을 사용한다. (경로 Setting_Common_code \ CustomKeyValue_ItemObject)
 *
 *                    CustomKeyValue_ItemObject 의
 *                    get_value()를 사용하여 보여줄때는 배열값 선택시에는 해당 Position의  Get_Key()를 사용하여 Key값을 사용한다.
 *
 *                    Array_Set에 있는 Column 값을 동일 하게 CMN_KEY,CMN_VALUE로 맞추어 준다.
 *    사용 xml     :  Spinner Control
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/
public class CustomCombo_Adapter extends ArrayAdapter<CustomKeyValue_ItemObject> {

    //보낸 컨텍스트
    private Context _context;
    // 사용자 정의값 (CustomKeyValue_ItemObject) 사용 (Key,Value) Select Key,Value 로 사용.
    private CustomKeyValue_ItemObject[] _Custom_KeyValue_ItemObject;

    public CustomCombo_Adapter(Context context, int textViewResourceId,
                               CustomKeyValue_ItemObject[] values) {
        super(context, textViewResourceId, values);
        this._context = context;
        this._Custom_KeyValue_ItemObject = values;
    }

    @Override
    public int getCount() {

        return _Custom_KeyValue_ItemObject.length;
    }

    @Override
    public CustomKeyValue_ItemObject getItem(int position) {
        return _Custom_KeyValue_ItemObject[position];
    }

    @Override
    public long getItemId(int position) {

        return position;
    }


    //todo   이것은 스피너의 "passive"상태를위해 사용.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //todo  여기서 동적 TextView를 만들었지 만 각 회 전자 항목에 대한 맞춤 레이아웃을 참조 할 수 있음.
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);

        //todo    values ​​배열 (CustomKeyValue_ItemObject 배열)과 현재 위치를 사용하여 현재 항목을 가져올 수 있습니다.
        //todo   이제는 객체 (CustomKeyValue_ItemObject 클래스)에서 생성 한 각 메소드를 참조 할 수 있습니다.
        label.setText(_Custom_KeyValue_ItemObject[position].get_Value());

        //todo   마지막으로 각 스피너 항목에 대한 동적 (또는 사용자 정의)보기를 반환.
        return label;
    }


    //todo   그리고 선택된 항목이 팝업 될 때..
    //todo   일반적으로 같은 뷰이지만 원하는 경우 사용자 정의 할 수 있습니다.
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(_Custom_KeyValue_ItemObject[position].get_Value());

        return label;
    }
}