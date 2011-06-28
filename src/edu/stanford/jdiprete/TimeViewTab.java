package edu.stanford.jdiprete;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class TimeViewTab extends ListActivity {
	
	   private CaptureObject captureObject;	
	   private ArrayList<TextMessage> textMessageObjectList;
	   private String[] textMessages;
	   private Long captureId;
	
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	        
	       // grab capture id and capture object so we know the info to populate this view with
	       captureId = this.getIntent().getLongExtra("captureId", 0);
	       captureObject = ((SnapshotApplication)this.getApplication()).getCaptureDatabase().getCaptureObjectForId(captureId);
	       textMessageObjectList = ((SnapshotApplication)this.getApplication()).getTextMessageDataBase().getTextMessagesForCapture(captureObject);
	       
	       // iterate through text message list and grab text to populate listview with
	       textMessages = new String[textMessageObjectList.size()];
	       for( int i = 0; i < textMessageObjectList.size(); i++ ) {
	    	   
	    	   Boolean isIncomming = textMessageObjectList.get(i).isIncoming();
	    	   String toOrFrom;
	    	   if (isIncomming){
	        		toOrFrom = "From: " + textMessageObjectList.get(i).getPerson() + " -  ";
	    	   }else{
	        		toOrFrom = "To: " + textMessageObjectList.get(i).getPerson() +" -  ";
	    	   }
	    	   String tmp = textMessageObjectList.get(i).getTime().toString();
	    	   String time = getTime(tmp);
	    	   String str = toOrFrom + time + "\n" + textMessageObjectList.get(i).getBody();
	    	   textMessages[i] = str;
	       }    

	       
	        // create list view of texts
	        setListAdapter(new ArrayAdapter<String>(this, R.layout.archive, textMessages));

	        ListView lv = getListView();
	        lv.setTextFilterEnabled(true);

	        lv.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {
	            
	              String contact = textMessageObjectList.get(position).getPerson();
	        	  
			      Intent myIntent = new Intent();
			      myIntent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.DialogOnclick");
			      myIntent.putExtra("contact", contact);
			      myIntent.putExtra("captureId", captureId);
			      startActivity(myIntent);
	          }
	        });
	        
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
	   
	   static final String[] TEXTS = new String[] { "text1", "text2", "text3" };
}