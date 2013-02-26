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
	TextView txtvw_interval;
	long intervalInMillis = defaultIntervalInMillis;
	
	// 云同步最近同步时间
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
		setContentView(R.layout.activity_settings);
		
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
							"错误编号：" + error + "\n错误信息：" + errorMsg,
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
		 * 读取现有preferences。用以完成界面初始化
		 *****************************************/
		if(null == userDataSync.currentLogedInUser || "".equals(userDataSync.currentLogedInUser)) {
			userDataSync.currentLogedInUser = userDataSync.anonymousUser;
		} else {
			;
		}
		sharedPref = getSharedPreferences(
				userDataSync.currentLogedInUser, Context.MODE_PRIVATE);
		
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
				TimeLoggerHelper timgLoggerHelper = new TimeLoggerHelper(SettingsActivity.this);
				/*********************************
				 * 判断应该开启还是关闭定时记录
				 ********************************/
				if(isLogStarted){
					/************************
					 * 如果已开启，则关闭
					 ***********************/
					timgLoggerHelper.stopTimeLogger();
					//改变UI
					isLogStarted = false;
					btn_logSwitch.setBackgroundResource(R.drawable.switch_off);
					//弹出Toast
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOff, 
							Toast.LENGTH_SHORT).show();
				}else{
					/************************
					 * 如果已关闭，则开启
					 ***********************/
					timgLoggerHelper.launchTimeLogger();
					//改变UI
					isLogStarted = true;
					btn_logSwitch.setBackgroundResource(R.drawable.switch_on);
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_rglRcdOn, 
							Toast.LENGTH_SHORT).show();
				}
	         
			}
		});
		
		
		/**************************************
		 * 选择时间间隔
		 **************************************/
		Log.v("Toilet", "before get btn_logInterval.");	
		//时间间隔初始化
		txtvw_interval = (TextView)findViewById(R.id.act_settings_txtvw_interval);
		txtvw_interval.setText(Long.toString(intervalInMillis/60000));
		
		/***********************************
		 * 点击时间间隔的TextView，
		 * 将弹出对话框，供输入时间间隔
		 **********************************/
		//以下是Dialog的输入框
		final EditText edttxt_interval = new EditText(SettingsActivity.this);
		edttxt_interval.setInputType(0x00000002);
		//以下是Dialog的按钮监听
		final DialogInterface.OnClickListener posListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				intervalInMillis = (Integer.parseInt(edttxt_interval.getText().toString()))*60000;
				txtvw_interval.setText(Long.toString(intervalInMillis/60000));
				//写入preferences
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putLong("IntervalInMillis", intervalInMillis);
				editor.commit();
				
				/**********************************
				 * 判断是定时记录功能是开启还是关闭
				 * 如果已开启，则根据新间隔重启定时服务
				 * 如果未开启，则不做任何事
				 *********************************/
				if(isLogStarted){
					/*
					 * 如果定时记录功能开启，改变interval的操作是有意义的
					 * 根据新的时间间隔值重新开始计时。
					 */
		            TimeLoggerHelper timeLoggerHelper = new TimeLoggerHelper(SettingsActivity.this);
		            timeLoggerHelper.launchTimeLogger();
					//弹出Toast
					Toast.makeText(getApplicationContext(), R.string.act_settings_toast_intervalChanged, 
							Toast.LENGTH_SHORT).show();					
				}else{
					/*
					 * 如果定时记录功能关闭，改变interval的操作是没有意义的
					 * 根据新的时间间隔值重新开始计时。
					 */
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
		 * 设置自定义类别
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
						} else if(userDataSync.isWorkingOffline) {
							Toast.makeText(SettingsActivity.this,
									getText(R.string.success_log_out_with_offline),
									Toast.LENGTH_LONG).show();
							
							// Cancel the state of working offline
							userDataSync.isWorkingOffline = false;
							
							// Redirect User to Login Activity and Switch User
							userDataSync.isSwithingUser = true;
							Intent iLogin = new Intent(SettingsActivity.this, LoginActivity.class);
							startActivity(iLogin);
							
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
					        			NetToolUtil.serverUrl = NetToolUtil.serverUrlHttps;
					        			
					        			//retStr = NetToolUtil.sendGetRequest(NetToolUtil.accountLogoutUrl, null, userDataSync.defaultEncoding);
										
					        			retStr = NetToolUtil.sendGetRequestHttps(NetToolUtil.accountLogoutUrl, null, userDataSync.defaultEncoding);
					        			
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
							Toast.makeText(SettingsActivity.this,
									getText(R.string.syncing_data_in_background_en),
									Toast.LENGTH_LONG).show();
							
							// Sync User Data as a background service
							new Thread() {
								public void run() {
									DatabaseUtil dbUtil = new DatabaseUtil(SettingsActivity.this);
									Message msg = new Message();
									
									try {				
										// Sync User Data Table by Table
										Log.i("CloudSync","Start User Data Syncing...");
										
										// User Profile
										userDataSync.currentSyncDataTable = DatabaseUtil.USER_PROFILE;
										Log.i("CloudSync","Now Syncing: " + userDataSync.currentSyncDataTable);
										userDataSync.doUserDataSync(NetToolUtil.userProfilePullUrl, userDataSync.PULL, DatabaseUtil.USER_PROFILE, dbUtil, userDataSync.lastSyncTime);
										userDataSync.doUserDataSync(NetToolUtil.userProfilePushUrl, userDataSync.PUSH, DatabaseUtil.USER_PROFILE, dbUtil, userDataSync.lastSyncTime);
										
										// User Settings
										userDataSync.currentSyncDataTable = DatabaseUtil.USER_SETTINGS;
										Log.i("CloudSync","Now Syncing: " + userDataSync.currentSyncDataTable);
										userDataSync.doUserDataSync(NetToolUtil.userSettingsPullUrl, userDataSync.PULL, DatabaseUtil.USER_SETTINGS, dbUtil, userDataSync.lastSyncTime);
										userDataSync.doUserDataSync(NetToolUtil.userSettingsPushUrl, userDataSync.PUSH, DatabaseUtil.USER_SETTINGS, dbUtil, userDataSync.lastSyncTime);
										
										// TodoList
										userDataSync.currentSyncDataTable = DatabaseUtil.TODOLIST;
										Log.i("CloudSync","Now Syncing: " + userDataSync.currentSyncDataTable);
										userDataSync.doUserDataSync(NetToolUtil.todolistPullUrl, userDataSync.PULL, DatabaseUtil.TODOLIST, dbUtil, userDataSync.lastSyncTime);
										userDataSync.doUserDataSync(NetToolUtil.todolistPushUrl, userDataSync.PUSH, DatabaseUtil.TODOLIST, dbUtil, userDataSync.lastSyncTime);
										
										// WishList
										userDataSync.currentSyncDataTable = DatabaseUtil.WISHLIST;
										userDataSync.doUserDataSync(NetToolUtil.wishlistPullUrl, userDataSync.PULL, DatabaseUtil.WISHLIST, dbUtil, userDataSync.lastSyncTime);
										userDataSync.doUserDataSync(NetToolUtil.wishlistPushUrl, userDataSync.PUSH, DatabaseUtil.WISHLIST, dbUtil, userDataSync.lastSyncTime);
										
										// Collector
										userDataSync.currentSyncDataTable = DatabaseUtil.COLLECTOR;
										Log.i("CloudSync","Now Syncing: " + userDataSync.currentSyncDataTable);
										userDataSync.doUserDataSync(NetToolUtil.collectorPullUrl, userDataSync.PULL, DatabaseUtil.COLLECTOR, dbUtil, userDataSync.lastSyncTime);
										userDataSync.doUserDataSync(NetToolUtil.collectorPushUrl, userDataSync.PUSH, DatabaseUtil.COLLECTOR, dbUtil, userDataSync.lastSyncTime);
										
										// Record
										userDataSync.currentSyncDataTable = DatabaseUtil.RECORD;
										Log.i("CloudSync","Now Syncing: " + userDataSync.currentSyncDataTable);
										userDataSync.doUserDataSync(NetToolUtil.recordPullUrl, userDataSync.PULL, DatabaseUtil.RECORD, dbUtil, userDataSync.lastSyncTime);
										userDataSync.doUserDataSync(NetToolUtil.recordPushUrl, userDataSync.PUSH, DatabaseUtil.RECORD, dbUtil, userDataSync.lastSyncTime);
										
										// Prime Types
										userDataSync.currentSyncDataTable = DatabaseUtil.PRIM_TYPES;
										Log.i("CloudSync","Now Syncing: " + userDataSync.currentSyncDataTable);
										userDataSync.doUserDataSync(NetToolUtil.primeTypesPullUrl, userDataSync.PULL, DatabaseUtil.PRIM_TYPES, dbUtil, userDataSync.lastSyncTime);
										userDataSync.doUserDataSync(NetToolUtil.primeTypesPushUrl, userDataSync.PUSH, DatabaseUtil.PRIM_TYPES, dbUtil, userDataSync.lastSyncTime);
										
										// Sub Types
										userDataSync.currentSyncDataTable = DatabaseUtil.SUB_TYPES;
										Log.i("CloudSync","Now Syncing: " + userDataSync.currentSyncDataTable);
										userDataSync.doUserDataSync(NetToolUtil.subTypesPullUrl, userDataSync.PULL, DatabaseUtil.SUB_TYPES, dbUtil, userDataSync.lastSyncTime);
										userDataSync.doUserDataSync(NetToolUtil.subTypesPushUrl, userDataSync.PUSH, DatabaseUtil.SUB_TYPES, dbUtil, userDataSync.lastSyncTime);
										
										// Update Last Sync Time
										userDataSync.lastSyncTime = System.currentTimeMillis();
										
										Log.i("CloudSync","User Data Syncing Done!");
										Log.i("CloudSync","Last Sync Time is: " + userDataSync.lastSyncTime);
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										msg.what = HTTP_ERROR;
										mHandler.sendMessage(msg);
										e.printStackTrace();
									} finally {
										
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
		 * 保存更改
		 */
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean("isLogStarted", isLogStarted);
		editor.putBoolean("isRingOn", isRingOn);
		editor.putBoolean("isVibrationOn", isVibrationOn);
		editor.putLong("lastSyncTime", userDataSync.lastSyncTime);
		editor.commit();
	}
}
