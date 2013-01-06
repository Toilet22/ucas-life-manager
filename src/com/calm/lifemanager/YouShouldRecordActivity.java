package com.calm.lifemanager;

import java.util.Calendar;

import com.calm.lifemanager.R;
import com.calm.scrollwidget.*;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class YouShouldRecordActivity extends Activity {
	RatingBar rtBar_effc;
	RatingBar rtBar_mood;
	Button btn_save;
	float rt_effc;
	float rt_mood;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet","YouRecord: before setContentView.");
		setContentView(R.layout.activity_youshouldrecord);

		Log.v("Toilet","YouRecord: before find RatingBar.");
		rtBar_effc = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_effc);
		rtBar_mood = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_mood);
		btn_save = (Button)findViewById(R.id.act_youRcd_btn_save);
		
		/*
		 * ������ѡ�������ش���
		 */
		Log.v("Toilet","YouRecord: before findWheelView.");
		WheelView whl_fatherType = (WheelView) findViewById(R.id.act_youRcd_wheel_fatherType);
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
        
        final WheelView whl_subType = (WheelView) findViewById(R.id.act_youRcd_wheel_subType);
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
				/************************************
				 * ?????????????????????????????????
				 * �������ݿ�
				 *??????????????????????????????????*/
				
				/********************************************
				//������һ�μ���
				 ********************************************/
				//ָ����ʱ��¼��Activity
				Intent intent = new Intent(YouShouldRecordActivity.this, TimeToRecordBroadcastReceiver.class);
				//ָ��PendingIntent
				PendingIntent sender = PendingIntent.getBroadcast(YouShouldRecordActivity.this, 0, intent, 0);
				//���AlarmManager����
				AlarmManager am; 
	            am = (AlarmManager)getSystemService(ALARM_SERVICE);
	            //���ϵͳʱ��
	            Calendar c=Calendar.getInstance();
	            c.setTimeInMillis(System.currentTimeMillis()); 
	            //������ʱ����
	            //???????????interval?????????????????
	            int interval = 3000;
				am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval, sender); 
				// �˳�
				YouShouldRecordActivity.this.finish();
			}
		});
		
		
	}
	
}
