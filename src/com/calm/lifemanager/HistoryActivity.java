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
		
		TabView view = null;
		
		// 时间
		view = new TabView(this,R.drawable.ic_launcher,R.drawable.ic_launcher);
		view.setBackground(this.getResources().getDrawable(R.drawable.ic_launcher));
		
		TabSpec timeSpec = tabHost.newTabSpec("时间");
		timeSpec.setIndicator(view);
		Intent timeIntent = new Intent(this,TimeHistoryActivity.class);
		timeSpec.setContent(timeIntent);
		
		// 时间
		view = new TabView(this, R.drawable.ic_launcher, R.drawable.ic_launcher);
		view.setBackground(this.getResources().getDrawable(
				R.drawable.ic_launcher));

		TabSpec moodSpec = tabHost.newTabSpec("时间");
		timeSpec.setIndicator(view);
		Intent moodIntent = new Intent(this, MoodHistoryActivity.class);
		timeSpec.setContent(timeIntent);
		
		tabHost.setCurrentTab(0);  
	}
}
