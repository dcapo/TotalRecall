package edu.stanford.jdiprete;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class Visualizer extends TabActivity {
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	        setContentView(R.layout.visualizer);
	        
	        Long captureId = this.getIntent().getLongExtra("captureId", 0);
	        
	        Resources res = getResources(); // Resource object to get Drawables
	        TabHost tabHost = getTabHost();  // The activity TabHost
	        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	        Intent intent;  // Reusable Intent for each tab

	        // Create an Intent to launch an Activity for the tab (to be reused)
	        intent = new Intent().setClass(this, MapTab.class);
	        intent.putExtra("captureId", captureId);

	        // Initialize a TabSpec for each tab and add it to the TabHost
	        spec = tabHost.newTabSpec("MapTab").setIndicator("Map",
	                          res.getDrawable(R.drawable.compass))
	                      .setContent(intent);
	        tabHost.addTab(spec);

	        // Do the same for the other tabs
	        intent = new Intent().setClass(this, TimeViewTab.class);
	        intent.putExtra("captureId", captureId);
	        spec = tabHost.newTabSpec("TimeViewTab").setIndicator("Time",
	                          res.getDrawable(R.drawable.clock))
	                      .setContent(intent);
	        tabHost.addTab(spec);

	        tabHost.setCurrentTab(0);
	        
	   }
}
