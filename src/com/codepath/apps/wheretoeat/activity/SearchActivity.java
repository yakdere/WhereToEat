package com.codepath.apps.wheretoeat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.codepath.apps.wheretoeat.R;

public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	/**
	YelpClient client = YelpClientApp.getRestClient();
	client.search("food", "san francisco", new JsonHttpResponseHandler() {
	  @Override
	  public void onSuccess(int code, JSONObject body) {
	    try {
	      JSONArray jsonArray = body.getJSONArray("businesses");
	      ArrayList<Restaurant> rs = Restaurant.fromJson(jsonArray);
	      // Now we have an array of restaurant objects
	      // Might now create an ArrayAdapter<Restaurant> to load the restaurants into a ListView
	    } catch (JSONException e) {
	      e.printStackTrace();
	    }
	  }

	  @Override
	  public void onFailure(Throwable arg0) {
	    Toast.makeText(getBaseContext(), "FAIL", Toast.LENGTH_LONG).show();
	  }
	});
	*/
}
