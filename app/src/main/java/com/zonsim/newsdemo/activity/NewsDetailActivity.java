package com.zonsim.newsdemo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.zonsim.newsdemo.R;

/**
 * 新闻详情
 * Created by tang-jw on 2016/6/1.
 */
public class NewsDetailActivity extends Activity {
	
	private LinearLayout mProgress;
	private WebView mWebView;
	
	@Override
	protected void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		mProgress = (LinearLayout) findViewById(R.id.ll_progress);
		mWebView = (WebView) findViewById(R.id.wv_news);
		
		String id = getIntent().getStringExtra("id");
		initSetting();
		mWebView.loadUrl("http://118.145.26.215:8090/edu/lianyi/EduNews/everyNewsDetail.do?Id="+id);
		
	}
	
	
	//初始化webview设置
	private void initSetting() {
		//对webview初始化设置
		WebSettings settings = mWebView.getSettings();
		
		//设置支持javascript
//		settings.setJavaScriptEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		
		//设置webview的缩放级别
		settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		
		//设置webview的页面导航
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.requestFocusFromTouch();
		
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				
				mProgress.setVisibility(View.VISIBLE);
				
				if (newProgress > 90) {
					mProgress.setVisibility(View.GONE);
				}
				super.onProgressChanged(view, newProgress);
			}
			
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}
			
			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				super.onReceivedIcon(view, icon);
			}
			
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	
}
