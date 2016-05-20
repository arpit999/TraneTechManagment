package com.tranetech.tranetechmanagment;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.tranetech.slidingmenu.MainActivity;

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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends Activity {
	Button btn_feedback;

	EditText et_firstname, et_address, et_ussermail, et_comments;
	DateValidator dv, doj;
	public static String firstname, address, ussermail, comments;
	public String URLmessage, success;
	ProgressDialog mprocessingdialog;
	public static String URL;

	private boolean validEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		return pattern.matcher(email).matches();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		// get action bar
		ActionBar action = getActionBar();

		// Enabling Up / Back navigation
		action.setDisplayHomeAsUpEnabled(true);

		intialization();
		addlisners();
	}

	private void addlisners() {
		// TODO Auto-generated method stub
		btn_feedback.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				firstname = et_firstname.getText().toString();
				address = et_address.getText().toString();
				ussermail = et_ussermail.getText().toString();
				comments = et_comments.getText().toString();

				if (isWifiAvailable() || isNetworkAvailable()) {

					if ((et_comments.getText().toString().length() <= 0)
							&& (!et_comments.getText().toString()
									.matches("[a-zA-Z.? ]*"))) {
						et_comments
								.setError("Comments..? Note:@,#,!,%....Not allowed ");
					} else {

						try {
							URL = "http://openspace.tranetech.com/mis/feedback.php?"
									+ "first_name="
									+ firstname.replaceAll(" ", "%20")
									+ "&Adress="
									+ address.replaceAll(" ", "%20")
									+ "&usermail="
									+ ussermail.trim()
									+ "&comment="
									+ comments.replaceAll(" ", "%20");
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						new ShowDialogAsyncTask().execute();
						Intent home = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(home);
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Check Internet..", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}

	private void intialization() {
		// TODO Auto-generated method stub
		btn_feedback = (Button) findViewById(R.id.btn_feedback);
		et_firstname = (EditText) findViewById(R.id.et_firstname);
		et_address = (EditText) findViewById(R.id.et_address);
		et_ussermail = (EditText) findViewById(R.id.et_ussermail);
		et_comments = (EditText) findViewById(R.id.et_comments);
		firstname = LoginDetails.ussername;
		address = LoginDetails.address;
		ussermail = LoginDetails.email_id;

		et_firstname.setText(firstname);
		et_address.setText(address);
		et_ussermail.setText(ussermail);

	}

	public class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mprocessingdialog = new ProgressDialog(Feedback.this);
			mprocessingdialog.setMessage("UpLoading.....");
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}