package com.calm.lifemanager;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoSomethingActivity extends Activity {
	private Button btn_back;
	private Button btn_start;
	private Button btn_finish;
	private TextView txt_time;
	private int hour=0, min=0, sec=0;
	private Calendar cStart = Calendar.getInstance();
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dosomething);
		txt_time = (TextView)findViewById(R.id.act_doSth_txt_time);		

		/*
		 * 开始计时
		 */		
		final Timer timer = new Timer();
		final Handler handler = new Handler(){
			public void handleMessage(Message msg){  
	            switch (msg.what) {  
	            case 1:  
	                txt_time.setText(Integer.toString(hour)+" : "+Integer.toString(min)
	                		+" : "+Integer.toString(sec));  break;
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
				timer.schedule(task, 1000, 1000); 			
			}			
		});
		 
		/*
		 * 停止计时 并保存
		 */
		btn_finish = (Button)findViewById(R.id.act_doSth_btn_finish);
		btn_finish.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				timer.cancel();	

				
			}			
		});
		
		/*
		 * 返回
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
