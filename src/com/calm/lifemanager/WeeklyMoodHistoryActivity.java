package com.calm.lifemanager;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

public class WeeklyMoodHistoryActivity extends TabActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TabHost tabHost = getTabHost();
        
        LayoutInflater.from(this).inflate(R.layout.activity_history_mood_generic,tabHost.getTabContentView(),true);
        
		
        TabView moodDistributeView = null;
		//moodDistributeView = new TabView(this, R.drawable.btn_efficient,R.drawable.btn_efficient);
		moodDistributeView = new TabView(this);
		//moodDistributeView.setBackground(this.getResources().getDrawable(R.drawable.btn_efficient));
		moodDistributeView.setBackgroundResource(R.drawable.btn_efficient);

		TabView detailsView = null;
		//detailsView = new TabView(this, R.drawable.btn_sheet,R.drawable.btn_sheet);
		detailsView = new TabView(this);
		//detailsView.setBackground(this.getResources().getDrawable(R.drawable.btn_sheet));
		detailsView.setBackgroundResource(R.drawable.btn_sheet);
        
		tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator(moodDistributeView)     
                .setContent(R.id.activity_history_mood_daily_distribute));
        
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator(detailsView)     
                .setContent(R.id.activity_history_mood_daily_details));
		
		tabHost.setCurrentTab(0);  
		
		//Ìí¼Ó¼àÌýÊÂ¼þ  
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {  
              
            public void onTabChanged(String tabId) {  
                if (tabId.equals("tab1")) {  
                      
                }  
                if (tabId.equals("tab2")) {  
                    
                }  
            }  
        });  
          
    }
}
