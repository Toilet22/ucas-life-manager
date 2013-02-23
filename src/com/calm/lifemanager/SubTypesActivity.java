package com.calm.lifemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SubTypesActivity extends Activity {
	DatabaseUtil dbUtil;
	ListView lstvw_subTypes;
	String fatherTypeName;
	Button btn_newType;
	Button btn_back;
	String newTypeName;
	Cursor cursor;
	
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
        cursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id,  type_name FROM tb_sub_types where " + DatabaseUtil.KEY_TYPE_BELONGTO + "=?;",new String[] {fatherTypeName});
        
        if(cursor.moveToNext()){
        	Log.i("Toilet","subTypesActivity_fetchAllData: fetch specific data works!");
        }else{
        	Log.i("Toilet","subTypesActivity_fetchAllData: fetch specific data failed!");        	
        }        
         
        //��ȡSimpleCursorAdatper��ʵ��
        String[] fromCol = {DatabaseUtil.KEY_TYPE_NAME};
        int[] toView = {R.id.layout_subType_name};
        @SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
        		R.layout.layout_sub_type, cursor, fromCol, toView);
        //��adapter
        Log.i("subTypesActivity", "before setAdatper");
        lstvw_subTypes.setAdapter(adapter);
        
        /*
        lstvw_subTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
			}
		});
        */
        /***********************************
		 * �����������
		 * ����Dialog���������
		 **********************************/
        Log.i("SubTypesActivity","before btn_newType.");
        btn_newType = (Button)findViewById(R.id.act_subTypes_btn_newType);
        
		//������Dialog�������
		final EditText dialog_newType_edttxt_Typename = new EditText(SubTypesActivity.this);
		dialog_newType_edttxt_Typename.setMaxEms(20);
		
		//������Dialog�İ�ť����
		final DialogInterface.OnClickListener newListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				newTypeName = (dialog_newType_edttxt_Typename.getText().toString());
				//д�����ݿ����	
				Log.i("dialog newType", newTypeName);
//				if(dbUtil.newSubType(newTypeName, null) == -1){
//					Toast.makeText(getApplicationContext(), R.string.act_sub_types_alreadyExist, 
//							Toast.LENGTH_SHORT).show();
//				}
//				cursor.requery();
				if(dbUtil.newSubType(newTypeName, null, fatherTypeName) == -1){
					Toast.makeText(getApplicationContext(), R.string.act_sub_types_alreadyExist, 
							Toast.LENGTH_SHORT).show();
				}
				cursor.requery();
			}
		};
		
		//��Ҫ��Dialog
		final AlertDialog dlg_newType = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_newType)
			.setView(dialog_newType_edttxt_Typename)
			.setPositiveButton(R.string.act_settings_dlg_posBtn, newListener)
			.setNegativeButton(R.string.act_settings_dlg_negBtn, null)
			.create();		
		
		//��Ӷ���ʾʱ������TextView�ĵ������
		btn_newType.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				dlg_newType.show();		
			} 
		});
        
        /***********************************
		 * �༭�������
		 * ����Dialog�����µ������
		 **********************************/        
		//������Dialog�������
		final EditText dialog_editType_edttxt_Typename = new EditText(SubTypesActivity.this);
		dialog_newType_edttxt_Typename.setMaxEms(20);
		
		//������Dialog�İ�ť����
		final DialogInterface.OnClickListener editListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				newTypeName = (dialog_editType_edttxt_Typename.getText().toString());
				//д�����ݿ����	
				Log.i("dialog editType", "editListener");
				
			}
		};
		
		//��Ҫ��Dialog
		final AlertDialog dlg_editType = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_newType)
			.setView(dialog_editType_edttxt_Typename)
			.setPositiveButton(R.string.act_settings_dlg_posBtn, editListener)
			.setNegativeButton(R.string.act_settings_dlg_negBtn, null)
			.create();	
        
        /***********************************
		 * ɾ���������
		 * ����Dialog�Թ�ȷ��
		 **********************************/		
		//������Dialog�İ�ť����
		final DialogInterface.OnClickListener removeListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				//���ݿ�ɾ������	
				Log.i("dialog removeType", "removeListener");
				
			}
		};
		
		//��Ҫ��Dialog
		final AlertDialog dlg_removeType = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_remove)
			.setPositiveButton(R.string.act_settings_dlg_posBtn, removeListener)
			.setNegativeButton(R.string.act_settings_dlg_negBtn, null)
			.create();	
        
		
		/***********************************
		 * ɾ���������
		 * ����Dialog�Թ�ȷ��
		 **********************************/	
		final Cursor fatherCursor;
        fatherCursor = dbUtil.rawQuery("SELECT DISTINCT oid as _id, type_name FROM tb_prim_types", null); 
        if(!fatherCursor.moveToNext()){
        	Log.e("fatherCursor", "No data!");
        }
        //��ȡSimpleCursorAdatper��ʵ��
        String[] from = {DatabaseUtil.KEY_TYPE_NAME};
        int[] to = {R.id.layout_primType_name};
		CursorAdapter fatherAdapter = new SimpleCursorAdapter(this, 
		        		R.layout.layout_prim_type, fatherCursor, from, to);;
		//������Dialog�İ�ť����
		final DialogInterface.OnClickListener movetoListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				//���ݿ���²���	
				Log.i("dialog removeType", fatherCursor.getString(which));
				
			}
		};
		
		//��Ҫ��Dialog
		final AlertDialog dlg_movetoType = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_remove)
			.setAdapter(fatherAdapter, movetoListener)
			.setPositiveButton(R.string.act_settings_dlg_posBtn, removeListener)
			.setNegativeButton(R.string.act_settings_dlg_negBtn, null)
			.create();	
		
        /***********************************
		 * ������������б��ĳ����Ŀ��
		 * ����Dialog����ʾ��Ҫ���еĲ���ѡ�
		 *    �༭
		 *    ɾ��
		 **********************************/        		
		//������Dialog�ı༭��ť����
		final DialogInterface.OnClickListener dlg_editListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				//�����༭Dialog	
				dlg_editType.show();
				
			}
		};       	

		//������Dialog��ɾ����ť����
		final DialogInterface.OnClickListener dlg_removeListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				newTypeName = (dialog_newType_edttxt_Typename.getText().toString());
				//����ɾ��Dialog	
				dlg_removeType.show();
			}
		};
		
		//������Dialog���Ƶ���ť����
		final DialogInterface.OnClickListener dlg_movetoListener = new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				//����ɾ��Dialog	
				dlg_movetoType.show();
			}
		};
		
		//��Ҫ��Dialog
		final AlertDialog dlg_whatToDo = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_whatToDo)
			.setPositiveButton(R.string.act_sub_types_btn_edit, dlg_editListener)
			.setNegativeButton(R.string.act_sub_types_btn_remove, dlg_removeListener)
			.setNeutralButton(R.string.act_sub_types_btn_moveto, dlg_movetoListener)
			.create();		
		
		//��Ӷ���Ŀ�ĳ�������
        lstvw_subTypes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
					long id) {
				dlg_whatToDo.show();
				return true;
			}  	
		});		

        /***************************************
         * ���ذ�ť
         **************************************/
        Log.i("SubTypesActivity","before btn_back");
        btn_back = (Button)findViewById(R.id.act_subTypes_btn_back);
        btn_back.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				finish();
			}
        });

	}

}
