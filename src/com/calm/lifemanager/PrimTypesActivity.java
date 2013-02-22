package com.calm.lifemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

public class PrimTypesActivity extends Activity {
	DatabaseUtil dbUtil;
	ListView lstvw_primTypes;
	String newTypeName;
	String selectedTypeName;
	Button btn_newType;
	Button btn_back;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);	

        Log.i("PrimTypesActivity", "before setContentView");
        setContentView(R.layout.activity_prim_types);
        
        //获取ListView实例
        Log.i("PrimTypesActivity","Get ListView");
        lstvw_primTypes = (ListView)findViewById(R.id.act_primTypes_lstvw_primTypes);
        
        //获取数据库操作单元
        dbUtil = new DatabaseUtil(this);
        Log.i("PrimTypesActivity","Open DB");
        dbUtil.open();

        //获得指向数据的游标
        final Cursor cursor;
        //String[] selectCol = {DatabaseUtil.KEY_TYPE_NAME, DatabaseUtil.KEY_TYPE_ICON};
        //cursor = dbUtil.fetchAllData(DatabaseUtil.PRIM_TYPES, selectCol);
        cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id, type_name FROM tb_prim_types", null);
        
        if(cursor.moveToNext()){
        	Log.i("Toilet","PrimTypesActivity_fetchAllData: fetch specific data works!");
        }else{
        	Log.i("Toilet","PrimTypesActivity_fetchAllData: fetch specific data failed!");        	
        }        
         
        //获取SimpleCursorAdatper的实例
        String[] fromCol = {DatabaseUtil.KEY_TYPE_NAME};
        int[] toView = {R.id.layout_primType_name};
        @SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
        		R.layout.layout_prim_type, cursor, fromCol, toView);
        //绑定adapter
        Log.i("PrimTypesActivity", "before setAdatper");
        lstvw_primTypes.setAdapter(adapter);
        
        /************************************
         * 单击初级类别的某个条目，
         * 进入其对应的次级类别列表
         ***********************************/
        lstvw_primTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// 获取被点击的类别名
				cursor.moveToPosition(position);
				String typeName = cursor.getString(1);
				Log.i("cursor getString", typeName);
				//开启次级类别的Activity
				Intent intent = new Intent(PrimTypesActivity.this, SubTypesActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putString("PrimTypeName", typeName);
				intent.putExtras(mBundle);
				startActivity(intent);
			}  	
		});
        
        /***********************************
		 * 新增初级类别，
		 * 弹出Dialog输入类别名
		 **********************************/
        btn_newType = (Button)findViewById(R.id.act_primTypes_btn_newType);
        
		//以下是Dialog的输入框
		final EditText dialog_newType_edttxt_Typename = new EditText(PrimTypesActivity.this);
		dialog_newType_edttxt_Typename.setMaxEms(20);
		
		//以下是Dialog的按钮监听
		final DialogInterface.OnClickListener newListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				newTypeName = (dialog_newType_edttxt_Typename.getText().toString());
				//写入数据库操作	
				Log.i("dialog newType", newTypeName);
				if(dbUtil.newPrimeType(newTypeName, null) == -1){
					Toast.makeText(getApplicationContext(), R.string.act_prim_types_alreadyExist, 
							Toast.LENGTH_SHORT).show();
				}
				cursor.requery();
			}
		};
		
		//需要的Dialog
		final AlertDialog dlg_newType = new AlertDialog.Builder(PrimTypesActivity.this)
			.setTitle(R.string.act_prim_types_newType)
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
		final EditText dialog_editType_edttxt_Typename = new EditText(PrimTypesActivity.this);
		dialog_newType_edttxt_Typename.setMaxEms(20);
		
		//以下是Dialog的按钮监听
		final DialogInterface.OnClickListener editListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				newTypeName = (dialog_editType_edttxt_Typename.getText().toString());
				//写入数据库操作	
				Log.i("dialog editType", "editListener");
				ContentValues newVal = new ContentValues();
				newVal.put(DatabaseUtil.KEY_TYPE_NAME, newTypeName);
				dbUtil.updatePrimeType(selectedTypeName, newVal);
				cursor.requery();
			}
		};
		
		//需要的Dialog
		final AlertDialog dlg_editType = new AlertDialog.Builder(PrimTypesActivity.this)
			.setTitle(R.string.act_prim_types_newType)
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
				dbUtil.deletePrimeType(selectedTypeName);
				cursor.requery();
			}
		};
		
		//需要的Dialog
		final AlertDialog dlg_removeType = new AlertDialog.Builder(PrimTypesActivity.this)
			.setTitle(R.string.act_prim_types_remove)
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
		final AlertDialog dlg_whatToDo = new AlertDialog.Builder(PrimTypesActivity.this)
			.setTitle(R.string.act_prim_types_whatToDo)
			.setPositiveButton(R.string.act_prim_types_btn_edit, dlg_editListener)
			.setNegativeButton(R.string.act_prim_types_btn_remove, dlg_removeListener)
			.create();		
		
		//添加对条目的长按监听
        lstvw_primTypes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
					long id) {
				cursor.moveToPosition(position);
				selectedTypeName = cursor.getString(1);
				Log.i("PrimTypesAcitivty_longClicked",selectedTypeName);
				dlg_whatToDo.show();
				return true;
			}  	
		});	
        
        /***************************************
         * 返回按钮
         **************************************/
        Log.i("SubTypesActivity","before btn_back");
        btn_back = (Button)findViewById(R.id.act_primTypes_btn_back);
        btn_back.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				finish();
			}
        });

	}

}
