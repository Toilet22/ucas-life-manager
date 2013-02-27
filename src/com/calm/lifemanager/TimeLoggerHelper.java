package com.calm.lifemanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TimeLoggerHelper {
	long currTimeInMillis;
	long nextTimeInMillis;
	Activity thisActivity;
	
	public TimeLoggerHelper(Activity activity){		
		currTimeInMillis = 0;
		nextTimeInMillis = 0;
		thisActivity = activity;
	}
	
	public void launchTimeLogger(){
		/*********************************
		 * ���ϵͳʱ�䣬��������ʱ��
		 * �����´ε���ʱ��д��preference
		 ********************************/
		if(null == userDataSync.currentLogedInUser || "".equals(userDataSync.currentLogedInUser)) {
			userDataSync.currentLogedInUser = userDataSync.anonymousUser;
		} else {
			;
		}
		SharedPreferences sharedPref = thisActivity.getSharedPreferences(
				userDataSync.currentLogedInUser, Context.MODE_PRIVATE);
		long intervalInMillis = sharedPref.getLong("IntervalInMillis", 30 * 60000);
		//for test only
		intervalInMillis /= 30;
		Log.v("Toilet", "TimeLoggerHelper: test IntervalInMillis is "+ Long.toString(intervalInMillis)+".");
        Calendar c=Calendar.getInstance();
        currTimeInMillis = c.getTimeInMillis();
        nextTimeInMillis = currTimeInMillis + intervalInMillis;
        Log.v("Toilet", "TimeLoggerHelper: test currTimeInMillis is "+ Long.toString(currTimeInMillis)+".");
        Log.v("Toilet", "TimeLoggerHelper: test nextTimeInMillis is "+ Long.toString(nextTimeInMillis)+".");
        c.setTimeInMillis(System.currentTimeMillis()); 
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putLong("NextLogTimeInMillis", nextTimeInMillis);
		editor.commit();
		
		
		/********************************
		 * ���¿�����Activity������ʼʱ��
		 *******************************/
		//�ƶ�Ҫ������Activity
		Intent new_intent = new Intent(thisActivity, TimeToRecordBroadcastReceiver.class);		
		//Intent new_intent = new Intent(thisActivity, SplashActivity.class);			
		Bundle mBundle = new Bundle();
        mBundle.putLong("StartTimeInMillis", currTimeInMillis);
        new_intent.putExtras(mBundle);
        //new_intent.putExtra("StartTimeInMillis", currTimeInMillis);
        Log.v("Toilet", "TimeLoggerHelper: test Bundle: the currTimeInMillis is "+ Long.toString(new_intent.getExtras().getLong("StartTimeInMillis"))+".");
        
        /********************************
         * ������ʱ�� 
         *******************************/
        //���AlarmManager����
		AlarmManager am; 
        am = (AlarmManager)thisActivity.getSystemService("alarm");
		//������ʱ����
		Log.v("Toilet", "SettingsActivity: before startLog.");
		PendingIntent new_sender = PendingIntent.getBroadcast(thisActivity, 0, new_intent, 0);					
		am.set(AlarmManager.RTC_WAKEUP, nextTimeInMillis, new_sender);
				
	}			
	
	public void stopTimeLogger(){
		/********************************
		 * �رն�Ӧ��alarmManager
		 *******************************/
		Intent new_intent = new Intent(thisActivity, TimeToRecordBroadcastReceiver.class);	
		//Intent new_intent = new Intent(thisActivity, SplashActivity.class);				
		PendingIntent sender = PendingIntent.getBroadcast(thisActivity, 0, new_intent, 0);					
		AlarmManager am = (AlarmManager)thisActivity.getSystemService("alarm");
		am.cancel(sender);
	}
	
	
}
