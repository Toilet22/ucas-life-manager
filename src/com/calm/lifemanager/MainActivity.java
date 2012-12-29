package com.calm.lifemanager;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.ContentValues;
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
        Log.i("Insert","Create a user with specified User interface");
        dbUtil.createUser("lilihang", "password", System.currentTimeMillis());
        
        // Test Insert
        Log.i("Insert","Create a user with generic interface");
        ContentValues newUserValues = new ContentValues();
        newUserValues.put(DatabaseUtil.KEY_USERNAME, "hustcalm");
        newUserValues.put(DatabaseUtil.KEY_PASSWORD, "password");
        newUserValues.put(DatabaseUtil.KEY_CTIME, System.currentTimeMillis());
        dbUtil.insertData(DatabaseUtil.USER, newUserValues);
        
        Log.i("Query","fetch all users with specified User interface");
        Cursor cursor = dbUtil.fetchAllUsers();
        if(cursor != null) {
        	while(cursor.moveToNext()) {
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}
        }
        
        Log.i("Query","fetch all users with generic interface");
        String[] selectColumns = {DatabaseUtil.KEY_USERNAME, DatabaseUtil.KEY_PASSWORD, DatabaseUtil.KEY_CTIME};
        cursor = dbUtil.fetchAllData(DatabaseUtil.USER, selectColumns);
        if(cursor != null) {
        	while(cursor.moveToNext()) {
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}
        }
        
        Log.i("Query","fetch user with specified User interface");
        String username = "hustcalm";
        cursor = dbUtil.fetchUser(username, selectColumns);
        if(cursor != null) {
        	Log.i("UTrace","fetch specific data works!");
        	do{
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}while(cursor.moveToNext());
        }
        
        Log.i("Query","fetch user with generic interface");
        cursor = dbUtil.fetchData(DatabaseUtil.USER, DatabaseUtil.KEY_USERNAME, username, selectColumns);
        if(cursor != null) {
        	Log.i("UTrace","fetch specific data works!");
        	do{
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}while(cursor.moveToNext());
        }
        
        // Test Update
        Log.i("Update","Update user with specified User interface");
        ContentValues updateValues = new ContentValues();
        updateValues.put(DatabaseUtil.KEY_PASSWORD, "newpassword");
        updateValues.put(DatabaseUtil.KEY_CTIME, System.currentTimeMillis());
        dbUtil.updateUser(username, updateValues);
        
        Log.i("Update","Update user with generic interface");
        String newusername = "lihang";
        updateValues.put(DatabaseUtil.KEY_PASSWORD, "newpassword");
        updateValues.put(DatabaseUtil.KEY_CTIME, System.currentTimeMillis());
        dbUtil.updateUser(newusername, updateValues);
        
        Log.i("Query","fetch all users with specified User interface");
        cursor = dbUtil.fetchAllUsers();
        if(cursor != null) {
        	while(cursor.moveToNext()) {
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}
        }
        
        Log.i("Query","fetch all users with generic interface");
        cursor = dbUtil.fetchAllData(DatabaseUtil.USER, selectColumns);
        if(cursor != null) {
        	while(cursor.moveToNext()) {
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}
        }
        
        Log.i("Query","fetch user with specified User interface");
        cursor = dbUtil.fetchUser(username, selectColumns);
        if(cursor != null) {
        	Log.i("UTrace","fetch specific data works!");
        	do{
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}while(cursor.moveToNext());
        }
        
        Log.i("Query","fetch user with generic interface");
        cursor = dbUtil.fetchData(DatabaseUtil.USER, DatabaseUtil.KEY_USERNAME, username, selectColumns);
        if(cursor != null) {
        	Log.i("UTrace","fetch specific data works!");
        	do{
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}while(cursor.moveToNext());
        }
        
        // Test Delete
        Log.i("Delete","delete user with specified User interface");
        String deleteusername = "Lihang";
        dbUtil.deleteUser(deleteusername);
        
        Log.i("Delete","delete user with generic interface");
        String newdeleteusername = "hustcalm";
        dbUtil.deleteData(DatabaseUtil.USER, DatabaseUtil.KEY_USERNAME, newdeleteusername);
        
        Log.i("Query","fetch all users with specified User interface");
        cursor = dbUtil.fetchAllUsers();
        if(cursor != null) {
        	while(cursor.moveToNext()) {
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}
        }
        
        Log.i("Query","fetch all users with generic interface");
        cursor = dbUtil.fetchAllData(DatabaseUtil.USER, selectColumns);
        if(cursor != null) {
        	while(cursor.moveToNext()) {
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}
        }
        
        Log.i("Query","fetch user with specified User interface");
        cursor = dbUtil.fetchUser(username, selectColumns);
        if(cursor != null) {
        	Log.i("UTrace","fetch specific data works!");
        	do{
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}while(cursor.moveToNext());
        }
        
        Log.i("Query","fetch user with generic interface");
        cursor = dbUtil.fetchData(DatabaseUtil.USER, DatabaseUtil.KEY_USERNAME, username, selectColumns);
        if(cursor != null) {
        	Log.i("UTrace","fetch specific data works!");
        	do{
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}while(cursor.moveToNext());
        }
        
        dbUtil.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
