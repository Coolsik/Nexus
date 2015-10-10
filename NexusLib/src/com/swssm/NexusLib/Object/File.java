package com.swssm.NexusLib.Object;

import org.swssm.nexuslib.R;

import android.util.Log;

public class File {
	private String mFileName;
	private String mExtension;
	private int mImageResource;
	
	public File(String mFileName, String mExtension) {
		super();
		this.mFileName = mFileName;
		this.mExtension = mExtension;
		setImageResource();
	}

	public String getFileName() {
		return mFileName;
	}

	public void setFileName(String mFileName) {
		this.mFileName = mFileName;
	}

	public String getExtension() {
		return mExtension;
	}

	public void setExtension(String mExtension) {
		this.mExtension = mExtension;
	}
	
	public int getImageResource() {
		 return mImageResource;
	}
	
	private void setImageResource() {
		Log.d("lueseypid", mExtension);
		
		if(mExtension.equals("3gp")) {
			mImageResource = R.drawable.gp3;
		}
		
		else if(mExtension.equals("asx")) {
			mImageResource = R.drawable.asx;
		}
		
		else if(mExtension.equals("avi")) {
			mImageResource = R.drawable.avi;
		}
		
		else if(mExtension.equals("flv")) {
			mImageResource = R.drawable.flv;
		}
		
		else if(mExtension.equals("m3u")) {
			mImageResource = R.drawable.m3u;
		}
		
		else if(mExtension.equals("m4v")) {
			mImageResource = R.drawable.m4v;
		}
		
		else if(mExtension.equals("mov")) {
			mImageResource = R.drawable.mov;
		}
		
		else if(mExtension.equals("mp4")) {
			mImageResource = R.drawable.mp4;
		}
		
		else if(mExtension.equals("mpeg")) {
			mImageResource = R.drawable.mpg;
		}
		
		else if(mExtension.equals("mpg")) {
			mImageResource = R.drawable.mpg;
		}
		
		else if(mExtension.equals("rm")) {
			mImageResource = R.drawable.rm;
		}
		
		else if(mExtension.equals("vob")) {
			mImageResource = R.drawable.vob;
		}
		
		else if(mExtension.equals("wmv")) {
			mImageResource = R.drawable.wmv;
		}
		
		else {
			mImageResource = R.drawable.basic;
		}
		
	}
}
