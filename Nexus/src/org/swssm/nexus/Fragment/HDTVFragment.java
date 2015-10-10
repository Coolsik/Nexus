package org.swssm.nexus.Fragment;

import java.util.HashMap;

import org.swssm.nexus.R;
import org.swssm.nexus.Dialog.ChannelInfoActivity;
import org.swssm.nexus.TimeTable.HDTVChannelAdapter;
import org.swssm.nexus.TimeTable.HdtvTimeTable;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.swssm.NexusLib.Object.Channel;
import com.swssm.NexusLib.Object.TvPrograme;
import com.swssm.NexusLib.network.EnvData;
import com.swssm.NexusLib.network.HDTVNetwork;
import com.swssm.NexusLib.network.ResponseJsonObject;

public class HDTVFragment extends Fragment {
	
	public final static int TIMETAGBLE_PARSING_COMPLETE=2;
	
	//Intent Flag
	public final static String TV_PROGRAME = "tv_programe";
	
	//public final static int 
	public final static String ServerURL = "http://192.168.0.155:3030";
	
	HdtvTimeTable mTimeTable;
	ProgressDialog mProgressDlg;
	ListView mHDTVChannelList;
	HDTVChannelAdapter mAdapter;
	HDTVNetwork mHDTVNetwork;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_hdtv, container, false);
		
		mHDTVChannelList = (ListView) root.findViewById(R.id.lv_hdtvchannel);
		return root;
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case TIMETAGBLE_PARSING_COMPLETE:
				requestChannelList();
				break;
				
			case EnvData.EVENT_HANDLER.CHANNEL_LIST_COMPLETE:
				Log.d("lueseypid", "CHANNEL_LIST_COMPLETE");
				if(mProgressDlg!=null) {
					mProgressDlg.dismiss();
				}
				ResponseJsonObject response = (ResponseJsonObject)msg.obj;
				
				if(response.getResult() == EnvData.JSON_VALUE.SUCCESS) {
					setChannelList(response);
				} else {
					Toast.makeText(getActivity(), "체널 리스트 수신에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		
		if(mTimeTable == null) {
			mTimeTable = new HdtvTimeTable(mHandler);
		}
		/*
		if(mTimeTable.getHdtvTimeTable()==null) {
			mProgressDlg = new ProgressDialog(getActivity());
			mProgressDlg.setMessage("체널 정보를 수신중 입니다.");
			mProgressDlg.setCancelable(true); 
			mProgressDlg.show();
			mTimeTable.start();
		}
		else {
			
		}
		*/
		requestChannelList();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(mHDTVNetwork!=null) {
			mHDTVNetwork.disconnect();
			mHDTVNetwork=null;
		}
	}
	public void requestChannelList() {
		Log.d("lueseypid", "requestChannelList");
		mHDTVNetwork = new HDTVNetwork(getActivity(), ServerURL, mHandler);
		mHDTVNetwork.connect();
		mHDTVNetwork.requestChannelList();
	} 
	
	public void setChannelList(ResponseJsonObject response) {
		HashMap<String, Channel> channelList = mTimeTable.getHdtvTimeTable();
		//HashMap<String, Channel> channelList = null;
		if(channelList == null) {
			Log.e("lueseypid", "체널 수신에 실패 하였습니다.");
			//Toast.makeText(getActivity(), "체널 수신에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		else if(channelList.size() == 0) {
			Log.i("lueseypid", "수신된 체널이 없습니다.");
			//Toast.makeText(getActivity(), "수신된 체널이 없습니다.", Toast.LENGTH_SHORT).show();
			return;
		} 
		//Toast.makeText(getActivity(), channelList.size() + "개의 체널을 수신하였습니다. ", Toast.LENGTH_SHORT).show();
		
		if(response.getResult() == EnvData.JSON_VALUE.FAIL) {
			Log.i("lueseypid", "수신된 체널이 없습니다.");
			return;
		}
		
		mAdapter = new HDTVChannelAdapter(getActivity(), channelList, response.getIsAvailable());
		mHDTVChannelList.setAdapter(mAdapter);
		mHDTVChannelList.setOnItemClickListener(listener);
	}
	
	OnItemClickListener listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Channel channel = (Channel) parent.getItemAtPosition(position);
			TvPrograme tvPrograme = channel.currentTvPrograme();
			
			Intent intent = new Intent(getActivity(), ChannelInfoActivity.class);
			intent.putExtra(TV_PROGRAME, tvPrograme);
			startActivity(intent);
		}
	};
}
