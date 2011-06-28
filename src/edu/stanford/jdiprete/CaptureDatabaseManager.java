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

public class CaptureDatabaseManager {
	Context context;
	
	private SQLiteDatabase db;
	
	private final String CAPTURE_DATABASE_NAME = "CAPTURE_database_name";
	private final int CAPTURE_DATABASE_VERSION = 1;
	
	private final String CAPTURE_TABLE_NAME = "CAPTURE_database_table";
	private final String CAPTURE_TABLE_ROW_ID = "id";
	private final String CAPTURE_TABLE_ROW_ONE = "start_time";
	private final String CAPTURE_TABLE_ROW_TWO = "end_time";
	private final String CAPTURE_TABLE_ROW_THREE = "label";

	
	public CaptureDatabaseManager(Context context)
	{
		this.context = context;
		CaptureDatabaseOpenHelper helper = new CaptureDatabaseOpenHelper(context);
		this.db = helper.getWritableDatabase();
	}
	
	public void addRow(long start, long end, String label)
	{
		ContentValues values = new ContentValues();
		values.put(CAPTURE_TABLE_ROW_ONE, start);
		values.put(CAPTURE_TABLE_ROW_TWO, end);
		values.put(CAPTURE_TABLE_ROW_THREE, label);
		
		try
		{
			db.insert(CAPTURE_TABLE_NAME, null, values);
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
			db.delete(CAPTURE_TABLE_NAME, CAPTURE_TABLE_ROW_ID + "=" + rowID, null);
		}
		catch (Exception e)
		{
			Log.e("Database Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateRow(long rowID, long start, long end, String label)
	{
		ContentValues values = new ContentValues();
		values.put(CAPTURE_TABLE_ROW_ONE, start);
		values.put(CAPTURE_TABLE_ROW_TWO, end);
		values.put(CAPTURE_TABLE_ROW_THREE, label);
		
		try
		{
			db.update(CAPTURE_TABLE_NAME, values, CAPTURE_TABLE_ROW_ID + "=" + rowID, null);	
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
			cursor = db.query(CAPTURE_TABLE_NAME, 
					new String[] { CAPTURE_TABLE_ROW_ID, CAPTURE_TABLE_ROW_ONE, CAPTURE_TABLE_ROW_TWO, CAPTURE_TABLE_ROW_THREE }, 
					CAPTURE_TABLE_ROW_ID + "=" + rowID, 
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
			cursor = db.query(CAPTURE_TABLE_NAME, 
					new String[] { CAPTURE_TABLE_ROW_ID, CAPTURE_TABLE_ROW_ONE, CAPTURE_TABLE_ROW_TWO, CAPTURE_TABLE_ROW_THREE }, 
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
	
	
	private class CaptureDatabaseOpenHelper extends SQLiteOpenHelper
	{

		public CaptureDatabaseOpenHelper(Context context) {
			super(context, CAPTURE_DATABASE_NAME, null, CAPTURE_DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String newTableQueryString = "CREATE TABLE " + CAPTURE_TABLE_NAME + 
			" (" + CAPTURE_TABLE_ROW_ID + " integer primary key autoincrement not null, " + 
			CAPTURE_TABLE_ROW_ONE + " TEXT, " + CAPTURE_TABLE_ROW_TWO + " TEXT, " + CAPTURE_TABLE_ROW_THREE + " TEXT" + ");";
			
			db.execSQL(newTableQueryString);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
