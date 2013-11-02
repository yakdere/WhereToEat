package com.codepath.apps.wheretoeat.activity;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.wheretoeat.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
/*
 * added Location Listener
 */

public class SearchActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{
	private LocationClient locationClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// check Google Play service APK is available and up to date.
        // see http://developer.android.com/google/play-services/setup.html
		final int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (result != ConnectionResult.SUCCESS) {
			// direct user to update google play services if no workie
			GooglePlayServicesUtil.getErrorDialog(result, this, 0).show();
		}
		locationClient = new LocationClient(this, this, this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show();

	}
	@Override
	public void onConnected(Bundle arg0) {
		Location loc = locationClient.getLastLocation();
		Log.d("DEBUG", "location=" + loc.toString());

	}
	@Override
	public void onDisconnected() {
	}
	
	//attached the Button onClick in activity_search layout
	public void findRestaurant(View v) {
		
	}
	
	//Define the location update callback by implementing LocationListener
	@Override
	public void onLocationChanged(Location location) {
		String msg = "Updated Location: " + Double.toString(location.getLatitude()) + "," 
				+ Double.toString(location.getLongitude());
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		
	}
	@Override
	public void onProviderDisabled(String provider) {
	}
	@Override
	public void onProviderEnabled(String provider) {
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}


