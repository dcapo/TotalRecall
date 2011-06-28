package edu.stanford.jdiprete;

import android.location.Location;

public class Place {
	private long start_time;
	private long end_time;
	private Location location;
	private String name;
	private long id;
	
	public Place(long p_id, long start, long end, Location loc)
	{
		id = p_id;
		start_time = start;
		end_time = end;
		location = loc;
		name = "Place";
	}
	
	public Place(long start, long end, Location loc)
	{
		start_time = start;
		end_time = end;
		location = loc;
		name = "Place";
	}
	
	public long getStartTime()
	{
		return start_time;
	}
	
	public void setStartTime(long start)
	{
		start_time = start;
	}
	
	public long getEndTime()
	{
		return end_time;
	}
	
	public void setEndTime(long end)
	{
		end_time = end;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public void setLocation(Location loc)
	{
		location = loc;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public long getID()
	{
		return id;
	}
	
	public void setID(long new_id)
	{
		id = new_id;
	}
}
