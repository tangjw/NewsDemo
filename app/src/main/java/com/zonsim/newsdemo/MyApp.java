package com.zonsim.newsdemo;

import android.app.Application;

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

		
	}
}
