package org.swssm.nexusPlayer;

import java.lang.ref.WeakReference;

import org.swssm.nexusPlayer.RemoteControll.RemoteControll;
import org.videolan.libvlc.EventHandler;
import org.videolan.libvlc.IVideoPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.swssm.NexusLib.Object.Channel;
import com.swssm.NexusLib.Object.File;
import com.swssm.NexusLib.network.EnvData;
import com.swssm.NexusLib.network.FileStreamingNetwork;
import com.swssm.NexusLib.network.HDTVNetwork;
import com.swssm.NexusLib.network.IPTVNetwork;
import com.swssm.NexusLib.network.ResponseJsonObject;

public class NexusPlayerActivity extends Activity implements SurfaceHolder.Callback, IVideoPlayer {
	
	public static final String TAG = "Nexus VedioPlayer";
	
	// Intent Flag
	public static final String TYPE = "type";
	public static final int HDTV = 0;
	public static final int IPTV = 1;
	public static final int FILE = 2;
	public static final String FILE_PATH = "filepath";
	public static final String CHANNEL = "channel";
	
	public final static String ServerURL = "http://192.168.0.155:3030";
	public final static String FtpURL = "ftp://nexus:nexus@192.168.0.155/Downloads/";

	// Display Object (SurfaceView, SurfaceHolder)
	private SurfaceView mSurfaceView;		// 동영상이 출력될 SurfaceView객체
	private SurfaceHolder mHolder;			// SurfaceView를 관리하는 객체
	
	
	// RemoteController
	RelativeLayout mRemoteController_1;
	RelativeLayout mRemoteController_2;
	RelativeLayout mRemoteController_3;
	boolean  mIsVisible;
	
	// MediaPlayer
	private LibVLC mLibVlc;
	private int mVideoHeight;
	private int mVideoWidth;
	
	// PlayChannel Info
	Channel mChannel;
	int mType;
	String mFilePath;
	
	// Handler
	Handler mHandler = new MyHandler(this);
	Handler mServerHandler = new ServerHandler(this);
	
	// Network
	HDTVNetwork mHDTVNetwork;
	IPTVNetwork mIPTVNetwork;
	FileStreamingNetwork mFileStreamingNetwork;
	
