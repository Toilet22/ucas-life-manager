package com.calm.lifemanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//按钮
	Button btn_doRcd;
	Button btn_history;
	Button btn_future;
	Button btn_settings;
	Button btn_login;
	//TextView
	TextView txtvw_logState;
	TextView txtvw_logNextTime;
	//preferences记录
	SharedPreferences sharedPref;
	//开启和关闭定时记录
	Button btn_logSwitch;
	Boolean isLogStarted;
	//时间间隔
	final int defaultInterval = 1;
	int interval = defaultInterval;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current);
		
		/******************************************
		 * 读取现有preferences。用以完成界面初始化
		 *****************************************/
		Log.v("Toilet", "NewCurrAct: before sharedPref.");
		sharedPref = getSharedPreferences(
		        getString(R.string.curr_usr_name), Context.MODE_PRIVATE);
		if(sharedPref.contains("isLogStarted")){
			isLogStarted = sharedPref.getBoolean("isLogStarted", true);
			interval = sharedPref.getInt("interval", defaultInterval);
		}else{
			isLogStarted = true;
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean("isLogStarted", isLogStarted);
			editor.putInt("interval", interval);
			editor.commit();
		}
		//根据是否开启定时记录初始化页面
		Log.v("Toilet", "NewCurrAct: before get txtvw & btn.");
		txtvw_logState = (TextView)findViewById(R.id.act_curr_txtvw_logState);
		txtvw_logNextTime = (TextView)findViewById(R.id.act_curr_txtvw_logNextTime);
		btn_logSwitch = (Button)findViewById(R.id.act_curr_btn_logSwitch);
		if(isLogStarted){
			txtvw_logState.setText(R.string.act_current_txtvw_logStarted);
			btn_logSwitch.setText(R.string.act_current_btn_logStop);
		}else{
			txtvw_logState.setText(R.string.act_current_txtvw_logStopped);
			btn_logSwitch.setText(R.string.act_current_btn_logStart);
		}
		
		//添加监听
		Log.v("Toilet", "NewCurrAct: before btn_logSwitch.setOnClickListener.");
		btn_logSwitch.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
		        //获得系统时间
				Calendar c=Calendar.getInstance();
		        int currHour = c.get(Calendar.HOUR_OF_DAY);
		        int currMin = c.get(Calendar.MINUTE);
		           c.setTimeInMillis(System.currentTimeMillis()); 
		        Log.v("Toilet", "MainActivity: test currHour: the Hour is "+ Integer.toString(currHour)+".");
				//指定定时记录的Activity
				Intent intent = new Intent(MainActivity.this, TimeToRecordBroadcastReceiver.class);
				//向intent中添加起始时间数据
		        Bundle mBundle = new Bundle();
		        mBundle.putInt("Hour", currHour);
		        mBundle.putInt("Minute", currMin);
			    intent.putExtras(mBundle);
			    Log.v("Toilet", "MainActivity: test Bundle: the Hour is "+ Integer.toString(mBundle.getInt("Hour"))+".");
				//指定PendingIntent
				PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
				//获得AlarmManager对象
				AlarmManager am; 
			    am = (AlarmManager)getSystemService(ALARM_SERVICE);
			           
				//判断是开启还是关闭
				if(isLogStarted){
					Log.v("Toilet", "MainActivity: before closeLog.");
					isLogStarted = false;
					txtvw_logState.setText(R.string.act_current_txtvw_logStopped);
					btn_logSwitch.setText(R.string.act_current_btn_logStart);
					//关闭定时服务
					am.cancel(sender);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOff, 
							Toast.LENGTH_SHORT).show();
					
				}else{
					Log.v("Toilet", "MainActivity: before startLog.");
					isLogStarted = true;
					txtvw_logState.setText(R.string.act_current_txtvw_logStarted);
					btn_logSwitch.setText(R.string.act_current_btn_logStop);
					//开启定时服务
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval*3000, sender); 
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOn, 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		/***************************************
		 * 记录时间
		 **************************************/
		Log.v("Toilet", "NewCurrAct: before btn_doRcd.");
		btn_doRcd = (Button)findViewById(R.id.act_current_btn_Rcd);
		btn_doRcd.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iRcd = new Intent(MainActivity.this, IWantToRecordActivity.class);
				startActivity(iRcd);
			}
		});

		/****************************************
		 * 历史记录
		 ***************************************/
		btn_history = (Button)findViewById(R.id.act_curr_btn_history);
		btn_history.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				Intent iHistory = new Intent(MainActivity.this, HistoryActivity.class);
				startActivity(iHistory);				
			}		
		});
		
		/****************************************
		 * 未来规划
		 ***************************************/
		btn_future = (Button)findViewById(R.id.act_curr_btn_future);
		btn_future.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				Intent iFuture = new Intent(MainActivity.this, FutureActivity.class);
				startActivity(iFuture);				
			}		
		});
		
		/****************************************
		 * 设置
		 ***************************************/
		btn_settings = (Button)findViewById(R.id.act_curr_btn_settings);
		btn_settings.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				Intent iSettings = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(iSettings);				
			}		
		});
		
		/****************************************
		 * 登录
		 ***************************************/
		btn_login = (Button)findViewById(R.id.act_curr_btn_login);
		btn_login.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				Intent iLogin = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(iLogin);				
			}		
		});
		
	}
	
	
	public void onPause(){

		Log.v("Toilet", "NewCurrAct: onPause.");
		super.onPause();
		/*
		 * 保存更改
		 */
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean("isLogStarted", isLogStarted);
		editor.commit();
	}

}
