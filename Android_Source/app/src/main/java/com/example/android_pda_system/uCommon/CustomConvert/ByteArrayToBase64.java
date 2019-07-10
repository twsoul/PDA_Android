package com.example.android_pda_system.uCommon.CustomConvert;

import android.util.Base64;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************************************************************
 *    Description  : PDA Android BitmapToByteArray
 *    Usage        :  byte[] <-> base64(file,image) 변환하는 클래스
 *
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.06.19		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class ByteArrayToBase64 {

    public String Encode(byte[] str) {

        String encoded = Base64.encodeToString(str, Base64.DEFAULT);
        return encoded;

    }

    public byte[] Decode(String base64_str) {

        byte[] decoded = Base64.decode(base64_str, Base64.DEFAULT);

        return decoded;

    }

    public boolean CheckBase64(String check_str) {
        Pattern base64Pattern = Pattern.compile("[A-Za-z0-9\\\\/=]+");

        Matcher base64Matcher = base64Pattern.matcher(check_str);


        return base64Matcher.find();


    }
}
