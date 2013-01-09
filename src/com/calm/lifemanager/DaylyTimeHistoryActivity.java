package com.calm.lifemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;

public class DaylyTimeHistoryActivity extends TabActivity {
	private static int currentSelectedTab = 0;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        TabHost tabHost = getTabHost();
	        
	        LayoutInflater.from(this).inflate(R.layout.activity_history_time_generic,tabHost.getTabContentView(),true);
	        
	        TabView timeCostView = null;
	        timeCostView = new TabView(this,R.drawable.btn_time,R.drawable.btn_time);
	        //timeCostView.setBackground(this.getResources().getDrawable(R.drawable.btn_time));
			
			TabView efficientView = null;
			efficientView = new TabView(this,R.drawable.btn_efficient,R.drawable.btn_efficient);
			//efficientView.setBackground(this.getResources().getDrawable(R.drawable.btn_efficient));
			
			TabView detailsView = null;
			detailsView = new TabView(this,R.drawable.btn_sheet,R.drawable.btn_sheet);
			//detailsView.setBackground(this.getResources().getDrawable(R.drawable.btn_sheet));
			
	        tabHost.addTab(tabHost.newTabSpec("tab1")
	                .setIndicator(timeCostView)
	        		//.setIndicator("",getResources().getDrawable(R.drawable.btn_time))
	                .setContent(R.id.activity_history_time_daily_time_cost));
	        
	        tabHost.addTab(tabHost.newTabSpec("tab2")
	                .setIndicator(efficientView)     
	                .setContent(R.id.activity_history_time_daily_efficient));
	        
	        tabHost.addTab(tabHost.newTabSpec("tab3")
	                .setIndicator(detailsView)     
	                .setContent(R.id.activity_history_time_daily_details));
			
			tabHost.setCurrentTab(0);  
			
			//���Tab�����¼�  
	        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {  
	              
	            public void onTabChanged(String tabId) {
	            	
	                if (tabId.equals("tab1")) {  
	                	Log.i("DailyTime","Tab 1 selected!");
	                	currentSelectedTab = 0;
	                	
	                }  
	                if (tabId.equals("tab2")) {  
	                	Log.i("DailyTime","Tab 2 selected!");
	                	currentSelectedTab = 1;
	                }  
	                if (tabId.equals("tab3")) {  
	                	Log.i("DailyTime","Tab 3 selected!");
	                	currentSelectedTab = 2;
	                }  
	            }  
	        });
	        
	        final ImageView imgViewTimeCost = (ImageView)findViewById(R.id.activity_history_time_daily_imageView_show_time_cost);
	        //����ʱ��ֲ�ͼ����ʾ
	        //
	        //imgViewTimeCost.setImageResource(resId);
	        
	        ImageView imgViewShowEfficient = (ImageView)findViewById(R.id.activity_history_time_daily_imageView_show_efficient);
	        
	        ImageButton imgButtonBack = (ImageButton)findViewById(R.id.actitivity_history_time_daily_imageButton_back);
	        ImageButton imgButtonSuggestion = (ImageButton)findViewById(R.id.actitivity_history_time_daily_imageButton_suggestion);
	        ImageButton imgButtonSwapLeft = (ImageButton)findViewById(R.id.actitivity_history_time_daily_imageButton_swap_left);
	        ImageButton imgButtonSwapRight = (ImageButton)findViewById(R.id.actitivity_history_time_daily_imageButton_swap_right);
	        
	        // ���ImageButton����¼�
	        imgButtonBack.setOnClickListener(new Button.OnClickListener() {
	        	public void onClick(View v) {
	        		Log.i("DailyTime","Back Button pushed");
	        		Intent backIntent = new Intent();
	        		backIntent.setClass(DaylyTimeHistoryActivity.this,MainActivity.class);
	        		startActivity(backIntent);
	        		DaylyTimeHistoryActivity.this.finish();
	        	}
	        });
	        
	        imgButtonSuggestion.setOnClickListener(new Button.OnClickListener() {
	        	public void onClick(View v) {
	        		Log.i("DailyTime","Seggestion Button pushed");
	        		
	        		//��������Ի���
	        		AlertDialog.Builder builder = new AlertDialog.Builder(DaylyTimeHistoryActivity.this.getParent());
	        		
	        		builder.setTitle("ʱ�佨��");
	        		builder.setMessage("һ�����һ���");
	        		builder.setPositiveButton("ȷ��", null);
	        		builder.setNegativeButton("ȡ��", null); 
	        		
	        		AlertDialog suggestionDialog = builder.create();
	        		
	        		suggestionDialog.show();
	        	}
	        });
	        
	        imgButtonSwapLeft.setOnClickListener(new Button.OnClickListener() {
	        	public void onClick(View v) {
	        		Log.i("DailyTime","Swap Left Button pushed");
	        		
	        		//����ѡ��ı�ǩ�л���ͼ
	        		switch(currentSelectedTab){
	        		//ʱ��ֲ�
	        		case 0:
	        			Log.i("DailyTime","Processing Time Distribution...");
	        			// ����ͼƬ����
	        			//imgViewTimeCost.setImageResource(R.drawable.ic_launcher);
	        			
	        			// ����achartengine����Activity��ͼ
	        			try{
	        				Intent achartIntent = new PieChartIntentGenerator(new double[]{3,2,1}).execute(DaylyTimeHistoryActivity.this);
	        				startActivity(achartIntent);
	        			}catch(Exception e){
	        				Log.d("ChartEngine",e.getMessage());
	        			}        			
	        			break;
	        		//Ч�ʷֲ�
	        		case 1:
	        			Log.i("DailyTime","Processing Efficient Distribution...");
	        			break;
	        		//�鿴�굥
	        		case 2:
	        			Log.i("DailyTime","Processing Details List...");
	        			break;
	        		default:
	        			break;
	        	
	        		}
	        	}
	        });
	        
	        imgButtonSwapRight.setOnClickListener(new Button.OnClickListener() {
	        	public void onClick(View v) {
	        		Log.i("DailyTime","Swap Right Button pushed");
	        		
	        		//����ѡ��ı�ǩ�л���ͼ
	        		
	        		switch(currentSelectedTab){
	        		//ʱ��ֲ�
	        		case 0:
	        			Log.i("DailyTime","Processing Time Distribution...");
	        			// ����ͼƬ����
	        			//imgViewTimeCost.setImageResource(R.drawable.ic_launcher);
	        			
	        			// ����achartengine����Activity��ͼ
	        			try{
	        				Intent achartIntent = new PieChartIntentGenerator(new double[]{3,2,1}).execute(DaylyTimeHistoryActivity.this);
	        				startActivity(achartIntent);
	        			}catch(Exception e){
	        				Log.d("ChartEngine",e.getMessage());
	        			}
	        			break;
	        		//Ч�ʷֲ�
	        		case 1:
	        			Log.i("DailyTime","Processing Efficient Distribution...");
	        			break;
	        		//�鿴�굥
	        		case 2:
	        			Log.i("DailyTime","Processing Details List...");
	        			break;
	        		default:
	        			break;
	        	
	        		}
	        	}
	        });
	        
	    }
}
