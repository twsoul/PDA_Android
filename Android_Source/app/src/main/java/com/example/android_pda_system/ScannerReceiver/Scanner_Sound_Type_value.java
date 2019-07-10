package com.example.android_pda_system.ScannerReceiver;

import java.util.Arrays;

/*******************************************************************************
 *    Description  : PDA Android 스캐너 소리타입(enum)을 ARRAY로 가져올 경우 사용할
 *    Usage        :  스캐너 소리타입을 정의해 주려고 했지만
 *                    20190603... 현재 단말기에서 설정 가능하므로 필요성이 떨어져 개발 하지 않음.
 *                    차후 개발시 아래 기능 쓸만할 것 같아 살려둠.
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/


public class Scanner_Sound_Type_value {
    public String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }
}
