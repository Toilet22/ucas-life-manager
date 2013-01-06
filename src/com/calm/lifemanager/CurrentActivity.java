package com.calm.lifemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CurrentActivity extends Activity {
	Button btn_back;
	Button btn_doRcd;
	Button btn_mood;
	Button btn_countDown;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current);
		
		/*
		 * 返回
		 */
		btn_back = (Button)findViewById(R.id.act_current_btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iMain = new Intent(CurrentActivity.this, MainActivity.class);
				startActivity(iMain);
				CurrentActivity.this.finish();
			}
		});
		
		/*
		 * 记录时间
		 */
		btn_doRcd = (Button)findViewById(R.id.act_current_btn_Rcd);
		btn_doRcd.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iRcd = new Intent(CurrentActivity.this, IWantToRecordActivity.class);
				startActivity(iRcd);
			}
		});
		
		/*
		 * 心情
		 */
		btn_mood = (Button)findViewById(R.id.act_current_btn_mood);
		btn_mood.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iHappy = new Intent(CurrentActivity.this, HappyActivity.class);
				startActivity(iHappy);
			}
		});
		
		/*
		 * 倒计时
		 */
		btn_countDown = (Button)findViewById(R.id.act_current_btn_countDown);
		btn_countDown.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iCount = new Intent(CurrentActivity.this, DoSomethingActivity.class);
				startActivity(iCount);
			}
		});
		
		
		
	}

}
