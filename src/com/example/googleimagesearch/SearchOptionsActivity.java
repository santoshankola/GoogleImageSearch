package com.example.googleimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


public class SearchOptionsActivity extends Activity {

	Spinner spImageSize;
	Spinner spColorFilter;
	Spinner spImageType;
	EditText etSiteFiter;
	SearchOption searchOption;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_options);
		setUpViews();
		searchOption = (SearchOption) getIntent().getSerializableExtra("searchoption");
		setViewValues();
		
	}

	private void setViewValues(){
		setSpinnerToValue(spImageSize,searchOption.getImageSize());
		setSpinnerToValue(spColorFilter, searchOption.getColorFilter());
		setSpinnerToValue(spImageType, searchOption.getImageType());
		etSiteFiter.setText(searchOption.getSiteFilter());
	}
	public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
			}
		}
		spinner.setSelection(index);
	}
	
	public void onSaveOptions(View v){
		String value = spImageSize.getSelectedItem().toString();
		/*
		 SearchOption searchOption = new SearchOption(spImageSize.getSelectedItem().toString(),
													spColorFilter.getSelectedItem().toString(),
													spImageType.getSelectedItem().toString(),
													etSiteFiter.getText().toString());
													*/
		searchOption.setImageSize(spImageSize.getSelectedItem().toString());
		searchOption.setColorFilter(spColorFilter.getSelectedItem().toString());
		searchOption.setImageType(spImageType.getSelectedItem().toString());
		searchOption.setSiteFilter(etSiteFiter.getText().toString());
		
		 Intent data = new Intent();
		 data.putExtra("searchoption", searchOption);
		  // Activity finished ok, return the data
		 setResult(RESULT_OK, data); // set result code and bundle data for response

		 finish();
		
	}
	
	private void setUpViews(){
		spImageSize = (Spinner) findViewById(R.id.spinnerImageSize);
		spImageType = (Spinner) findViewById(R.id.spinnerImageType);
		spColorFilter = (Spinner) findViewById(R.id.spinnerColorFilter);
		etSiteFiter = (EditText) findViewById(R.id.etSiteFilter);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_options, menu);
		return true;
	}

}
