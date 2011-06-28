package edu.stanford.jdiprete;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;

//This class takes care of whatever action we need to perform right after a new SMS is received.
public class SMSReceiver {
	private BroadcastReceiver SMSreceiver;
	private Snapshot snapshot;
	final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	
	public SMSReceiver(Snapshot s) {
		snapshot = s;
		Log.d("Message", "SMS_RECEIVER CLASS STARTEDDDDDDD!!!!!!!!!");
		SMSreceiver = new BroadcastReceiver() 
		{
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("Message", "ADD TO DATABASE");
				//---get the SMS message passed in---
				Bundle bundle = intent.getExtras();        
				SmsMessage[] msgs = null;
				String str = "";      
				String date = "";
				String address = "";
				String body = "";
				if (bundle != null)
				{
					//---retrieve the SMS message received---
					Object[] pdus = (Object[]) bundle.get("pdus");
					msgs = new SmsMessage[pdus.length];            
					for (int i=0; i<msgs.length; i++){
						msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
						address = msgs[i].getOriginatingAddress();
						str += "SMS from " + address;                     
						str += ": ";
						body = msgs[i].getMessageBody().toString();
						str += body;
						date = Long.toString(msgs[i].getTimestampMillis());

					}
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
					date = formatter.format(new Date(Long.parseLong(date))); 
					str += "\n";
					str += "Sent on ";
					str += date;

					Uri personUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
					String person = "Unlisted Contact";
					Cursor cur = context.getContentResolver().query(personUri,  
							new String[] { PhoneLookup.DISPLAY_NAME },  
							null, null, null );  
					if(cur.moveToFirst()) {  
						int nameIdx = cur.getColumnIndex(PhoneLookup.DISPLAY_NAME);   
						person = cur.getString(nameIdx);  
						cur.close();   
					} 
					str += "\n";
					str += "Contact: ";
					str += person;	
					Boolean boxFlag = true;
					int id = ((SnapshotApplication)snapshot.getApplication()).getTextMessageDataBase().getTextMessageDataBaseSize();
					TextMessage tm = new TextMessage(address, body, date, id, person, boxFlag);
					((SnapshotApplication)snapshot.getApplication()).getTextMessageDataBase().addTextMessage(tm);
					Log.d("Message", "NEW MESSAGE STORED: " + str);
				}    
			}
		};
		IntentFilter filter = new IntentFilter(SMS_RECEIVED);
		snapshot.registerReceiver(SMSreceiver, filter);
	}
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*private void readSMS(Snapshot s, String boxAddr, Boolean boxFlag)
	{
		Uri SMSURI = Uri.parse(boxAddr);
		String[] projection = new String[]{"_id", "address", "body", "date"};
		Cursor cursor = null;
		TextMessage tm = null;
		try {
			cursor = s.getContentResolver().query(SMSURI
					, projection
					, null //selection
					, null //selectionArgs
					, null); //sortOrder

			if (cursor != null && cursor.moveToFirst()) {
				do {
					int    id    = cursor.getInt(cursor.getColumnIndex("_id"));
					String address = cursor.getString(cursor.getColumnIndex("address"));
					String body = cursor.getString(cursor.getColumnIndex("body"));
					String date = cursor.getString(cursor.getColumnIndex("date"));

					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
					date = formatter.format(new Date(Long.parseLong(date))); 

					Uri personUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
					String person = "Unlisted Contact";
					Cursor cur = s.getContentResolver().query(personUri,  
							new String[] { PhoneLookup.DISPLAY_NAME },  
							null, null, null );  
					if(cur.moveToFirst()) {  
						int nameIdx = cur.getColumnIndex(PhoneLookup.DISPLAY_NAME);   
						person = cur.getString(nameIdx);  
						cur.close();   
					}  
					tm = new TextMessage(address, body, date, id, person, boxFlag);
					textmessage_array.add(tm);
					// Debugging code
					//Log.d("Message", "id: " + id + " person: " + person + "  address: " + address + "  body: " + body + "  date: " + date);

				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}*/
