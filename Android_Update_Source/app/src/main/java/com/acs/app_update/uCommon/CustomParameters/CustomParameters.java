package com.acs.app_update.uCommon.CustomParameters;


import com.acs.app_update.uCommon.Setting_EncryptAndDescrypt.EncryptAndDescrypt;

import java.util.ArrayList;

/*******************************************************************************
 *    Description  : PDA Android DBConnect 이후 프로시저명+파라미터를 url 뒤에 붙여주는 부분
 *    Usage        : CustomParameters Param= new CustomParameters(프로시저 명)
 *                     Param.add("파라미터 명","Value");
 *                     Param.add("파라미터 명","Value");
 *                     Param.add("파라미터 명","Value");
 *                     형식으로 작성 이후
 *                     String Url = Param.Json_Excute();
 *
 *    사용 xml     :
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.22		JH_KIM                            CREATE
 *
 *******************************************************************************/


public class CustomParameters {
    private String _Select_Procedure;
    private ArrayList<CustomParameters_Object> arr_PARAM = new ArrayList<>();

    //todo   최초설정시 프로시저 명을 입력한다.
    public CustomParameters(String Select_Procedure) {

        _Select_Procedure = Select_Procedure;


    }

    public void add(String Param, String Value) {
        arr_PARAM.add(new CustomParameters_Object(Param, Value));
    }

    //region   volley 사용시 map<String,String>에 acs_parameters 에 value 로 넘겨받을 String 생성하여 return;
    public String ExcuteStr() {
        String _DataConnect_Parameters = "";
        EncryptAndDescrypt AES = new EncryptAndDescrypt();
       /*
       &nsdp => key,value사이를 구분하는 구분자
       &nsdv => 각각 파라미터,Procedure를 구분하는 구분자
        */
        try {
            //todo   파라미터 없을때 처리
            if (arr_PARAM.size() == 0) {
                _DataConnect_Parameters = _DataConnect_Parameters + "Procedure_Name" + "&nsdp" + _Select_Procedure;
            }

            //todo  파라미터 잆을때 처리
            for (int iRow = 0; iRow < arr_PARAM.size(); iRow++) {
                //todo  첫번째 Row 일때 뒤에 프로시저명을 붙여준다.
                if (iRow == 0) {
                    _DataConnect_Parameters = _DataConnect_Parameters + "Procedure_Name" + "&nsdp" + _Select_Procedure + "&nsdv";
                }
                //todo   마지막 검색일때는 뒤에 || 를 제외한다.
                if (iRow == arr_PARAM.size() - 1) {
                    _DataConnect_Parameters = _DataConnect_Parameters + arr_PARAM.get(iRow).get_Param() + "&nsdp" + arr_PARAM.get(iRow).get_Value();
                } else {
                    _DataConnect_Parameters = _DataConnect_Parameters + arr_PARAM.get(iRow).get_Param() + "&nsdp" + arr_PARAM.get(iRow).get_Value() + "&nsdv";
                }
            }
            return AES.encrypt(_DataConnect_Parameters);
        } catch (Exception ex) {
            return "Error";
        }
    }
    //endregion


}
