package com.calm.lifemanager;


import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	final int defaultInterval = 1;
	//preferences记录
	SharedPreferences sharedPref;
	
	//开启和关闭定时记录
	Button btn_logSwitch;
	Boolean isLogStarted;
	
	//是否开启铃声
	Button btn_ringtone;
	Boolean isRingOn;
	
	//是否开启震动
	Button btn_vibration;
	Boolean isVibrationOn;
	
	//返回保存
	Button btn_save;
	Button btn_back;
	//自定义类别
	Button btn_types;
	
	
	// 用户登录和数据同步
	Button btn_switch_user;
	Button btn_sync_data;
	TextView txtvw_current_loged_in_user;
	
	//选择时间间隔
	//Button btn_setInterval;
	TextView txtvw_interval;
	//Spinner spnr_logInterval;
	int interval = defaultInterval;
	//static final String[] intervals = { "0.5小时", "1小时", "1.5小时", "2小时", "2.5小时", "3小时","3.5小时", "4小时"};  
    //ArrayAdapter <String> arrayAdapter = null;  
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet", "SettingActivity: before setContentView.");
		setContentView(R.layout.activity_settings);
		Log.v("Toilet", "SettingActivity: after setContentView.");
		
		Log.v("Toilet", "SettingActivity: before reading the database.");
		/******************************************
		 * 读取现有preferences。用以完成界面初始化
		 *****************************************/
		sharedPref = getSharedPreferences(
		        getString(R.string.curr_usr_name), Context.MODE_PRIVATE);
		if(sharedPref.contains("isLogStarted")){
			isLogStarted = sharedPref.getBoolean("isLogStarted", true);
			interval = sharedPref.getInt("interval", defaultInterval);
			isRingOn = sharedPref.getBoolean("isRingOn", true);
			isVibrationOn = sharedPref.getBoolean("isVibrationOn", true);
		}else{
			isLogStarted = true;
			isRingOn = true;
			isVibrationOn = true;
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean("isLogStarted", isLogStarted);
			editor.putBoolean("isRingOn", isRingOn);
			editor.putBoolean("isVibrationOn", isVibrationOn);
			editor.putInt("interval", interval);
			editor.commit();
		}
		
		//btn_logInterval = (Button)findViewById(R.id.button_Interval);
		//txtvw_logInterval = (Button)findViewById(R.id.textView2_Interval);
		
		/**************************************
		 * 开启和关闭定时记录功能
		 **************************************/
		Log.v("Toilet", "before get btn_logSwitch.");
		btn_logSwitch = (Button)findViewById(R.id.act_settings_btn_startRglRcd);
		//按钮初始化
		if(isLogStarted){
			btn_logSwitch.setBackgroundResource(R.drawable.switch_on);	
		}else{
			btn_logSwitch.setBackgroundResource(R.drawable.switch_off);			
		}
		//添加监听
		btn_logSwitch.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
	            //获得系统时间
				Calendar c=Calendar.getInstance();
		        int currHour = c.get(Calendar.HOUR_OF_DAY);
		        int currMin = c.get(Calendar.MINUTE);
	            c.setTimeInMillis(System.currentTimeMillis()); 
		        Log.v("Toilet", "SettingsActivity: test currHour: the Hour is "+ Integer.toString(currHour)+".");
				//指定定时记录的Activity
				Intent intent = new Intent(SettingsActivity.this, TimeToRecordBroadcastReceiver.class);
				//向intent中添加起始时间数据
	            Bundle mBundle = new Bundle();
	            mBundle.putInt("Hour", currHour);
	            mBundle.putInt("Minute", currMin);
	            intent.putExtras(mBundle);
	            Log.v("Toilet", "SettingsActivity: test Bundle: the Hour is "+ Integer.toString(mBundle.getInt("Hour"))+".");
				//指定PendingIntent
				PendingIntent sender = PendingIntent.getBroadcast(SettingsActivity.this, 0, intent, 0);
				//获得AlarmManager对象
				AlarmManager am; 
	            am = (AlarmManager)getSystemService(ALARM_SERVICE);
	            
				//判断是开启还是关闭
				if(isLogStarted){
					Log.v("Toilet", "SettingsActivity: before closeLog.");
					isLogStarted = false;
					btn_logSwitch.setBackgroundResource(R.drawable.switch_off);
					//关闭定时服务
					am.cancel(sender);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOff, 
							Toast.LENGTH_SHORT).show();
					
				}else
				{
					Log.v("Toilet", "SettingsActivity: before startLog.");
					isLogStarted = true;
					btn_logSwitch.setBackgroundResource(R.drawable.switch_on);
					//开启定时服务
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval*3000, sender); 
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOn, 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		/**************************************
		 * 选择时间间隔
		 **************************************/
		Log.v("Toilet", "before get btn_logInterval.");	
		txtvw_interval = (TextView)findViewById(R.id.act_settings_txtvw_interval);
		//时间间隔初始化
		txtvw_interval.setText(Integer.toString(interval));
		
		/*
		 * 点击时间间隔的TextView，将弹出对话框，供输入时间间隔
		 */
		//以下是Dialog的输入框
		final EditText edttxt_interval = new EditText(SettingsActivity.this);
		edttxt_interval.setInputType(0x00000002);
		//以下是Dialog的按钮监听
		final DialogInterface.OnClickListener posListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				interval = Integer.parseInt(edttxt_interval.getText().toString());
				txtvw_interval.setText(Integer.toString(interval));
				//判断是定时记录功能是开启还是关闭
				if(isLogStarted){
					/*
					 * 如果定时记录功能开启，改变interval的操作是有意义的
					 * 根据新的时间间隔值重新开始计时。
					 */
		            //获得系统时间
		            Calendar c=Calendar.getInstance();
		            int currHour = c.get(Calendar.HOUR_OF_DAY);
		            int currMin = c.get(Calendar.MINUTE);
		            Log.v("Toilet", "SettingsActivity_Dialog: test currHour: the Hour is "+ Integer.toString(currHour)+".");
		            c.setTimeInMillis(System.currentTimeMillis()); 
					//指定定时记录的Activity
					Intent intent = new Intent(SettingsActivity.this, TimeToRecordBroadcastReceiver.class);
					//指定PendingIntent
					PendingIntent sender = PendingIntent.getBroadcast(SettingsActivity.this, 0, intent, 0);
					//获得AlarmManager对象
					AlarmManager am; 
		            am = (AlarmManager)getSystemService(ALARM_SERVICE);
					//关闭定时服务
					Log.v("Toilet", "SettingsActivity: before closeLog.");
					am.cancel(sender);
					//开启定时服务
					Log.v("Toilet", "SettingsActivity: before startLog.");
					//给新开启的Activity传递起始时间
					Intent new_intent = new Intent(SettingsActivity.this, TimeToRecordBroadcastReceiver.class);
					Bundle mBundle = new Bundle();
		            mBundle.putInt("Hour", currHour);
		            mBundle.putInt("Minute", currMin);
		            new_intent.putExtras(mBundle);
		            Log.v("Toilet", "SettingsActivity_Dialog: test Bundle: the Hour is "+ Integer.toString(mBundle.getInt("Hour"))+".");
					PendingIntent new_sender = PendingIntent.getBroadcast(SettingsActivity.this, 0, new_intent, 0);					
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval*3000, new_sender); 
					//弹出Toast
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_intervalChanged, 
							Toast.LENGTH_SHORT).show();					
				}else{
					/*
					 * 如果定时记录功能关闭，改变interval的操作是没有意义的
					 * 根据新的时间间隔值重新开始计时。
					 */
					//txtvw_interval.setText(Integer.toString(interval));
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_intervalFailed, 
							Toast.LENGTH_SHORT).show();					
				}				
			}
		};
		
		//需要的Dialog
		final AlertDialog dlg_interval = new AlertDialog.Builder(SettingsActivity.this)
			.setTitle(R.string.act_settings_dlg_interval_title)
			.setView(edttxt_interval)
			.setPositiveButton(R.string.act_settings_dlg_posBtn, posListener)
			.setNegativeButton(R.string.act_settings_dlg_negBtn, null)
			.create();
		
		
		//添加对显示时间间隔的TextView的点击监听
		txtvw_interval.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				dlg_interval.show();		
			} 
		});
		
		
		/**************************************
		 * 是否铃声提醒
		 **************************************/
		Log.v("Toilet", "before get btn_ringtone.");
		btn_ringtone = (Button)findViewById(R.id.act_settings_btn_ring);
		//按钮初始化
		if(isRingOn){
			btn_ringtone.setBackgroundResource(R.drawable.switch_on);	
		}else{
			btn_ringtone.setBackgroundResource(R.drawable.switch_off);			
		}
		//添加监听
		btn_ringtone.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
				//判断是开启还是关闭
				if(isRingOn){
					Log.v("Toilet", "SettingsActivity: before turn off ring.");
					isRingOn = false;
					btn_ringtone.setBackgroundResource(R.drawable.switch_off);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_ringOff, 
							Toast.LENGTH_SHORT).show();
					
				}else
				{
					Log.v("Toilet", "SettingsActivity: before turn on ring.");
					isRingOn = true;
					btn_ringtone.setBackgroundResource(R.drawable.switch_on);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_ringOn, 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		/**************************************
		 * 是否震动
		 **************************************/
		Log.v("Toilet", "before get btn_vibration.");
		btn_vibration = (Button)findViewById(R.id.act_settings_btn_vabrate);
		//按钮初始化
		if(isVibrationOn){
			btn_vibration.setBackgroundResource(R.drawable.switch_on);	
		}else{
			btn_vibration.setBackgroundResource(R.drawable.switch_off);			
		}
		//添加监听
		btn_vibration.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
				//判断是开启还是关闭
				if(isVibrationOn){
					Log.v("Toilet", "SettingsActivity: before turn off vibration.");
					isVibrationOn = false;
					btn_vibration.setBackgroundResource(R.drawable.switch_off);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_vibrationOff, 
							Toast.LENGTH_SHORT).show();
				}else
				{
					Log.v("Toilet", "SettingsActivity: before turn on vibration.");
					isVibrationOn = true;
					btn_vibration.setBackgroundResource(R.drawable.switch_on);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_vibrationOn, 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
        

		/**************************************
		 * 开启和关闭定时记录功能
		 **************************************/
		btn_types = (Button)findViewById(R.id.act_settings_btn_setTypes);
		btn_types.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
		        Log.v("Toilet", "btn_types pushed");
				Intent intent = new Intent(SettingsActivity.this, PrimTypesActivity.class);
				startActivity(intent);
			}
		});
		
        /*******************************************
         * 返回
         ********************************************/
		Log.v("Toilet", "before get btn_back.");
		btn_back = (Button)findViewById(R.id.act_settings_btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});		
		
		
		// User Data Sync
		txtvw_current_loged_in_user = (TextView)findViewById(R.id.act_settings_txtvw_current_loged_user);
		txtvw_current_loged_in_user.setText("DayDayUp");
		
		btn_switch_user = (Button)findViewById(R.id.act_settings_btn_switch_user);
		btn_switch_user.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				
			}
		});
		
		btn_sync_data = (Button)findViewById(R.id.act_settings_btn_sync_data);
		btn_switch_user.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				
			}
		});
	}
	
	public void onPause(){
		super.onPause();
		/*
		 * 保存更改
		 */
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean("isLogStarted", isLogStarted);
		editor.putBoolean("isRingOn", isRingOn);
		editor.putBoolean("isVibrationOn", isVibrationOn);
		editor.putInt("interval", interval);
		editor.commit();
	}
}
