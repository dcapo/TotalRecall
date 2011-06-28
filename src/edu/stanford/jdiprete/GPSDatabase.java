package edu.stanford.jdiprete;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.location.Location;
import android.util.Log;

public class GPSDatabase {
	
	private GPSDatabaseManager gps_dbm;
	private Snapshot snapshot;
	private ArrayList<Location> gps_array;
	
	public GPSDatabase(Snapshot s)
	{
		snapshot = s;
		gps_array = new ArrayList<Location>();
		readDatabase();
	}
	
	private void readDatabase()
	{
		gps_dbm = ((SnapshotApplication)snapshot.getApplication()).getGPSDatabaseManager();
		ArrayList<ArrayList<Object> > db_rows = gps_dbm.getAllRowsAsArray();
		for (ArrayList<Object> row: db_rows)
		{
			long time = Long.parseLong((String)row.get(1));
			double x_coord = Double.parseDouble((String) row.get(2));
			double y_coord = Double.parseDouble((String)row.get(3));

			Location location = new Location("dummyprovider");
			location.setTime(time);
			location.setLatitude(x_coord);
			location.setLongitude(y_coord);
			
			gps_array.add(location);
		}
	}
	
	public ArrayList<Location> getGPSArray()
	{
		return gps_array;	
	}
	
	public ArrayList<Location> getGPSArrayForCapture(CaptureObject capture_object)
	{
		ArrayList<Location> capture_array = new ArrayList<Location>();
		for (Location l: gps_array)
		{
			Date date = new Date(l.getTime());
			Date start = new Date(capture_object.getStartTime());
			Date end = new Date(capture_object.getEndTime());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			//Log.d("TimesForGPS", "GPS Time: " + formatter.format(date) + "Capture Start: " + formatter.format(start) + " Capture End: " + formatter.format(end));
			if ((date.after(start) || date.equals(start)) && (date.before(end) || date.equals(end)))
			{
				capture_array.add(l);
			}
		}
		return capture_array;
	}
	
	public void addLocation(Location l)
	{
		Location location = new Location("dummyprovider");
		location.setLatitude(l.getLatitude());
		location.setLongitude(l.getLongitude());
		location.setTime(l.getTime());
		gps_dbm.addRow(Long.toString(l.getTime()), Double.toString(l.getLatitude()), Double.toString(l.getLongitude()));
		gps_array.add(location);
	}
	
}
