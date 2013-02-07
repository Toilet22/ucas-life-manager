package com.calm.lifemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;

public class DaylyMoodHistoryActivity extends TabActivity {
	private static int currentSelectedTab = 0;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TabHost tabHost = getTabHost();

		LayoutInflater.from(this).inflate(
				R.layout.activity_history_mood_generic,
				tabHost.getTabContentView(), true);
		
		
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

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(detailsView)
				.setContent(R.id.activity_history_mood_daily_details));

		tabHost.setCurrentTab(0);

		// ��Ӽ����¼�
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				if (tabId.equals("tab1")) {
					Log.i("DailyMood", "Tab 1 selected!");
					currentSelectedTab = 0;
					
					// ��ʼ����ֲ�ͼ
					
				}
				if (tabId.equals("tab2")) {
					Log.i("DailyMood", "Tab 2 selected!");
					currentSelectedTab = 1;
					
					// ��ʼ�����б�
					
				}
			}
		});
		
		//ImageView imgViewMoodDistribute = (ImageView)findViewById(R.id.activity_history_time_daily_imageView_show_mood_distribute);
        
		//ListView listViewMoodDetails = (ListView)findViewById(R.id.activity_history_mood_daily_listView_show_details);
        
        ImageButton imgButtonBack = (ImageButton)findViewById(R.id.actitivity_history_mood_daily_imageButton_back);
        ImageButton imgButtonSuggestion = (ImageButton)findViewById(R.id.actitivity_history_mood_daily_imageButton_suggestion);
        ImageButton imgButtonSwapLeft = (ImageButton)findViewById(R.id.actitivity_history_mood_daily_imageButton_swap_left);
        ImageButton imgButtonSwapRight = (ImageButton)findViewById(R.id.actitivity_history_mood_daily_imageButton_swap_right);
        
        // ���ImageButton����¼�
        imgButtonBack.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		Log.i("DailyMood","Back Button pushed");
        		Intent backIntent = new Intent();
        		backIntent.setClass(DaylyMoodHistoryActivity.this,MainActivity.class);
        		startActivity(backIntent);
        		DaylyMoodHistoryActivity.this.finish();
        	}
        });
        
        imgButtonSuggestion.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		Log.i("DailyMood","Seggestion Button pushed");
        		
        		//��������Ի���
        		AlertDialog.Builder builder = new AlertDialog.Builder(DaylyMoodHistoryActivity.this.getParent());
        		
        		builder.setTitle("���齨��");
        		builder.setMessage("ЦһЦʮ���٣�");
        		builder.setPositiveButton("ȷ��", null);
        		builder.setNegativeButton("ȡ��", null); 
        		
        		AlertDialog suggestionDialog = builder.create();
        		
        		suggestionDialog.show();
        	}
        });
        
        imgButtonSwapLeft.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		Log.i("DailyMood","Swap Left Button pushed");
        		
        		//����ѡ��ı�ǩ�л���ͼ
        		switch(currentSelectedTab){
        		//����ֲ�
        		case 0:
        			Log.i("DailyMood","Processing Mood Distribution...");
        			break;
        		//�鿴�굥
        		case 1:
        			Log.i("DailyMood","Processing Details List...");
        			break;
        		default:
        			break;
        	
        		}
        	}
        });
        
        imgButtonSwapRight.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		Log.i("DailyMood","Swap Right Button pushed");
        		
        		//����ѡ��ı�ǩ�л���ͼ
        		
        		switch(currentSelectedTab){
        		//����ֲ�
        		case 0:
        			Log.i("DailyMood","Processing Mood Distribution...");
        			break;
        		//�鿴�굥
        		case 1:
        			Log.i("DailyMood","Processing Details List...");
        			break;
        		default:
        			break;
        	
        		}
        	}
        });
	}
}
