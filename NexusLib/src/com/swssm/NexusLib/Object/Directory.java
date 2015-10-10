package com.swssm.NexusLib.Object;

import java.util.ArrayList;

public class Directory {
	
	private String mDirName;
	private String mParentDirPath;
	private ArrayList<String> mDirList;
	private ArrayList<File> mFileList;
	
	public Directory(String dirName, String parentDirPath,
			ArrayList<String> dirList, ArrayList<File> fileList) {
		super();
		this.mDirName = dirName;
		this.mParentDirPath = parentDirPath;
		this.mDirList = dirList;
		this.mFileList = fileList;
	}

	public String getDirName() {
		return mDirName;
	}

	public void setDirName(String mDirName) {
		this.mDirName = mDirName;
	}

	public String getParentDirPath() {
		return mParentDirPath;
	}

	public void setParentDirPath(String mParentDirPath) {
		this.mParentDirPath = mParentDirPath;
	}

	public ArrayList<String> getDirList() {
		return mDirList;
	}

	public void setDirList(ArrayList<String> mDirList) {
		this.mDirList = mDirList;
	}

	public ArrayList<File> getFileList() {
		return mFileList;
	}

	public void setFileList(ArrayList<File> mFileList) {
		this.mFileList = mFileList;
	}
}
