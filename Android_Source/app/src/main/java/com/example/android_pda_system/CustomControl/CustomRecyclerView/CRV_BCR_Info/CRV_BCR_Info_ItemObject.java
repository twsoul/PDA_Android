package com.example.android_pda_system.CustomControl.CustomRecyclerView.CRV_BCR_Info;

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
public class CRV_BCR_Info_ItemObject {

    private String BCR_ID;
    private String PLANT_ID;
    private String PLANT_NAME;
    private String PART_ID;
    private String PART_NAME;
    private String QTY;
    private Boolean CHECKED;


    public CRV_BCR_Info_ItemObject(String BCR_ID, String PLANT_ID, String PLANT_NAME, String PART_ID, String PART_NAME, String QTY) {
        this.BCR_ID = BCR_ID;
        this.PLANT_ID = PLANT_ID;
        this.PLANT_NAME = PLANT_NAME;
        this.PART_ID = PLANT_ID;
        this.PART_NAME = PART_NAME;
        this.QTY = QTY;
        this.CHECKED = true; //Default= false;
    }

    public String getBCR_ID() {
        return BCR_ID;
    }

    public void setBCR_ID(String BCR_ID) {
        this.BCR_ID = BCR_ID;
    }

    public String getPLANT_ID() {
        return PLANT_ID;
    }

    public void setPLANT_ID(String PLANT_ID) {
        this.PLANT_ID = PLANT_ID;
    }

    public String getPLANT_NAME() {
        return PLANT_NAME;
    }

    public void setPLANT_NAME(String PLANT_NAME) {
        this.PLANT_NAME = PLANT_NAME;
    }

    public String getPART_ID() {
        return PART_ID;
    }

    public void setPART_ID(String PART_ID) {
        this.PART_ID = PART_ID;
    }

    public String getPART_NAME() {
        return PART_NAME;
    }

    public void setPART_NAME(String PART_NAME) {
        this.PART_NAME = PART_NAME;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public Boolean getCHECKED() {
        return CHECKED;
    }

    public void setCHECKED(Boolean CHECKED) {
        this.CHECKED = CHECKED;
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
