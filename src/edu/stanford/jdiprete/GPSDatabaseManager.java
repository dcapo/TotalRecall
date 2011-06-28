package edu.stanford.jdiprete;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class GPSDatabaseManager {
	Context context;
	
	private SQLiteDatabase db;
	
	private final String GPS_DATABASE_NAME = "gps_database_name";
	private final int GPS_DATABASE_VERSION = 1;
	
	private final String GPS_TABLE_NAME = "gps_database_table";
	private final String GPS_TABLE_ROW_ID = "id";
	private final String GPS_TABLE_ROW_ONE = "time";
	private final String GPS_TABLE_ROW_TWO = "x_coordinate";
	private final String GPS_TABLE_ROW_THREE = "y_coordinate";

	
	public GPSDatabaseManager(Context context)
	{
		this.context = context;
		GPSDatabaseOpenHelper helper = new GPSDatabaseOpenHelper(context);
		this.db = helper.getWritableDatabase();
	}
	
	public void addRow(String time, String x_coordinate, String y_coordinate)
	{
		ContentValues values = new ContentValues();
		values.put(GPS_TABLE_ROW_ONE, time);
		values.put(GPS_TABLE_ROW_TWO, x_coordinate);
		values.put(GPS_TABLE_ROW_THREE, y_coordinate);
		
		try
		{
			db.insert(GPS_TABLE_NAME, null, values);
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
			db.delete(GPS_TABLE_NAME, GPS_TABLE_ROW_ID + "=" + rowID, null);
		}
		catch (Exception e)
		{
			Log.e("Database Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateRow(long rowID, String time, String x_coordinate, String y_coordinate)
	{
		ContentValues values = new ContentValues();
		values.put(GPS_TABLE_ROW_ONE, time);
		values.put(GPS_TABLE_ROW_TWO, x_coordinate);
		values.put(GPS_TABLE_ROW_THREE, y_coordinate);
		
		try
		{
			db.update(GPS_TABLE_NAME, values, GPS_TABLE_ROW_ID + "=" + rowID, null);	
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
			cursor = db.query(GPS_TABLE_NAME, 
					new String[] { GPS_TABLE_ROW_ID, GPS_TABLE_ROW_ONE, GPS_TABLE_ROW_TWO, GPS_TABLE_ROW_THREE }, 
					GPS_TABLE_ROW_ID + "=" + rowID, 
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
			cursor = db.query(GPS_TABLE_NAME, 
					new String[] { GPS_TABLE_ROW_ID, GPS_TABLE_ROW_ONE, GPS_TABLE_ROW_TWO, GPS_TABLE_ROW_THREE }, 
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
	
	
	private class GPSDatabaseOpenHelper extends SQLiteOpenHelper
	{

		public GPSDatabaseOpenHelper(Context context) {
			super(context, GPS_DATABASE_NAME, null, GPS_DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String newTableQueryString = "CREATE TABLE " + GPS_TABLE_NAME + 
			" (" + GPS_TABLE_ROW_ID + " integer primary key autoincrement not null, " + 
			GPS_TABLE_ROW_ONE + " TEXT, " + GPS_TABLE_ROW_TWO + " TEXT, " + GPS_TABLE_ROW_THREE + " TEXT" + ");";
			
			db.execSQL(newTableQueryString);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
