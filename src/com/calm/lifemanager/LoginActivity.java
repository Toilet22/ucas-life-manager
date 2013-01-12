package com.calm.lifemanager;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
	
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_login);
	        
	        remember = true; 
	        autolog = true;	

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
					}else{
						btn_remember.setBackgroundResource(R.drawable.switch_on);	
						remember = true;
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
					}else{
						btn_autolog.setBackgroundResource(R.drawable.switch_on);
						autolog = true;
						btn_remember.setBackgroundResource(R.drawable.switch_on);	
						remember = true;
					}
				}	        	
	        });
	        
	        /*
	         * 登录
	         */
	        btn_login = (Button)findViewById(R.id.act_login_login);
	        btn_login.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new Thread() {
						public void run() {
							// 获得用户名和密码
							String usernanme = edt_name.getText().toString();
							String password = edt_password.getText().toString();
							
							Map<String, String> loginUser = new HashMap<String, String>();
			        	    loginUser.put("username", usernanme);
			        	    loginUser.put("password", password);
			        	    
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
						}
					}.start();
				}       	
	        });
	        
	        /*
	         * 注册
	         */
	        btn_register = (Button)findViewById(R.id.act_login_register);
	        btn_register.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v) {
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
}
