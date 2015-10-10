package org.swssm.nexus.TimeTable;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.swssm.nexus.Fragment.HDTVFragment;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Handler;
import android.util.Log;

import com.swssm.NexusLib.Object.Channel;
import com.swssm.NexusLib.Object.TvPrograme;

public class HdtvTimeTable extends Thread {
	
	private HashMap<String, Channel>	mChannelList;
	Handler mHandler;
	
	public HdtvTimeTable(Handler handler) {
		mHandler = handler;
	}
	
	@Override
	public void run() {
		getHdtvTimeTableImpl();
	}
	
	public HashMap<String, Channel> getHdtvTimeTable() {
		return mChannelList;
	}
	
	private void getHdtvTimeTableImpl() {
		
		if(mChannelList!=null)
			return;
		
		mChannelList=new HashMap<String, Channel>();
		HttpClient http = null;
		InputStream is = null;
		try {
			http=new DefaultHttpClient();
			
			
			HttpGet get=new HttpGet("http://192.168.0.155:3030/xmltv.xml");
			HttpResponse response = http.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode == 200) {
				Log.d("LueseyPid", "연결성공");
				HttpEntity entitiy=response.getEntity();
				is=entitiy.getContent();
							
				XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
				XmlPullParser xpp=factory.newPullParser();
				xpp.setInput(is, "UTF-8");
				
				parsingChannelInfo(xpp);
				
			} else {
				Log.d("lueseypid", "연결실패");
			}
			
		} catch (ClientProtocolException e) {
			Log.e("lueseypid", "Error : " + e.getMessage());
		} catch (IOException e) {
			Log.e("lueseypid", "Error : " + e.getMessage());
		} catch (XmlPullParserException e) {
			Log.e("lueseypid", "Error : " + e.getMessage());
		} catch (ParseException e) {
			Log.e("lueseypid", "Error : " + e.getMessage());
		}
		
		finally {
			http.getConnectionManager().shutdown();
			
			if(is!=null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
		}
		
		mHandler.sendEmptyMessage(HDTVFragment.TIMETAGBLE_PARSING_COMPLETE);
		Log.i("lueseypid", "Hdtv timetable parsing complete");
		return;
	}
	
	private void parsingChannelInfo(XmlPullParser xpp) 
			throws XmlPullParserException, IOException, ParseException {
		
		int eventType=xpp.getEventType();
		
		while(eventType!=XmlPullParser.END_DOCUMENT) {
			switch(eventType) {
			
			case XmlPullParser.START_TAG:
				if(xpp.getName().equals("channel")) {
					String id=xpp.getAttributeValue(null, "id");
					
					xpp.nextTag();
					xpp.next();
					xpp.nextTag();
					xpp.nextTag();
					xpp.next();
					
					int channelNumber=Integer.parseInt(xpp.getText());
					
					xpp.nextTag();
					xpp.nextTag();
					xpp.next();
					String channelName=xpp.getText();
					
					mChannelList.put(id, new Channel(id, channelName, channelNumber));
				}
				
				else if(xpp.getName().equals("programme")) {
					parsingTvProgrameInfo(xpp, eventType);
				}
				break;
				
			case XmlPullParser.END_TAG:
				break;
			case XmlPullParser.TEXT:
				break;
			}
			
			eventType=xpp.next();
		}
	}
	
	private void parsingTvProgrameInfo(XmlPullParser xpp, int eventType) throws XmlPullParserException, IOException, ParseException {
		
		TvPrograme tvPrograme = new TvPrograme();
		
		while(true) {
			switch(eventType) {
			case XmlPullParser.START_TAG:
				
				if(xpp.getName().equals("programme")) {
					String id=xpp.getAttributeValue(null, "channel");
					String startTime=xpp.getAttributeValue(null, "start");
					String endTime=xpp.getAttributeValue(null, "stop");
					
					tvPrograme.setId(id);
					tvPrograme.setStartTime(startTime);
					tvPrograme.setEndTime(endTime);
				}

				else if(xpp.getName().equals("title")) {
					xpp.next();
					String title=xpp.getText();
					tvPrograme.setTitle(title);
				}
				
				else if(xpp.getName().equals("sub-title")) {
					xpp.next();
					String subTitle=xpp.getText();
					tvPrograme.setSubTitle(subTitle);
				}
				
				else if(xpp.getName().equals("value")) {
					xpp.next();
					String permission=xpp.getText();
					tvPrograme.setPermission(permission);
				}
				
				else if(xpp.getName().equals("director")) {
					xpp.next();
					tvPrograme.addDirector(xpp.getText());

				}
				
				else if(xpp.getName().equals("actor")) {
					xpp.next();
					tvPrograme.addDirector(xpp.getText());
				}
				
				else if(xpp.getName().equals("desc")) {
					xpp.next();
					String description=xpp.getText();
					tvPrograme.setDescription(description);
				}
				break;
				
			case XmlPullParser.END_TAG:
				if(xpp.getName().equals("programme")) { 
					Channel channel = mChannelList.get(tvPrograme.getId());
					channel.addTvPrograme(tvPrograme);
					return;
				}
				break;
			}
			
			eventType=xpp.next();
		}
	}
}