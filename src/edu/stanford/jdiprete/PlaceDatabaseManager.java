package edu.stanford.jdiprete;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlaceDatabaseManager {
Context context;
	
	private SQLiteDatabase db;
	
	private final String PLACE_DATABASE_NAME = "PLACE_database_name";
	private final int PLACE_DATABASE_VERSION = 1;
	
	private final String PLACE_TABLE_NAME = "PLACE_database_table";
	private final String PLACE_TABLE_ROW_ID = "id";
	private final String PLACE_TABLE_ROW_ONE = "start_time";
	private final String PLACE_TABLE_ROW_TWO = "end_time";
	private final String PLACE_TABLE_ROW_THREE = "name";
	private final String PLACE_TABLE_ROW_FOUR = "x_coordinate";
	private final String PLACE_TABLE_ROW_FIVE = "y_coordinate";

	
	public PlaceDatabaseManager(Context context)
	{
		this.context = context;
		PLACEDatabaseOpenHelper helper = new PLACEDatabaseOpenHelper(context);
		this.db = helper.getWritableDatabase();
	}
	
	public void addRow(long start, long end, String name, String x_coordinate, String y_coordinate)
	{
		ContentValues values = new ContentValues();
		values.put(PLACE_TABLE_ROW_ONE, start);
		values.put(PLACE_TABLE_ROW_TWO, end);
		values.put(PLACE_TABLE_ROW_THREE, name);
		values.put(PLACE_TABLE_ROW_FOUR, x_coordinate);
		values.put(PLACE_TABLE_ROW_FIVE, y_coordinate);
		
		try
		{
			db.insert(PLACE_TABLE_NAME, null, values);
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
			db.delete(PLACE_TABLE_NAME, PLACE_TABLE_ROW_ID + "=" + rowID, null);
		}
		catch (Exception e)
		{
			Log.e("Database Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateRow(long rowID, long start, long end, String name, String x_coordinate, String y_coordinate)
	{
		ContentValues values = new ContentValues();
		values.put(PLACE_TABLE_ROW_ONE, start);
		values.put(PLACE_TABLE_ROW_TWO, end);
		values.put(PLACE_TABLE_ROW_THREE, name);
		values.put(PLACE_TABLE_ROW_FOUR, x_coordinate);
		values.put(PLACE_TABLE_ROW_FIVE, y_coordinate);
		
		
		try
		{
			db.update(PLACE_TABLE_NAME, values, PLACE_TABLE_ROW_ID + "=" + rowID, null);	
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
			cursor = db.query(PLACE_TABLE_NAME, 
					new String[] { PLACE_TABLE_ROW_ID, PLACE_TABLE_ROW_ONE, PLACE_TABLE_ROW_TWO, PLACE_TABLE_ROW_THREE, PLACE_TABLE_ROW_FOUR, PLACE_TABLE_ROW_FIVE }, 
					PLACE_TABLE_ROW_ID + "=" + rowID, 
					null, null, null, null);
			
			cursor.moveToFirst();
			
			if (!cursor.isAfterLast())
			{
				do
				{
					rowArray.add(cursor.getLong(0));
					rowArray.add(cursor.getLong(1));
					rowArray.add(cursor.getLong(2));
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
			cursor = db.query(PLACE_TABLE_NAME, 
					new String[] { PLACE_TABLE_ROW_ID, PLACE_TABLE_ROW_ONE, PLACE_TABLE_ROW_TWO, PLACE_TABLE_ROW_THREE, PLACE_TABLE_ROW_FOUR, PLACE_TABLE_ROW_FIVE }, 
					null, null, null, null, null);
			
			cursor.moveToFirst();
			
			if (!cursor.isAfterLast())
			{
				do
				{
					ArrayList<Object> dataList = new ArrayList<Object>();
					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getLong(1));
					dataList.add(cursor.getLong(2));
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
	
	
	private class PLACEDatabaseOpenHelper extends SQLiteOpenHelper
	{

		public PLACEDatabaseOpenHelper(Context context) {
			super(context, PLACE_DATABASE_NAME, null, PLACE_DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String newTableQueryString = "CREATE TABLE " + PLACE_TABLE_NAME + 
			" (" + PLACE_TABLE_ROW_ID + " integer primary key autoincrement not null, " + 
			PLACE_TABLE_ROW_ONE + " TEXT, " + PLACE_TABLE_ROW_TWO + " TEXT, " + PLACE_TABLE_ROW_THREE + " TEXT, " + PLACE_TABLE_ROW_FOUR + " TEXT, " + PLACE_TABLE_ROW_FIVE + " TEXT" + ");";
			
			db.execSQL(newTableQueryString);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
