package com.zonsim.newsdemo.chat.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;

import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.easeui.ui.EaseChatFragment;
import com.easemob.easeui.widget.chatrow.EaseChatRow;
import com.easemob.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.zonsim.newsdemo.chat.DemoHelper;
import com.zonsim.newsdemo.chat.chatrow.ChatRowEvaluation;
import com.zonsim.newsdemo.chat.chatrow.ChatRowPictureText;
import com.zonsim.newsdemo.chat.chatrow.ChatRowRobotMenu;
import com.zonsim.newsdemo.chat.chatrow.ChatRowTransferToKefu;

import org.json.JSONObject;


public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentListener {

	// 避免和基类定义的常量可能发生的冲突，常量从11开始定义
	private static final int ITEM_FILE = 11;
	private static final int ITEM_SHORT_CUT_MESSAGE = 12;
	public static final int REQUEST_CODE_CONTEXT_MENU = 14;

	private static final int REQUEST_CODE_SELECT_FILE = 11;
	//EVALUATION
	public static final int REQUEST_CODE_EVAL = 26;
	//SHORT CUT MESSAGES
	public static final int REQUEST_CODE_SHORTCUT = 27;
	
	@Override
	protected void setUpView() {
		setChatFragmentListener(this);
		super.setUpView();
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
			switch (resultCode) {
			case ContextMenuActivity.RESULT_CODE_COPY: // 复制消息
				clipboard.setText(((TextMessageBody) contextMenuMessage.getBody()).getMessage());
				break;
			case ContextMenuActivity.RESULT_CODE_DELETE: // 删除消息
				conversation.removeMessage(contextMenuMessage.getMsgId());
				messageList.refresh();
				break;
			default:
				break;
			}
		}
		if(resultCode == Activity.RESULT_OK){
			if(requestCode == REQUEST_CODE_EVAL){
				messageList.refresh();
			}else if(requestCode == REQUEST_CODE_SHORTCUT){
				String content = data.getStringExtra("content");
				if(!TextUtils.isEmpty(content)){
					inputMenu.setInputMessage(content);
				}
			}else if(requestCode == REQUEST_CODE_SELECT_FILE){
				if (data != null) {
					Uri uri = data.getData();
					if (uri != null) {
						sendFileByUri(uri);
					}
				}
			}
		}

	}

	@Override
	public void onSetMessageAttributes(EMMessage message) {
		
	}

	@Override
	public void onEnterToChatDetails() {
		
	}

	@Override
	public void onAvatarClick(String username) {
		// 头像点击事件
	}

	@Override
	public boolean onMessageBubbleClick(EMMessage message) {
		// 消息框点击事件，demo这里不做覆盖，如需覆盖，return true
		return false;
	}

	@Override
	public void onMessageBubbleLongClick(EMMessage message) {
		// 消息框长按
		startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message),
				REQUEST_CODE_CONTEXT_MENU);
	}

	@Override
	public boolean onExtendMenuItemClick(int itemId, View view) {
		switch(itemId){
			case ITEM_FILE:
				break;
			case ITEM_SHORT_CUT_MESSAGE:
				break;
			default:break;
		}
		//不覆盖已有的点击事件
		return false;
	}

	@Override
	public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
		// 设置自定义listview item提供者
		return new CustomChatRowProvider();
	}

	/**
	 * chat row provider
	 * 
	 */
	private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
		@Override
		public int getCustomChatRowTypeCount() {
			//此处返回的数目为getCustomChatRowType 中的布局的个数
			return 8;
		}

		@Override
		public int getCustomChatRowType(EMMessage message) {
			return 0;
		}

		@Override
		public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
			if (message.getType() == EMMessage.Type.TXT) {
				if (DemoHelper.getInstance().isRobotMenuMessage(message)) {
					return new ChatRowRobotMenu(getActivity(), message, position, adapter);
				} else if (DemoHelper.getInstance().isEvalMessage(message)) {
					return new ChatRowEvaluation(getActivity(), message, position, adapter);
				} else if (DemoHelper.getInstance().isPictureTxtMessage(message)) {
					return new ChatRowPictureText(getActivity(), message, position, adapter);
				}else if (DemoHelper.getInstance().isTransferToKefuMsg(message)){
					return new ChatRowTransferToKefu(getActivity(), message, position, adapter);
				}
			}
			return null;
		}
	}
	
	public void sendRobotMessage(String content, String menuId) {
		EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
		if (!TextUtils.isEmpty(menuId)) {
			JSONObject msgTypeJson = new JSONObject();
			try {
				JSONObject choiceJson = new JSONObject();
				choiceJson.put("menuid", menuId);
				msgTypeJson.put("choice", choiceJson);
			} catch (Exception e) {
				
			}
			message.setAttribute("msgtype", msgTypeJson);
		}
		sendMessage(message);
	}
}
