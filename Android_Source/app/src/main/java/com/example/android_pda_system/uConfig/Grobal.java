package com.example.android_pda_system.uConfig;


/*******************************************************************************
 *    Description  : PDA Android Grobal변수
 *    Usage        : db연결문자열 및 공통으로 쓰는 변수들을 여기서 정의.
 *
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.06.19		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Grobal {
    //TODO 실제 동작하는 프로그램에서 사용되는 정보

    //todo  웹서비스 접속정보
    //public static String _DBConnectionString_Path = "http://192.168.10.113:9999/Android_WS/Service1.svc/ACS_POST"; // 김재현 iis
//    public static String _DBConnectionString_Path = "http://192.168.10.240/MES_AKCTC_WS/Android_WS/Service1.svc/ACS_POST"; // 지사서버 iis
    public static String _DBConnectionString_Path = "http://10.150.1.25:8012/Android_WS/Service1.svc/ACS_POST"; //  ak청양 iis

    //TODO 공통코드 가져올때 사용되는 Procedure
    public static String _Common_KeyValue_SP = "SP_ANDROIDPDA_Common_KeyValue_L";


    //TODO  app update시 사용 ( 업데이터 앱에서 사용함.)
    //실행시키거나 삭제할 pda 패키지 명
    public static String TargetPackage = "com.example.android_pda_system";  // 업데이터 앱에서 사용함.
    // 내부에 설치될 app 이름
    public static String DownLoadAppName = "pda_system";     // 업데이터 앱에서 사용함.

    //apk 파일 버전      //  USP_ANDROIDPDA_APK_FILE_CHECK_L
    public static String CheckFile_SpName = "SP_ANDROIDPDA_APK_FILE_CHECK_L";
    //apk 파일 출력 sp    // USP_ANDROIDPDA_APK_FILE_L
    public static String GetFile_SpName = "SP_ANDROIDPDA_APK_FILE_L ";

}
