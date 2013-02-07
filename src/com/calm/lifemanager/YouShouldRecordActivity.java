package com.calm.lifemanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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
	WheelView whl_fatherType;
	WheelView whl_subType;
	float rt_effc;
	float rt_mood;
	String type;
    String fatherType[] = new String[] {"学习", "工作", "社交", "娱乐", "思考", "运动"};
    String subType[][] = new String[][] {
    		new String[] {"上课", "预习", "做作业", "复习", "非专业知识", "其他"},
    		new String[] {"上班", "写代码", "处理文档", "联系客户", "总结工作", "规划工作", "跑腿儿", "其他"},
    		new String[] {"朋友聚餐", "电话联系", "网络交流", "外出游玩", "其他"},
    		new String[] {"看电影", "玩游戏", "听音乐", "上网", "外出旅行", "逛街", "棋牌乐", "其他"},
    		new String[] {"人生愿景", "长期规划", "短期目标", "一会儿干啥", "工作难题", "灵光乍现！", "人情世故", "心中的他/她", "其他"},
    		new String[] {"散步", "跑步", "球类运动", "田径项目", "极限运动", "其他"}
    		};
	//preferences记录
	SharedPreferences sharedPref;
	Boolean isRingOn;
	Boolean isVibrationOn;
	int interval;
	
	private void setAllBtnUnpushed(){
		btn_excited.setBackgroundResource(R.drawable.excited);
		btn_happy.setBackgroundResource(R.drawable.happy);
		btn_ok.setBackgroundResource(R.drawable.ok);
		btn_sad.setBackgroundResource(R.drawable.sad);
		btn_angry.setBackgroundResource(R.drawable.angry);
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet","YouRecord: before setContentView.");
		setContentView(R.layout.activity_youshouldrecord);
		
		/*
		 * 读取preferences
		 */
		sharedPref = getSharedPreferences(getString(R.string.curr_usr_name), 
				Context.MODE_PRIVATE);
		isRingOn = sharedPref.getBoolean("isRingOn", true);
		isVibrationOn = sharedPref.getBoolean("isVibrationOn", true);
		interval = sharedPref.getInt("interval", 30);

		/***************************************
		 * 铃声和振动
		 **************************************/
		if(isVibrationOn){
			Vibrator vib = (Vibrator) YouShouldRecordActivity.this.getSystemService(Service.VIBRATOR_SERVICE); 
	        long pattern[] = {20,200,20,200,20,200,100,200,20,200,20,200};
			vib.vibrate(pattern, -1); 
		}
		
		
		/***************************************
		 * 效率
		 **************************************/
		Log.v("Toilet","YouRecord: before find RatingBar.");
		rtBar_effc = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_effc);
		//rtBar_mood = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_mood);
		btn_save = (Button)findViewById(R.id.act_youRcd_btn_save);
		
		/***************************************
		 * 心情相关
		 **************************************/
		Log.v("Toilet", "YouShouldRcd: before get buttons about moods");
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
		Log.v("Toilet","YouRecord: before findWheelView.");
		whl_fatherType = (WheelView) findViewById(R.id.act_youRcd_wheel_fatherType);
        whl_fatherType.setAdapter(new ArrayWheelAdapter<String>(fatherType));
        whl_fatherType.setVisibleItems(5);
        whl_fatherType.setCurrentItem(1);

        
        whl_subType = (WheelView) findViewById(R.id.act_youRcd_wheel_subType);
        whl_subType.setVisibleItems(5);
		whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType[1]));
		whl_subType.setCurrentItem(1);

        whl_fatherType.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType[newValue]));
				whl_subType.setCurrentItem(subType[newValue].length / 2);
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
				+ subType[whl_fatherType.getCurrentItem()][whl_subType.getCurrentItem()];
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
		//指定定时记录的Activity
		Intent intent = new Intent(YouShouldRecordActivity.this, TimeToRecordBroadcastReceiver.class);
		//指定PendingIntent
		PendingIntent sender = PendingIntent.getBroadcast(YouShouldRecordActivity.this, 0, intent, 0);
		//获得AlarmManager对象
		AlarmManager am; 
        am = (AlarmManager)getSystemService(ALARM_SERVICE);
        //获得系统时间
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis()); 
        //开启定时服务
		am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval*3000, sender); 
	}
	
}
