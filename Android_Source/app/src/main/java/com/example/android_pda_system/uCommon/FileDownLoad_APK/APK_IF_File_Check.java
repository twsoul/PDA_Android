package com.example.android_pda_system.uCommon.FileDownLoad_APK;


import java.io.File;

public interface APK_IF_File_Check {
    void onSuccess(boolean Check_Result);

    void Finish(boolean Finish_Result, File _DownloadFile, APK_UpdateType _UpdateType);
}
