package com.calm.lifemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class bootStarter extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		Intent bootIntent = new Intent(context, MainActivity.class);
		bootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(bootIntent);
	}
	
}
