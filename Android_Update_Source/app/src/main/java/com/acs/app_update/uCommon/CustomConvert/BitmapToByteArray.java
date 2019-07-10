package com.acs.app_update.uCommon.CustomConvert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/*******************************************************************************
 *    Description  : PDA Android BitmapToByteArray
 *    Usage        :  byte[] <-> bitmap(이미지) 변환하는 클래스
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

public class BitmapToByteArray {

    public byte[] BitmaptoByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public Bitmap ByteArrayToBitmap(byte[] byteArray) {
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize=2;

        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //bitmap= BitmapFactory.decodeByteArray(byteArray,0,byteArray.length,option);

        byteArray = null;
        return bitmap;

    }

}
