package com.swssm.NexusLib.network;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.io.Serializable;
import java.net.MalformedURLException;

import org.json.JSONObject;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Network implements IOCallback, Serializable {

	private static final long serialVersionUID = 5183400537166819297L;
	
	Handler mHandler;
	SocketIO mSocketIO;
	String mUrl;
	String mMacAddress;
	
	public Network(Context context, String url, Handler handler) {
		try {
			mUrl = url;
			mSocketIO = new SocketIO(url);
			mHandler = handler;
			
			WifiManager wifi = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
			mMacAddress = wifi.getConnectionInfo().getMacAddress();
			
		} catch (MalformedURLException e) {
			Log.d("lueseypid", "Exception : " + e.getMessage());
		}
	}
	
	public void connect() {
		if(mSocketIO.isConnected()) {
			Log.d("lueseypid", "socket.io already connected");
			return;
		}
		mSocketIO.connect(this);
	}
	
	public void disconnect() {
		if(!mSocketIO.isConnected()) {
			Log.d("lueseypid", "socket.io already disconnected");
			return;
		}
		mSocketIO.disconnect();
	}

	
	// Implements from IOCallback
	@Override
	public void on(String event, IOAcknowledge ack, Object... arg2) {
		Log.d("lueseypid", "on");
	}

	@Override
	public void onConnect() {
		Log.d("lueseypid", "onConnect");
	}

	@Override
	public void onDisconnect() {
		Log.d("lueseypid", "onDisconnect");
	}

	@Override
	public void onError(SocketIOException arg0) {
		Log.e("lueseypid", "onError : " + arg0.getMessage());
		Log.e("lueseypid", "onError : " + arg0.toString());
	}

	@Override
	public void onMessage(String arg0, IOAcknowledge arg1) {}

	@Override
	public void onMessage(JSONObject arg0, IOAcknowledge arg1) {}
	
	void sendHandlerMessage(int what, ResponseJsonObject response) {
		Message msg = new Message();
		msg.what = what;
		msg.obj = response;
		mHandler.sendMessage(msg);
	}
	
	void sendHandlerMessage(int what, Object obj) {
		Message msg = new Message();
		msg.what = what;
		msg.obj = obj;
		mHandler.sendMessage(msg);
	}
}
