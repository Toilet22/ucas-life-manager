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
    String fatherType[] = new String[] {"ѧϰ", "����", "�罻", "����", "˼��", "�˶�"};
    String subType[][] = new String[][] {
    		new String[] {"�Ͽ�", "Ԥϰ", "����ҵ", "��ϰ", "��רҵ֪ʶ", "����"},
    		new String[] {"�ϰ�", "д����", "�����ĵ�", "��ϵ�ͻ�", "�ܽṤ��", "�滮����", "���ȶ�", "����"},
    		new String[] {"���Ѿ۲�", "�绰��ϵ", "���罻��", "�������", "����"},
    		new String[] {"����Ӱ", "����Ϸ", "������", "����", "�������", "���", "������", "����"},
    		new String[] {"����Ը��", "���ڹ滮", "����Ŀ��", "һ�����ɶ", "��������", "���է�֣�", "��������", "���е���/��", "����"},
    		new String[] {"ɢ��", "�ܲ�", "�����˶�", "�ﾶ��Ŀ", "�����˶�", "����"}
    		};
	int startHour;
	int startMin;
	int endHour;
	int endMin;

	private void setAllBtnUnpushed(){
		btn_excited.setBackgroundResource(R.drawable.excited);
		btn_happy.setBackgroundResource(R.drawable.happy);
		btn_ok.setBackgroundResource(R.drawable.ok);
		btn_sad.setBackgroundResource(R.drawable.sad);
		btn_angry.setBackgroundResource(R.drawable.angry);
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet","iRecord: before setContentView.");
		setContentView(R.layout.activity_iwanttorecord);

		Log.v("Toilet","iRecord: before find RatingBar.");
		rtBar_effc = (RatingBar)findViewById(R.id.act_iRcrd_rtBar_effc);
		//rtBar_mood = (RatingBar)findViewById(R.id.act_iRcrd_rtBar_mood);
		btn_save = (Button)findViewById(R.id.act_iRcd_btn_save);
		
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
		 * �������
		 **************************************/
		Log.v("Toilet", "iShouldRcd: before get buttons about moods");
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
		Log.v("Toilet","iRecord: before findWheelView.");
		whl_fatherType = (WheelView) findViewById(R.id.act_iRcd_wheel_fatherType);
        whl_fatherType.setAdapter(new ArrayWheelAdapter<String>(fatherType));
        whl_fatherType.setVisibleItems(5);
        whl_fatherType.setCurrentItem(1);

        
        whl_subType = (WheelView) findViewById(R.id.act_iRcd_wheel_subType);
        whl_subType.setVisibleItems(5);
		whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType[1]));
		whl_subType.setCurrentItem(1);

        whl_fatherType.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				whl_subType.setAdapter(new ArrayWheelAdapter<String>(subType[newValue]));
				whl_subType.setCurrentItem(subType[newValue].length / 2);
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
					+ subType[whl_fatherType.getCurrentItem()][whl_subType.getCurrentItem()];
			Toast.makeText(getApplicationContext(), type, 
					Toast.LENGTH_SHORT).show();	
		/************************************
		 * ?????????????????????????????????
		 * �������ݿ⣺��ֹʱ�䣬Ч�ʣ����飬���
		 *??????????????????????????????????*/
	}
}

