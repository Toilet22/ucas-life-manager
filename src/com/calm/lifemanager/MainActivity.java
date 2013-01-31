package com.calm.lifemanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button btn_settings;
	Button btn_login;
	Button btn_yesterday;
	Button btn_today;
	Button btn_tomorrow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Deprecate android network on main thread exception
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//        .detectDiskReads()
//        .detectDiskWrites()
//        .detectNetwork()   // or .detectAll() for all detectable problems
//        .penaltyLog()
//        .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//        .detectLeakedSqlLiteObjects()
//        .detectLeakedClosableObjects()
//        .penaltyLog()
//        .penaltyDeath()
//        .build());
        
        /************************************************************************
         * UI代码：苏轶伦
         ***********************************************************************/
        //声明按钮
        Log.v("Toilet", "start init btns");
        
        //btn_cloud_sync_test = (Button)findViewById(R.id.btn_cloud_sync_test);
        //btn_achartengine_test = (Button)findViewById(R.id.btn_achartengine_test);
        
        		
        //进入设置界面
        Log.v("Toilet", "before push any btn");
        btn_settings = (Button)findViewById(R.id.button4_settings);
        btn_settings.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.v("Toilet", "btn_settings pushed");
				Intent itnt_settings = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(itnt_settings);
				//MainActivity.this.finish();
			}
        });
        
        //登录/切换用户/退出
        
        //进入过去的我
        btn_yesterday = (Button)findViewById(R.id.button1_yester);
        btn_yesterday.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
		        Log.v("Toilet", "btn_yesterday pushed");
				Intent iHistry = new Intent(MainActivity.this, HistoryActivity.class);
				startActivity(iHistry);
			}
		});
        
        //进入现在的我
        btn_today = (Button)findViewById(R.id.button2_today);
        btn_today.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
		        Log.v("Toilet", "btn_current pushed");
				Intent iCurr = new Intent(MainActivity.this, CurrentActivity.class);
				startActivity(iCurr);
			}
		});
        
        //进入将来的我
        btn_tomorrow = (Button)findViewById(R.id.button3_tomorrow);
        btn_tomorrow.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
		        Log.v("Toilet", "btn_tomorrow pushed");
				Intent iFuture = new Intent(MainActivity.this, FutureActivity.class);
				startActivity(iFuture);
			}
		});
        
        //进入登录页面
        btn_login = (Button)findViewById(R.id.button5_login);
        btn_login.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
		        Log.v("Toilet", "btn_login pushed");
				Intent iLogin = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(iLogin);
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
