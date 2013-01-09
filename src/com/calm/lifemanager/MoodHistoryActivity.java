package com.calm.lifemanager;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class MoodHistoryActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history_mood);
		
		TabHost tabHost = getTabHost();
		
		//为TabHost添加标签     
        //新建一个newTabSpec(newTabSpec)     
        //设置其标签和图标（setIndicator）     
        //设置内容（setContent）     
		
		TabView dailyView = null;
		//dailyView = new TabView(this,R.drawable.btn_day,R.drawable.btn_day);
		dailyView = new TabView(this);
		dailyView.setBackground(this.getResources().getDrawable(R.drawable.btn_day));
		
		TabView weeklyView = null;
		//weeklyView = new TabView(this,R.drawable.btn_week,R.drawable.btn_week);
		weeklyView = new TabView(this);
		weeklyView.setBackground(this.getResources().getDrawable(R.drawable.btn_week));
		
		TabView monthlyView = null;
		//monthlyView = new TabView(this,R.drawable.btn_month,R.drawable.btn_month);
		monthlyView = new TabView(this);
		monthlyView.setBackground(this.getResources().getDrawable(R.drawable.btn_month));
		
		Intent daylyIntent = new Intent(this,DaylyMoodHistoryActivity.class);
		
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator(dailyView)     
                .setContent(daylyIntent));
        
        Intent weeklyIntent = new Intent(this,WeeklyMoodHistoryActivity.class);
        
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator(weeklyView)     
                .setContent(weeklyIntent));
        
        Intent monthlyIntent = new Intent(this,MonthlyMoodHistoryActivity.class);
        
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator(monthlyView)     
                .setContent(monthlyIntent));
		
		tabHost.setCurrentTab(0);
	}
}
