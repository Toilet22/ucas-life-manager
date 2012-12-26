package com.calm.lifemanager;

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
	private static final String DATABASE_NAME = "lifemanager.db";

	/**
	 * Database Version
	 */
	private static final int DATABASE_VERSION = 1;

	/**
	 * Table Name
	 */
	private static final String USER = "tb_user";

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
	private static final String USER_PROFILE = "tb_user_profile";

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
	private static final String USER_SETTINGS = "tb_user_settings";

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
	private static final String TODOLIST = "tb_todolist";

	/**
	 * Table columns
	 */
	public static final String KEY_TITLE = "title";
	public static final String KEY_START = "start";
	public static final String KEY_END = "end";
	public static final String KEY_WHERE = "where";
	public static final String KEY_DESC = "desc";
	public static final String KEY_TYPE = "type";
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
	private static final String COLLECTOR = "tb_collector";

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
	private static final String RECORD = "tb_record";

	/**
	 * Table columns
	 */
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
		+ KEY_RATING + " SMALLINT, "
		+ KEY_MOOD + " SMALLINT, "
		+ KEY_STATUS + " SMALLINT, "
		+ KEY_CTIME + " INTEGER not null, "
		+ KEY_MTIME + " INTEGER not null, "
		+ KEY_STIME + " INTEGER not null);";
	
	/**
	 * Table Name
	 */
	private static final String TIME_TIPS = "tb_time_tips";

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
	private static final String MOOD_TIPS = "tb_mood_tips";

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
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		/**
		 * onCreate method is called for the 1st time when database doesn't exists.
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG, "Creating DataBase: " + DATABASE_NAME);
			
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

	// 提供的数据操作接口
	// User Table
	// 新增用户，删除用户，更新用户，查询用户
	/**
	 * This method is used to create/insert new record User record.
	 * @param username
	 * @param password
	 * @param ctime
	 * @return long
	 */
	public long createUser(String username, String password, int ctime) {
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
	public boolean deleteStudent(String username) {
		return mDb.delete(USER, KEY_USERNAME + "=" + username, null) > 0;
	}

	/**
	 * This method will return Cursor holding all the User records.
	 * @return Cursor
	 */
	public Cursor fetchAllUsers() {
		return mDb.query(USER, new String[] {KEY_USERNAME, KEY_PASSWORD,
				KEY_CTIME}, null, null, null, null, null);
	}

	/**
	 * This method will return Cursor holding the specific User record.
	 * @param username
	 * @return Cursor
	 * @throws SQLException
	 */
	public Cursor fetchUser(String username) throws SQLException {
		Cursor mCursor =
			mDb.query(true, USER, new String[] {KEY_USERNAME,
					KEY_PASSWORD, KEY_CTIME}, KEY_USERNAME + "=" + username, null,
					null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	/**
	 * This method will update User record.
	 * @param username
	 * @param password
	 * @param ctime
	 * @return boolean
	 */
	public boolean updateUser(String username, String password, int ctime) {
		ContentValues args = new ContentValues();
		args.put(KEY_USERNAME, username);
		args.put(KEY_PASSWORD, password);
		return mDb.update(USER, args, KEY_USERNAME + "=" + username, null) > 0;
	}
	
	//提供的数据操作接口
	//UserProfile Table
	//新增用户记录，删除用户记录，更新用户记录，查询用户记录
	
	public long createUserProfile(){
		return 0;
	}
	
	public boolean deleteUserProfile(){
		return true;
	} 
	
	public boolean updateUserProfile(){
		return true;
	}
	
	public Cursor fetchUserProfile(String username) throws SQLException {
		Cursor dummy = null;
		return dummy;
	} 
	
	public Cursor fetchAllUserProfiles(){
		Cursor dummy = null;
		return dummy;
	}
	
	//提供的数据操作接口
	//UserSettings Table
	//新增用户设置，删除用户设置，更新用户设置，查询用户设置
	
	//提供的数据操作接口
	//Todolist Table
	//新增代办事项，删除代办事项，更新代办事项，查询代办事项
	
	//提供的数据操作接口
	//Collector Table
	//新增收集箱事项，删除收集箱事项，更新收集箱事项，查询收集箱事项
	
	//提供的数据操作接口
	//Wishlist Table
	//新增心愿，删除心愿，更新心愿，查询心愿
	
	//提供的数据操作接口
	//Record Table
	//新增记录，删除记录，更新记录，查询记录
	
	//提供的数据操作接口
	//Time_tips Table
	//新增tips，删除tips，更新tips，查询tips
	
	//提供的数据操作接口
	//Mood_tips Table
	//新增tips，删除tips，更新tips，查询tips
	
	// 外部调用实例
	////插入
	//DatabaseUtil dbUtil = new DatabaseUtil(this);
	//dbUtil.open();
	//dbUtil.createUser("user", "password");
	//dbUtil.close();
	//
	////查询
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

