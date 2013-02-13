package com.calm.lifemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract.Columns;
import android.util.Log;

public class DatabaseUtil{

	private static final String TAG = "DatabaseUtil";

	/**
	 * Database Name
	 */
	//private static final String DATABASE_NAME = "lifemanager.db";
	public static String dbName = "default_user.db";

	/**
	 * Database Version
	 */
	//private static final int DATABASE_VERSION = 1;
	public static int dbVersion = 1;

	/**
	 * Table Name
	 */
	public static final String USER = "tb_user";

	/**
	 * Table columns
	 */
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_CTIME = "ctime";
	public static final String KEY_MTIME = "mtime";
	public static final String KEY_STIME = "stime";

	/**
	 * Database creation SQL statement
	 */
	private static final String CREATE_USER_TABLE =
		"create table " + USER + " ("
	    + KEY_USERNAME + " VARCHAR(30) not null, "
		+ KEY_PASSWORD + " VARCHAR(16) not null, " 
	    + KEY_CTIME + " INTEGER not null);";

	/**
	 * Table Name
	 */
	public static final String USER_PROFILE = "tb_user_profile";

	/**
	 * Table columns
	 */
	public static final String KEY_NICKNAME = "nickname";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_SEX = "sex";
	public static final String KEY_AGE = "age";
	public static final String KEY_JOB = "job";

	/**
	 * Database creation SQL statement
	 */
	private static final String CREATE_USER_PROFILE_TABLE =
		"create table " + USER_PROFILE + " ("
	    + KEY_USERNAME + " VARCHAR(30) not null, "
		+ KEY_NICKNAME + " VARCHAR(30), " 
		+ KEY_EMAIL + " VARCHAR(30), " 
		+ KEY_SEX + " SMALLINT, " 
		+ KEY_AGE + " SMALLINT, "
		+ KEY_JOB + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/**
	 * Table Name
	 */
	public static final String USER_SETTINGS = "tb_user_settings";

	/**
	 * Table columns
	 */
	public static final String KEY_MODE = "mode";
	public static final String KEY_RINGLEVEL = "ringlevel";
	public static final String KEY_ALARMTYPE = "alarmtype";

	/**
	 * Database creation SQL statement
	 */
	private static final String CREATE_USER_SETTINGS_TABLE =
		"create table " + USER_SETTINGS + " (" 
	    + KEY_USERNAME + " VARCHAR(30) not null, "
		+ KEY_MODE + " SMALLINT, " 
		+ KEY_RINGLEVEL + " SMALLINT, "
		+ KEY_ALARMTYPE + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	
	/**
	 * Table Name
	 */
	public static final String TODOLIST = "tb_todolist";

	/**
	 * Table columns
	 */
	public static final String KEY_TITLE = "title";
	public static final String KEY_START = "start_time";
	public static final String KEY_END = "end_time";
	public static final String KEY_WHERE = "place";
	public static final String KEY_DESC = "description";
	public static final String KEY_TYPE = "event_type";
	public static final String KEY_REPETITION = "repetition";
	public static final String KEY_REMINDER = "reminder";
	public static final String KEY_PRIORITY = "priority";
	public static final String KEY_STATUS = "status";

	/**
	 * Database creation SQL statement
	 */
	private static final String CREATE_TODOLIST_TABLE =
		"create table " + TODOLIST + " (" 
	    + KEY_TITLE + " text not null, "
		+ KEY_START + " INTEGER, " 
		+ KEY_END + " INTEGER, " 
		+ KEY_DESC + " text, " 
		+ KEY_TYPE + " SMALLINT, "
		+ KEY_REPETITION + " SMALLINT, "
		+ KEY_REMINDER + " SMALLINT, "
		+ KEY_PRIORITY + " SMALLINT, "
		+ KEY_STATUS + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/**
	 * Table Name
	 */
	public static final String COLLECTOR = "tb_collector";

	/**
	 * Table columns
	 */

	/**
	 * Database creation SQL statement
	 */
	private static final String CREATE_COLLECTOR_TABLE =
		"create table " + COLLECTOR + " (" 
	    + KEY_TITLE + " text not null, "
		+ KEY_START + " INTEGER, " 
		+ KEY_END + " INTEGER, " 
		+ KEY_DESC + " text, " 
		+ KEY_TYPE + " SMALLINT, "
		+ KEY_REPETITION + " SMALLINT, "
		+ KEY_REMINDER + " SMALLINT, "
		+ KEY_PRIORITY + " SMALLINT, "
		+ KEY_STATUS + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/**
	 * Table Name
	 */
	private static final String WISHLIST = "tb_wishlist";

	/**
	 * Table columns
	 */

	/**
	 * Database creation SQL statement
	 */
	private static final String CREATE_WISHLIST_TABLE =
		"create table " + WISHLIST + " (" 
	    + KEY_TITLE + " text not null, "
		+ KEY_START + " INTEGER, " 
		+ KEY_END + " INTEGER, " 
		+ KEY_DESC + " text, " 
		+ KEY_TYPE + " SMALLINT, "
		+ KEY_REPETITION + " SMALLINT, "
		+ KEY_REMINDER + " SMALLINT, "
		+ KEY_PRIORITY + " SMALLINT, "
		+ KEY_STATUS + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	
	/**
	 * Table Name
	 */
	public static final String RECORD = "tb_record";

	/**
	 * Table columns
	 */
	public static final String KEY_COST = "cost_time";
	public static final String KEY_RATING = "rating";
	public static final String KEY_MOOD = "mood";
	
