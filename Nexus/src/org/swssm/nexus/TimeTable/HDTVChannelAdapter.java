package org.swssm.nexus.TimeTable;

import java.util.HashMap;

import org.swssm.nexus.R;
import org.swssm.nexus.Activity.MainActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.swssm.NexusLib.Object.Channel;
import com.swssm.NexusLib.Object.TvPrograme;

public class HDTVChannelAdapter extends BaseAdapter {

	// Intent Flag
	public static final String TYPE = "type";
	public static final int HDTV = 0;
	public static final int IPTV = 1;
	public static final String CHANNEL = "channel";
	
	private Context mContext;
	private HashMap<String, Channel>	mChannelList;
	private String[] mKeys;
	private LayoutInflater mInflater;
	private HashMap<String, Boolean> isAvailable;
	
	public HDTVChannelAdapter(Context context, HashMap<String, Channel> channelList, 
			HashMap<String, Boolean> isAvailable) {
		this.isAvailable = isAvailable;
		mChannelList = channelList;
		mContext = context;
		mKeys = mChannelList.keySet().toArray(new String[channelList.size()]);
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	}
	
	@Override
	public int getCount() {
		return mChannelList.size();
	}

	@Override
	public Channel getItem(int position) {
		return mChannelList.get(mKeys[position]);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.item_hdtv_channel, parent, false);
		}
		
		Log.d("lueseypid", "position : " + position);
		
		final Channel channel = getItem(position);
		TvPrograme tvPrograme=channel.currentTvPrograme();
		
		Log.d("lueseypid", channel.getChannelName());
		
		ImageView company = (ImageView) convertView.findViewById(R.id.img_company);
		TextView tv = (TextView) convertView.findViewById(R.id.tv_hdtv_name);
		TextView time = (TextView) convertView.findViewById(R.id.tv_hdtv_time);
		//ImageButton play = (ImageButton) convertView.findViewById(R.id.btn_play);
		Button play = (Button) convertView.findViewById(R.id.btn_play);
		play.setFocusable(false);
		
		company.setImageResource(channel.getCompanyLogoResourceID());
		
		if(tvPrograme!=null) {
			tv.setText(tvPrograme.getTitle());
			time.setText(tvPrograme.getShowTime());
			//tvPrograme.getTvProgrameImage();
		}
		
		if(!isAvailable.get(channel.getChannelName())) {
			Log.d("lueseypid", "channel : " + channel.getChannelName()  + "을 사용할 수 없음");
			play.setEnabled(false);
		}
		
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(MainActivity.isRunningProcess(mContext, "org.swssm.nexusPlayer")) {
					ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE); 
					am.killBackgroundProcesses("org.swssm.nexusPlayer");
				}
				Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("org.swssm.nexusPlayer");
				intent.putExtra(TYPE, HDTV);
				intent.putExtra(CHANNEL, channel);
				mContext.startActivity(intent); 
			}
		});
		
		
		
		return convertView;
	}

}
