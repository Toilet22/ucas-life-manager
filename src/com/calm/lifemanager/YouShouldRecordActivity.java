package com.calm.lifemanager;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.Service;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.calm.scrollwidget.ArrayWheelAdapter;
import com.calm.scrollwidget.OnWheelChangedListener;
import com.calm.scrollwidget.WheelView;

public class YouShouldRecordActivity extends Activity {
	RatingBar rtBar_effc;
	//RatingBar rtBar_mood;
	Button btn_excited;
	Button btn_happy;
	Button btn_ok;
	Button btn_sad;
	Button btn_angry;
	Button btn_save;
	Button btn_setStart;
	Button btn_setEnd;
	TextView txt_startTime;
	TextView txt_endTime;
	WheelView whl_fatherType;
	WheelView whl_subType;
	float rt_effc;
	float rt_mood;
	int startHour;
	int startMin;
	int endHour;
	int endMin;
	String type;
    String fatherType[];
    String subType[];
	//preferences记录
	SharedPreferences sharedPref;
	Boolean isRingOn;
	Boolean isVibrationOn;
	DatabaseUtil dbUtil;
	

	/*************************************** 
	 * 获得父类别的String[]
	 **************************************/
	private String[] getFatherTypeNameInStringArray(){
        //获得游标
		Cursor cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id, type_name FROM tb_prim_types", null);
        //测试游标
        if(cursor.moveToNext()){
        	Log.i("iRcd_FatherTypeNameInStringArray","PrimTypesActivity_fetchAllData: fetch specific data works!");
        }else{
        	Log.i("iRcd_FatherTypeNameInStringArray","PrimTypesActivity_fetchAllData: fetch specific data failed!");        	
        }        
		ArrayList<String> arrayFatherTypes = new ArrayList<String>();
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			Log.i("iRcd_FatherTypeNameInStringArray",cursor.getString(1));
			arrayFatherTypes.add(cursor.getString(1));
		}
		String[] retVal = (String[])arrayFatherTypes.toArray(new String[arrayFatherTypes.size()]);
		Log.i("iRcd_getFatherTypeNameInStringArray","after change type.");
		Log.i("iRcd_getFatherTypeNameInStringArray",retVal[1]);
		return retVal;
	}
	
	/*************************************** 
	 * 获得子类别的String[]
	 **************************************/
	private String[] getSubTypeNameInStringArray(String fatherTypeName){
        //获得游标
        Cursor cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id,  type_name FROM tb_sub_types where " + DatabaseUtil.KEY_TYPE_BELONGTO + "=?;",new String[] {fatherTypeName});
        //测试游标
        if(cursor.moveToNext()){
        	Log.i("iRcd_getSubTypeNameInStringArray","SubTypesActivity_fetchAllData: fetch specific data works!");
        }else{
        	Log.i("iRcd_getSubTypeNameInStringArray","SubTypesActivity_fetchAllData: fetch specific data failed!");        	
        }        
		ArrayList<String> arrayFatherTypes = new ArrayList<String>();
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			Log.i("iRcd_SubTypeNameInStringArray",cursor.getString(1));
			arrayFatherTypes.add(cursor.getString(1));
		}
		String[] retVal = (String[])arrayFatherTypes.toArray(new String[arrayFatherTypes.size()]);
		Log.i("iRcd_getSubTypeNameInStringArray","after change type.");
		Log.i("iRcd_getSubTypeNameInStringArray",retVal[1]);
		return retVal;
	}
	
	private void setAllBtnUnpushed(){
		btn_excited.setBackgroundResource(R.drawable.excited);
		btn_happy.setBackgroundResource(R.drawable.happy);
		btn_ok.setBackgroundResource(R.drawable.ok);
		btn_sad.setBackgroundResource(R.drawable.sad);
		btn_angry.setBackgroundResource(R.drawable.angry);
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		/************************************
		 * 休眠中唤醒
		 ***********************************/
		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				//| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				//| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		Log.v("YouRcd","YouRecord: before setContentView.");
		setContentView(R.layout.activity_youshouldrecord);	
		
		/************************************
		 * 读取preferences
		 ***********************************/
		Log.v("YouRcd","YouRecord: before get preferences.");
		if(null == userDataSync.currentLogedInUser || "".equals(userDataSync.currentLogedInUser)) {
			userDataSync.currentLogedInUser = userDataSync.anonymousUser;
		} else {
			;
		}
		sharedPref = getSharedPreferences(
				userDataSync.currentLogedInUser, Context.MODE_PRIVATE);
		isRingOn = sharedPref.getBoolean("isRingOn", true);
		isVibrationOn = sharedPref.getBoolean("isVibrationOn", true);
		
		/***************************************
		 * 铃声和振动
		 **************************************/

		Log.v("YouRcd","YouRecord: before set vibration.");
		if(isVibrationOn){
			Vibrator vib = (Vibrator) YouShouldRecordActivity.this.getSystemService(Service.VIBRATOR_SERVICE); 
	        long pattern[] = {20,200,20,200,20,200,100,200,20,200,20,200};
			vib.vibrate(pattern, -1); 
		}
		

        /***************************************
         * 获取数据库操作单元
         **************************************/
        dbUtil = new DatabaseUtil(this);
        Log.i("PrimTypesActivity","Open DB");
        dbUtil.open();
		
		/***************************************
		 * 读取开始时间并设置
		 **************************************/
		Log.v("YouRcd","YouRecord: before get Bundle.");
		Bundle mBundle = YouShouldRecordActivity.this.getIntent().getExtras();
		long startTimeInMillis = mBundle.getLong("StartTimeInMillis");
		//long startTimeInMillis = YouShouldRecordActivity.this.getIntent().getLongExtra("StartTimeInMillis", 0);
        Log.v("Toilet", "YouRcd_getStartTimeInMillis: test Bundle: the currTimeInMillis is " + Long.toString(startTimeInMillis)+".");
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeInMillis(startTimeInMillis);
		startHour = startTime.get(Calendar.HOUR_OF_DAY);
		startMin = startTime.get(Calendar.MINUTE);
		Log.v("YouRcd","YouRecord: the startHour is" + Integer.toString(startHour) +".");
		Log.v("YouRcd","YouRecord: the startMin is" + Integer.toString(startMin) +".");
		
		
		/***************************************
		 * timePicker起止时间设置
		 **************************************/
		//get current time
		Log.v("YouRcd","YouRecord: before use timePicker.");
		Calendar currTime = Calendar.getInstance();
		endHour = currTime.get(Calendar.HOUR_OF_DAY);
		endMin = currTime.get(Calendar.MINUTE);
		// init the time;
		txt_startTime = (TextView)findViewById(R.id.act_youRcd_txt_startTime);
		txt_startTime.setText(Integer.toString(startHour) + ":"+Integer.toString(startMin));
		txt_endTime = (TextView)findViewById(R.id.act_youRcd_txt_endTime);
		txt_endTime.setText(Integer.toString(endHour) + ":"+Integer.toString(endMin));
		
		// set start time;
		btn_setStart = (Button)findViewById(R.id.act_youRcd_btn_setStart);
		btn_setStart.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View view){
				new TimePickerDialog(YouShouldRecordActivity.this,new OnTimeSetListener(){
					public void onTimeSet(TimePicker view,int hour,int minute)
					{
						//set the startTime
						//startHour = hour;
						//startMin = minute;
						txt_startTime.setText(Integer.toString(startHour) + ":"+Integer.toString(startMin));
					}
				}, startHour, startMin, true).show();
			}
		});
		
		//set end time
		btn_setEnd = (Button)findViewById(R.id.act_youRcd_btn_setEnd);
		btn_setEnd.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View view){
				new TimePickerDialog(YouShouldRecordActivity.this,new OnTimeSetListener(){
					public void onTimeSet(TimePicker view,int hour,int minute)
					{
						//set the endTime
						endHour = hour;
						endMin = minute;
						txt_endTime.setText(Integer.toString(endHour) + ":"+Integer.toString(endMin));
					}
				}, endHour, endMin, true).show();
			}
		});

		/*************************************** 
		 * 获得父类别的String[]
		 **************************************/
		Log.i("YouRcd","before getFatherTypeNameInStringArray()");
        fatherType = getFatherTypeNameInStringArray();
        
		/***************************************
		 * 效率
		 **************************************/
		Log.v("YouRcd","YouRecord: before find RatingBar.");
		rtBar_effc = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_effc);
		//rtBar_mood = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_mood);
		btn_save = (Button)findViewById(R.id.act_youRcd_btn_save);
		
		/***************************************
		 * 心情相关
		 **************************************/
		Log.v("YouRcd", "YouShouldRcd: before get buttons about moods");
		btn_excited = (Button)findViewById(R.id.act_youRcd_btn_excited);
		btn_happy = (Button)findViewById(R.id.act_youRcd_btn_happy);
		btn_ok = (Button)findViewById(R.id.act_youRcd_btn_ok);
		btn_sad = (Button)findViewById(R.id.act_youRcd_btn_sad);
		btn_angry = (Button)findViewById(R.id.act_youRcd_btn_angry);

		//excited
		btn_excited.setOnClickListener(new Button.OnClickListener(){
			//按钮按下时，改变心情变量，改变按钮背景
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_excited.setBackgroundResource(R.drawable.excited_pushed);	
				rt_mood = 1;
			}			
		});
		//happy
		btn_happy.setOnClickListener(new Button.OnClickListener(){
			//按钮按下时，改变心情变量，改变按钮背景
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_happy.setBackgroundResource(R.drawable.happy_pushed);				
				rt_mood = 2;	
			}			
		});
		//ok
		btn_ok.setOnClickListener(new Button.OnClickListener(){
			//按钮按下时，改变心情变量，改变按钮背景
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_ok.setBackgroundResource(R.drawable.ok_pushed);		
				rt_mood = 3;			
			}			
		});
		//sad
		btn_sad.setOnClickListener(new Button.OnClickListener(){
			//按钮按下时，改变心情变量，改变按钮背景
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_sad.setBackgroundResource(R.drawable.sad_pushed);		
				rt_mood = 4;			
			}			
		});
		//angry
		btn_angry.setOnClickListener(new Button.OnClickListener(){
			//按钮按下时，改变心情变量，改变按钮背景
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_angry.setBackgroundResource(R.drawable.angry_pushed);			
				rt_mood = 5;		
			}			
		});
		
		
		
		/***************************************
		 * 以下是选择类别相关代码
		 **************************************/
		Log.v("YouRcd","YouRecord: before findWheelView.");
		whl_fatherType = (WheelView) findViewById(R.id.act_youRcd_wheel_fatherType);
        whl_fatherType.setAdapter(new ArrayWheelAdapter<String>(fatherType));
        whl_fatherType.setVisibleItems(5);
        whl_fatherType.setCurrentItem(1);

        whl_subType = (WheelView) findViewById(R.id.act_youRcd_wheel_subType);
        whl_subType.setVisibleItems(5);
		subType = getSubTypeNameInStringArray(fatherType[1]);
		whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType));
		whl_subType.setCurrentItem(1);

        whl_fatherType.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				subType = getSubTypeNameInStringArray(fatherType[newValue]);
				whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType));
				whl_subType.setCurrentItem(1);
			}
		});
        
		
        /********************************************************
		 * 以下是保存按钮
		 * 实际不执行保存，保存功能在onPause()方法中完成
		 *******************************************************/
		btn_save.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View arg0) {
				// 退出
				YouShouldRecordActivity.this.finish();
			}
		});
		

	}
	
	
	/*
	 * 在此完成数据的保存工作
	 */
	public void onPause(){
		super.onPause();
		// 获取记录数据
		rt_effc = rtBar_effc.getRating();
		//rt_mood = rtBar_mood.getRating();
		type = fatherType[whl_fatherType.getCurrentItem()] + "_"
				+ subType[whl_subType.getCurrentItem()];
		Toast.makeText(getApplicationContext(), type, 
				Toast.LENGTH_SHORT).show();	
		
		/************************************
		 * ?????????????????????????????????
		 * 存入数据库： 效率，心情，类别
		 *??????????????????????????????????
		 ***********************************/
		
		
		/********************************************
		//开启下一次计数
		 ********************************************/
		TimeLoggerHelper timeLoggerHelper = new TimeLoggerHelper(YouShouldRecordActivity.this);
		timeLoggerHelper.launchTimeLogger();
	}
	
	
	
	/*
	 * 在此开启下一次记录的计时
	 */
	public void onStop(){
		super.onStop();
		// 获取记录数据
				rt_effc = rtBar_effc.getRating();
				//rt_mood = rtBar_mood.getRating();
				type = fatherType[whl_fatherType.getCurrentItem()] + "_"
						+ subType[whl_subType.getCurrentItem()];
				Toast.makeText(getApplicationContext(), type, 
						Toast.LENGTH_SHORT).show();	
				
				/************************************
				 * ?????????????????????????????????
				 * 存入数据库： 效率，心情，类别
				 *??????????????????????????????????
				 ***********************************/
				
				
				/********************************************
				//开启下一次计数
				 ********************************************/
				TimeLoggerHelper timeLoggerHelper = new TimeLoggerHelper(YouShouldRecordActivity.this);
				timeLoggerHelper.stopTimeLogger();
				timeLoggerHelper.launchTimeLogger();
	}
}
