package com.swssm.NexusLib.network;

import java.util.ArrayList;

import io.socket.IOAcknowledge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.swssm.NexusLib.Object.Directory;
import com.swssm.NexusLib.Object.File;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class FileStreamingNetwork extends Network {

	private static final long serialVersionUID = 4755237243461561273L;

	public FileStreamingNetwork(Context context, String url, Handler handler) {
		super(context, url, handler);
		Log.d("lueseypid", "FileStreamingNetwork Constructor");
	}

	
	@Override
	public void onMessage(String object, IOAcknowledge ack) {
		//Log.d("lueseypid", "onMessage(String" + object);
		
		
		JSONObject returnValue;
		try {
			returnValue = new JSONObject(object);
			int return_type = returnValue.getInt(EnvData.JSON_KEY.JSON_RETURN_TYPE);
			Log.d("lueseypid", "return_type : " + return_type);
			
			switch(return_type) {
			case EnvData.JSON_VALUE.REQUEST_FILE_LIST:
				responseFileList(returnValue);
			case EnvData.JSON_VALUE.REQUEST_FILE_STREAMING:
				responseFileStreaming(returnValue);
				break;
			}
			
			

		} catch (JSONException e) {
			Log.e("lueseypid", "Exception : " + e.getMessage());
		}
		
	}
	
	
	public void requestFileList(String path) {
		JSONObject requestObject = new JSONObject();
		
		try {
			requestObject.put(EnvData.JSON_KEY.JSON_FUNCTION_CODE, EnvData.JSON_VALUE.REQUEST_FILE_LIST);
			requestObject.put(EnvData.JSON_KEY.JSON_FOLDER_NAME, path);
			
		} catch (JSONException e) {
			Log.e("lueseypid", "Exception : " + e.getMessage());
		}
		
		mSocketIO.send(requestObject.toString());
	}
	
	public void responseFileList(JSONObject returnValue) throws JSONException {
		String parentDir = returnValue.getString(EnvData.JSON_KEY.JSON_PARENT_DIR);
		Log.d("lueseypid", parentDir);
		
		String presentDir = returnValue.getString(EnvData.JSON_KEY.JSON_PRESENT_DIR);
		Log.d("lueseypid", parentDir);
		
		ArrayList<String> dirList = new ArrayList<String>();
		JSONArray jsonDirList = returnValue.getJSONArray(EnvData.JSON_KEY.JSON_FOLDER_LIST);

		for(int i=0; i<jsonDirList.length(); i++) {
			dirList.add((String)jsonDirList.get(i));
		}
		
		ArrayList<File> fileList = new ArrayList<File>();
		JSONArray jsonFileList = returnValue.getJSONArray(EnvData.JSON_KEY.JSON_FILE_LIST);
		Log.d("lueseypid", "jsonFileList : " + jsonFileList.length());
		
		for(int i=0; i<jsonFileList.length(); i++) {
			String name = jsonFileList.getJSONObject(i).getString(EnvData.JSON_KEY.JSON_FILE_NAME);
			String extension = jsonFileList.getJSONObject(i).getString(EnvData.JSON_KEY.JSON_FILE_EXTENSION);
			fileList.add(new File(name, extension));
		}
		
		Directory dir = new Directory(presentDir, parentDir, dirList, fileList);
		sendHandlerMessage(EnvData.EVENT_HANDLER.FILE_LIST_COMPLETE, dir);
	}
	
	public void requestFileStreaming() {
		
	}
	
	public void responseFileStreaming(JSONObject returnValue) throws JSONException {
		
	}
}
