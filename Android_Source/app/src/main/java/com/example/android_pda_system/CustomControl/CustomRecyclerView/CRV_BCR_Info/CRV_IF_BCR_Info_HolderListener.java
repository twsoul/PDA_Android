package com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_Info;

/*******************************************************************************
 *    Description  : PDA Android CustomRecyclerView Listener
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

public interface CRV_IF_BCR_Info_HolderListener {
    void onListItemClick(int position);
}
