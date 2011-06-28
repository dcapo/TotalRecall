package edu.stanford.jdiprete;

import java.sql.Time;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AutoCapture extends Activity {
	private Button b1, b2, save_button;
	private BroadcastReceiver start_receiver;
	private BroadcastReceiver end_receiver;
	private Intent gps_intent;
	private int start_hour;
	private int start_minute;
	private int end_hour;
	private int end_minute;
	
	static final int START_TIME_DIALOG_ID = 0;
	static final int END_TIME_DIALOG_ID = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto_capture);
		
	    start_receiver = new BroadcastReceiver()
		{

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				if (((SnapshotApplication)getApplication()).getCapturing())
        		{
        			startService(gps_intent);
        		}
			}
			
		};
		
	    end_receiver = new BroadcastReceiver()
		{

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				if (((SnapshotApplication)getApplication()).getCapturing())
        		{
        			stopService(gps_intent);
        		}
			}
			
		};
		
		final EditText nameCapture = (EditText) findViewById(R.id.nameThisCapture);
		nameCapture.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					Toast.makeText(AutoCapture.this, nameCapture.getText(), Toast.LENGTH_SHORT).show();
					return true;
				}
				return false;
			}
		});
		//Start Day:
		final Spinner spinner = (Spinner) findViewById(R.id.start_spinner);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.day_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    //Start Time:
		b1 = (Button) findViewById(R.id.startButton);
		b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(START_TIME_DIALOG_ID);
			}
		});
		//End Day:
	    Spinner spinner2 = (Spinner) findViewById(R.id.end_spinner);
	    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
	            this, R.array.day_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner2.setAdapter(adapter2);
	    //End Time:
		b2 = (Button) findViewById(R.id.endButton);
		b2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(END_TIME_DIALOG_ID);
			}
		});
		save_button = (Button) findViewById(R.id.saveButton);
		save_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				//get start time
				
				Calendar calendar = Calendar.getInstance();
				int current_day = Calendar.DAY_OF_WEEK;
				int set_day = spinner.getSelectedItemPosition();
				int difference = set_day - current_day;
				if (difference < 0)
				{
					difference = 7 - difference;
				}
				
				
				
				
				//get end time
				
				
				
			}
		}
		);
	}
	
	private TimePickerDialog.OnTimeSetListener mStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			start_hour = hourOfDay;
			start_minute = minute;
		}
	};
	private TimePickerDialog.OnTimeSetListener mEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			end_hour = hourOfDay;
			end_minute = minute;
		}
	};
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case START_TIME_DIALOG_ID:
			return new TimePickerDialog(this, mStartTimeSetListener, 0, 0, false);
		case END_TIME_DIALOG_ID:
			return new TimePickerDialog(this, mEndTimeSetListener, 0, 0, false);
		}
		return null;
	}
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	      Toast.makeText(parent.getContext(), "The day of the week is " +
	          parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    }
	}
}
