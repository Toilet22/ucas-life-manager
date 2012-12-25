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
		
		// ʱ��
		view = new TabView(this,R.drawable.ic_launcher,R.drawable.ic_launcher);
		view.setBackground(this.getResources().getDrawable(R.drawable.ic_launcher));
		
		TabSpec timeSpec = tabHost.newTabSpec("ʱ��");
		timeSpec.setIndicator(view);
		Intent timeIntent = new Intent(this,TimeHistoryActivity.class);
		timeSpec.setContent(timeIntent);
		
		// ʱ��
		view = new TabView(this, R.drawable.ic_launcher, R.drawable.ic_launcher);
		view.setBackground(this.getResources().getDrawable(
				R.drawable.ic_launcher));

		TabSpec moodSpec = tabHost.newTabSpec("ʱ��");
		timeSpec.setIndicator(view);
		Intent moodIntent = new Intent(this, MoodHistoryActivity.class);
		timeSpec.setContent(timeIntent);
		
		tabHost.setCurrentTab(0);  
	}
}
