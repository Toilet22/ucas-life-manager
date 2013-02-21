package com.calm.lifemanager;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SubTypesActivity extends Activity {
	DatabaseUtil dbUtil;
	ListView lstvw_subTypes;
	String fatherTypeName;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);	

        Log.i("subTypesActivity", "before setContentView");
        setContentView(R.layout.activity_sub_types);
        
        //��ȡ����������
        Bundle mBundle = SubTypesActivity.this.getIntent().getExtras();
        fatherTypeName = mBundle.getString("PrimTypeName");
        Log.i("fatherTypeName",fatherTypeName);
        
        //��ȡListViewʵ��
        Log.i("subTypesActivity","Get ListView");
        lstvw_subTypes = (ListView)findViewById(R.id.act_subTypes_lstvw_subTypes);
        
        //��ȡu���ݿ������Ԫ
        dbUtil = new DatabaseUtil(this);
        Log.i("subTypesActivity","Open DB");
        dbUtil.open();

        //���ָ�����ݵ��α�
        Cursor cursor;
        //String[] selectCol = {DatabaseUtil.KEY_TYPE_NAME, DatabaseUtil.KEY_TYPE_ICON};
        //cursor = dbUtil.fetchAllData(DatabaseUtil.sub_TYPES, selectCol);
        //
        cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id,  type_name FROM tb_sub_types where KEY_TYPE_BELONGTO=?" + fatherTypeName +";", null);
        
        if(cursor.moveToNext()){
        	Log.i("Toilet","subTypesActivity_fetchAllData: fetch specific data works!");
        }else{
        	Log.i("Toilet","subTypesActivity_fetchAllData: fetch specific data failed!");        	
        }        
         
        //��ȡSimpleCursorAdatper��ʵ��
        String[] fromCol = {DatabaseUtil.KEY_TYPE_NAME};
        int[] toView = {R.id.layout_primType_name};
        @SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
        		R.layout.layout_sub_type, cursor, fromCol, toView);
        //��adapter
        Log.i("subTypesActivity", "before setAdatper");
        lstvw_subTypes.setAdapter(adapter);
        
        lstvw_subTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				
			}
        	
		});
        

	}

}
