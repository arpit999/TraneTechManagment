package com.tranetech.tranetechmanagment;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.widget.TextView;

public class Contactus extends Activity {

	TextView tv_kerala_num, tv_infocity_emai, tv_UAE_email1, tv_UAE_No1,
			Tranetech_Site, tv_UAE_No2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_us);
		// get action bar
		ActionBar action = getActionBar();

		// Enabling Up / Back navigation
		action.setDisplayHomeAsUpEnabled(true);
		LoadId();
		LoadLink();
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

	private void LoadId() {

		tv_kerala_num = (TextView) findViewById(R.id.tv_kerala_num);
		tv_infocity_emai = (TextView) findViewById(R.id.tv_infocity_email);
		tv_UAE_email1 = (TextView) findViewById(R.id.tv_UAE_email1);
		tv_UAE_No1 = (TextView) findViewById(R.id.tv_UAE_No1);
		Tranetech_Site = (TextView) findViewById(R.id.tv_UAE_email2);
		tv_UAE_No2 = (TextView) findViewById(R.id.tv_UAE_No2);

	}

	private void LoadLink() {

		Linkify.addLinks(tv_kerala_num, Linkify.PHONE_NUMBERS);
		Linkify.addLinks(tv_infocity_emai, Linkify.EMAIL_ADDRESSES);
		Linkify.addLinks(tv_UAE_No1, Linkify.PHONE_NUMBERS);
		Linkify.addLinks(tv_UAE_No2, Linkify.PHONE_NUMBERS);
		Linkify.addLinks(tv_UAE_email1, Linkify.EMAIL_ADDRESSES);
		Linkify.addLinks(Tranetech_Site, Linkify.WEB_URLS);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}