package com.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "friends";
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_TABLE = "highscore";
	public static final String KEY_ID = "userid";
	public static final String KEY_NAME = "name";
	public static final String KEY_SCORE = "score";
	static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "+DATABASE_TABLE
			+"("+KEY_ID+" integer primary key, "+KEY_NAME+" text not null, "+KEY_SCORE+" integer not null);";
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			db.execSQL(DATABASE_CREATE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		try{
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			onCreate(db);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
