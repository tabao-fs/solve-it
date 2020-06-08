package com.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbUtils {
	public SQLiteDatabase db;
	Context dbContext;
	DbHelper dbHelper;
	
	public int topScorers = 5;
	String[] name = new String[topScorers];
	String[] score = new String[topScorers];
	int[] numScore = new int[topScorers];
	int[] scoreFromXmlToDb = new int[topScorers];
	String[] setHighScore = new String[topScorers];
	
	public boolean noResults=false;
	
	public DbUtils(){
		
	}
	
	public DbUtils(Context context){
		this.dbContext = context;
	}

	public DbUtils open() throws SQLException{
		dbHelper = new DbHelper(dbContext);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public int[] getNumScore(){
		return this.numScore;
	}
	
	public void search(){
		int index=0;
		try{
			Cursor c = db.rawQuery("SELECT * FROM "+ DbHelper.DATABASE_TABLE+" ORDER BY "+DbHelper.KEY_SCORE+" DESC;", null);
			if(c.moveToFirst()){
				do{
					this.name[index] = c.getString(c.getColumnIndex(DbHelper.KEY_NAME));
					this.score[index] = c.getString(c.getColumnIndex(DbHelper.KEY_SCORE));
					this.numScore[index] = Integer.parseInt(this.score[index]);
					setHighScore[index] = String.format("%s\t|\t%s\t|\t%s\n", (index+1), this.name[index], this.score[index]);
					++index;
				} while (c.moveToNext() && index<setHighScore.length);
			}
			for(int idx=0;idx<setHighScore.length;idx++){
				if(setHighScore[idx]==null){
					setHighScore[idx]="";
				}
			}
		} catch (NullPointerException e){
			noResults=true;
		}
	}
	
	public String display() {
		search();
		return combine(setHighScore);
	}
	
	public String createXml(){
		String xmlStart = "<?xml version=\"1.0\" encoding=\"utf-8\"?><friends>";
//		String xmlStart = "<?xml%20version=\"1.0\"%20encoding=\"utf-8\"?><friends>";
		String xmlEnd ="</friends>";
		String[] xmlData = new String[topScorers];
		String completeXml="";
		
		for(int index=0; index<xmlData.length; index++){
			xmlData[index]="<highscore><name>"+this.name[index]+"</name>"+"<score>"+this.score[index]+"</score></highscore>";
		}
		
		for(int idx=0;idx<xmlData.length;idx++){
			if(xmlData[idx]==null){
				xmlData[idx]="";
			}
		}
		
		String[] orgXml = {xmlStart, combine(xmlData), xmlEnd};
		completeXml = combine(orgXml);
		return completeXml;
	}
	
	public String combine(String[] args){
		StringBuilder builder = new StringBuilder();
        for(String s : args) {
            builder.append(s);
        }
        String txt = builder.toString();
		return txt;
	}
	
	public long insert(String name, int score){
		ContentValues initialValues = new ContentValues();
		initialValues.put(DbHelper.KEY_NAME, name);
		initialValues.put(DbHelper.KEY_SCORE, score);
		
		return db.insert(DbHelper.DATABASE_TABLE, null, initialValues);
	}
	
	public int update(String name, int score) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.KEY_SCORE, score);
		
		return db.update(DbHelper.DATABASE_TABLE, values, DbHelper.KEY_NAME+" = "+name, null);
	}
	
	public void createTable(){
		try{
			db.execSQL(DbHelper.DATABASE_CREATE);
		} catch(Exception e){
			System.out.print(e);
		}
	}
	
	public void dropTable() {
		try{
			db.execSQL("DROP TABLE IF EXISTS "+DbHelper.DATABASE_TABLE);
		} catch(Exception e) {
			System.out.print(e);
		}
	}
	
}
