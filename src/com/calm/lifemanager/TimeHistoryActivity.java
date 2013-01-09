package com.calm.lifemanager;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class TimeHistoryActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history_time);
		
		TabHost tabHost = getTabHost();
		
//		TabView view = null;
//		
//		// 日
//		view = new TabView(this,R.drawable.ic_launcher,R.drawable.ic_launcher);
//		view.setBackground(this.getResources().getDrawable(R.drawable.ic_launcher));
//		
//		TabSpec daylySpec = tabHost.newTabSpec("top_tab1");
//		//timeSpec.setIndicator(view);
//		timeSpec.setIndicator("日");
//		
//		Intent timeIntent = new Intent(this,TimeHistoryActivity.class);
//		timeSpec.setContent(timeIntent);
//		
//		// 周
//		view = new TabView(this, R.drawable.ic_launcher, R.drawable.ic_launcher);
//		view.setBackground(this.getResources().getDrawable(
//				R.drawable.ic_launcher));
//
//		TabSpec weeklySpec = tabHost.newTabSpec("top_tab2");
//		//timeSpec.setIndicator(view);
//		timeSpec.setIndicator("周");
//		Intent moodIntent = new Intent(this, MoodHistoryActivity.class);
//		timeSpec.setContent(moodIntent);
//
//		// 月
//		view = new TabView(this, R.drawable.ic_launcher, R.drawable.ic_launcher);
//		view.setBackground(this.getResources().getDrawable(
//				R.drawable.ic_launcher));
//
//		TabSpec monthlySpec = tabHost.newTabSpec("top_tab2");
//		//timeSpec.setIndicator(view);
//		timeSpec.setIndicator("月");
//		Intent moodIntent = new Intent(this, MoodHistoryActivity.class);
//		timeSpec.setContent(moodIntent);
//		
//		tabHost.addTab(daylySpec);
//		tabHost.addTab(weeklySpec);
//		tabHost.addTab(monthlySpec);
		
		//为TabHost添加标签     
        //新建一个newTabSpec(newTabSpec)     
        //设置其标签和图标（setIndicator）     
        //设置内容（setContent） 
		
		TabView dailyView = null;
		dailyView = new TabView(this,R.drawable.btn_day,R.drawable.btn_day);
		//dailyView.setBackground(this.getResources().getDrawable(R.drawable.btn_day));
		
		TabView weeklyView = null;
		weeklyView = new TabView(this,R.drawable.btn_week,R.drawable.btn_week);
		//weeklyView.setBackground(this.getResources().getDrawable(R.drawable.btn_week));
		
		TabView monthlyView = null;
		monthlyView = new TabView(this,R.drawable.btn_month,R.drawable.btn_month);
		//monthlyView.setBackground(this.getResources().getDrawable(R.drawable.btn_month));
        
		Intent daylyIntent = new Intent(this,DaylyTimeHistoryActivity.class);
		
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator(dailyView)     
                .setContent(daylyIntent));
        
        Intent weeklyIntent = new Intent(this,WeeklyTimeHistoryActivity.class);
        
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator(weeklyView)     
                .setContent(weeklyIntent));
        
        Intent monthlyIntent = new Intent(this,MonthlyTimeHistoryActivity.class);
        
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator(monthlyView)     
                .setContent(monthlyIntent));
		
		tabHost.setCurrentTab(0);  
	}
}
