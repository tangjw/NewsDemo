package com.zonsim.newsdemo.chat.chatrow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.easemob.chat.EMMessage;
import com.easemob.easeui.widget.chatrow.EaseChatRow;
import com.zonsim.newsdemo.R;
import com.zonsim.newsdemo.chat.Constant;
import com.zonsim.newsdemo.chat.DemoHelper;
import com.zonsim.newsdemo.chat.ui.ChatFragment;
import com.zonsim.newsdemo.chat.ui.SatisfactionActivity;

import org.json.JSONObject;

/**
 * 客服评价
 */

public class ChatRowEvaluation extends EaseChatRow {
	
	Button btnEval;

	public ChatRowEvaluation(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflatView() {
		if (DemoHelper.getInstance().isEvalMessage(message)) {
			inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ? R.layout.em_row_received_satisfaction
					: R.layout.em_row_sent_satisfaction, this);
		}
	}

	@Override
	protected void onFindViewById() {
		btnEval = (Button) findViewById(R.id.btn_eval);
		
	}

	@Override
	protected void onUpdateView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSetUpView() {
		try {
			final JSONObject jsonObj = message.getJSONObjectAttribute(Constant.WEICHAT_MSG);
			if(jsonObj.has("ctrlType")&&!jsonObj.isNull("ctrlType")){
				btnEval.setEnabled(true);
				btnEval.setText("立即评价");
				btnEval.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						((Activity)context).startActivityForResult(new Intent(context, SatisfactionActivity.class)
								.putExtra("msgId", message.getMsgId()), ChatFragment.REQUEST_CODE_EVAL);
					}
				});
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onBubbleClick() {
		
	}

}
