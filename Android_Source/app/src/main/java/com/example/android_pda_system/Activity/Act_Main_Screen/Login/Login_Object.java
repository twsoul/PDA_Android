package com.example.android_pda_system.Activity.Act_Main_Screen.Login;

public class Login_Object {
    /*******************************************************************************
     *    Description  : PDA Android Login_Object
     *    Usage        : 로그인 정보 저장할 object
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

    public String EMP_ID;
    public String EMP_NAME;
    public String EMP_NO;

    public Login_Object(String EMP_ID, String EMP_NAME, String EMP_NO) {
        this.EMP_ID = EMP_ID;
        this.EMP_NAME = EMP_NAME;
        this.EMP_NO = EMP_NO;
    }

    public String get_EMP_ID() {
        return EMP_ID;
    }

    public void set_EMP_ID(String _EMP_ID) {
        this.EMP_ID = _EMP_ID;
    }

    public String get_EMP_NAME() {
        return EMP_NAME;
    }

    public void set_EMP_NAME(String _EMP_NAME) {
        this.EMP_NAME = _EMP_NAME;
    }

    public String get_EMP_NO() {
        return EMP_NO;
    }

    public void set_EMP_NO(String _EMP_NO) {
        this.EMP_NO = _EMP_NO;
    }


}
