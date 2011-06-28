package edu.stanford.jdiprete;

import java.sql.Time;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug.IntToString;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DialogOnclick extends Activity {
	private ListView lv1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_onclick);
		//grabs database info
		final Long captureId = this.getIntent().getLongExtra("captureId", 0);
		CaptureObject captureObject = ((SnapshotApplication)this.getApplication()).getCaptureDatabase().getCaptureObjectForId(captureId);
		ArrayList<TextMessage> txtMessageObjectList;
		txtMessageObjectList = ((SnapshotApplication)this.getApplication()).getTextMessageDataBase().getTextMessagesForCapture(captureObject);
		//local variables
		final ArrayList<TextMessage> conversationArray = new ArrayList<TextMessage>();
		String contact = this.getIntent().getStringExtra("contact");
		//ArrayList<String> stringArray= new ArrayList<String>();
		String contactString = "Conversation with " + contact;
		TextView conversationWithX = (TextView)findViewById(R.id.conversationWithX);
		conversationWithX.setText((CharSequence) contactString);
		populateConversationArray(txtMessageObjectList, conversationArray, contact);
		String[] strArray = printOutConversation(conversationArray);
		lv1=(ListView)findViewById(R.id.ListView01);
		lv1.setAdapter(new ArrayAdapter<String>(this,R.layout.archive, strArray));
		
		
        // set listview divider color
        int[] colors = {0, 0xFF49C1E0, 0}; // blue for the example
        lv1.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
        lv1.setDividerHeight(1);
        
        int[] selectorColors = {0xFF3D3D3D, 0xFF49C1E0, 0xFF3D3D3D};
        lv1.setSelector(new GradientDrawable(Orientation.RIGHT_LEFT, selectorColors));
        
        lv1.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {

	        	  // grab contact from selected message
	        	  String contact = conversationArray.get(position).getPerson();
	        	  
			      Intent myIntent = new Intent();
			      myIntent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.DialogOnclick");
			      myIntent.putExtra("contact", contact);
			      myIntent.putExtra("captureId", captureId);
			      startActivity(myIntent); 
	          }
	        });
	}

	private String[] printOutConversation(ArrayList<TextMessage> conversationArray) {
		String[] strArray = new String[conversationArray.size()];
		for (int i = 0; i < conversationArray.size(); i++){
			Boolean isIncomming = conversationArray.get(i).isIncoming();
			String toOrFrom;
			if (isIncomming){
				toOrFrom = conversationArray.get(i).getPerson() + " -  ";
			}else{
				toOrFrom = "You -  ";
			}
			String tmp = conversationArray.get(i).getTime().toString();
			String time = getTime(tmp);
			String str = toOrFrom + time + "\n" + conversationArray.get(i).getBody();
			strArray[i] = str;
		}
		return strArray;
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
	
	private void populateConversationArray(ArrayList<TextMessage> txtMessageObjectList,
			ArrayList<TextMessage> conversationArray, String contact) {
		for (int i = 0; i < txtMessageObjectList.size(); i++) {
			String tempContact = txtMessageObjectList.get(i).getPerson();
			//Toast.makeText(getApplicationContext(), "l" + contact +"l", Toast.LENGTH_SHORT).show();
			//Toast.makeText(getApplicationContext(), "l" + tempContact + "l//", Toast.LENGTH_SHORT).show();
			if(tempContact.equalsIgnoreCase(contact)){
				TextMessage addedNewMessage = txtMessageObjectList.get(i);
				conversationArray.add(addedNewMessage);
			}
			//String num = Integer.toString(conversationArray.size());
			//Toast.makeText(getApplicationContext(), num, Toast.LENGTH_LONG).show();
		}
	}
}