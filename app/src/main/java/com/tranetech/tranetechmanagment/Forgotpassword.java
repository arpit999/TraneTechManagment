package com.tranetech.tranetechmanagment;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.tranetech.tranetechmanagment.Feedback.ShowDialogAsyncTask;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgotpassword extends Activity {
	Button frgt;
	EditText editText1;
	public static String URL;
	public static String URLmessage, success;
	ProgressDialog mprocessingdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpassword);
		// get action bar
		ActionBar action = getActionBar();

		// Enabling Up / Back navigation
		action.setDisplayHomeAsUpEnabled(true);
		frgt = (Button) findViewById(R.id.button1);
		editText1 = (EditText) findViewById(R.id.editText1);
		frgt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (isWifiAvailable()||isNetworkAvailable()) {
					if ((!validEmail(editText1.getText().toString()))) {
						editText1.setError("Please Enter valid email");
					} else {

						try {
							URL = "http://openspace.tranetech.com/mis/smtpmail/sendmail.php?"
									+ "R_emailid="
									+ editText1.getText().toString().trim();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						new ShowDialogAsyncTask().execute();
						Toast.makeText(getApplicationContext(),
								"password is sent to your email",
								Toast.LENGTH_LONG).show();

						Intent i = new Intent(getApplicationContext(),
								LoginDetails.class);
						startActivity(i);
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Check Internet..", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}

	public class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mprocessingdialog = new ProgressDialog(Forgotpassword.this);
			mprocessingdialog.setMessage("Sending.....");
			mprocessingdialog.setIndeterminate(false);
			mprocessingdialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONParser jParser = new JSONParser();
			// JSONArray data = null;
			String jsonstr = jParser.makeServiceCall(URL, JSONParser.POST);

			Log.d("Json url view string", jsonstr);

			try {
				JSONObject jobj = new JSONObject(jsonstr);
				URLmessage = jobj.getString("message").toString();
				Log.d("JSON data MESSAGE FROM URL", URLmessage);
				success = jobj.getString("success").toString();
				Log.d("MESSAGE FROM URL(sucess ? )", success);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;

		}

		@SuppressLint("ShowToast")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mprocessingdialog.dismiss();

			if (URLmessage.contains("true")) {
				Toast.makeText(getApplicationContext(), "" + URLmessage,
						Toast.LENGTH_SHORT).show();
				Intent reg = new Intent(getApplicationContext(),
						LoginDetails.class);
				startActivity(reg);
			} else {
				Toast.makeText(getApplicationContext(), "" + URLmessage,
						Toast.LENGTH_SHORT).show();

			}
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private boolean validEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		return pattern.matcher(email).matches();
	}

	public Boolean isWifiAvailable() {

		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo wifiInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			if (wifiInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}

