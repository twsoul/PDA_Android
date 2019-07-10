package com.example.android_pda_system.uCommon.CustomKeyValue_ReturnCode;

/*******************************************************************************
 *    Description  : PDA Android CustomRecyclerView ItemObject
 *    Usage        : Spinner Control에서 사용 될 멤버변수 Object 를 정의해준다.
 *
 *                    Key,Value성 컨트롤을 사용할 것이기 때문에
 *
 * ////// SP에서 조회 시 컬럼값을 KEY,VALUE 로 alias 해줘야 한다.
 *
 *    사용 xml     :  act_bcr_chkl >>> Spinner Control
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class CustomKeyValue_ItemObject {

    //todo  json data와 동일한 이름
    private String _Key;
    private String _Value;


    public CustomKeyValue_ItemObject() {
        this._Key = "";
        this._Value = "";
    }


    public String get_Key() {
        return _Key;
    }

    public void set_Key(String Key) {

        this._Key = Key;
    }

    public String get_Value() {
        return _Value;
    }

    public void set_Value(String Value) {
        this._Value = Value;
    }


}
