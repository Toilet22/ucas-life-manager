package com.calm.lifemanager;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	Button btn_remember;
	Button btn_autolog;
	Button btn_login;
	Button btn_register;
	Button btn_back;
	EditText edt_name;
	EditText edt_password;
	
	String name;
	String password;
	boolean remember;
	boolean autolog;
	
	private String userNameValue,passwordValue;  
    private SharedPreferences sp;  
	
    Handler mHandler;
    Runnable mRunnableShowToast;
    
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_login);
	        
	        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);  
	        
	        mHandler = new Handler();
	        
	        mRunnableShowToast = new Runnable()
	        {
	                    public void run() {
	                            // TODO Auto-generated method stub
	                    		Toast.makeText(LoginActivity.this,"Login Successfully!", Toast.LENGTH_LONG).show();
	                    }
	        }; 
	        
	        remember = true; //?????????????????????????????????????
	        autolog = true;	//????????????????????????????????????

	        edt_name = (EditText)findViewById(R.id.act_login_name);
	        edt_password = (EditText)findViewById(R.id.act_login_pswd);
	        
	        /*
	         * 是否记住密码	      
	         */
	        //初始化图标
	        btn_remember = (Button)findViewById(R.id.act_login_remember);
			if(remember){
				btn_remember.setBackgroundResource(R.drawable.switch_on);
			}else{
				btn_remember.setBackgroundResource(R.drawable.switch_off);	
			}
	        btn_remember.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v) {
					if(remember){
						btn_remember.setBackgroundResource(R.drawable.switch_off);
						remember = false;
						
						sp.edit().putBoolean("ISCHECK", false).commit(); 
					}else{
						btn_remember.setBackgroundResource(R.drawable.switch_on);	
						remember = true;
						
						sp.edit().putBoolean("ISCHECK", true).commit(); 
					}					
				}	        	
	        });
	        
	        
	        /*
	         * 是否自动登录
	         */
	        //初始化图标
	        btn_autolog = (Button)findViewById(R.id.act_login_autolog);
	        if(autolog){
				btn_autolog.setBackgroundResource(R.drawable.switch_on);
			}else{
				btn_autolog.setBackgroundResource(R.drawable.switch_off);
			}
	        btn_autolog.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v) {
					if(autolog){
						btn_autolog.setBackgroundResource(R.drawable.switch_off);
						autolog = false;
						
						sp.edit().putBoolean("AUTO_ISCHECK", false).commit(); 
					}else{
						btn_autolog.setBackgroundResource(R.drawable.switch_on);
						autolog = true;
						btn_remember.setBackgroundResource(R.drawable.switch_on);	
						remember = true;
						
						sp.edit().putBoolean("AUTO_ISCHECK", true).commit(); 
					}
				}	        	
	        });
	        
			/*
			 * 判断记住密码多选框的状态
			 */
			if (sp.getBoolean("ISCHECK", false)) {
				// 设置默认是记录密码状态
				btn_remember.setBackgroundResource(R.drawable.switch_on);
				
				edt_name.setText(sp.getString("USERNAME", ""));
				edt_password.setText(sp.getString("PASSWORD", ""));
				// 判断自动登陆多选框状态
				if (sp.getBoolean("AUTO_ISCHECK", false)) {
					// 设置默认是自动登录状态
					btn_autolog.setBackgroundResource(R.drawable.switch_on);
					
					// 验证用户登录
					validateUserLogin();
				}
			}
	        
	        /*
	         * 登录
	         */
	        btn_login = (Button)findViewById(R.id.act_login_login);
	        btn_login.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v) {
					// TODO Auto-generated method stub
					validateUserLogin();
				}
	        });
	        
	        /*
	         * 注册
	         */
	        btn_register = (Button)findViewById(R.id.act_login_register);
	        btn_register.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v) {
					Log.v("Toilet", "btn_register pushed.");
					Intent iRegst = new Intent(LoginActivity.this, RegisterActivity.class);
					startActivity(iRegst);
					LoginActivity.this.finish();
				}       	
	        });
	        
	        /*
	         * 返回
	         */
	        btn_back = (Button)findViewById(R.id.act_login_back);
	        btn_back.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v) {
					LoginActivity.this.finish();
					
				}
	        });
	 }
	 
	 public void validateUserLogin() {
		 new Thread() {
				public void run() {
					// 获得用户名和密码
					userNameValue = edt_name.getText().toString();
					passwordValue = edt_password.getText().toString();
					
					if(remember == true) {
						//记住用户名、密码、  
	                      Editor editor = sp.edit();
	                      editor.putString("USERNAME", userNameValue);
	                      editor.putString("PASSWORD",passwordValue);
	                      editor.commit();  
					}
					
					Map<String, String> loginUser = new HashMap<String, String>();
	        	    loginUser.put("username", userNameValue);
	        	    loginUser.put("password", passwordValue);
	        	    loginUser.put("visit_type", "android");
	        	    
	        	    String retStr = null;
	        	    try {
						retStr = NetToolUtil.sendPostRequest(NetToolUtil.accountLoginUrl, loginUser, "utf-8");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	    
	        	    if(retStr != null) {
	        	    	Log.i("User Login","result:" + retStr);
	        	    }
	        	    
	        	    JSONObject retJson = new JSONObject();
	        	    try {
						retJson = new JSONObject(retStr);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	    
	        	    String message = null;
	        	    try {
						message = retJson.getString("message");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	    
	        	    int status = 100;
	        	    try {
						status = retJson.getInt("status");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	    
	        	    if(status == 0) {
	        	    	// Login Successfully
	        	    	Intent iMain = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(iMain);
						LoginActivity.this.finish();
						
						mHandler.post(mRunnableShowToast);
	        	    }
	        	    else {
	        	    	// Login failed, pop up a dialog to alert user what is wrong
	        	    }
	        	    
	        	    Log.i("User Login","Return status is: " + status);
	        	    Log.i("User Login","Return message is: " + message);
				}
			}.start();
	 }
}
