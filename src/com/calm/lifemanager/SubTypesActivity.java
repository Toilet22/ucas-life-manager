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
				// TODO Auto-generated method stub
				
				
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
				Log.i("dialog newType", "newListener");
				
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
		
		//��Ҫ��Dialog
		final AlertDialog dlg_whatToDo = new AlertDialog.Builder(SubTypesActivity.this)
			.setTitle(R.string.act_sub_types_whatToDo)
			.setPositiveButton(R.string.act_sub_types_btn_edit, dlg_editListener)
			.setNegativeButton(R.string.act_sub_types_btn_remove, dlg_removeListener)
			.create();		
		
		//��Ӷ���Ŀ�ĳ�������
        lstvw_subTypes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
					long id) {
				dlg_whatToDo.show();
				return true;
			}  	
		});		

        

	}

}
