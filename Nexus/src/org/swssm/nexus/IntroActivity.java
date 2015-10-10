package org.swssm.nexus;

import org.swssm.nexus.Activity.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.widget.TextView;

public class IntroActivity extends Activity {
	
	private static final int HANDLE_MSG_WIFI_OFF=0;
	
	private static String PREF_SERVER_IP_ADDRESS="Ipaddr";
	
	IntroThread mIntroThread;
	ProgressDialog mSearchServerDlg;
	TextView mTv_IntroNotice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)   {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		mTv_IntroNotice=(TextView) findViewById(R.id.tv_intro_notice);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		//Wifi 연결 상태 확인
		if(checkWifiOn()) {
			String serverIpAddr=getServerIpAddr();
			if(serverIpAddr==null) {
				//mSearchServerDlg=new ProgressDialog(this);
				//mSearchServerDlg.setMessage("Nexus서버를 검색 중입니다.");
				//mSearchServerDlg.setCancelable(false);
				//mSearchServerDlg.setCancelable(true);
				//mSearchServerDlg.show();	
				
				connectNexusServer();
				
				mIntroThread=new IntroThread(mHandler);
				mHandler.postDelayed(mIntroThread, 3000);
			} else {
				//서버로 연결
				
			}
			
			//connectNexusServer();
		} else {
			//mIntroThread=new IntroThread(mHandler);
			//mHandler.postDelayed(mIntroThread, 3000);
		}

		
		//else {
			
		//}
		
	}
	
	private boolean checkWifiOn() {
		ConnectivityManager cm=(ConnectivityManager) getApplication()
				.getSystemService(CONNECTIVITY_SERVICE);
		
		NetworkInfo.State wifiState=cm.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).getState();
		
		if(wifiState!=NetworkInfo.State.CONNECTED) { 
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("WiFi 오");
			builder.setMessage("WiFi가 연결되지 않았습니다. 연결 하시겠습니까?");
			builder.setPositiveButton("확인", listener);
			builder.setNegativeButton("취소", listener);
			builder.show();;
			return false;
		}
		return true;
	}
	
	private String getServerIpAddr() {
		SharedPreferences pref=getSharedPreferences("setting", MODE_PRIVATE);
		return pref.getString(PREF_SERVER_IP_ADDRESS, null);
	}
	
	DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which) {
			case DialogInterface.BUTTON_POSITIVE:
				startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				finish();
				break;
			}
		}
	};
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch(msg.what) {
			case HANDLE_MSG_WIFI_OFF:
				break;
			}
		}
	};
	
	// 페이지 넘어가는 쓰레드
	class IntroThread implements Runnable {
		Handler handler;
		public IntroThread(Handler handler) {
			this.handler=handler;
		}
		
		@Override
		public void run() {
			Intent intent=new Intent(IntroActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mHandler.removeCallbacks(mIntroThread);
	}
	 
	// 서버와 연결
	public void connectNexusServer() {
		mTv_IntroNotice.setText("Nexus 서버와 연결중 입니다..");
	}
}