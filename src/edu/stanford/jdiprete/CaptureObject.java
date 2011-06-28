package edu.stanford.jdiprete;

public class CaptureObject {
	private long start_time;
	private long end_time;
	private String label;
	private long id;
	
	public CaptureObject(long start, long end, String co_label, long co_id)
	{
		start_time = start;
		end_time = end;
		label = co_label;
		id = co_id;
	}
	
	public CaptureObject(long start, long end, String co_label)
	{
		start_time = start;
		end_time = end;
		label = co_label;
		id = 0;
	}
	
	public CaptureObject(long start, long end)
	{
		start_time = start;
		end_time = end;
		label = "Capture";
	}
	
	public CaptureObject()
	{
		label = "Capture";
	}
	
	public long getID()
	{
		return id;
	}
	
	public void setID(long an_id)
	{
		id = an_id;
	}
	
	public void setLabel(String co_label)
	{
		label = co_label;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setStartTime(long time)
	{
		start_time = time;
	}
	
	public long getStartTime()
	{
		return start_time;
	}
	
	public void setEndTime(long time)
	{
		end_time = time;
	}
	
	public long getEndTime()
	{
		return end_time;
	}
	
}
