package com.m3.m3sdk_demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.m3.sdk.scannerlib.Barcode;
import com.m3.sdk.scannerlib.BarcodeListener;
import com.m3.sdk.scannerlib.BarcodeManager;

import net.m3mobile.app.scannerservicehoney.IScannerServiceHoneywell;
import net.m3mobile.app.scannerservicehoney.SymbolConfig;
import net.m3mobile.app.scannerservicehoney.SymbologyValue;
import net.m3mobile.app.scannerservicehoney.SymbologyValue.*;

public class ScanHoneyActivity extends Activity implements View.OnClickListener, ServiceConnection {

    // lib
    private Barcode mBarcode = null;
    private BarcodeListener mListener = null;
    private BarcodeManager mManager = null;

    //ui
    private TextView mTvResult = null;

    private final static String TAG = "ScanHoneyActivity";
    private IScannerServiceHoneywell mHService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_honey);

        mBarcode = new Barcode(this);
        mManager = new BarcodeManager(this);

        mainScreen();
        bindScannerService();

        mListener = new BarcodeListener() {

            @Override
            public void onGetSymbology(int nSymbol, int nVal) {
                Log.i(TAG, "onGetSymbology result="+nSymbol + ", "+ nVal);
            }

            @Override
            public void onBarcode(String strBarcode) {
                Log.i(TAG,"result="+strBarcode);
                // mTvResult.setText(strBarcode);
            }

            @Override
            public void onBarcode(String barcode, String codeType) {
                Log.i(TAG,"result="+barcode);
                mTvResult.setText("data: " + barcode + " type: " + codeType);
            }

        };

        mManager.addListener(mListener);

    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy() -- ");

        unbindService(this);
        mManager.removeListener(mListener);
        mManager.dismiss();

        super.onDestroy();
    }


    protected void mainScreen()
    {
        Button btnStart = (Button)findViewById(R.id.startread);
        btnStart.setOnClickListener(this);
        Button btnStop = (Button)findViewById(R.id.stopread);
        btnStop.setOnClickListener(this);
        Button btnEnable = (Button)findViewById(R.id.enable);
        btnEnable.setOnClickListener(this);
        Button btnDisable = (Button)findViewById(R.id.disable);
        btnDisable.setOnClickListener(this);
        Button btnSymbologies = (Button)findViewById(R.id.btnTestSymbologies);
        btnSymbologies.setOnClickListener(this);

        mTvResult = (TextView)findViewById(R.id.scanresult);
    }

    public void bindScannerService(){
        Intent intent = null;
        intent = new Intent("net.m3mobile.app.scannerservicehoney.start");
        intent.setPackage("net.m3mobile.app.scannerservicehoney");
        boolean bBind = bindService(intent,this, Context.BIND_AUTO_CREATE | Context.BIND_ABOVE_CLIENT);
        Log.d(TAG, "bindScannerService " + bBind);
    }
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d(TAG, "onServiceConnected " + iBinder.getClass().getSimpleName());

        mHService = IScannerServiceHoneywell.Stub.asInterface(iBinder);

        // test aidl
        try {
            /**
             * end char mode
             public static final int END_CHAR_ENTER = 0;
             public static final int END_CHAR_SPACE = 1;
             public static final int END_CHAR_TAB = 2;
             public static final int END_CHAR_KEY_ENTER = 3;
             public static final int END_CHAR_KEY_SPACE = 4;
             public static final int END_CHAR_KEY_TAB = 5;
             public static final int END_CHAR_NONE = 6;
             */
            mHService.setEndCharMode(1);
            mHService.setPrefix("pre_");
            mHService.setPostfix("_suffix");
            /**
             * SOUND MODE
             public static final int SOUND_NONE = 0;
             public static final int SOUND_BEEP = 1;
             public static final int SOUND_DING_DONG=2;
             */
            mHService.setSoundMode(1);
            mHService.setVibration(true);

            /**
             * OUTPUT MODE
             public static final int OUTPUT_DIRECT = 0;
             public static final int OUTPUT_EMU_KEY = 1;
             public static final int OUTPUT_CLIPBOARD = 2;
             */
            mHService.setOutputMode(1);
            /**
             * READ MODE
             public static final int READ_ASYNC = 0;
             public static final int READ_SYNC = 1;
             public static final int READ_CONTINUE = 2;
             */
            mHService.setReadMode(1);
            mHService.setScannerTriggerMode(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d(TAG, "onServiceDisconnected " + componentName.getClassName());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        try {
            if(id == R.id.startread){
                mHService.decodeStart();
            }else if(id == R.id.stopread){
                mHService.decodeStop();
            }else if(id == R.id.enable){
                mHService.setScanner(true);
            }else if(id == R.id.disable){
                mHService.setScanner(false);
            }else if(id == R.id.btnTestSymbologies){
                testHoneywellSymbologyConfig();
            }
        } catch (RemoteException e) {
            Log.d(TAG, "onClick error");
            e.printStackTrace();
        }

    }

    private void testHoneywellSymbologyConfig(){

        Log. d(TAG, "testHoneywellSymbologyConfig++");

        int flags = 0;											// flags config
        int min = 0;											// minimum length config
        int max = 0;											// maximum length config
        int postal_config = 0;									// postal config
        String temp;											// temp string for converting string to int
        SymbolConfig symConfig = new SymbolConfig(0);		// symbology config
        int min_default, max_default;
        String strMinDefault = null;
        String strMaxDefault = null;
        boolean bNotSupported = false;


        for(int i = 0; i < SymbologyID.SYM_ALL; i++)
        {
            symConfig.symID = i;								// symID
            flags = 0;											// reset the flags

            // Set appropriate sym config mask...
            switch(i)
            {
                // Flag & Range:
                case SymbologyID.SYM_AZTEC:
                case SymbologyID.SYM_CODABAR:
                case SymbologyID.SYM_CODE11:
                case SymbologyID.SYM_CODE128:
                case SymbologyID.SYM_GS1_128:
                case SymbologyID.SYM_CODE39:
                    //case SymbologyID.SYM_CODE49: 		// not supported
                case SymbologyID.SYM_CODE93:
                case SymbologyID.SYM_COMPOSITE:
                case SymbologyID.SYM_DATAMATRIX:
                case SymbologyID.SYM_INT25:
                case SymbologyID.SYM_MAXICODE:
                case SymbologyID.SYM_MICROPDF:
                case SymbologyID.SYM_PDF417:
                case SymbologyID.SYM_QR:
                case SymbologyID.SYM_RSS:
                case SymbologyID.SYM_IATA25:
                case SymbologyID.SYM_CODABLOCK:
                case SymbologyID.SYM_MSI:
                case SymbologyID.SYM_MATRIX25:
                case SymbologyID.SYM_KOREAPOST:
                case SymbologyID.SYM_STRT25:
                    //case SymbologyID.SYM_PLESSEY: 	// not supported
                case SymbologyID.SYM_CHINAPOST:
                case SymbologyID.SYM_TELEPEN:
                    //case SymbologyID.SYM_CODE16K: 	// not supported
                    //case SymbologyID.SYM_POSICODE:	// not supported
                case SymbologyID.SYM_HANXIN:
                    //case SymbologyID.SYM_GRIDMATRIX:	// not supported
                    try
                    {
                        symConfig = mHService.getSymbologyConfig(symConfig.symID); // gets the current symConfig
                        Log.i(TAG, "getSymbologyConfig symID: [" + symConfig.symID + "] Mask [" + symConfig.Mask + "] Flags [" + symConfig.Flags
                                + "] Min [" + symConfig.MinLength + "] Max [" + symConfig.MaxLength + "]");
                    }
                    catch(Exception e)
                    {
                        Log.e(TAG, "getSymbologyConfig failed: " + i);
                        e.printStackTrace();
                    }
                    symConfig.Mask = SymbologyFlags.SYM_MASK_FLAGS | SymbologyFlags.SYM_MASK_MIN_LEN | SymbologyFlags.SYM_MASK_MAX_LEN;
                    break;
                // Flags Only:
                case SymbologyID.SYM_EAN8:
                case SymbologyID.SYM_EAN13:
                case SymbologyID.SYM_POSTNET:
                case SymbologyID.SYM_UPCA:
                case SymbologyID.SYM_UPCE0:
                case SymbologyID.SYM_UPCE1:
                case SymbologyID.SYM_ISBT:
                case SymbologyID.SYM_BPO:
                case SymbologyID.SYM_CANPOST:
                case SymbologyID.SYM_AUSPOST:
                case SymbologyID.SYM_JAPOST:
                case SymbologyID.SYM_PLANET:
                case SymbologyID.SYM_DUTCHPOST:
                case SymbologyID.SYM_TLCODE39:
                case SymbologyID.SYM_TRIOPTIC:
                case SymbologyID.SYM_CODE32:
                case SymbologyID.SYM_COUPONCODE:
                case SymbologyID.SYM_USPS4CB:
                case SymbologyID.SYM_IDTAG:
                    //case SymbologyID.SYM_LABEL:		// not supported
                case SymbologyID.SYM_US_POSTALS1:
                    try{
                        symConfig = mHService.getSymbologyConfig(symConfig.symID); // gets the current symConfig
                        Log.i(TAG, "getSymbologyConfig symID: [" + symConfig.symID + "] Mask [" + symConfig.Mask + "] Flags [" + symConfig.Flags + "] ");

                    }catch(Exception e){
                        Log.e(TAG, "getSymbologyConfig failed: " + i);
                        e.printStackTrace();
                    }
                    symConfig.Mask = SymbologyFlags.SYM_MASK_FLAGS;
                    break;
                default:
                    bNotSupported = true;
                    break;
            }

            // Set symbology config...
            switch(i)
            {
                case SymbologyID.SYM_AZTEC:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // "sym_aztec_enable"
                    // min, max
                    min = symConfig.MinLength; // "sym_aztec_min"
                    max = symConfig.MaxLength; // "sym_aztec_max"
                    break;
                case SymbologyID.SYM_CODABAR:
                    // enable, check char, start/stop transmit, codabar concatenate
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // "sym_codabar_enable"
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_ENABLE : 0; // "sym_codabar_check_enable"
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT : 0; // "sym_codabar_check_transmit_enable"
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_START_STOP_XMIT) > 0 ? SymbologyFlags.SYMBOLOGY_START_STOP_XMIT : 0; // sym_codabar_start_stop_transmit_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CODABAR_CONCATENATE) > 0 ? SymbologyFlags.SYMBOLOGY_CODABAR_CONCATENATE : 0; // sym_codabar_concatenate_enable
                    // min, max
                    min = symConfig.MinLength; // sym_codabar_min
                    max = symConfig.MaxLength; // sym_codabar_max
                    break;
                case SymbologyID.SYM_CODE11:
                    // enable, check char
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_code11_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_ENABLE : 0; // sym_code11_check_enable
                    // min, max
                    min = symConfig.MinLength; // sym_code11_min
                    max = symConfig.MaxLength; // sym_code11_max
                    break;
                case SymbologyID.SYM_CODE128:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_code128_enable
                    // min, max
                    min = symConfig.MinLength; // sym_code128_min
                    max = symConfig.MaxLength; // sym_code128_max
                    break;
                case SymbologyID.SYM_GS1_128:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_gs1_128_enable
                    // min, max
                    min = symConfig.MinLength; // sym_gs1_128_min
                    max = symConfig.MaxLength; // sym_gs1_128_max
                    break;
                case SymbologyID.SYM_CODE39:
                    // enable, check char, start/stop transmit, append, full ascii
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_code39_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_ENABLE : 0; // sym_code39_check_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT : 0; // sym_code39_check_transmit_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_START_STOP_XMIT) > 0 ? SymbologyFlags.SYMBOLOGY_START_STOP_XMIT : 0; // sym_code39_start_stop_transmit_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE_APPEND_MODE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE_APPEND_MODE : 0; // sym_code39_append_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE_FULLASCII) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE_FULLASCII : 0; // sym_code39_fullascii_enable
                    // min, max
                    min = symConfig.MinLength; // sym_code39_min
                    max = symConfig.MaxLength; // sym_code39_max
                    break;
                case SymbologyID.SYM_CODE49:
                case SymbologyID.SYM_PLESSEY:
                case SymbologyID.SYM_CODE16K:
                case SymbologyID.SYM_POSICODE:
                case SymbologyID.SYM_LABEL:
                    // not supported
                    break;
                case SymbologyID.SYM_CODE93:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_code93_enable
                    // min, max
                    min = symConfig.MinLength; // sym_code93_min
                    max = symConfig.MaxLength; // sym_code93_max
                    break;
                case SymbologyID.SYM_COMPOSITE:
                    // enable, composit upc
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_composite_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_COMPOSITE_UPC) > 0 ? SymbologyFlags.SYMBOLOGY_COMPOSITE_UPC : 0; // sym_composite_upc_enable
                    // min, max
                    min = symConfig.MinLength; // sym_composite_min
                    max = symConfig.MaxLength; // sym_composite_max
                    break;
                case SymbologyID.SYM_DATAMATRIX:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_datamatrix_enable
                    // min, max
                    min = symConfig.MinLength; // sym_datamatrix_min
                    max = symConfig.MaxLength; // sym_datamatrix_max
                    break;
                case SymbologyID.SYM_EAN8:
                    // enable, check char transmit, addenda separator, 2 digit addena, 5 digit addena, addena required
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_ean8_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT : 0; // sym_ean8_check_transmit_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ADDENDA_SEPARATOR) > 0 ? SymbologyFlags.SYMBOLOGY_ADDENDA_SEPARATOR : 0; // sym_ean8_addenda_separator_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_2_DIGIT_ADDENDA) > 0 ? SymbologyFlags.SYMBOLOGY_2_DIGIT_ADDENDA: 0; // sym_ean8_2_digit_addenda_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_5_DIGIT_ADDENDA) > 0 ? SymbologyFlags.SYMBOLOGY_5_DIGIT_ADDENDA : 0; // sym_ean8_5_digit_addenda_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ADDENDA_REQUIRED) > 0 ? SymbologyFlags.SYMBOLOGY_ADDENDA_REQUIRED : 0; // sym_ean8_addenda_required_enable
                    break;
                case SymbologyID.SYM_EAN13:
                    // enable, check char transmit, addenda separator, 2 digit addena, 5 digit addena, addena required
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_ean13_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT : 0; // sym_ean13_check_transmit_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ADDENDA_SEPARATOR) > 0 ? SymbologyFlags.SYMBOLOGY_ADDENDA_SEPARATOR : 0; // sym_ean13_addenda_separator_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_2_DIGIT_ADDENDA) > 0 ? SymbologyFlags.SYMBOLOGY_2_DIGIT_ADDENDA: 0; // sym_ean13_2_digit_addenda_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_5_DIGIT_ADDENDA) > 0 ? SymbologyFlags.SYMBOLOGY_5_DIGIT_ADDENDA : 0; // sym_ean13_5_digit_addenda_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ADDENDA_REQUIRED) > 0 ? SymbologyFlags.SYMBOLOGY_ADDENDA_REQUIRED : 0; // sym_ean13_addenda_required_enable
                    break;
                case SymbologyID.SYM_INT25:
                    // enable, check enable, check transmit
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_int25_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_ENABLE : 0; // sym_int25_check_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT : 0; // sym_int25_check_transmit_enable
                    // min, max
                    min = symConfig.MinLength; // sym_int25_min
                    max = symConfig.MaxLength; // sym_int25_max
                    break;
                case SymbologyID.SYM_MAXICODE:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_maxicode_enable
                    // min, max
                    min = symConfig.MinLength; // sym_maxicode_min
                    max = symConfig.MaxLength; // sym_maxicode_max
                    break;
                case SymbologyID.SYM_MICROPDF:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_micropdf_enable
                    // min, max
                    min = symConfig.MinLength; // sym_micropdf_min
                    max = symConfig.MaxLength; // sym_micropdf_max
                    break;
                case SymbologyID.SYM_PDF417:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_pdf417_enable
                    // min, max
                    min = symConfig.MinLength; // sym_pdf417_min
                    max = symConfig.MaxLength; // sym_pdf417_max
                    break;
                case SymbologyID.SYM_QR:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_qr_enable
                    // min, max
                    min = symConfig.MinLength; // sym_qr_min
                    max = symConfig.MaxLength; // sym_qr_max
                    break;
                case SymbologyID.SYM_HANXIN:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_hanxin_enable
                    // min, max
                    min = symConfig.MinLength; // sym_hanxin_min
                    max = symConfig.MaxLength; // sym_hanxin_max
                    break;
                case SymbologyID.SYM_RSS:
                    // rss enable, rsl enable, rse enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_RSS_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_RSS_ENABLE : 0; // sym_rss_rss_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_RSL_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_RSL_ENABLE : 0; // sym_rss_rsl_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_RSE_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_RSE_ENABLE : 0; // sym_rss_rse_enable
                    // min, max
                    min = symConfig.MinLength; // sym_rss_min
                    max = symConfig.MaxLength; // sym_rss_max
                    break;
                case SymbologyID.SYM_UPCA:
                    // enable, check transmit, sys num transmit, addenda separator, 2 digit addenda, 5 digit addenda, addenda required
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_upca_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT : 0; // sym_upca_check_transmit_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_NUM_SYS_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_NUM_SYS_TRANSMIT : 0; // sym_upca_sys_num_transmit_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ADDENDA_SEPARATOR) > 0 ? SymbologyFlags.SYMBOLOGY_ADDENDA_SEPARATOR : 0; // sym_upca_addenda_separator_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_2_DIGIT_ADDENDA) > 0 ? SymbologyFlags.SYMBOLOGY_2_DIGIT_ADDENDA : 0; // sym_upca_2_digit_addenda_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_5_DIGIT_ADDENDA) > 0 ? SymbologyFlags.SYMBOLOGY_5_DIGIT_ADDENDA : 0; // sym_upca_5_digit_addenda_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ADDENDA_REQUIRED) > 0 ? SymbologyFlags.SYMBOLOGY_ADDENDA_REQUIRED : 0; // sym_upca_addenda_required_enable

                    break;
                case SymbologyID.SYM_UPCE1:
                    // upce1 enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_UPCE1_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_UPCE1_ENABLE : 0; // sym_upce1_upce1_enable
                    break;
                case SymbologyID.SYM_UPCE0:
                    // enable, upce expanded, char char transmit, num sys transmit, addenda separator, 2 digit addenda, 5 digit addenda, addenda required
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_upce0_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_EXPANDED_UPCE) > 0 ? SymbologyFlags.SYMBOLOGY_EXPANDED_UPCE : 0; // sym_upce0_upce_expanded_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT : 0; // sym_upce0_check_transmit_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_NUM_SYS_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_NUM_SYS_TRANSMIT : 0; // sym_upce0_sys_num_transmit_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ADDENDA_SEPARATOR) > 0 ? SymbologyFlags.SYMBOLOGY_ADDENDA_SEPARATOR : 0; // sym_upce0_addenda_separator_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_2_DIGIT_ADDENDA) > 0 ? SymbologyFlags.SYMBOLOGY_2_DIGIT_ADDENDA : 0; // sym_upce0_2_digit_addenda_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_5_DIGIT_ADDENDA) > 0 ? SymbologyFlags.SYMBOLOGY_5_DIGIT_ADDENDA : 0; // sym_upce0_5_digit_addenda_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ADDENDA_REQUIRED) > 0 ? SymbologyFlags.SYMBOLOGY_ADDENDA_REQUIRED : 0; // sym_upce0_addenda_required_enable
                    break;
                case SymbologyID.SYM_ISBT:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_isbt_enable
                    break;
                case SymbologyID.SYM_IATA25:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_iata25_enable
                    // min, max
                    min = symConfig.MinLength; // sym_iata25_min
                    max = symConfig.MaxLength; // sym_iata25_max
                    break;
                case SymbologyID.SYM_CODABLOCK:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_codablock_enable
                    // min, max
                    min = symConfig.MinLength; // sym_codablock_min
                    max = symConfig.MaxLength; // sym_codablock_max
                    break;

                /* Post Symbology Config */
                case SymbologyID.SYM_POSTNET:
                    Log. i(TAG, "Configure SYM_POSTNET");
                    // enable
                    flags |= (postal_config == SymbologyValue.POSTNET) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;
                    Log. i(TAG, "SYM_POSTNET postal_config = " + postal_config);
                    Log. i(TAG, "SYM_POSTNET flags = " + flags);
                    // check transmit
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT : 0; // sym_postnet_check_transmit_enable
                    break;
                case SymbologyID.SYM_JAPOST:
                    // enable
                    flags |= (postal_config == SymbologyValue.JAPAN_POST) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;
                    break;
                case SymbologyID.SYM_PLANET:
                    // enable
                    flags |= (postal_config == SymbologyValue.PLANETCODE) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;
                    break;
                case SymbologyID.SYM_DUTCHPOST:
                    // enable
                    flags |= (postal_config == SymbologyValue.KIX) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;
                    break;
                case SymbologyID.SYM_US_POSTALS1:
                    // enable
                    flags |= (postal_config == SymbologyValue.US_POSTALS) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;
                    break;
                case SymbologyID.SYM_USPS4CB:
                    // enable
                    flags |= (postal_config == SymbologyValue.USPS_4_STATE) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;
                    break;
                case SymbologyID.SYM_IDTAG:
                    // enable
                    flags |= (postal_config == SymbologyValue.UPU_4_STATE) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;
                    break;
                case SymbologyID.SYM_BPO:
                    // enable
                    flags |= (postal_config == SymbologyValue.ROYAL_MAIL) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;
                    break;
                case SymbologyID.SYM_CANPOST:
                    // enable
                    flags |= (postal_config == SymbologyValue.CANADIAN) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;
                    break;
                case SymbologyID.SYM_AUSPOST:
                    // enable
                    flags |= (postal_config == SymbologyValue.AUS_POST) ? SymbologyFlags.SYMBOLOGY_ENABLE : 0;

                    // Interpret Mode
                    int sym_aus_interpret_mode = 0;
                    switch(sym_aus_interpret_mode)
                    {
                        // Numeric N Table:
                        case 1:
                            flags |= SymbologyFlags.SYMBOLOGY_AUS_POST_NUMERIC_N_TABLE;
                            break;
                        // Alphanumeric C Table:
                        case 2:
                            flags |= SymbologyFlags.SYMBOLOGY_AUS_POST_ALPHANUMERIC_C_TABLE;
                            break;
                        // Combination N & C Tables:
                        case 3:
                            flags |= SymbologyFlags.SYMBOLOGY_AUS_POST_COMBINATION_N_AND_C_TABLES;
                            break;
                        default:
                            break;
                    }
                    break;
                /* ===================== */

                case SymbologyID.SYM_MSI:
                    // enable, check transmit
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_msi_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT) > 0 ? SymbologyFlags.SYMBOLOGY_CHECK_TRANSMIT : 0; // sym_msi_check_transmit_enable
                    Log.i(TAG, "sym msi flags = " + flags);
                    // min, max
                    min = symConfig.MinLength; // sym_msi_min
                    max = symConfig.MaxLength; // sym_msi_max
                    break;
                case SymbologyID.SYM_TLCODE39:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_tlcode39_enable
                    break;
                case SymbologyID.SYM_MATRIX25:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_matrix25_enable
                    // min, max
                    min = symConfig.MinLength; // sym_matrix25_min
                    max = symConfig.MaxLength; // sym_matrix25_max
                    break;
                case SymbologyID.SYM_KOREAPOST:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_koreapost_enable
                    // min, max
                    min = symConfig.MinLength; // sym_koreapost_min
                    max = symConfig.MaxLength; // sym_koreapost_max
                    break;
                case SymbologyID.SYM_TRIOPTIC:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_trioptic_enable
                    break;
                case SymbologyID.SYM_CODE32:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_code32_enable
                    break;
                case SymbologyID.SYM_STRT25:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_strt25_enable
                    // min, max
                    min = symConfig.MinLength; // sym_strt25_min
                    max = symConfig.MaxLength; // sym_strt25_max
                    break;
                case SymbologyID.SYM_CHINAPOST:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_chinapost_enable
                    // min, max
                    min = symConfig.MinLength; // sym_chinapost_min
                    max = symConfig.MaxLength; // sym_chinapost_max
                    break;
                case SymbologyID.SYM_TELEPEN:
                    // enable, telepen old style
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_telepen_enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_TELEPEN_OLD_STYLE) > 0 ? SymbologyFlags.SYMBOLOGY_TELEPEN_OLD_STYLE : 0; // sym_telepen_telepen_old_style_enable
                    // min, max
                    min = symConfig.MinLength; // sym_telepen_min
                    max = symConfig.MaxLength; // sym_telepen_max
                    break;
                case SymbologyID.SYM_COUPONCODE:
                    // enable
                    flags |= (symConfig.Flags & SymbologyFlags.SYMBOLOGY_ENABLE) > 0 ? SymbologyFlags.SYMBOLOGY_ENABLE : 0; // sym_couponcode_enable
                    break;

                default:
                    symConfig.Mask = 0; // will not setSymbologyConfig
                    break;
            }

            if(bNotSupported)
            {
                bNotSupported = false; // // do nothing, but reset flag
            }

            if(symConfig.Mask == (SymbologyFlags.SYM_MASK_FLAGS | SymbologyFlags.SYM_MASK_MIN_LEN | SymbologyFlags.SYM_MASK_MAX_LEN) ) // Flags & Range
            {
                symConfig.Flags = flags;
                symConfig.MinLength = min;
                symConfig.MaxLength = max;
                try
                {
                    Log.i(TAG, "setSymbologyConfig symID: [" + symConfig.symID + "] Mask [" + symConfig.Mask + "] Flags [" + symConfig.Flags
                            + "] Min [" + symConfig.MinLength + "] Max [" + symConfig.MaxLength + "]");

                    mHService.setSymbologyConfig(symConfig);
                }
                catch (Exception e) {
                    Log.e(TAG, "setSymbologyConfig failed : " + i);
                    e.printStackTrace();
                }
            }
            else if(symConfig.Mask == (SymbologyFlags.SYM_MASK_FLAGS)) // Flag Only
            {
                symConfig.Flags = flags;
                try
                {
                    Log.i(TAG, "getSymbologyConfig symID: [" + symConfig.symID + "] Mask [" + symConfig.Mask + "] Flags [" + symConfig.Flags + "] ");
                    mHService.setSymbologyConfig(symConfig);
                }
                catch (Exception e)
                {
                    Log.e(TAG, "setSymbologyConfig failed : " + i);
                    e.printStackTrace();
                }
            }
            else
            {
                // invalid
            }
        }

        Log. d(TAG, "testHoneywellSymbologyConfig --");

    }
}
