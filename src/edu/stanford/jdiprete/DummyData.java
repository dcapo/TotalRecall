package edu.stanford.jdiprete;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.location.Location;
import android.util.Log;

public class DummyData {
	Snapshot snapshot;
	CaptureDatabase cdb;
	GPSDatabase gpsdb;
	TextMessageDataBase tmdb;
	PlaceDatabase pdb;

	public DummyData(Snapshot s)
	{
		snapshot = s;
		cdb = ((SnapshotApplication)snapshot.getApplication()).getCaptureDatabase();
		gpsdb = ((SnapshotApplication)snapshot.getApplication()).getGPSDatabase();
		tmdb = ((SnapshotApplication)snapshot.getApplication()).getTextMessageDataBase();
		pdb = ((SnapshotApplication)snapshot.getApplication()).getPlaceDatabase();
	}
	
	
	public void createPresentationDummyData()
	{
		
		//CAPTURE
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		try {
			Date start = formatter.parse("09/03/2011 - 19:04:00");
			Date end = formatter.parse("10/03/2011 - 02:00:00");
			CaptureObject capture = new CaptureObject(start.getTime(), end.getTime(), "Boys Night Out");
			cdb.addCaptureObject(capture);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//PLACES
		//Cheese Cake Factory
		try {
			Date start = formatter.parse("09/03/2011 - 19:11:00");
			Date end = formatter.parse("09/03/2011 - 21:07:00");
			double lat = 37.44691;
			double lon = -122.160877;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			Place place = new Place(start.getTime(), end.getTime(), location);
			pdb.addPlace(place);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//El'Amour
		try {
			Date start = formatter.parse("09/03/2011 - 21:16:00");
			Date end = formatter.parse("09/03/2011 - 21:49:00");
			double lat = 37.446013;
			double lon = -122.161242;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			Place place = new Place(start.getTime(), end.getTime(), location);
			pdb.addPlace(place);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Old Pro
		try {
			Date start = formatter.parse("09/03/2011 - 21:56:00");
			Date end = formatter.parse("10/03/2011 - 01:41:00");
			double lat = 37.445;
			double lon = -122.161413;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			Place place = new Place(start.getTime(), end.getTime(), location);
			pdb.addPlace(place);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//GPS Coordinates
		
		//Walking to Cheesecake
		Date date;
		try {
			date = formatter.parse("09/03/2011 - 19:05:00");
			double lat = 37.44672;
			double lon = -122.160673;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("09/03/2011 - 19:07:00");
			double lat = 37.446993;
			double lon = -122.160378;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("09/03/2011 - 19:09:00");
			double lat = 37.447112;
			double lon = -122.160512;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("09/03/2011 - 19:11:00");
			double lat = 37.44691;
			double lon = -122.160877;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Walking to El'Amor
		try {
			date = formatter.parse("09/03/2011 - 21:09:00");
			double lat = 37.446584;
			double lon = -122.161033;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("09/03/2011 - 21:11:00");
			double lat = 37.445894;
			double lon = -122.161725;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("09/03/2011 - 21:13:00");
			double lat = 37.445822;
			double lon = -122.161596;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("09/03/2011 - 21:16:00");
			double lat = 37.446013;
			double lon = -122.161242;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Walking to Old Pro
		try {
			date = formatter.parse("09/03/2011 - 21:51:00");
			double lat = 37.445315;
			double lon = -122.162122;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("09/03/2011 - 21:53:00");
			double lat = 37.445136;
			double lon = -122.16181;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("09/03/2011 - 21:55:00");
			double lat = 37.445;
			double lon = -122.161413;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		//Texts
		
		//Cheesecake texts
		TextMessage tm = new TextMessage("1111111111", "Hey, what are you up to?", "09/03/2011 - 20:02:00", 1, "Justine", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111111", "Boys night out. You going out tonight?", "09/03/2011 - 20:05:00", 1, "Justine", false);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111111", "No, I think I'm gonna just stay in and study tonight", "09/03/2011 - 20:09:00", 1, "Justine", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111222", "Dude, the waitress at Cheesecake factory just slapped me", "09/03/2011 - 20:49:00", 1, "Jerry", false);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111222", "Haha what'd you do?", "09/03/2011 - 20:51:00", 1, "Jerry", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111222", "Cole said I was trying to be a smooth operator...", "09/03/2011 - 20:56:00", 1, "Jerry", false);
		tmdb.addTextMessage(tm);
		
		//walking texts
		tm = new TextMessage("1111111222", "Haha nice", "09/03/2011 - 21:09:20", 1, "Jerry", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111222", "Hey, we're heading to Old Pro, wanna join?", "09/03/2011 - 21:53:20", 1, "Jerry", false);
		tmdb.addTextMessage(tm);
		
		//L'amour texts
		tm = new TextMessage("1111111222", "I can't believe I've never had froyo before, this stuff is amazing", "09/03/2011 - 21:26:20", 1, "Jerry", false);
		tmdb.addTextMessage(tm);
		
		//Old Pro texts
		tm = new TextMessage("1111111222", "Nah, I'm in for the night", "09/03/2011 - 21:59:20", 1, "Jerry", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111222", "Oh my god, I just saw justine riding the mechanical bull, you have to see this.", "10/03/2011 - 01:02:00", 1, "Jerry", false);
		tmdb.addTextMessage(tm);
		
	}
	
	
	public void populateGPSDatabase()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date date;
		try {
			date = formatter.parse("17/02/2011 - 15:30:00");
			double lat = 37.44448877182996;
			double lon = -122.16097354888916;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date = formatter.parse("17/02/2011 - 16:40:00");
			double lat = 37.44685676109754;
			double lon = -122.16150999069214;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date = formatter.parse("17/02/2011 - 18:50:00");
			double lat = 37.44748707654544;
			double lon = -122.15803384780884;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("18/02/2011 - 16:22:00");
			double lat = 21.25;
			double lon = 100.8;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date = formatter.parse("18/02/2011 - 17:10:00");
			double lat = 24.25;
			double lon = 98.8;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date = formatter.parse("18/02/2011 - 18:00:00");
			double lat = 25.25;
			double lon = 96.8;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			date = formatter.parse("20/02/2011 - 14:20:00");
			double lat = 15.25;
			double lon = 70.8;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date = formatter.parse("20/02/2011 - 15:10:00");
			double lat = 16.25;
			double lon = 72.8;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date = formatter.parse("20/02/2011 - 16:30:00");
			double lat = 17.25;
			double lon = 76.8;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			location.setTime(date.getTime());
			gpsdb.addLocation(location);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
	}
	
	public void populateCaptureDatabase()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		try {
			Date start = formatter.parse("15/06/2008 - 20:00:00");
			Date end = formatter.parse("015/06/2008 - 23:59:00");
			CaptureObject capture = new CaptureObject(start.getTime(), end.getTime(), "Grad Night");
			cdb.addCaptureObject(capture);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			Date start = formatter.parse("18/09/2010 - 16:00:00");
			Date end = formatter.parse("19/09/2010 - 2:00:00");
			CaptureObject capture = new CaptureObject(start.getTime(), end.getTime(), "21st Bday Party");
			cdb.addCaptureObject(capture);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			Date start = formatter.parse("15/02/2011 - 14:00:00");
			Date end = formatter.parse("15/02/2011 - 19:00:00");
			CaptureObject capture = new CaptureObject(start.getTime(), end.getTime(), "SF Day Trip");
			cdb.addCaptureObject(capture);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		

	}
	
	//String tm_phone_number, String tm_body, String tm_date, int tm_id, String tm_person, boolean tm_is_incoming
	
	public void populateSMSDatabase()
	{
		TextMessage tm = new TextMessage("1111111111", "Yay first text", "17/02/2011 - 15:30:00", 1, "Justine", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111111", "Yay second text", "17/02/2011 - 15:30:00", 1, "Justine", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111111", "Yay third text", "17/02/2011 - 15:30:00", 1, "Justine", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111112222", "QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ" +
				"QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ" +
				"QQQQQQQQQQQQQQQQQQQQ", "17/02/2011 - 16:50:00", 1, "Cole", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111111", "I just got here, where are you guys?", "17/02/2011 - 18:30:00", 1, "Justine", false);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111111", "Test One", "17/02/2011 - 19:30:00", 1, "Justine", false);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111111", "Test Two", "17/02/2011 - 20:30:00", 1, "Justine", false);
		tmdb.addTextMessage(tm);
		
		tm = new TextMessage("1111111111", "quack", "18/02/2011 - 17:10:00", 1, "Justine", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111113333", "what's up", "18/02/2011 - 18:30:00", 1, "Stephen", false);
		tmdb.addTextMessage(tm);
		
		tm = new TextMessage("1111114444", "where are you", "20/02/2011 - 14:30:00", 1, "Dan", true);
		tmdb.addTextMessage(tm);
		tm = new TextMessage("1111111111", "blah", "20/02/2011 - 17:30:00", 1, "Justine", true);
		tmdb.addTextMessage(tm);
		Log.d("TextMessageDatabas", "Size: " + tmdb.getTextMessages().size());
		
	}
	
	public void populatePlaceDatabase()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		try {
			Date start = formatter.parse("17/02/2011 - 16:20:00");
			Date end = formatter.parse("17/02/2011 - 17:15:00");
			double lat = 37.44518877182996;
			double lon = -122.16117354888916;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			Place place = new Place(start.getTime(), end.getTime(), location);
			pdb.addPlace(place);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			Date start = formatter.parse("17/02/2011 - 18:30:00");
			Date end = formatter.parse("17/02/2011 - 18:50:00");
			double lat = 37.44618877182996;
			double lon = -122.16137354888916;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			Place place = new Place(start.getTime(), end.getTime(), location);
			pdb.addPlace(place);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			Date start = formatter.parse("17/02/2011 - 15:45:00");
			Date end = formatter.parse("17/02/2011 - 16:10:00");
			double lat = 37.44478877182996;
			double lon = -122.16097354888916;
			Location location = new Location("dummyprovider");
			location.setLatitude(lat);
			location.setLongitude(lon);
			Place place = new Place(start.getTime(), end.getTime(), location);
			pdb.addPlace(place);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
