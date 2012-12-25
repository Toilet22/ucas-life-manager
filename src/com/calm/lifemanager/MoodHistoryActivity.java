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
		
//		TabView view = null;
//		
//		// ��
//		view = new TabView(this,R.drawable.ic_launcher,R.drawable.ic_launcher);
//		view.setBackground(this.getResources().getDrawable(R.drawable.ic_launcher));
//		
//		TabSpec daylySpec = tabHost.newTabSpec("top_tab1");
//		//timeSpec.setIndicator(view);
//		timeSpec.setIndicator("��");
//		
//		Intent timeIntent = new Intent(this,TimeHistoryActivity.class);
//		timeSpec.setContent(timeIntent);
//		
//		// ��
//		view = new TabView(this, R.drawable.ic_launcher, R.drawable.ic_launcher);
//		view.setBackground(this.getResources().getDrawable(
//				R.drawable.ic_launcher));
//
//		TabSpec weeklySpec = tabHost.newTabSpec("top_tab2");
//		//timeSpec.setIndicator(view);
//		timeSpec.setIndicator("��");
//		Intent moodIntent = new Intent(this, MoodHistoryActivity.class);
//		timeSpec.setContent(moodIntent);
//
//		// ��
//		view = new TabView(this, R.drawable.ic_launcher, R.drawable.ic_launcher);
//		view.setBackground(this.getResources().getDrawable(
//				R.drawable.ic_launcher));
//
//		TabSpec monthlySpec = tabHost.newTabSpec("top_tab2");
//		//timeSpec.setIndicator(view);
//		timeSpec.setIndicator("��");
//		Intent moodIntent = new Intent(this, MoodHistoryActivity.class);
//		timeSpec.setContent(moodIntent);
//		
//		tabHost.addTab(daylySpec);
//		tabHost.addTab(weeklySpec);
//		tabHost.addTab(monthlySpec);
		
		//ΪTabHost��ӱ�ǩ     
        //�½�һ��newTabSpec(newTabSpec)     
        //�������ǩ��ͼ�꣨setIndicator��     
        //�������ݣ�setContent��     
		
		Intent daylyIntent = new Intent(this,DaylyMoodHistoryActivity.class);
		
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("ÿ��",getResources().getDrawable(R.drawable.ic_launcher))     
                .setContent(daylyIntent));
        
        Intent weeklyIntent = new Intent(this,WeeklyMoodHistoryActivity.class);
        
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("ÿ��",getResources().getDrawable(R.drawable.ic_launcher))     
                .setContent(weeklyIntent));
        
        Intent monthlyIntent = new Intent(this,MonthlyMoodHistoryActivity.class);
        
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("ÿ��",getResources().getDrawable(R.drawable.ic_launcher))     
                .setContent(monthlyIntent));
		
		tabHost.setCurrentTab(0);
	}
}
