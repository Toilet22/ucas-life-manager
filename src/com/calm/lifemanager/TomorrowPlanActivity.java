package com.calm.lifemanager;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

public class TomorrowPlanActivity extends TabActivity implements TabHost.TabContentFactory {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tomorrowplan);
		
		TabHost tabHost = getTabHost();
		
		//ΪTabHost��ӱ�ǩ     
        //�½�һ��newTabSpec(newTabSpec)
        //�������ǩ��ͼ�꣨setIndicator��     
        //�������ݣ�setContent��     

		TabView todolistView = null;
		
		// Todolist
		todolistView = new TabView(this);
		//todolistView.setBackground(this.getResources().getDrawable(R.drawable.tomorrow_plan_todo_btn));
		todolistView.setBackgroundResource(R.drawable.tomorrow_plan_todo_btn);
		
        tabHost.addTab(tabHost.newTabSpec("bottom_tab1")
                .setIndicator(todolistView)
        		//.setIndicator("ʱ��",getResources().getDrawable(R.drawable.tomorrow_plan_todo_btn))
                .setContent(this));
        
     
		
        // Collector
        TabView collectorView = null;
        collectorView = new TabView(this);
        //collectorView.setBackground(this.getResources().getDrawable(R.drawable.tomorrow_plan_collect_btn));
        collectorView.setBackgroundResource(R.drawable.tomorrow_plan_collect_btn);
        
        tabHost.addTab(tabHost.newTabSpec("bottom_tab2")     
                .setIndicator(collectorView)
                .setContent(this));
        
      
        // Wishlist
        TabView wishlistView = null;
        wishlistView = new TabView(this);
        //wishlistView.setBackground(this.getResources().getDrawable(R.drawable.tomorrow_plan_wish_btn));
        wishlistView.setBackgroundResource(R.drawable.tomorrow_plan_wish_btn);
        
        tabHost.addTab(tabHost.newTabSpec("bottom_tab3")     
                .setIndicator(wishlistView)
                .setContent(this));
		
		tabHost.setCurrentTab(0);
		
		//���Tab�����¼� 
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