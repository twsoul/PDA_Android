package com.acs.app_update.uCommon.Setting_DataBase_Access;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/*******************************************************************************
 *    Description  : PDA Android Volley_Connected_Error
 *    Usage        :  에러발생시 구분하기 위한 클래스
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
public class Volley_Connected_Error {
    public String GetError(VolleyError error) {

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            Log.d("TimeoutError", "연결상태를 다시 확인해주세요.");
            return "연결상태를 다시 확인해주세요.";

        } else if (error instanceof AuthFailureError) {

            Log.d("AuthFailureError", "인증에러");
            return "인증에러";

        } else if (error instanceof ServerError) {

            Log.d("ServerError", "서버에서 에러가 발생하였습니다.\n 관리자에게 문의주세요");
            return "서버에서 에러가 발생하였습니다.\n 관리자에게 문의주세요";

        } else if (error instanceof NetworkError) {

            Log.d("NetworkError", "네트워크 에러 입니다.");
            return "네트워크 에러 입니다.";

        } else if (error instanceof ParseError) {

            Log.d("ParseError",  error.toString());
            return error.toString();

        } else {

            Log.d("noneerror", "알수없는 오류 입니다.");
            return "알수없는 오류 입니다.";

        }
    }

}
