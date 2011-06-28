package edu.stanford.jdiprete;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.location.Location;

public class CaptureDatabase {
	private CaptureDatabaseManager c_dbm;
	private ArrayList<CaptureObject> capture_array;
	private Snapshot snapshot;
	private ArrayList<ArrayList<TextMessage>> multiple_text_array;
	
	public CaptureDatabase(Snapshot s)
	{
		snapshot = s;
		capture_array = new ArrayList<CaptureObject>();
		readDatabase();
	}
	
	private void readDatabase()
	{
		//capture_array.clear();
		c_dbm = ((SnapshotApplication)snapshot.getApplication()).getCaptureDatabaseManager();
		ArrayList<ArrayList<Object> > db_rows = c_dbm.getAllRowsAsArray();
		for (ArrayList<Object> row: db_rows)
		{
			long id = (Long)row.get(0);
			long start = (Long)row.get(1);
			long end = (Long)row.get(2);
			String label = (String)row.get(3);


			CaptureObject capture_object = new CaptureObject(start, end, label, id);
			
			capture_array.add(capture_object);
		}
	}
	
	public CaptureObject getCaptureObjectForId(long id)
	{
		for (CaptureObject co: capture_array)
		{
			if(co.getID() == id)
			{
				return co;
			}
		}
		return null;
	}
	
	public ArrayList<CaptureObject> getCaptureObjectArray()
	{
		Collections.sort(capture_array, new Comparator<Object>(){
			 
            public int compare(Object o1, Object o2) {
                CaptureObject co1 = (CaptureObject) o1;
                CaptureObject co2 = (CaptureObject) o2;
               return -(new Time(co1.getStartTime()).compareTo(new Time(co2.getStartTime())));
            }
 
        });
		return capture_array;
	}
	
	public void addCaptureObject(CaptureObject co)
	{
		c_dbm.addRow(co.getStartTime(), co.getEndTime(), co.getLabel());
		co.setID(capture_array.size() + 1);
		capture_array.add(co);
		//readDatabase();
	}
	
	public long getLastID()
	{
		return capture_array.size();
	}
	
	public void updateLastEndTime(long new_end)
	{
		CaptureObject co = this.getCaptureObjectForId(capture_array.size());
		c_dbm.updateRow(capture_array.size(), co.getStartTime(), new_end, co.getLabel());
	}
	
	public void setMultiplePlaceArray(ArrayList<ArrayList<TextMessage>> array)
	{
		multiple_text_array = array;
	}
	
	public ArrayList<TextMessage> getMultipleTextMessagesForIndex(int index)
	{
		return multiple_text_array.get(index);
	}
}
