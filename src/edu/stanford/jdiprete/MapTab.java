package edu.stanford.jdiprete;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MapTab extends MapActivity {
	
	private CaptureObject captureObject;	
	private ArrayList<TextMessage> textMessageObjectList;
	private ArrayList<Location> gpsTags;
	private ArrayList<Place> places;
	
	//overlay variables
    private List<Overlay> mOverlayList;
    private Drawable drawable;
    //private ItemizedOverlay itemizedOverlay;
    private TextItem textItem;
    private List<Overlay> placesOverlayList;
    private Drawable placesDrawable;
    //private ItemizedOverlay placesItemizedOverlay;
    private PlaceItem placeItem;
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	    	
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.map);
    	
	    // grab capture id so we know the info to populate this view with
	    long captureId = this.getIntent().getLongExtra("captureId", 0);
	    Log.d("CaptureID", "ID is: " + captureId);
	    captureObject = ((SnapshotApplication)this.getApplication()).getCaptureDatabase().getCaptureObjectForId(captureId);
	    textMessageObjectList = ((SnapshotApplication)this.getApplication()).getTextMessageDataBase().getTextMessagesForCapture(captureObject);
	    //textMessageObjectList = ((SnapshotApplication)this.getApplication()).getTextMessageDataBase().getTextMessages();
	     gpsTags = ((SnapshotApplication)this.getApplication()).getGPSDatabase().getGPSArrayForCapture(captureObject);
	    Log.d("GPSArray", "Size is: " + gpsTags.size());
	    Log.d("GPSARRAY", "Total Size: " + ((SnapshotApplication)this.getApplication()).getGPSDatabase().getGPSArray().size());
	    Log.d("TextMessageArray", "Size is: " + textMessageObjectList.size());
	    places = ((SnapshotApplication)this.getApplication()).getPlaceDatabase().getPlacesForCapture(captureObject);
	    

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
	    for (Place p: places)
	    {
	    	Log.d("PLACE", "Start time: " + formatter.format(new Date(p.getStartTime())) + " End Time: " + formatter.format(p.getEndTime()));
	    }
	    // setup map ish
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        MapController mapController = mapView.getController();
        
        centerAndZoom(mapController);
        
        drawPath(mapView);
        
        addPlaces(mapView, captureId);
    
        addTexts(mapView, captureId);

    }
	
	
	//Centers and Zooms the Map based on the GPS path
	
	private void centerAndZoom(MapController mapController)
	{
        double avg_lat = 0, avg_lon = 0, min_lat = 0, max_lat = 0, min_lon = 0, max_lon = 0;
        for (int i = 0; i < gpsTags.size(); i++)
        {
        	double current_lat = gpsTags.get(i).getLatitude();
        	double current_lon = gpsTags.get(i).getLongitude();
        	avg_lat += current_lat;
        	avg_lon += current_lon;
        	if (i == 0) {
        		min_lat = current_lat;
        		min_lon = current_lon;
        		max_lat = current_lat;
        		max_lon = current_lon;
        	}
        	if (current_lat > max_lat) max_lat = current_lat;
        	if (current_lon > max_lon) max_lon = current_lon;
        	if (current_lat < min_lat) min_lat = current_lat;
        	if (current_lon < min_lon) min_lon = current_lon;
        }
        double latSpanForCapture = Math.abs(max_lat - min_lat);
        double lonSpanForCapture = Math.abs(max_lon - min_lon);
        mapController.zoomToSpan((int)(latSpanForCapture * 1e6), (int)(lonSpanForCapture * 1e6));
        
        Log.d("message", "LatSpan: " + Double.toString(latSpanForCapture));
        Log.d("message2", "LonSpan: " + Double.toString(lonSpanForCapture));

        if (gpsTags.size() > 0)
        {
            avg_lat /= gpsTags.size();
            avg_lon /= gpsTags.size();
            GeoPoint pt = new GeoPoint((int)(avg_lat * 1e6), (int)(avg_lon * 1e6));
        	mapController.setCenter(pt);
        }
	}
	
	
	//draws the path based on the gps coordinates
	
	private void drawPath(MapView mapView)
	{
        GeoPoint geoPoint1;
        GeoPoint geoPoint2;
        for( int i = 0; i < gpsTags.size()-1; i++ ){
        	
        	geoPoint1 = new GeoPoint( (int)(gpsTags.get(i).getLatitude() * 1e6), (int)(gpsTags.get(i).getLongitude() * 1e6) );
        	geoPoint2 = new GeoPoint( (int)(gpsTags.get(i+1).getLatitude() * 1e6), (int)(gpsTags.get(i+1).getLongitude() * 1e6) );

	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        	Log.d("LOCATION", "Location: " + gpsTags.get(i).getLatitude() + ", " + gpsTags.get(i).getLongitude() + " TIME: " + formatter.format(new Date(gpsTags.get(i).getTime())));
        	MapOverlay mapOverlay = new MapOverlay(geoPoint1, geoPoint2);
        	mapView.getOverlays().add(mapOverlay);
        }
        
	}
	
	//adds the text items to the map
	
	private void addTexts(MapView mapView, long captureId)
	{
        // overlays for text messages
        mOverlayList = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.pin_drop_message_gen1);
        textItem = new TextItem(this, drawable, captureId);
        ArrayList<Location> multiple_places = new ArrayList<Location>();   
        
        GeoPoint geoPoint = null;
        ArrayList<ItemizedOverlay> IOArrayList = new ArrayList<ItemizedOverlay>();
        // loop through texts to drop pins with corresponding body for each text
        String toOrFrom;
        for( int i = 0; i < textMessageObjectList.size(); i++ ) {
        	TextMessage text_message = textMessageObjectList.get(i);
        	Location textLocation = text_message.getLocation();
        	
        	//Check if text is at a place, don't add if it is
        	boolean is_at_place = false;
        	for (Place place: places)
        	{
        		if ((new Date(text_message.getLongDate())).after(new Date(place.getStartTime())) && (new Date(text_message.getLongDate())).before(new Date(place.getEndTime()))
        				|| place.getLocation().distanceTo(text_message.getLocation())< 5)
        		{
        			is_at_place = true;
        		}
        	}
        	
        	
        	//Check if text is a multi-text
        	
        	Log.d("MULTIDebug", "Text: " + textMessageObjectList.get(i).getBody() + " is at place? " + is_at_place);
        	boolean is_multiple_texts = false;
        	if (!is_at_place)
        	{
	        	for (int j = 0; j <  textMessageObjectList.size(); j++)
	        	{
	        		if (i != j)
	        		{
	        			TextMessage tm = textMessageObjectList.get(j);
	        			if (tm.getLocation().distanceTo(textLocation) < 15)
	        			{
	        				is_multiple_texts = true;
	        				boolean is_added = false;
	        				for (Location l: multiple_places)
	        				{
	        					if (tm.getLocation().distanceTo(l) < 5)
	        					{
	        						is_added = true;
	        					}
	        				}
	        				if (!is_added && !is_at_place)
	        				{
	        					multiple_places.add(tm.getLocation());
	        				}
	        			}
	        		}
	        	}
        	}
        	
        	
        	//Create the array of text messages for each multitext place
        	
        	ArrayList<ArrayList<TextMessage>> multiple_text_array = new ArrayList<ArrayList<TextMessage>>();
            for (int j = 0; j < multiple_places.size(); j++)
            {
            	Location l = multiple_places.get(j);
            	ArrayList<TextMessage> text_messages = new ArrayList<TextMessage>();
            	for (TextMessage tm: textMessageObjectList)
            	{
            		if (tm.getLocation().equals(l))
            		{
            			text_messages.add(tm);
            		}
            	}
            	multiple_text_array.add(text_messages);
            }
        	((SnapshotApplication)this.getApplication()).getCaptureDatabase().setMultiplePlaceArray(multiple_text_array);
        	
        	//Add texts that aren't multi-texts or place texts
        	
        	if (!is_at_place && !is_multiple_texts)
        	{
        	
	        	String textBody = textMessageObjectList.get(i).getBody();
	        	Boolean isIncomming = textMessageObjectList.get(i).isIncoming();
	        	
	        	
	        	if (isIncomming){
	        		toOrFrom = "From:  ";
	        	}else{
	        		toOrFrom = "To:  ";
	        	}
	        	String timeTmp = textMessageObjectList.get(i).getTime().toString();
	        	String time = getTime(timeTmp);
	        	String title = toOrFrom + textMessageObjectList.get(i).getPerson() + "      " + time;
	        
	        	// get geotag info from location
	        	Double latitude = textLocation.getLatitude();
	        	Double longitude = textLocation.getLongitude();
	        	geoPoint = new GeoPoint((int)(latitude * 1e6), (int)(longitude * 1e6));
	        	
	        	// assign overlay item as text body and add to itemizedOverlay
	        	OverlayItem overlayItem = new OverlayItem(geoPoint, title, textBody);
	
	        	textItem.addOverlay(overlayItem);
	        	IOArrayList.add(textItem);
        	}
        }

        //drop icons for texts
        for (int i=0; i < IOArrayList.size(); i++){
        	textItem = (TextItem)IOArrayList.get(i);
        	mOverlayList.add(textItem);
        }
        
        //create overlays for multiple texts
        for (int i = 0; i < multiple_places.size(); i++)
        {
        	Location l = multiple_places.get(i);
        	Log.d("MULTIPLEPlace", "Location: " + l);
        	ArrayList<TextMessage> text_messages = ((SnapshotApplication)this.getApplication()).getCaptureDatabase().getMultipleTextMessagesForIndex(i);
        	drawable = this.getResources().getDrawable(R.drawable.pin_drop_multi_txt);
        	MultiTextItem multiTxt = new MultiTextItem(this, drawable, captureId, i);
        	Double latitude = l.getLatitude();
        	Double longitude = l.getLongitude();
        	geoPoint = new GeoPoint((int)(latitude * 1e6), (int)(longitude * 1e6));
        	String snippet = Integer.toString(text_messages.size()) + " text messages.";
        	OverlayItem xLocation = new OverlayItem(geoPoint, "Text Messages", snippet);
        	multiTxt.addOverlay(xLocation);
        	mOverlayList.add(multiTxt);
        }
	}
	
	
	//adds the places items to the map
	
	private void addPlaces(MapView mapView, long captureId)
	{
        // overlays for places
        placesOverlayList = mapView.getOverlays();
        placesDrawable = this.getResources().getDrawable(R.drawable.androidmarker);
        
        // add places to map
        GeoPoint placeGeoPoint = null;
        ArrayList<ItemizedOverlay> placesIOList = new ArrayList<ItemizedOverlay>();
        for( int i = 0; i < places.size(); i++ ) {
            placeItem = new PlaceItem(this, placesDrawable, captureId, places.get(i), ((SnapshotApplication)this.getApplication()).getTextMessageDataBase().getTextMessagesForPlace(places.get(i), captureObject));
        	
        	// get location object and arrival/departure times for this place object
        	Location placeLocation = places.get(i).getLocation();
        	long arrivalTime = places.get(i).getStartTime();
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
	        
	        String arr = formatter.format(new Date(arrivalTime));
	        
	        String arrival_string = getTimeAndDate(arr); 
        	
	        long departureTime = places.get(i).getEndTime();
	        String dpr = formatter.format(new Date(departureTime));
	        String departure_string = getTimeAndDate(dpr); 
        	
	        String body = "Arrived: " + arrival_string + "\n" + "Departed: " + departure_string;

	        
        	// place geotag info
        	Double latitude = placeLocation.getLatitude();
        	Double longitude = placeLocation.getLongitude();
        	placeGeoPoint = new GeoPoint((int)(latitude * 1e6), (int)(longitude * 1e6));
        	
        	// assign overlay with title to this location
        	OverlayItem overlayItem = new OverlayItem(placeGeoPoint, "Location Pin", body);
        	placeItem.addOverlay(overlayItem);
        	placesIOList.add(placeItem);
        	
        }
        
        // drop markers for places overlays
        for (int i=0; i < placesIOList.size(); i++){
        	placeItem = (PlaceItem)placesIOList.get(i);
        	placesOverlayList.add(placeItem);
        }
        
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
	
	
	private class MapOverlay extends com.google.android.maps.Overlay {

	      private GeoPoint mGpt1;
	      private GeoPoint mGpt2;

	      protected MapOverlay(GeoPoint gp1, GeoPoint gp2 ) {
	         mGpt1 = gp1;
	         mGpt2 = gp2;
	      }
	      @Override
	      public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
	            long when) {
	         super.draw(canvas, mapView, shadow);
	         Paint paint;
	         paint = new Paint();
//	         paint.setColor(Color.BLUE);
	         paint.setColor(0xFF49C1E0);	// line color is blue used in rest of app
	         paint.setAntiAlias(true);
	         paint.setStyle(Style.STROKE);
	         paint.setStrokeWidth(6);
	         Point pt1 = new Point();
	         Point pt2 = new Point();
	         Projection projection = mapView.getProjection();
	         projection.toPixels(mGpt1, pt1);
	         projection.toPixels(mGpt2, pt2);
	         canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
	         return true;
	      }
	   }
}