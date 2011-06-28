package edu.stanford.jdiprete;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DialogOnclickPlaces extends Activity {
	private ListView lvl;
		@Override
		public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// TODO Auto-generated constructor stub
			setContentView(R.layout.dialog_onclick_places);
		
		//grabs database info
			final Long captureId = this.getIntent().getLongExtra("captureId", 0);
			CaptureObject captureObject = ((SnapshotApplication)this.getApplication()).getCaptureDatabase().getCaptureObjectForId(captureId);
			final ArrayList<TextMessage> txtMessageObjectList;
			txtMessageObjectList = ((SnapshotApplication)this.getApplication()).getTextMessageDataBase().getTextMessagesForCapture(captureObject);
			long placeId = this.getIntent().getLongExtra("placeId", 0);
			Place place = ((SnapshotApplication)this.getApplication()).getPlaceDatabase().getPlaceForId(placeId);
		//local variables
			ArrayList<TextMessage> conversationArray = new ArrayList<TextMessage>();
			final ArrayList<TextMessage> tm_array = ((SnapshotApplication)this.getApplication()).getTextMessageDataBase().getTextMessagesForPlace(place, captureObject);
			
			String titleString = "Location Activity";
			TextView title = (TextView)findViewById(R.id.title);
			title.setText((CharSequence) titleString);
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			String arrInfo = formatter.format(new Date(place.getStartTime()));
			String dprInfo = formatter.format(new Date(place.getEndTime()));
			String getArrival = getTimeAndDate(arrInfo);
			String getDeparture = getTimeAndDate(dprInfo);
			String infoString = "Arrived: " + getArrival + "\n" +  "Departed: " + getDeparture;
			TextView information = (TextView)findViewById(R.id.info);
			information.setText((CharSequence) infoString);
			
			String[] strArray = buildArray(tm_array);
			lvl=(ListView)findViewById(R.id.ListView01);
			lvl.setAdapter(new ArrayAdapter<String>(this,R.layout.archive, strArray));
			
			lvl.setOnItemClickListener(new OnItemClickListener() {
		          public void onItemClick(AdapterView<?> parent, View view,
		              int position, long id) {

		        	  // grab contact from selected message
		        	  String contact = tm_array.get(position).getPerson();
		        	  
				      Intent myIntent = new Intent();
				      myIntent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.DialogOnclick");
				      myIntent.putExtra("contact", contact);
				      myIntent.putExtra("captureId", captureId);
				      startActivity(myIntent); 
		          }
		        });
			
			
	        // set listview divider color
	        int[] colors = {0, 0xFF49C1E0, 0}; // blue for the example
	        lvl.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
	        lvl.setDividerHeight(1);
	        
	        int[] selectorColors = {0xFF3D3D3D, 0xFF49C1E0, 0xFF3D3D3D};
	        lvl.setSelector(new GradientDrawable(Orientation.RIGHT_LEFT, selectorColors));
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
		
		
		
		private String[] buildArray(ArrayList<TextMessage> tm_array) {
			String[] xArray = new String[tm_array.size()];
			for (int f=0; f<tm_array.size(); f++){
				Boolean isIncomming = tm_array.get(f).isIncoming();
				String toOrFrom;
				if (isIncomming){
					toOrFrom = "From: ";
				}else{
					toOrFrom = "To: ";
				}
				String tmp = tm_array.get(f).getTime().toString();
				String time = getTime(tmp);
				String xFinalStr = toOrFrom + tm_array.get(f).getPerson() + "  -   " + time + "\n" + tm_array.get(f).getBody();
				xArray[f] = xFinalStr;
			}
			return xArray;
		}
		
		private String getTime (String Info) {
			String rtrnString;
			StringTokenizer st = new StringTokenizer(Info, ":");
			int hr = Integer.parseInt(st.nextToken());
			String mins = st.nextToken();
			String sec = st.nextToken();
			String amPm = "AM";
			String hour ;
			if (hr > 12){
				 hour = Integer.toString(hr - 12);
				amPm = "PM";
			}else{
				hour = Integer.toString(hr);
			}
			rtrnString = hour + ":" + mins + amPm;
			return rtrnString;
		}
}
