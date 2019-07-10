package com.acs.app_update;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.acs.app_update.CustomControl.CustomProgressDialog.Dlg_Load_Dialog.Dlg_Load_Dialog;
import com.acs.app_update.CustomControl.CustomProgressDialog.Dlg_MessageBox_YN.Dlg_IF_MessageBox;
import com.acs.app_update.CustomControl.CustomProgressDialog.Dlg_MessageBox_YN.Dlg_MessageBox;
import com.acs.app_update.uCommon.FileDownLoad_APK.APK_File_Check;
import com.acs.app_update.uCommon.FileDownLoad_APK.APK_IF_File_Check;
import com.acs.app_update.uCommon.FileDownLoad_APK.APK_InstallCheck;
import com.acs.app_update.uCommon.FileDownLoad_APK.APK_UpdateType;
import com.acs.app_update.uConfig.uPackage_TargetConfig.Package_TargetName;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Context mContext = MainActivity.this;
    public Dlg_Load_Dialog _Custom_Load_Dialog;
    public Dlg_MessageBox _MsgBox = new Dlg_MessageBox();
    File _GetDownLoadFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //todo 권한 체크
        Permission_Check();
    }

    //TODO 권한확인
    public void Permission_Check() {

        //todo  마시멜로우 버전과 같거나 이상이라면 저장소 권한 체크
        //  외부저장소 관련 권한 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(mContext, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }

                //todo 마지막 인자는 체크해야될 권한 갯수
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        2);


            } else {
                Toast.makeText(mContext, "권한 설정 완료", Toast.LENGTH_SHORT).show();
                Run();
            }
        }
    }


    //todo 권한체크했는지 확인....
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //todo 수락 클릭시 실행
                    Run();

                } else {
                    // 거절클릭시 사용자가 직접 권한을 주도록 화면을 띄워줌.
                    Toast.makeText(mContext, "권한이 없어 실행 불가합니다.권한 설정을 해주세요.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:" + mContext.getPackageName()));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(i);
                }
                return;
            }
        }
    }


    public void Run() {
        _Custom_Load_Dialog = new Dlg_Load_Dialog(mContext);

        //TODO APK파일 체크 후 가져오기
        APK_File_Check AFG = new APK_File_Check(mContext);
        _Custom_Load_Dialog.Show_Load("업데이트 체크중입니다.");
        AFG.Version_Check(new APK_IF_File_Check() {
            @Override
            public void onSuccess(boolean Check_Result) {
                // 업데이트 취소시 혹은 에러발생시..
                if (Check_Result) {
                    _Custom_Load_Dialog.Close();
                    android.os.Process.killProcess(android.os.Process.myPid());
                } else {
                    _Custom_Load_Dialog.Close();
                    _MsgBox.Dlg_Select(new Dlg_IF_MessageBox() {
                        @Override
                        public void onClicked(boolean Msg_result) {
                            finish();
                        }
                    }, mContext, "다운로드 실패", "다운로드를 실패하였습니다. \n다시시도 해주세요.", "확인");
                }
            }

            @Override
            public void Finish(boolean Finish_Result, File _DownloadFile, APK_UpdateType _UpdateType) {


                //TODO  업데이트가 완료되었을때.

                if (Finish_Result) {
                    _GetDownLoadFile = _DownloadFile;
                    APK_InstallCheck check = new APK_InstallCheck();
                    //TODO 현재 존재하는 패키지가 있을경우 + 쿼리문에서 업데이트 타입이 다운그레이드 일 경우. 에만 삭제 후 설치하도록 유도.
                    if (check.APK_InstallCheck(mContext, Package_TargetName.TargetPackage) && _UpdateType == APK_UpdateType.DOWNGRADE) {
                        if (installApk(_GetDownLoadFile)) {
                            deleteApk();
                            //TODO  완료되면 앱 종료.
                            Toast.makeText(mContext, "다운로드가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            android.os.Process.killProcess(android.os.Process.myPid());
                        } else {
                            _MsgBox.Dlg_Select(new Dlg_IF_MessageBox() {
                                @Override
                                public void onClicked(boolean Msg_result) {
                                    android.os.Process.killProcess(android.os.Process.myPid());

                                }
                            }, mContext, "실행불가", "안드로이드 버전이 api25이상이 아닙니다.", "확인");
                        }
                    } else {
                        if (installApk(_GetDownLoadFile)) {
                            //TODO  완료되면 앱 종료.
                            Toast.makeText(mContext, "다운로드가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            android.os.Process.killProcess(android.os.Process.myPid());
                        } else {
                            _MsgBox.Dlg_Select(new Dlg_IF_MessageBox() {
                                @Override
                                public void onClicked(boolean Msg_result) {
                                    android.os.Process.killProcess(android.os.Process.myPid());

                                }
                            }, mContext, "실행불가", "안드로이드 버전이 api25이상이 아닙니다.", "확인");
                        }
                    }

                } else {
                    Toast.makeText(mContext, "다운로드가 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    _Custom_Load_Dialog.Close();
                    _MsgBox.Dlg_Select(new Dlg_IF_MessageBox() {
                        @Override
                        public void onClicked(boolean Msg_result) {
                            finish();
                        }
                    }, mContext, "다운로드 실패", "다운로드를 실패하였습니다. \n다시시도 해주세요.", "확인");
                }
            }
        });
    }

    //TODO 설치후 APK 실행.
    public boolean installApk(File file) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {


                // todo 기존에 패키지 명으로 되어있었지만 ... 기본 다운로드 경로로 변경.
                Uri apkUri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);

                return true;

            } else {
                // TODO FileProvider 는 api 24 이상부터 사용가능 ...
                // TODO 그리고 사용하기 위해서 res/xml/provider_paths.xml 이 필요하고
                // TODO manifest 에 provider 를 등록해줘야 한다.
                Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileprovider", file);
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
                return true;
            }

        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return false;
        }
    }

    // TODO apk삭제
    public void deleteApk() {
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE);

        uninstallIntent.setData(Uri.parse("package:" + Package_TargetName.TargetPackage));
        startActivity(uninstallIntent);

        //startActivity(uninstallIntent);
    }


}
