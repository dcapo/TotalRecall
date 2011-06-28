package edu.stanford.jdiprete;

import java.util.Date;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SnapshotApplication extends Application {
	
	private boolean capturing;
	private long capture_end_time = 0;
	private TextMessageDatabaseManager tm_dbm;
	private TextMessageDataBase tmdb;
	private GPSDatabase gpsdb;
	private GPSDatabaseManager gps_dbm;
	private SMSDatabaseManager sms_dbm;
	private CaptureDatabaseManager c_dbm;
	private CaptureDatabase cdb;
	private PlaceDatabaseManager p_dbm;
	private PlaceDatabase pdb;
	private Context context;
	private Intent gps_intent;

	
	public boolean getCapturing()
	{
		if ((new Date(capture_end_time)).after(new Date(System.currentTimeMillis())))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public void setCaptureEndTime(long end_time)
	{
		capture_end_time = end_time;
	}
	
	public TextMessageDatabaseManager getTextMessageDatabaseManager()
	{
		return tm_dbm;
	}
	
	public void setTextMessageDatabaseManager(TextMessageDatabaseManager manager)
	{
		tm_dbm = manager;
	}
	
	public TextMessageDataBase getTextMessageDataBase()
	{
		return tmdb;
	}
	
	public void setTextMessageDataBase(TextMessageDataBase database)
	{
		tmdb = database;
	}
	
	public GPSDatabase getGPSDatabase()
	{
		return gpsdb;
	}
	
	public void setGPSDatabase(GPSDatabase gps_database)
	{
		gpsdb = gps_database;
	}
	
	public GPSDatabaseManager getGPSDatabaseManager()
	{
		return gps_dbm;
	}
	
	public void setGPSDatabaseManager (GPSDatabaseManager gps_database)
	{
		gps_dbm = gps_database;
	}
	
	public SMSDatabaseManager getSMSDatabaseManager()
	{
		return sms_dbm;
	}
	
	public void setSMSDatabaseManager (SMSDatabaseManager sms_database)
	{
		sms_dbm = sms_database;
	}
	
	public CaptureDatabaseManager getCaptureDatabaseManager()
	{
		return c_dbm;
	}
	
	public void setCaptureDatabaseManager(CaptureDatabaseManager capture_database)
	{
		c_dbm = capture_database;
	}
	
	public CaptureDatabase getCaptureDatabase()
	{
		return cdb;
	}
	
	public void setCaptureDatabase(CaptureDatabase capture_database)
	{
		cdb = capture_database;
	}
	
	public PlaceDatabaseManager getPlaceDatabaseManager()
	{
		return p_dbm;
	}
	
	public void setPlaceDatabaseManager(PlaceDatabaseManager dbm)
	{
		p_dbm = dbm;
	}
	
	public PlaceDatabase getPlaceDatabase()
	{
		return pdb;
	}
	
	public void setPlaceDatabase(PlaceDatabase database)
	{
		pdb = database;
	}
	
	public Intent getGPSIntent()
	{
		return gps_intent;
	}
	
	public void setGPSIntent(Intent intent)
	{
		gps_intent = intent;
	}
	
	public Context getContext()
	{
		return context;
	}
	
	public void setContext(Context cntxt)
	{
		context = cntxt;
	}
	
}
