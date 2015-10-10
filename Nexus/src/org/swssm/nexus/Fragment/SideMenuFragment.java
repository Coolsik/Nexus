package org.swssm.nexus.Fragment;

import org.swssm.nexus.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SideMenuFragment extends Fragment {
	
	OnSideMenuEventListener mListener;
	Button mBtn_HDTV;
	Button mBtn_IPTV;
	Button mBtn_FILE;
	
	public interface OnSideMenuEventListener {
		public void onSideMenuEvent(int viewID);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_sidemenu, container, false);
					
		mBtn_HDTV=(Button) root.findViewById(R.id.btn_hdtv);
		mBtn_IPTV=(Button) root.findViewById(R.id.btn_iptv);
		mBtn_FILE=(Button) root.findViewById(R.id.btn_file);
		
		mBtn_HDTV.setOnClickListener(mClickListener);
		mBtn_IPTV.setOnClickListener(mClickListener);
		mBtn_FILE.setOnClickListener(mClickListener);
		return root;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mListener=(OnSideMenuEventListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException
				("activity must implement OnSideMenuEventListener");
		}
	}
	
	View.OnClickListener mClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int viewID=v.getId();
			switch(viewID) {
			case R.id.btn_hdtv:
				clickHDTVBtn(viewID);
				
				break;
				
			case R.id.btn_iptv:
				clickIPTVBtn(viewID);
				break;
				
			case R.id.btn_file:
				clickFILEBtn(viewID);
				break;
			}		
			mListener.onSideMenuEvent(viewID);
		}
	};
	
	public void clickHDTVBtn(int viewID) {
		mBtn_HDTV.setBackgroundResource(R.drawable.btn_hdtv_press);
		mBtn_IPTV.setBackgroundResource(R.drawable.btn_iptv_style);
		mBtn_FILE.setBackgroundResource(R.drawable.btn_file_style);
	}
	
	public void clickIPTVBtn(int viewID) {
		mBtn_IPTV.setBackgroundResource(R.drawable.btn_iptv_press);
		mBtn_HDTV.setBackgroundResource(R.drawable.btn_hdtv_style);
		mBtn_FILE.setBackgroundResource(R.drawable.btn_file_style);
	}
	
	public void clickFILEBtn(int viewID) {
		mBtn_IPTV.setBackgroundResource(R.drawable.btn_iptv_style);
		mBtn_HDTV.setBackgroundResource(R.drawable.btn_hdtv_style);
		mBtn_FILE.setBackgroundResource(R.drawable.btn_file_press);
	}
}
