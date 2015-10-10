package org.swssm.nexus.Activity;

import java.util.List;

import org.swssm.nexus.R;
import org.swssm.nexus.Fragment.FileFragment;
import org.swssm.nexus.Fragment.FileListFragment;
import org.swssm.nexus.Fragment.HDTVFragment;
import org.swssm.nexus.Fragment.MainFragment;
import org.swssm.nexus.Fragment.SideMenuFragment;
import org.swssm.nexus.Fragment.TorrentFragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements SideMenuFragment.OnSideMenuEventListener, FileFragment.OnFileButtonListener {
	
	public static final int HDTV_SCHEDULE = 0;
	public static final int IPTV_SCHEDULE = 1;
	
	// Intent Flag
	public static final String TYPE = "type";
	public static final int HDTV = 0;
	public static final int IPTV = 1;
	
	Fragment mSideMenu;
	Fragment mContents;
	FragmentManager mFm;
	FragmentTransaction mTr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mFm=getFragmentManager();
		mSideMenu=mFm.findFragmentById(R.id.fragment_sidemenu);
		mContents = mFm.findFragmentById(R.id.fl_contents);
		
		mTr=mFm.beginTransaction();
		mTr.hide(mSideMenu);
		mTr.commit();
		
		changeFragment(new MainFragment());
	}
	
	public void mOnClick(View v) {
		switch(v.getId()) {
		
		// 타이틀바 메뉴 버튼
		case R.id.btn_menu:
			mTr=mFm.beginTransaction();
			if(mSideMenu.isVisible()) {
				mTr.hide(mSideMenu);
			} else {
				mTr.show(mSideMenu);
			}
			
			mTr.commit();
			break;
			
		// 타이틀바 셋팅 버튼
		case R.id.btn_setting:
			Toast.makeText(this, "Click Setting Menu", Toast.LENGTH_SHORT).show();
			break;
			
		// 타이틀바 스케줄 버튼
		case R.id.btn_schedule:
			showScheduleDlg();
			break;
		}
	}
	
	private void showScheduleDlg() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setItems(R.array.schedule, listener);
		builder.show();
	}
	
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			//String[] schedule = getResources().getStringArray(R.array.schedule);
			//Toast.makeText(MainActivity.this, schedule[which] + ", " + which, Toast.LENGTH_SHORT).show();
			
			mTr=mFm.beginTransaction();
			switch(which) {
			case HDTV_SCHEDULE:
				break;
				
			case IPTV_SCHEDULE:
				break;
			}
			mTr.commit();
		}
	};

	@Override
	public void onSideMenuEvent(int viewID) {
		
		switch(viewID) {
		case R.id.btn_hdtv:
			changeFragment(new HDTVFragment());
			break;
			
		case R.id.btn_iptv:
			if(isRunningProcess(this, "org.swssm.nexusPlayer")) {
				ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE); 
				am.killBackgroundProcesses("org.swssm.nexusPlayer");
			}
			
			Intent intent = getPackageManager().getLaunchIntentForPackage("org.swssm.nexusPlayer");
			intent.putExtra(TYPE, IPTV);
			startActivity(intent);
			break;
			
			
		case R.id.btn_file:
			changeFragment(new FileFragment());
			break;
		}
	}
	
	private void changeFragment(Fragment fragment) {
		if(mContents != null) {
			Log.d("lueseypid", "file");
			mTr = mFm.beginTransaction();
			mTr.remove(mContents);
			mTr.commit();
		}
		
		mContents = fragment;
		mTr=mFm.beginTransaction();
		mTr.add(R.id.fl_contents, mContents, "FILE");
		mTr.commit();
	}
	
	public static boolean isRunningProcess(Context context, String packageName) {
		 
        boolean isRunning = false;
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);                     
        List<RunningAppProcessInfo> list = am.getRunningAppProcesses();
 
       for(RunningAppProcessInfo rap : list) {                               
    	   if(rap.processName.equals(packageName)) {                                  
				isRunning = true;    
				break;
            }
        }
       return isRunning;
    }

	@Override
	public void OnFileButtonEvent(int viewID) {
		switch(viewID) {
		case R.id.btn_torrent:
			
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.155:8086/gui/"));
			startActivity(intent);
			//changeFragment(new TorrentFragment());
			break;
			
		case R.id.btn_filestreaming:
			changeFragment(new FileListFragment());
			break;
		}
	}
}
