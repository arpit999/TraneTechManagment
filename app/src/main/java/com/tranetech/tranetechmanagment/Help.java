package com.tranetech.tranetechmanagment;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.widget.TextView;

public class Help extends Activity {

	TextView text_Site;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		// get action bar
		ActionBar action = getActionBar();

		// Enabling Up / Back navigation
		action.setDisplayHomeAsUpEnabled(true);

		text_Site = (TextView) findViewById(R.id.text_Site);

		Linkify.addLinks(text_Site, Linkify.WEB_URLS);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
		//	NavUtils.navigateUpFromSameTask(this);
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
