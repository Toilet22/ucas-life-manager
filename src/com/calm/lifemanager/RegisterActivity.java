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

public class RegisterActivity extends Activity {
	EditText edt_name;
	EditText edt_pswd1;
	EditText edt_pswd2;
	EditText edt_email;
	
	Button btn_regst;
	Button btn_back;
	

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_register);
	        
	        edt_name = (EditText)findViewById(R.id.act_regst_edt_name);
	        edt_pswd1 = (EditText)findViewById(R.id.act_regst_edt_pswd1);
	        edt_pswd2 = (EditText)findViewById(R.id.act_regst_edt_pswd1);
	        edt_email = (EditText)findViewById(R.id.act_regst_edt_email);
	        
	        /*
	         * ×¢²á¹¦ÄÜ
	         */
	        btn_regst = (Button)findViewById(R.id.act_regst_btn_regst);
	        btn_regst.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new Thread() {
						public void run() {
							String username = edt_name.getText().toString();
							String password1 = edt_pswd1.getText().toString();
							String password2 = edt_pswd1.getText().toString();
							String email = edt_email.getText().toString();
							
							if(username.length() < 2) {
								Log.i("User Registration","User Name Too Short");
							}
							
							if(!(password1.equals(password2))) {
								Log.i("User Registration","Please input the same password");
							}
							
							if(email.length() <=0 || email == null) {
								// Do Nothing
							}
							
							Map<String, String> newUser = new HashMap<String, String>();
							newUser.put("username", username);
							newUser.put("password", password1);
	
							String retStr = null;
							try {
								retStr = NetToolUtil.sendPostRequest(
										NetToolUtil.accountRegisterUrl, newUser,
										"utf-8");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	
							if (retStr != null) {
								Log.i("User Registation", "result:" + retStr);
							}
							}
					}.start();
				}	        	
	        });
	        
	        /*
	         * ·µ»Ø
	         */
	        btn_back = (Button)findViewById(R.id.act_regst_btn_back);
	        btn_back.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v) {
					Intent iBack = new Intent(RegisterActivity.this, LoginActivity.class);
					startActivity(iBack);
					RegisterActivity.this.finish();
				}	        	
	        });
	 }

}
