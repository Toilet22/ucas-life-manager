package com.calm.lifemanager;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HistoryActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history);
		
		TabHost tabHost = getTabHost();
		
		//为TabHost添加标签     
        //新建一个newTabSpec(newTabSpec)     
        //设置其标签和图标（setIndicator）     
        //设置内容（setContent）     
		
		TabView timeView = null;
		
		// 时间
		//timeView = new TabView(this,R.drawable.btn_time_cost,R.drawable.btn_time_cost);
		//timeView.setBackground(this.getResources().getDrawable(R.drawable.btn_time_cost));
		
		//timeView = new TabView(this,R.drawable.btn_time_cost);
		
		timeView = new TabView(this);
		//timeView.setBackground(this.getResources().getDrawable(R.drawable.btn_time_cost));
		//timeView.setBackgroundResource(R.drawable.btn_time_cost);
		timeView.setBackgroundResource(R.drawable.btn_history_time);
		
		Intent timeIntent = new Intent(this,TimeHistoryActivity.class);
		
        tabHost.addTab(tabHost.newTabSpec("bottom_tab1")     
                .setIndicator(timeView)
        		//.setIndicator("时间",getResources().getDrawable(R.drawable.btn_time_cost))
                .setContent(timeIntent));
        
        TabView moodView = null;
		
		// 心情
        //moodView = new TabView(this,R.drawable.btn_mood,R.drawable.btn_mood);
        //moodView.setBackground(this.getResources().getDrawable(R.drawable.btn_mood));
		
        moodView = new TabView(this);
        //moodView.setBackground(this.getResources().getDrawable(R.drawable.btn_mood));
        //moodView.setBackgroundResource(R.drawable.btn_mood);
        moodView.setBackgroundResource(R.drawable.btn_history_mood);
        
        Intent moodIntent = new Intent(this, MoodHistoryActivity.class);
        tabHost.addTab(tabHost.newTabSpec("bottom_tab2")     
                .setIndicator(moodView)
                .setContent(moodIntent));
		
		tabHost.setCurrentTab(0);
	}
}
