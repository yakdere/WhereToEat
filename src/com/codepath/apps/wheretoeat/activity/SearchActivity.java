package com.codepath.apps.wheretoeat.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.wheretoeat.R;
import com.codepath.apps.wheretoeat.YelpApp;
import com.codepath.apps.wheretoeat.models.Restaurant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

	private LocationClient locationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		//find a better title
		getActionBar().setTitle("Where To Eat?");
		locationClient = new LocationClient(this, this, this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	@Override
	protected void onResume() {
		super.onResume();
		locationClient.connect();
	}
	@Override
	protected void onPause() {
		super.onPause();
		locationClient.disconnect();
	}
	@Override
	public void onConnectionFailed(ConnectionResult fail) {
		Toast.makeText(this, "Connection Failed" + fail.toString(), Toast.LENGTH_LONG).show();

	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	public void onConnected(Bundle arg0) {
		Location loc = locationClient.getLastLocation();
		if (loc != null) {
			Log.d("DEBUG", "location=" + loc.toString());
		}
		Log.d("DEBUG", "Current location is:" + loc.getLatitude() + loc.getLongitude());
	}
	@Override
	public void onDisconnected() {
	}

	//attached the Button onClick in activity_search layout
	public void onfindRestaurant(View v) {
		YelpApp.getRestClient().search("restaurant", locationClient.getLastLocation(), new JsonHttpResponseHandler() {
			// convert response to objects, create intent, pass to results activity
			@Override
			public void onSuccess(int httpResponse, JSONObject jsonResponse) {
				try {
					ArrayList<Restaurant> restaurants = Restaurant.fromJson(jsonResponse.getJSONArray("businesses"));
					Log.d("DEBUG", jsonResponse.getJSONArray("businesses").toString());
					sendIntent(restaurants);
				} catch (JSONException e) {
					e.printStackTrace();
				}	
			}                        
			@Override
			public void onFailure(Throwable e, JSONObject error) {
				Log.e("ERROR", e.toString() + error.toString());
				Toast.makeText(SearchActivity.this, "Unable to access Yelp", Toast.LENGTH_SHORT).show();
			}   
		});
	}
	private void sendIntent(ArrayList<Restaurant> restaurants) {
		Restaurant lastone = (Restaurant) getIntent().getSerializableExtra("last restaurant");
		if( lastone != null && restaurants.contains(lastone)) { 
			restaurants.remove(restaurants.indexOf(lastone));	
		}
		Intent i = new Intent(SearchActivity.this, SearchResultActivity.class);
		i.putExtra("restaurants", restaurants);
		i.putExtra("latitude", locationClient.getLastLocation().getLatitude());
		i.putExtra("longitude", locationClient.getLastLocation().getLongitude());
		startActivityForResult(i, 2);
		overridePendingTransition(R.anim.in_right, R.anim.out_left);
	}
	public void onHistory(MenuItem mi) {
		Intent i = new Intent(this, HistoryActivity.class);
		startActivityForResult(i, 2);
		overridePendingTransition(R.anim.in_right, R.anim.out_left);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.in_left, R.anim.out_right);
	}
}



