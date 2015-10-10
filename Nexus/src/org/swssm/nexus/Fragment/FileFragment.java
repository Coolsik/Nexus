package org.swssm.nexus.Fragment;

import org.swssm.nexus.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FileFragment extends Fragment {
	
	OnFileButtonListener mListener;
	Button mBtn_Download;
	Button mBtn_Play;
	
	public interface OnFileButtonListener {
		public void OnFileButtonEvent(int viewID);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_file, container, false);
		
		mBtn_Download = (Button) v.findViewById(R.id.btn_torrent);
		mBtn_Play = (Button) v.findViewById(R.id.btn_filestreaming);
		
		mBtn_Download.setOnClickListener(listener);
		mBtn_Play.setOnClickListener(listener);
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mListener=(OnFileButtonListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException
				("activity must implement OnFileButtonListener");
		}
	}
	
	View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mListener.OnFileButtonEvent(v.getId());
		}
	};
}
