package com.swssm.NexusLib.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import org.swssm.nexuslib.R;

public class Channel implements Serializable {
	
	private static final long serialVersionUID = 654794650433824945L;
	
	public static final String SBS = "SBS";
	public static final String KBS1 = "KBS1";
	public static final String KBS2 = "KBS2";
	public static final String MBC = "SEOULMBC";
	public static final String EBS = "EBS_2";
	
	private String 				mId;						// 체널 ID트
	private String 				mChannelName;				// 체널 이름
	private int					mChannelNumber;			// 체널 번호
	private String				mStreamingURL;			// Streamming URL Info
	private int					mCompanyLogoResourceID;	// 방송국 로고 리소스 아이디

	private ArrayList<TvPrograme> mProgrameList;		// 방송 리스트
	
	public Channel(String id, String channelName, int channelNumber) {
		super();
		this.mId = id;
		this.mChannelName = channelName;
		this.mChannelNumber = channelNumber;
		this.mProgrameList = new ArrayList<TvPrograme>();
		
		if(channelName.equals(SBS)) {
			
			//http://192.168.0.155:9981/stream/channelid/1429708111?ticket=696D0B14114553893CD0D0AC269A61D7C1E2D988
			mStreamingURL = "http://192.168.0.155:9981/stream/channelid/1429708111?ticket=696D0B14114553893CD0D0AC269A61D7C1E2D988?title=SBS&transcode=1&mux=matroska&vcodec=H264&scodec=NONE&resolution=384&bandwidth=0";
			mCompanyLogoResourceID = R.drawable.logo_sbs;
		} 
		
		else if(channelName.equals(KBS1)) {
			mStreamingURL = "http://192.168.0.155:9981/stream/channelid/2033533751?ticket=BE112D5BE95320CF0DD01905DA485702057C0B8E?title=KBS1&transcode=1&mux=matroska&vcodec=H264&scodec=NONE&resolution=384&bandwidth=0";
			mCompanyLogoResourceID = R.drawable.logo_kbs1;
		} 
		
		else if(channelName.equals(KBS2)) {
			mStreamingURL = "http://192.168.0.155:9981/stream/channelid/1684152952?ticket=FE320CBBECAB3E6CD75F256602A64564C841817F?title=KBS2&transcode=1&mux=matroska&vcodec=H264&scodec=NONE&resolution=384&bandwidth=0";
			
			//mStreamingURL = "http://192.168.0.155:9981/stream/channelid/571264134?ticket=A6A54CBCBBCD9A38AF36CC9D04F46DB80442D4CC?title=KBS2&transcode=1&mux=matroska&vcodec=H264&scodec=NONE&resolution=384&bandwidth=0";
			mCompanyLogoResourceID = R.drawable.logo_kbs2;
		} 
				
		else if(channelName.equals(MBC)) {
			mStreamingURL = "http://192.168.0.155:9981/stream/channelid/349823475?ticket=F2DC99ECBA20485656A8DFC289AD83E081F354D3?title=MBC&transcode=1&mux=matroska&vcodec=H264&scodec=NONE&resolution=384&bandwidth=0";
			
			//mStreamingURL = "http://192.168.0.155:9981/stream/channelid/1613577972?ticket=9DFE20DD0B6D30F155E27EB2B7453CC2BCBF9288?title=MBC&transcode=1&mux=matroska&vcodec=H264&scodec=NONE&resolution=384&bandwidth=0";
			mCompanyLogoResourceID = R.drawable.logo_mbc;
		} 
			
		else if(channelName.equals(EBS)) {
			mStreamingURL = "http://192.168.0.155:9981/stream/channelid/1429708111?ticket=696D0B14114553893CD0D0AC269A61D7C1E2D988?title=EBS&transcode=1&mux=matroska&vcodec=H264&scodec=NONE&resolution=384&bandwidth=0";
			
			//mStreamingURL = "http://192.168.0.155:9981/stream/channelid/1080029849?ticket=828A7AC107784F60290C94E589E1791D757A00E3?title=EBS&transcode=1&mux=matroska&vcodec=H264&scodec=NONE&resolution=384&bandwidth=0";
			mCompanyLogoResourceID = R.drawable.logo_ebs;
		} 
		
		else {
			return;
		}
		
	}

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getChannelName() {
		return mChannelName;
	}

	public void setChannelName(String mChannelName) {
		this.mChannelName = mChannelName;
	}

	public int getChannelNumber() {
		return mChannelNumber;
	}

	public void setChannelNumber(int mChannelNumber) {
		this.mChannelNumber = mChannelNumber;
	}
	
	public void addTvPrograme(TvPrograme tvPrograme) {
		mProgrameList.add(tvPrograme);
	}

	public ArrayList<TvPrograme> getProgrameList() {
		return mProgrameList;
	}

	public String getStreamingURL() {
		return mStreamingURL;
	}

	public int getCompanyLogoResourceID() {
		return mCompanyLogoResourceID;
	}
	
	public TvPrograme currentTvPrograme() {
		Calendar currentTime = Calendar.getInstance();
		
		TvPrograme tvPrograme;
		for(int i=0; i < mProgrameList.size(); i++) {
			
			tvPrograme = mProgrameList.get(i);
			
			Calendar startTime = tvPrograme.getStartTime();
			Calendar endTime  = tvPrograme.getEndTime();
			
			if(currentTime.after(startTime) && currentTime.before(endTime)) {
				return tvPrograme;
			}
		}
		
		return null;
	}
}
