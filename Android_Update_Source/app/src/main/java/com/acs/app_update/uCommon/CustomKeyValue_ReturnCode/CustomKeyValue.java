package com.acs.app_update.uCommon.CustomKeyValue_ReturnCode;

import android.content.Context;
import android.widget.Toast;

import com.acs.app_update.uCommon.CustomParameters.CustomParameters;
import com.acs.app_update.uCommon.Setting_DataBase_Access.VolleyCallback;
import com.acs.app_update.uCommon.Setting_DataBase_Access.Volley_Connected_ErrorType;
import com.acs.app_update.uCommon.Setting_DataBase_Access.Volley_JsonObject_Get;
import com.acs.app_update.uConfig.Grobal;

import org.json.JSONArray;

/*******************************************************************************
 *    Description  : PDA Android 콤보박스 혹은 공통코드 등,, Key,Value 성 결과값을 필요로하는 기능을 공통으로 사용하는 메서드
 *    Usage        : A_WCF_SP_SSPPDA_Common_Key_Value_L( Test 당시)
 * @P_CODE_ID 는 가져올 group_Id를 입력하고 (atm과 동일)
 *    그 이외의 value 값을 추가적으로 받을 컬럼 (임시)
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class CustomKeyValue {
    public Context _context;
    public CustomKeyValue_ItemObject[] _Arr_Result;

    public void SetValue(final CustomKeyValue_Callback callback, Context context, String CODE_ID, String VALUE) {

        this._context = context;

        CustomParameters param = new CustomParameters(Grobal._Common_KeyValue_SP);

        param.add("@P_CODE_ID", CODE_ID);
        param.add("@P_VALUE", VALUE);

        final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(_context, param.ExcuteStr());

        obj_get.callWCFService(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray[] result) {

                _Arr_Result = new CustomKeyValue_Array_Set().Get_KeyValue_Data(result[0]);
                callback.onSuccess(_Arr_Result);
                //todo  EMP 조회 콤보박스(Spinner)의 아답터를 생성하고 ccb_Name에 Set
            }

            @Override
            public void onFail(String result, Volley_Connected_ErrorType ErrorType) {

                Toast.makeText(_context, result, Toast.LENGTH_LONG);

                callback.onFail(result);
            }

        });


    }
}
