package com.tranetech.tranetechmanagment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAttendence extends Activity {

	ProgressDialog mprocessingdialog;
	String URLmessage, success;
	public String URL = "http://openspace.tranetech.com/mis/attendance.php?"
			+ "R_id=" + LoginDetails.r_id;
	ListView list;
	static ArrayList<HashMap<String, String>> attendence_webdata = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// get action bar
		ActionBar action = getActionBar();

		// Enabling Up / Back navigation
		action.setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewforattendence);
		list = (ListView) findViewById(R.id.list);
		new GetData().execute();
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

	private class GetData extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mprocessingdialog = new ProgressDialog(ViewAttendence.this);
			mprocessingdialog.setMessage("Please Wait.......");
			mprocessingdialog.setIndeterminate(false);
			mprocessingdialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			JSONParser jParser = new JSONParser();
			// JSONArray data = null;
			String jsonstr = jParser.makeServiceCall(URL, JSONParser.GET);

			Log.d("Json Response->>>>", jsonstr);

			if (jsonstr != null) {
				try {
					JSONObject jobj = new JSONObject(jsonstr);
					URLmessage = jobj.getString("message").toString();
					Log.d("JSON data MESSAGE FROM URL", URLmessage);
					success = jobj.getString("success").toString();
					Log.d("MESSAGE FROM URL(sucess ? )", success);
					JSONArray jarray = jobj.getJSONArray("Data");

					for (int i = 0; i < jarray.length(); i++) {
						JSONObject jobjin = jarray.getJSONObject(i);

						String a_date = jobjin.getString("A_date");
						String ain_time = jobjin.getString("Ain_time");
						String out_time = jobjin.getString("out_time");

						HashMap<String, String> adding = new HashMap<String, String>();
						adding.put("A_date", a_date);
						adding.put("Ain_time", ain_time);
						adding.put("out_time", out_time);

						attendence_webdata.add(adding);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					// TODO: handle exception

				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mprocessingdialog.isShowing()) {
				mprocessingdialog.dismiss();
			}

			Filllist();
			Toast.makeText(ViewAttendence.this, "" + URLmessage,
					Toast.LENGTH_SHORT).show();

		}

		private void Filllist() {
			// TODO Auto-generated method stub
			Cus adapter = new Cus();
			list.setAdapter(adapter);
		}

		private class Cus extends BaseAdapter {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return attendence_webdata.size();

			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@SuppressWarnings("unused")
			@SuppressLint("SimpleDateFormat")
			@Override
			public View getView(int position, View view, ViewGroup parent) {
				// TODO Auto-generated method stub

				// get the layout inflater

				LayoutInflater mLayoutInflater = (LayoutInflater) getApplicationContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				// create a ViewHolder reference
				ViewHolder holder = null;

				// check to see if the reused view is null or not, if is not
				// null then reuse it
				if (view == null) {
					holder = new ViewHolder();
					// check to see if the reused view is null or not, if is not
					// null then reuse it
					if (view == null) {
						holder = new ViewHolder();

						view = mLayoutInflater.inflate(
								R.layout.listview_values, null);
						holder.txt_date = (TextView) view
								.findViewById(R.id.t_date);
						holder.txt_intime = (TextView) view
								.findViewById(R.id.txt_intime);
						holder.txt_outtime = (TextView) view
								.findViewById(R.id.txt_outtime);

						// the setTag is used to store the data within this view
						view.setTag(holder);
					} else {
						// the getTag returns the viewHolder object set as a tag
						// to the view
						holder = (ViewHolder) view.getTag();
						view = mLayoutInflater.inflate(
								R.layout.listview_values, parent, false);
					}

				}
				// get the string item from the position "position" from array
				// list to put it on the TextView
				String date = attendence_webdata.get(position).get("A_date")
						.toString();
				String intime = attendence_webdata.get(position)
						.get("Ain_time").toString();
				String outtime = attendence_webdata.get(position)
						.get("out_time").toString();
				if (date != null) {
					if (holder.txt_date != null) {
						// set the item name on the TextView
						holder.txt_date.setText(date);
					}
				}
				if (intime != null) {
					if (holder.txt_intime != null) {
						// set the item name on the TextView
						holder.txt_intime.setText(intime);
					}
				}
				if (outtime != null) {
					if (holder.txt_outtime != null) {
						// set the item name on the TextView
						holder.txt_outtime.setText(outtime);
					}
				}

				// this method must return the view corresponding to the data at
				// the specified position.
				return view;

			}
		}

	}

	/**
	 * Static class used to avoid the calling of "findViewById" every time the
	 * getView() method is called, because this can impact to your application
	 * performance when your list is too big. The class is static so it cache
	 * all the things inside once it's created.
	 */
	private static class ViewHolder {
		private TextView txt_outtime;
		private TextView txt_intime;
		private TextView txt_date;

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		attendence_webdata.clear();
		list.setAdapter(null);
	}
}
