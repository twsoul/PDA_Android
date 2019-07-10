package com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_CNT;

/*******************************************************************************
 *    Description  : PDA Android CustomRecyclerView ItemObject
 *    Usage        : RecyclerView에서 사용 될 멤버변수 Object 를 정의해준다.
 *
 *                   item_bcr_detail.xml 에서 사용되는 id와 동일하게 가져가는것이 좋다.
 *
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
public class CRV_BCR_CNT_ItemObject {

    private String BCR_ID;
    private String PART_ID;
    private String LOC_ID;
    private String WEIGHT_CNT;
    private Boolean CHECKED;


    public CRV_BCR_CNT_ItemObject(String BCR_ID, String PART_ID, String LOC_ID, String WEIGHT_CNT) {
        this.BCR_ID = BCR_ID;
        this.PART_ID = PART_ID;
        this.LOC_ID = LOC_ID;
        this.WEIGHT_CNT = WEIGHT_CNT;
        this.CHECKED = true; //Default= false;

    }

    public String get_BCR_ID() {
        return BCR_ID;
    }

    public void set_BCR_ID(String _User_Id) {

        this.BCR_ID = _User_Id;
    }

    public String get_PART_ID() {
        return PART_ID;
    }

    public void set_PART_ID(String PART_ID) {
        this.PART_ID = PART_ID;
    }

    public String get_LOC_ID() {
        return LOC_ID;
    }

    public void set_LOC_ID(String LOC_ID) {
        this.LOC_ID = LOC_ID;
    }

    public String get_WEIGHT_CNT() {
        return WEIGHT_CNT;
    }

    public void set_WEIGHT_CNT(String WEIGHT_CNT) {
        this.WEIGHT_CNT = WEIGHT_CNT;
    }

    //region 체크박스가 표시 되었을때.
    public Boolean isSelected() {
        return CHECKED;
    }

    public void setSelected(Boolean CHECKED) {
        this.CHECKED = CHECKED;
    }


    //endregion

}
