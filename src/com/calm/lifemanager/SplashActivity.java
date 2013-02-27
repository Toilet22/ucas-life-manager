package com.calm.lifemanager;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3000; // —”≥Ÿ“ª√Î
	boolean isFirstUse;
	ImageView img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/************************************
		 * –›√ﬂ÷–ªΩ–—
		 ***********************************/
		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				//| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				//| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		setContentView(R.layout.activity_splash);
		
		Vibrator vib = (Vibrator) SplashActivity.this.getSystemService(Service.VIBRATOR_SERVICE); 
        long pattern[] = {200,2000,200,2000,200,2000,200,2000,200,2000,200,2000,200};
		//vib.vibrate(pattern, -1); 
		
		SharedPreferences judgeFirstUse = getSharedPreferences("firstUseApp", MODE_WORLD_READABLE);
		isFirstUse = judgeFirstUse.getBoolean("firstUseApp", true);
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent nextIntent = new Intent();
				if(true) {
					nextIntent.setClass(SplashActivity.this, UserTourActivity.class);
					
					// First Use, Create anonymous_user.db
					DatabaseUtil dbUtil = new DatabaseUtil(SplashActivity.this);
        	    	dbUtil.open();
        	    	
        	    	// Initial Assigned Types
        	    	dbUtil.initPrimeTypes();
        	    	dbUtil.initSubTypes();
        	    	
        	    	dbUtil.close();
        	    	
        	    	// Create Users Info Database
        	    	dbUtil = new DatabaseUtil(SplashActivity.this,userDataSync.usersInfoDbName);
        	    	dbUtil.open();
        	    	dbUtil.close();
				}
				else {
					nextIntent.setClass(SplashActivity.this, MainActivity.class);
					
					// Test Cloud Sync
					//nextIntent.setClass(SplashActivity.this, CloudSyncTestActivity.class);
				}
				SplashActivity.this.startActivity(nextIntent);
				SplashActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
		
		Editor editor = judgeFirstUse.edit();
		editor.putBoolean("firstUseApp", false);
		editor.commit();
		
	}
}
