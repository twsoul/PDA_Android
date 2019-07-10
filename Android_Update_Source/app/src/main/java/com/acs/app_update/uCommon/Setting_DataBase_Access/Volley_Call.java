package com.acs.app_update.uCommon.Setting_DataBase_Access;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.acs.app_update.uConfig.Preference.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Volley_Call {

    static Context _Context;
    static String _Param;
    static JSONArray[] _arr_Result = new JSONArray[1];// todo Default 값으로 1을 준다. 없을경우 는 1개의 JSONArray를 담아야 하기 때문..
    static String _err_str = null;

    static int _Request;

    public Volley_Call(int request,Context context,  String param) {
        this._Context = context;
        this._Param = param;
        this._Request=request;

    }

    // todo 실질적으로 통신하는 부분.
    public void Volley(final VolleyCallback callback) {
        StringRequest _PostRequest = new StringRequest(_Request, AppPreference.DBConnectionUrl(_Context), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response);


                    /////////////// 데이터 가져오는 부분//////////////////////
                    //todo table 개수를 들고온다 name().length();

                    _arr_Result = new JSONArray[parentObject.names().length()];
                    for (int TableRow = 0; TableRow < parentObject.names().length(); TableRow++) {
                        _arr_Result[TableRow] = parentObject.getJSONArray(parentObject.names().getString(TableRow));


                        for (int iRow = 0; iRow < _arr_Result[TableRow].length(); iRow++) {

                            JSONObject finalObject = _arr_Result[TableRow].getJSONObject(iRow);
                            //todo  가져올 컬럼값들은 각각의 변수에 입력 하고
                            // 전달받을 Row_Data 에 대한 정보를 받을 변수 생성
                            // 확실히 구분하기 위해.
                            try {
                                _err_str = null;
                                _err_str = finalObject.getString("err_Result"); // 에러발생시 WEBSERVICE에서 "err_Result"컬럼으로 하여금 에러메세지 가져옴.

                            } catch (Exception ex) {
                            }
                        }
                    }
                    //todo  Interface 완료 부분
                    if (_err_str == null) {
                        //todo  Interface 완료 부분
                        callback.onSuccess(_arr_Result);
                    } else {
                        //todo  sql에러 발생시.
                        callback.onFail(_err_str, Volley_Connected_ErrorType.SqlError);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //todo  Interface 실패 부분
                // 네트워크에러 발생시.
                callback.onFail(new Volley_Connected_Error().GetError(error), Volley_Connected_ErrorType.NetWorkError);

                Log.d("Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getParams() {

                //todo   Map형태로 리턴해줘야 하기때문에 전달받은 프로시저명과 파라미터를 value에 넣는다.
                Map<String, String> params = new HashMap<>();
                params.put("acs_parameters", _Param);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this._Context);

        //todo   타임아웃의 경우...
        _PostRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(

                7000, //todo  7초 딜레이 계산

                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(_PostRequest);
    }

//    // todo 실질적으로 통신하는 부분.
//    public void Volley(final VolleyCallback callback) {
//        StringRequest _PostRequest = new StringRequest(_Request, AppPreference.DBConnectionUrl(_Context), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject parentObject = null;
//                try {
//                    parentObject = new JSONObject(response);
//                    Iterator itr = parentObject.keys();
//
//                    /////////////// 데이터 가져오는 부분//////////////////////
//                    //todo 처음에 1로 초기화 했기때문에 여러개의 경우 입력한 개수 만큼 새로 초기화를 해준다.
//
//                    _arr_Result = new JSONArray[parentObject.length()];
//                    for (int TableRow = 0; TableRow < parentObject.length(); TableRow++) {
//                        _arr_Result[TableRow] = parentObject.getJSONArray(_arr_Table.get(TableRow));
//
//
//                        for (int iRow = 0; iRow < _arr_Result[TableRow].length(); iRow++) {
//
//                            JSONObject finalObject = _arr_Result[TableRow].getJSONObject(iRow);
//
//                            //todo  가져올 컬럼값들은 각각의 변수에 입력 하고
//                            // 전달받을 Row_Data 에 대한 정보를 받을 변수 생성
//                            // 확실히 구분하기 위해.
//                            try {
//                                _err_str = null;
//                                _err_str = finalObject.getString("err_Result"); // 에러발생시 WEBSERVICE에서 "err_Result"컬럼으로 하여금 에러메세지 가져옴.
//
//                            } catch (Exception ex) {
//                            }
//                        }
//                    }
//                    //todo  Interface 완료 부분
//                    if (_err_str == null) {
//                        //todo  Interface 완료 부분
//                        callback.onSuccess(_arr_Result);
//                    } else {
//                        //todo  sql에러 발생시.
//                        callback.onFail(_err_str, Volley_Connected_ErrorType.SqlError);
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //todo  Interface 실패 부분
//                // 네트워크에러 발생시.
//                callback.onFail(new Volley_Connected_Error().GetError(error), Volley_Connected_ErrorType.NetWorkError);
//
//                Log.d("Error", error.toString());
//            }
//        }) {
//            @Override
//            public Map<String, String> getParams() {
//
//                //todo   Map형태로 리턴해줘야 하기때문에 전달받은 프로시저명과 파라미터를 value에 넣는다.
//                Map<String, String> params = new HashMap<>();
//                params.put("acs_parameters", _Param);
//
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(this._Context);
//
//        //todo   타임아웃의 경우...
//        _PostRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
//
//                7000, //todo  7초 딜레이 계산
//
//                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//
//                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        queue.add(_PostRequest);
//    }
}
