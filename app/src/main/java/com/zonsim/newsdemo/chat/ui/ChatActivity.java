package com.zonsim.newsdemo.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.easemob.easeui.ui.EaseChatFragment;
import com.zonsim.newsdemo.R;
import com.zonsim.newsdemo.chat.Constant;
import com.zonsim.newsdemo.chat.utils.HelpDeskPreferenceUtils;


/**
 * 聊天页面，需要fragment的使用{@link EaseChatFragment}
 */
public class ChatActivity extends FragmentActivity {

    public static ChatActivity activityInstance;
    private ChatFragment chatFragment;
    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        // 聊天人或群id
        toChatUsername = HelpDeskPreferenceUtils.getInstance(this).getSettingCustomerAccount();
    
        System.out.println("哈哈哈哈："+toChatUsername);
        // 可以直接new EaseChatFratFragment使用
        chatFragment = new ChatFragment();
        Intent intent = getIntent();
        intent.putExtra(Constant.EXTRA_USER_ID, toChatUsername);
        intent.putExtra(Constant.EXTRA_SHOW_USERNICK, true);
        // 传入参数
        chatFragment.setArguments(intent.getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

//    public String getToChatUsername() {
//        return toChatUsername;
//    }

//	public void sendTextMessage(String txtContent){
//		chatFragment.sendTextMessage(txtContent);
//	}

    public void sendRobotMessage(String txtContent, String menuId) {
        chatFragment.sendRobotMessage(txtContent, menuId);
    }



}
