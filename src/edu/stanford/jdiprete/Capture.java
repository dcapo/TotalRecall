package edu.stanford.jdiprete;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Capture extends Activity {
		private CaptureObject capture_object;
		private Intent gps_intent;
		private BroadcastReceiver receiver; 
		
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	        setContentView(R.layout.capture);
	        
	        
	        receiver = new BroadcastReceiver()
			{

				@Override
				public void onReceive(Context context, Intent intent) {
					// TODO Auto-generated method stub
					Log.d("ALARM!!", "YAY IT WORKED!!");
					if (((SnapshotApplication)getApplication()).getCapturing())
            		{
            			stopService(gps_intent);
            		}
				}
				
			};
	        
	        final Button button1 = (Button) findViewById(R.id.button1);
	        button1.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	            	
            		capture_object = new CaptureObject();
            		if (((EditText)findViewById(R.id.editText1)).getText().toString() != "")
            		{
            			capture_object.setLabel(((EditText)findViewById(R.id.editText1)).getText().toString());
            		}else
            		{
            			capture_object.setLabel("Capture");
            		}
            		capture_object.setStartTime(System.currentTimeMillis());
            		DatePicker dp = (DatePicker)findViewById(R.id.datePicker1);
            		TimePicker tp = (TimePicker)findViewById(R.id.timePicker1);
            		String date_string = "";
            		if (Integer.toString(dp.getDayOfMonth()).length() == 1)
            		{
            			date_string += "0";
            		}
            		date_string += Integer.toString(dp.getDayOfMonth()) + "/";
            		if (Integer.toString(dp.getMonth()).length() == 1)
            		{
            			date_string += "0";
            		}
            		date_string += Integer.toString(dp.getMonth() + 1) + "/";
            		date_string += Integer.toString(dp.getYear()) + " - ";
            		date_string += Integer.toString(tp.getCurrentHour()) + ":";
            		date_string += Integer.toString(tp.getCurrentMinute()) + ":";
            		date_string += "00";       		
            		
            		Log.d("Date String", date_string);
            		

            		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
            	
            		try {
						Date date = formatter.parse(date_string);
						
						// check that end time is after current time
						if( System.currentTimeMillis() > date.getTime() ) 
						{
							CharSequence errMessage = "Please select an end time after the current time!";
							Toast.makeText(getApplicationContext(), errMessage, Toast.LENGTH_SHORT).show();
						}else
						{
		            		capture_object.setEndTime(date.getTime());
		            		((SnapshotApplication)getApplication()).getCaptureDatabase().addCaptureObject(capture_object);
		            		((SnapshotApplication)getApplication()).setCaptureEndTime(date.getTime());
		            		
		            		gps_intent = new Intent();
		            		gps_intent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.GPSService");
		            		startService(gps_intent);
		            		((SnapshotApplication)getApplication()).setGPSIntent(gps_intent);
		            		
		           			AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		           			//Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
		           			Intent intent = new Intent("edu.stanford.jdiprete.GPSService");
		           			
		           			IntentFilter filter = new IntentFilter("edu.stanford.jdiprete.GPSService");
		           			getApplicationContext().registerReceiver(receiver, filter);
		           			
		           		    PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
		           		            PendingIntent.FLAG_UPDATE_CURRENT);
		           			am.set(AlarmManager.RTC_WAKEUP, date.getTime(), sender);
		           			
		           			finish();
		           			//DAN- instead of the am.set function call:
		           			//am.setRepeating(AlarmManager.RTC_WAKEUP, whatever time you want, whatever a week is in milliseconds, sender)
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
	            	
            		
	            }
	        });
	   }
	   

	   
	 
}
