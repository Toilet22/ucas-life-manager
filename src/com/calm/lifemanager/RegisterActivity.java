package com.calm.lifemanager;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	EditText edt_name;
	EditText edt_pswd1;
	EditText edt_pswd2;
	EditText edt_email;
	
	Button btn_regst;
	Button btn_back;
	
	private  Handler mHandler;
	private Runnable mRunnableShowToast;
	
	private static final int USERNAME_EXSISTED = 11;
	    
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_register);
	        
	        mHandler = new Handler() {
	        	public void handleMessage(Message msg) {  
	                switch (msg.what) {  
	                case USERNAME_EXSISTED:
	                	;
	                default:
	                	;
	                }  
	                super.handleMessage(msg); 
	            };
	        };
	        
	        mRunnableShowToast = new Runnable()
	        {
	                    public void run() {
	                            // TODO Auto-generated method stub
	                    		Toast.makeText(RegisterActivity.this,"Register Successfully!", Toast.LENGTH_LONG).show();
	                    }
	        }; 
	        
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
							String password2 = edt_pswd2.getText().toString();
							String email = edt_email.getText().toString();
							
							// Validate User Registration Info
							if(username.length() < 2) {
								Log.i("User Registration","User Name Too Short");
								
								// Pop up a dialog to alert that user name too short
							}
							
							if(!(password1.equals(password2))) {
								Log.i("User Registration","Please input the same password");
								
								// Pop up a dialog to alert that password should be the same
							}
							
							// Pop up a dialog to alert user to retry
							
							if(email.length() <=0 || email == null) {
								// Do Nothing
							}
							
							Map<String, String> newUser = new HashMap<String, String>();
							newUser.put("username", username);
							newUser.put("password", password1);
							newUser.put("visit_type", "android");
							
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
							
							// Get info out of the String
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
			        	    	// Pop up a dialog to inform user success of registration
			        	    	
			        	    	// Create User Database
			        	    	DatabaseUtil dbUtil = new DatabaseUtil(RegisterActivity.this, username);
			        	    	dbUtil.open();
			        	    	dbUtil.close();
			        	    	
								// Show Toast of Successful Registration
								mHandler.post(mRunnableShowToast);
								
								// User Login
			        	    	Intent iLogin = new Intent(RegisterActivity.this, MainActivity.class);
								startActivity(iLogin);
								RegisterActivity.this.finish();
			        	    }
			        	    else {
			        	    	// Pop up a dialog to inform failure status and message of registration
			        	    	
			        	    }
			        	    
			        	    Log.i("User Login","Return status is: " + status);
			        	    Log.i("User Login","Return message is: " + message);
							
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