	/**
	 * Database creation SQL statement
	 */
	private static final String CREATE_RECORD_TABLE =
		"create table " + RECORD + " (" 
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
	
	/**
	 * Table Name
	 */
	public static final String TIME_TIPS = "tb_time_tips";

	/**
	 * Table columns
	 */
	public static final String KEY_CONTENT = "content";
	public static final String KEY_INDEX = "tip_index";
	
	/**
	 * Database creation SQL statement
	 */
	private static final String CREATE_TIME_TIPS_TABLE =
		"create table " + TIME_TIPS + " ("
		+ KEY_TITLE + " text not null, "
		+ KEY_CONTENT + " text not null, "
		+ KEY_INDEX + " VARCHAR(50) not null);";
	
	
	/**
	 * Table Name
	 */
	public static final String MOOD_TIPS = "tb_mood_tips";

	/**
	 * Table columns
	 */
	
	/**
	 * Database creation SQL statement
	 */
	private static final String CREATE_MOOD_TIPS_TABLE =
		"create table " + MOOD_TIPS + " (" 
		+ KEY_TITLE + " text not null, "
		+ KEY_CONTENT + " text not null, "
		+ KEY_INDEX + " VARCHAR(50) not null);";
	
	/**
	 * Context
	 */
	private final Context mCtx;

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	/**
	 * Inner private class. Database Helper class for creating and updating database.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			//super(context, DATABASE_NAME, null, DATABASE_VERSION);
			super(context, dbName, null, dbVersion);
		}
		
		/**
		 * onCreate method is called for the 1st time when database doesn't exists.
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

	// �ṩ��ͨ�����ݲ����ӿ�
	// All Tables
	// ������¼��ɾ����¼�����¼�¼����ѯ��¼
	
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
	
	/**
	 * This method is a generic interface used to fetch all records from the specified table.
	 * @param tb
	 * @param selectColumns
	 * @return
	 */
	public Cursor fetchAllData(String tb,String[] selectColumns) {
		return mDb.query(tb, selectColumns, null, null, null, null, null);
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
	public long createUserProfile(String username, String nickname, String email, int sex, int age, int job, long ctime, long mtime, long stime){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, username);
		initialValues.put(KEY_NICKNAME, nickname);
		initialValues.put(KEY_EMAIL, email);
		initialValues.put(KEY_SEX, sex);
		initialValues.put(KEY_JOB, job);
		initialValues.put(KEY_CTIME, ctime);
		initialValues.put(KEY_MTIME, mtime);
		initialValues.put(KEY_STIME, stime);
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
	public long createUserSettings(String username, int mode, int ringLevel, int alarmType, long ctime, long mtime, long stime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, username);
		initialValues.put(KEY_MODE, mode);
		initialValues.put(KEY_RINGLEVEL, ringLevel);
		initialValues.put(KEY_ALARMTYPE, alarmType);
		initialValues.put(KEY_CTIME, ctime);
		initialValues.put(KEY_MTIME, mtime);
		initialValues.put(KEY_STIME, stime);
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
	 * @param username
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
	public long createTodolistEvent(String username, String title, long start_time, long end_time, String place, String description, 
			int event_type, int repetition, int reminder, int priority, int status, long ctime, long mtime, long stime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, username);
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
		initialValues.put(KEY_CTIME, ctime);
		initialValues.put(KEY_MTIME, mtime);
		initialValues.put(KEY_STIME, stime);
		return mDb.insert(TODOLIST, null, initialValues);
	}
	
	//�ṩ�����ݲ����ӿ�
	//Collector Table
	//�����ռ������ɾ���ռ�����������ռ��������ѯ�ռ�������
	
	/**
	 * This method is used to create new collector event.
	 * @param username
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
	public long createCollectorEvent(String username, String title, long start_time, long end_time, String place, String description, 
			int event_type, int repetition, int reminder, int priority, int status, long ctime, long mtime, long stime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, username);
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
		initialValues.put(KEY_CTIME, ctime);
		initialValues.put(KEY_MTIME, mtime);
		initialValues.put(KEY_STIME, stime);
		return mDb.insert(COLLECTOR, null, initialValues);
	}
	
	//�ṩ�����ݲ����ӿ�
	//Wishlist Table
	//������Ը��ɾ����Ը��������Ը����ѯ��Ը
	
	/**
	 * This method is used to create new wishlist event.
	 * @param username
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
	public long createWishlistEvent(String username, String title, long start_time, long end_time, String place, String description, 
			int event_type, int repetition, int reminder, int priority, int status, long ctime, long mtime, long stime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, username);
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
		initialValues.put(KEY_CTIME, ctime);
		initialValues.put(KEY_MTIME, mtime);
		initialValues.put(KEY_STIME, stime);
		return mDb.insert(WISHLIST, null, initialValues);
	}
	
	//�ṩ�����ݲ����ӿ�
	//Record Table
	//������¼��ɾ����¼�����¼�¼����ѯ��¼
	
	/**
	 * This method is used to create new record event.
	 * @param username
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
	public long createRecordEvent(String username, int event_type, long start_time, long end_time, long cost_time, int rating, int mood, int status, long ctime, long mtime, long stime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, username);
		initialValues.put(KEY_TYPE, event_type);
		initialValues.put(KEY_START, start_time);
		initialValues.put(KEY_END, end_time);
		initialValues.put(KEY_COST, end_time);
		initialValues.put(KEY_STATUS, status);
		initialValues.put(KEY_CTIME, ctime);
		initialValues.put(KEY_MTIME, mtime);
		initialValues.put(KEY_STIME, stime);
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

