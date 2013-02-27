package com.calm.lifemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TimeToRecordBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.v("Toilet","TTRCRD receiver: before intent.");
		Intent intnt_doRecord = new Intent(context, YouShouldRecordActivity.class);
		//Intent intnt_doRecord = new Intent(context, SplashActivity.class);
		
		Bundle bundle = intent.getExtras();
        Log.v("Toilet", "TimeToRcd: test Bundle: the currTimeInMillis is "+ Long.toString(bundle.getLong("StartTimeInMillis"))+".");
        Log.v("Toilet", "TimeToRecordBroadCastReceiver: test Bundle: the Hour is "+ Integer.toString(bundle.getInt("Hour"))+".");
	    bundle.putString("STR_CALLER", ""); 
	    intnt_doRecord.putExtras(bundle); 
	    //intnt_doRecord.putExtra("StartTimeInMillis", intent.getLongExtra("StartTimeInMillis", 0));
	    intnt_doRecord.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		Log.v("Toilet","TTRCRD receiver: before startActivity");
		context.startActivity(intnt_doRecord);	
		
	}

}
