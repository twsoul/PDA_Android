package com.acs.app_update.uCommon.Setting_DataBase_Access;

import org.json.JSONArray;
import org.json.JSONObject;

/*******************************************************************************
 *    Description  : PDA Android CustomComboBox Array Set
 *    Usage        : Volley_JsonObject_Get.class 에서 return 받은 JSONArray를
 *                   String 형태로 리턴시켜주는 과정.
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

public class Volley_JsonObject_CRUD_ResultGet {
    public String Get_Result_Data(JSONArray param) {
        //리턴받을 변수을 생성..
        String _Key = "";
        try {

            for (int iRow = 0; iRow < param.length(); iRow++) {

                JSONObject finalObject = param.getJSONObject(iRow);

                //가져올 컬럼값들은 각각의 변수에 입력 하고
                // 전달받을 Row_Data 에 대한 정보를 받을 변수 생성
                // 확실히 구분하기 위해.
                _Key = finalObject.getString("RESULT"); // 결과값 Result_컬럼으로 받음.


            }
            return _Key;
        } catch (Exception ex) {
            return _Key;
        }


    }
}
