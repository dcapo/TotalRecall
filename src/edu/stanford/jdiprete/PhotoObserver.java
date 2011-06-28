package edu.stanford.jdiprete;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

public class PhotoObserver extends ContentObserver {
	private Handler p_handler = null;
	private Snapshot snapshot;
	
	public PhotoObserver(Handler handler, Snapshot s) {
		super(handler);
		p_handler = handler;
		snapshot = s;
	}
	
	@Override public boolean deliverSelfNotifications() { 
		return false; 
	}
	
	@Override public void onChange(boolean arg0) { 
		super.onChange(arg0); 
		Cursor cc = snapshot.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,null);  
	}
}
