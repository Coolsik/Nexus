package com.swssm.NexusLib.Object;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class TvPrograme implements Serializable {
	
	private static final long serialVersionUID = 1528837449473594773L;

	private String				Id;					// 방송프로그램 ID
	
	private Calendar 				StartTime;			// 방송시작 시간
	private Calendar 				EndTime;			// 방송종료 시간
		
	private String				Title;				// 제목
	private String 				SubTitle;			// 소제목
	private String 				Description;		// 방송에 대한 설명
	private String 				Permission;		// 시청가능 연령
	
	private ArrayList<String> 	ActorList;			// 출연진
	private ArrayList<String> 	DirectorList;		// 연출진
	
	private Bitmap 				mTvProgrameImage;
	
	public TvPrograme() {}
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Calendar getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) throws ParseException {
		StartTime=parseDateFormat(startTime);
	}

	public Calendar getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) throws ParseException {
		EndTime=parseDateFormat(endTime);
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getSubTitle() {
		return SubTitle;
	}

	public void setSubTitle(String subTitle) {
		SubTitle = subTitle;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPermission() {
		return Permission;
	}

	public void setPermission(String permission) {
		Permission = permission;
	}
	
	public void addActor(String actor) {
		if(ActorList == null) {
			ActorList = new ArrayList<String>();
		}
		ActorList.add(actor);
	}
	
	public String getActorList() {
		String actor = "출연진 : ";
		
		if(ActorList==null) return null;
		
		
		for(int i=0; i<ActorList.size(); i++) {
			if(i==0) {
				actor = actor + ActorList.get(i);
			} else {
				actor = actor + ", " + ActorList.get(i);
			}
		}
		return actor;
	}
	
	public void addDirector(String director) {
		if(DirectorList == null) {
			DirectorList = new ArrayList<String>();
		}
		DirectorList.add(director);
	}
	
	public String getDirectorList() {
		String director = "연출진 : ";
		
		if(DirectorList==null) return null;
		
		for(int i=0; i<DirectorList.size(); i++) {
			if(i==0) {
				director = director + DirectorList.get(i);
			} else {
				director = director + ", " + DirectorList.get(i);
			}
		}
		return director;
	}
	
	@SuppressLint("SimpleDateFormat")
	public Calendar parseDateFormat(String dateStr) throws ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss +0900");
		format.parse(dateStr);
		Calendar calendar=format.getCalendar();
		
		/*
		Log.d("lueseypid", calendar.get(Calendar.YEAR) + "년 "
						+ (calendar.get(Calendar.MONTH) + 1) + "월 "
						+ calendar.get(Calendar.DAY_OF_MONTH) + "일 "
						+ calendar.get(Calendar.HOUR_OF_DAY) + "시 "
						+ calendar.get(Calendar.MINUTE) + "분에 시작합니다. ");
		*/
		return calendar;
	}
	
	@SuppressLint("DefaultLocale")
	public String getShowTime() {
		//String time = StartTime.get(Calendar.HOUR_OF_DAY) + ":" + StartTime.get(Calendar.MINUTE) 
		//		+ " ~ " + EndTime.get(Calendar.HOUR_OF_DAY) + ":" + EndTime.get(Calendar.MINUTE);
		
		String time = String.format("%02d:%02d ~ %02d:%02d", 
				StartTime.get(Calendar.HOUR_OF_DAY),
				StartTime.get(Calendar.MINUTE), 
				EndTime.get(Calendar.HOUR_OF_DAY),
				EndTime.get(Calendar.MINUTE));
		
		
		return time;
	}
	
	
	public Bitmap getTvProgrameImage() {
		if(mTvProgrameImage != null) {
			return mTvProgrameImage;
		}
		
		TvProgrameImageThread thread;
		try {
			thread = new TvProgrameImageThread();
			thread.start();
			thread.join();
			
			//String imageUrl = thread.getImageUrl();
			//Log.d("lueseypid", "imageUrl : " + imageUrl);
			mTvProgrameImage = thread.getTvProgrameImage();
			
		} catch (InterruptedException e) {
			Log.e("lueseypid", "Error : " + e.getMessage());
		}

		return mTvProgrameImage;
	}
	
	public class TvProgrameImageThread extends Thread {
		
		public static final String searchUrl = "http://m.search.daum.net/search?w=tot&nil_mtopsearch=btn&DA=YZR&q=";
		public String mImageUrl;
		public Bitmap mTvPrograme;
		
		public void run() {
			 
			try {
				HttpClient httpClient = new DefaultHttpClient();
			 
				String name = getTitle().replace(" ", "+");
				String strUrl = searchUrl + name;
				Log.d("lueseypid", "url : " + strUrl);
				HttpGet httpGet = new HttpGet(strUrl);
			 
			 
				httpClient.execute(httpGet, new BasicResponseHandler() {
					
					@Override
					public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
						String res = new String(super.handleResponse(response).getBytes("8859_1"), "euc-kr");
						Document doc = Jsoup.parse(res);
						
						Element element = doc.getElementById("tv_img_0");
						try {
							if(element != null) {
				          		Element el = element.child(0).child(0);
				          		mImageUrl = el.attr("src");
				          		Log.d("lueseypid", "data : " + mImageUrl);
				          	}
						} catch (Exception e) {}
						return res;
					}
				});
				
			} catch (Exception e) {
				Log.e("lueseypid", "Error : " + e.getMessage());
			}
			 
			if(mImageUrl == null) {
				return;
			}
			 
			URL url;
			try {
				url = new URL(mImageUrl);
				URLConnection conn = url.openConnection();
				conn.connect();
				
				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				mTvProgrameImage = BitmapFactory.decodeStream(bis);
				bis.close();
				
				
			} catch (Exception e) {
				Log.e("lueseypid", "Error : " + e.getMessage());
			}
		}
		
		public String getImageUrl() {
			return mImageUrl;
		}
		
		public Bitmap getTvProgrameImage() {
			return mTvProgrameImage;
		}
	}
}
