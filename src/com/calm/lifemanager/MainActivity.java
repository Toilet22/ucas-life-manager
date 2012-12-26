package com.calm.lifemanager;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Test DatabaseUtil
        Log.i("DB","new DB");
        DatabaseUtil dbUtil = new DatabaseUtil(this);
        Log.i("DB","Open DB");
        dbUtil.open();
        Log.i("Table","Create a user");
        dbUtil.createUser("lilihang", "password", (int)System.currentTimeMillis());
        
        Log.i("Table","Create a user profile");
        dbUtil.createUserProfile("Lihang", "hustcalm", "licalmer@gmail.com", 
        		1, 21, 1, 
        		(int)System.currentTimeMillis(), 
        		(int)System.currentTimeMillis(),
        		(int)System.currentTimeMillis());
        
        Log.i("Table","Create a user settings");
        dbUtil.createUserSettings("Lihang", 1, 1, 1, 
        		(int)System.currentTimeMillis(),
        		(int)System.currentTimeMillis(), 
        		(int)System.currentTimeMillis());
        
        Log.i("Query","fetch all users");
        Cursor cursor = dbUtil.fetchAllUsers();
        if(cursor != null) {
        	while(cursor.moveToNext()) {
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}
        }
        
        Log.i("Query","fetch user with username and password");
        String username = "lilihang";
        String[] selectColumns = {DatabaseUtil.KEY_USERNAME, DatabaseUtil.KEY_PASSWORD};
        Cursor newCursor = dbUtil.fetchUser(username, selectColumns);
        if(newCursor != null) {
        	Log.i("UTrace","fetch specific data works!");
        	do{
        		Log.i("User", " User Name: " + newCursor.getString(0) 
        				+ " Password " + newCursor.getString(1));
        	}while(newCursor.moveToNext());
        }
        
        dbUtil.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
