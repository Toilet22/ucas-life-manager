package com.calm.lifemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CloudSyncTestActivity extends Activity {
	Button btn_user_register;
	Button btn_user_login;
	Button btn_user_logout;
	Button btn_userprofile_push;
	Button btn_userprofile_pull;
	Button btn_todolist_push;
	Button btn_todolisg_pull;
	Button btn_get_time_tip;
	Button btn_get_mood_tip;
	Button btn_check_network;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_sync_test);

        Log.i("Cloud", "Start Init Cloud Sync Test Buttons");
        
        btn_user_register = (Button)findViewById(R.id.act_cloud_sync_test_btn_user_register);
        
        btn_user_register.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
        
    	btn_user_login = (Button)findViewById(R.id.act_cloud_sync_test_btn_user_login);
    	
    	btn_user_login.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
    	
    	btn_user_logout = (Button)findViewById(R.id.act_cloud_sync_test_btn_user_logout);
    	
    	btn_user_logout.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
    	
    	btn_userprofile_push = (Button)findViewById(R.id.act_cloud_sync_test_btn_userprofile_push);
    	
    	btn_userprofile_push.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
    	
    	btn_userprofile_pull = (Button)findViewById(R.id.act_cloud_sync_test_btn_userprofile_pull);
    	
    	btn_userprofile_pull.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
    	
    	btn_todolist_push = (Button)findViewById(R.id.act_cloud_sync_test_btn_todolist_push);
    	
    	btn_todolist_push.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
    	
    	btn_todolisg_pull = (Button)findViewById(R.id.act_cloud_sync_test_btn_todolist_pull);
    	
    	btn_todolisg_pull.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
    	
    	btn_get_time_tip = (Button)findViewById(R.id.act_cloud_sync_test_btn_get_timp_tip);
    	
    	btn_get_time_tip.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
    	
    	btn_get_mood_tip = (Button)findViewById(R.id.act_cloud_sync_test_btn_get_mood_tip);
    	
    	btn_get_mood_tip.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
    	
    	btn_check_network = (Button)findViewById(R.id.act_cloud_sync_test_btn_check_network);
    	
    	btn_check_network.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.i("Cloud", "Start Testing ...");
			}
        });
        
    }
}
