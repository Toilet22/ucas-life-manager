package com.calm.lifemanager;

import java.util.Calendar;

import com.calm.lifemanager.R;
import com.calm.scrollwidget.*;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class YouShouldRecordActivity extends Activity {
	RatingBar rtBar_effc;
	RatingBar rtBar_mood;
	Button btn_save;
	float rt_effc;
	float rt_mood;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet","YouRecord: before setContentView.");
		setContentView(R.layout.activity_youshouldrecord);

		Log.v("Toilet","YouRecord: before find RatingBar.");
		rtBar_effc = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_effc);
		rtBar_mood = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_mood);
		btn_save = (Button)findViewById(R.id.act_youRcd_btn_save);
		
		/*
		 * 以下是选择类别相关代码
		 */
		Log.v("Toilet","YouRecord: before findWheelView.");
		WheelView whl_fatherType = (WheelView) findViewById(R.id.act_youRcd_wheel_fatherType);
        String fatherType[] = new String[] {"学习", "工作", "社交", "娱乐", "思考", "运动"};
        whl_fatherType.setAdapter(new ArrayWheelAdapter<String>(fatherType));
        whl_fatherType.setVisibleItems(5);
        whl_fatherType.setCurrentItem(1);

        final String subType[][] = new String[][] {
        		new String[] {"上课", "预习", "做作业", "复习", "非专业知识", "其他"},
        		new String[] {"上班", "写代码", "处理文档", "联系客户", "总结工作", "规划工作", "跑腿儿", "其他"},
        		new String[] {"朋友聚餐", "电话联系", "网络交流", "外出游玩", "其他"},
        		new String[] {"看电影", "玩游戏", "听音乐", "上网", "外出旅行", "逛街", "棋牌乐", "其他"},
        		new String[] {"人生愿景", "长期规划", "短期目标", "一会儿干啥", "工作难题", "灵光乍现！", "人情世故", "心中的他/她", "其他"},
        		new String[] {"散步", "跑步", "球类运动", "田径项目", "极限运动", "其他"}
        		};
        
        final WheelView whl_subType = (WheelView) findViewById(R.id.act_youRcd_wheel_subType);
        whl_subType.setVisibleItems(5);
		whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType[1]));
		whl_subType.setCurrentItem(1);

        whl_fatherType.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType[newValue]));
				whl_subType.setCurrentItem(subType[newValue].length / 2);
			}
		});
        
		
		/*
		 * 以下是保存记录
		 */
		btn_save.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View arg0) {
				// 获取记录数据
				rt_effc = rtBar_effc.getRating();
				rt_mood = rtBar_mood.getRating();
				/************************************
				 * ?????????????????????????????????
				 * 存入数据库
				 *??????????????????????????????????*/
				
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
	            //???????????interval?????????????????
	            int interval = 3000;
				am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval, sender); 
				// 退出
				YouShouldRecordActivity.this.finish();
			}
		});
		
		
	}
	
}
