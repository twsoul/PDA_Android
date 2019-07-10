// 2015-10-06	v.1.0.1	전재영		최초 릴리즈
// 2016-02-15	v.1.0.2	전재영		바코드 리딩 시 코드 타입 알 수 있도록 수정
// 2016-09-28	v.1.2.0	전재영		Scanner Intent SDK 추가
// 2016-12-29	v.1.2.2  전재영		KeySDK 추가
// 2017-01-18	v.1.2.3	전재영		Scanner Intent SDK 에 Barcode Raw Data 받을 수 있도록 수정 - ScanEmul 1.3.0 부터 가능
// 2018-03-14   v.2.0.4 한재윤		BarcodeManager sendBarcode 수정 - SM15 통합스캔에뮬 변동사항 적용
// 2018-05-09	v.2.0.1 전재영		AIDL SDK 추가 ScanEmul 버전 2.0.2 이상 부터 사용 가능
// 2018-05-09	v.2.0.2 한재윤		1D AIDL SDK 추가
// 2018-07-23	v.2.0.3	전재영		N6600 2D SDK 추가
// 2018-10-08	v.2.0.4	한재윤		2D Scanner Intent SDK 에 Decode count 받을 수 있도록 수정 - ScanEmul 2.2.3 부터 가능

package com.m3.m3sdk_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnScanner = (Button)findViewById(R.id.button_demo_scanner);
		Button btnScannerIntent = (Button)findViewById(R.id.button_demo_scanner_intent);
		Button btnScannerAidl = (Button)findViewById(R.id.button_demo_scanner_aidl);
		Button btnScannerHoney = (Button)findViewById(R.id.button_demo_scanner_honey);
		Button btnKey = (Button)findViewById(R.id.button_demo_key);
	
		btnScannerIntent.setOnClickListener(this);
		btnScanner.setOnClickListener(this);
		btnKey.setOnClickListener(this);
		btnScannerAidl.setOnClickListener(this);
		findViewById(R.id.button_demo_scanner_1d_aidl).setOnClickListener(this);
		btnScannerHoney.setOnClickListener(this);

		if(Build.MODEL.equals("M3SM10")) {
			btnScannerIntent.setVisibility(View.GONE);
			btnKey.setVisibility(View.GONE);
		}
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.button_demo_scanner){
			startActivity(new Intent(MainActivity.this, com.m3.m3sdk_demo.ScannerActivity.class));		
		}else if(id == R.id.button_demo_scanner_intent){
			startActivity(new Intent(MainActivity.this, com.m3.m3sdk_demo.ScannerIntentActivity.class));
		}else if(id == R.id.button_demo_key){
			startActivity(new Intent(MainActivity.this, com.m3.m3sdk_demo.KeyActivity.class));			
		}else if(id == R.id.button_demo_scanner_aidl){
			startActivity(new Intent(MainActivity.this, Scanner2DAidlActivity.class));
		}else if(id == R.id.button_demo_scanner_1d_aidl){
			startActivity(new Intent(MainActivity.this, com.m3.m3sdk_demo.Scanner1DAidlActivity.class));
		}else if(id == R.id.button_demo_scanner_honey){
			startActivity(new Intent(MainActivity.this, com.m3.m3sdk_demo.ScanHoneyActivity.class));
		}

	}
}
