package com.zonsim.newsdemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 详情页BaseActivity
 * 默认不弹出输入法软键盘
 * 设置默认ProgressDialog等
 * Created by 唐军伟 on 3/27.
 */
public abstract class BaseActivity extends FragmentActivity {
	
	public ProgressDialog mProgressDialog;
	private InputMethodManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐藏标题
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//设置窗口背景不透明
		getWindow().setBackgroundDrawable(null);
		//有EditView的界面，默认不弹窗软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		/*//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("正在加载数据....");
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		setContentView(initContentView());
		initView();
		initListener();
		initData();
	}
	
	
	/**
	 * 点击非EditText控件，隐藏软键盘
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 显示加载数据的dialog
	 */
	protected void showProgressDialog() {
		if (mProgressDialog != null && !mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}
	/**
	 * 关闭加载数据的dialog
	 */
	protected void dismissProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	/**
	 * 返回界面的布局
	 * @return layout id
	 */
	protected abstract int initContentView();
	
	/**
	 * 初始化控件，findViewById()
	 */
	protected abstract void initView();
	
	/**
	 * 给控件设置监听器，初始化监听器
	 */
	protected abstract void initListener();
	
	/**
	 * 初始化数据（网络加载、本地加载数据等操作）
	 */
	protected abstract void initData();
}
