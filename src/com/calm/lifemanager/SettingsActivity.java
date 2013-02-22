package com.calm.lifemanager;


import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	final long defaultIntervalInMillis = 30*60000;
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
	long intervalInMillis = defaultIntervalInMillis;
	//static final String[] intervals = { "0.5Сʱ", "1Сʱ", "1.5Сʱ", "2Сʱ", "2.5Сʱ", "3Сʱ","3.5Сʱ", "4Сʱ"};  
    //ArrayAdapter <String> arrayAdapter = null;  
	
	// ��ͬ�����ͬ��ʱ��
	long defaultLastSyncTime = 0;
	
	private Handler mHandler;
	private Runnable mRunnableShowToast;
	
	private ProgressDialog pd;
	private Bundle logoutBundle = new Bundle();

	private static final int LOGOUT_SUCCESS = 0;
	private static final int LOGOUT_ERROR = 1;
	private static final int HTTP_ERROR = -1;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet", "SettingActivity: before setContentView.");
		setContentView(R.layout.activity_settings);
		Log.v("Toilet", "SettingActivity: after setContentView.");
		
		Log.v("Toilet", "SettingActivity: before reading the database.");
		
		// Handler Implementation
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case LOGOUT_SUCCESS:
					// Redirect User to Login Activity and Switch User
					userDataSync.isSwithingUser = true;
					Intent iLogin = new Intent(SettingsActivity.this, LoginActivity.class);
					startActivity(iLogin);
					
					Toast.makeText(SettingsActivity.this,
							getText(R.string.switch_user),
							Toast.LENGTH_LONG).show();
					break;
				case LOGOUT_ERROR:
					Bundle errorBundle = (Bundle) msg.obj;
					int error = errorBundle.getInt("error");
					String errorMsg = errorBundle.getString("errorMsg");
					Toast.makeText(SettingsActivity.this,
							"�����ţ�" + error + "\n������Ϣ��" + errorMsg,
							Toast.LENGTH_LONG).show();
					break;
				case HTTP_ERROR:
					Toast.makeText(SettingsActivity.this,
							getText(R.string.login_http_error),
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			};
		};
		
		/******************************************
		 * ��ȡ����preferences��������ɽ����ʼ��
		 *****************************************/
		sharedPref = getSharedPreferences(
		        getString(R.string.curr_usr_name), Context.MODE_PRIVATE);
		if(sharedPref.contains("IntervalInMillis")){
			isLogStarted = sharedPref.getBoolean("isLogStarted", false);
			intervalInMillis = sharedPref.getLong("IntervalInMillis", defaultIntervalInMillis);
			isRingOn = sharedPref.getBoolean("isRingOn", true);
			isVibrationOn = sharedPref.getBoolean("isVibrationOn", true);
			userDataSync.lastSyncTime = sharedPref.getLong("lastSyncTime", defaultLastSyncTime);
		}else{
			isLogStarted = false;
			isRingOn = true;
			isVibrationOn = true;
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean("isLogStarted", isLogStarted);
			editor.putBoolean("isRingOn", isRingOn);
			editor.putBoolean("isVibrationOn", isVibrationOn);
			editor.putLong("IntervalInMillis", intervalInMillis);
			editor.putLong("lastSyncTime", userDataSync.lastSyncTime);
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
				TimeLoggerHelper timgLoggerHelper = new TimeLoggerHelper(SettingsActivity.this);
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
					btn_logSwitch.setBackgroundResource(R.drawable.switch_off);
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
					btn_logSwitch.setBackgroundResource(R.drawable.switch_on);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOn, 
							Toast.LENGTH_SHORT).show();
				}
	           /*/���ϵͳʱ��
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
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval*60000, sender); 
					//���´ε���ʱ��д��preference
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putInt("NextLogTime_Hour", currHour + interval/60);
					editor.putInt("NextLogTime_Min", currMin + interval % 60);
					editor.commit();
					Log.v("Toilet", "SettingsActivity: test the NextLogTime_Hour:" +Integer.toString(currHour + interval/60)+".");
					Log.v("Toilet", "SettingsActivity: test the NextLogTime_Min:" +Integer.toString(currHour + interval%60)+".");
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOn, 
							Toast.LENGTH_SHORT).show();
				}
				*/
			}
		});
		
		
		/**************************************
		 * ѡ��ʱ����
		 **************************************/
		Log.v("Toilet", "before get btn_logInterval.");	
		//ʱ������ʼ��
		txtvw_interval = (TextView)findViewById(R.id.act_settings_txtvw_interval);
		txtvw_interval.setText(Long.toString(intervalInMillis/60000));
		
		/***********************************
		 * ���ʱ������TextView��
		 * �������Ի��򣬹�����ʱ����
		 **********************************/
		//������Dialog�������
		final EditText edttxt_interval = new EditText(SettingsActivity.this);
		edttxt_interval.setInputType(0x00000002);
		//������Dialog�İ�ť����
		final DialogInterface.OnClickListener posListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				intervalInMillis = (Integer.parseInt(edttxt_interval.getText().toString()))*60000;
				txtvw_interval.setText(Long.toString(intervalInMillis/60000));
				//д��preferences
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putLong("IntervalInMillis", intervalInMillis);
				editor.commit();
				
				/**********************************
				 * �ж��Ƕ�ʱ��¼�����ǿ������ǹر�
				 * ����ѿ�����������¼��������ʱ����
				 * ���δ�����������κ���
				 *********************************/
				if(isLogStarted){
					/*
					 * �����ʱ��¼���ܿ������ı�interval�Ĳ������������
					 * �����µ�ʱ����ֵ���¿�ʼ��ʱ��
					 */
		            TimeLoggerHelper timeLoggerHelper = new TimeLoggerHelper(SettingsActivity.this);
		            timeLoggerHelper.launchTimeLogger();
					//����Toast
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_intervalChanged, 
							Toast.LENGTH_SHORT).show();					
				}else{
					/*
					 * �����ʱ��¼���ܹرգ��ı�interval�Ĳ�����û�������
					 * �����µ�ʱ����ֵ���¿�ʼ��ʱ��
					 */
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
				Intent iSettings = new Intent(SettingsActivity.this, MainActivity.class);
				startActivity(iSettings);
				finish();
			}
		});
		
		/**
		 * User Data Sync Section
		 */
		// User Name Indicator
		txtvw_current_loged_in_user = (TextView)findViewById(R.id.act_settings_txtvw_current_loged_user);
		//txtvw_current_loged_in_user.setText("DayDayUp");
		txtvw_current_loged_in_user.setText(userDataSync.currentLogedInUser);
		
		// Switch User
		btn_switch_user = (Button)findViewById(R.id.act_settings_btn_switch_user);
		btn_switch_user.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
						
						if(null == userDataSync.currentLogedInUser || "".equals(userDataSync.currentLogedInUser)) {
							Toast.makeText(SettingsActivity.this,
									getText(R.string.please_login_first),
									Toast.LENGTH_LONG).show();
						} else {
							pd = ProgressDialog.show(SettingsActivity.this, "",
									getString(R.string.is_logingout));
							
							new Thread() {
								public void run() {
									
									Looper.prepare();
									Message msg = new Message();
									
									// Log out current user
									String retStr = null;
									String message = null;
									int status = 100;
					        		
					        		try {
					        			retStr = NetToolUtil.sendGetRequest(NetToolUtil.accountLogoutUrl, null, userDataSync.defaultEncoding);
										
					        			// Get info out of the String
										JSONObject retJson = new JSONObject(retStr);
										message = retJson.getString("message");
										status = retJson.getInt("status");
										
										if (status == 0) {
											// Show Toast of Successful Logout
											msg.what = LOGOUT_SUCCESS;
										} else {
											// Logout Error
											logoutBundle.putInt("error", status);
											logoutBundle.putString("errorMsg", message);
											msg.what = LOGOUT_ERROR;
										}
										msg.obj = logoutBundle;
										mHandler.sendMessage(msg);
										
									} catch (Exception e) {
										// TODO Auto-generated catch block
										msg.what = HTTP_ERROR;
										mHandler.sendMessage(msg);
										e.printStackTrace();
									}  finally {
										pd.dismiss();
										Looper.loop();
									}
								}
							}.start();
					
						}
			}
		});
		
		// Sync User Data
		btn_sync_data = (Button)findViewById(R.id.act_settings_btn_sync_data);
		btn_sync_data.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				
						// Make sure user is connected to network & is online
						if(!NetToolUtil.isConnnected(SettingsActivity.this)) {
							Toast.makeText(SettingsActivity.this,
									getText(R.string.login_http_error),
									Toast.LENGTH_LONG).show();
						} else if(null == userDataSync.currentLogedInUser || "".equals(userDataSync.currentLogedInUser)) {
							Toast.makeText(SettingsActivity.this,
									getText(R.string.please_login_first),
									Toast.LENGTH_LONG).show();
						} else {
							// Sync User Data as a background service
							new Thread() {
								public void run() {
									// Sync User Data Table by Table
									DatabaseUtil dbUtil = new DatabaseUtil(SettingsActivity.this);
									dbUtil.open();
									
									try {
										userDataSync.currentSyncDataTable = DatabaseUtil.USER_PROFILE;
										userDataSync.doUserDataSync(NetToolUtil.userProfilePullUrl, userDataSync.PULL, DatabaseUtil.USER_PROFILE, dbUtil, userDataSync.lastSyncTime);
										userDataSync.doUserDataSync(NetToolUtil.userProfilePushUrl, userDataSync.PUSH, DatabaseUtil.USER_PROFILE, dbUtil, userDataSync.lastSyncTime);
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}.start();
							
							
						}
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
		editor.commit();
	}
}
