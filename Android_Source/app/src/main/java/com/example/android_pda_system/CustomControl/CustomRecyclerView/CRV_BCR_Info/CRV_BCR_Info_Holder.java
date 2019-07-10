package com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_Info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android_pda_system.R;

/*******************************************************************************
 *    Description  : PDA Android CustomRecyclerView Holder
 *    Usage        : CRV_BCR_Adapter 에서 호출함
 *                   item_bcr_detail.xml에 정의되어있는 컨트롤에 대한 정보를 Holder에서 입력해준다.
 *
 *   CRV_BCR_ItemObject와 ID값을 맞춰주면 알아보기 편하다.
 *    사용 xml     :  item_bcr_detail.xml
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.17		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class CRV_BCR_Info_Holder extends RecyclerView.ViewHolder {


    public TextView H_BCR_ID;
    public TextView H_PART_NAME;
    public TextView H_PLANT_NAME;
    public TextView H_QTY;
    public CheckBox H_Checked;
    public CRV_BCR_Info_Adapter mAdapter;

    public CRV_IF_BCR_Info_HolderListener mListener;

    //region  CRV_BCR_Info_Holder 레이아웃에 List_item정의된 id에대한 기능 및 초기화
    public CRV_BCR_Info_Holder(View itemView, CRV_BCR_Info_Adapter customRecyclerViewAdapter) {
        super(itemView);
        mAdapter = customRecyclerViewAdapter;
        H_BCR_ID = itemView.findViewById(R.id.txt_BCR_ID);
        H_PART_NAME = itemView.findViewById(R.id.txt_PART_NAME);
        H_PLANT_NAME = itemView.findViewById(R.id.txt_PLANT_NAME);
        H_QTY = itemView.findViewById(R.id.txt_QTY);
        H_Checked = itemView.findViewById(R.id.Chk_Checked);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 리스트가 클릭되었을때...
                // CRV_IF_BCR_CNT_HolderListener 에서 override 된 onListItemClick 이벤트를 실행.
                mListener.onListItemClick(getAdapterPosition());
            }
        });
    }
    //endregion

    //region 리스트 클릭시 onCreateViewHolder 의  postViewHolder.setOnListItemClickListener(this); 통해 얻어온다.
    public void setOnListItemClickListener(CRV_IF_BCR_Info_HolderListener customRecyclerViewOnClickListener) {
        mListener = customRecyclerViewOnClickListener;
    }
    //endregion

}
