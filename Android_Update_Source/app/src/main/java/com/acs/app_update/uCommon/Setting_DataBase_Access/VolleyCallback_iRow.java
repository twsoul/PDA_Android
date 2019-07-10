package com.acs.app_update.uCommon.Setting_DataBase_Access;

import org.json.JSONArray;

/*******************************************************************************
 *    Description  : PDA Android VolleyCallback_iRow 각각의 RecyclerView의 전체 리스트를 각각 처리할 때.
 *  *
 *  *                     행번호를 받아 Return 시켜줌으로써 정확하게 처리한 항목 하지않은 항목을 처리 가능하다.
 *                      인터페이스부분...
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

public interface VolleyCallback_iRow {
    void onSuccess(JSONArray[] result, int iRow);

    void onFail(String result, Volley_Connected_ErrorType ErrorType, int iRow);
}