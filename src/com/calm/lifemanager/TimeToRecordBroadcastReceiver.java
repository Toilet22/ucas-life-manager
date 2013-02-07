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
		Bundle bundleRet = intent.getExtras();
        Log.v("Toilet", "TimeToRecordBroadCastReceiver: test Bundle: the Hour is "+ Integer.toString(bundleRet.getInt("Hour"))+".");
	    bundleRet.putString("STR_CALLER", ""); 
	    intnt_doRecord.putExtras(bundleRet); 
	    intnt_doRecord.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		Log.v("Toilet","TTRCRD receiver: before startActivity");
		context.startActivity(intnt_doRecord);	
		
	}

}
