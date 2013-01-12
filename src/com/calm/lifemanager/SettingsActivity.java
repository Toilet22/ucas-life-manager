package com.calm.lifemanager;


import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	
	//�����͹رն�ʱ��¼
	Button btn_logSwitch;
	Boolean isLogStarted;
	TextView txtvw_logState;
	
	//���ر���
	Button btn_save;
	Button btn_back;
	
	//ѡ��ʱ����
	//Button btn_setInterval;
	TextView txtvw_logInterval;
	Spinner spnr_logInterval;
	int interval;
	static final String[] intervals = { "0.5Сʱ", "1Сʱ", "1.5Сʱ", "2Сʱ", "2.5Сʱ", "3.5Сʱ", "4Сʱ"};  
    ArrayAdapter <String> arrayAdapter = null;  
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet", "SettingActivity: before setContentView.");
		setContentView(R.layout.activity_settings);
		Log.v("Toilet", "SettingActivity: after setContentView.");
		
		Log.v("Toilet", "SettingActivity: before reading the datebase.");
		isLogStarted = true;
		txtvw_logState = (TextView)findViewById(R.id.textView1_LogState);
		interval = 3000;
		
		//btn_logInterval = (Button)findViewById(R.id.button_Interval);
		//txtvw_logInterval = (Button)findViewById(R.id.textView2_Interval);
		
		/**************************************
		 * �����͹رն�ʱ��¼����
		 **************************************/
		Log.v("Toilet", "before get btn_logSwitch.");
		btn_logSwitch = (Button)findViewById(R.id.button_LogSwitch);
		btn_logSwitch.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v){
				//ָ����ʱ��¼��Activity
				Intent intent = new Intent(SettingsActivity.this, TimeToRecordBroadcastReceiver.class);
				//ָ��PendingIntent
				PendingIntent sender = PendingIntent.getBroadcast(SettingsActivity.this, 0, intent, 0);
				//���AlarmManager����
				AlarmManager am; 
	            am = (AlarmManager)getSystemService(ALARM_SERVICE);
	            //���ϵͳʱ��
	            Calendar c=Calendar.getInstance();
	            c.setTimeInMillis(System.currentTimeMillis()); 
	            
				//�ж��ǿ������ǹر�
				if(isLogStarted){
					Log.v("Toilet", "SettingsActivity: before closeLog.");
					isLogStarted = false;
					txtvw_logState.setText(R.string.act_settings_txtvw_closed);
					btn_logSwitch.setText(R.string.act_settings_btn_startLog);
					//�رն�ʱ����
					am.cancel(sender);
					
				}else
				{
					Log.v("Toilet", "SettingsActivity: before startLog.");
					isLogStarted = true;
					txtvw_logState.setText(R.string.act_settings_txtvw_started);
					btn_logSwitch.setText(R.string.act_settings_btn_closeLog);
					//������ʱ����
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval, sender); 
				}
			}
		});
		
		
		/**************************************
		 * ѡ��ʱ����
		 **************************************/
		Log.v("Toilet", "before get btn_logInterval.");		
		spnr_logInterval = (Spinner)findViewById(R.id.act_setting_spnr_Interval);
		//������һ��ArrayAdapter����datas������simple_spinner_item��  
        //android.R.layout.simple_spinner_item����Android�ṩ��һ�ֱ�׼spinner�Ĳ���  
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,intervals);  
        //�������ؼ���ʱ����ۣ�Ϊϵͳ�ṩ��simple_spinner_dropdown_item   
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );   
        spnr_logInterval.setAdapter(arrayAdapter);  
        //Ϊspnr_logInterval���ѡ���¼�������  
        spnr_logInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
        {  
            public void onItemSelected(AdapterView<?> parent, View view,  int position, long id) {  
                //interval = (position+1) * 1800000;  
                interval = (position+1) * 3000;  
            }

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}  
        });  
        spnr_logInterval.setVisibility(View.VISIBLE);  

        /*******************************************
         * ���� 
         ********************************************/
		Log.v("Toilet", "before get btn_save.");
        btn_save = (Button)findViewById(R.id.act_setting_btn_save);
        btn_save.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){

        	}
        });
        
        
        /*******************************************
         * ����
         ********************************************/
		Log.v("Toilet", "before get btn_back.");
		btn_back = (Button)findViewById(R.id.act_setting_btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
		
		
		
	}

}
