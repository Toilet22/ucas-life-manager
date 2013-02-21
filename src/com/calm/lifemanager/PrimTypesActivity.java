package com.calm.lifemanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PrimTypesActivity extends Activity {
	DatabaseUtil dbUtil;
	ListView lstvw_primTypes;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);	

        Log.i("PrimTypesActivity", "before setContentView");
        setContentView(R.layout.activity_prim_types);
        
        //��ȡListViewʵ��
        Log.i("PrimTypesActivity","Get ListView");
        lstvw_primTypes = (ListView)findViewById(R.id.act_primTypes_lstvw_primTypes);
        
        //��ȡ���ݿ������Ԫ
        dbUtil = new DatabaseUtil(this);
        Log.i("PrimTypesActivity","Open DB");
        dbUtil.open();

        //���ָ�����ݵ��α�
        final Cursor cursor;
        //String[] selectCol = {DatabaseUtil.KEY_TYPE_NAME, DatabaseUtil.KEY_TYPE_ICON};
        //cursor = dbUtil.fetchAllData(DatabaseUtil.PRIM_TYPES, selectCol);
        cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id, type_name FROM tb_prim_types", null);
        
        if(cursor.moveToNext()){
        	Log.i("Toilet","PrimTypesActivity_fetchAllData: fetch specific data works!");
        }else{
        	Log.i("Toilet","PrimTypesActivity_fetchAllData: fetch specific data failed!");        	
        }        
         
        //��ȡSimpleCursorAdatper��ʵ��
        String[] fromCol = {DatabaseUtil.KEY_TYPE_NAME};
        int[] toView = {R.id.layout_primType_name};
        @SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
        		R.layout.layout_prim_type, cursor, fromCol, toView);
        //��adapter
        Log.i("PrimTypesActivity", "before setAdatper");
        lstvw_primTypes.setAdapter(adapter);
        
        lstvw_primTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// ��ȡ������������
				cursor.moveToPosition(position);
				String typeName = cursor.getString(1);
				Log.i("cursor getString", typeName);
				
				//�����μ�����Activity
				Intent intent = new Intent(PrimTypesActivity.this, SubTypesActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putString("PrimTypeName", typeName);
				intent.putExtras(mBundle);
				startActivity(intent);
			}
        	
		});
        

	}

}
