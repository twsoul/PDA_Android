package com.example.android_pda_system.Activity.Act_Main_Sub_Menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android_pda_system.Activity.Act_Screen.Act_Inv.Act_InvIn;
import com.example.android_pda_system.Activity.Act_Screen.Act_Inv.Act_InvInsp;
import com.example.android_pda_system.Activity.Act_Screen.Act_Inv.Act_InvOut;
import com.example.android_pda_system.Activity.ExtendsClass.Act_MainTitleActivity;
import com.example.android_pda_system.CustomControl.CustomRadioGroup.CustomRadioGroupBox;
import com.example.android_pda_system.R;

public class Act_Inv extends Act_MainTitleActivity implements View.OnClickListener {
    private Context mContext = Act_Inv.this;

    private Button Btn_InvIn;
    private Button Btn_InvOut;
    private Button Btn_InvInsp;
    private CustomRadioGroupBox Rdo_Plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inv);

        Btn_InvIn = findViewById(R.id.Btn_InvIn);
        Btn_InvIn.setOnClickListener(this);

        Btn_InvOut = findViewById(R.id.Btn_InvOut);
        Btn_InvOut.setOnClickListener(this);

        Btn_InvInsp = findViewById(R.id.Btn_InvInsp);
        Btn_InvInsp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent _intent;
        switch (v.getId()) {
            //todo 재고입고
            case R.id.Btn_InvIn:
                _intent = new Intent(mContext, Act_InvIn.class);
                startActivity(_intent);
                break;
            //todo 재고출고
            case R.id.Btn_InvOut:
                _intent = new Intent(mContext, Act_InvOut.class);
                startActivity(_intent);
                break;
            //todo 재고실사
            case R.id.Btn_InvInsp:
                _intent = new Intent(mContext, Act_InvInsp.class);
                startActivity(_intent);
                break;


        }

    }

    // 숨기기 아이템 숨기기.
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem Hide = menu.findItem(R.id.btn_Hide);
        Hide.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    //todo   상단 버튼 클릭했을때 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo   Handle presses on the action bar items
        switch (item.getItemId()) {
//            case R.id.btn_Hide:
//                //상단 조회조건 들 layout 숨기기
//                final LinearLayout _Lay_Search = findViewById(R.id.Lay_Search);
//                if (_Lay_Search.getVisibility() == View.GONE) {
//                    _Lay_Search.setVisibility(View.VISIBLE);
//                } else {
//                    _Lay_Search.setVisibility(View.GONE);
//                }
//                return true;
            case R.id.btn_back:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
