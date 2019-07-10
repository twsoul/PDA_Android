package com.acs.app_update.uCommon.Setting_DataBase_Access;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONArray;

/*******************************************************************************
 *    Description  : PDA Android Volley_JsonObject_Get
 *    InterFace    : 고정 URL을 던져서 map에 프로시저명, 파라미터를 모두 입력한다..
 *                   이후 VolleyCallback 인터페이스를 통해
 *                   사용한 activity에서 JSONArray를 Return 받는다
 *    사용 xml     :
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.23		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Volley_JsonObject_Get {
    static Context _Context;
    static String _Param;
    static String err_str = null;

    public Volley_JsonObject_Get(Context context, String param) {
        this._Context = context;
        this._Param = param;



    }


    // todo 일반적인 단일 쿼리를 수행 할때 사용하는 WCFCallService
    public void callWCFService(final VolleyCallback callback) {
        try {
            //TODO ※FOR문을 돌릴때는 다른자원을 할당받을 수 있도록 반드시 SLEEP을 써줘야 한다.※※※※※※※※※※※※※※※※※※※※
            Thread.sleep(50);
            //TODO ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※


            Volley_Call Vol_Call = new Volley_Call(Request.Method.POST,_Context, _Param);
            Vol_Call.Volley(new VolleyCallback() {
                @Override
                public void onSuccess(JSONArray[] result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onFail(String result, Volley_Connected_ErrorType Error_Type) {
                    callback.onFail(err_str, Volley_Connected_ErrorType.SqlError);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //todo RecyclerView에서 데이터를 for문 혹은 여러개의 데이터를 저장할때 행 번호(iRow) 값을 받는다. 이후 명확한 row 의 구분을 지어주기 위해 사용된다.
    public void callWCFService(final VolleyCallback_iRow callback, final int arr_iRow) {
        try {
            //TODO ※FOR문을 돌릴때는 다른자원을 할당받을 수 있도록 반드시 SLEEP을 써줘야 한다.※※※※※※※※※※※※※※※※※※※※
            Thread.sleep(50);
            //TODO ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※


            //todo 전달 받은 값들을 웹서비스에 전달하여 결과값을 받는다. 한다.
            Volley_Call Vol_Call = new Volley_Call(Request.Method.POST,_Context, _Param);
            Vol_Call.Volley(new VolleyCallback() {
                @Override
                public void onSuccess(JSONArray[] result) {
                    callback.onSuccess(result, arr_iRow);
                }

                @Override
                public void onFail(String result, Volley_Connected_ErrorType Error_Type) {
                    callback.onFail(err_str, Volley_Connected_ErrorType.SqlError, arr_iRow);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//region 2019-06-20 에 Volley_Call.Class로 웹서비스 요청을 동일하게 처리함...
    //region todo 일반적인 단일 쿼리를 수행 할때 사용하는 WCFCallService
//    public JSONArray[] callWCFService(final VolleyCallback callback) {
//
//        StringRequest _PostRequest = new StringRequest(Request.Method.POST, AppPreference.DBConnectionUrl(_Context), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject parentObject = null;
//                try {
//                    parentObject = new JSONObject(response);
//
//                    /////////////// 데이터 가져오는 부분//////////////////////
//                        //todo 처음에 1로 초기화 했기때문에 여러개의 경우 입력한 개수 만큼 새로 초기화를 해준다.
//                        arr_Result=new JSONArray[arr_Table.size()];
//                        for (int TableRow =0 ; TableRow<arr_Table.size();TableRow++){
//                            arr_Result[TableRow] = parentObject.getJSONArray(arr_Table.get(TableRow));
//
//
//                            for (int iRow = 0; iRow < arr_Result[TableRow].length(); iRow++) {
//
//                                JSONObject finalObject = arr_Result[TableRow].getJSONObject(iRow);
//
//                                //todo  가져올 컬럼값들은 각각의 변수에 입력 하고
//                                // 전달받을 Row_Data 에 대한 정보를 받을 변수 생성
//                                // 확실히 구분하기 위해.
//                                try {
//                                    err_str=null;
//                                        err_str = finalObject.getString("err_Result"); // 에러발생시 WEBSERVICE에서 "err_Result"컬럼으로 하여금 에러메세지 가져옴.
//
//                                } catch (Exception ex) {
//
//                                }
//                            }
//                            //todo  Interface 완료 부분
//                            if (err_str == null) {
//                                //todo  Interface 완료 부분
//                                callback.onSuccess(arr_Result);
//                            } else {
//                                //todo  sql에러 발생시.
//                                callback.onFail(err_str, Volley_Connected_ErrorType.SqlError);
//                            }
//
//                        }
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
//                callback.onFail(new Volley_Connected_Error().GetError(error),Volley_Connected_ErrorType.NetWorkError);
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
//
//        return arr_Result;
//    }
    //endregion

    //region todo RecyclerView에서 데이터를 for문 혹은 여러개의 데이터를 저장할때 행 번호(iRow) 값을 받는다. 이후 명확한 row 의 구분을 지어주기 위해 사용된다.
//    public JSONArray[] callWCFService(final VolleyCallback_iRow callback, final int arr_iRow) {
//
//        //todo TEXT 만들어서 DB 정보 가져왔는데 수정함...(현재 grobal변수 혹은 환경설정 값)
//
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        StringRequest _PostRequest = new StringRequest(Request.Method.POST, AppPreference.DBConnectionUrl(_Context), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject parentObject = null;
//                try {
//                    parentObject = new JSONObject(response);
//
//                    /////////////// 데이터 가져오는 부분//////////////////////
//                    //todo arr_Table(json)에서 가져올 테이블을정의하지 않았을 경우.
//                    if (arr_Table.size()==0) {
//
//                        arr_Result[0] = parentObject.getJSONArray("Table"); // todo Default 테이블 이름이 "Table" 이다..
//                        for (int iRow = 0; iRow < arr_Result[0].length(); iRow++) {
//
//                            JSONObject finalObject = arr_Result[0].getJSONObject(iRow);
//
//                            //todo 가져올 컬럼값들은 각각의 변수에 입력 하고
//                            // 전달받을 Row_Data 에 대한 정보를 받을 변수 생성
//                            // 확실히 구분하기 위해.
//                            err_str=null;
//                            try {
//                                err_str = finalObject.getString("err_Result"); // 에러발생시 WEBSERVICE에서 "err_Result"컬럼으로 하여금 에러메세지 가져옴.
//                            } catch (Exception ex) {
//
//                            }
//                        }
//                        //todo 에러가 있을때 구분...
//                        if (err_str == null) {
//                            //todo Interface 완료 부분
//                            callback.onSuccess(arr_Result, arr_iRow);
//                        } else {
//                            callback.onFail(err_str, Volley_Connected_ErrorType.SqlError, arr_iRow);
//                        }
//                    }
//                    //todo arr_Table(json)에서 가져올 테이블을 정의했을 경우
//                    else {
//                        //todo 처음에 1로 초기화 했기때문에 여러개의 경우 입력한 개수 만큼 새로 초기화를 해준다.
//                        arr_Result=new JSONArray[arr_Table.size()];
//                        for (int TableRow =0 ; TableRow<arr_Table.size();TableRow++){
//                            arr_Result[TableRow] = parentObject.getJSONArray(arr_Table.get(TableRow));
//
//
//                            for (int iRow = 0; iRow < arr_Result[TableRow].length(); iRow++) {
//
//                                JSONObject finalObject = arr_Result[TableRow].getJSONObject(iRow);
//
//                                //todo  가져올 컬럼값들은 각각의 변수에 입력 하고
//                                // 전달받을 Row_Data 에 대한 정보를 받을 변수 생성
//                                // 확실히 구분하기 위해.
//                                try {
//                                    if(err_str==null) {
//                                        err_str = finalObject.getString("err_Result"); // 에러발생시 WEBSERVICE에서 "err_Result"컬럼으로 하여금 에러메세지 가져옴.
//                                    }
//                                } catch (Exception ex) {
//
//                                }
//                            }
//                            //todo  Interface 완료 부분
//                            if (err_str == null) {
//                                //todo  Interface 완료 부분
//                                callback.onSuccess(arr_Result,arr_iRow);
//                            } else {
//                                //todo  sql에러 발생시.
//                                callback.onFail(err_str, Volley_Connected_ErrorType.SqlError,arr_iRow);
//                            }
//
//                        }
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //todo Interface 실패 부분
//                callback.onFail(new Volley_Connected_Error().GetError(error),Volley_Connected_ErrorType.NetWorkError, arr_iRow);
//
//                Log.d("Error", error.toString());
//            }
//        }) {
//            @Override
//            public Map<String, String> getParams() {
//
//                //todo Map형태로 리턴해줘야 하기때문에 전달받은 프로시저명과 파라미터를 value에 넣는다.
//                Map<String, String> params = new HashMap<>();
//                params.put("acs_parameters", _Param);
//
//
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(this._Context);
//
//        //todo 타임아웃의 경우...
//        _PostRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
//
//                7000, //todo 7초 딜레이 계산
//
//                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//
//                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        queue.add(_PostRequest);
//
//        return arr_Result;
//    }

    //endregion
    // endregion


}
