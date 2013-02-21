package com.calm.lifemanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;

public class userDataSync {
	public static String currentLogedInUser = "";
	public static long lastSyncTime = 0;

	public static String defaultEncoding = "utf-8";

	public static final String PUSH = "push";
	public static final String PULL = "pull";

	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static final String DATA = "data";
	
	public static int retStatus = 100;
	public static String retMessage = null;
	
	// 用户数据同步通用接口,网络同步请求操作
	/**
	 * This method is a generic interface to do user data sync
	 * 
	 * @param url
	 * @param type
	 * @param param
	 * @param context
	 * @return JSONObject
	 */
	public static JSONObject doUserDataSyncPost(String url, JSONObject param,
			Context context) {
		String retStr = null;
		JSONObject retJson = null;
		if (currentLogedInUser == null || "".equals(currentLogedInUser)) {
			return null;
		} else {
			try {
				retStr = NetToolUtil.sendPostRequestJson(url, param,
						defaultEncoding);
				retJson = new JSONObject(retStr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return retJson;
		}
	}

	// 用户数据同步专用接口，准备pull操作数据
	/**
	 * This method is used to prepare data for pull
	 * 
	 * @return JSONObject
	 */
	public static JSONObject doPreparePullParam() {
		JSONObject retJson = new JSONObject();
		try {
			retJson.put("username", currentLogedInUser);
			retJson.put("last_time", lastSyncTime);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retJson;
	}

	// 用户数据同步专用接口，准备push操作数据
	/**
	 * This method is used to prepare data to push
	 * @param dataSrc
	 * @param dataBase
	 * @param lastSyncTime
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject doPreparePushParam(String dataSrc,
			DatabaseUtil dataBase, long lastSyncTime) throws JSONException {
		Cursor retCursor = null;
		JSONObject retJson = new JSONObject();
		JSONArray dataSection = new JSONArray();
		String whereClause = null;

		try {
			retJson.put("username", currentLogedInUser);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dataBase.open();
		retCursor = dataBase.fetchDataWithWhere(dataSrc, whereClause);
		if (retCursor != null) {
			JSONObject dataJson = new JSONObject();
			int columnCount = retCursor.getColumnCount();
			String[] columnNames = retCursor.getColumnNames();
			do {
				long mtimeColumn = retCursor
						.getColumnIndex(DatabaseUtil.KEY_MTIME);
				if (mtimeColumn > lastSyncTime) {
					// Data should push, add to JSON
					dataJson = doDataConvertToJson(columnCount, columnNames, retCursor);
					dataSection.put(dataJson);
				}
			} while (retCursor.moveToNext());
		}
		
		retJson.put("data",dataSection);
		return retJson;
	}
	
	// 用户数据同步专用接口，从数据库查询数据后转换成JSON数据
	/**
	 * This method is used to convert data retrieved from database to JSON format
	 * @param columnCount
	 * @param columnNames
	 * @param retCursor
	 * @return
	 * @throws JSONException
	 */
	private static JSONObject doDataConvertToJson(int columnCount,
			String[] columnNames, Cursor retCursor) throws JSONException {

		JSONObject dataJson = new JSONObject();

		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			if (columnNames[columnIndex].equals(DatabaseUtil.KEY_NICKNAME)) {
				dataJson.put(DatabaseUtil.KEY_NICKNAME,
						retCursor.getString(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_EMAIL)) {
				dataJson.put(DatabaseUtil.KEY_EMAIL,
						retCursor.getString(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_SEX)) {
				dataJson.put(DatabaseUtil.KEY_SEX,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_AGE)) {
				dataJson.put(DatabaseUtil.KEY_AGE,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_JOB)) {
				dataJson.put(DatabaseUtil.KEY_JOB,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_CTIME)) {
				dataJson.put(DatabaseUtil.KEY_CTIME,
						retCursor.getLong(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_MTIME)) {
				dataJson.put(DatabaseUtil.KEY_MTIME,
						retCursor.getLong(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_STIME)) {
				dataJson.put(DatabaseUtil.KEY_STIME,
						retCursor.getLong(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_MODE)) {
				dataJson.put(DatabaseUtil.KEY_MODE,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex]
					.equals(DatabaseUtil.KEY_RINGLEVEL)) {
				dataJson.put(DatabaseUtil.KEY_RINGLEVEL,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex]
					.equals(DatabaseUtil.KEY_ALARMTYPE)) {
				dataJson.put(DatabaseUtil.KEY_ALARMTYPE,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_TITLE)) {
				dataJson.put(DatabaseUtil.KEY_TITLE,
						retCursor.getString(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_START)) {
				dataJson.put(DatabaseUtil.KEY_START,
						retCursor.getLong(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_END)) {
				dataJson.put(DatabaseUtil.KEY_END,
						retCursor.getLong(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_DESC)) {
				dataJson.put(DatabaseUtil.KEY_DESC,
						retCursor.getString(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_WHERE)) {
				dataJson.put(DatabaseUtil.KEY_WHERE,
						retCursor.getString(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_KIND)) {
				dataJson.put(DatabaseUtil.KEY_KIND,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex]
					.equals(DatabaseUtil.KEY_REPETITION)) {
				dataJson.put(DatabaseUtil.KEY_REPETITION,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex]
					.equals(DatabaseUtil.KEY_REMINDER)) {
				dataJson.put(DatabaseUtil.KEY_REMINDER,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex]
					.equals(DatabaseUtil.KEY_PRIORITY)) {
				dataJson.put(DatabaseUtil.KEY_PRIORITY,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_STATUS)) {
				dataJson.put(DatabaseUtil.KEY_STATUS,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_TYPE)) {
				dataJson.put(DatabaseUtil.KEY_TYPE,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_RATING)) {
				dataJson.put(DatabaseUtil.KEY_RATING,
						retCursor.getInt(columnIndex));
			} else if (columnNames[columnIndex].equals(DatabaseUtil.KEY_MOOD)) {
				dataJson.put(DatabaseUtil.KEY_MOOD,
						retCursor.getInt(columnIndex));
			} else {
				;
			}
		}
		return dataJson;
	}
	
	// 用户数据同步专用接口，处理从服务器接收的未同步数据
	public static void doUserDataSyncHook(JSONObject retJson) throws JSONException {
		// Parse Data Retrieved From Server	
		
		retStatus = retJson.getInt(STATUS);
		if(retStatus == 20) {
			JSONArray dataSection = new JSONArray();
			dataSection = retJson.getJSONArray(DATA);
			for(int dataIndex = 0; dataIndex < dataSection.length(); dataIndex++) {
				JSONObject tmpJson = (JSONObject)dataSection.opt(dataIndex);
				// Get data from JSON and update database
				
			}
		} else {
			retMessage = retJson.getString(MESSAGE);
		}
	}
}
