package edu.stanford.jdiprete;

import edu.stanford.jdiprete.SMSDate;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TextMessage{
	
	private String phone_number;
	private String body;
	private String date_string;
	private String date_and_time_string;
	private SMSDate date;
	private Time time;
	private long id;
	private boolean is_incoming;
	private String person;
	private Location location;
	private long long_date;
	
	public TextMessage(String tm_phone_number, String tm_body, String tm_date, long tm_id, String tm_person, boolean tm_is_incoming)
	{
		phone_number = tm_phone_number;
		body = tm_body;
		date_and_time_string = tm_date;
		setLongDate();
		parseDate();
		//Log.d("Date test", "Month: " + date.getMonth() + " Day: " + date.getDay() + " Year: " + date.getYear());
		
		id = tm_id;
		is_incoming = tm_is_incoming;
		person = tm_person;
	}
	
	
	
	private void setLongDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date date;
		try {
			date = formatter.parse(date_and_time_string);
			long_date = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public long getLongDate()
	{
		return long_date;
	}
	
	private void parseDate()
	{
		String[] td_array = date_and_time_string.split(" - ");
		date_string = td_array[0];
		String[] date_array = td_array[0].split("/");
		String[] time_array = td_array[1].split(":");
		//Log.d("Date test", "After parse: Month: " + Integer.parseInt(date_array[1]) + " Day: " + Integer.parseInt(date_array[0]) + " Year: " + Integer.parseInt(date_array[2]));
		date = new SMSDate(Integer.parseInt(date_array[2]), Integer.parseInt(date_array[1]), Integer.parseInt(date_array[0]));
		time = new Time(Integer.parseInt(time_array[0]), Integer.parseInt(time_array[1]), Integer.parseInt(time_array[2]));	
	}
	
	public String getPhoneNumber()
	{
		return phone_number;
	}
	

	public String getPerson()
	{
		return person;
	}
	
	public String getBody()
	{
		return body;
	}
	
	public String getDateAndTimeString()
	{
		return date_string;
	}
	
	public SMSDate getDate()
	{
		return date;
	}
	
	public String getDateString()
	{
		return date_string;
	}
	
	public Time getTime()
	{
		return time;
	}
	public long getID()
	{
		return id;
	}
	
	public boolean isIncoming()
	{
		return is_incoming;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public void setLocation(Location l)
	{
		location = l;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
