package com.example.googleimagesearch;

import java.net.URI;
import java.security.spec.EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	Button btnSearch;
	EditText etSearch;
	GridView gvResults;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	private final int REQUEST_CODE = 998;
	SearchOption searchOption;
	String queryString = "";
	String query="";
	Map<String, String> sizeMap = new HashMap<String, String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setUpViews();
		setUpMaps();
		searchOption = new SearchOption("small","blue","faces","google.com");
		
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("resultImage", imageResult);
				startActivity(i);
			}

		
		});
		
        gvResults.setOnScrollListener(new EndlessScrollListener() {
	    @Override
	    public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
	        customLoadMoreDataFromApi(page); 
                // or customLoadMoreDataFromApi(totalItemsCount); 
	    }
        });
	}
	
    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	AsyncHttpClient client = new AsyncHttpClient();
		//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=%s&start=%d&imgsz=medium
		//client.get("https://ajax.googleapis.com/ajax/services/search/images?"+"start="+0+"&v=1.0&q="+Uri.encode(query),
		client.get(getQueryString(offset*8) + Uri.encode(query),
				new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response){
				JSONArray imageJsonResults = null;
				try{
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));	
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			
		});
    	
    	
    }

	private String getQueryString(int offset){
		String queryStr = "https://ajax.googleapis.com/ajax/services/search/images?"+"&rsz=8&v=1.0"+
							"&imgsz="+sizeMap.get(searchOption.getImageSize()) +
							"&imgcolor="+searchOption.getColorFilter() +
							"&imgtype="+searchOption.getImageType()+
							"&as_sitesearch="+searchOption.getSiteFilter()+
							"&start="+offset+
							"&q=";
		
		return queryStr;
	}
	public void onShowSettings(MenuItem mi){
		Intent i = new Intent(SearchActivity.this, SearchOptionsActivity.class);
		i.putExtra("searchoption",searchOption);
		startActivityForResult(i,REQUEST_CODE);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	private void setUpViews(){
		btnSearch = (Button) findViewById(R.id.btnSearch);
		etSearch = (EditText) findViewById(R.id.etSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
	}
	
	private void setUpMaps(){
		sizeMap.put("small", "icon");
		sizeMap.put("medium","medium");
		sizeMap.put("large","xxlarge");
		sizeMap.put("extralarge", "huge");
	}
	
	public void onImageSearch(View v){

		query = etSearch.getText().toString();
		if(query.isEmpty()){
			Toast.makeText(this, "Please enter a search item", Toast.LENGTH_SHORT).show();
			return;
		}
		imageResults.clear();
		Toast.makeText(this, "Searching for "+query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=%s&start=%d&imgsz=medium
		//client.get("https://ajax.googleapis.com/ajax/services/search/images?"+"start="+0+"&v=1.0&q="+Uri.encode(query),
		client.get(getQueryString(0) + Uri.encode(query),
				new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response){
				JSONArray imageJsonResults = null;
				
				try{
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
		  searchOption = (SearchOption)data.getSerializableExtra("searchoption");

	  }
	} 
	
	

}
