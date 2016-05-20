package com.tranetech.slidingmenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.tranetech.tranetechmanagment.R;

@SuppressLint({ "SimpleDateFormat", "ValidFragment" })
public class DatePickerFragment extends DialogFragment {
	int mday, mmonth, myear;
	double year;
	static double month, day;
	Calendar cal;
	DatePickerDialog datepicker;
	DatePicker dp_start, dp_end;
	static long selected_year_s = 99, selected_month_s = 99,
			selected_day_s = 99;
	static long selected_year_e = 99, selected_month_e = 99,
			selected_day_e = 99;
	static long diffDays;
	boolean chk = false;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		cal = Calendar.getInstance();
		mday = cal.get(Calendar.DAY_OF_MONTH);
		mmonth = cal.get(Calendar.MONTH);
		myear = cal.get(Calendar.YEAR);
		datepicker = new DatePickerDialog(getActivity(), datePickerListener,
				myear, mmonth, mday);
		switch (LeaveFragment.getting_id) {
		case R.id.ib_startdate:
			// set date picker as current date to maximum

			dp_start = datepicker.getDatePicker();
			dp_start.setMinDate(cal.getTimeInMillis());

			return datepicker;
		case R.id.ib_enddate:
			dp_end = datepicker.getDatePicker();

			return datepicker;

		}
		return null;
	}

	public void alertOneButton() {

		new AlertDialog.Builder(getActivity()).setTitle("Wrong Data Input!")
				.setMessage("Please enter end Date after start date")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						dialog.cancel();
					}
				}).show();
	}

	public DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			// TODO Auto-generated method stub
			switch (LeaveFragment.getting_id) {
			case R.id.ib_startdate:
				selected_year_s = selectedYear;
				selected_month_s = selectedMonth+1;
				selected_day_s = selectedDay;

				LeaveFragment.et_startdate.setText(selectedYear + "-"
						+ (selectedMonth + 1) + "-" + selectedDay);
				LeaveFragment.et_enddate.setText("");
				break;

			case R.id.ib_enddate:
				selected_year_e = selectedYear;
				selected_month_e = selectedMonth+1;
				selected_day_e = selectedDay;

				if (selected_year_e > selected_year_s) {
					chk = true;

				} else if (selected_month_e > selected_month_s
						&& selected_year_e == selected_year_s) {
					chk = true;

				} else if (selected_day_e > selected_day_s
						&& selected_year_e == selected_year_s
						&& selected_month_e == selected_month_s) {
					chk = true;
				}

				if (chk == false) {

					alertOneButton();
					LeaveFragment.et_enddate.setText("");
				} else {

					String Datestart = ("" + selected_month_s + "/" + ""
							+ selected_day_s + "/" + "" + selected_year_s);

					String Dateend = ("" + selected_month_e + "/" + ""
							+ selected_day_e + "/" + "" + selected_year_e);

					Log.d("Testing", "" + Datestart);
					Log.d("Testing", "" + Dateend);

					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

					Date d1 = null;
					Date d2 = null;

					try {
						d1 = format.parse(Datestart);
						d2 = format.parse(Dateend);

						// in milliseconds
						long diff = d2.getTime() - d1.getTime();

//						long diffSeconds = diff / 1000 % 60;
//						long diffMinutes = diff / (60 * 1000) % 60;
//						long diffHours = diff / (60 * 60 * 1000) % 24;
						diffDays = diff / (24 * 60 * 60 * 1000);
						
					

						System.out.print(diffDays + " days, ");
//						System.out.print(diffHours + " hours, ");
//						System.out.print(diffMinutes + " minutes, ");
//						System.out.print(diffSeconds + " seconds.");

					} catch (Exception e) {
						e.printStackTrace();
					}

					LeaveFragment.et_enddate.setText(selectedYear + "-"
							+ (selectedMonth + 1) + "-" + selectedDay);
				}

				break;
			}
		}
	};

}
