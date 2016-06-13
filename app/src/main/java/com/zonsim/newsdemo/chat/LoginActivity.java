package com.zonsim.newsdemo.chat;

import android.content.Intent;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.zonsim.newsdemo.MyApp;
import com.zonsim.newsdemo.R;
import com.zonsim.newsdemo.activity.BaseActivity;
import com.zonsim.newsdemo.chat.ui.ChatActivity;
import com.zonsim.newsdemo.chat.utils.CommonUtils;
import com.zonsim.newsdemo.utils.MyToast;


/**
 * 登陆到环信
 * Created by tang-jw on 5/9.
 */
public class LoginActivity extends BaseActivity {
	
	@Override
	protected int initContentView() {
		return R.layout.activity_news_detail;
	}
	
	@Override
	protected void initView() {
		//检测是否已经登录过环信，如果登录过则环信SDK会自动登录，不需要再次调用登录操作
		if (EMChat.getInstance().isLoggedIn()) {
			mProgressDialog.setMessage("正在联系客服中...");
			showProgressDialog();
			
			new Thread() {
				@Override
				public void run() {
					try {
						//加载本地数据库中的消息到内存中
						EMChatManager.getInstance().loadAllConversations();
					} catch (Exception e) {
						e.printStackTrace();
					}
					toChatActivity();
				}
			}.start();
			
			
		} else {
			// 随机创建一个用户并登录到环信服务器
			createAccountAndLoginChatServer();
		}
	}
	
	@Override
	protected void initData() {
		
	}
	
	@Override
	protected void initListener() {
		
	}
	
	/**
	 * 自动生成账号并登陆
	 */
	private void createAccountAndLoginChatServer() {
		//自动生成账号
		final String randomAccount = CommonUtils.getRandomAccount();
		final String userPwd = Constant.DEFAULT_ACCOUNT_PWD;
		
		mProgressDialog.setMessage("正在联系客服中...");
		showProgressDialog();
		
		createAccountToServer(randomAccount, userPwd, new EMCallBack() {
			@Override
			public void onSuccess() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//登陆环信服务器
						loginHuanxinServer(randomAccount, userPwd);
						
					}
				});
			}
			
			@Override
			public void onError(int i, String s) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						finish();
						MyToast.show(MyApp.application, "链接服务器失败");
					}
				});
			}
			
			@Override
			public void onProgress(int i, String s) {
				
			}
		});
		
	}
	
	/**
	 * 登陆到环信服务器
	 *
	 * @param userName
	 * @param userPwd
	 */
	private void loginHuanxinServer(final String userName, final String userPwd) {
		
		//登陆到环信服务器
		EMChatManager.getInstance().login(userName, userPwd, new EMCallBack() {
			@Override
			public void onSuccess() {
				
				DemoHelper.getInstance().setCurrentUserName(userName);
				DemoHelper.getInstance().setCurrentPassword(userPwd);
				try {
					EMChatManager.getInstance().loadAllConversations();
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				dismissProgressDialog();
				toChatActivity();
			}
			
			@Override
			public void onError(int i, String s) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						finish();
						MyToast.show(MyApp.application, "链接服务器失败");
					}
				});
			}
			
			@Override
			public void onProgress(final int i, final String s) {
				
			}
		});
		
	}
	
	/**
	 * 进入聊天界面
	 */
	private void toChatActivity() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (!LoginActivity.this.isFinishing()) {
					dismissProgressDialog();
					//进入主页面
					Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
					startActivity(intent);
					finish();
				}
				
			}
		});
		
	}
	
	/**
	 * 注册用户
	 */
	private void createAccountToServer(final String name, final String pwd, final EMCallBack callback) {
		new Thread() {
			@Override
			public void run() {
				try {
					EMChatManager.getInstance().createAccountOnServer(name, pwd);
					if (callback != null) {
						callback.onSuccess();
					}
				} catch (EaseMobException e) {
					if (callback != null) {
						callback.onError(e.getErrorCode(), e.getMessage());
					}
				}
			}
		}.start();
	}
	
	
}
