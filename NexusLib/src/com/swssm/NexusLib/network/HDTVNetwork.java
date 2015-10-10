package com.swssm.NexusLib.network;

import io.socket.IOAcknowledge;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.swssm.NexusLib.Object.Channel;

public class HDTVNetwork extends Network{

	private static final long serialVersionUID = -149919162466637981L;
	
	public HDTVNetwork(Context context, String url, Handler handler)  {
		super(context, url, handler);
		Log.d("lueseypid", "HDTVNetwork Constructor");
	}
	
	public void requestChannelList() {
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put(EnvData.JSON_KEY.JSON_FUNCTION_CODE, EnvData.JSON_VALUE.REQUEST_CHANNEL_LIST);
			requestObject.put(EnvData.JSON_KEY.JSON_MAC_ADDRESS, mMacAddress);
		} catch (JSONException e) {
			Log.e("lueseypid", "Exception : " + e.getMessage());
		}
		
		mSocketIO.send(requestObject.toString());
	}
	
	public void requestStreaming(Channel channel) {
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put(EnvData.JSON_KEY.JSON_FUNCTION_CODE, EnvData.JSON_VALUE.REQUEST_CHANNEL_STREAMING_URL);
			requestObject.put(EnvData.JSON_KEY.JSON_CHANNEL_ID, channel.getChannelName());
			requestObject.put(EnvData.JSON_KEY.JSON_MAC_ADDRESS, mMacAddress);
		} catch (JSONException e) {
			Log.e("lueseypid", "Exception : " + e.getMessage());
		}
		
		mSocketIO.send(requestObject.toString());
	}
	
	@Override
	public void onMessage(String object, IOAcknowledge ack) {
		Log.d("lueseypid", "onMessage(String" + object);
		
		JSONObject returnValue;
		try {
			returnValue = new JSONObject(object);
			int return_type = returnValue.getInt(EnvData.JSON_KEY.JSON_RETURN_TYPE);
			
			switch(return_type) {
			case EnvData.JSON_VALUE.REQUEST_CHANNEL_LIST:
				responseChannelList(returnValue);
				break;
				
			case EnvData.JSON_VALUE.REQUEST_CHANNEL_STREAMING_URL:
				responseStreaming(returnValue);
				break;
			}
			
			

		} catch (JSONException e) {
			Log.e("lueseypid", "Exception : " + e.getMessage());
		}
	}
	
	public void responseChannelList(JSONObject returnValue) throws JSONException {
		int result = returnValue.getInt(EnvData.JSON_KEY.JSON_RESULT);
		int return_type = returnValue.getInt(EnvData.JSON_KEY.JSON_RETURN_TYPE);
		JSONObject channel_list=returnValue.getJSONObject(EnvData.JSON_KEY.JSON_CHANNEL_LIST);
		
		HashMap<String, Boolean> isAvailable = new HashMap<String, Boolean>();
		
		for(int i=0; i<EnvData.CHANNEL_LIST.length; i++) {
			Boolean b = (Boolean) channel_list.get(EnvData.CHANNEL_LIST[i]);
			isAvailable.put(EnvData.CHANNEL_LIST[i], b);
			//Log.d("lueseypid", EnvData.CHANNEL_LIST[i]);
			//Log.d("lueseypid", "CHANNEL : " + b.booleanValue());
		}
		
		ResponseJsonObject response = new ResponseJsonObject(result, return_type, isAvailable);
		sendHandlerMessage(EnvData.EVENT_HANDLER.CHANNEL_LIST_COMPLETE, response);
	}
	
	public void responseStreaming(JSONObject returnValue) throws JSONException {
		int result = returnValue.getInt(EnvData.JSON_KEY.JSON_RESULT);
		int return_type = returnValue.getInt(EnvData.JSON_KEY.JSON_RETURN_TYPE);
		boolean isSuperUser = returnValue.getBoolean(EnvData.JSON_KEY.JSON_IS_SUPERUSER);
		
		ResponseJsonObject response = new ResponseJsonObject(result, return_type, isSuperUser);
		sendHandlerMessage(EnvData.EVENT_HANDLER.STREAMING_START, response);
	}
}
