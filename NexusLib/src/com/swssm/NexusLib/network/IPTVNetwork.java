package com.swssm.NexusLib.network;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.IOAcknowledge;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class IPTVNetwork extends Network {

	private static final long serialVersionUID = 5398293867494480189L;
	
	public IPTVNetwork(Context context, String url, Handler handler) {
		super(context, url, handler);
		Log.d("lueseypid", "IPTVNetwork Constructor");
	}

	@Override
	public void onMessage(String object, IOAcknowledge ack) {
		Log.d("lueseypid", "onMessage(String" + object);
		
		JSONObject returnValue;
		try {
			returnValue = new JSONObject(object);
			int return_type = returnValue.getInt(EnvData.JSON_KEY.JSON_RETURN_TYPE);
			
			switch(return_type) {
			case EnvData.JSON_VALUE.REQUEST_IPTV_STREAMING:
				responseStreaming(returnValue);
				break;
			}
			
			

		} catch (JSONException e) {
			Log.e("lueseypid", "Exception : " + e.getMessage());
		}
	}
	
	public void requestStreaming() {
		JSONObject requestObject = new JSONObject();
		
		try {
			requestObject.put(EnvData.JSON_KEY.JSON_FUNCTION_CODE, EnvData.JSON_VALUE.REQUEST_IPTV_STREAMING);
			requestObject.put(EnvData.JSON_KEY.JSON_MAC_ADDRESS, mMacAddress);
		} catch (JSONException e) {
			Log.e("lueseypid", "Exception : " + e.getMessage());
		}
		
		mSocketIO.send(requestObject.toString());
	}
	
	public void responseStreaming(JSONObject returnValue) throws JSONException {
		int result = returnValue.getInt(EnvData.JSON_KEY.JSON_RESULT);
		int return_type = returnValue.getInt(EnvData.JSON_KEY.JSON_RETURN_TYPE);
		boolean isSuperUser = returnValue.getBoolean(EnvData.JSON_KEY.JSON_IS_SUPERUSER);
		
		ResponseJsonObject response = new ResponseJsonObject(result, return_type, isSuperUser);
		sendHandlerMessage(EnvData.EVENT_HANDLER.STREAMING_START, response);
	}
	
	public void requestRemoteEvent(int eventCode) {
		JSONObject requestObject = new JSONObject();
		
		try {
			requestObject.put(EnvData.JSON_KEY.JSON_FUNCTION_CODE, EnvData.JSON_VALUE.REQUEST_REMOTE_CONTROL_EVENT);
			requestObject.put(EnvData.JSON_KEY.JSON_EVENT_CODE, eventCode);
		} catch (JSONException e) {
			Log.e("lueseypid", "Exception : " + e.getMessage());
		}
		
		mSocketIO.send(requestObject.toString());
	}
}
