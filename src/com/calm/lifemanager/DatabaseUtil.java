package com.calm.lifemanager;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseUtil{

	private static final String TAG = "DatabaseUtil";

	/**
	 * Database Name
	 */
	//private static final String DATABASE_NAME = "lifemanager.db";
	public static String defaultDbName = "anonymous_user.db";
	public static String dbName = "anonymous_user.db";

	/**
	 * Database Version
	 */
	//private static final int DATABASE_VERSION = 1;
	public static int defaultDbVersion = 1;
	public static int dbVersion = 1;
	
	// User Data Sync Variables
	public static long defaultSyncTime = 0;
	
	// Table columns
	public static final String KEY_ID = "_id";
		
	/***************************************
	 * �û��б�
	 * *************************************/
	// Table Name
	public static final String USER = "tb_user";

	// Table columns
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_CTIME = "ctime";
	public static final String KEY_MTIME = "mtime";
	public static final String KEY_STIME = "stime";

	// Database creation SQL statement
	private static final String CREATE_USER_TABLE =
		"create table " + USER + " ("
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	    + KEY_USERNAME + " VARCHAR(30) not null, "
		+ KEY_PASSWORD + " VARCHAR(16) not null, " 
	    + KEY_CTIME + " INTEGER not null);";

	/***************************************
	 * �û�����
	 **************************************/
	// Table Name
	public static final String USER_PROFILE = "tb_user_profile";

	// Table columns
	public static final String KEY_NICKNAME = "nickname";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_SEX = "sex";
	public static final String KEY_AGE = "age";
	public static final String KEY_JOB = "job";

	// Database creation SQL statement
	private static final String CREATE_USER_PROFILE_TABLE =
		"create table " + USER_PROFILE + " ("
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ KEY_NICKNAME + " VARCHAR(30), " 
		+ KEY_EMAIL + " VARCHAR(30), " 
		+ KEY_SEX + " SMALLINT, " 
		+ KEY_AGE + " SMALLINT, "
		+ KEY_JOB + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/***************************************
	 * �û��趨
	 **************************************/
	// Table Name
	public static final String USER_SETTINGS = "tb_user_settings";

	// Table columns
	public static final String KEY_MODE = "mode";
	public static final String KEY_RINGLEVEL = "ringlevel";
	public static final String KEY_ALARMTYPE = "alarmtype";

	// Database creation SQL statement
	private static final String CREATE_USER_SETTINGS_TABLE =
		"create table " + USER_SETTINGS + " (" 
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ KEY_MODE + " SMALLINT, " 
		+ KEY_RINGLEVEL + " SMALLINT, "
		+ KEY_ALARMTYPE + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/***************************************
	 * todoList�б�
	 **************************************/
	// Table Name
	public static final String TODOLIST = "tb_todolist";

	// Table columns
	public static final String KEY_TITLE = "title";
	public static final String KEY_START = "start";
	public static final String KEY_END = "end";
	public static final String KEY_WHERE = "place";
	public static final String KEY_DESC = "desc";
	public static final String KEY_KIND = "kind";
	public static final String KEY_REPETITION = "repetition";
	public static final String KEY_REMINDER = "reminder";
	public static final String KEY_PRIORITY = "priority";
	public static final String KEY_STATUS = "status";

	// Database creation SQL statement
	private static final String CREATE_TODOLIST_TABLE =
		"create table " + TODOLIST + " (" 
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	    + KEY_TITLE + " text not null, "
		+ KEY_START + " INTEGER, " 
		+ KEY_END + " INTEGER, " 
		+ KEY_DESC + " text, " 
		+ KEY_WHERE + " text, "
		+ KEY_KIND + " SMALLINT, "
		+ KEY_REPETITION + " SMALLINT, "
		+ KEY_REMINDER + " SMALLINT, "
		+ KEY_PRIORITY + " SMALLINT, "
		+ KEY_STATUS + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/***************************************
	 * collector�б�
	 **************************************/
	// Table Name
	public static final String COLLECTOR = "tb_collector";

	// Table columns

	// Database creation SQL statement
	private static final String CREATE_COLLECTOR_TABLE =
		"create table " + COLLECTOR + " (" 
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	    + KEY_TITLE + " text not null, "
		+ KEY_START + " INTEGER, " 
		+ KEY_END + " INTEGER, " 
		+ KEY_DESC + " text, " 
		+ KEY_WHERE + " text, "
		+ KEY_KIND + " SMALLINT, "
		+ KEY_REPETITION + " SMALLINT, "
		+ KEY_REMINDER + " SMALLINT, "
		+ KEY_PRIORITY + " SMALLINT, "
		+ KEY_STATUS + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/***************************************
	 * wishList�б�
	 **************************************/
	// Table Name
	public static final String WISHLIST = "tb_wishlist";

	// Table columns

	// Database creation SQL statement
	private static final String CREATE_WISHLIST_TABLE =
		"create table " + WISHLIST + " (" 
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	    + KEY_TITLE + " text not null, "
		+ KEY_START + " INTEGER, " 
		+ KEY_END + " INTEGER, " 
		+ KEY_DESC + " text, " 
		+ KEY_WHERE + " text, "
		+ KEY_KIND + " SMALLINT, "
		+ KEY_REPETITION + " SMALLINT, "
		+ KEY_REMINDER + " SMALLINT, "
		+ KEY_PRIORITY + " SMALLINT, "
		+ KEY_STATUS + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/***************************************
	 * timeLog �б�
	 **************************************/
	// Table Name
	public static final String RECORD = "tb_record";

	// Table columns
	public static final String KEY_COST = "cost_time";
	public static final String KEY_RATING = "rating";
	public static final String KEY_MOOD = "mood";
	public static final String KEY_TYPE = "type";
	
	// Database creation SQL statement
	private static final String CREATE_RECORD_TABLE =
		"create table " + RECORD + " (" 
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ KEY_TYPE + " SMALLINT, "
		+ KEY_START + " INTEGER, " 
		+ KEY_END + " INTEGER, " 
		+ KEY_COST + " INTEGER, " 
		+ KEY_RATING + " SMALLINT, "
		+ KEY_MOOD + " SMALLINT, "
		+ KEY_STATUS + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	// Table Name
	public static final String TIME_TIPS = "tb_time_tips";

	// Table columns
	public static final String KEY_CONTENT = "content";
	public static final String KEY_INDEX = "tip_index";
	
	// Database creation SQL statement
	private static final String CREATE_TIME_TIPS_TABLE =
		"create table " + TIME_TIPS + " ("
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ KEY_TITLE + " text not null, "
		+ KEY_CONTENT + " text not null, "
		+ KEY_INDEX + " VARCHAR(50) not null);";
	
	/***************************************
	 * ���齨���б�
	 **************************************/
	// Table Name
	public static final String MOOD_TIPS = "tb_mood_tips";

	// Table columns
	
	// Database creation SQL statement
	private static final String CREATE_MOOD_TIPS_TABLE =
		"create table " + MOOD_TIPS + " (" 
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ KEY_TITLE + " text not null, "
		+ KEY_CONTENT + " text not null, "
		+ KEY_INDEX + " VARCHAR(50) not null);";
	
	/***************************************
	 * �û��Զ������,�����б�
	 **************************************/
	// Table Name
	public static final String PRIM_TYPES = "tb_prim_types";

	// Table columns
	public static final String KEY_TYPE_NAME = "type_name";
	public static final String KEY_TYPE_ICON = "type_icon";

	// Database creation SQL statement
	private static final String CREATE_PRIM_TYPES_TABLE =
		"create table " + PRIM_TYPES + " (" 
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ KEY_TYPE_NAME + " VARCHAR(30) not null, "
		+ KEY_TYPE_ICON + " text, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/***************************************
	 * �û��Զ������,�μ��б�
	 **************************************/
	// Table Name
	public static final String SUB_TYPES = "tb_sub_types";

	// Table columns
	public static final String KEY_TYPE_BELONGTO = "type_belongto";

	// Database creation SQL statement
	private static final String CREATE_SUB_TYPES_TABLE =
		"create table " + SUB_TYPES + " (" 
		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ KEY_TYPE_NAME + " VARCHAR(30) not null, "
		+ KEY_TYPE_ICON + " text, "
		+ KEY_TYPE_BELONGTO + " VARCHAR(30) not null, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/***************************************
	 * ��������
	 **************************************/
	private final Context mCtx;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	/***************************************
	 * Inner private class. 
	 *   DatabaseHelper class for creating 
	 *   and updating database.
	 **************************************/
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			//super(context, DATABASE_NAME, null, DATABASE_VERSION);
			super(context, dbName, null, dbVersion);
		}
		
		/**
		 * onCreate method is called for the 1st time 
		 *   when database doesn't exists.
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG, "Creating DataBase: " + dbName);
			
			db.execSQL(CREATE_USER_TABLE);
			Log.i(TAG,"Creating DataBase Table: " + USER);
			
			db.execSQL(CREATE_USER_PROFILE_TABLE);
			Log.i(TAG,"Creating DataBase Table: " + USER_PROFILE);
			
			db.execSQL(CREATE_USER_SETTINGS_TABLE);
			Log.i(TAG,"Creating DataBase Table: " + USER_SETTINGS);
			
			db.execSQL(CREATE_TODOLIST_TABLE);
			Log.i(TAG,"Creating DataBase Table: " + TODOLIST);
			
			db.execSQL(CREATE_COLLECTOR_TABLE);
			Log.i(TAG,"Creating DataBase Table: " + COLLECTOR);
			
			db.execSQL(CREATE_WISHLIST_TABLE);
			Log.i(TAG,"Creating DataBase Table: " + WISHLIST);
			
			db.execSQL(CREATE_RECORD_TABLE);
			Log.i(TAG,"Creating DataBase Table: " + RECORD);
			
			db.execSQL(CREATE_TIME_TIPS_TABLE);
			Log.i(TAG,"Creating DataBase Table: " + TIME_TIPS);
			
			db.execSQL(CREATE_MOOD_TIPS_TABLE);
			Log.i(TAG,"Creating DataBase Table: " + MOOD_TIPS);

			db.execSQL(CREATE_PRIM_TYPES_TABLE);
			Log.i(TAG, "Creating DataBase Table: " + PRIM_TYPES);
			
			db.execSQL(CREATE_SUB_TYPES_TABLE);
			Log.i(TAG, "Creating DataBase Table: " + SUB_TYPES);
		}
		
		/**
		 * onUpgrade method is called when database version changes.
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS tb_user");
			db.execSQL("DROP TABLE IF EXISTS tb_user_profile");
			db.execSQL("DROP TABLE IF EXISTS tb_user_settings");
			db.execSQL("DROP TABLE IF EXISTS tb_todolist");
			db.execSQL("DROP TABLE IF EXISTS tb_collector");
			db.execSQL("DROP TABLE IF EXISTS tb_wishlist");
			db.execSQL("DROP TABLE IF EXISTS tb_record");
			db.execSQL("DROP TABLE IF EXISTS tb_time_tips");
			db.execSQL("DROP TABLE IF EXISTS tb_mood_tips");
			db.execSQL("DROP TABLE IF EXISTS tb_prim_types");
			db.execSQL("DROP TABLE IF EXISTS tb_sub_types");
			onCreate(db);
			
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion);
		}
	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 *
	 * @param ctx the Context within which to work
	 */
	public DatabaseUtil(Context ctx) {
		this.mCtx = ctx;
	}
	
	public DatabaseUtil(Context ctx, String dbName) {
		this.mCtx = ctx;
		DatabaseUtil.dbName = dbName;
	}
	
	public DatabaseUtil(Context ctx, String dbName, int dbVersion) {
		this.mCtx = ctx;
		DatabaseUtil.dbName = dbName; 
		DatabaseUtil.dbVersion = dbVersion; 
	}
	
	/**
	 * This method is used for creating/opening connection
	 * @return instance of DatabaseUtil
	 * @throws SQLException
	 */
	public DatabaseUtil open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * This method is used for closing the connection.
	 */
	public void close() {
		mDbHelper.close();
	}
	
	/**
	 * This method is used to check if a database existed
	 * @param dbName
	 * @return
	 */
	public boolean exist(String dbName) {
		File dbFile = mCtx.getDatabasePath(dbName);
		if (dbFile.exists() == true) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method is used to rename an existed database
	 * @param oldDbName
	 * @param newDbName
	 * @return void
	 */
	public void rename (String oldDbName, String newDbName) {
		File dbFile = mCtx.getDatabasePath(dbName);
		File newDbFile = new File(dbFile.getParentFile(), newDbName);
		dbFile.renameTo(newDbFile);
		newDbFile.delete();
	}
	
	/******************************************
	 *  �ṩ��ͨ�����ݲ����ӿ�
	 *  All Tables
	 *  ������¼��ɾ����¼�����¼�¼����ѯ��¼
	 *****************************************/
	
	/**
	 * Insert Time Stuff Automatically.
	 * @param iniContentValues
	 */
	public void updateRecordTime(ContentValues iniContentValues) {
		iniContentValues.put(KEY_CTIME, System.currentTimeMillis());
		iniContentValues.put(KEY_MTIME, System.currentTimeMillis());
		iniContentValues.put(KEY_STIME, defaultSyncTime);
	}
	
	/**
	 * This is a generic interface used to insert new record to the specified table.
	 * @param tb
	 * @param insertValues
	 * @return long
	 */
	public long insertData(String tb, ContentValues insertValues) {
		return mDb.insert(tb, null, insertValues);
	}
	
	/**
	 * This method is a generic interface used to delete a record from specified table according to specified keyword and key value.
	 * @param tb
	 * @param keyWord
	 * @param keyValue
	 * @return boolean
	 */

	public boolean deleteData(String tb,String keyWord, String keyValue) {
		return mDb.delete(tb, keyWord + "='" + keyValue + "'", null) > 0;
	}
	
	/**
	 * This method is a generic interface used to update a record from the specified table.
	 * @param tb
	 * @param keyWord
	 * @param keyValue
	 * @param updateValues
	 * @return boolean
	 */
	
	public boolean updateData(String tb, String keyWord, String keyValue, ContentValues updateValues) {
		return mDb.update(tb, updateValues, keyWord + "=" + keyValue, null) > 0;
	}
	
	/**
	 * This method is a generic interface used to fetch a specified record from the specified table.
	 * @param tb
	 * @param keyWord
	 * @param keyValue
	 * @param selectColumns
	 * @return Cursor
	 * @throws SQLException
	 */
	public Cursor fetchData(String tb, String keyWord, String keyValue, String[] selectColumns) throws SQLException {
		Cursor mCursor =
				mDb.query(true, tb, selectColumns, keyWord + "='" + keyValue + "'", null,
						null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
	}
	
	public Cursor fetchDataWithWhere(String tb, String whereClause) throws SQLException {
		Cursor mCursor =
				mDb.query(true, tb, null, whereClause, null,
						null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
	}
	
	/**
	 * This method is a generic interface used to fetch all records from the specified table.
	 * @param tb
	 * @param selectColumns
	 * @return Cursor
	 */
	public Cursor fetchAllData(String tb,String[] selectColumns) {
		return mDb.query(tb, selectColumns, null, null, null, null, null);
	}
	
	/**
	 * This method is an interface for handling raw SQL queries.
	 * @param queryClause
	 * @param selectionArgs
	 * @return Cursor
	 */
	public Cursor rawQuery(String queryClause, String[] selectionArgs) {
		return mDb.rawQuery(queryClause, selectionArgs);
	}
	
	
	// ������ר�е����ݲ����ӿڣ�����������ݱ��й�
	
	// �ṩ�����ݲ����ӿ�
	// User Table
	// �����û���ɾ���û��������û�����ѯ�û�
	
	/**
	 * This method is used to create/insert new record User record.
	 * @param username
	 * @param password
	 * @param ctime
	 * @return long
	 */
	public long createUser(String username, String password, long ctime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, username);
		initialValues.put(KEY_PASSWORD, password);
		initialValues.put(KEY_CTIME, ctime);
		return mDb.insert(USER, null, initialValues);
	}
	
	/**
	 * This method will delete User record.
	 * @param username
	 * @return boolean
	 */
	public boolean deleteUser(String username) {
		return mDb.delete(USER, KEY_USERNAME + "='" + username + "'", null) > 0;
	}

	/**
	 * This method will update User record.
	 * @param username
	 * @param updateValues
	 * @return boolean
	 */
	public boolean updateUser(String username, ContentValues updateValues) {
		return mDb.update(USER, updateValues, KEY_USERNAME + "='" + username + "'", null) > 0;
	}

	/**
	 * This method will return Cursor holding the specific User record with selected columns.
	 * @param username
	 * @return Cursor
	 * @throws SQLException
	 */
	public Cursor fetchUser(String username, String[] selectColumns) throws SQLException {
		Cursor mCursor =
			mDb.query(true, USER, selectColumns, KEY_USERNAME + "='" + username + "'", null,
					null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	/**
	 * This method will return Cursor holding all the User records with selected columns.
	 * @return Cursor
	 */
	public Cursor fetchAllUsers() {
		return mDb.query(USER, new String[] {KEY_USERNAME, KEY_PASSWORD,
				KEY_CTIME}, null, null, null, null, null);
	}
	
	//�ṩ�����ݲ����ӿ�
	//UserProfile Table
	//�����û���¼��ɾ���û���¼�������û���¼����ѯ�û���¼
	
	/**
	 * This method is used to create/insert new User Profile
	 * @param username
	 * @param nickname
	 * @param email
	 * @param sex
	 * @param age
	 * @param job
	 * @param ctime
	 * @param mtime
	 * @param stime
	 * @return long
	 */
	public long createUserProfile(String nickname, String email, int sex, int age, int job){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NICKNAME, nickname);
		initialValues.put(KEY_EMAIL, email);
		initialValues.put(KEY_SEX, sex);
		initialValues.put(KEY_JOB, job);
		updateRecordTime(initialValues);
		return mDb.insert(USER_PROFILE, null, initialValues);
	}
	
	/**
	 * This method will delete User Profile.
	 * @param username
	 * @return boolean
	 */
	public boolean deleteUserProfile(String username){
		return mDb.delete(USER_PROFILE, KEY_USERNAME + "='" + username + "'", null) > 0;
	} 
	
	/**
	 * This method will update User Profile.
	 * @param username
	 * @param updateValues
	 * @return boolean
	 */
	public boolean updateUserProfile(String username, ContentValues updateValues){
		return mDb.update(USER_PROFILE, updateValues, KEY_USERNAME + "='" + username + "'", null) > 0;
	}
	
	/**
	 * This method will return Cursor holding the specific User Profile with selected columns.
	 * @param username
	 * @param selectColumns
	 * @return Cursor
	 * @throws SQLException
	 */
	public Cursor fetchUserProfile(String username, String[] selectColumns) throws SQLException {
		Cursor mCursor =  
				mDb.query(true, USER_PROFILE, selectColumns, KEY_USERNAME + "='" + username + "'", null, null, null, null, null);
		if (mCursor != null)
			mCursor.moveToFirst();
		return mCursor;
	}
	
	/**
	 * This method will return Cursor holding User Profiles with selected columns.
	 * @param selectColumns
	 * @return Cursor
	 */
	public Cursor fetchAllUserProfiles(String[] selectColumns){
		return mDb.query(USER_PROFILE, selectColumns, null, null, null, null, null);
	}
	
	//�ṩ�����ݲ����ӿ�
	//UserSettings Table
	//�����û����ã�ɾ���û����ã������û����ã���ѯ�û�����
	
	/**
	 * This method is used to create/insert new User Settings.
	 * @param username
	 * @param mode
	 * @param ringLevel
	 * @param alarmType
	 * @param ctime
	 * @param mtime
	 * @param stime
	 * @return long
	 */
	public long createUserSettings(int mode, int ringLevel, int alarmType) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_MODE, mode);
		initialValues.put(KEY_RINGLEVEL, ringLevel);
		initialValues.put(KEY_ALARMTYPE, alarmType);
		updateRecordTime(initialValues);
		return mDb.insert(USER_SETTINGS, null, initialValues);
	}
	
	/**
	 * This method will delete User Settings.
	 * @param username
	 * @return boolean
	 */
	public boolean deleteUserSettings(String username) {
		return mDb.delete(USER_SETTINGS, KEY_USERNAME + "='" + username + "'", null) > 0;
	}
	
	/**
	 * This method will update User Settings with selected columns.
	 * @param username
	 * @param updateValues
	 * @return boolean
	 */
	public boolean updateUserSettings(String username, ContentValues updateValues) {
		return mDb.update(USER_SETTINGS, updateValues, KEY_USERNAME + "='" + username + "'", null) > 0;
	}
	
	public Cursor fetchUserSettings(String username, String[] selectColumns) throws SQLException {
		Cursor mCursor = 
				mDb.query(true, USER_SETTINGS, selectColumns, KEY_USERNAME + "='" + username + "'", null, null, null, null, null);
		if (mCursor != null)
			mCursor.moveToFirst();
		return mCursor;
	}
	
	//�ṩ�����ݲ����ӿ�
	//Todolist Table
	//�����������ɾ������������´��������ѯ��������
	
	/**
	 * This method is used to create new todolist event.
	 * @param title
	 * @param start_time
	 * @param end_time
	 * @param place
	 * @param description
	 * @param event_type
	 * @param repetition
	 * @param reminder
	 * @param priority
	 * @param status
	 * @param ctime
	 * @param mtime
	 * @param stime
	 * @return
	 */
	public long createTodolistEvent(String title, long start_time, long end_time, String place, String description, 
			int event_type, int repetition, int reminder, int priority, int status) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_START, start_time);
		initialValues.put(KEY_END, end_time);
		initialValues.put(KEY_WHERE, place);
		initialValues.put(KEY_DESC, description);
		initialValues.put(KEY_TYPE, event_type);
		initialValues.put(KEY_REPETITION, repetition);
		initialValues.put(KEY_REMINDER, reminder);
		initialValues.put(KEY_PRIORITY, priority);
		initialValues.put(KEY_STATUS, status);
		updateRecordTime(initialValues);;
		return mDb.insert(TODOLIST, null, initialValues);
	}
	
	//�ṩ�����ݲ����ӿ�
	//Collector Table
	//�����ռ������ɾ���ռ�����������ռ��������ѯ�ռ�������
	
	/**
	 * This method is used to create new collector event.
	 * @param title
	 * @param start_time
	 * @param end_time
	 * @param place
	 * @param description
	 * @param event_type
	 * @param repetition
	 * @param reminder
	 * @param priority
	 * @param status
	 * @param ctime
	 * @param mtime
	 * @param stime
	 * @return long
	 */
	public long createCollectorEvent(String title, long start_time, long end_time, String place, String description, 
			int event_type, int repetition, int reminder, int priority, int status) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_START, start_time);
		initialValues.put(KEY_END, end_time);
		initialValues.put(KEY_WHERE, place);
		initialValues.put(KEY_DESC, description);
		initialValues.put(KEY_TYPE, event_type);
		initialValues.put(KEY_REPETITION, repetition);
		initialValues.put(KEY_REMINDER, reminder);
		initialValues.put(KEY_PRIORITY, priority);
		initialValues.put(KEY_STATUS, status);
		updateRecordTime(initialValues);
		return mDb.insert(COLLECTOR, null, initialValues);
	}
	
	//�ṩ�����ݲ����ӿ�
	//Wishlist Table
	//������Ը��ɾ����Ը��������Ը����ѯ��Ը
	
	/**
	 * This method is used to create new wishlist event.
	 * @param title
	 * @param start_time
	 * @param end_time
	 * @param place
	 * @param description
	 * @param event_type
	 * @param repetition
	 * @param reminder
	 * @param priority
	 * @param status
	 * @param ctime
	 * @param mtime
	 * @param stime
	 * @return long
	 */
	public long createWishlistEvent(String title, long start_time, long end_time, String place, String description, 
			int event_type, int repetition, int reminder, int priority, int status) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_START, start_time);
		initialValues.put(KEY_END, end_time);
		initialValues.put(KEY_WHERE, place);
		initialValues.put(KEY_DESC, description);
		initialValues.put(KEY_TYPE, event_type);
		initialValues.put(KEY_REPETITION, repetition);
		initialValues.put(KEY_REMINDER, reminder);
		initialValues.put(KEY_PRIORITY, priority);
		initialValues.put(KEY_STATUS, status);
		updateRecordTime(initialValues);
		return mDb.insert(WISHLIST, null, initialValues);
	}
	
	//�ṩ�����ݲ����ӿ�
	//Record Table
	//������¼��ɾ����¼�����¼�¼����ѯ��¼
	
	/**
	 * This method is used to create new record event.
	 * @param event_type
	 * @param start_time
	 * @param end_time
	 * @param cost_time
	 * @param rating
	 * @param mood
	 * @param status
	 * @param ctime
	 * @param mtime
	 * @param stime
	 * @return long
	 */
	public long createRecordEvent(int event_type, long start_time, long end_time, long cost_time, int rating, int mood, int status) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TYPE, event_type);
		initialValues.put(KEY_START, start_time);
		initialValues.put(KEY_END, end_time);
		initialValues.put(KEY_COST, end_time);
		initialValues.put(KEY_STATUS, status);
		updateRecordTime(initialValues);
		return mDb.insert(RECORD, null, initialValues);
	}
	
	//�ṩ�����ݲ����ӿ�
	//Time_tips Table
	//����tips��ɾ��tips������tips����ѯtips
	
	/**
	 * This method is used to create new time tips.
	 * @param tip_index
	 * @param title
	 * @param content
	 * @return long
	 */
	public long createTimeTips(String tip_index, String title, String content) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_INDEX, tip_index);
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_CONTENT, content);
		return mDb.insert(TIME_TIPS, null, initialValues);
	}
	
	//�ṩ�����ݲ����ӿ�
	//Mood_tips Table
	//����tips��ɾ��tips������tips����ѯtips
	
	/**
	 * This method is used to create new mood tips.
	 * @param tip_index
	 * @param title
	 * @param content
	 * @return long
	 */
	public long createMoodTips(String tip_index, String title, String content) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_INDEX, tip_index);
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_CONTENT, content);
		return mDb.insert(MOOD_TIPS, null, initialValues);
	}
	
	//�ṩ�����ݲ����ӿ�
	//Prime_types Table
	//��ʼ��types������types��ɾ��types������types����ѯtypes
	
	/**
	 * This method is used to initial prime types.
	 */
	public void initPrimeTypes() {
		Log.i("DatabaseUtil","Creating Assigned Prime Types...");
		
		createPrimeTypes("ѧϰ", null);
		createPrimeTypes("����", null);
		createPrimeTypes("�罻", null);
		createPrimeTypes("�˶�", null);
		createPrimeTypes("˼��", null);
		createPrimeTypes("����", null);
		createPrimeTypes("����", null);
		createPrimeTypes("����", null);
		createPrimeTypes("δ��¼", null);
		
		Log.i("DatabaseUtil","Creating Assigned Prime Types Done!");
	}
	
	/**
	 * This method is used to create/insert new Prime Types.
	 * @param username
	 * @param typeName
	 * @param typeIcon
	 * @return long
	 */
	public long createPrimeTypes(String typeName, String typeIcon) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TYPE_NAME, typeName);
		initialValues.put(KEY_TYPE_ICON, typeIcon);
		updateRecordTime(initialValues);
		return mDb.insert(PRIM_TYPES, null, initialValues);
	}
	
	/**
	 * Check whether a prime type existed. 
	 * @param typeName
	 * @return
	 */
	public boolean isPrimeTypeExisted(String typeName) {
		Cursor mCursor = mDb.query(true, PRIM_TYPES, null, KEY_TYPE_NAME + "='"
				+ typeName + "'", null, null, null, null, null);
		if (mCursor.moveToNext()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Used to create a new Prime Type and remember to check duplication.
	 * @param typeName
	 * @param typeIcon
	 * @return long
	 */
	public long newPrimeType(String typeName, String typeIcon) {
		if(!isPrimeTypeExisted(typeName)) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_TYPE_NAME, typeName);
			initialValues.put(KEY_TYPE_ICON, typeIcon);
			updateRecordTime(initialValues);
			return mDb.insert(PRIM_TYPES, null, initialValues);
		} else {
			// Prime Type Already Existed, Return -1
			Log.w("DatabaseUtil.newPrimeType","Already exists!");
			return -1;
		}
	}
	
	/**
	 * 
	 * @param typeName
	 * @return boolean
	 */
	public boolean deletePrimeType(String typeName) {
		// Delete Prime Type
		if(mDb.delete(PRIM_TYPES, KEY_TYPE_NAME + "='" + typeName + "'", null) > 0) {
			// Delete Sub Type Belong to It
			if(!deleteSubTypeBelongTo(typeName)) {
				return false;
			}
//			Cursor subTypeCursor = fetchSubTypesBelongTo(typeName, null);
//			if(subTypeCursor != null) {
//				while(subTypeCursor.moveToNext()) {
//					if(!deleteSubType(subTypeCursor.getColumnName(subTypeCursor.getColumnIndex(KEY_TYPE_NAME)))) {
//						return false;
//					}
//				}
//			}
		} else {
			// Delete Prime Type Error
			return false;
		}
		return true;
		
	}
	
	/**
	 * Used to update a prime type.
	 * @param typeName
	 * @param updateValues
	 * @return boolean
	 */
	public boolean updatePrimeType(String typeName, ContentValues updateValues) {
		String updateTypeName = updateValues.getAsString(KEY_TYPE_NAME);
		
		// Check whether the new type name existed
		if(!isPrimeTypeExisted(updateTypeName)) {
			updateValues.put(KEY_MTIME, System.currentTimeMillis());
			
			if(mDb.update(PRIM_TYPES, updateValues, KEY_TYPE_NAME + "='" + typeName + "'", null) > 0) {
				// Update Sub Type Belong to It
				String newTypeName = updateValues.getAsString(KEY_TYPE_NAME);
				
				ContentValues subTypeUpdateValues = new ContentValues();
				subTypeUpdateValues.put(KEY_TYPE_BELONGTO, newTypeName);
				subTypeUpdateValues.put(KEY_MTIME, System.currentTimeMillis());
				
				if(!updateSubTypeBelongTo(typeName, subTypeUpdateValues)) {
					return false;
				}
//				Cursor subTypeCursor = fetchSubTypesBelongTo(typeName, null);
//				if(subTypeCursor != null) {
//					while(subTypeCursor.moveToNext()) {
//						if(!updateSubType(subTypeCursor.getColumnName(subTypeCursor.getColumnIndex(KEY_TYPE_NAME)), subTypeUpdateValues)) {
//							return false;
//						}
//					}
//				}
			} else {
				// Update Prime Type Error
				return false;
			}
			return true;
		} else {
			// Prime Type Already Existed, Return false
			return false;
		}
	}
	
	/**
	 * Used to fetch Prime Types according to type name.
	 * @param typeName
	 * @param selectColumns
	 * @return Cursor
	 * @throws SQLException
	 */
	public Cursor fetchPrimeTypes(String typeName, String[] selectColumns) throws SQLException {
		Cursor mCursor = 
				mDb.query(true, PRIM_TYPES, selectColumns, KEY_TYPE_NAME + "='" + typeName + "'", null, null, null, null, null);
		if (mCursor != null)
			mCursor.moveToFirst();
		return mCursor;
	}
	
	//�ṩ�����ݲ����ӿ�
	//Prime_types Table
	//����types��ɾ��types������types����ѯtypes
	
	/**
	 * This method is used to initial prime types.
	 */
	public void initSubTypes() {
		Log.i("DatabaseUtil","Creating Assigned Sub Types...");
		
		createSubTypes("רҵ֪ʶ", null, "ѧϰ");
		createSubTypes("����֪ʶ", null, "ѧϰ");
		createSubTypes("��������", null, "ѧϰ");
		createSubTypes("����", null, "ѧϰ");
		
		
		createSubTypes("�滮����", null, "����");
		createSubTypes("�����ĵ�", null, "����");
		createSubTypes("��ϵ�ͻ�", null, "����");
		createSubTypes("��д����", null, "����");
		createSubTypes("����", null, "����");
		
		
		createSubTypes("�绰��ϵ", null, "�罻");
		createSubTypes("���罻��", null, "�罻");
		createSubTypes("����ۻ�", null, "�罻");
		createSubTypes("�������", null, "�罻");
		createSubTypes("����", null, "�罻");
		
		
		createSubTypes("ɢ������", null, "�˶�");
		createSubTypes("�����˶�", null, "�˶�");
		createSubTypes("С���˶�", null, "�˶�");
		createSubTypes("������", null, "�˶�");
		createSubTypes("����", null, "�˶�");
		
		
		createSubTypes("����Ը��", null, "˼��");
		createSubTypes("���ڹ滮", null, "˼��");
		createSubTypes("���ڹ滮", null, "˼��");
		createSubTypes("��������", null, "˼��");
		createSubTypes("����", null, "˼��");
		
		
		createSubTypes("������;", null, "����");
		createSubTypes("��Ӱ����", null, "����");
		createSubTypes("������Ϸ", null, "����");
		createSubTypes("��������", null, "����");
		createSubTypes("����", null, "����");
		
		createSubTypes("�ճ���Ʒ", null, "����");
		createSubTypes("���Ѫƴ", null, "����");
		createSubTypes("���ڲ�Ʒ", null, "����");
		createSubTypes("�������", null, "����");
		createSubTypes("����", null, "����");
		
		createSubTypes("��ͨ·;", null, "����");
		createSubTypes("����", null, "����");
		
		
		createSubTypes("δ��¼", null, "δ��¼");
		
		Log.i("DatabaseUtil","Creating Assigned Sub Types Done!");
	}
	
	/**
	 * This method is used to create/insert new Prime Types.
	 * @param typeName
	 * @param typeIcon
	 * @param typeBelongTo
	 * @return long
	 */
	public long createSubTypes(String typeName, String typeIcon, String typeBelongTo) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TYPE_NAME, typeName);
		initialValues.put(KEY_TYPE_ICON, typeIcon);
		initialValues.put(KEY_TYPE_BELONGTO, typeBelongTo);
		updateRecordTime(initialValues);
		return mDb.insert(SUB_TYPES, null, initialValues);
	}
	
	/**
	 * Check whether a prime type existed. 
	 * @param typeName
	 * @return
	 */
	public boolean isSubTypeExisted(String typeName) {
		Cursor mCursor = mDb.query(true, SUB_TYPES, null, KEY_TYPE_NAME + "='"
				+ typeName + "'", null, null, null, null, null);
		if (mCursor.moveToNext()) {
			return true;
		} else {
			return false;
		}
	}
	
	public long newSubType(String typeName, String typeIcon) {
		if(!isSubTypeExisted(typeName)) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_TYPE_NAME, typeName);
			initialValues.put(KEY_TYPE_ICON, typeIcon);
			updateRecordTime(initialValues);
			return mDb.insert(SUB_TYPES, null, initialValues);
		} else {
			// Prime Type Already Existed, Return -1
			return -1;
		}
	}
	
	/**
	 * This method will delete Sub Type.
	 * @param typeName
	 * @return boolean
	 */
	public boolean deleteSubType(String typeName) {
		return mDb.delete(SUB_TYPES, KEY_TYPE_NAME + "='" + typeName + "'", null) > 0;
	}
	
	/**
	 * Used to delete sub types belong to specified prime type.
	 * @param primeName
	 * @return boolean
	 */
	public boolean deleteSubTypeBelongTo(String primeName) {
		return mDb.delete(SUB_TYPES, KEY_TYPE_BELONGTO + "='" + primeName + "'", null) > 0;
	}
	
	/**
	 * This method will update Sub Types with selected columns.
	 * @param typeName
	 * @param updateValues
	 * @return boolean
	 */
	public boolean updateSubType(String typeName, ContentValues updateValues) {
		String updateTypeName = updateValues.getAsString(KEY_TYPE_NAME);
		
		if(!isSubTypeExisted(updateTypeName)) {
			return mDb.update(SUB_TYPES, updateValues, KEY_TYPE_NAME + "='" + typeName + "'", null) > 0;
		} else {
			// Sub Type Already Existed, Return false
			return false;
		}
	}
	
	/**
	 *  Used to update sub types belong to specified prime type.
	 * @param primeName
	 * @param updateValues
	 * @return boolean
	 */
	public boolean updateSubTypeBelongTo(String primeName, ContentValues updateValues) {
		return mDb.update(SUB_TYPES, updateValues, KEY_TYPE_BELONGTO + "='" + primeName + "'", null) > 0;
	}
	
	/**
	 * Used to fetch Sub Types.
	 * @param typeName
	 * @param selectColumns
	 * @return Cursor
	 * @throws SQLException
	 */
	public Cursor fetchSubTypes(String typeName, String[] selectColumns) throws SQLException {
		Cursor mCursor = 
				mDb.query(true, SUB_TYPES, selectColumns, KEY_TYPE_NAME + "='" + typeName + "'", null, null, null, null, null);
		if (mCursor != null)
			mCursor.moveToFirst();
		return mCursor;
	}
	
	public Cursor fetchSubTypesBelongTo(String primeName, String[] selectColumns) throws SQLException {
		Cursor mCursor = 
				mDb.query(true, SUB_TYPES, selectColumns, KEY_TYPE_BELONGTO + "='" + primeName + "'", null, null, null, null, null);
		if (mCursor != null)
			mCursor.moveToFirst();
		return mCursor;
	}
	
	// �ⲿ����ʵ��
	////����
	//DatabaseUtil dbUtil = new DatabaseUtil(this);
	//dbUtil.open();
	//dbUtil.createUser("user", "password");
	//dbUtil.close();
	//
	////��ѯ
	//DatabaseUtil dbUtil = new DatabaseUtil(this);
	//dbUtil.open();
	//Cursor cursor = dbUtil.fetchAllUsers();
	//if(cursor != null){
	// while(cursor.moveToNext()){
	//	Log.i("User", "User Name: " + cursor.getString(1) +
	//             " Grade " + cursor.getString(2));
	// }
	//}
	//dbUtil.close();
}

