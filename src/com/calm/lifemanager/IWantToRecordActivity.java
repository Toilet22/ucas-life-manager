package com.calm.lifemanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IWantToRecordActivity extends Activity {
	Button btn_back;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iwanttorecord);
		
		/*
		 * ����
		 */
		btn_back = (Button)findViewById(R.id.act_iRcd_btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				IWantToRecordActivity.this.finish();
			}
		});
	}
}
