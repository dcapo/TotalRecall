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

public class MultiTextListView extends Activity{

	private Long captureId;
	private ArrayList<TextMessage> textMessages;
	
	private ListView lv1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_onclick);
		
		//grabs database info
		captureId = this.getIntent().getLongExtra("captureId", 0);
		CaptureObject captureObject = ((SnapshotApplication)this.getApplication()).getCaptureDatabase().getCaptureObjectForId(captureId);
		
		//grab list of texts for this multi text location
		int textIndex = this.getIntent().getIntExtra("textIndex", -1);
		textMessages = ((SnapshotApplication)this.getApplication()).getCaptureDatabase().getMultipleTextMessagesForIndex(textIndex);
		
		String multiTextsTitle = "Text Messages"; //+ contact;
		TextView multiTextsView = (TextView)findViewById(R.id.conversationWithX);
		multiTextsView.setText((CharSequence) multiTextsTitle);
		
		String[] strArray = printOutConversation(textMessages);
		lv1=(ListView)findViewById(R.id.ListView01);
		lv1.setAdapter(new ArrayAdapter<String>(this,R.layout.archive, strArray));
		
		// allow list to be filtered by entered text
        lv1.setTextFilterEnabled(true);
        
        // set listview divider color
        int[] colors = {0, 0xFF49C1E0, 0}; // blue for the example
        lv1.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
        lv1.setDividerHeight(1);
        
        int[] selectorColors = {0xFF3D3D3D, 0xFF49C1E0, 0xFF3D3D3D};
        lv1.setSelector(new GradientDrawable(Orientation.RIGHT_LEFT, selectorColors));

        // allow texts to be clicked an pull up conversation
        lv1.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {

        	  // grab contact from selected message
        	  String contact = textMessages.get(position).getPerson();
        	  
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
				toOrFrom = "From: " + conversationArray.get(i).getPerson() + " -  ";
			}else{
				toOrFrom = "To: " + conversationArray.get(i).getPerson() + " -  ";
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
	
//	private void populateConversationArray(ArrayList<TextMessage> txtMessageObjectList,
//			ArrayList<TextMessage> conversationArray, String contact) {
//		for (int i = 0; i < txtMessageObjectList.size(); i++) {
//			String tempContact = txtMessageObjectList.get(i).getPerson();
//			//Toast.makeText(getApplicationContext(), "l" + contact +"l", Toast.LENGTH_SHORT).show();
//			//Toast.makeText(getApplicationContext(), "l" + tempContact + "l//", Toast.LENGTH_SHORT).show();
//			if(tempContact.equalsIgnoreCase(contact)){
//				TextMessage addedNewMessage = txtMessageObjectList.get(i);
//				conversationArray.add(addedNewMessage);
//			}
//			//String num = Integer.toString(conversationArray.size());
//			//Toast.makeText(getApplicationContext(), num, Toast.LENGTH_LONG).show();
//		}
//	}
	
}
