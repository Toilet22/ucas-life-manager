package com.calm.lifemanager;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

public class TodaySummaryActivity extends TabActivity  implements TabHost.TabContentFactory {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_todaysummary);
		
		TabHost tabHost = getTabHost();
		
		//为TabHost添加标签     
        //新建一个newTabSpec(newTabSpec)     
        //设置其标签和图标（setIndicator）     
        //设置内容（setContent）     

		TabView timeView = null;
		
		// Time
		timeView = new TabView(this);
		timeView.setBackground(this.getResources().getDrawable(R.drawable.today_summary_time_btn));
		
        tabHost.addTab(tabHost.newTabSpec("bottom_tab1")
                .setIndicator(timeView)
        		//.setIndicator("时间",getResources().getDrawable(R.drawable.btn_time_cost))
                .setContent(this));
        
     
		
		// Mood
        TabView moodView = null;
        moodView = new TabView(this);
        moodView.setBackground(this.getResources().getDrawable(R.drawable.today_summary_mood_btn));
        
        tabHost.addTab(tabHost.newTabSpec("bottom_tab2")     
                .setIndicator(moodView)
                .setContent(this));
        
      
        // Task
        TabView taskView = null;
        taskView = new TabView(this);
        taskView.setBackground(this.getResources().getDrawable(R.drawable.today_summary_task_btn));
        
        tabHost.addTab(tabHost.newTabSpec("bottom_tab3")     
                .setIndicator(taskView)
                .setContent(this));
		
		tabHost.setCurrentTab(0);
		
		//添加Tab监听事件 
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {  
              
            public void onTabChanged(String tabId) {
            	
                if (tabId.equals("bottom_tab1")) {
                	Log.i("Today Summary","Tab 1 selected!");
                	
                }  
                if (tabId.equals("bottom_tab2")) {  
                	Log.i("Today Summary","Tab 2 selected!");
                }  
                if (tabId.equals("bottom_tab3")) {
                	Log.i("Today Summary","Tab 3 selected!");
                }  
            }  
        });
	}
	
	 public View createTabContent(String tag) {
	        final TextView tv = new TextView(this);
	        tv.setText("Content for tab with tag " + tag);
	        return tv;
	    }
}
