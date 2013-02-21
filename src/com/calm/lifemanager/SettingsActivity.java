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
	//preferences��¼
	SharedPreferences sharedPref;
	
	//�����͹رն�ʱ��¼
	Button btn_logSwitch;
	Boolean isLogStarted;
	
	//�Ƿ�������
	Button btn_ringtone;
	Boolean isRingOn;
	
	//�Ƿ�����
	Button btn_vibration;
	Boolean isVibrationOn;
	
	//���ر���
	Button btn_save;
	Button btn_back;
	//�Զ������
	Button btn_types;
	
	
	// �û���¼������ͬ��
	Button btn_switch_user;
	Button btn_sync_data;
	TextView txtvw_current_loged_in_user;
	
	//ѡ��ʱ����
	//Button btn_setInterval;
	TextView txtvw_interval;
	//Spinner spnr_logInterval;
	int interval = defaultInterval;
	//static final String[] intervals = { "0.5Сʱ", "1Сʱ", "1.5Сʱ", "2Сʱ", "2.5Сʱ", "3Сʱ","3.5Сʱ", "4Сʱ"};  
    //ArrayAdapter <String> arrayAdapter = null;  
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet", "SettingActivity: before setContentView.");
		setContentView(R.layout.activity_settings);
		Log.v("Toilet", "SettingActivity: after setContentView.");
		
		Log.v("Toilet", "SettingActivity: before reading the database.");
		/******************************************
		 * ��ȡ����preferences��������ɽ����ʼ��
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
		 * �����͹رն�ʱ��¼����
		 **************************************/
		Log.v("Toilet", "before get btn_logSwitch.");
		btn_logSwitch = (Button)findViewById(R.id.act_settings_btn_startRglRcd);
		//��ť��ʼ��
		if(isLogStarted){
			btn_logSwitch.setBackgroundResource(R.drawable.switch_on);	
		}else{
			btn_logSwitch.setBackgroundResource(R.drawable.switch_off);			
		}
		//��Ӽ���
		btn_logSwitch.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
	            //���ϵͳʱ��
				Calendar c=Calendar.getInstance();
		        int currHour = c.get(Calendar.HOUR_OF_DAY);
		        int currMin = c.get(Calendar.MINUTE);
	            c.setTimeInMillis(System.currentTimeMillis()); 
		        Log.v("Toilet", "SettingsActivity: test currHour: the Hour is "+ Integer.toString(currHour)+".");
				//ָ����ʱ��¼��Activity
				Intent intent = new Intent(SettingsActivity.this, TimeToRecordBroadcastReceiver.class);
				//��intent�������ʼʱ������
	            Bundle mBundle = new Bundle();
	            mBundle.putInt("Hour", currHour);
	            mBundle.putInt("Minute", currMin);
	            intent.putExtras(mBundle);
	            Log.v("Toilet", "SettingsActivity: test Bundle: the Hour is "+ Integer.toString(mBundle.getInt("Hour"))+".");
				//ָ��PendingIntent
				PendingIntent sender = PendingIntent.getBroadcast(SettingsActivity.this, 0, intent, 0);
				//���AlarmManager����
				AlarmManager am; 
	            am = (AlarmManager)getSystemService(ALARM_SERVICE);
	            
				//�ж��ǿ������ǹر�
				if(isLogStarted){
					Log.v("Toilet", "SettingsActivity: before closeLog.");
					isLogStarted = false;
					btn_logSwitch.setBackgroundResource(R.drawable.switch_off);
					//�رն�ʱ����
					am.cancel(sender);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOff, 
							Toast.LENGTH_SHORT).show();
					
				}else
				{
					Log.v("Toilet", "SettingsActivity: before startLog.");
					isLogStarted = true;
					btn_logSwitch.setBackgroundResource(R.drawable.switch_on);
					//������ʱ����
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval*3000, sender); 
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOn, 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		/**************************************
		 * ѡ��ʱ����
		 **************************************/
		Log.v("Toilet", "before get btn_logInterval.");	
		txtvw_interval = (TextView)findViewById(R.id.act_settings_txtvw_interval);
		//ʱ������ʼ��
		txtvw_interval.setText(Integer.toString(interval));
		
		/*
		 * ���ʱ������TextView���������Ի��򣬹�����ʱ����
		 */
		//������Dialog�������
		final EditText edttxt_interval = new EditText(SettingsActivity.this);
		edttxt_interval.setInputType(0x00000002);
		//������Dialog�İ�ť����
		final DialogInterface.OnClickListener posListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				interval = Integer.parseInt(edttxt_interval.getText().toString());
				txtvw_interval.setText(Integer.toString(interval));
				//�ж��Ƕ�ʱ��¼�����ǿ������ǹر�
				if(isLogStarted){
					/*
					 * �����ʱ��¼���ܿ������ı�interval�Ĳ������������
					 * �����µ�ʱ����ֵ���¿�ʼ��ʱ��
					 */
		            //���ϵͳʱ��
		            Calendar c=Calendar.getInstance();
		            int currHour = c.get(Calendar.HOUR_OF_DAY);
		            int currMin = c.get(Calendar.MINUTE);
		            Log.v("Toilet", "SettingsActivity_Dialog: test currHour: the Hour is "+ Integer.toString(currHour)+".");
		            c.setTimeInMillis(System.currentTimeMillis()); 
					//ָ����ʱ��¼��Activity
					Intent intent = new Intent(SettingsActivity.this, TimeToRecordBroadcastReceiver.class);
					//ָ��PendingIntent
					PendingIntent sender = PendingIntent.getBroadcast(SettingsActivity.this, 0, intent, 0);
					//���AlarmManager����
					AlarmManager am; 
		            am = (AlarmManager)getSystemService(ALARM_SERVICE);
					//�رն�ʱ����
					Log.v("Toilet", "SettingsActivity: before closeLog.");
					am.cancel(sender);
					//������ʱ����
					Log.v("Toilet", "SettingsActivity: before startLog.");
					//���¿�����Activity������ʼʱ��
					Intent new_intent = new Intent(SettingsActivity.this, TimeToRecordBroadcastReceiver.class);
					Bundle mBundle = new Bundle();
		            mBundle.putInt("Hour", currHour);
		            mBundle.putInt("Minute", currMin);
		            new_intent.putExtras(mBundle);
		            Log.v("Toilet", "SettingsActivity_Dialog: test Bundle: the Hour is "+ Integer.toString(mBundle.getInt("Hour"))+".");
					PendingIntent new_sender = PendingIntent.getBroadcast(SettingsActivity.this, 0, new_intent, 0);					
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval*3000, new_sender); 
					//����Toast
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_intervalChanged, 
							Toast.LENGTH_SHORT).show();					
				}else{
					/*
					 * �����ʱ��¼���ܹرգ��ı�interval�Ĳ�����û�������
					 * �����µ�ʱ����ֵ���¿�ʼ��ʱ��
					 */
					//txtvw_interval.setText(Integer.toString(interval));
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_intervalFailed, 
							Toast.LENGTH_SHORT).show();					
				}				
			}
		};
		
		//��Ҫ��Dialog
		final AlertDialog dlg_interval = new AlertDialog.Builder(SettingsActivity.this)
			.setTitle(R.string.act_settings_dlg_interval_title)
			.setView(edttxt_interval)
			.setPositiveButton(R.string.act_settings_dlg_posBtn, posListener)
			.setNegativeButton(R.string.act_settings_dlg_negBtn, null)
			.create();
		
		
		//��Ӷ���ʾʱ������TextView�ĵ������
		txtvw_interval.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				dlg_interval.show();		
			} 
		});
		
		
		/**************************************
		 * �Ƿ���������
		 **************************************/
		Log.v("Toilet", "before get btn_ringtone.");
		btn_ringtone = (Button)findViewById(R.id.act_settings_btn_ring);
		//��ť��ʼ��
		if(isRingOn){
			btn_ringtone.setBackgroundResource(R.drawable.switch_on);	
		}else{
			btn_ringtone.setBackgroundResource(R.drawable.switch_off);			
		}
		//��Ӽ���
		btn_ringtone.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
				//�ж��ǿ������ǹر�
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
		 * �Ƿ���
		 **************************************/
		Log.v("Toilet", "before get btn_vibration.");
		btn_vibration = (Button)findViewById(R.id.act_settings_btn_vabrate);
		//��ť��ʼ��
		if(isVibrationOn){
			btn_vibration.setBackgroundResource(R.drawable.switch_on);	
		}else{
			btn_vibration.setBackgroundResource(R.drawable.switch_off);			
		}
		//��Ӽ���
		btn_vibration.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
				//�ж��ǿ������ǹر�
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
		 * �����͹رն�ʱ��¼����
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
         * ����
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
		 * �������
		 */
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean("isLogStarted", isLogStarted);
		editor.putBoolean("isRingOn", isRingOn);
		editor.putBoolean("isVibrationOn", isVibrationOn);
		editor.putInt("interval", interval);
		editor.commit();
	}
}
