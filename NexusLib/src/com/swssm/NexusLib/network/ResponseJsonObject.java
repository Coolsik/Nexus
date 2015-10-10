package com.swssm.NexusLib.network;

import java.util.HashMap;

public class ResponseJsonObject {
	private int mResult;
	private int mReturn_type;
	private HashMap<String, Boolean> isAvailable;
	private boolean isSuperUser;
	
	public ResponseJsonObject(int result, int return_type, HashMap<String, Boolean> isAvailable) {
		mResult = result;
		mReturn_type = return_type;
		this.isAvailable = isAvailable;
	}
	
	public ResponseJsonObject(int result, int return_type, boolean isSuperUser) {
		mResult = result;
		mReturn_type = return_type;
		this.isSuperUser = isSuperUser;
	}

	public int getResult() {
		return mResult;
	}

	public int getReturn_type() {
		return mReturn_type;
	}

	public HashMap<String, Boolean> getIsAvailable() {
		return isAvailable;
	}

	public boolean isSuperUser() {
		return isSuperUser;
	}
}
