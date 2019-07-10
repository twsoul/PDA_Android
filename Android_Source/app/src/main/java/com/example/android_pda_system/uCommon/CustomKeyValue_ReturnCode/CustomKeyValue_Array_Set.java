package com.example.android_pda_system.uCommon.CustomKeyValue_ReturnCode;

import org.json.JSONArray;
import org.json.JSONObject;

/*******************************************************************************
 *    Description  : PDA Android CustomComboBox Array Set
 *    Usage        : Volley_JsonObject_Get.class 에서 return 받은 JSONArray를
 *                   Customobject 형태로 리턴시켜주는 과정.
 *
 *   CustomKeyValue_ItemObject 와 ID값을 맞춰주면 알아보기 편하다.
 *    사용 xml     :  item_bcr_detail.xml
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.24		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class CustomKeyValue_Array_Set {
    public CustomKeyValue_ItemObject[] Get_KeyValue_Data(JSONArray param) {
        //todo  리턴받을 배열을 생성..
        CustomKeyValue_ItemObject[] arr_ComboBox_Object = null;

        try {
            arr_ComboBox_Object = new CustomKeyValue_ItemObject[param.length()];


            for (int iRow = 0; iRow < param.length(); iRow++) {

                JSONObject finalObject = param.getJSONObject(iRow);

                //todo  가져올 컬럼값들은 각각의 변수에 입력 하고
                // 전달받을 Row_Data 에 대한 정보를 받을 변수 생성
                // 확실히 구분하기 위해.
                String _Key = finalObject.getString("CMN_KEY"); // 임시로 "사원번호"로 지정 차후 "Key" 변경예정
                String _Value = finalObject.getString("CMN_VALUE"); // 임시로 "@@사원"로 지정 차후 "Value" 변경예정


                //todo  리턴할 CustomKeyValue_ItemObject[] 에 저장한다.
                arr_ComboBox_Object[iRow] = new CustomKeyValue_ItemObject();
                arr_ComboBox_Object[iRow].set_Key(_Key);
                arr_ComboBox_Object[iRow].set_Value(_Value);


            }
            return arr_ComboBox_Object;
        } catch (Exception ex) {
            return arr_ComboBox_Object;
        }


    }
}
