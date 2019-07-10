package com.example.android_pda_system.Activity.Act_Screen.Act_Inv;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_pda_system.Activity.ExtendsClass.Act_MainTitleActivity;
import com.example.android_pda_system.CustomControl.CustomDialog.CustomDateDialog.CustomDateDialog;
import com.example.android_pda_system.CustomControl.CustomDialog.Dlg_MessageBox_YN.Dlg_IF_MessageBox;
import com.example.android_pda_system.CustomControl.CustomRadioGroup.CustomRadioGroupBox;
import com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_Info.CRV_BCR_Info_Adapter;
import com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_Info.CRV_BCR_Info_ItemObject;
import com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_Info.CRV_IF_BCR_Info_AdapterListener;
import com.example.android_pda_system.CustomControl.CustomSpinControl.CustomComboBox.CustomComboBox;
import com.example.android_pda_system.R;
import com.example.android_pda_system.ScannerReceiver.ScannerReceiver_Listner;
import com.example.android_pda_system.uCommon.CustomParameters.CustomParameters;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.VolleyCallback;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.VolleyCallback_iRow;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_Connected_ErrorType;
import com.example.android_pda_system.uCommon.Setting_DataBase_Access.Volley_JsonObject_Get;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 *    Description  : PDA Android 바코드 조회화면
 *    Usage        : CRV_BCR_CNT_Adapter (RecyclerView)사용
 *                    해당 CRV_BCR_CNT_Adapter 내에 Set_View 에서 true,False로 Dialog창(Dlg_CNT_UPDATE) 띄울지 구분
 *    사용 xml     :  act_inv_insp.xmll******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

// RecyclerViewAdapter에서 클릭이벤트로 사용할 onListItemClicked를 Override하기 위해 implemets함.
public class Act_InvIn extends Act_MainTitleActivity implements CRV_IF_BCR_Info_AdapterListener {
    //현재 Context
    private Context mContext = Act_InvIn.this;

    //region  컨트롤 및 변수 생성 관련
    //날짜 관련 컨트롤
    private CustomDateDialog Dte_Date;
    private Button Btn_Search;

    //선택 삭제 관련 컨트롤
    private Button btnDelete;
    //수동입력 버튼 관련 컨트롤
    private TextView Txt_BARCODE;
    private Button Btn_Insert;
    //저장 버튼 관련 컨트롤
    private Button btn_Save;

    //콤보박스 관련 컨트롤
    private CustomComboBox ccb_emp;

    //라디오그룹박스 관련 컨트롤
    private CustomRadioGroupBox Rdo_Plant;


    private RecyclerView _RecyclerView;
    private LinearLayoutManager _LayoutManager;
    private CRV_BCR_Info_Adapter _CRV_BCR_Info_Adapter;

    //endregion

    //region 상단 타이틀 바 다른 레이아웃 으로 메뉴레이아웃을 res/title_menu/title_menu 참조 하여 가져옴.

