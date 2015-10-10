package org.swssm.nexus.FileStreaming;

import org.swssm.nexus.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swssm.NexusLib.Object.Directory;
import com.swssm.NexusLib.Object.File;

public class FileListAdapter extends BaseAdapter {

	public static final int DIRECTORY_ID = 0;
	public static final int FILE_ID = 1;
	
	private Context mContext;
	private Directory mDir;
	private LayoutInflater mInflater;
	
	public FileListAdapter(Context context, Directory dir) {
		mContext = context;
		mDir = dir;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	}
	
	@Override
	public int getCount() {
		int count = mDir.getDirList().size() + mDir.getFileList().size();
		return count;
	}

	@Override
	public Directory getItem(int position) {
		return mDir;
	}

	@Override
	public long getItemId(int position) {
		if(position < mDir.getDirList().size()) {
			return DIRECTORY_ID;
		} 
		
		else if(position < mDir.getFileList().size() + mDir.getDirList().size()) {
			return FILE_ID; 
		}
		
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.item_file_list, parent, false);
		}
		
		ImageView img_fileType = (ImageView) convertView.findViewById(R.id.img_type);
		TextView tv_fileName = (TextView) convertView.findViewById(R.id.tv_file_name);
		
		String name = null;
		if(position < mDir.getDirList().size()) {
			Log.d("lueseypid", "dirlist : " + position);
			name = mDir.getDirList().get(position);
			img_fileType.setImageResource(R.drawable.folder);
		} 
		
		else if(position < mDir.getFileList().size() + mDir.getDirList().size()) {
			File file = mDir.getFileList().get(position - mDir.getDirList().size()); 
			name = file.getFileName();
			img_fileType.setImageResource(file.getImageResource());
		}
		tv_fileName.setText(name);
		return convertView;
	}
}
