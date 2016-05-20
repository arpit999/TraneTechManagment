package com.tranetech.tranetechmanagment;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class Register1 extends Activity {
	public static String URL;
	public static String URLmessage;
	ProgressDialog mprocessingdialog;
	String username, address, location, emailid, mobilenum, position, password,
			dob, bankac, pcname, pcpsw, usertype, bloodgrpup, joindate, docid,
			success;
	Animation animAlpha;
	Spinner spin_usertype, spin_position, spin_location, spin_Bloodgroup;
	String SelectUserType, SelectPosition, SelectLocation, SelectBloodGroup;
	static int idchk = 999;
	DatePickerDialog datpiker;
	final Context context = this;
	private ImageButton ib, img_doj;
	private Calendar cal;
	private int mday, mmonth, myear;
	private Button btnRegister;
	private EditText edtreg_password, regETemail, reg_renterpassword, EdtDob,
			EDTUserName, ETmobilenumber, Edtaddress, EdtJoindate,
			EdtBanknumber, EDTPcName, EDTPcPassword, EDTdocument_ID_number;
	// int pos = 0;
	private CheckBox checkBox1;
	ActionBar action;
	DateValidator dv, doj;

	private boolean validEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		return pattern.matcher(email).matches();
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register);

		action = getActionBar();

		action.setDisplayHomeAsUpEnabled(true);

		cal = Calendar.getInstance();
		mday = cal.get(Calendar.DAY_OF_MONTH);
		mmonth = cal.get(Calendar.MONTH);
		myear = cal.get(Calendar.YEAR);
		LoaduielEments();
		GetSpinnerValue();
		LoadAddlisners();

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

	@SuppressLint("DefaultLocale")
	private void GetSpinnerValue() {

		spin_usertype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@SuppressLint("DefaultLocale")
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				SelectUserType = spin_usertype.getSelectedItem().toString();
				SelectUserType = spin_usertype.equals("All Types") ? SelectUserType
						.replaceAll(" ", "%20").toLowerCase() : SelectUserType;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		spin_position.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				SelectPosition = spin_position.getSelectedItem().toString();
				SelectPosition = spin_position.equals("All Types") ? SelectPosition
						.replaceAll(" ", "%20").toLowerCase() : SelectPosition;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		spin_location.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				SelectLocation = spin_location.getSelectedItem().toString();
				SelectLocation = spin_location.equals("All Types") ? SelectLocation
						.replaceAll(" ", "%20").toLowerCase() : SelectLocation;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		spin_Bloodgroup.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				SelectBloodGroup = spin_Bloodgroup.getSelectedItem().toString();
				SelectBloodGroup = spin_Bloodgroup.equals("All Types") ? SelectBloodGroup
						.replaceAll(" ", "%20").toLowerCase()
						: SelectBloodGroup;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		datpiker = new DatePickerDialog(this, datePickerListener, myear,
				mmonth, mday);
		switch (id) {
		case R.id.img_dob:
			// set date picker as current date to maximum

			DatePicker dp_dob = datpiker.getDatePicker();
			dp_dob.setMaxDate(new Date().getTime());

			return datpiker;
		case R.id.img_doj:
			// set date picker as current date to maximum
			DatePicker dp_doj = datpiker.getDatePicker();
			dp_doj.setMaxDate(new Date().getTime());

			return datpiker;

		}
		return null;
	}

	public DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			// TODO Auto-generated method stub

			switch (idchk) {
			case R.id.img_dob:

				EdtDob.setText(selectedYear + "-" + (selectedMonth + 1) + "-"
						+ selectedDay);
				break;

			case R.id.img_doj:

				EdtJoindate.setText(selectedYear + "-" + (selectedMonth + 1)
						+ "-" + selectedDay);
				break;
			}
		}
	};

	private void getTextfromEdittext() {
		// TODO Auto-generated method stub
		username = EDTUserName.getText().toString();
		password = edtreg_password.getText().toString();
		address = Edtaddress.getText().toString();
		emailid = regETemail.getText().toString().trim();
		dob = EdtDob.getText().toString();
		bankac = EdtBanknumber.getText().toString();
		pcname = EDTPcName.getText().toString();
		pcpsw = EDTPcPassword.getText().toString();
		docid = EDTdocument_ID_number.getText().toString();
		mobilenum = ETmobilenumber.getText().toString();
		joindate = EdtJoindate.getText().toString();
	}

	private void LoaduielEments() {
		// TODO Auto-generated method stub
		btnRegister = (Button) findViewById(R.id.btnRegister);
		regETemail = (EditText) findViewById(R.id.regETemail);
		edtreg_password = (EditText) findViewById(R.id.regETpassword);
		reg_renterpassword = (EditText) findViewById(R.id.regETreenterpassword);
		ib = (ImageButton) findViewById(R.id.img_dob);
		img_doj = (ImageButton) findViewById(R.id.img_doj);
		EdtDob = (EditText) findViewById(R.id.EdtDob);
		EdtJoindate = (EditText) findViewById(R.id.EdtJoindate);
		EdtBanknumber = (EditText) findViewById(R.id.EdtBanknumber);
		EDTPcName = (EditText) findViewById(R.id.EDTPcName);
		EDTPcPassword = (EditText) findViewById(R.id.EDTPcPassword);
		EDTdocument_ID_number = (EditText) findViewById(R.id.EDTdocument_ID_number);
		spin_Bloodgroup = (Spinner) findViewById(R.id.spin_Bloodgroup);
		EDTUserName = (EditText) findViewById(R.id.EDTUserName);
		ETmobilenumber = (EditText) findViewById(R.id.ETmobilenumber);
		Edtaddress = (EditText) findViewById(R.id.Edtaddress);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		spin_usertype = (Spinner) findViewById(R.id.spinner_usertype);
		spin_position = (Spinner) findViewById(R.id.spinner_position);
		spin_location = (Spinner) findViewById(R.id.spinner_location);

	}

	private void LoadAddlisners() {
		animAlpha = AnimationUtils.loadAnimation(this, R.anim.fade_out);

		ib.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				idchk = v.getId();
				showDialog(idchk);

			}
		});

		img_doj.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				idchk = v.getId();
				showDialog(idchk);

			}
		});

		btnRegister.setOnClickListener(new View.OnClickListener() {

			public void alertOneButton() {

				new AlertDialog.Builder(Register1.this)
						.setTitle("Wrong Data Input!")
						.setMessage("Please enter valid Date")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										dialog.cancel();
									}
								}).show();
			}

			@SuppressLint("NewApi")
			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				v.startAnimation(animAlpha);

				getTextfromEdittext();
				if (isWifiAvailable() || isNetworkAvailable()) {
					if ((EDTUserName.getText().toString().length() <= 0)) {
						EDTUserName
								.setError("ussername ? Note:Special charctor not allowed");

					} else if (EdtDob.getText().toString().length() <= 0) {
						alertOneButton();
					} else if (edtreg_password.getText().toString().length() <= 0) {
						edtreg_password
								.setError("Password Should not be Empty");
					} else if (!edtreg_password.getText().toString()
							.equals(reg_renterpassword.getText().toString())) {
						reg_renterpassword.setError("Pasword does not match");
					} else if ((!validEmail(regETemail.getText().toString()))) {
						regETemail.setError("Please enter valid email");
					} else if (EdtBanknumber.getText().toString().length() > 25) {
						EdtBanknumber
								.setError("Please Enter valid bank account number");
					} else if (EdtJoindate.getText().toString().length() <= 0) {

						alertOneButton();

					} else if (EDTPcName.getText().toString().length() <= 0) {
						EDTPcName.setError("PC name ?");
					} else if (EDTPcPassword.getText().toString().length() <= 0)
						EDTPcPassword.setError("PC password ?");
					else if (EDTdocument_ID_number.getText().toString()
							.length() <= 0) {
						EDTdocument_ID_number.setError("Document ID ?");

					} else if ((ETmobilenumber.getText().toString().length() <= 0)
							&& (ETmobilenumber.getText().toString().length() >= 13)) {
						ETmobilenumber
								.setError("Mobile number should not be empty");
					} else if (Edtaddress.getText().toString().length() <= 0) {
						Edtaddress.setError("Adress should not be empty");
					} else if (!checkBox1.isChecked()) {
						checkBox1.setError("Please accept term & condition");
					}

					// localhost/test/tt/insert_user?R_name=jigar&R_address=ahemedabad&R_location=aa&R_emailid=hirensa&R_mono=8182838485&R_position=a&R_password=abcd&R_dob=10/04/1992&R_bno=101&R_pcname=apple&R_pcpwd=aa&R_usertype=employee&R_bg=b&R_joindate=15/04/2015&R_docid=1025
					else {
						try {
							URL = "http://openspace.tranetech.com/mis/registration.php?"
									+ "R_name="
									+ username.replaceAll(" ", "%20")
									+ "&R_dob="
									+ dob
									+ "&R_password="
									+ password.replaceAll(" ", "")
									+ "&R_emailid="
									+ emailid.replaceAll(" ", "")
									+ "&R_bno="
									+ bankac.replaceAll(" ", "")
									+ "&R_bg="
									+ SelectBloodGroup
									+ "&R_joindate="
									+ joindate
									+ "&R_pcname="
									+ pcname.replaceAll(" ", "%20")
									+ "&R_pcpwd="
									+ pcpsw.replaceAll(" ", "")
									+ "&R_docid="
									+ docid.replaceAll(" ", "%20")
									+ "&R_mono="
									+ mobilenum.replaceAll(" ", "")
									+ "&R_address="
									+ address.replaceAll(" ", "%20")
									+ "&R_position="
									+ SelectPosition.replaceAll(" ", "%20")
									+ "&R_location="
									+ SelectLocation.replaceAll(" ", "%20")
									+ "&R_usertype="
									+ SelectUserType.replaceAll(" ", "%20");
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						new ShowDialogAsyncTask().execute();

					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Please check internet...", Toast.LENGTH_SHORT)
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
			mprocessingdialog = new ProgressDialog(Register1.this);
			mprocessingdialog.setMessage("Please Wait.....");
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
				Log.d("MESSAGE FROM URL(success ? )", success);
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
			if (URLmessage.matches("EmailID Already Exist")) {
				Toast.makeText(getApplicationContext(), "" + URLmessage,
						Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(getApplicationContext(), "" + URLmessage,
						Toast.LENGTH_SHORT).show();
				Intent reg = new Intent(getApplicationContext(),
						LoginDetails.class);
				startActivity(reg);
			}

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}

}
// for password validation
// "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"
