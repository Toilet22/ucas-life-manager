package com.calm.lifemanager;


import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.calm.scrollwidget.ArrayWheelAdapter;
import com.calm.scrollwidget.OnWheelChangedListener;
import com.calm.scrollwidget.WheelView;

public class IWantToRecordActivity extends Activity {
	RatingBar rtBar_effc;
	//RatingBar rtBar_mood;
	Button btn_excited;
	Button btn_happy;
	Button btn_ok;
	Button btn_sad;
	Button btn_angry;
	WheelView whl_fatherType;
	WheelView whl_subType;
	Button btn_save;
	Button btn_setStart;
	Button btn_setEnd;
	TextView txt_startTime;
	TextView txt_endTime;
	
	float rt_effc;
	float rt_mood;
	String type;
    String fatherType[];
    String subType[]; 
	int startHour;
	int startMin;
	int endHour;
	int endMin;
	DatabaseUtil dbUtil;

	/*************************************** 
	 * ��ø�����String[]
	 **************************************/
	private String[] getFatherTypeNameInStringArray(){
        //����α�
		Cursor cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id, type_name FROM tb_prim_types", null);
        //�����α�
        if(cursor.moveToNext()){
        	Log.i("iRcd_FatherTypeNameInStringArray","PrimTypesActivity_fetchAllData: fetch specific data works!");
        }else{
        	Log.i("iRcd_FatherTypeNameInStringArray","PrimTypesActivity_fetchAllData: fetch specific data failed!");        	
        }        
		ArrayList<String> arrayFatherTypes = new ArrayList<String>();
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			Log.i("iRcd_FatherTypeNameInStringArray",cursor.getString(1));
			arrayFatherTypes.add(cursor.getString(1));
		}
		String[] retVal = (String[])arrayFatherTypes.toArray(new String[arrayFatherTypes.size()]);
		Log.i("iRcd_getFatherTypeNameInStringArray","after change type.");
		Log.i("iRcd_getFatherTypeNameInStringArray",retVal[1]);
		return retVal;
	}
	
	/*************************************** 
	 * ���������String[]
	 **************************************/
	private String[] getSubTypeNameInStringArray(String fatherTypeName){
        //����α�
        Cursor cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id,  type_name FROM tb_sub_types where " + DatabaseUtil.KEY_TYPE_BELONGTO + "=?;",new String[] {fatherTypeName});
        //�����α�
        if(cursor.moveToNext()){
        	Log.i("iRcd_getSubTypeNameInStringArray","SubTypesActivity_fetchAllData: fetch specific data works!");
        	Log.i("iRcd_getSubTypeNameInStringArray","first content: " + cursor.getString(1));
        }else{
        	Log.i("iRcd_getSubTypeNameInStringArray","SubTypesActivity_fetchAllData: fetch specific data failed!");        	
        }     
		ArrayList<String> arrayFatherTypes = new ArrayList<String>();
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			Log.i("iRcd_SubTypeNameInStringArray",cursor.getString(1));
			arrayFatherTypes.add(cursor.getString(1));
		}
		String[] retVal = (String[])arrayFatherTypes.toArray(new String[arrayFatherTypes.size()]);
		Log.i("iRcd_getSubTypeNameInStringArray","after change type.");
		Log.i("iRcd_getSubTypeNameInStringArray",retVal[0]);
		return retVal;
	}
	
	private void setAllBtnUnpushed(){
		btn_excited.setBackgroundResource(R.drawable.excited);
		btn_happy.setBackgroundResource(R.drawable.happy);
		btn_ok.setBackgroundResource(R.drawable.ok);
		btn_sad.setBackgroundResource(R.drawable.sad);
		btn_angry.setBackgroundResource(R.drawable.angry);
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("iRcd","iRecord: before setContentView.");
		setContentView(R.layout.activity_iwanttorecord);

		Log.v("iRcd","iRecord: before find RatingBar.");
		rtBar_effc = (RatingBar)findViewById(R.id.act_iRcrd_rtBar_effc);
		btn_save = (Button)findViewById(R.id.act_iRcd_btn_save);
		
		/***************************************
         * ��ȡ���ݿ������Ԫ
         **************************************/
        dbUtil = new DatabaseUtil(this);
        Log.i("PrimTypesActivity","Open DB");
        dbUtil.open();
		
		/***************************************
		 * timePicker��ֹʱ������
		 **************************************/
		//get current time
		final Calendar c = Calendar.getInstance();
		startHour = c.get(Calendar.HOUR_OF_DAY);
		startMin = c.get(Calendar.MINUTE);
		endHour = startHour;
		endMin = startMin;
		// init the time;
		txt_startTime = (TextView)findViewById(R.id.act_iRcd_txt_startTime);
		txt_startTime.setText(Integer.toString(startHour) + ":"+Integer.toString(startMin));
		txt_endTime = (TextView)findViewById(R.id.act_iRcd_txt_endTime);
		txt_endTime.setText(Integer.toString(endHour) + ":"+Integer.toString(endMin));
		
		// set start time;
		btn_setStart = (Button)findViewById(R.id.act_iRcd_btn_setStart);
		btn_setStart.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View view){
				new TimePickerDialog(IWantToRecordActivity.this,new OnTimeSetListener(){
					public void onTimeSet(TimePicker view,int hour,int minute)
					{
						//set the startTime
						startHour = hour;
						startMin = minute;
						txt_startTime.setText(Integer.toString(startHour) + ":"+Integer.toString(startMin));
					}
				}, startHour, startMin, true).show();
			}
		});
		
		//set end time
		btn_setEnd = (Button)findViewById(R.id.act_iRcd_btn_setEnd);
		btn_setEnd.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View view){
				new TimePickerDialog(IWantToRecordActivity.this,new OnTimeSetListener(){
					public void onTimeSet(TimePicker view,int hour,int minute)
					{
						//set the endTime
						endHour = hour;
						endMin = minute;
						txt_endTime.setText(Integer.toString(endHour) + ":"+Integer.toString(endMin));
					}
				}, endHour, endMin, true).show();
			}
		});
		

		/*************************************** 
		 * ��ø�����String[]
		 **************************************/
		Log.i("iRcd","before getFatherTypeNameInStringArray()");
        fatherType = getFatherTypeNameInStringArray();
		
		/***************************************
		 * �������
		 **************************************/
		Log.v("iRcd", "iShouldRcd: before get buttons about moods");
		btn_excited = (Button)findViewById(R.id.act_iRcd_btn_excited);
		btn_happy = (Button)findViewById(R.id.act_iRcd_btn_happy);
		btn_ok = (Button)findViewById(R.id.act_iRcd_btn_ok);
		btn_sad = (Button)findViewById(R.id.act_iRcd_btn_sad);
		btn_angry = (Button)findViewById(R.id.act_iRcd_btn_angry);

		//excited
		btn_excited.setOnClickListener(new Button.OnClickListener(){
			//��ť����ʱ���ı�����������ı䰴ť����
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_excited.setBackgroundResource(R.drawable.excited_pushed);	
				rt_mood = 1;
			}			
		});
		//happy
		btn_happy.setOnClickListener(new Button.OnClickListener(){
			//��ť����ʱ���ı�����������ı䰴ť����
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_happy.setBackgroundResource(R.drawable.happy_pushed);				
				rt_mood = 2;	
			}			
		});
		//ok
		btn_ok.setOnClickListener(new Button.OnClickListener(){
			//��ť����ʱ���ı�����������ı䰴ť����
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_ok.setBackgroundResource(R.drawable.ok_pushed);		
				rt_mood = 3;			
			}			
		});
		//sad
		btn_sad.setOnClickListener(new Button.OnClickListener(){
			//��ť����ʱ���ı�����������ı䰴ť����
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_sad.setBackgroundResource(R.drawable.sad_pushed);		
				rt_mood = 4;			
			}			
		});
		//angry
		btn_angry.setOnClickListener(new Button.OnClickListener(){
			//��ť����ʱ���ı�����������ı䰴ť����
			public void onClick(View v) {
				setAllBtnUnpushed();
				btn_angry.setBackgroundResource(R.drawable.angry_pushed);			
				rt_mood = 5;		
			}			
		});
		
		
		/***************************************
		 * ������ѡ�������ش���
		 **************************************/
		Log.v("iRcd","iRecord: before findWheelView.");
		whl_fatherType = (WheelView) findViewById(R.id.act_iRcd_wheel_fatherType);
        whl_fatherType.setAdapter(new ArrayWheelAdapter<String>(fatherType));
        whl_fatherType.setVisibleItems(5);
        whl_fatherType.setCurrentItem(1);
        
        whl_subType = (WheelView) findViewById(R.id.act_iRcd_wheel_subType);
        whl_subType.setVisibleItems(5);
		subType = getSubTypeNameInStringArray(fatherType[1]);
		whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType));
		whl_subType.setCurrentItem(1);

        whl_fatherType.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				subType = getSubTypeNameInStringArray(fatherType[newValue]);
				whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType));
				whl_subType.setCurrentItem(1);
			}
		});
        
		
        /********************************************************
		 * �����Ǳ��水ť
		 * ʵ�ʲ�ִ�б��棬���湦����onPause()���������
		 *******************************************************/
		btn_save.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View arg0) {		
				// �˳�
				IWantToRecordActivity.this.finish();
			}
		});
	}
	
	/**********************************************
	 * �ڴ�������ݵı��湤��
	 *********************************************/
	public void onPause(){
		super.onPause();
			// ��ȡ��¼����
			rt_effc = rtBar_effc.getRating();
			//rt_mood = rtBar_mood.getRating();
			//������ֹʱ�� startHour, startMin, endHour, endMIn ҲҪ���棡����
			
			//���
			type = fatherType[whl_fatherType.getCurrentItem()] + "_"
					+ subType[whl_subType.getCurrentItem()];
			Toast.makeText(getApplicationContext(), type, 
					Toast.LENGTH_SHORT).show();	
		/************************************
		 * ?????????????????????????????????
		 * �������ݿ⣺��ֹʱ�䣬Ч�ʣ����飬���
		 *??????????????????????????????????*/
	}
}

