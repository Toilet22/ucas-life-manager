package com.calm.lifemanager;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TabHost;

public class FutureActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_future);
		
		TabHost tabHost = getTabHost();
		
		//ΪTabHost��ӱ�ǩ     
        //�½�һ��newTabSpec(newTabSpec)     
        //�������ǩ��ͼ�꣨setIndicator��     
        //�������ݣ�setContent��     
		
		TabView todolistView = null;
		
		// Todolist
		todolistView = new TabView(this);
		//todolistView.setBackground(this.getResources().getDrawable(R.drawable.todolist_future));
		todolistView.setBackgroundResource(R.drawable.todolist_future);
		
		Intent todolistIntent = new Intent(this,TodolistActivity.class);
		
        tabHost.addTab(tabHost.newTabSpec("bottom_tab1")
                .setIndicator(todolistView)
        		//.setIndicator("ʱ��",getResources().getDrawable(R.drawable.btn_time_cost))
                .setContent(todolistIntent));
        
     
		
		// Collector
        TabView collectorView = null;
        collectorView = new TabView(this);
        //collectorView.setBackground(this.getResources().getDrawable(R.drawable.collector_future));
        collectorView.setBackgroundResource(R.drawable.collector_future);
        
        Intent collectorIntent = new Intent(this, CollectorActivity.class);
        tabHost.addTab(tabHost.newTabSpec("bottom_tab2")     
                .setIndicator(collectorView)
                .setContent(collectorIntent));
        
      
        // Wishlist
        TabView wishlistView = null;
        wishlistView = new TabView(this);
        //wishlistView.setBackground(this.getResources().getDrawable(R.drawable.wishlist_future));
        wishlistView.setBackgroundResource(R.drawable.wishlist_future);
        
        Intent wishlistIntent = new Intent(this, WishlistActivity.class);
        tabHost.addTab(tabHost.newTabSpec("bottom_tab3")     
                .setIndicator(wishlistView)
                .setContent(wishlistIntent));
		
		tabHost.setCurrentTab(0);
		
		//���Tab�����¼�  
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {  
              
            public void onTabChanged(String tabId) {
            	
                if (tabId.equals("bottom_tab1")) {
                	Log.i("Future","Tab 1 selected!");
                	
                }  
                if (tabId.equals("bottom_tab2")) {  
                	Log.i("Future","Tab 2 selected!");
                }  
                if (tabId.equals("bottom_tab3")) {
                	Log.i("Future","Tab 3 selected!");
                }  
            }  
        });
	}
}
