package org.swssm.nexus.Fragment;

import org.swssm.nexus.R;
import org.swssm.nexus.Activity.MainActivity;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment{

	// Intent Flag
	public static final String TYPE = "type";
	public static final int HDTV = 0;
	public static final int IPTV = 1;
	
	Button mBtn_sbs;
	Button mBtn_kbs1;
	Button mBtn_kbs2;
	Button mBtn_mbc;
	Button mBtn_ebs;
	Button mBtn_iptv;
	Button mBtn_file;

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, container, false);
		
		mBtn_sbs = (Button) v.findViewById(R.id.btn_main_sbs);
		mBtn_kbs1 = (Button) v.findViewById(R.id.btn_main_kbs1);
		mBtn_kbs2 = (Button) v.findViewById(R.id.btn_main_kbs2);
		mBtn_mbc = (Button) v.findViewById(R.id.btn_main_mbc);
		mBtn_ebs  = (Button) v.findViewById(R.id.btn_main_ebs);
		
		mBtn_iptv  = (Button) v.findViewById(R.id.btn_main_iptv);
		mBtn_file  = (Button) v.findViewById(R.id.btn_main_file);
		
		mBtn_sbs.setOnClickListener(listener);
		mBtn_kbs1.setOnClickListener(listener);
		mBtn_kbs2.setOnClickListener(listener);
		mBtn_mbc.setOnClickListener(listener);
		mBtn_ebs.setOnClickListener(listener);
		mBtn_iptv.setOnClickListener(listener);
		mBtn_file.setOnClickListener(listener);
		
		return v;
	}
	
	View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.btn_main_sbs:
				break;

			case R.id.btn_main_kbs1:
				break;
				
			case R.id.btn_main_kbs2:
				break;
				
			case R.id.btn_main_mbc:
				break;
				
			case R.id.btn_main_ebs:
				break;
				
			case R.id.btn_main_iptv:
				if(MainActivity.isRunningProcess(getActivity(), "org.swssm.nexusplayer")) {
					ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE); 
					am.killBackgroundProcesses("org.swssm.nexusplayer");
				}
				
				Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("org.swssm.nexusplayer");
				intent.putExtra(TYPE, IPTV);
				startActivity(intent);
				break;
				
			case R.id.btn_main_file:
				break;
			}
		}
	};
	
}
