package com.calm.lifemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SubTypesActivity extends Activity {
	DatabaseUtil dbUtil;
	ListView lstvw_subTypes;
	String fatherTypeName;
	Button btn_newType;
	Button btn_back;
	String newTypeName;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);	

        Log.i("subTypesActivity", "before setContentView");
        setContentView(R.layout.activity_sub_types);
        
        //获取父类别的名称
        Bundle mBundle = SubTypesActivity.this.getIntent().getExtras();
        fatherTypeName = mBundle.getString("PrimTypeName");
        Log.i("fatherTypeName",fatherTypeName);
        
        //获取ListView实例
        Log.i("subTypesActivity","Get ListView");
        lstvw_subTypes = (ListView)findViewById(R.id.act_subTypes_lstvw_subTypes);
        
        //获取u数据库操作单元
        dbUtil = new DatabaseUtil(this);
        Log.i("subTypesActivity","Open DB");
        dbUtil.open();

        //获得指向数据的游标
        Cursor cursor;
        //String[] selectCol = {DatabaseUtil.KEY_TYPE_NAME, DatabaseUtil.KEY_TYPE_ICON};
        //cursor = dbUtil.fetchAllData(DatabaseUtil.sub_TYPES, selectCol);
        //
        cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id,  type_name FROM tb_sub_types where " + DatabaseUtil.KEY_TYPE_BELONGTO + "=?;",new String[] {fatherTypeName});
        
        if(cursor.moveToNext()){
        	Log.i("Toilet","subTypesActivity_fetchAllData: fetch specific data works!");
        }else{
        	Log.i("Toilet","subTypesActivity_fetchAllData: fetch specific data failed!");        	
        }        
         
        //获取SimpleCursorAdatper的实例
        String[] fromCol = {DatabaseUtil.KEY_TYPE_NAME};
        int[] toView = {R.id.layout_subType_name};
        @SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
        		R.layout.layout_sub_type, cursor, fromCol, toView);
        //绑定adapter
        Log.i("subTypesActivity", "before setAdatper");
        lstvw_subTypes.setAdapter(adapter);
        
        /*
        lstvw_subTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				
			}
        	
		});
        */
        /***********************************
		 * 新增初级类别，
		 * 弹出Dialog输入类别名
		 **********************************/
        Log.i("SubTypesActivity","before btn_newType.");
        btn_newType = (Button)findViewById(R.id.act_subTypes_btn_newType);
        
		//以下是Dialog的输入框
		final EditText dialog_newType_edttxt_Typename = new EditText(SubTypesActivity.this);
		dialog_newType_edttxt_Typename.setMaxEms(20);
		
		//以下是Dialog的按钮监听
		final DialogInterface.OnClickListener newListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				newTypeName = (dialog_newType_edttxt_Typename.getText().toString());
				//写入数据库操作	
				Log.i("dialog newType", "newListener");
				
			}
		};
		
		//需要的Dialog
		final AlertDialog dlg_newType = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_newType)
			.setView(dialog_newType_edttxt_Typename)
			.setPositiveButton(R.string.act_settings_dlg_posBtn, newListener)
			.setNegativeButton(R.string.act_settings_dlg_negBtn, null)
			.create();		
		
		//添加对显示时间间隔的TextView的点击监听
		btn_newType.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				dlg_newType.show();		
			} 
		});
        
        /***********************************
		 * 编辑初级类别，
		 * 弹出Dialog输入新的类别名
		 **********************************/        
		//以下是Dialog的输入框
		final EditText dialog_editType_edttxt_Typename = new EditText(SubTypesActivity.this);
		dialog_newType_edttxt_Typename.setMaxEms(20);
		
		//以下是Dialog的按钮监听
		final DialogInterface.OnClickListener editListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				newTypeName = (dialog_editType_edttxt_Typename.getText().toString());
				//写入数据库操作	
				Log.i("dialog editType", "editListener");
				
			}
		};
		
		//需要的Dialog
		final AlertDialog dlg_editType = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_newType)
			.setView(dialog_editType_edttxt_Typename)
			.setPositiveButton(R.string.act_settings_dlg_posBtn, editListener)
			.setNegativeButton(R.string.act_settings_dlg_negBtn, null)
			.create();	
        
        /***********************************
		 * 删除初级类别，
		 * 弹出Dialog以供确认
		 **********************************/		
		//以下是Dialog的按钮监听
		final DialogInterface.OnClickListener removeListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				//数据库删除操作	
				Log.i("dialog removeType", "removeListener");
				
			}
		};
		
		//需要的Dialog
		final AlertDialog dlg_removeType = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_remove)
			.setPositiveButton(R.string.act_settings_dlg_posBtn, removeListener)
			.setNegativeButton(R.string.act_settings_dlg_negBtn, null)
			.create();	
        
        /***********************************
		 * 长按初级类别列表的某个条目，
		 * 弹出Dialog，提示需要进行的操作选项：
		 *    编辑
		 *    删除
		 **********************************/        		
		//以下是Dialog的编辑按钮监听
		final DialogInterface.OnClickListener dlg_editListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				//弹出编辑Dialog	
				dlg_editType.show();
				
			}
		};       	
		
		//以下是Dialog的删除按钮监听
		final DialogInterface.OnClickListener dlg_removeListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				newTypeName = (dialog_newType_edttxt_Typename.getText().toString());
				//弹出删除Dialog	
				dlg_removeType.show();
			}
		};
		
		//需要的Dialog
		final AlertDialog dlg_whatToDo = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_whatToDo)
			.setPositiveButton(R.string.act_sub_types_btn_edit, dlg_editListener)
			.setNegativeButton(R.string.act_sub_types_btn_remove, dlg_removeListener)
			.create();		
		
		//添加对条目的长按监听
        lstvw_subTypes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
					long id) {
				dlg_whatToDo.show();
				return true;
			}  	
		});		

        

	}

}
