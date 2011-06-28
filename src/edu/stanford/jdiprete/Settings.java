package edu.stanford.jdiprete;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;

public class Settings extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		final Button add_button = (Button) findViewById(R.id.addButton);
		add_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent();
				myIntent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.AutoCapture");
				startActivity(myIntent); 
			}
		});
	}
}

