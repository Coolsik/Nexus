package org.swssm.nexus.Fragment;

import org.swssm.nexus.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class TorrentFragment extends Fragment {
	
	WebView mWebView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_torrent, container, false);
		
		mWebView = (WebView) v.findViewById(R.id.wv_torrent);
		return v;
	}
	
	@Override
	public void onResume() {

		/*
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.setWebChromeClient(new WebChromeClient());
		WebSettings set = mWebView.getSettings();

		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);

		set.setSupportMultipleWindows(true);
		set.setJavaScriptCanOpenWindowsAutomatically(true);
		
		set.setAllowContentAccess(true);
		set.setAllowFileAccess(true);
		set.setAllowFileAccessFromFileURLs(true);
		set.setAllowUniversalAccessFromFileURLs(true);
		set.setAppCacheEnabled(true);
		set.setDatabaseEnabled(true);
		set.setDisplayZoomControls(true);
		set.setGeolocationEnabled(true);
		set.setLoadsImagesAutomatically(true);
		set.setLoadWithOverviewMode(true);
		set.setSaveFormData(true);
		set.setUseWideViewPort(true);
		set.setSupportZoom(true);
		set.setBlockNetworkLoads(true);
		set.setBlockNetworkImage(true);
		set.setDomStorageEnabled(true);
		
		
		
		mWebView.loadUrl("http://192.168.0.155/gui/");
		//mWebView.loadUrl("http://google.com");
		 */
		super.onResume();
	}
}
