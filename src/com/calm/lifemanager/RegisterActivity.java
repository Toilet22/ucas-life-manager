package com.calm.lifemanager;

import android.app.Activity;
import android.os.Bundle;
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
	        
	        btn_regst = (Button)findViewById(R.id.act_regst_btn_regst);
	        btn_back = (Button)findViewById(R.id.act_regst_btn_back);
	 }

}
