package edu.stanford.jdiprete;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.sax.StartElementListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; 
import com.google.android.maps.OverlayItem;
import android.app.Activity;



public class ItemizedOverlay extends com.google.android.maps.ItemizedOverlay {


	public ItemizedOverlay(Context context, Drawable defaultMarker, Long captureId) {
			super(boundCenterBottom(defaultMarker));
			// TODO Auto-generated constructor stub
			mContext = context;
			this.captureId = captureId;
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}
	
	
	/**
	 * Called when the user clicks on one of the icons
	 *   in the map. It uses a Toast to say hello.
	 * @param pIndex is the Find's index in the ArrayList
	 */
	@Override
	protected boolean onTap(int pIndex) {
		Dialog dialog = new Dialog(mContext);
		dialog.setCanceledOnTouchOutside(true);
		
		
		OverlayItem item = mOverlays.get(pIndex);
		dialog.setContentView(R.layout.custom_dialog);
		dialog.setTitle(item.getTitle());

		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(item.getSnippet());
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		
		image.setImageResource(R.drawable.androidmarker);
		dialog.show();
		
		//Get contact
		StringTokenizer st = new StringTokenizer(item.getTitle(), "  ,      ");
		String temp = st.nextToken();
		final String contact = st.nextToken();		
		
		
		final Button button = (Button) dialog.findViewById(R.id.more_info_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent();
                myIntent.setClassName("edu.stanford.jdiprete", "edu.stanford.jdiprete.DialogOnclick");
                myIntent.putExtra("contact", contact);
                myIntent.putExtra("captureId", captureId);
                mContext.startActivity(myIntent); 
            }
        });
		return true;
	}
	

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private long captureId;
}
