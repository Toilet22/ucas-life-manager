package com.calm.lifemanager;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ListView;

public class PrimTypesActivity extends Activity {
	DatabaseUtil dbUtil;
	ListView lstvw_primTypes;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);	
        
        //获取ListView实例
        Log.i("PrimTypesActivity","Get ListView");
        lstvw_primTypes = new ListView(this);
        //获取数据库操作单元
        dbUtil = new DatabaseUtil(this);
        Log.i("PrimTypesActivity","Open DB");
        dbUtil.open();
        
        Log.i("Insert","Create a user with specified User interface");
        Cursor cursor;
        String[] selectCol = {DatabaseUtil.KEY_TYPE_NAME, DatabaseUtil.KEY_TYPE_ICON};
        //获得指向数据的游标
        //cursor = dbUtil.fetchAllData(DatabaseUtil.PRIM_TYPES, selectCol);
        cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id, type_name FROM tb_prim_types", null);
        
        if(cursor != null) {
        	while(cursor.moveToNext()) {
        		Log.i("Toilet","PrimTypesActivity_fetchAllData: fetch specific data works!");
        		Log.i("PrimTypes", " Type Name: " + cursor.getString(0)); 
        		Log.i("PrimTypes", " Type Icon: " + cursor.getString(1));
        	}  
        	cursor.moveToFirst();
        }else{
        	Log.i("Toilet","PrimTypesActivity_fetchAllData: fetch specific data failed!");        	
        }        
         
        /*
        String[] selectColumns = {DatabaseUtil.KEY_USERNAME, DatabaseUtil.KEY_PASSWORD, DatabaseUtil.KEY_CTIME};
        cursor = dbUtil.fetchAllData(DatabaseUtil.USER, selectColumns);
        if(cursor != null && cursor.getCount() > 0) {
        	Log.i("Database","cursor is not none.");
        	
        	while(cursor.moveToNext()) {
        		Log.i("User", " User Name: " + cursor.getString(0) 
        				+ " Password " + cursor.getString(1)
        				+ " Create Time " + cursor.getString(2));
        	}
        }
        */
        
        
        //获取SimpleCursorAdatper的实例
        String[] fromCol = {DatabaseUtil.KEY_TYPE_NAME};
        int[] toView = {R.id.layout_primType_name};
        @SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
        		R.layout.layout_prim_type, cursor, fromCol, toView);

        Log.i("PrimTypesActivity", "after get SimpleCursorAdapter.");
        
        Log.i("PrimTypesActivity", "before setAdatper");
        lstvw_primTypes.setAdapter(adapter);
        

        Log.i("PrimTypesActivity", "before setContentView");
        setContentView(R.layout.activity_prim_types);
	}

}
