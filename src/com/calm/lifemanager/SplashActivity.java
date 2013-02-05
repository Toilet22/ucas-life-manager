package com.calm.lifemanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3000; // —”≥Ÿ»˝√Î
	boolean isFirstUse;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		SharedPreferences judgeFirstUse = getSharedPreferences("firstUseApp", MODE_WORLD_READABLE);
		isFirstUse = judgeFirstUse.getBoolean("firstUseApp", true);
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent nextIntent = new Intent();
				if(isFirstUse) {
					nextIntent.setClass(SplashActivity.this, UserTourActivity.class);
				}
				else {
					nextIntent.setClass(SplashActivity.this, MainActivity.class);
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
