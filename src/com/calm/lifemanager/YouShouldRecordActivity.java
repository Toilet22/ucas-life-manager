package com.calm.lifemanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.calm.scrollwidget.ArrayWheelAdapter;
import com.calm.scrollwidget.OnWheelChangedListener;
import com.calm.scrollwidget.WheelView;

public class YouShouldRecordActivity extends Activity {
	RatingBar rtBar_effc;
	//RatingBar rtBar_mood;
	Button btn_excited;
	Button btn_happy;
	Button btn_ok;
	Button btn_sad;
	Button btn_angry;
	Button btn_save;
	WheelView whl_fatherType;
	WheelView whl_subType;
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
	//preferences��¼
	SharedPreferences sharedPref;
	Boolean isRingOn;
	Boolean isVibrationOn;
	int interval;
	
	private void setAllBtnUnpushed(){
		btn_excited.setBackgroundResource(R.drawable.excited);
		btn_happy.setBackgroundResource(R.drawable.happy);
		btn_ok.setBackgroundResource(R.drawable.ok);
		btn_sad.setBackgroundResource(R.drawable.sad);
		btn_angry.setBackgroundResource(R.drawable.angry);
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("Toilet","YouRecord: before setContentView.");
		setContentView(R.layout.activity_youshouldrecord);
		
		/*
		 * ��ȡpreferences
		 */
		sharedPref = getSharedPreferences(getString(R.string.curr_usr_name), 
				Context.MODE_PRIVATE);
		isRingOn = sharedPref.getBoolean("isRingOn", true);
		isVibrationOn = sharedPref.getBoolean("isVibrationOn", true);
		interval = sharedPref.getInt("interval", 30);

		/***************************************
		 * ��������
		 **************************************/
		if(isVibrationOn){
			Vibrator vib = (Vibrator) YouShouldRecordActivity.this.getSystemService(Service.VIBRATOR_SERVICE); 
	        long pattern[] = {20,200,20,200,20,200,100,200,20,200,20,200};
			vib.vibrate(pattern, -1); 
		}
		
		
		/***************************************
		 * Ч��
		 **************************************/
		Log.v("Toilet","YouRecord: before find RatingBar.");
		rtBar_effc = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_effc);
		//rtBar_mood = (RatingBar)findViewById(R.id.act_youRcrd_rtBar_mood);
		btn_save = (Button)findViewById(R.id.act_youRcd_btn_save);
		
		/***************************************
		 * �������
		 **************************************/
		Log.v("Toilet", "YouShouldRcd: before get buttons about moods");
		btn_excited = (Button)findViewById(R.id.act_youRcd_btn_excited);
		btn_happy = (Button)findViewById(R.id.act_youRcd_btn_happy);
		btn_ok = (Button)findViewById(R.id.act_youRcd_btn_ok);
		btn_sad = (Button)findViewById(R.id.act_youRcd_btn_sad);
		btn_angry = (Button)findViewById(R.id.act_youRcd_btn_angry);

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
		Log.v("Toilet","YouRecord: before findWheelView.");
		whl_fatherType = (WheelView) findViewById(R.id.act_youRcd_wheel_fatherType);
        whl_fatherType.setAdapter(new ArrayWheelAdapter<String>(fatherType));
        whl_fatherType.setVisibleItems(5);
        whl_fatherType.setCurrentItem(1);

        
        whl_subType = (WheelView) findViewById(R.id.act_youRcd_wheel_subType);
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
				YouShouldRecordActivity.this.finish();
			}
		});
		
		
	}
	
	
	/*
	 * �ڴ�������ݵı��湤��
	 */
	public void onPause(){
		super.onPause();
		
	}
	
	/*
	 * �ڴ˿�����һ�μ�¼�ļ�ʱ
	 */
	public void onStop(){
		super.onStop();

		// ��ȡ��¼����
		rt_effc = rtBar_effc.getRating();
		//rt_mood = rtBar_mood.getRating();
		type = fatherType[whl_fatherType.getCurrentItem()] + "_"
				+ subType[whl_fatherType.getCurrentItem()][whl_subType.getCurrentItem()];
		Toast.makeText(getApplicationContext(), type, 
				Toast.LENGTH_SHORT).show();	
		
		/************************************
		 * ?????????????????????????????????
		 * �������ݿ⣺ Ч�ʣ����飬���
		 *??????????????????????????????????
		 ***********************************/
		
		
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
		am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + interval*3000, sender); 
	}
	
}
