package com.example.android_pda_system.uConfig.Device_Info;


import java.util.ArrayList;

/*******************************************************************************
 *    Description  : PDA Android divice uuid
 *    Usage        :  pda 고유값을 받아와서 등록된 번호에 대해서만 처리해주기 위함.
 *
 *                     uuid 를 가져올때 1순위 - 기기uuid
 *                                      2순위 - 번호가 부여되어있을 때 uuid
 *
 *                     로그인 성공시에는 log에만 uuid 가 남고
 *                     로그인 실패시에는 USP_ANDROIDPDA_UUID_S 를 통해 저장
 *
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.06.03		JH_KIM                            CREATE
 *
 *******************************************************************************/


public class Device_GetCheck {

    public ArrayList<String> Get_Device_Info() {
        ArrayList<String> Serial = new ArrayList<>();

        Serial.add("F5B92742-DF33-334D-88A7-7DCCCF49DB4C");   // M3 개발용 uuid
        Serial.add("21e767e1-4b8e-30f8-a32e-18cf6e938b48");   // acs 김재현 폰
        Serial.add("137E4B8D-FA55-3E98-A55C-A0191CA1D6B0");   // acs 김재현 가상
        Serial.add("DEE2E540-EE60-3B1D-84B9-9B18B284434D");   // acs 김재현 가상
        Serial.add("63366355-9DBF-34AF-A6A0-5C0EE3EF1623");   // acs 서태우 가상
        Serial.add("2E1404C5-8485-35B7-8276-140A8C76D805");   // acs 서태우 가상


        Serial.add("ACEDB3D6-F9C7-36BF-BAF9-406DC7CE915E");   // acs 김재현 가상

        return Serial;
    }
}
