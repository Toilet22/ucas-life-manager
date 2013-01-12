package com.calm.lifemanager;


import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.calm.scrollwidget.ArrayWheelAdapter;
import com.calm.scrollwidget.OnWheelChangedListener;
import com.calm.scrollwidget.WheelView;

public class IWantToRecordActivity extends Activity {
	RatingBar rtBar_effc;
	RatingBar rtBar_mood;
	Button btn_save;
	Button btn_setStart;
	Button btn_setEnd;
	TextView txt_startTime;
	TextView txt_endTime;
	
	float rt_effc;
	float rt_mood;
	int startHour;
	int startMin;
	int endHour;
	int endMin;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet","YouRecord: before setContentView.");
		setContentView(R.layout.activity_iwanttorecord);

		Log.v("Toilet","YouRecord: before find RatingBar.");
		rtBar_effc = (RatingBar)findViewById(R.id.act_iRcrd_rtBar_effc);
		rtBar_mood = (RatingBar)findViewById(R.id.act_iRcrd_rtBar_mood);
		btn_save = (Button)findViewById(R.id.act_iRcd_btn_save);
		
		/*
		 * timePicker
		 */
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
		
		/*
		 * ������ѡ�������ش���
		 */
		Log.v("Toilet","YouRecord: before findWheelView.");
		WheelView whl_fatherType = (WheelView) findViewById(R.id.act_iRcd_wheel_fatherType);
        String fatherType[] = new String[] {"ѧϰ", "����", "�罻", "����", "˼��", "�˶�"};
        whl_fatherType.setAdapter(new ArrayWheelAdapter<String>(fatherType));
        whl_fatherType.setVisibleItems(5);
        whl_fatherType.setCurrentItem(1);

        final String subType[][] = new String[][] {
        		new String[] {"�Ͽ�", "Ԥϰ", "����ҵ", "��ϰ", "��רҵ֪ʶ", "����"},
        		new String[] {"�ϰ�", "д����", "�����ĵ�", "��ϵ�ͻ�", "�ܽṤ��", "�滮����", "���ȶ�", "����"},
        		new String[] {"���Ѿ۲�", "�绰��ϵ", "���罻��", "�������", "����"},
        		new String[] {"����Ӱ", "����Ϸ", "������", "����", "�������", "���", "������", "����"},
        		new String[] {"����Ը��", "���ڹ滮", "����Ŀ��", "һ�����ɶ", "��������", "���է�֣�", "��������", "���е���/��", "����"},
        		new String[] {"ɢ��", "�ܲ�", "�����˶�", "�ﾶ��Ŀ", "�����˶�", "����"}
        		};
        
        final WheelView whl_subType = (WheelView) findViewById(R.id.act_iRcd_wheel_subType);
        whl_subType.setVisibleItems(5);
		whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType[1]));
		whl_subType.setCurrentItem(1);

        whl_fatherType.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType[newValue]));
				whl_subType.setCurrentItem(subType[newValue].length / 2);
			}
		});
        
		
		/*
		 * �����Ǳ����¼
		 */
		btn_save.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View arg0) {
				// ��ȡ��¼����
				rt_effc = rtBar_effc.getRating();
				rt_mood = rtBar_mood.getRating();
				
				// �˳�
				IWantToRecordActivity.this.finish();
			}
		});
		
		
	}
	
}

