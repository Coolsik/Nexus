package org.swssm.nexusPlayer.RemoteControll;

import org.swssm.nexusPlayer.NexusPlayerActivity;
import org.swssm.nexusPlayer.R;

import android.view.View;
import android.view.View.OnClickListener;

import com.swssm.NexusLib.network.EnvData;
import com.swssm.NexusLib.network.IPTVNetwork;

public class RemoteControll implements OnClickListener {
	IPTVNetwork mIPTVNetwork;
	NexusPlayerActivity mActivity;
	
	public static final int[] btnIDList = {
		R.id.btn_ok, R.id.btn_left, R.id.btn_right, R.id.btn_down, R.id.btn_up, R.id.btn_Previous,
		R.id.btn_menu, R.id.btn_Exit,
		R.id.btn_num0, R.id.btn_num1, R.id.btn_num2, R.id.btn_num3, R.id.btn_num4, R.id.btn_num5,
		R.id.btn_num6, R.id.btn_num7, R.id.btn_num8, R.id.btn_num9, R.id.btn_asterisk, R.id.btn_sharp,
		R.id.navi, R.id.btn_channelUp, R.id.btn_VolumeUp, R.id.btn_channelDown, R.id.btn_VolumeDown,
		R.id.btn_vprevious, R.id.btn_playpause, R.id.btn_stop, R.id.btn_fast
	};
	
	public RemoteControll(NexusPlayerActivity activity, IPTVNetwork IPTVNetwork) { 
		mActivity = activity;
		mIPTVNetwork = IPTVNetwork;
	}
	
	public void setOnClickListener() {
		for(int i=0; i < btnIDList.length; i++) {
			mActivity.findViewById(btnIDList[i]).setOnClickListener(this);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_ok:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.OK);
			break;
			
		case R.id.btn_left:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Left);
			break;
			
		case R.id.btn_right:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Right);
			break;
			
		case R.id.btn_down:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Down);
			break;
			
		case R.id.btn_up:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Up);
			break;
			
		case R.id.btn_Previous:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Previous);
			break;
			
		case R.id.btn_menu:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Menu);
			break;
			
		case R.id.btn_Exit: 
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Exit);
			break;
		
		case R.id.btn_num0:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_0);
			break;
			
		case R.id.btn_num1:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_1);
			break;
			
		case R.id.btn_num2:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_2);
			break;
			
		case R.id.btn_num3:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_3);
			break;
			
		case R.id.btn_num4:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_4);
			break;
			
		case R.id.btn_num5:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_5);
			break;
			
		case R.id.btn_num6:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_6);
			break;
			
		case R.id.btn_num7:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_7);
			break;
			
		case R.id.btn_num8:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_8);
			break;
			
		case R.id.btn_num9:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.NUMBER_9);
			break;
			
		case R.id.btn_asterisk:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Asterisk);
			break;
			
		case R.id.btn_sharp:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Sharp);
			break;
				
		case R.id.btn_channelUp:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.ChannelUp);
			break;
			
		case R.id.btn_VolumeUp:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.VolumeUp);
			break;
			
		case R.id.btn_channelDown:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.ChannelDown);
			break;	
			
		case R.id.btn_VolumeDown:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.VolumeDown);
			break;
			
		case R.id.btn_vprevious:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Previous);
			break;
			
		case R.id.btn_playpause:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.PlayPause);
			break;
			
		case R.id.btn_stop:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.Stop);
			break;
			
		case R.id.btn_fast:
			mIPTVNetwork.requestRemoteEvent(EnvData.REMOTE_EVENT.FastForward);
			break;
		}
	}
}
