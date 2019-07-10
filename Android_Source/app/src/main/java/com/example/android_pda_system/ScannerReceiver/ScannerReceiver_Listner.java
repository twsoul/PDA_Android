package com.example.android_pda_system.ScannerReceiver;

/*******************************************************************************
 *    Description  : PDA Android ScannerReceiver_Listner
 *                      스캔된 바코드 정보를 인터페이스 해줄 리스너
 *                      ScannerReceiver (Activity) 와
 *                      ScannerReceiver_Frg (Fragment)
 *                      같이 쓰는 리스너.
 *    사용 xml     :
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.30		JH_KIM                            CREATE
 *
 *******************************************************************************/

public interface ScannerReceiver_Listner {
    void OnBarcode(String _barcode,
                   String _type,
                   String _module,
                   String _rawdata,
                   int _length,
                   int _decCount);
}
