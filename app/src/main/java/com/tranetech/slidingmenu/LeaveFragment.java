package com.tranetech.slidingmenu;

import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tranetech.tranetechmanagment.JSONParser;
import com.tranetech.tranetechmanagment.LoginDetails;
import com.tranetech.tranetechmanagment.R;

public class LeaveFragment extends DialogFragment implements OnClickListener {

	public LeaveFragment() {
	}

	public static int getting_id = 999;
	public static EditText et_startdate, et_enddate, etcategory, etdescription,
			et_num_of_days;
	private TextView category, descrption;
	Button btn_submit, btn_calculate;
	View rootView;
	boolean chk = false;

	public static String URL;
	ProgressDialog mprocessingdialog;
	public static String date, URLmessage, success;
	public static ImageButton ib_startdate, ib_enddate;
	Animation animation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_leave, container,
				false);
		// TODO Auto-generated method stub
		category = (TextView) rootView.findViewById(R.id.category);
		descrption = (TextView) rootView.findViewById(R.id.descrption);
		et_startdate = (EditText) rootView.findViewById(R.id.et_startdate);
		et_enddate = (EditText) rootView.findViewById(R.id.et_enddate);
		etcategory = (EditText) rootView.findViewById(R.id.etcategory);
		et_num_of_days = (EditText) rootView.findViewById(R.id.et_num_of_days);
		etdescription = (EditText) rootView.findViewById(R.id.etdescription);
		btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
		btn_calculate = (Button) rootView.findViewById(R.id.btn_calculate);
		ib_startdate = (ImageButton) rootView.findViewById(R.id.ib_startdate);
		ib_enddate = (ImageButton) rootView.findViewById(R.id.ib_enddate);
		ib_startdate.setOnClickListener(this);
		ib_enddate.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		btn_calculate.setOnClickListener(this);
		btn_calculate.setEnabled(false);
		et_num_of_days.setText("");
		return rootView;
	}

	public Boolean isWifiAvailable() {

		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
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
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public void alertOneButton() {

		new AlertDialog.Builder(getActivity()).setTitle("Wrong Data Input!")
				.setMessage("Please enter valid Date")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						dialog.cancel();
					}
				}).show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_startdate:
			getting_id = v.getId();
			showDatePickerDialog(getting_id);
			et_num_of_days.setText("");
			break;

		case R.id.ib_enddate:
			getting_id = v.getId();
			showDatePickerDialog(getting_id);
			chk = true;
			btn_calculate.setEnabled(true);
			break;
		case R.id.btn_calculate:
			// et_num_of_days.setText("" + DatePickerFragment.day + "days-" + ""
			// + DatePickerFragment.month + "months-" + ""
			// + DatePickerFragment.year + "year");
			long  days;
			long num_of_days = (DatePickerFragment.diffDays);
			if((DatePickerFragment.selected_month_s==2)||(DatePickerFragment.selected_month_e==2)){
				days = num_of_days+1;
				
			}else{
				days = num_of_days+1;
			}
			
			
			
			et_num_of_days.setText("" + days + " Days");
			break;
		case R.id.btn_submit:

			if (isWifiAvailable() || isNetworkAvailable()) {
				if ((et_startdate.getText().length() <= 0)) {

					alertOneButton();

				} else if ((et_enddate.getText().length() <= 0)) {
					alertOneButton();
				} else if (etcategory.getText().toString().length() <= 0) {

					etcategory.setError("Should not be empty");
				} else if (etdescription.getText().toString().length() <= 0) {
					etdescription.setError("Should not be empty");

				} else if (et_num_of_days.getText().toString().length() <= 0) {
					et_num_of_days.setError("Should not be empty");

				} else {

					String id = LoginDetails.r_id, noofdays = et_num_of_days
							.getText().toString().trim();
					String cate = etcategory.getText().toString()
							.replaceAll(" ", "%20");
					String descc = etdescription.getText().toString()
							.replaceAll(" ", "%20");
					String startdate = et_startdate.getText().toString().trim();
					String enddate = et_enddate.getText().toString().trim();

					try {

						URL = "http://openspace.tranetech.com/mis/l_leave.php?"
								+ "R_id=" + id + "&L_sdate=" + startdate
								+ "&L_edate=" + enddate + "&L_category="
								+ cate.replaceAll(" ", "%20") + "&L_desc="
								+ descc.replaceAll(" ", "%20") + "&L_days="
								+ noofdays.replaceAll(" ", "%20");

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {

						e.printStackTrace();
					}
					new ShowDialogAsyncTask().execute();

				}
			} else {
				Toast.makeText(getActivity(), "Please Check Internet..",
						Toast.LENGTH_LONG).show();
			}

			break;
		}

	}

	public class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mprocessingdialog = new ProgressDialog(getActivity());
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
				Log.d("MESSAGE FROM URL(sucess ==> )", success);
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
				Toast.makeText(getActivity(), "" + URLmessage,
						Toast.LENGTH_SHORT).show();

				Intent reg = new Intent(getActivity(), MainActivity.class);
				startActivity(reg);

			} else {
				Toast.makeText(getActivity(), "" + URLmessage,
						Toast.LENGTH_SHORT).show();

			}

		}

	}

	public void showDatePickerDialog(int getting_id2) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datepicker");
	}

}
