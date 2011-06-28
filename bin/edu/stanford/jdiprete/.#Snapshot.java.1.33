package edu.stanford.jdiprete;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class Snapshot extends Activity {
	
	private TextMessageDatabaseManager tm_dbm;
	private TextMessageDataBase tmdb;
	private GPSDatabase gpsdb;
	private GPSDatabaseManager gps_dbm;
	private SMSDatabaseManager sms_dbm;
	private CaptureDatabaseManager c_dbm;
	private CaptureDatabase cdb;
	private PlaceDatabase pdb;
	private PlaceDatabaseManager p_dbm;
	private Intent gps_intent;
	private CaptureObject capture_object;
	private ToggleButton capture_button;
	private DummyData dd;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.d("OnCREATE", "Create!");
        
        //make context available from anywhere
        //((SnapshotApplication)this.getApplication()).setContext((Context)this);
        
        //initialize the database stuff
        gps_dbm = new GPSDatabaseManager(this);
        ((SnapshotApplication)this.getApplication()).setGPSDatabaseManager(gps_dbm);
        
        sms_dbm = new SMSDatabaseManager(this);
        ((SnapshotApplication)this.getApplication()).setSMSDatabaseManager(sms_dbm);
        
        c_dbm = new CaptureDatabaseManager(this);
        ((SnapshotApplication)this.getApplication()).setCaptureDatabaseManager(c_dbm);
        
        p_dbm = new PlaceDatabaseManager(this);
        ((SnapshotApplication)this.getApplication()).setPlaceDatabaseManager(p_dbm);
        
        tm_dbm = new TextMessageDatabaseManager(this);
        ((SnapshotApplication)this.getApplication()).setTextMessageDatabaseManager(tm_dbm);
        
        gpsdb = new GPSDatabase(this);
        ((SnapshotApplication)this.getApplication()).setGPSDatabase(gpsdb);
        
        tmdb = new TextMessageDataBase(this);
        ((SnapshotApplication)this.getApplication()).setTextMessageDataBase(tmdb);
        
        cdb = new CaptureDatabase(this);
        ((SnapshotApplication)this.getApplication()).setCaptureDatabase(cdb);
        
        pdb = new PlaceDatabase(this);
        ((SnapshotApplication)this.getApplication()).setPlaceDatabase(pdb);
        
      //Cache messages on the fly
        ContentResolver contentResolver = getContentResolver();
        Handler handler = new Handler();
        ContentObserver m_SMSObserver = new SMSObserver(handler, this);
        contentResolver.registerContentObserver(Uri.parse("content://sms/"), true, m_SMSObserver);
        

       	dd = new DummyData(this);
       	dd.createPresentationDummyData();
		/*dd.populateCaptureDatabase();
		dd.populateGPSDatabase();
		dd.populateSMSDatabase();
		dd.populatePlaceDatabase();*/

        
        //Set up button listeners (REMOVE when UI changes)
        
        final Button archive_button = (Button) findViewById(R.id.archive_button);
        archive_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent();
                myIntent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.Archive");
                startActivity(myIntent); 
            }
        });
        
        capture_button = (ToggleButton) findViewById(R.id.capture_button);
        if (((SnapshotApplication)this.getApplication()).getCapturing())
    	{
        	capture_button.setText("End Capture");
    	}else
    	{
    		capture_button.setText("Capture");
    	}
            
        capture_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (!((SnapshotApplication)getApplication()).getCapturing())
            	{
	
                    Intent myIntent = new Intent();
                    myIntent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.Capture");
                    startActivity(myIntent); 
              		capture_button.setText("End Capture");
            	}else
            	{
            		capture_button.setText("Capture");  
            		if (((SnapshotApplication)getApplication()).getCapturing())
            		{
            			stopService(((SnapshotApplication)getApplication()).getGPSIntent());
            			cdb.updateLastEndTime(System.currentTimeMillis());
            			((SnapshotApplication)getApplication()).setCaptureEndTime(System.currentTimeMillis());
            		}

            	}

            	

            }
        });
        final Button about_button = (Button) findViewById(R.id.about_button);
        about_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent();
                myIntent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.About");
                startActivity(myIntent); 
            }
        });
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	if (((SnapshotApplication)this.getApplication()).getCapturing() && capture_button.getText().equals("Capture"))
    	{
    		capture_button.setText("End Capture");
    		capture_button.toggle();
    	}else if (!((SnapshotApplication)this.getApplication()).getCapturing() && capture_button.getText().equals("End Capture"))
    	{
    		capture_button.setText("Capture");
    		capture_button.toggle();
    	}
    }
}