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
	//��ť
	Button btn_doRcd;
	Button btn_history;
	Button btn_future;
	Button btn_settings;
	Button btn_login;
	//TextView
	TextView txtvw_logState;
	TextView txtvw_logNextTime;
	//�����͹رն�ʱ��¼
	Button btn_logSwitch;
	Boolean isLogStarted;
	long nextTimeInMillis;
	int nextTime_Hour, nextTime_Min;
	Calendar nextLogTime;
	
	//ʱ����
	final long defaultIntervalInMillis = 30*60000;
	long intervalInMillis = defaultIntervalInMillis;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current);
		nextLogTime = Calendar.getInstance();
		
		/******************************************
		 * ��ȡ����preferences��������ɽ����ʼ��
		 *****************************************/
		Log.v("Toilet", "NewCurrAct: before sharedPref.");
		//preferences��¼
		SharedPreferences sharedPref;
		sharedPref = getSharedPreferences(
		        getString(R.string.curr_usr_name), Context.MODE_PRIVATE);
		if(sharedPref.contains("isLogStarted")){
			isLogStarted = sharedPref.getBoolean("isLogStarted", false);
			intervalInMillis = sharedPref.getLong("IntervalInMillis", defaultIntervalInMillis);
			nextTimeInMillis = sharedPref.getLong("NextLogTimeInMillis", 0);
		}else{
			isLogStarted = false;
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean("isLogStarted", isLogStarted);
			editor.putLong("IntervalInMillis", intervalInMillis);
			editor.commit();
		}
		//�����Ƿ�����ʱ��¼��ʼ��ҳ��
		Log.v("Toilet", "NewCurrAct: before get txtvw & btn.");
		txtvw_logState = (TextView)findViewById(R.id.act_curr_txtvw_logState);
		txtvw_logNextTime = (TextView)findViewById(R.id.act_curr_txtvw_logNextTime);
		btn_logSwitch = (Button)findViewById(R.id.act_curr_btn_logSwitch);
		if(isLogStarted){
			txtvw_logState.setText(R.string.act_current_txtvw_logStarted);
			btn_logSwitch.setText(R.string.act_current_btn_logStop);
			nextLogTime.setTimeInMillis(nextTimeInMillis);
			nextTime_Hour = nextLogTime.get(Calendar.HOUR_OF_DAY);
			nextTime_Min = nextLogTime.get(Calendar.MINUTE);
			txtvw_logNextTime.setText(Integer.toString(nextTime_Hour)+":"+Integer.toString(nextTime_Min));
		}else{
			txtvw_logState.setText(R.string.act_current_txtvw_logStopped);
			btn_logSwitch.setText(R.string.act_current_btn_logStart);
		}
		
		//��Ӽ���
		Log.v("Toilet", "NewCurrAct: before btn_logSwitch.setOnClickListener.");
		btn_logSwitch.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
				TimeLoggerHelper timgLoggerHelper = new TimeLoggerHelper(MainActivity.this);
				/*********************************
				 * �ж�Ӧ�ÿ������ǹرն�ʱ��¼
				 ********************************/
				if(isLogStarted){
					/************************
					 * ����ѿ�������ر�
					 ***********************/
					timgLoggerHelper.stopTimeLogger();
					
					//�ı�UI
					isLogStarted = false;
					txtvw_logState.setText(R.string.act_current_txtvw_logStopped);
					btn_logSwitch.setText(R.string.act_current_btn_logStart);
					txtvw_logNextTime.setText(R.string.act_current_txtvw_nextLogTime);
					
					//����Toast
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOff, 
							Toast.LENGTH_SHORT).show();
				}else{
					/************************
					 * ����ѹرգ�����
					 ***********************/
					timgLoggerHelper.launchTimeLogger();
					//�ı�UI
					isLogStarted = true;
					txtvw_logState.setText(R.string.act_current_txtvw_logStarted);
					btn_logSwitch.setText(R.string.act_current_btn_logStop);
					txtvw_logNextTime.setText(Integer.toString(nextTime_Hour)+":"+Integer.toString(nextTime_Min));
					//preferences��¼
					SharedPreferences sharedPref;
					sharedPref = getSharedPreferences(
					        getString(R.string.curr_usr_name), Context.MODE_PRIVATE);
					nextTimeInMillis = sharedPref.getLong("NextLogTimeInMillis", 30*60000);
					nextLogTime.setTimeInMillis(nextTimeInMillis);
					nextTime_Hour = nextLogTime.get(Calendar.HOUR_OF_DAY);
					nextTime_Min = nextLogTime.get(Calendar.MINUTE);
					txtvw_logNextTime.setText(Integer.toString(nextTime_Hour)+":"+Integer.toString(nextTime_Min));
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOn, 
							Toast.LENGTH_SHORT).show();
				}
		        /*
				//���ϵͳʱ��
				Calendar c=Calendar.getInstance();
		        int currHour = c.get(Calendar.HOUR_OF_DAY);
		        int currMin = c.get(Calendar.MINUTE);
		           c.setTimeInMillis(System.currentTimeMillis()); 
		        Log.v("Toilet", "MainActivity: test currHour: the Hour is "+ Integer.toString(currHour)+".");
				//ָ����ʱ��¼��Activity
				Intent intent = new Intent(MainActivity.this, TimeToRecordBroadcastReceiver.class);
				//��intent�������ʼʱ������
		        Bundle mBundle = new Bundle();
		        mBundle.putInt("Hour", currHour);
		        mBundle.putInt("Minute", currMin);
			    intent.putExtras(mBundle);
			    Log.v("Toilet", "MainActivity: test Bundle: the Hour is "+ Integer.toString(mBundle.getInt("Hour"))+".");
				//ָ��PendingIntent
				PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
				//���AlarmManager����
				AlarmManager am; 
			    am = (AlarmManager)getSystemService(ALARM_SERVICE);
			           
				//�ж��ǿ������ǹر�
				if(isLogStarted){
					Log.v("Toilet", "MainActivity: before closeLog.");
					isLogStarted = false;
					txtvw_logState.setText(R.string.act_current_txtvw_logStopped);
					btn_logSwitch.setText(R.string.act_current_btn_logStart);
					//�رն�ʱ����
					am.cancel(sender);
					txtvw_logNextTime.setText(R.string.act_current_txtvw_nextLogTime);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOff, 
							Toast.LENGTH_SHORT).show();
					
				}else{
					Log.v("Toilet", "MainActivity: before startLog.");
					isLogStarted = true;
					txtvw_logState.setText(R.string.act_current_txtvw_logStarted);
					btn_logSwitch.setText(R.string.act_current_btn_logStop);
					//������ʱ����
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval*60000, sender); 
					//���´ε���ʱ��д��preference
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putInt("NextLogTime_Hour", currHour + interval/60);
					editor.putInt("NextLogTime_Min", currMin + interval % 60);
					editor.commit();
					Log.v("Toilet", "SettingsActivity: test the NextLogTime_Hour:" +Integer.toString(currHour + interval/60)+".");
					Log.v("Toilet", "SettingsActivity: test the NextLogTime_Min:" +Integer.toString(currMin + interval%60)+".");
					txtvw_logNextTime.setText(Integer.toString(currHour + interval/60)+" : "+Integer.toString(currHour + interval/60));
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOn, 
							Toast.LENGTH_SHORT).show();
				}
				*/
			}
		});
		
		
		/***************************************
		 * ��¼ʱ��
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
		 * ��ʷ��¼
		 ***************************************/
		btn_history = (Button)findViewById(R.id.act_curr_btn_history);
		btn_history.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				Intent iHistory = new Intent(MainActivity.this, HistoryActivity.class);
				startActivity(iHistory);				
			}		
		});
		
		/****************************************
		 * δ���滮
		 ***************************************/
		btn_future = (Button)findViewById(R.id.act_curr_btn_future);
		btn_future.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				Intent iFuture = new Intent(MainActivity.this, FutureActivity.class);
				startActivity(iFuture);				
			}		
		});
		
		/****************************************
		 * ����
		 ***************************************/
		btn_settings = (Button)findViewById(R.id.act_curr_btn_settings);
		btn_settings.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				Intent iSettings = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(iSettings);	
			}		
		});
		
		/****************************************
		 * ��¼
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
		 * �������
		 */
		//preferences��¼
		SharedPreferences sharedPref;
		sharedPref = getSharedPreferences(
		        getString(R.string.curr_usr_name), MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean("isLogStarted", isLogStarted);
		editor.commit();
	}

	public void onRestart(){
		super.onRestart();

		/********************************
		 * ��ʼ���붨ʱ��¼��ص�UI
		 *******************************/
		//preferences��¼
		SharedPreferences sharedPref;
		sharedPref = getSharedPreferences(
		        getString(R.string.curr_usr_name), Context.MODE_PRIVATE);
		sharedPref = getSharedPreferences(
		        getString(R.string.curr_usr_name), Context.MODE_PRIVATE);
		if(isLogStarted){
			Log.v("Toilet", "MainActivity onStart(): reinit the UIs.");
			txtvw_logState.setText(R.string.act_current_txtvw_logStarted);
			btn_logSwitch.setText(R.string.act_current_btn_logStop);
			nextTimeInMillis = sharedPref.getLong("NextLogTimeInMillis", 30*60000);
			nextLogTime.setTimeInMillis(nextTimeInMillis);
			nextTime_Hour = nextLogTime.get(Calendar.HOUR_OF_DAY);
			nextTime_Min = nextLogTime.get(Calendar.MINUTE);
			Log.v("Toilet", "MainActivity.onStart(): NextLogTimeInMillis = "+Long.toString(nextTimeInMillis)+".");
			txtvw_logNextTime.setText(Integer.toString(nextTime_Hour)+":"+Integer.toString(nextTime_Min));
		}else{
			txtvw_logState.setText(R.string.act_current_txtvw_logStopped);
			btn_logSwitch.setText(R.string.act_current_btn_logStart);
		}		
	}
}
