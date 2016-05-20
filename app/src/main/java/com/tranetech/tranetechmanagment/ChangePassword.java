package com.tranetech.tranetechmanagment;

import org.json.JSONException;
import org.json.JSONObject;

import com.tranetech.slidingmenu.MainActivity;
import com.tranetech.tranetechmanagment.Forgotpassword.ShowDialogAsyncTask;

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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends Activity {

	EditText OldPassword, NewPassword, ConfirmPassword;
	Button Reset;
	public static String URL;
	public static String URLmessage, success;
	ProgressDialog mprocessingdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		// get action bar
		ActionBar action = getActionBar();

		// Enabling Up / Back navigation
		action.setDisplayHomeAsUpEnabled(true);

		LoadId();
		ClickButton();

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

	public void ClickButton() {

		Reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isWifiAvailable() || isNetworkAvailable()) {
					if (OldPassword.getText().toString().length() <= 0) {
						OldPassword.setError(" OldPassword ??? ");
					} else if (!LoginDetails.userpassword.equals(OldPassword
							.getText().toString())) {
						OldPassword.setError("Old Password Does not match ");
					} else if (NewPassword.getText().toString().length() <= 0) {
						NewPassword.setError(" NewPassword ??? ");
					} else if (ConfirmPassword.getText().toString().length() <= 0) {
						ConfirmPassword.setError(" Password ??? ");
					} else if (!ConfirmPassword.getText().toString()
							.equals(NewPassword.getText().toString())) {
						ConfirmPassword.setError(" Password Does not match ");
					} else {

						try {
							URL = "http://openspace.tranetech.com/mis/change_password.php?"
									+ "R_emailid="
									+ LoginDetails.userid.toString()
									+ "&R_password="
									+ ConfirmPassword.getText().toString()
											.trim();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						new ShowDialogAsyncTask().execute();

						Intent i = new Intent(getBaseContext(),
								ChangePassword.class);
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
			mprocessingdialog = new ProgressDialog(ChangePassword.this);
			mprocessingdialog.setTitle("Tranetech MIS");
			mprocessingdialog.setMessage("Updating.....");
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

			if (success.contains("true")) {
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

	public void LoadId() {

		OldPassword = (EditText) findViewById(R.id.et_OldPass);
		NewPassword = (EditText) findViewById(R.id.et_NewPass);
		ConfirmPassword = (EditText) findViewById(R.id.et_ConfirmPass);
		Reset = (Button) findViewById(R.id.btn_ResetPassword);
	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			Intent l = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(l);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

}
