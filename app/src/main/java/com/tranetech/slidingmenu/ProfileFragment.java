package com.tranetech.slidingmenu;

import org.json.JSONArray;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tranetech.tranetechmanagment.Edit_profile;
import com.tranetech.tranetechmanagment.JSONParser;
import com.tranetech.tranetechmanagment.LoginDetails;
import com.tranetech.tranetechmanagment.R;
import com.tranetech.tranetechmanagment.Feedback.ShowDialogAsyncTask;

public class ProfileFragment extends Fragment implements OnClickListener {
	private TextView etphone, etaddress, etemail, txt_usertype, txt_position,
			txt_location, pcname, pcpassword, bg, jd, doc_id, ac_no, username,
			Dob;
	Button btn_edit;
	ImageView img_profile;
	public String URLmessage, success;
	ProgressDialog mprocessingdialog;
	public static String URL;
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

	public ProfileFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_profile, container,
				false);
		username = (TextView) rootView.findViewById(R.id.username);
		etphone = (TextView) rootView.findViewById(R.id.tv_contect);
		etaddress = (TextView) rootView.findViewById(R.id.tv_address_profile);
		etemail = (TextView) rootView.findViewById(R.id.tv_emailweb);
		txt_usertype = (TextView) rootView
				.findViewById(R.id.tv_usertype_web_profile);
		txt_position = (TextView) rootView
				.findViewById(R.id.tv_position_profile);
		txt_location = (TextView) rootView
				.findViewById(R.id.tv_location_profile);
		pcname = (TextView) rootView.findViewById(R.id.tv_pcname_profile);
		pcpassword = (TextView) rootView.findViewById(R.id.tv_pcpsw_web);
		bg = (TextView) rootView.findViewById(R.id.tv_bldgrp_web);
		jd = (TextView) rootView.findViewById(R.id.tv_joindate_web);
		Dob = (TextView) rootView.findViewById(R.id.tv_dob_profile);
		doc_id = (TextView) rootView.findViewById(R.id.tv_docID_web);
		ac_no = (TextView) rootView.findViewById(R.id.tv_ac_no_web);
		img_profile = (ImageView) rootView.findViewById(R.id.img_profile_frag);
		btn_edit = (Button) rootView.findViewById(R.id.btn_edit);

		btn_edit.setOnClickListener(this);
		Getting_webdata();
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

	private void Getting_webdata() {
		// TODO Auto-generated method stub
		r_id = LoginDetails.r_id;
		try {
			URL = "http://openspace.tranetech.com/mis/displayprofile.php?"
					+ "R_id=" + r_id;
			;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new ShowDialogAsyncTask().execute();

	}

	public class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mprocessingdialog = new ProgressDialog(getActivity());
			mprocessingdialog.setMessage("Please Wait.......");
			mprocessingdialog.setIndeterminate(false);
			mprocessingdialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONParser jParser = new JSONParser();
			// JSONArray data = null;
			String jsonstr_pf = jParser.makeServiceCall(URL, JSONParser.GET);

			Log.d("Json url view string", jsonstr_pf);
			try {
				JSONObject jobj = new JSONObject(jsonstr_pf);

				JSONArray jarray = jobj.getJSONArray("Data");

				for (int i = 0; i < jarray.length(); i++) {
					JSONObject jobjin = jarray.getJSONObject(i);

					ussername = jobjin.getString("R_name").toString();
					contect_no = jobjin.getString("R_mono").toString().trim();
					address = jobjin.getString("R_address").toString();
					dob = jobjin.getString("R_dob").toString();
					joindate = jobjin.getString("R_joindate");
					email_id = jobjin.getString("R_emailid").toString().trim();
					usertype = jobjin.getString("R_usertype").toString();
					position = jobjin.getString("R_position").toString();
					location = jobjin.getString("R_location").toString();
					pc_name = jobjin.getString("R_pcname").toString();
					pc_psw = jobjin.getString("R_pcpwd").toString();
					document_id = jobjin.getString("R_docid").toString();
					bank_ac = jobjin.getString("R_bno").toString();
					blood_grp = jobjin.getString("R_bg").toString();
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
			etphone.setText(contect_no);
			etaddress.setText(address);
			etemail.setText(email_id);
			txt_usertype.setText(usertype);
			txt_position.setText(position);
			txt_location.setText(location);
			pcname.setText(pc_name);
			pcpassword.setText(pc_psw);
			bg.setText(blood_grp);
			jd.setText(joindate);
			doc_id.setText(document_id);
			ac_no.setText(bank_ac);
			username.setText(ussername);
			Dob.setText(dob);

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_edit:
			if (isWifiAvailable() || isNetworkAvailable()) {
				Intent i = new Intent(getActivity().getApplicationContext(),
						Edit_profile.class);
				getActivity().startActivity(i);

			} else {
				Toast.makeText(getActivity(), "PLease check internet....",
						Toast.LENGTH_SHORT).show();
			}
			break;

		}
	}

}
