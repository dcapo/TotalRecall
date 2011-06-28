package edu.stanford.jdiprete;

import java.util.ArrayList;
import java.util.Date;

import android.location.Location;
import android.util.Log;

public class PlaceDatabase {
	PlaceDatabaseManager p_dbm;
	Snapshot snapshot;
	ArrayList<Place> place_array;
	
	public PlaceDatabase(Snapshot s)
	{
		snapshot = s;
		place_array = new ArrayList<Place>();
		readDatabase();
	}
	
	private void readDatabase()
	{
		p_dbm = ((SnapshotApplication)snapshot.getApplication()).getPlaceDatabaseManager();
		ArrayList<ArrayList<Object> > db_rows = p_dbm.getAllRowsAsArray();
		for (ArrayList<Object> row: db_rows)
		{
			long id = (Long)row.get(0);
			long start = (Long)row.get(1);
			long end = (Long)row.get(2);
			String name = (String)row.get(3);
			double x_coord = Double.parseDouble((String) row.get(4));
			double y_coord = Double.parseDouble((String)row.get(5));
			
			Location location = new Location("dummyprovider");
			location.setTime(start);
			location.setLatitude(x_coord);
			location.setLongitude(y_coord);

			Place place = new Place(id, start, end, location);
			
			place_array.add(place);
		}
	}
	
	public ArrayList<Place> getPlaceArray()
	{
		return place_array;
	}
	
	public ArrayList<Place> getPlacesForCapture(CaptureObject capture_object)
	{
		ArrayList<Place> capture_array = new ArrayList<Place>();
		for (Place p: place_array)
		{
			Date date = new Date(p.getStartTime());
			Date start = new Date(capture_object.getStartTime());
			Date end = new Date(capture_object.getEndTime());
			if (date.after(start) && date.before(end))
			{
				capture_array.add(p);
			}
		}
		return capture_array;	
	}
	
	public void addPlace(Place place)
	{
		p_dbm.addRow(place.getStartTime(), place.getEndTime(), place.getName(), Double.toString(place.getLocation().getLatitude()), Double.toString(place.getLocation().getLongitude()));
		place.setID(place_array.size()+2);
		place_array.add(place);
		Location location = new Location("dummyprovider");
		location.setLatitude(place.getLocation().getLatitude());
		location.setLongitude(place.getLocation().getLongitude());
		place.setLocation(location);
	}
	
	public Place getPlaceForId(long id)
	{
		for (Place p: place_array)
		{
			if(p.getID() == id)
			{
				return p;
			}
		}
		return null;
	}
}
