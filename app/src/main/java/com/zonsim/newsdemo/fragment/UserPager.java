package com.zonsim.newsdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zonsim.newsdemo.MyApp;
import com.zonsim.newsdemo.R;
import com.zonsim.newsdemo.chat.LoginActivity;

public class UserPager extends BaseFragment {
	
	private LinearLayout mShare;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pager3_user, null);
		mShare = (LinearLayout) view.findViewById(R.id.ll_share);
		initData();
		initListener();
		mShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyApp.application, LoginActivity.class);
				startActivity(intent);
				
			}
		});
		return view;
	}
	
	private void initListener() {
		
	}
	
	private void initData() {
		
	}
	
	@Override
	protected void onInvisible() {
		
	}
	
	@Override
	protected void onVisible() {
		
	}
}
