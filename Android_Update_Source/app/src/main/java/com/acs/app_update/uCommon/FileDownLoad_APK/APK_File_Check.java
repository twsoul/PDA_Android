package com.acs.app_update.uCommon.FileDownLoad_APK;

import android.content.Context;
import android.widget.Toast;


import com.acs.app_update.CustomControl.CustomProgressDialog.Dlg_Load_Dialog.Dlg_Load_Dialog;
import com.acs.app_update.CustomControl.CustomProgressDialog.Dlg_MessageBox_YN.Dlg_IF_MessageBox;
import com.acs.app_update.CustomControl.CustomProgressDialog.Dlg_MessageBox_YN.Dlg_MessageBox;
import com.acs.app_update.uCommon.CustomParameters.CustomParameters;
import com.acs.app_update.uCommon.Setting_DataBase_Access.VolleyCallback;
import com.acs.app_update.uCommon.Setting_DataBase_Access.Volley_Connected_ErrorType;
import com.acs.app_update.uCommon.Setting_DataBase_Access.Volley_JsonObject_Get;
import com.acs.app_update.uCommon.Setting_DataBase_Access.Volley_JsonObject_VersionInfo_ResultGet;
import com.acs.app_update.uConfig.Grobal;
import com.acs.app_update.uConfig.uPackage_TargetConfig.Package_TargetApp;
import com.acs.app_update.uConfig.uPackage_TargetConfig.Package_TargetVersion;

import org.json.JSONArray;

import java.io.File;

public class APK_File_Check {

    public Context _Context;
    //Dialog 창 띄우기 Yes or No
    public Dlg_MessageBox _MsgBox = new Dlg_MessageBox();
    //로딩창
    public Dlg_Load_Dialog _Custom_Load_Dialog;
    //가지고 있을 버전정보.
    public String _Version_Info_Temp ;
    //다운로드될 파일을 가지고 있는 변수
    File _GetDownloadFile= null;
    //UPGRADE인지 DOWNGRADE 인지 체크하기 위한 APK_UpdateType 변수
    public APK_UpdateType _UpdateType =null;
    public APK_File_Check(Context context) {
        this._Context = context;
        this._Custom_Load_Dialog = new Dlg_Load_Dialog(_Context);
    }

    public void Version_Check(final APK_IF_File_Check IF_Check) {
        try {


            // json parsing 된 문자를 가져올 URL 설정.
            CustomParameters param = new CustomParameters(Grobal.CheckFile_SpName);
            param.add("@P_VERSION_INFO",     new Package_TargetVersion(_Context).get());

            //바코드 정보를 가져와 리스트에 뿌려준다.
            final Volley_JsonObject_Get obj_get = new Volley_JsonObject_Get(_Context, param.ExcuteStr());

            obj_get.callWCFService(new VolleyCallback() {
                @Override
                public void onSuccess(final JSONArray[] result) {
                    // 에러발생시 Error_str 에 담아서 결과값 넘어옴.
                    try {
                        // 결과가 TRUE인지 FALSE인지..(파일이 있는지 없는지..
                         Volley_JsonObject_VersionInfo_ResultGet VOLLEY_RESULT = new Volley_JsonObject_VersionInfo_ResultGet();
                        if (VOLLEY_RESULT.Get_Result_Data(result[0]).equals("TRUE")) {
                            _UpdateType=VOLLEY_RESULT._UpdateType;
                            _MsgBox.Dlg_Select_YN(new Dlg_IF_MessageBox() {
                                @Override
                                public void onClicked(boolean Msg_result) {

                                    if (Msg_result) {
                                        _Custom_Load_Dialog.Show_Load("다운로드중..");


                                        //APK. 가져옴..
                                        try {
                                            APK_File_Get AFG = new APK_File_Get(_Context);
                                            AFG.setAPK_Get_Result_Listener(new APK_File_Get.APK_Get_Result_Listener() {
                                                @Override
                                                public void OnGet_Result(boolean get_result, File _DownloadFile) {
                                                    _Custom_Load_Dialog.Close();
                                                    //리턴받은 파일을 받아서 다시 메인 결과에 파일 리턴
                                                    _GetDownloadFile =_DownloadFile;
                                                    if (get_result&& _GetDownloadFile!=null) {

                                                        IF_Check.Finish(true,_GetDownloadFile,_UpdateType);



                                                    } else {
                                                        IF_Check.Finish(false,_GetDownloadFile,_UpdateType);
                                                    }
                                                }
                                            });

                                            AFG.APK_Get();


                                        } catch (Exception ex) {
                                            //다운로드 이후 실행 실패시..
                                            IF_Check.Finish(false,_GetDownloadFile,_UpdateType);
                                        }
                                        //끝낫을때 이벤트


                                    } else // 아니오 클릭 시.
                                    {
                                        Toast.makeText(_Context, "업데이트를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                                        IF_Check.onSuccess(false);

                                    }
                                }
                            }, _Context, "새로운 업데이트", "새로운 버전이 있습니다 다운하겠습니까?", "예", "아니오");


                        } else {
                            Toast.makeText(_Context, "새로운 업데이트가 없습니다.", Toast.LENGTH_SHORT).show();
                            Package_TargetApp TargetApp = new Package_TargetApp(_Context);
                            TargetApp.run();
                            IF_Check.onSuccess(true);
                        }


//                        byte[] aa = JsonDeserializationContext.DeserializeObjectresult.getJSONObject(0).getString("FILE");
//                        FileDownLoad FD = new FileDownLoad(Act_BCR_CHK.this);
//                        FD.writeResponseBodyToDisk(aa);


                    } catch (Exception ex) {
                        IF_Check.onSuccess(false);
                        Toast.makeText(_Context,
                                ex.toString(),
                                Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFail(String result, Volley_Connected_ErrorType ErrorType) {
                    IF_Check.onSuccess(false);
                    Toast.makeText(_Context,
                            result,
                            Toast.LENGTH_SHORT).show();
                }
            }); // for문의 행 번호를 넘겨준다.


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
