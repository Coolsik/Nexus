package org.swssm.nexus.Dialog;

import org.swssm.nexus.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.swssm.NexusLib.Object.TvPrograme;

public class ChannelInfoActivity extends Activity {
	
	//Intent Flag
	public final static String TV_PROGRAME = "tv_programe";
	
	ImageView mTvProgrameImg;
	TextView mTv_title;
	TextView mTv_description;
	TextView mTv_permission;
	TextView mTv_actor;
	TextView mTv_director;
	
	
	TvPrograme mTvPrograme;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_channel_info);
		
		Intent intent = getIntent();
		mTvPrograme = (TvPrograme) intent.getSerializableExtra(TV_PROGRAME);
		
		
		mTvProgrameImg = (ImageView) findViewById(R.id.img_tvprograme_img);
		
		Bitmap image = mTvPrograme.getTvProgrameImage();
		if(image !=null) {
			mTvProgrameImg.setImageBitmap(mTvPrograme.getTvProgrameImage());
		}
		
		setTvInfo();
	}
	
	private void setTvInfo() {
		mTv_title = (TextView) findViewById(R.id.tv_title);
		mTv_description = (TextView) findViewById(R.id.tv_description);
		mTv_permission = (TextView) findViewById(R.id.tv_permission);
		mTv_actor = (TextView) findViewById(R.id.tv_actor);
		mTv_director = (TextView) findViewById(R.id.tv_director);
		
		String title = mTvPrograme.getTitle();
		if(mTvPrograme.getSubTitle() != null) title = title + " : " + mTvPrograme.getSubTitle();
		mTv_title.setText(title);
		
		if(mTvPrograme.getDescription() != null) {
			mTv_description.setVisibility(View.VISIBLE);
			mTv_description.setText(mTvPrograme.getDescription());
		}
		
		if(mTvPrograme.getDescription() != null) {
			mTv_permission.setVisibility(View.VISIBLE);
			mTv_permission.setText(mTvPrograme.getPermission());
		}
		
		if(mTvPrograme.getActorList() != null) {
			mTv_actor.setVisibility(View.VISIBLE);
			mTv_actor.setText(mTvPrograme.getActorList());
		}
		
		if(mTvPrograme.getDirectorList() != null) {
			mTv_director.setVisibility(View.VISIBLE);
			mTv_director.setText(mTvPrograme.getDirectorList());
		}
	}
}
