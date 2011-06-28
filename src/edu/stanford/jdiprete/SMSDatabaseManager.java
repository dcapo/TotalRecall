package edu.stanford.jdiprete;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SMSDatabaseManager {
	
	Context context;

	private SQLiteDatabase db;
	private final String SMS_DATABASE_NAME = "sms_database_name";
	private final int SMS_DATABASE_VERSION = 1;
	
	private final String SMS_TABLE_NAME = "sms_database_table";
	private final String SMS_TABLE_ROW_ID = "id";
	private final String SMS_TABLE_ROW_ONE = "address";
	private final String SMS_TABLE_ROW_TWO = "body";
	private final String SMS_TABLE_ROW_THREE = "date";
	private final String SMS_TABLE_ROW_FOUR = "x_coordinate";
	private final String SMS_TABLE_ROW_FIVE = "y_coordinate";
	
	public SMSDatabaseManager(Context context)
	{
		this.context = context;
		SMSDatabaseOpenHelper helper = new SMSDatabaseOpenHelper(context);
		this.db = helper.getWritableDatabase();
	}
	
	public void addRow(String address, String body, String date, String x_coordinate, String y_coordinate)
	{
		ContentValues values = new ContentValues();
		values.put(SMS_TABLE_ROW_ONE, address);
		values.put(SMS_TABLE_ROW_TWO, body);
		values.put(SMS_TABLE_ROW_THREE, date);
		values.put(SMS_TABLE_ROW_FOUR, x_coordinate);
		values.put(SMS_TABLE_ROW_FIVE, y_coordinate);
		
		try
		{
			db.insert(SMS_TABLE_NAME, null, values);
		}catch(Exception e)
		{
			Log.e("Database Error", e.toString());
			e.printStackTrace();
		}	
	}
	
	public void deleteRow(long rowID)
	{
		try
		{
			db.delete(SMS_TABLE_NAME, SMS_TABLE_ROW_ID + "=" + rowID, null);
		}
		catch (Exception e)
		{
			Log.e("Database Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateRow(long rowID, String address, String body, String date, String x_coordinate, String y_coordinate)
	{
		ContentValues values = new ContentValues();
		values.put(SMS_TABLE_ROW_ONE, address);
		values.put(SMS_TABLE_ROW_TWO, body);
		values.put(SMS_TABLE_ROW_THREE, date);
		values.put(SMS_TABLE_ROW_FOUR, x_coordinate);
		values.put(SMS_TABLE_ROW_FIVE, y_coordinate);
		
		try
		{
			db.update(SMS_TABLE_NAME, values, SMS_TABLE_ROW_ID + "=" + rowID, null);	
		}
		catch (Exception e)
		{
			Log.e("Database Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public ArrayList<Object> getRowAsArray(long rowID)
	{
		ArrayList<Object> rowArray = new ArrayList<Object>();
		Cursor cursor;
		
		try
		{
			cursor = db.query(SMS_TABLE_NAME, 
					new String[] { SMS_TABLE_ROW_ID, SMS_TABLE_ROW_ONE, SMS_TABLE_ROW_TWO, SMS_TABLE_ROW_THREE, SMS_TABLE_ROW_FOUR, SMS_TABLE_ROW_FIVE }, 
					SMS_TABLE_ROW_ID + "=" + rowID, 
					null, null, null, null);
			
			cursor.moveToFirst();
			
			if (!cursor.isAfterLast())
			{
				do
				{
					rowArray.add(cursor.getLong(0));
					rowArray.add(cursor.getString(1));
					rowArray.add(cursor.getString(2));
					rowArray.add(cursor.getString(3));
					rowArray.add(cursor.getString(4));
					rowArray.add(cursor.getString(5));
				}
				while (cursor.moveToNext());
			}
			cursor.close();
		}
		catch (SQLException e)
		{
			Log.e("Database Error", e.toString());
			e.printStackTrace();
		}
		return rowArray;
	}
	
	public ArrayList<ArrayList<Object> > getAllRowsAsArray()
	{
		ArrayList<ArrayList<Object> > dataArrays = new ArrayList<ArrayList<Object> >();
		Cursor cursor;
		
		try
		{
			cursor = db.query(SMS_TABLE_NAME, 
					new String[] { SMS_TABLE_ROW_ID, SMS_TABLE_ROW_ONE, SMS_TABLE_ROW_TWO, SMS_TABLE_ROW_THREE, SMS_TABLE_ROW_FOUR, SMS_TABLE_ROW_FIVE }, 
					null, null, null, null, null);
			
			cursor.moveToFirst();
			
			if (!cursor.isAfterLast())
			{
				do
				{
					ArrayList<Object> dataList = new ArrayList<Object>();
					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getString(1));
					dataList.add(cursor.getString(2));
					dataList.add(cursor.getString(3));
					dataList.add(cursor.getString(4));
					dataList.add(cursor.getString(5));
					
					dataArrays.add(dataList);
				}
				while (cursor.moveToNext());
			}
			cursor.close();
		}
		catch (SQLException e)
		{
			Log.e("Database Error", e.toString());
			e.printStackTrace();
		}
		
		return dataArrays;
	}
	
	
	private class SMSDatabaseOpenHelper extends SQLiteOpenHelper
	{

		public SMSDatabaseOpenHelper(Context context) {
			super(context, SMS_DATABASE_NAME, null, SMS_DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String newTableQueryString = "CREATE TABLE " + SMS_TABLE_NAME + 
			" (" + SMS_TABLE_ROW_ID + " integer primary key autoincrement not null, " + 
			SMS_TABLE_ROW_ONE + " TEXT, " + SMS_TABLE_ROW_TWO + " TEXT, " + SMS_TABLE_ROW_THREE + " TEXT, " + SMS_TABLE_ROW_FOUR + " TEXT, " + SMS_TABLE_ROW_FIVE + " TEXT" + ");";
			
			db.execSQL(newTableQueryString);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}



