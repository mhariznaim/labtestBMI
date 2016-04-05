package com.example.labtest3454;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbFuel extends SQLiteOpenHelper {

	
	public static final String dbName = "dbFuel";
	public static final String tblName = "tblFuelInfo";
	public static final String colCurrentPrice = "curr_price";
	public static final String colPurchasePrice = "purchase_price";
	public static final String colStatus = "liter_status";
	
	
	
	public DbFuel(Context context ) {
		 super(context,dbName,null,1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS "+ tblName + " ("+ colCurrentPrice +" varchar, "+ colPurchasePrice +" varchar, "+ colStatus +" varchar ); ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
        db.execSQL("DROP Table if exists "+ tblName);
	}
	
	public int fnTotalRow()
	{
	   int intRow;
	   SQLiteDatabase db = this.getReadableDatabase();
	   intRow = (int) DatabaseUtils.queryNumEntries(db, tblName);
	   
	   return intRow;	
		
	}
	
	public void fnExecuteSql(String strSql, Context appContext)
	{
		try {
			
			SQLiteDatabase db = this.getReadableDatabase();
			db.execSQL(strSql);
			
		 
		} catch (Exception e) {
			 Log.d("unable to run query", "error!");
		}
		
		
	}
	public void fnClearData()
	{
		String strSql = "Delete from "+ tblName;
		this.getReadableDatabase().execSQL(strSql);
		
	}
}
