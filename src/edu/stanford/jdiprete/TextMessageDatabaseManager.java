package edu.stanford.jdiprete;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TextMessageDatabaseManager {


Context context;
	
	private SQLiteDatabase db;
	
	private final String TEXT_MESSAGE_DATABASE_NAME = "text_message_database_name";
	private final int TEXT_MESSAGE_DATABASE_VERSION = 1;
	
	private final String TEXT_MESSAGE_TABLE_NAME = "text_message_database_table";
	private final String TEXT_MESSAGE_TABLE_ROW_ID = "id";
	private final String TEXT_MESSAGE_TABLE_ROW_ONE = "phone_number";
	private final String TEXT_MESSAGE_TABLE_ROW_TWO = "date";
	private final String TEXT_MESSAGE_TABLE_ROW_THREE = "body";
	private final String TEXT_MESSAGE_TABLE_ROW_FOUR = "person";
	private final String TEXT_MESSAGE_TABLE_ROW_FIVE = "is_incoming";

	
	public TextMessageDatabaseManager(Context context)
	{
		this.context = context;
		TEXT_MESSAGEDatabaseOpenHelper helper = new TEXT_MESSAGEDatabaseOpenHelper(context);
		this.db = helper.getWritableDatabase();
	}
	
	public void addRow(String phone_number, long date, String body, String person, int incoming)
	{
		ContentValues values = new ContentValues();
		values.put(TEXT_MESSAGE_TABLE_ROW_ONE, phone_number);
		values.put(TEXT_MESSAGE_TABLE_ROW_TWO, date);
		values.put(TEXT_MESSAGE_TABLE_ROW_THREE, body);
		values.put(TEXT_MESSAGE_TABLE_ROW_FOUR, person);
		values.put(TEXT_MESSAGE_TABLE_ROW_FIVE, incoming);
		
		try
		{
			db.insert(TEXT_MESSAGE_TABLE_NAME, null, values);
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
			db.delete(TEXT_MESSAGE_TABLE_NAME, TEXT_MESSAGE_TABLE_ROW_ID + "=" + rowID, null);
		}
		catch (Exception e)
		{
			Log.e("Database Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateRow(long rowID, String phone_number, long date, String body, String person, int incoming)
	{
		ContentValues values = new ContentValues();
		values.put(TEXT_MESSAGE_TABLE_ROW_ONE, phone_number);
		values.put(TEXT_MESSAGE_TABLE_ROW_TWO, date);
		values.put(TEXT_MESSAGE_TABLE_ROW_THREE, body);
		values.put(TEXT_MESSAGE_TABLE_ROW_FOUR, person);
		values.put(TEXT_MESSAGE_TABLE_ROW_FIVE, incoming);
		
		
		try
		{
			db.update(TEXT_MESSAGE_TABLE_NAME, values, TEXT_MESSAGE_TABLE_ROW_ID + "=" + rowID, null);	
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
			cursor = db.query(TEXT_MESSAGE_TABLE_NAME, 
					new String[] { TEXT_MESSAGE_TABLE_ROW_ID, TEXT_MESSAGE_TABLE_ROW_ONE, TEXT_MESSAGE_TABLE_ROW_TWO, TEXT_MESSAGE_TABLE_ROW_THREE, TEXT_MESSAGE_TABLE_ROW_FOUR, TEXT_MESSAGE_TABLE_ROW_FIVE }, 
					TEXT_MESSAGE_TABLE_ROW_ID + "=" + rowID, 
					null, null, null, null);
			
			cursor.moveToFirst();
			
			if (!cursor.isAfterLast())
			{
				do
				{
					rowArray.add(cursor.getLong(0));
					rowArray.add(cursor.getString(1));
					rowArray.add(cursor.getLong(2));
					rowArray.add(cursor.getString(3));
					rowArray.add(cursor.getString(4));
					rowArray.add(cursor.getInt(5));
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
			cursor = db.query(TEXT_MESSAGE_TABLE_NAME, 
					new String[] { TEXT_MESSAGE_TABLE_ROW_ID, TEXT_MESSAGE_TABLE_ROW_ONE, TEXT_MESSAGE_TABLE_ROW_TWO, TEXT_MESSAGE_TABLE_ROW_THREE, TEXT_MESSAGE_TABLE_ROW_FOUR, TEXT_MESSAGE_TABLE_ROW_FIVE }, 
					null, null, null, null, null);
			
			cursor.moveToFirst();
			
			if (!cursor.isAfterLast())
			{
				do
				{
					ArrayList<Object> dataList = new ArrayList<Object>();
					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getString(1));
					dataList.add(cursor.getLong(2));
					dataList.add(cursor.getString(3));
					dataList.add(cursor.getString(4));
					dataList.add(cursor.getInt(5));
					
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
	
	
	private class TEXT_MESSAGEDatabaseOpenHelper extends SQLiteOpenHelper
	{

		public TEXT_MESSAGEDatabaseOpenHelper(Context context) {
			super(context, TEXT_MESSAGE_DATABASE_NAME, null, TEXT_MESSAGE_DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String newTableQueryString = "CREATE TABLE " + TEXT_MESSAGE_TABLE_NAME + 
			" (" + TEXT_MESSAGE_TABLE_ROW_ID + " integer primary key autoincrement not null, " + 
			TEXT_MESSAGE_TABLE_ROW_ONE + " TEXT, " + TEXT_MESSAGE_TABLE_ROW_TWO + " TEXT, " + TEXT_MESSAGE_TABLE_ROW_THREE + " TEXT, " + TEXT_MESSAGE_TABLE_ROW_FOUR + " TEXT, " + TEXT_MESSAGE_TABLE_ROW_FIVE + " TEXT" + ");";
			
			db.execSQL(newTableQueryString);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
