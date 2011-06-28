package edu.stanford.jdiprete;

import java.security.Provider;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

public class GPSService extends Service {

	private static final long GET_UPDATE_INTERVAL = 1000;
	private static final long REQUEST_LOCATION_INTERVAL = 15000;
	private static final long MIN_STAY_AT_PLACE = 60;
	private static final long PLACE_RADIUS = 25;
	private Timer timer;
	private Location currentLocation;
	private Location lastLocation;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private String bestProvider;
	private int counter = 0;
	private boolean placeFlag; 
	private long arrivalTime;
	private long leaveTime;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		timer = new Timer();
		placeFlag = false;
		startService();
	}

	private void startService() {
		//timer = new Timer();
		Log.d("Capture", "Service Started");
		counter = 0;
		arrivalTime = 0;
		leaveTime = 0;
		lastLocation = null;
		currentLocation = null;
		locationListener = new LocationListener() {
			//LOCATION CHANGED
			public void onLocationChanged(Location location) {
				Log.d("Message", "*******LOCATION CHANGED!*******");
				addGeotag(location);
				if (placeFlag){
					leaveTime = location.getTime();
					sendToPlaceDatabase();
					placeFlag = false;
				}
				counter = 0;
				currentLocation = location;
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {}

			public void onProviderEnabled(String provider) {
				Log.d("Message", "Provider Enabled");
			}

			public void onProviderDisabled(String provider) {}
		};


		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, REQUEST_LOCATION_INTERVAL, PLACE_RADIUS, locationListener);
		
		final Criteria criteria = new Criteria();
		// get the system location service
	    // create criteria to filter the providers
	    //NEED TO BE IN OR NOT DEPENDING ON TYPE OF PHONE 
	    criteria.setAccuracy(Criteria.ACCURACY_FINE);
	    criteria.setAltitudeRequired(false);
	    criteria.setBearingRequired(false);
	    criteria.setCostAllowed(true);
	    criteria.setPowerRequirement(Criteria.POWER_LOW);
	    bestProvider = locationManager.getBestProvider(criteria, false);
	    
	    TimerTask timerTask = new TimerTask(){
	        public void run() {
	            getUpdateLocation(criteria);
	        }
	    };
	    timer.scheduleAtFixedRate(timerTask,0,GET_UPDATE_INTERVAL);
	    Log.i(getClass().getSimpleName(), "Timer started!");
	}
	
	private void addGeotag(Location location) {
		((SnapshotApplication)this.getApplication()).getGPSDatabase().addLocation(location);
	}

	private void sendToPlaceDatabase() {
		Place place = new Place (arrivalTime,leaveTime,lastLocation);
		((SnapshotApplication)this.getApplication()).getPlaceDatabase().addPlace(place);
		Log.i(getClass().getSimpleName(), "Arrival time: " + arrivalTime + ", Leave time: " + leaveTime
				+ ", last Location: " + lastLocation.getLatitude() + ", " + lastLocation.getLongitude());
	}

	private void stopService()
	{
		Log.d("GPSService", "STOPPED!");
		if (timer != null)
		{
			if (lastLocation != null)
			{
				lastLocation.setTime(System.currentTimeMillis() - 1000);
				((SnapshotApplication)this.getApplication()).getGPSDatabase().addLocation(lastLocation);
			}
			if (placeFlag){
				leaveTime = System.currentTimeMillis();
				sendToPlaceDatabase();
				placeFlag = false;
			}
			locationManager.removeUpdates(locationListener);
			timer.cancel();
		}
	}

	private void getUpdateLocation(Criteria criteria) {
	    Log.i(getClass().getSimpleName(), "background task - start");
	    if(lastLocation == null) lastLocation = locationManager.getLastKnownLocation(bestProvider);
	    if (currentLocation == null) {
	    	currentLocation = locationManager.getLastKnownLocation(bestProvider);
	    } else {
	    	Log.i(getClass().getSimpleName(), "Current Location: " + currentLocation.getLatitude() + ", " + currentLocation.getLongitude());
	    	Log.i(getClass().getSimpleName(), "Last Location: " + lastLocation.getLatitude() + ", " + lastLocation.getLongitude());
	    
		    if (counter == 0) arrivalTime = currentLocation.getTime();
		    if (currentLocation.distanceTo(lastLocation) < 25) {
		    	counter++;
		    }
		    if (counter >= MIN_STAY_AT_PLACE) {
		    	placeFlag = true;
		    	Log.i(getClass().getSimpleName(), "Place Flag = True");
		    }
		    lastLocation = currentLocation;
	    	Log.i(getClass().getSimpleName(), "Counter: " + counter);
		    Log.i(getClass().getSimpleName(), "----------------------------------------------");
	    }
	}

	@Override 
	public void onDestroy()
	{
		super.onDestroy();
		stopService();
	}


}