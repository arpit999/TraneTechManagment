package com.tranetech.slidingmenu;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tranetech.tranetechmanagment.JSONParser;
import com.tranetech.tranetechmanagment.LoginDetails;
import com.tranetech.tranetechmanagment.R;

public class ReportFragment extends Fragment implements OnClickListener {
	ImageView repimg;
	TextView tvuser, dates, des;
	EditText etdate, etdes;
	Spinner spin_design;
	Button btn_submit;
	int mday, mmonth, myear;
	String date_url;
	Calendar cal;
	public static String URLmessage, success, URL, selecteditem;
	ProgressDialog mprocessingdialog;

	public ReportFragment() {
	}

	@SuppressLint("DefaultLocale")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.report, container, false);

		tvuser = (TextView) rootView.findViewById(R.id.tvuser);
		dates = (TextView) rootView.findViewById(R.id.date);
		spin_design = (Spinner) rootView.findViewById(R.id.spin_design);
		des = (TextView) rootView.findViewById(R.id.des);
		etdes = (EditText) rootView.findViewById(R.id.etdes);
		cal = Calendar.getInstance();
		mday = cal.get(Calendar.DAY_OF_MONTH);
		mmonth = cal.get(Calendar.MONTH);
		myear = cal.get(Calendar.YEAR);
		btn_submit = (Button) rootView.findViewById(R.id.btn_submit);

		tvuser.setText(LoginDetails.ussername);

		dates.setText(mday + "-" + (mmonth + 1) + "-" + myear);

		date_url = Integer.toString(myear) + "-" + Integer.toString(mmonth) + "-"
				+ Integer.toString(mday);

		btn_submit.setOnClickListener(this);
		spin_design.setOnItemSelectedListener(new OnItemSelectedListener() {

			@SuppressLint("DefaultLocale")
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				selecteditem = spin_design.getSelectedItem().toString();
				selecteditem = selecteditem.equals("All Types") ? selecteditem
						.replaceAll(" ", "%20").toLowerCase() : selecteditem;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btn_submit:
			if (isWifiAvailable()|isNetworkAvailable()) {
				if (spin_design.getSelectedItem().toString().isEmpty()) {
					etdes.setError("Please select value");
				} else if (etdes.getText().toString().length() <= 0) {
					etdes.setError("Please enter the Description");
					break;
				} else {
					String Date = dates.getText().toString().trim();
					String id = LoginDetails.r_id, field = selecteditem, Desc = etdes
							.getText().toString();
					try {
						// URL="http://openspace.tranetech.com/mis/preport.php?R_id=1&Pr_fieldwork=analysis&Pr_description=arpit_agkds";
						URL = "http://openspace.tranetech.com/mis/preport.php?"
								+ "R_id=" + id + "&Pr_fieldwork=" + field.replaceAll(" ","%20")
								+ "&Pr_description="
								+ Desc.replaceAll(" ", "%20") + "&Pr_date="
								+ date_url;

					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					new ShowDialogAsyncTask().execute();
					Toast.makeText(getActivity(), "Report Submited..",
							Toast.LENGTH_LONG).show();

				}
			} else {
				Toast.makeText(getActivity(), "Please Check Internet..",
						Toast.LENGTH_LONG).show();
			}
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

			if (URLmessage.contains("true")) {
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
}
