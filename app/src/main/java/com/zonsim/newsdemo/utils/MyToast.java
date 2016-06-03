package com.zonsim.newsdemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义的Toast 避免Toast长时间不消失
 * Created by tang-jw on 2016/6/3.
 */
public class MyToast {
	
	private static Toast mToast;
	
	private static void showToast(Context context, String text, boolean flag) {
		int length = Toast.LENGTH_SHORT;
		if (flag) {
			length = Toast.LENGTH_LONG;
		}
		if (mToast == null) {
			
			mToast = Toast.makeText(context, text, length);
		}
		mToast.setDuration(length);
		mToast.setText(text);
		mToast.show();
	}
	
	public static void show(Context context, String string) {
		showToast(context, string, false);
	}
	
	public static void showLong(Context context, String string) {
		showToast(context, string, true);
	}
	
}
