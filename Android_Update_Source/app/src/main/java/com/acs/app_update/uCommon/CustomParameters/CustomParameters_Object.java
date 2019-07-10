package com.acs.app_update.uCommon.CustomParameters;

public class CustomParameters_Object {

    /*******************************************************************************
     *    Description  : PDA Android CustomParameters_Object
     *    Usage        : CustomParameters에서 add("","") 형식으로 받을때
     *                     Key,Value Object 정의
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

    private String _Param;
    private String _Value;

    public CustomParameters_Object(String Param, String Value) {
        this._Param = Param;
        this._Value = Value;
    }

    public String get_Param() {
        return _Param;
    }

    public void set_Param(String _Param) {
        this._Param = _Param;
    }

    public String get_Value() {
        return _Value;
    }

    public void set_Value(String _Value) {
        this._Value = _Value;
    }


}
