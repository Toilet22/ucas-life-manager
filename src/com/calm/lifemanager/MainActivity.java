package com.calm.lifemanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button btn_settings;
	Button btn_login;
	Button btn_yesterday;
	Button btn_today;
	Button btn_tomorrow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*************************************************************************
         * 数据库代码：李立杭
         *************************************************************************/
  /*      // Test DatabaseUtil
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
 */       
        /************************************************************************
         * UI代码：苏轶伦
         ***********************************************************************/
        //声明按钮
        Log.e("Toilet", "start init btns");
        btn_login = (Button)findViewById(R.id.button5_login);
        btn_yesterday = (Button)findViewById(R.id.button1_yester);
        btn_tomorrow = (Button)findViewById(R.id.button3_tomorrow);
        
        //进入设置界面
        Log.e("Toilet", "before push btn_settings");
        btn_settings = (Button)findViewById(R.id.button4_settings);
        btn_settings.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
		        Log.v("Toilet", "push btn_settings");
				Intent itnt_settings = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(itnt_settings);
				//MainActivity.this.finish();
			}
        });
        
        //登录/切换用户/退出
        
        //进入过去的我
        
        //进入现在的我
        btn_today = (Button)findViewById(R.id.button2_today);
        btn_today.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// back to main
				Intent iMain = new Intent(MainActivity.this, CurrentActivity.class);
				startActivity(iMain);
			}
		});
        
        //进入将来的我
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
