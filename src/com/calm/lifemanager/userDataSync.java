package com.calm.lifemanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class userDataSync {
	public static String currentLogedInUser = "";
	public static boolean isSwithingUser = false;
	
	public static long lastSyncTime = 0;
	public static String currentSyncDataTable = "";
	
	public static String defaultEncoding = "utf-8";

	public static final String PUSH = "push";
	public static final String PULL = "pull";

	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static final String DATA = "data";
	
	public static int retStatus = 100;
	public static String retMessage = null;
	
	// 用户数据同步通用接口,一个完整的同步逻辑
	public static boolean doUserDataSync(String url, String type, String dataSrc, DatabaseUtil dataBase, long lastSyncTime) throws JSONException {
		JSONObject retJson = new JSONObject();
		if(type.equals(PULL)) {
			Log.i("CloudSync","Pulling Data from " + url);
			retJson = doUserDataSyncPost(url, doPreparePullParam());
			if(retJson == null) {
				return false;
			}
		} else {
			Log.i("CloudSync","Pushing Data to " + url);
			retJson = doUserDataSyncPost(url, doPreparePushParam(dataSrc, dataBase, lastSyncTime));
			if(retJson == null) {
				return false;
			}
		}
		doUserDataSyncHook(retJson, dataBase, lastSyncTime);
		return true;
	}
	
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
	public static JSONObject doUserDataSyncPost(String url, JSONObject param) {
		String retStr = null;
		JSONObject retJson = null;
		if (currentLogedInUser == null || "".equals(currentLogedInUser)) {
			return null;
		} else {
			try {
				retStr = NetToolUtil.sendPostRequestJson(url, param,
						defaultEncoding);
				Log.i("CloudSync","Return Data from " + url + " :" + retStr);
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
			retJson.put("visit_type", "android");
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
		
		if (retCursor != null && retCursor.getCount() > 0) {
			retCursor.moveToFirst();
			
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
		
		if(retCursor != null) {
			retCursor.close();
		}
		if(dataBase != null) {
			dataBase.close();
		}
		
		retJson.put("data",dataSection);
		retJson.put("visit_type", "android");
		
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
	/**
	 * This method is used to handle data from server to database.
	 * @param retJson
	 * @param dataBase
	 * @param lastSyncTime
	 * @throws JSONException
	 */
	public static void doUserDataSyncHook(JSONObject retJson, DatabaseUtil dataBase, long lastSyncTime) throws JSONException {
		// Parse Data Retrieved From Server	
		
		retStatus = retJson.getInt(STATUS);
		if(retStatus == 20) {
			ContentValues tmpContentValues = new ContentValues();
			JSONArray dataSection = new JSONArray();
			dataSection = retJson.getJSONArray(DATA);
			//String whereClause = null;
			Cursor retCursor = null;
			String rawQuerySql = "SELECT DISTINCT oid as _id, ctime, mtime, stime FROM " + currentSyncDataTable;
			String tmpRawQuerySql = null;
			
			dataBase.open();
			//retCursor = dataBase.fetchDataWithWhere(currentSyncDataTable, whereClause);
			
			for(int dataIndex = 0; dataIndex < dataSection.length(); dataIndex++) {
				JSONObject tmpJson = (JSONObject)dataSection.opt(dataIndex);
				// Get data from JSON and update database
				tmpContentValues = doDataConvertToDatabase(tmpJson);
				tmpRawQuerySql = rawQuerySql + " WHERE ctime = " + tmpContentValues.getAsLong(DatabaseUtil.KEY_CTIME);
				retCursor = dataBase.rawQuery(tmpRawQuerySql, null);
				
				if(retCursor.getCount() > 0) {
					// Has a record with same c_time, update the record
					while(retCursor.moveToNext()) {
						// If retrieved server data is newer, then update the record
						if(retCursor.getLong(retCursor.getColumnIndex(DatabaseUtil.KEY_MTIME)) < tmpContentValues.getAsLong(DatabaseUtil.KEY_MTIME)) {
							tmpContentValues.put(DatabaseUtil.KEY_STIME, System.currentTimeMillis());
							dataBase.updateData(currentSyncDataTable, DatabaseUtil.KEY_CTIME, tmpContentValues.getAsLong(DatabaseUtil.KEY_CTIME).toString(), tmpContentValues);
						} else {
							// Just ignore the retrieved data, do nothing
							;
						}
					}
				} else {
					// Has no record, insert the record and update its s_time
					tmpContentValues.put(DatabaseUtil.KEY_STIME, System.currentTimeMillis());
					dataBase.insertData(currentSyncDataTable, tmpContentValues);
				}
			}
			
			if(retCursor != null) {
				retCursor.close();
			}
			if(dataBase != null) {
				dataBase.close();
			}
			
		} else {
			retMessage = retJson.getString(MESSAGE);
		}
	}
	
	// 用户数据同步专用接口，把服务器返回的JSON数据转换成数据库支持的数据
	/**
	 * This method is used to convert retrieved data from server to database record.
	 * @param retJson
	 * @return ContentValues
	 * @throws JSONException
	 */
	public static ContentValues doDataConvertToDatabase(JSONObject retJson) throws JSONException {
		ContentValues retContentValues = new ContentValues();
		
		if (retJson.has(DatabaseUtil.KEY_NICKNAME)) {
			retContentValues.put(DatabaseUtil.KEY_NICKNAME,
					retJson.getString(DatabaseUtil.KEY_NICKNAME));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_EMAIL)) {
			retContentValues.put(DatabaseUtil.KEY_EMAIL,
					retJson.getString(DatabaseUtil.KEY_EMAIL));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_SEX)) {
			retContentValues.put(DatabaseUtil.KEY_SEX,
					retJson.getInt(DatabaseUtil.KEY_SEX));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_AGE)) {
			retContentValues.put(DatabaseUtil.KEY_AGE,
					retJson.getInt(DatabaseUtil.KEY_AGE));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_JOB)) {
			retContentValues.put(DatabaseUtil.KEY_JOB,
					retJson.getInt(DatabaseUtil.KEY_JOB));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_CTIME)) {
			retContentValues.put(DatabaseUtil.KEY_CTIME,
					retJson.getLong(DatabaseUtil.KEY_CTIME));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_MTIME)) {
			retContentValues.put(DatabaseUtil.KEY_MTIME,
					retJson.getLong(DatabaseUtil.KEY_MTIME));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_STIME)) {
			retContentValues.put(DatabaseUtil.KEY_STIME,
					retJson.getLong(DatabaseUtil.KEY_STIME));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_MODE)) {
			retContentValues.put(DatabaseUtil.KEY_MODE,
					retJson.getInt(DatabaseUtil.KEY_MODE));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_RINGLEVEL)) {
			retContentValues.put(DatabaseUtil.KEY_RINGLEVEL,
					retJson.getInt(DatabaseUtil.KEY_RINGLEVEL));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_ALARMTYPE)) {
			retContentValues.put(DatabaseUtil.KEY_ALARMTYPE,
					retJson.getInt(DatabaseUtil.KEY_ALARMTYPE));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_TITLE)) {
			retContentValues.put(DatabaseUtil.KEY_TITLE,
					retJson.has(DatabaseUtil.KEY_TITLE));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_START)) {
			retContentValues.put(DatabaseUtil.KEY_START,
					retJson.getLong(DatabaseUtil.KEY_START));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_END)) {
			retContentValues.put(DatabaseUtil.KEY_END,
					retJson.getLong(DatabaseUtil.KEY_END));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_DESC)) {
			retContentValues.put(DatabaseUtil.KEY_DESC,
					retJson.has(DatabaseUtil.KEY_DESC));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_WHERE)) {
			retContentValues.put(DatabaseUtil.KEY_WHERE,
					retJson.has(DatabaseUtil.KEY_WHERE));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_KIND)) {
			retContentValues.put(DatabaseUtil.KEY_KIND,
					retJson.getInt(DatabaseUtil.KEY_KIND));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_REPETITION)) {
			retContentValues.put(DatabaseUtil.KEY_REPETITION,
					retJson.getInt(DatabaseUtil.KEY_REPETITION));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_REMINDER)) {
			retContentValues.put(DatabaseUtil.KEY_REMINDER,
					retJson.getInt(DatabaseUtil.KEY_REMINDER));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_PRIORITY)) {
			retContentValues.put(DatabaseUtil.KEY_PRIORITY,
					retJson.getInt(DatabaseUtil.KEY_PRIORITY));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_STATUS)) {
			retContentValues.put(DatabaseUtil.KEY_STATUS,
					retJson.getInt(DatabaseUtil.KEY_STATUS));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_TYPE)) {
			retContentValues.put(DatabaseUtil.KEY_TYPE,
					retJson.getInt(DatabaseUtil.KEY_TYPE));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_RATING)) {
			retContentValues.put(DatabaseUtil.KEY_RATING,
					retJson.getInt(DatabaseUtil.KEY_RATING));
		} 
		
		if (retJson.has(DatabaseUtil.KEY_MOOD)) {
			retContentValues.put(DatabaseUtil.KEY_MOOD,
					retJson.getInt(DatabaseUtil.KEY_MOOD));
		}
		
		return retContentValues;
	}
}
