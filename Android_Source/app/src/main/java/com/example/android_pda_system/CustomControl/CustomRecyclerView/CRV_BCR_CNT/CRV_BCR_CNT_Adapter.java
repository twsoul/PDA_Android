package com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_CNT;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.android_pda_system.CustomControl.CustomDialog.Dlg_CNT_UPDATE.Dlg_INSERT_CNT;
import com.example.android_pda_system.R;

import java.util.ArrayList;

/*******************************************************************************
 *    Description  : PDA Android CustomRecyclerView_Adapter
 *    Usage        : CRV_BCR_CNT_Holder, CRV_BCR_CNT_ItemObject ( 속성설정)
 *    InterFace    : CRV_IF_BCR_CNT_HolderListener 은 아답터 클릭을 발생했을때 사용 되는 I.F
 *                   CRV_IF_BCR_CNT_AdapterListener 은 메인화면으로 값을 전달하여 상속받은 메인화면에 값을 return 시켜주기위해 사용되는 I.F
 *    사용 xml     :  item_bcr_detail.xml
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

//todo OnListItemClickListener에서 인터페이스 상속
public class CRV_BCR_CNT_Adapter extends RecyclerView.Adapter<CRV_BCR_CNT_Holder> implements CRV_IF_BCR_CNT_HolderListener {

    private ArrayList<CRV_BCR_CNT_ItemObject> _Arr_itemList;  //todo 메인액티비티에서 인자로 넘어온 List를 받기 위한 List이다.
    private Context _context;
    //todo  리스트 클릭 시 팝업창 띄울것인지 체크
    private boolean _Popup_Used_Check = false;


    //region 리스너 관련 설정
    public CRV_IF_BCR_CNT_AdapterListener CRV_IF_BCR_CNT_AdapterListener;

    //todo 호출할 리스너
    public void setRecyclerView_AdapterListener(CRV_IF_BCR_CNT_AdapterListener CRV_IF_BCR_CNT_AdapterListener) {
        this.CRV_IF_BCR_CNT_AdapterListener = CRV_IF_BCR_CNT_AdapterListener;
    }
    //endregion

    //region  생성자
    public CRV_BCR_CNT_Adapter(Context context, ArrayList<CRV_BCR_CNT_ItemObject> Arr_itemList, boolean Popup_Used_Check) { //todo 메인액티비티에서 액티비티와 포스트 배열(posts)을 인자로 받는다.
        this._Arr_itemList = Arr_itemList;
        this._context = context;
        this._Popup_Used_Check = Popup_Used_Check;
    }
    // endregion

    //region  Holder 를 Create해준다.
    //todo setOnListItemClickListener 는 CRV_IF_BCR_CNT_HolderListener 에서 (implements)상속받음
    @Override
    public CRV_BCR_CNT_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseView = View.inflate(_context, R.layout.item_bcr_detail, null); //list_item은 실제 데이터를 보여주는 레이아웃 xml파일명이다.

        CRV_BCR_CNT_Holder postViewHolder = new CRV_BCR_CNT_Holder(baseView, this);
        //CRV_BCR_CNT_Holder 에 있는 setOnListItemClickListener 메서드 등록
        postViewHolder.setOnListItemClickListener(this);
        return postViewHolder;
    }
    //endregion

    //region
    @Override
    public void onBindViewHolder(CRV_BCR_CNT_Holder holder, int position) {

        final CRV_BCR_CNT_ItemObject objItem = _Arr_itemList.get(position);
        //todo 경우에 따라 원치 않는 상황을 방지 할 수 있습니다.
        holder.H_Delete_Check.setOnCheckedChangeListener(null);

        //todo true 인 경우 확인란이 선택되고 그렇지 않은 경우 선택되지 않습니다.
        holder.H_Delete_Check.setChecked(objItem.isSelected());


        //todo  Recyclerview에 Text Binding
        holder.H_BCR_ID.setText(_Arr_itemList.get(position).get_BCR_ID());
        holder.H_PART_ID.setText(_Arr_itemList.get(position).get_PART_ID());
        holder.H_LOC_ID.setText(_Arr_itemList.get(position).get_LOC_ID());
        holder.H_WEIGHT_CNT.setText(_Arr_itemList.get(position).get_WEIGHT_CNT());
        //todo  체크박스 체크시 상태값 update
        holder.H_Delete_Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //todo 체크박스의 마지막 상태값을 update쳐준다.
                objItem.setSelected(isChecked);

            }
        });

    }
    //endregion

    //region recyclerview 의 전체 RowCount 를 가져옴
    @Override
    public int getItemCount() {

        return this._Arr_itemList.size();
    }

    //todo  recyclerview 의 체크박스가 선택되어진 RowCount 를 가져옴
    public int getSelectedItemCount() {

        int FinalRow = 0;
        for (int iRow = 0; iRow < this._Arr_itemList.size(); iRow++) {
            if (_Arr_itemList.get(iRow).isSelected()) {
                FinalRow++;
            }
        }
        return FinalRow;
    }

    //todo recyclerview 에 있는 adapter list를 가져옴
    public ArrayList<CRV_BCR_CNT_ItemObject> getItemList() {
        //todo 현재 가지고 있는 itemList가 null이면 new ArrayList<CRV_BCR_CNT_ItemObject>를 가지고 온다.
        ArrayList<CRV_BCR_CNT_ItemObject> Return_Array = _Arr_itemList.size() == 0 ? new ArrayList<CRV_BCR_CNT_ItemObject>() : this._Arr_itemList;
        return Return_Array;
    }
    //endregion


    //todo 선택삭제 버튼 클릭 이벤트 (체크박스 체크여부로 표시)
    public ArrayList<CRV_BCR_CNT_ItemObject> Delete(Context context, CRV_BCR_CNT_Adapter _Adapter) {
        //  변수정의 /////////////////////////////
        //todo 체크되지 않은 항목 만 담을 Array생성
        ArrayList<CRV_BCR_CNT_ItemObject> listItem_res = new ArrayList<>();

        //todo 데이터를 전달받을 변수 생성
        String data = "";

        //todo Adapter에서 List정보를 가져옴.
        ArrayList<CRV_BCR_CNT_ItemObject> ItemList = (_Adapter).getItemList();
        // 변수정의 끝 /////////////////////////////
        for (int i = 0; i < ItemList.size(); i++) {
            //todo  ItemObject에 RecyclerView에 있는  리스트 정보를 담을 변수 생성
            CRV_BCR_CNT_ItemObject BCR_OBJ = ItemList.get(i);
            if (BCR_OBJ.isSelected() == true) {
                data = data + "\n" + BCR_OBJ.get_BCR_ID() + "[" + BCR_OBJ.get_PART_ID() + "]";
            } else {
                //todo 선택되지 않은 항목은 새로 추가.
                listItem_res.add(BCR_OBJ);
            }

        }
        //todo  Context에 삭제된것 알림.
        Toast.makeText(context,
                "삭제됨: \n" + data, Toast.LENGTH_LONG)
                .show();

        return listItem_res;
        //todo 체크박스에 선택되지 않은 listItem을 setAdapter를 사용하여 다시 View


    }

    //region
    //todo  사용하지 않을경우 아래부분을 제외시키면 된다.
    // todo RecyclerViewHolder에 현재해당하는 position에 대한 값을 바인딩 시켜줌.
    @Override
    public void onListItemClick(final int position) {
        Toast.makeText(_context, _Arr_itemList.get(position).get_BCR_ID() + "를 선택하였습니다.", Toast.LENGTH_SHORT).show();


        //todo CRV_BCR_CNT_Adapter 생성 시 true 로 생성하였으면 팝업창을 띄우고
        //todo                             false로 생성하였으면 팝업창을 띄우지 않는다.
        if (_Popup_Used_Check) {
            //todo 현재 parent contect 에 custom_dialog를 출력한다.
            Dlg_INSERT_CNT PIC = new Dlg_INSERT_CNT(_context, InputType.TYPE_CLASS_TEXT); //todo  현재 개발당시 수량변경으로 inputtype을 Number로 한다. // 키보드 가 숫자타입인지, 문자타입인지 등등..입력 타입 정의.
            //todo dialog에 수량정보를 던져준다. 이후 view를 실행함.
            PIC.Insert_Popup_Dialog(_Arr_itemList.get(position).get_WEIGHT_CNT());

            //todo 마지막(확인 클릭 시)에 수정한 값을 입력해준다.
            //todo 수정버튼 클릭 시 이벤트(Dlg_INSERT_CNT.class)
            PIC.setDialogListener(new Dlg_INSERT_CNT.Dlg_PositiveClicked_Listener() {
                @Override
                public void onPositiveClicked(String name) {
                    //todo _Arr_itemList.get(position).set_WEIGHT_CNT(name);

                    //todo  POPUP_INSER_CNT에서 OK를 누르면 해당 이벤트를 타게되는데..
                    //todo  그때 아래 리스터를 호출해준다(Activity로 전달하기위해(interface))
                    CRV_IF_BCR_CNT_AdapterListener.onListItemClicked(position, name);
                }
            });
        }

    }
    //endregion


}
