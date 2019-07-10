package com.example.android_pda_system.Activity.Act_Main_Screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android_pda_system.R;

/*******************************************************************************
 *    Description  : PDA Android 등록된 uuid 아닐경우 실행되는 화면
 *    Usage        :
 *    사용 xml     :  act_main_fail.xml
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Act_Main_Fail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_fail);
    }
}
