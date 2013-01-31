package com.calm.lifemanager;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoSomethingActivity extends Activity {
	private Button btn_back;
	private Button btn_start;
	private TextView txt_time;
	private int hour=0, min=0, sec=0;
	private Calendar cStart = Calendar.getInstance();
	boolean ifStarted = false;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dosomething);
		Log.v("Toilet", "DoSth: after seContentView");
		txt_time = (TextView)findViewById(R.id.act_doSth_txt_time);		
		Typeface typeFace = Typeface.createFromAsset(getAssets(),"UnidreamLED.ttf");
        txt_time.setTypeface(typeFace);
        txt_time.setText("   00 : 00 : 00   ");
		/*
		 * ��ʼ��ʱ
		 */		
		Log.v("Toilet", "DoSth: before Timer");
		final Timer timer = new Timer();
		final Handler handler = new Handler(){
			public void handleMessage(Message msg){  
	            switch (msg.what) {  
	            case 1:  
	                txt_time.setText((hour > 9 ? "   ":"   0" )+ Integer.toString(hour)
	                		+" : "+ (min > 9 ? "" : "0") + Integer.toString(min)
	                		+" : "+ (sec > 9 ? "" : "0") + Integer.toString(sec) + "   ");  
	                break;
	                		
	            }  
	        }  
		};
		
		final TimerTask task = new TimerTask() {  
	        public void run() {
	   			if(sec == 59){
	   				sec = 0;
	   				if(min == 59){
	   					min = 0;
	   					hour++;
	   				}else {
	   					min++;
	   				}
	   			}else {
	   				sec++;
	   			}
	            Message message = new Message();  
	            message.what = 1;  
	            handler.sendMessage(message);  
	        }  
	    }; 
		btn_start = (Button)findViewById(R.id.act_doSth_btn_start);
		btn_start.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				if(ifStarted){
					ifStarted = false;
					timer.cancel();
					btn_start.setBackgroundResource(R.drawable.countdown_off_btn);
				}else{
					ifStarted = true;
					timer.schedule(task, 1000, 1000);
					btn_start.setBackgroundResource(R.drawable.countdown_on_btn);
				}
			}			
		});
		
		/*
		 * ����
		 */
		btn_back = (Button)findViewById(R.id.act_doSth_btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				DoSomethingActivity.this.finish();
			}
		});
	}
}
