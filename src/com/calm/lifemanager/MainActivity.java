package com.calm.lifemanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
	
	Button btn_cloud_sync_test;
	Button btn_achartengine_test;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /************************************************************************
         * UI���룺������
         ***********************************************************************/
        //������ť
        //Log.i("Toilet", "Start Initial Buttons");
        btn_login = (Button)findViewById(R.id.button5_login);
        btn_yesterday = (Button)findViewById(R.id.button1_yester);
        btn_tomorrow = (Button)findViewById(R.id.button3_tomorrow);
        
        btn_cloud_sync_test = (Button)findViewById(R.id.btn_cloud_sync_test);
        btn_achartengine_test = (Button)findViewById(R.id.btn_achartengine_test);
        
        		
        //�������ý���
        //Log.i("Toilet", "before push btn_settings");
        btn_settings = (Button)findViewById(R.id.button4_settings);
        btn_settings.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.v("Toilet", "push btn_settings");
				Intent itnt_settings = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(itnt_settings);
				//MainActivity.this.finish();
			}
        });
        
        //��¼/�л��û�/�˳�
        
        //�����ȥ����
        btn_yesterday = (Button)findViewById(R.id.button1_yester);
        btn_yesterday.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iMain = new Intent(MainActivity.this, HistoryActivity.class);
				startActivity(iMain);
			}
		});
        
        //�������ڵ���
        btn_today = (Button)findViewById(R.id.button2_today);
        btn_today.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iMain = new Intent(MainActivity.this, CurrentActivity.class);
				startActivity(iMain);
			}
		});
        
        //���뽫������
        btn_tomorrow = (Button)findViewById(R.id.button3_tomorrow);
        btn_tomorrow.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iMain = new Intent(MainActivity.this, FutureActivity.class);
				startActivity(iMain);
			}
		});
        
        // ������ͬ��
        btn_cloud_sync_test.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iMain = new Intent(MainActivity.this, CloudSyncTestActivity.class);
				startActivity(iMain);
			}
		});
        
        // ��ͼ�������
        btn_achartengine_test.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iMain = new Intent(MainActivity.this, ChartEngineTestActivity.class);
				startActivity(iMain);
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
