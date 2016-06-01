package com.zonsim.newsdemo.utils;

import android.content.Context;

/**
 * dip => px  px => dip
 * Created by tang-jw on 5/29.
 */

public class DensityUtil {

/**
 * 根据手机的分辨率从dip的单位转成为px(像素)
 */
public static int dip2px(Context context, float dp) {
	final float density = context.getResources().getDisplayMetrics().density;
	return (int) (dp * density + 0.5f);
}

/**
 * 根据手机的分辨率从px(像素)的单位转成为dp
 */
public static int px2dip(Context context, float px) {
	final float density = context.getResources().getDisplayMetrics().density;
	return (int) (px / density + 0.5f);
}
	
}
