package com.tranetech.tranetechmanagment;

import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tranetech.slidingmenu.MainActivity;

@SuppressWarnings("unused")
public class LoginDetails extends Activity {
	public static String URL;
	public static String URLmessage;
	ProgressDialog mprocessingdialog;
	Animation animAlpha;
	public static int i;
	Button login;
	EditText username, password;
	static String userid;
	String success;
	static String userpassword;
	TextView textRegister, txtForgotPassword;
	ProgressBar pb;
	public static String ussername;
	public static String contect_no;
	public static String address;
	public static String dob;
	public static String joindate;
	public static String email_id;
	public static String usertype;
	public static String position;
	public static String location;
	public static String pc_name;
	public static String pc_psw;
	public static String document_id;
	public static String bank_ac;
	public static String blood_grp;
	public static String r_id;

	private boolean validEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		return pattern.matcher(email).matches();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_details);
		if (isWifiAvailable()) {

		} else {
			Toast.makeText(getApplicationContext(), "No internet connection",
					Toast.LENGTH_SHORT).show();
		}
		addli();
		loadui();

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
	private void addli() {
		// TODO Auto-generated method stub
		username = (EditText) findViewById(R.id.editTextUsername);

		password = (EditText) findViewById(R.id.editTextPassword);
		login = (Button) findViewById(R.id.btnlogin);
		textRegister = (TextView) findViewById(R.id.textRegister);
		txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
		animAlpha = AnimationUtils.loadAnimation(this, R.anim.fade_out);

	}

	private void loadui() {
		// TODO Auto-generated method stub

		login.setOnClickListener(new OnClickListener() {

			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//
//				Intent i2 = new Intent(LoginDetails.this, MainActivity.class);
//				startActivity(i2);
				
				userid = username.getText().toString().trim().toUpperCase();
				userpassword = password.getText().toString();
				Animation shake = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.shake);
				v.startAnimation(animAlpha);
				
				if (isWifiAvailable()||isNetworkAvailable()) {
				

					if ((!validEmail(username.getText().toString()))) {
						username.setError("Email is not valid");
						username.startAnimation(shake);
					} else if (password.getText().length() <= 0) {
						password.setError("Please enter the password");
						password.startAnimation(shake);
					} else {

						try {
							URL = "http://openspace.tranetech.com/mis/login.php?"
									+ "R_emailid="
									+ userid.toString().trim()
									+ "&R_password="
									+ userpassword.toString().trim();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						new ShowDialogAsyncTask().execute();

					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Check Internet..", Toast.LENGTH_LONG)
							.show();
				}

			}
		});

		textRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isWifiAvailable()||isNetworkAvailable()) {
					Intent toreg = new Intent(LoginDetails.this,
							Register1.class);
					startActivity(toreg);
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Check Internet..", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		txtForgotPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isWifiAvailable()||isNetworkAvailable()) {

					Intent i = new Intent(getApplicationContext(),
							Forgotpassword.class);
					startActivity(i);

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
			mprocessingdialog = new ProgressDialog(LoginDetails.this);

			mprocessingdialog.setMessage("Please Wait.......");
			mprocessingdialog.setIndeterminate(false);
			mprocessingdialog.show();

		}

		@SuppressLint("DefaultLocale")
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONParser jParser = new JSONParser();
			// JSONArray data = null;
			String jsonstr = jParser.makeServiceCall(URL, JSONParser.GET);

			Log.d("Json url view string", jsonstr);
			try {
				JSONObject jobj = new JSONObject(jsonstr);
				URLmessage = jobj.getString("message").toString();
				Log.d("JSON data MESSAGE FROM URL", URLmessage);
				success = jobj.getString("success").toString();
				Log.d("MESSAGE FROM URL(sucess ? )", success);

				JSONArray jarray = jobj.getJSONArray("Data");
				for (i = 0; i < jarray.length(); i++) {
					JSONObject jobjin = jarray.getJSONObject(i);
					ussername = jobjin.getString("R_name");
					contect_no = jobjin.getString("R_mono");
					address = jobjin.getString("R_address");
					dob = jobjin.getString("R_dob");
					joindate = jobjin.getString("R_joindate");
					email_id = jobjin.getString("R_emailid");
					usertype = jobjin.getString("R_usertype");
					position = jobjin.getString("R_position");
					location = jobjin.getString("R_location");
					pc_name = jobjin.getString("R_pcname");
					pc_psw = jobjin.getString("R_pcpwd");
					document_id = jobjin.getString("R_docid");
					bank_ac = jobjin.getString("R_bno");
					blood_grp = jobjin.getString("R_bg");
					r_id = jobjin.getString("R_id");
				}

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

			if (success.matches("true")) {
				Toast.makeText(getApplicationContext(), "" + URLmessage,
						Toast.LENGTH_SHORT).show();
				FillProfile();

			} else {

				Toast.makeText(getApplicationContext(), "" + URLmessage,
						Toast.LENGTH_SHORT).show();

			}

		}

		private void FillProfile() {
			// TODO Auto-generated method stub

			Intent i2 = new Intent(LoginDetails.this, MainActivity.class);
			startActivity(i2);
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}
}
