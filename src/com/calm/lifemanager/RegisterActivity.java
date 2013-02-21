package com.calm.lifemanager;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	EditText edt_name;
	EditText edt_pswd1;
	EditText edt_pswd2;
	EditText edt_email;

	String username;
	String password1;
	String password2;
	String email;

	Button btn_regst;
	Button btn_back;

	private Handler mHandler;
	private Runnable mRunnableShowToast;

	private ProgressDialog pd;
	private Bundle registrationBundle = new Bundle();

	private static final int REGISTER_SUCCESS = 0;
	private static final int REGISTER_ERROR = 1;
	private static final int HTTP_ERROR = -1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case REGISTER_SUCCESS:
					// Check Anonymous User Database, If exist then
					// rename
					// it, else create a new one
					DatabaseUtil dbUtil = new DatabaseUtil(
							RegisterActivity.this);
					if (dbUtil.exist(DatabaseUtil.defaultDbName)) {
						Log.i("Registraion",
								"First registration, Renaming "
										+ DatabaseUtil.defaultDbName
										+ " to " + username + ".db");
						dbUtil.rename(DatabaseUtil.defaultDbName,
								username);
					} else {
						// Create A New User Database
						Log.i("Registration",
								"Not First User, create a new database");
						dbUtil = new DatabaseUtil(
								RegisterActivity.this, username + ".db");
						dbUtil.open();

						// Initial Assigned Types
						dbUtil.initPrimeTypes();
						dbUtil.initSubTypes();

						dbUtil.close();
					}
					
					// User Login
					Intent iLogin = new Intent(RegisterActivity.this,
							MainActivity.class);
					startActivity(iLogin);
					RegisterActivity.this.finish();
					
					break;
				case REGISTER_ERROR:
					Bundle errorBundle = (Bundle) msg.obj;
					int error = errorBundle.getInt("error");
					String errorMsg = errorBundle.getString("errorMsg");
					Toast.makeText(RegisterActivity.this,
							"错误编号：" + error + "\n错误信息：" + errorMsg,
							Toast.LENGTH_LONG).show();
					break;
				case HTTP_ERROR:
					Toast.makeText(RegisterActivity.this,
							getText(R.string.login_http_error),
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			};
		};

		mRunnableShowToast = new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(RegisterActivity.this, "Register Successfully!",
						Toast.LENGTH_LONG).show();
			}
		};

		edt_name = (EditText) findViewById(R.id.act_regst_edt_name);
		edt_pswd1 = (EditText) findViewById(R.id.act_regst_edt_pswd1);
		edt_pswd2 = (EditText) findViewById(R.id.act_regst_edt_pswd2);
		edt_email = (EditText) findViewById(R.id.act_regst_edt_email);

		/*
		 * 注册功能
		 */
		btn_regst = (Button) findViewById(R.id.act_regst_btn_regst);
		btn_regst.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				validateUserRegistration();
			}
		});

		/*
		 * 返回
		 */
		btn_back = (Button) findViewById(R.id.act_regst_btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent iBack = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(iBack);
				RegisterActivity.this.finish();
			}
		});
	}

	public void validateUserRegistration() {
		username = edt_name.getText().toString();
		password1 = edt_pswd1.getText().toString();
		password2 = edt_pswd2.getText().toString();
		email = edt_email.getText().toString();

		if (username == null || "".equals(username)) {
			Toast.makeText(RegisterActivity.this, "请输入用户名！", Toast.LENGTH_LONG)
					.show();
		} else if (username.length() < 2) {
			Toast.makeText(RegisterActivity.this, "用户名太短，请至少使用2个字符！",
					Toast.LENGTH_LONG).show();
		} else if (password1 == null || "".equals(password1)) {
			Toast.makeText(RegisterActivity.this, "请输入密码！", Toast.LENGTH_LONG)
					.show();
		} else if (password1.length() < 6) {
			Toast.makeText(RegisterActivity.this, "密码太短，请至少使用6个字符！",
					Toast.LENGTH_LONG).show();
		} else if (password2 == null || "".equals(password2)) {
			Toast.makeText(RegisterActivity.this, "请确认密码！",
					Toast.LENGTH_LONG).show();
		} else if (!(password1.equals(password2))) {
			Toast.makeText(RegisterActivity.this, "两次输入的密码不一致！",
					Toast.LENGTH_LONG).show();
		} else if (email == null || "".equals(email)) {
			Toast.makeText(RegisterActivity.this, "请输入有效的邮箱地址！",
					Toast.LENGTH_LONG).show();
		} else {
			pd = ProgressDialog.show(RegisterActivity.this, "",
					getString(R.string.is_registering));

			new Thread() {
				public void run() {

					Looper.prepare();
					Message msg = new Message();

					Map<String, String> newUser = new HashMap<String, String>();
					newUser.put("username", username);
					newUser.put("password", password1);
					newUser.put("visit_type", "android");

					String retStr = null;
					String message = null;
					int status = 100;

					try {
						retStr = NetToolUtil.sendPostRequest(
								NetToolUtil.accountRegisterUrl, newUser,
								"utf-8");
						if (retStr != null) {
							Log.i("User Registation", "result:" + retStr);
						}

						// Get info out of the String
						JSONObject retJson = new JSONObject(retStr);
						message = retJson.getString("message");
						status = retJson.getInt("status");

						if (status == 0) {
							// Show Toast of Successful Registration
							msg.what = REGISTER_SUCCESS;
							mHandler.post(mRunnableShowToast);
						} else {
							// Pop up a dialog to inform failure status and
							// message
							// of registration
							registrationBundle.putInt("error", status);
							registrationBundle.putString("errorMsg", message);
							msg.what = REGISTER_ERROR;
						}
						msg.obj = registrationBundle;
						mHandler.sendMessage(msg);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						msg.what = HTTP_ERROR;
						mHandler.sendMessage(msg);
						e.printStackTrace();
					} finally {
						pd.dismiss();
						Looper.loop();
					}

					Log.i("User Login", "Return status is: " + status);
					Log.i("User Login", "Return message is: " + message);
				}
			}.start();
		}

	}

}
