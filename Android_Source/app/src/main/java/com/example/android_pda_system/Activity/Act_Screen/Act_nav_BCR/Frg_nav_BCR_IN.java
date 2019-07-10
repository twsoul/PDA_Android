package com.example.android_pda_system.Activity.Act_Screen.Act_nav_BCR;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_pda_system.Activity.ExtendsClass.Frg_MainTitleFragment;
import com.example.android_pda_system.CustomControl.CustomDialog.CustomDateDialog.CustomDateDialog;
import com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_CNT.CRV_BCR_CNT_Adapter;
import com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_CNT.CRV_BCR_CNT_ItemObject;
import com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_CNT.CRV_IF_BCR_CNT_AdapterListener;
import com.example.android_pda_system.CustomControl.CustomSpinControl.CustomComboBox.CustomComboBox;
import com.example.android_pda_system.R;
import com.example.android_pda_system.ScannerReceiver.ScannerReceiver_Listner;
import com.example.android_pda_system.uCommon.CustomParameters.CustomParameters;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.VolleyCallback;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_Connected_ErrorType;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_JsonObject_Get;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 *    Description  : PDA Android 아래 탭 컨트롤 을 사용하기위한 app activity의  sub 화면.
 *    Usage        :
 *    사용 xml     :  act_nav_bcr_chk.xml(현재 class에서 쓰고있는 main화면 xml에서  menu_bcr_chk_bottom.xml 를 사용 ),
 *                      menu_bcr_chk_bottom.xml(메뉴별 항목설정2~3개 2개 사용시 살짝 깨짐.. 3~5개 권장),
 *                      title_menu.xml ,
 *   일반 activity와의 차이점 **** :
 *
 *   context를 부르기 위해
 *
 *   onCreateView메서드 안에서
 *
 *    final View _view = inflater.inflate(R.layout.act_inv_insp, container, false);
 *    를 선언해 준뒤
 *    _view.getconext() 로 가져온다.
 *
 *    타 메서드 안에서는 (getContext()) 로 가져온다.
 *
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.27		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Frg_nav_BCR_IN extends Frg_MainTitleFragment implements CRV_IF_BCR_CNT_AdapterListener, ScannerReceiver_Listner {

    private View _view = null;
    private Context mContext = null;
    ArrayList<CRV_BCR_CNT_ItemObject> listItem; //todo   RecyclerView에서 공통으로 담아 사용할 전역변수

    //region  컨트롤 및 변수 생성 관련
    //todo  날짜 관련 컨트롤
    private CustomDateDialog Dte_Date;
    private Button Btn_Search;
    //todo  수동 입력 컨트롤
    private EditText Txt_BARCODE;
    private Button Btn_Insert;
    //todo  선택 삭제 관련 컨트롤
    private Button btnDelete;

    //todo  콤보박스 관련 컨트롤
    private CustomComboBox ccb_emp;


    private RecyclerView _RecyclerView;
    private LinearLayoutManager _LayoutManager;
    private CRV_BCR_CNT_Adapter _CRV_BCR_CNT_Adapter;


    DatePickerDialog datePickerDialog;


    //endregion

    //todo 바코드 인식할때 작동하는 Method ExtensClass에서 리스너 등록되어있음. Method
    @Override
    public void OnBarcode(String _barcode, String _type, String _module, String _rawdata, int _length, int _decCount) {
        Txt_BARCODE.setText(_barcode);
        Btn_Insert.performClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.act_inv_insp, container, false);

        //현재 Context
        mContext = _view.getContext();

        //region  컨트롤 정의 부분 ///////////////////////////////////////////////////////////////////////


        //todo   위젯에 대한 참조.(날짜 관련)
        Btn_Search = _view.findViewById(R.id.Btn_Search);

        Dte_Date = _view.findViewById(R.id.Dte_Date);


        //todo   위젯에 대한 참조.(선택삭제 관련)
        btnDelete = _view.findViewById(R.id.Btn_Delete);

        //todo   위젯에 대한 참조.(수동 입력 관련)
        Txt_BARCODE = _view.findViewById(R.id.Txt_BARCODE);
        Btn_Insert = _view.findViewById(R.id.Btn_Insert);
        //todo   위젯에 대한 참조.(콤보박스 관련)
        ccb_emp = _view.findViewById(R.id.Ccb_Emp_Name);


        /////////////////////////////////////////////////////////////
        //todo  rectclerView 관련 참조
        _RecyclerView = _view.findViewById(R.id.Recyclerview_List);
        //todo  layoutManager
        _LayoutManager = new LinearLayoutManager(mContext);
        _RecyclerView.setLayoutManager(_LayoutManager);
        //todo  recyclerView 관련 참조 끝

        //todo  recyclerView 최초 설정
        listItem = new ArrayList<>();
        //todo  최초에 listitem을 RecyclerView에 보여줌.
        Set_View(listItem, true);
        ////////////////////////////////////////////////////////////
        //endregion


        //region  컨트롤 Data 정의 부분 ///////////////////////////////////////////////////////////////////////
        //todo    콤보박스 데이터 가져오는 부분 생성 및 초기화.
        //todo   콤보박스 비동기 처리(공통코드 CODE_ID와 구분자 VALUE로 공통 코드 정보 들고옴.)
        //todo   json parsing 된 문자를 가져올 URL 설정.

        ccb_emp.GroupID("EMP_INFO");
        ccb_emp.SetData();

        //endregion


        //region 조회버튼 클릭 시 이벤트
        Btn_Search.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                try {
                    //todo  CRV_BCR_CNT_Json_Get 에서 바코드 정보를 조회 한 후 arr_RCV_BCR에 바코드 정보를 담는다.
                    //todo   json parsing 된 문자를 가져올 URL 설정.
                    CustomParameters param = new CustomParameters("SP_ANDROIDPDA_BCR_L");
                    param.add("@P_BCR_ID", "ALL");

                    final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(mContext, param.ExcuteStr());

                    obj_get.callWCFService(new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONArray[] result) {

                            ArrayList<CRV_BCR_CNT_ItemObject> arr_RCV_BCR = new Gson().fromJson(result[0].toString(), new TypeToken<List<CRV_BCR_CNT_ItemObject>>() {
                            }.getType());
                            //ArrayList<CRV_BCR_CNT_ItemObject> arr_RCV_BCR = new CRV_BCR_CNT_Array_Set().Get_RecyclerView_Data(result[0]);
                            Set_View(arr_RCV_BCR, true);
                        }

                        @Override
                        public void onFail(String result, Volley_Connected_ErrorType ErrorType) {
                            Toast.makeText(mContext,
                                    result,
                                    Toast.LENGTH_LONG).show();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });
        //endregion


        //region 바코트 텍스트 박스 이벤트 (엔터키 이벤트) + xml의 inputType="text"
        Txt_BARCODE.setImeActionLabel("입력", KeyEvent.KEYCODE_ENTER);
        Txt_BARCODE.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                boolean handled = false;

                if (actionId == KeyEvent.KEYCODE_UNKNOWN || actionId == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(mContext,
                            Txt_BARCODE.getText().toString() + "입력",
                            Toast.LENGTH_SHORT).show();
                    // Btn_Insert 버튼 강제 클릭
                    Btn_Insert.performClick();

                    handled = true;
                }
                return handled;
            }
        });
        //endregion


        //region수동 입력 버튼 이벤트
        Btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    // json parsing 된 문자를 가져올 URL 설정.
                    CustomParameters param = new CustomParameters("SP_ANDROIDPDA_BCR_L");
                    param.add("@P_BCR_ID", Txt_BARCODE.getText().toString());
                    //바코드 정보를 가져와 리스트에 뿌려준다.
                    final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(mContext, param.ExcuteStr());

                    obj_get.callWCFService(new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONArray[] result) {

                            ArrayList<CRV_BCR_CNT_ItemObject> arr_list_obj = new Gson().fromJson(result[0].toString(), new TypeToken<List<CRV_BCR_CNT_ItemObject>>() {
                            }.getType());
                            //ArrayList<CRV_BCR_CNT_ItemObject> arr_list_obj = new CRV_BCR_CNT_Array_Set().Get_RecyclerView_Data(result[0]);
                            //현재리스트에 추가
                            Add_View(arr_list_obj);
                        }

                        @Override
                        public void onFail(String result, Volley_Connected_ErrorType ErrorType) {
                            Toast.makeText(mContext,
                                    result,
                                    Toast.LENGTH_LONG).show();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        //endregion
        //region 선택삭제관련 이벤트
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 변수정의 /////////////////////////////
                //체크되지 않은 항목 만 담을 Array생성
                listItem = new ArrayList<>();
                //데이터를 전달받을 변수 생성
                String data = "";

                //Adapter에서 List정보를 가져옴.
                ArrayList<CRV_BCR_CNT_ItemObject> ItemList = (_CRV_BCR_CNT_Adapter)
                        .getItemList();
                // 변수정의 끝 /////////////////////////////
                for (int i = 0; i < ItemList.size(); i++) {
                    // ItemObject에 RecyclerView에 있는  리스트 정보를 담을 변수 생성
                    CRV_BCR_CNT_ItemObject BCR_OBJ = ItemList.get(i);
                    if (BCR_OBJ.isSelected() == true) {
                        data = data + "\n" + BCR_OBJ.get_BCR_ID() + "[" + BCR_OBJ.get_PART_ID() + "]";
                    } else {
                        //선택되지 않은 항목은 새로 추가.
                        listItem.add(BCR_OBJ);
                    }

                }
                //체크박스에 선택되지 않은 listItem을 setAdapter를 사용하여 다시 View
                Set_View(listItem, false);

                // 삭제된것 알림.
                Toast.makeText(mContext,
                        "삭제됨: \n" + data, Toast.LENGTH_LONG)
                        .show();
            }
        });
        //endregion

        //날짜 클릭이벤트 입력
        Dte_Date.ClickedEvent(new CustomDateDialog.CustomDateDialog_GetDateResult_Listener() {
            @Override
            public void OnGetDate(String DateString) {
                Dte_Date.setText(DateString);
            }
        });

        //endregion

        return _view;
    }


    //region CRV_BCR_CNT_Adapter 의 리스트가 이벤트가 발생할 때 리스트Data를 변경하는 메서드 Override
    @Override
    public void onListItemClicked(int position, String Update_String) {


        ArrayList<CRV_BCR_CNT_ItemObject> ItemList = (_CRV_BCR_CNT_Adapter)
                .getItemList();
        //_Custom_RecyclerViewAdapter_adapter 에서 리턴한 POSITION과 STRING 을 가지고
        // 리스트의 POSITION 위치의 BCR_ID 를 업데이트 한다.
        ItemList.get(position).set_WEIGHT_CNT(Update_String);
        ItemList.get(position).setSelected(true);
        Set_View(ItemList, false);

    }
    //endregion


    //region _RecyclerView에 데이터를 업데이트 시켜주는 부분(전체 조회시)
    //Last_Refresh==> 조회 후 마지막으로 스크롤 내릴지 여부체크
    public void Set_View(ArrayList<CRV_BCR_CNT_ItemObject> arr_Set_Object, boolean Last_Refresh) {
        // 화면이 바뀔때 마다  아답터 안의 context 정보 수정해야 함)
        _CRV_BCR_CNT_Adapter = new CRV_BCR_CNT_Adapter(getContext(), arr_Set_Object, true); //실제데이터들 넘김
        _CRV_BCR_CNT_Adapter.setRecyclerView_AdapterListener(this); // onListItemClicked 를 사용하기 위해서 필수로 사용해야함.
        _RecyclerView.setAdapter(_CRV_BCR_CNT_Adapter); //어답터 연결
        if (Last_Refresh) {
            _RecyclerView.scrollToPosition(_CRV_BCR_CNT_Adapter.getItemCount() - 1);// 마지막 줄로 내림
        }
    }
    //endregion

    //region _RecyclerView에 데이터를 업데이트 시켜주는 부분( 1개 추가시 )
    public void Add_View(ArrayList<CRV_BCR_CNT_ItemObject> arr_Set_Object) {
        //Adapter에서 List정보를 가져옴.
        ArrayList<CRV_BCR_CNT_ItemObject> ItemList = (_CRV_BCR_CNT_Adapter).getItemList();

        //1개씩 입력할때 중복체크
        for (int i = 0; i < ItemList.size(); i++) {
            if (ItemList.get(i).get_BCR_ID() == arr_Set_Object.get(i).get_BCR_ID()) {
                try {
                    Toast.makeText(getContext(), "중복입력입니다.", Toast.LENGTH_SHORT).show();

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
        }


        //1.공백을 제외한 길이가 0이 아닐때 ItemList에 추가
        if (Txt_BARCODE.getText().toString().replace(" ", "").length() != 0) {
            //2.ItemList가 null 일때 array에 저장 한 후 (NULL일 경우 에러발생으로 인해 추가)
            if (ItemList.isEmpty()) {
                //수동입력 된 부분 신규입력.
                Set_View(arr_Set_Object, true);
            } else {
                for (int i = 0; i < listItem.size(); i++) {
                    //기존 AdapterArray에 등록되있는 정보에서 Set_Object 추가
                    ItemList.add(arr_Set_Object.get(i));
                }

                ////체크 안되있던 나머지부분 View.
                Set_View(ItemList, true);
            }
            //텍스트 박스 초기화
            Txt_BARCODE.setText("");
            // 마지막 줄로 내림
            _RecyclerView.scrollToPosition(_CRV_BCR_CNT_Adapter.getItemCount() - 1);

        }

    }



    //endregion

}