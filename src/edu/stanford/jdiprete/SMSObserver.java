package edu.stanford.jdiprete;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

public class SMSObserver extends ContentObserver {

	private Handler m_handler = null;
	private Snapshot snapshot;
	private int lastID;

	public SMSObserver(Handler handler, Snapshot s) {
		super(handler);
		m_handler = handler;
		snapshot = s;
		lastID = -1;
		// TODO Auto-generated constructor stub
	}

	@Override public boolean deliverSelfNotifications() { 
		return false; 
	}

	@Override public void onChange(boolean arg0) { 
		super.onChange(arg0); 
		Message msg = new Message(); 
		msg.obj = "xxxxxxxxxx";
		m_handler.sendMessage(msg);

		Uri uriSMSURI = Uri.parse("content://sms/");
		Cursor cur = snapshot.getContentResolver().query(uriSMSURI, null, null,
				null, null);
		cur.moveToNext();
		String protocol = cur.getString(cur.getColumnIndex("protocol"));
		Log.d("Message", "Protocol: " + protocol);
		if(protocol == null){
			addMessage(cur, false);
		} else{
			Log.d("Msg", "SMS RECEIVE");  
			addMessage(cur, true);
		}
		cur.close();
	}
	
	private void addMessage(Cursor cur, Boolean boxFlag) {
		int id = cur.getInt(cur.getColumnIndex("_id"));
		int DBSize = ((SnapshotApplication)snapshot.getApplication()).getTextMessageDataBase().getTextMessageDataBaseSize();
		Log.d("Message", "id: " + Integer.toString(id) + "        DBSize: " + Integer.toString(DBSize));

		if (id != lastID) {
			String body = cur.getString(cur.getColumnIndex("body"));
			String address  = cur.getString(cur.getColumnIndex("address"));
			String date = cur.getString(cur.getColumnIndex("date"));
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			date = formatter.format(new Date(Long.parseLong(date))); 
			
			Uri personUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
			String person = "Unlisted Contact";
			Cursor contactCursor = snapshot.getContentResolver().query(personUri,  
					new String[] { PhoneLookup.DISPLAY_NAME },  
					null, null, null );  
			if(contactCursor.moveToFirst()) {  
				int nameIdx = contactCursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);   
				person = contactCursor.getString(nameIdx);  
				contactCursor.close();   
			} 
			
			TextMessage tm = new TextMessage(address, body, date, id, person, boxFlag);
			((SnapshotApplication)snapshot.getApplication()).getTextMessageDataBase().addTextMessage(tm);
			Log.d("Message", "SMS SEND BODY= " + body); 
			Log.d("Message", "NEW MESSAGE STORED"); 
			lastID = id;
		}
	}
}
