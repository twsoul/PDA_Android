package com.example.android_pda_system.uCommon.Setting_DataBase_Access;

import org.json.JSONArray;

/*******************************************************************************
 *    Description  : PDA Android VolleyCallback
 *    InterFace    : POST로 받는동안 짧은시간의 딜레이가 생기는데 성공 이벤트에 맞춰서 인터페이스를 하여
 *                   사용한 ACTIVITY에서 완료된 이벤트를 받아 처리하기위해 생성
 *    사용 xml     :
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.23		JH_KIM                            CREATE
 *
 *******************************************************************************/

public interface VolleyCallback {
    void onSuccess(JSONArray[] result);

    void onFail(String result, Volley_Connected_ErrorType Error_Type);
}