    // 상단 버튼 클릭했을때 이벤트
    //Act_MainTitleActivity 에서 뒤로가기 버튼에 대한 이벤트는 존재하나. 숨기기 버튼의 경우 가릴수 있는 layout.id 가 다르기 때문에 따로 지정.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.btn_Hide:
                //상단 조회조건 들 layout 숨기기 (어떤 레이아웃인지 알기위해 직접 작성)
                final LinearLayout _Lay_Search = findViewById(R.id.Lay_Search);
                if (_Lay_Search.getVisibility() == View.GONE) {
                    _Lay_Search.setVisibility(View.VISIBLE);
                } else {
                    _Lay_Search.setVisibility(View.GONE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion 상단 타이틀 바 다른 레이아웃


    //todo 바코드 인식할때 작동하는 Method ExtensClass에서 리스너 등록되어있음. Method
    @Override
    public void OnBarcode(String _barcode, String _type, String _module, String _rawdata, int _length, int _decCount) {
        //Txt_BARCODE.setText(_barcode);
        //Btn_Insert.performClick();
        insertData(_barcode);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region  컨트롤 정의 부분 ///////////////////////////////////////////////////////////////////////
        setContentView(R.layout.act_inv_in);
        //타이틀바 이름 변경.
        setTitle("재고입고");
        //진동시킬 변수를 생성한다.

        // 위젯에 대한 참조.(날짜 관련)
        Btn_Search = findViewById(R.id.Btn_Search);

        //CustomDateDialog 날짜관련 컨트롤

        Dte_Date = findViewById(R.id.Dte_Date);

        // 위젯에 대한 참조.(선택삭제 관련)
        btnDelete = findViewById(R.id.Btn_Delete);
        // 위젯에 대한 참조.(수동입력 관련)
        Btn_Insert = findViewById(R.id.Btn_Insert);
        Txt_BARCODE = findViewById(R.id.Txt_BARCODE);
        // 위젯에 대한 참조.(저장 버튼 관련)
        btn_Save = findViewById(R.id.Btn_InvIn);
        // 위젯에 대한 참조.(콤보박스 관련)
        ccb_emp = findViewById(R.id.Ccb_Emp_Name);
        // 위젯에 대한 참조.(라디오박스 관련)
        Rdo_Plant = findViewById(R.id.Rdo_Plant);
        /////////////////////////////////////////////////////////////
        //rectclerView 관련 참조
        _RecyclerView = findViewById(R.id.Recyclerview_List);
        //layoutManager
        _LayoutManager = new LinearLayoutManager(this);
        _RecyclerView.setLayoutManager(_LayoutManager);
        //recyclerView 관련 참조 끝

        //recyclerView 최초 설정
        ArrayList<CRV_BCR_Info_ItemObject> listItem = new ArrayList<>(); // RecyclerView에서 최초 설정할 변수
        //최초에 listitem을 RecyclerView에 보여줌.(에러발생 야기..)
        DataBinding(listItem);
        ////////////////////////////////////////////////////////////
        //endregion


        //region  컨트롤 Data 정의 부분 ///////////////////////////////////////////////////////////////////////
        //  콤보박스 데이터 가져오는 부분 생성 및 초기화.
        // 콤보박스 비동기 처리(공통코드 CODE_ID와 구분자 VALUE로 공통 코드 정보 들고옴.)
        // json parsing 된 문자를 가져올 URL 설정.


        //라디오그룹박스 바인딩
        Rdo_Plant.GroupID("PLANT");
        Rdo_Plant.SetData();
        //콤보박스 바인딩..
        ccb_emp.GroupID("EMP_INFO");
        ccb_emp.SetData();

        //endregion
        //Format() 정의 안할시에는 포맷='yyyy-MM-dd'으로 기본값.
        //Dte_Date.Format("yy-MM-dd");

        //날짜 클릭이벤트 입력( 바로 SetText됨..)
        // [ClickedEvent]정의 시, 데이트피커 다이얼로그에서 날짜 클릭시 이벤트를 받아 처리할 때 사용.
        Dte_Date.ClickedEvent(new CustomDateDialog.CustomDateDialog_GetDateResult_Listener() {
            @Override
            public void OnGetDate(String DateString) {
                //판단하여 SetText시켜줄 수 있음..
                Dte_Date.setText(DateString);
            }
        });


        //todo   저장 버튼 클릭 시 이벤트
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _Custom_Load_Dialog.Show_Load("저장중 입니다.");
                _MsgBox.Dlg_Select_YN(new Dlg_IF_MessageBox() {
                    @Override
                    public void onClicked(boolean Msg_result) {

                        if (Msg_result) {
                            //todo  1.  현재 표현되어지고 있는 아이템리스트
                            final ArrayList<CRV_BCR_Info_ItemObject> Save_ItemList = (_CRV_BCR_Info_Adapter).getItemList();
                            //todo  2.  에러 혹은 저장되지않은 바코드 리스트를 담을 배열
                            final ArrayList<CRV_BCR_Info_ItemObject> Save_ItemList_Res = new ArrayList<>();
                            //todo  3.   현재 체크되어있는 아이템 리스트의 총 갯수
                            final int FinaliRow = (_CRV_BCR_Info_Adapter).getSelectedItemCount();
                            //todo  4.   저장 혹은 실패 되어 return 된 총 갯수
                            //todo   final array 로 사용한 이유는 array의 경우는 final 일 지라도 증가 혹은 감소 시킬 수 있기 때문이다.
                            final int[] rows = {0};

                            //todo  for 문에서 사용되고있는 iRow 는  Volley를 사용할때 Row값을 넘겨준다.
                            //todo   이는 ..어떤 값이 처리되었는지 알기 위해서 이다.
                            for (int iRow = 0; iRow < Save_ItemList.size(); iRow++) {

                                try {
                                    //todo 저장할때 스레드 사용.....(명확하게 처리하기 위해..)
                                    Thread.sleep(50);
                                    if (Save_ItemList.get(iRow).isSelected()) {
                                        //todo   json parsing 된 문자를 가져올 URL 설정.
                                        CustomParameters param = new CustomParameters("SP_ANDROIDPDA_BCR_TRANS_IN_S");
                                        param.add("@P_BCR_ID", Save_ItemList.get(iRow).getBCR_ID());
                                        param.add("@P_USER_ID", _Login_Info.getString("loginId", null));
                                        //todo  바코드 정보를 가져와 리스트에 뿌려준다.
                                        final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(mContext, param.ExcuteStr());

                                        obj_get.callWCFService(new VolleyCallback_iRow() {
                                            @Override
                                            public void onSuccess(JSONArray[] result, int iRow) {

                                            }

                                            @Override
                                            public void onFail(String result, Volley_Connected_ErrorType ErrorType, int iRow) {
                                                //todo 실패 했을시에 실패한 이력 res 변수에 담음..
                                                Save_ItemList_Res.add(Save_ItemList.get(iRow));
                                            }


                                            @Override
                                            public void onFinish() {
                                                //todo   마지막에 총 row 의 수와 마지막 Row의 수를 비교하여 완료되었으면 true 를 리턴한다.
                                                //todo   작업이 끝났을 경우 로딩폼을 닫고 DataBinding
                                                if (FinaliRow == rows[0] + 1) {
                                                    _Custom_Load_Dialog.Close();
                                                    DataBinding(Save_ItemList_Res);
                                                } else {
                                                    //todo 하나 끝났을때 마다 Rows하나씩 증가 총 갯수랑 비교하기 위해.
                                                    rows[0]++;
                                                }
                                            }

                                        }, iRow); //todo   for문의 행 번호를 넘겨준다.

                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        } else //todo   아니오 클릭 시.
                        {
                            _Custom_Load_Dialog.Close();
                            return;
                        }
                    }
                }, mContext, "입고확인", "입고하시겠습니까?", "예", "아니오");

            }
        });
        //todo  region 조회버튼 클릭 시 이벤트
        Btn_Search.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                try {
                    //todo  CRV_BCR_Info_Json_Get 에서 바코드 정보를 조회 한 후 arr_RCV_BCR에 바코드 정보를 담는다.
                    //todo   json parsing 된 문자를 가져올 URL 설정.
                    CustomParameters param = new CustomParameters("SP_ANDROIDPDA_BCR_TRANS_IN_L");
                    param.add("@P_BCR_ID", "ALL");

                    final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(mContext, param.ExcuteStr());

                    obj_get.callWCFService(new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONArray[] result) {
                            ArrayList<CRV_BCR_Info_ItemObject> arr_RCV_BCR = new Gson().fromJson(result[0].toString(), new TypeToken<List<CRV_BCR_Info_ItemObject>>() {
                            }.getType());
                            //ArrayList<CRV_BCR_Info_ItemObject> arr_RCV_BCR = new CRV_BCR_Info_Array_Set().Get_RecyclerView_Data(result[0]);
                            DataBinding(arr_RCV_BCR);
                        }

                        @Override
                        public void onFail(String result, Volley_Connected_ErrorType ErrorType) {
                            _Vibrator.vibrate(1500);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });
        //todo  endregion


        //todo  endregion
        //region 선택삭제관련 이벤트
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //todo   아답터에 선택삭제 이벤트 정의 되어 있음.
                ArrayList<CRV_BCR_Info_ItemObject> list_res = _CRV_BCR_Info_Adapter.Delete(mContext, _CRV_BCR_Info_Adapter);
                DataBinding(list_res);

            }
        });
        //todo 수동입력 관련 이벤트
        Btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(Txt_BARCODE.getText().toString());
            }
        });
        //endregion

        //endregion
    }

    //todo  region CRV_BCR_Info_Adapter 의 리스트가 이벤트가 발생할 때 리스트Data를 변경하는 메서드 Override
    @Override
    public void onListItemClicked(CRV_BCR_Info_Adapter adapter, int position, String Update_String) {
        if (adapter == _CRV_BCR_Info_Adapter) {
            ArrayList<CRV_BCR_Info_ItemObject> ItemList = (_CRV_BCR_Info_Adapter)
                    .getItemList();
            //todo  _Custom_RecyclerViewAdapter_adapter 에서 리턴한 POSITION과 STRING 을 가지고
            // 리스트의 POSITION 위치의 BCR_ID 를 업데이트 한다.
            ItemList.get(position).setQTY(Update_String);
            ItemList.get(position).setSelected(true);
            //todo 현재위치의 값만 update 해준다.
            _CRV_BCR_Info_Adapter.notifyItemChanged(position, ItemList.get(position));
        }

    }
    //endregion


    // region _RecyclerView에 데이터를 업데이트 시켜주는 부분(전체 조회시)
    //todo  todo  Last_Refresh==> 조회 후 마지막으로 스크롤 내릴지 여부체크
    public void DataBinding(ArrayList<CRV_BCR_Info_ItemObject> arr_Set_Object) {
        //todo   화면이 바뀔때 마다  아답터 안의 context 정보 수정해야 함)

        //todo  실제데이터들 넘김

        _CRV_BCR_Info_Adapter = new CRV_BCR_Info_Adapter(mContext, arr_Set_Object, false);
        //todo   onListItemClicked 를 사용하기 위해서 필수로 사용해야함.
        _CRV_BCR_Info_Adapter.setRecyclerView_AdapterListener(this);
        //todo  어답터 연결
        _RecyclerView.setAdapter(_CRV_BCR_Info_Adapter);

    }
    //endregion

    //region _RecyclerView에 데이터를 업데이트 시켜주는 부분( 1개 추가시 )
    public void insertData(String BarcodeString) {

        try {
            //todo   json parsing 된 문자를 가져올 URL 설정.
            CustomParameters param = new CustomParameters("SP_ANDROIDPDA_BCR_TRANS_IN_L");
            param.add("@P_BCR_ID", BarcodeString);
            //todo  바코드 정보를 가져와 리스트에 뿌려준다.
            final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(mContext, param.ExcuteStr());

            obj_get.callWCFService(new VolleyCallback() {
                @Override
                public void onSuccess(JSONArray[] result) {

                    ArrayList<CRV_BCR_Info_ItemObject> arr_list_obj = new Gson().fromJson(result[0].toString(), new TypeToken<List<CRV_BCR_Info_ItemObject>>() {
                    }.getType());
                    //ArrayList<CRV_BCR_Info_ItemObject> arr_list_obj = new CRV_BCR_Info_Array_Set().Get_RecyclerView_Data(result[0]);
                    //todo  현재리스트에 추가
                    // 조회된 데이터가 있을 시에
                    if (arr_list_obj.size() == 1) {
                        Add_View(arr_list_obj);
                    } else {
                        //todo   삭제된것 알림.
                        Toast.makeText(mContext,
                                "존재하지 않는 데이터 입니다.", Toast.LENGTH_LONG)
                                .show();
                        //todo 1.5초간 진동울림. 없는데이터
                        _Vibrator.vibrate(1500);

                    }
                }

                @Override
                public void onFail(String result, Volley_Connected_ErrorType ErrorType) {
                    _Vibrator.vibrate(1500);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void Add_View(ArrayList<CRV_BCR_Info_ItemObject> arr_Set_Object) {
        //todo  Adapter에서 List정보를 가져옴.
        ArrayList<CRV_BCR_Info_ItemObject> ItemList = (_CRV_BCR_Info_Adapter).getItemList();

        //todo  1개씩 입력할때 중복체크
        for (int i = 0; i < ItemList.size(); i++) {
            if (ItemList.get(i).getBCR_ID().equals(arr_Set_Object.get(0).getBCR_ID())) {
                try {
                    //todo  예버튼 하나만 있는 메서드 Dlg_Select
                    _MsgBox.Dlg_Select(new Dlg_IF_MessageBox() {
                        @Override
                        public void onClicked(boolean Msg_result) {
                            //todo   확인버튼 클릭 이후 작성될 기능들....
                        }
                    }, mContext, "알림", "중복입력 입니다.", "예");

                    Toast.makeText(mContext, "중복입력입니다.", Toast.LENGTH_SHORT).show();
                    //todo  1.5초간 진동울림.
                    _Vibrator.vibrate(1500);
                    Thread.sleep(1000);
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        //todo  2.ItemList가 null 일때 array에 저장 한 후 (NULL일 경우 에러발생으로 인해 추가)
        if (ItemList.isEmpty()) {
            //todo  수동입력 된 부분 신규입력.
            DataBinding(arr_Set_Object);
        } else {
            for (int i = 0; i < arr_Set_Object.size(); i++) {
                //todo  기존 AdapterArray에 등록되있는 정보에서 Set_Object 추가
                ItemList.add(arr_Set_Object.get(i));
            }

            //todo  //todo  체크 안되있던 나머지부분 View.
            DataBinding(ItemList);
        }
        //todo   마지막 줄로 내림
        _RecyclerView.scrollToPosition(_CRV_BCR_Info_Adapter.getItemCount() - 1);

    }


    //endregion

}