	private final static int VideoSizeChanged = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nexus_player);
		
		mSurfaceView = (SurfaceView) findViewById(R.id.sf_videoplayer);
		mHolder = mSurfaceView.getHolder();
		mHolder.addCallback(this);
		
		Intent intent = getIntent();
		mType = intent.getIntExtra(TYPE, -1);
		mChannel = (Channel) intent.getSerializableExtra(CHANNEL);
		mFilePath = intent.getStringExtra(FILE_PATH);
	}
	
	public void setIPTVEvent() {
		mIsVisible = false;
		
		mSurfaceView.setOnClickListener(listener);
		mRemoteController_1 = (RelativeLayout) findViewById(R.id.linear_controller_1);
		mRemoteController_1.setOnClickListener(listener);
		
		mRemoteController_2 = (RelativeLayout) findViewById(R.id.linear_controller_2);
		mRemoteController_2.setOnClickListener(listener);
		
		mRemoteController_3 = (RelativeLayout) findViewById(R.id.linear_controller_3);
		mRemoteController_3.setOnClickListener(listener);
		
		findViewById(R.id.btn_next_1).setOnClickListener(listener);
		findViewById(R.id.btn_back_2).setOnClickListener(listener);
		findViewById(R.id.btn_next_2).setOnClickListener(listener);
		findViewById(R.id.btn_back_3).setOnClickListener(listener);
		
		new RemoteControll(this, mIPTVNetwork).setOnClickListener();
	}
	
	View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.sf_videoplayer:
				
				if(mIsVisible) {
					mRemoteController_1.setVisibility(View.INVISIBLE);
					mRemoteController_2.setVisibility(View.INVISIBLE);
					mRemoteController_3.setVisibility(View.INVISIBLE);
					mIsVisible = false;
				} else {
					mRemoteController_1.setVisibility(View.VISIBLE);
					mIsVisible = true;
				}
				
				
				break;
				
			case R.id.linear_controller_1:
				//mRemoteController_1.setVisibility(View.INVISIBLE);
				break;
				
			case R.id.linear_controller_2:
				//mRemoteController_2.setVisibility(View.INVISIBLE);
				break;
				
			case R.id.btn_next_1:
				mRemoteController_1.setVisibility(View.INVISIBLE);
				mRemoteController_2.setVisibility(View.VISIBLE);
				mRemoteController_3.setVisibility(View.INVISIBLE);
				break;
				
			case R.id.btn_back_2:
				mRemoteController_1.setVisibility(View.VISIBLE);
				mRemoteController_2.setVisibility(View.INVISIBLE);
				mRemoteController_3.setVisibility(View.INVISIBLE);
				break;
				
			case R.id.btn_next_2:
				mRemoteController_1.setVisibility(View.INVISIBLE);
				mRemoteController_2.setVisibility(View.INVISIBLE);
				mRemoteController_3.setVisibility(View.VISIBLE);
				break;
				
			case R.id.btn_back_3:
				mRemoteController_1.setVisibility(View.INVISIBLE);
				mRemoteController_2.setVisibility(View.VISIBLE);
				mRemoteController_3.setVisibility(View.INVISIBLE);
				break;
			}
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(mType == -1) {
			notifyRunError();
			
		} else if(mChannel == null && mType == HDTV) {
			notifyRunError();
		
		}  else {
			joinBroadcastRoom();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("lueseypid", "onPause");
		exitBroadcastRoom();
		releasePlayer();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("lueseypid", "onDestroy");
		super.onDestroy();
		releasePlayer();
	}
			
	private void joinBroadcastRoom() {
		
		if(mType == HDTV) {
			mHDTVNetwork = new HDTVNetwork(this, ServerURL, mServerHandler);
			mHDTVNetwork.connect();
			mHDTVNetwork.requestStreaming(mChannel);
		} 
		
		else if(mType == IPTV) {
			mIPTVNetwork = new IPTVNetwork(this, ServerURL, mServerHandler);
			mIPTVNetwork.connect();
			mIPTVNetwork.requestStreaming();
		} 
		
		else if(mType == FILE) {
			//mFileStreamingNetwork = new FileStreamingNetwork(this, url, handler)
			//Toast.makeText(getApplicationContext(), "FILE : " + mFilePath, Toast.LENGTH_SHORT).show();
			//Toast.makeText(getApplicationContext(), "FILE : " + mFilePath, Toast.LENGTH_SHORT).show();
			mFilePath = FtpURL + mFilePath.replace("/home/nexus/Downloads/", "");
			createPlayer(mFilePath);
		}
	}
	
	private void exitBroadcastRoom() {
		if(mHDTVNetwork!=null) {
			mHDTVNetwork.disconnect();
			mHDTVNetwork=null;
		}
		
		if(mIPTVNetwork!=null) {
			mIPTVNetwork.disconnect();
			mIPTVNetwork=null;
		}
	}
	
	private void notifyRunError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Nexus Player는 단독으로 실행될 수 없습니다. \nNexus를 실행해 주세요.");
		builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}).show();
	}
	
	// Surface가 생성될때 호출
	@Override
	public void surfaceCreated(SurfaceHolder holder) {}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, 
			int width, int height) {
		
		if(mLibVlc != null)
			mLibVlc.attachSurface(mHolder.getSurface(), this);
	}
	

	@Override
	public void setSurfaceSize(int width, int height, int visible_width,
			int visible_height, int sar_num, int sar_den) {
		Message msg = Message.obtain(mHandler, VideoSizeChanged, width, height);
		msg.sendToTarget();
	}
	
	
	private void setSize(int width, int height) {
        mVideoWidth = width;
        mVideoHeight = height;
        if (mVideoWidth * mVideoHeight <= 1) 
            return;

         // 화면 크기
        int w = getWindow().getDecorView().getWidth();
        int h = getWindow().getDecorView().getHeight();

        
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        if (w > h && isPortrait || w < h && !isPortrait) {
            int i = w;
            w = h;
            h = i;
        }

        float videoAR = (float) mVideoWidth / (float) mVideoHeight;
        float screenAR = (float) w / (float) h;

        if (screenAR < videoAR)
            h = (int) (w / videoAR);
        else
            w = (int) (h * videoAR);

        // Surface View Buffer 크기
        mHolder.setFixedSize(mVideoWidth, mVideoHeight);

        // Surface View 화면 크기 수정
        LayoutParams lp = mSurfaceView.getLayoutParams();
        //lp.width = w;
        //lp.height = h;
        
        lp.width = getWindow().getDecorView().getWidth()-1;
        lp.height = getWindow().getDecorView().getHeight()-1;
        mSurfaceView.setLayoutParams(lp);
        mSurfaceView.invalidate();
    }
	
	
	private void createPlayer(String url) {
		Log.d("lueseypid", "createPlayer");
		releasePlayer();
		
		try {
			mLibVlc = LibVLC.getInstance();
			mLibVlc.setHardwareAcceleration(LibVLC.HW_ACCELERATION_DISABLED);
			mLibVlc.setSubtitlesEncoding("");
			mLibVlc.setAout(LibVLC.AOUT_OPENSLES);
			mLibVlc.setTimeStretching(true);
			mLibVlc.setChroma("RV32");
			mLibVlc.setVerboseMode(true);
			LibVLC.restart(this);
			EventHandler.getInstance().addHandler(mHandler);
			mHolder.setFormat(PixelFormat.RGBX_8888);
			mHolder.setKeepScreenOn(true);
			MediaList list = mLibVlc.getMediaList();
			list.clear();
			list.add(new Media(mLibVlc, LibVLC.PathToURI(url)), false);
			mLibVlc.playIndex(0);
			
		} catch (Exception e) {
			Log.d("TAG", e.getMessage());
		}
	}
	
	private void releasePlayer() {
		Log.d("lueseypid", "releasePlayer");
		
		if(mLibVlc == null)
			return;
		
		EventHandler.getInstance().removeHandler(mHandler);
		mLibVlc.stop();
		mLibVlc.detachSubtitlesSurface();
		mHolder = null;
		mLibVlc.closeAout();
		mLibVlc.destroy();
		mLibVlc = null;
		
		mVideoHeight = 0;
		mVideoWidth = 0;
	}
	
	public int getType() {
		return mType;
	}
	
	private static class MyHandler extends Handler {
		
		private WeakReference<NexusPlayerActivity> mOwner;
		
		public MyHandler(NexusPlayerActivity activity) {
			mOwner = new WeakReference<NexusPlayerActivity>(activity);
		}
		
		@Override
		public void handleMessage(Message msg) {
			NexusPlayerActivity playerActivity = mOwner.get();
			
			if(msg.what == VideoSizeChanged) {
				playerActivity.setSize(msg.arg1, msg.arg2);
				return;
			}
			
			Bundle b = msg.getData();
			switch(b.getInt("event")) {
			case EventHandler.MediaPlayerEndReached:
				playerActivity.releasePlayer();
				break;
			case EventHandler.MediaPlayerPlaying:
			case EventHandler.MediaPlayerPaused:
			case EventHandler.MediaPlayerStopped:
			default:
				break;
			}
		}
	}
	
	private static class ServerHandler extends Handler {
		
		private NexusPlayerActivity mActivity;
		
		public ServerHandler(NexusPlayerActivity activity) {
			mActivity = activity;
		}
		
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case EnvData.EVENT_HANDLER.STREAMING_START:
				Log.d("lueseypid", "STREAMING_START");
				
				ResponseJsonObject response = (ResponseJsonObject)msg.obj;
				if(response.getResult() == EnvData.JSON_VALUE.SUCCESS) {
					if(mActivity.getType() == IPTV) {
						mActivity.createPlayer("http://192.168.0.155:8080/v4l");
						mActivity.setIPTVEvent();
						
					} else if(mActivity.getType() == HDTV) {
						mActivity.createPlayer(mActivity.mChannel.getStreamingURL());
					}
					
				} else {
					Toast.makeText(mActivity, "스트리밍 실패, 잠시후에 다시 시도 해주세요.", Toast.LENGTH_SHORT).show();
					mActivity.finish();
				}
				
				break;
			}
		}
	}
}
