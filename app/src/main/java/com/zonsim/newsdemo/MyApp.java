package com.zonsim.newsdemo;

import android.app.Application;

import com.easemob.chat.EMChat;
import com.zonsim.newsdemo.chat.DemoHelper;
import com.zonsim.newsdemo.chat.utils.HelpDeskPreferenceUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * application 初始化Logger
 * Created by tang-jw on 2016/5/26.
 */
public class MyApp extends Application {
	
	public static MyApp application;
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		
		//代码中设置环信IM的Appkey
		String appkey = HelpDeskPreferenceUtils.getInstance(this).getSettingCustomerAppkey();
		EMChat.getInstance().setAppkey(appkey);
		
		//init demo helper
		DemoHelper.getInstance().init(application);
		
		//初始化 JPush
		JPushInterface.setDebugMode(false);
		JPushInterface.init(this);
	}
}
