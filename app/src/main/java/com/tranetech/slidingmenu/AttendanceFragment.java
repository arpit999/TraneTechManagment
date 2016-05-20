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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tranetech.tranetechmanagment.JSONParser;
import com.tranetech.tranetechmanagment.LoginDetails;
import com.tranetech.tranetechmanagment.R;
import com.tranetech.tranetechmanagment.ViewAttendence;

public class AttendanceFragment extends Fragment implements OnClickListener {
	TextView ussername;
	Button btn_InTime, btn_outTime, btn_dsp_atndce;
	EditText et_Date, et_InTime, et_OutTime;
	private Calendar cal;
	private int day, month, year, hour, min, sec;
	ProgressDialog mprocessingdialog;
	public static String str_intime, date_a, In_time, outtime1;
	private String URL_intime, URL_outtime;
	public static String URLmessage;

	int k = 0, j = 0;
	String rID = LoginDetails.r_id;

	public AttendanceFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_attendance,
				container, false);
		ussername = (TextView) rootView.findViewById(R.id.username_atd);
		btn_InTime = (Button) rootView.findViewById(R.id.btn_InTime);
		btn_outTime = (Button) rootView.findViewById(R.id.btn_outTime);
		et_Date = (EditText) rootView.findViewById(R.id.et_Date);
		et_InTime = (EditText) rootView.findViewById(R.id.et_InTime);
		et_OutTime = (EditText) rootView.findViewById(R.id.et_OutTime);
		btn_InTime.setOnClickListener(this);
		btn_dsp_atndce = (Button) rootView.findViewById(R.id.btn_dsp_atndce);
		btn_dsp_atndce.setOnClickListener(this);
		btn_outTime.setOnClickListener(this);
		ussername.setText(LoginDetails.ussername);
		setRetainInstance(true);
		DateAndTime();
		return rootView;

	}

	@SuppressLint("SimpleDateFormat")
	private void DateAndTime() {

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DATE);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		// SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss a");
		hour = cal.get(Calendar.HOUR);
		// String currentTime = df.format(hour.getTime());
		min = cal.get(Calendar.MINUTE);
		sec = cal.get(Calendar.SECOND);
		et_InTime.setText("" + hour + ":" + min + ":" + sec);
		et_Date.setText("" + year + "-" + (month + 1) + "-" + day);
		date_a = et_Date.getText().toString();

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_dsp_atndce:

			Intent at = new Intent(getActivity(), ViewAttendence.class);
			startActivity(at);

			break;
		case R.id.btn_InTime:
			if (isWifiAvailable()) {
				j++;

				if (j > 0) {

					In_time = et_InTime.getText().toString();
					try {
						URL_intime = "http://openspace.tranetech.com/mis/intime.php?"
								+ "R_id="
								+ rID
								+ "&Ain_time="
								+ In_time
								+ "&A_date=" + date_a;


					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					new ShowDialogAsyncTask().execute();

					btn_InTime.setClickable(false);
					et_InTime.setText("");
					et_OutTime.setText("" + hour + ":" + min + ":" + sec);
					et_InTime.setClickable(false);
					break;
				} else {

					Toast.makeText(getActivity(), "Please Check Internet..",
							Toast.LENGTH_LONG).show();

				}
			}

		case R.id.btn_outTime:
			if (isWifiAvailable()) {
				k++;

				if (k > 0) {
					outtime1 = et_OutTime.getText().toString();
					try {

						URL_outtime = "http://openspace.tranetech.com/mis/outtime.php?"
								+ "R_id="
								+ rID
								+ "&A_date="
								+ date_a
								+ "&out_time=" + outtime1;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					new ShowDialogAsync1Task().execute();

					btn_outTime.setClickable(false);
					et_OutTime.setText("");
					et_OutTime.setClickable(false);
					break;
				} else {

					Toast.makeText(getActivity(), "Please Check Internet..",
							Toast.LENGTH_LONG).show();
				}

			}
		}
	}

	public class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mprocessingdialog = new ProgressDialog(getActivity());
			mprocessingdialog.setMessage("Please Wait.....");
			mprocessingdialog.setIndeterminate(false);
			mprocessingdialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			JSONParser jParser = new JSONParser();
			// JSONArray data = null;
			String jsonstr = jParser.makeServiceCall(URL_intime,
					JSONParser.POST);

			Log.d("Json url view string", jsonstr);
			try {
				JSONObject jobj = new JSONObject(jsonstr);
				URLmessage = jobj.getString("message").toString();
				Log.d("JSON data MESSAGE FROM URL", URLmessage);
				// success = jobj.getString("success").toString();
				// Log.d("MESSAGE FROM URL(sucess ==> )", success);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated method stub
			return null;

		}

		@SuppressLint("ShowToast")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mprocessingdialog.dismiss();
			Toast.makeText(getActivity(), "" + URLmessage, Toast.LENGTH_SHORT)
					.show();
			// if (success.contains("1")) {
			// Toast.makeText(getActivity(), "" + URLmessage,
			// Toast.LENGTH_SHORT).show();
			//
			// } else {
			// Toast.makeText(getActivity(), "" + URLmessage,
			// Toast.LENGTH_SHORT).show();
			// }
		}
	}

	public class ShowDialogAsync1Task extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mprocessingdialog = new ProgressDialog(getActivity());
			mprocessingdialog.setMessage("Please wait.....");
			mprocessingdialog.setIndeterminate(false);
			mprocessingdialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			JSONParser jParser = new JSONParser();
			// JSONArray data = null;
			String jsonstr = jParser.makeServiceCall(URL_outtime,
					JSONParser.POST);

			Log.d("Json url view string", jsonstr);
			try {
				JSONObject jobj = new JSONObject(jsonstr);
				URLmessage = jobj.getString("message").toString();
				Log.d("JSON data MESSAGE FROM URL", URLmessage);
				// success = jobj.getString("success").toString();
				// Log.d("MESSAGE FROM URL(sucess ==> )", success);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // TODO Auto-generated method stub

			return null;

		}

		@SuppressLint("ShowToast")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mprocessingdialog.dismiss();
			Toast.makeText(getActivity(), "" + URLmessage, Toast.LENGTH_SHORT)
					.show();

		}
	}

	
}
