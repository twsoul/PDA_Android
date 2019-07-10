package com.acs.app_update.uCommon.CustomKeyValue_ReturnCode;

/*******************************************************************************
 *    Description  : PDA Android CustomKeyValue_Callback
 *    InterFace    : 사용한 클래스에서 이벤트를 받아 처리하기위해 생성
 *    사용 xml     :
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.23		JH_KIM                            CREATE
 *
 *******************************************************************************/

public interface CustomKeyValue_Callback {
    void onSuccess(CustomKeyValue_ItemObject[] result);

    void onFail(String result);
}