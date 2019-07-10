package com.example.android_pda_system.ScannerReceiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;

/*******************************************************************************
 *    Description  : PDA Android ScannerReceiver
 *                      스캔된 바코드 정보를 가지고와서
 *                      리스너에 전달해준다.
 *    사용 xml     :
 *******************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.05.30		JH_KIM                            CREATE
 *
 *******************************************************************************/


public class ScannerReceiver_Frg extends Fragment {

    public Context _context;
    public String _barcode;
    public String _type;
    public String _module;
    public String _rawdata;
    public int _length;
    public int _decCount;
    protected static final String TAG = "ScannerReceiver.Class";
    ScannerReceiver_Listner _ScannerReceiver_Listner;

    //todo  호출할 리스너
    public void setBroadcaseReceiver_Listner(ScannerReceiver_Listner ScannerReceiver_Listner) {
        this._ScannerReceiver_Listner = ScannerReceiver_Listner;
    }

    //todo  ScannerReceiver_Frg 에 파라미터를 받아 처리하는 경우 (Fragment만 ... 이상함)
    //   파라미터를 받지말라는 경고메세지가 뜨는데 무시 하기위해 생성.
    public ScannerReceiver_Frg() {
    }

    //todo   오류검사 배제..
    //todo  ScannerReceiver_Frg 에 파라미터를 받아 처리하는 경우 (Fragment만 ... 이상함)
    // 파라미터를 받지말라는 경고메세지가 뜨는데 무시하는 부분.
    // @SuppressLint("ValidFragment") 사용
    @SuppressLint("ValidFragment")
    public ScannerReceiver_Frg(Context context) {

        this._context = context;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ScannerReceiver_Values.SCANNER_ACTION_BARCODE);
        filter.addAction(ScannerReceiver_Values.SCANNER_ACTION_IS_ENABLE_ANSWER);
        _context.registerReceiver(BarcodeIntentBroadcast, filter);
        _context.sendBroadcast(new Intent(ScannerReceiver_Values.SCANNER_ACTION_IS_ENABLE));
    }


    public android.content.BroadcastReceiver BarcodeIntentBroadcast = new android.content.BroadcastReceiver() {

        //        public String barcode;
//        public String type;
//        public String module;
        public byte[] rawdata;
//        public int length;
//        public int decCount;


        @Override
        public void onReceive(Context content, Intent intent) {

            if (intent.getAction().equals(ScannerReceiver_Values.SCANNER_ACTION_BARCODE)) {

                _barcode = intent.getExtras().getString(ScannerReceiver_Values.SCANNER_EXTRA_BARCODE_DATA);
                _type = intent.getExtras().getString(ScannerReceiver_Values.SCANNER_EXTRA_BARCODE_CODE_TYPE);
                _module = intent.getExtras().getString(ScannerReceiver_Values.SCANNER_EXTRA_MODULE_TYPE);
                try {
                    rawdata = intent.getExtras().getByteArray(ScannerReceiver_Values.SCANNER_EXTRA_BARCODE_RAW_DATA);
                } catch (Exception e) {
                    Log.d(TAG, "onReceive scanner - null raw data");
                }
                _length = intent.getExtras().getInt(ScannerReceiver_Values.SCANNER_EXTRA_BARCODE_DATA_LENGTH, 0);
                _decCount = intent.getExtras().getInt(ScannerReceiver_Values.SCANNER_EXTRA_BARCODE_DEC_COUNT, 0);

                if (_barcode != null) {

                    if (rawdata.length > 0) {

                        _rawdata = "";
                        for (int i = 0; i < rawdata.length; i++) {
                            _rawdata += String.format("0x%02X ", (int) rawdata[i] & 0xFF);
                        }

                        // mTvResult.setText("data: " + barcode + " \ntype: " + type + " \nraw: " + strRawData);


                    } else {
                        //mTvResult.setText("data: " + barcode + " type: " + type);
                    }

                    _ScannerReceiver_Listner.OnBarcode(_barcode,
                            _type,
                            _module,
                            _rawdata,
                            _length,
                            _decCount);


                } else {
                    int nSymbol = intent.getExtras().getInt("symbology", -1);
                    int nValue = intent.getExtras().getInt("value", -1);

                    Log.i(TAG, "getSymbology [" + nSymbol + "][" + nValue + "]");

                    if (nSymbol != -1) {
                        // edSymNum.setText(Integer.toString(nSymbol));
                        // edValNum.setText(Integer.toString(nValue));
                    }
                }

            } else if (intent.getAction().equals(ScannerReceiver_Values.SCANNER_ACTION_IS_ENABLE_ANSWER)) {
                boolean bEnable = intent.getBooleanExtra(ScannerReceiver_Values.SCANNER_EXTRA_IS_ENABLE_ANSWER, false);

                Log.i(TAG, "is enable scanner [" + bEnable + "]");
            }
        }


    };

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(BarcodeIntentBroadcast);
        super.onDestroy();
    }

}
