package edu.stanford.jdiprete;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Archive extends Activity {
		private ArrayAdapter<String> adapter;
		private ArrayList<CaptureObject> capturesList;
		private String[] captureLabels;
		
		private ListView lv;
		
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.archive_background); 
	       
	       	updateList();
	       
	        // create list view of captures
	       	lv = (ListView)findViewById(R.id.archiveLV);
	       	
	       	adapter = new ArrayAdapter<String>(this, R.layout.archive, captureLabels);
	       	adapter.notifyDataSetChanged();
	       	lv.setAdapter(adapter);
	        lv.setTextFilterEnabled(true);
	        	        
	        // set listview divider color
	        int[] colors = {0, 0xFF49C1E0, 0}; // blue for the example
	        lv.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
	        lv.setDividerHeight(1);
	        
	        // sets selector color - booya 3D3D3D
//	        int[] selectorColors = {0xFF49C1E0, 0xFF49C1E0, 0xFF49C1E0};
	        int[] selectorColors = {0xFF3D3D3D, 0xFF49C1E0, 0xFF3D3D3D};
	        lv.setSelector(new GradientDrawable(Orientation.RIGHT_LEFT, selectorColors));
	        

	        lv.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {
	            
	        	  // grab capture id and pass through to visualizer to setup tabview data
	        	  long selectedCaptureId = capturesList.get(position).getID();
	        	  
	        	  // When clicked, show a toast with the TextView text
	              Intent myIntent = new Intent();
	              myIntent.putExtra("captureId", selectedCaptureId);
	              myIntent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.Visualizer");
	              startActivity(myIntent); 
	          }
	        });
	        
	   }
	   
	   private void updateList()
	   {
	       	// grab capture list, get labels to populate listview
	       capturesList = ((SnapshotApplication)this.getApplication()).getCaptureDatabase().getCaptureObjectArray();
	       
	       captureLabels = new String[capturesList.size()];
           SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
	       for (int i = 0; i < capturesList.size(); i++) {
	    	   String str = capturesList.get(i).getLabel() + ": ";
	    	   String tmp = formatter.format(new Date(capturesList.get(i).getStartTime()));
	    	   String timeDate = getTimeAndDate(tmp);
	    	   
	    	   str += timeDate;
	    	   captureLabels[i] = str;  	   
	       }
	   }
	   
	   @Override
	   public void onStart()
	   {
		   super.onStart();
		   updateList();
	   }
	   private String getTimeAndDate(String arrInfo) {
			String rtrnString;
			String date;
			String time;
			StringTokenizer st = new StringTokenizer(arrInfo, "/, - ,:");
			date = st.nextToken();
			int month = Integer.parseInt(st.nextToken());
			String year = st.nextToken();
			int hr = Integer.parseInt(st.nextToken());
			String min = st.nextToken();
			String sec = st.nextToken();
			String amPm = "AM";
			
			switch (month){
				case 1: date = "January " + date + ", " + year; break;
				case 2: date = "February " + date + ", " + year; break;
				case 3: date = "March " + date + ", " + year; break;
				case 4: date = "April " + date + ", " + year; break;
				case 5: date = "May " + date + ", " + year; break;
				case 6: date = "June " + date + ", " + year; break;
				case 7: date = "July " + date + ", " + year; break;
				case 8: date = "August " + date + ", " + year; break;
				case 9: date = "September " + date + ", " + year; break;
				case 10: date = "October " + date + ", " + year; break;
				case 11: date = "November " + date + ", " + year; break;
				case 12: date = "December " + date + ", " + year; break;
			}
			String hour ;
			if (hr > 12){
				 hour = Integer.toString(hr - 12);
				amPm = "PM";
			}else{
				hour = Integer.toString(hr);
			}
			time = hour + ":" + min + ":" + amPm;
			rtrnString = date + " - " + time;
			return rtrnString;
		}
	   
}
