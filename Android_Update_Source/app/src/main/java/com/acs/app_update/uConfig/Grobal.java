package com.acs.app_update.uConfig;


public class Grobal {
    //DBConnection 할 웹서비스 경로    //http://192.168.10.113:9999/Android_WS/Service1.svc/ACS_POST
    //public static String _DBConnectionString_Path = "http://192.168.10.111:200/Android_WS/Service1.svc/ACS_POST"; //서태우 iis
//    public static String _DBConnectionString_Path = "http://192.168.10.240/MES_AKCTC_WS/Android_WS/Service1.svc/ACS_POST"; // 지사서버 iis
    public static String _DBConnectionString_Path = "http://10.150.1.25:8012/Android_WS/Service1.svc/ACS_POST"; // ak청양 iis

    //TODO 공통코드 가져올때 사용되는 Procedure
    public static String _Common_KeyValue_SP="SP_ANDROIDPDA_Common_KeyValue_L";



    //TODO  app update시 사용 ( 업데이터 앱에서 사용함.)
    //실행시키거나 삭제할 pda 패키지 명
    public static String TargetPackage ="com.example.android_pda_system";
    // 내부에 설치될 app 이름
    public static String DownLoadAppName ="pda_system";
    //apk 파일 버전      //  USP_ANDROIDPDA_APK_FILE_CHECK_L
    public static String CheckFile_SpName = "SP_ANDROIDPDA_APK_FILE_CHECK_L";
    //apk 파일 출력 sp    // USP_ANDROIDPDA_APK_FILE_L
    public static String GetFile_SpName = "SP_ANDROIDPDA_APK_FILE_L ";
}


