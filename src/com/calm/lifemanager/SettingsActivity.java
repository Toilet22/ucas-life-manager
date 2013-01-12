package com.calm.lifemanager;


import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	
	//开启和关闭定时记录
	Button btn_logSwitch;
	Boolean isLogStarted;
	TextView txtvw_logState;
	
	//返回保存
	Button btn_save;
	Button btn_back;
	
	//选择时间间隔
	//Button btn_setInterval;
	TextView txtvw_logInterval;
	Spinner spnr_logInterval;
	int interval;
	static final String[] intervals = { "0.5小时", "1小时", "1.5小时", "2小时", "2.5小时", "3.5小时", "4小时"};  
    ArrayAdapter <String> arrayAdapter = null;  
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet", "SettingActivity: before setContentView.");
		setContentView(R.layout.activity_settings);
		Log.v("Toilet", "SettingActivity: after setContentView.");
		
		Log.v("Toilet", "SettingActivity: before reading the datebase.");
		isLogStarted = true;
		txtvw_logState = (TextView)findViewById(R.id.textView1_LogState);
		interval = 3000;
		
		//btn_logInterval = (Button)findViewById(R.id.button_Interval);
		//txtvw_logInterval = (Button)findViewById(R.id.textView2_Interval);
		
		/**************************************
		 * 开启和关闭定时记录功能
		 **************************************/
		Log.v("Toilet", "before get btn_logSwitch.");
		btn_logSwitch = (Button)findViewById(R.id.button_LogSwitch);
		btn_logSwitch.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
				//指定定时记录的Activity
				Intent intent = new Intent(SettingsActivity.this, TimeToRecordBroadcastReceiver.class);
				//指定PendingIntent
				PendingIntent sender = PendingIntent.getBroadcast(SettingsActivity.this, 0, intent, 0);
				//获得AlarmManager对象
				AlarmManager am; 
	            am = (AlarmManager)getSystemService(ALARM_SERVICE);
	            //获得系统时间
	            Calendar c=Calendar.getInstance();
	            c.setTimeInMillis(System.currentTimeMillis()); 
	            
				//判断是开启还是关闭
				if(isLogStarted){
					Log.v("Toilet", "SettingsActivity: before closeLog.");
					isLogStarted = false;
					txtvw_logState.setText(R.string.act_settings_txtvw_closed);
					btn_logSwitch.setText(R.string.act_settings_btn_startLog);
					//关闭定时服务
					am.cancel(sender);
					
				}else
				{
					Log.v("Toilet", "SettingsActivity: before startLog.");
					isLogStarted = true;
					txtvw_logState.setText(R.string.act_settings_txtvw_started);
					btn_logSwitch.setText(R.string.act_settings_btn_closeLog);
					//开启定时服务
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval, sender); 
				}
			}
		});
		
		
		/**************************************
		 * 选择时间间隔
		 **************************************/
		Log.v("Toilet", "before get btn_logInterval.");		
		spnr_logInterval = (Spinner)findViewById(R.id.act_setting_spnr_Interval);
		//定义了一个ArrayAdapter，将datas数组与simple_spinner_item绑定  
        //android.R.layout.simple_spinner_item是由Android提供的一种标准spinner的布局  
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,intervals);  
        //声明当控件打开时的外观：为系统提供的simple_spinner_dropdown_item   
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );   
        spnr_logInterval.setAdapter(arrayAdapter);  
        //为spnr_logInterval添加选择事件监听器  
        spnr_logInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
        {  
            public void onItemSelected(AdapterView<?> parent, View view,  int position, long id) {  
                //interval = (position+1) * 1800000;  
                interval = (position+1) * 3000;  
            }

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}  
        });  
        spnr_logInterval.setVisibility(View.VISIBLE);  

        /*******************************************
         * 保存 
         ********************************************/
		Log.v("Toilet", "before get btn_save.");
        btn_save = (Button)findViewById(R.id.act_setting_btn_save);
        btn_save.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){

        	}
        });
        
        
        /*******************************************
         * 返回
         ********************************************/
		Log.v("Toilet", "before get btn_back.");
		btn_back = (Button)findViewById(R.id.act_setting_btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
		
		
		
	}

}
