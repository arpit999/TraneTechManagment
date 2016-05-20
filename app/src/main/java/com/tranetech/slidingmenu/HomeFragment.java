package com.tranetech.slidingmenu;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.tranetech.tranetechmanagment.Aboutus;
import com.tranetech.tranetechmanagment.Contactus;
import com.tranetech.tranetechmanagment.Feedback;
import com.tranetech.tranetechmanagment.Help;
import com.tranetech.tranetechmanagment.R;

public class HomeFragment extends Fragment implements OnClickListener {
	ImageView img_1;
	Button ivabtus, ivfdbck, ivcntus, ivhlp;
	public int currentimageindex = 0;
	// Timer timer;
	// TimerTask task;
	Handler mHandler = new Handler();
	Runnable mUpdateResults;

	private int[] IMAGE_IDS = { R.drawable.homeimage_a, R.drawable.homeimage_b,
			R.drawable.homeimage_c, R.drawable.homeimage_d,
			R.drawable.homescreen_f };

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		img_1 = (ImageView) rootView.findViewById(R.id.img_1);
		ivabtus = (Button) rootView.findViewById(R.id.ivabtus);
		ivfdbck = (Button) rootView.findViewById(R.id.ivfdbck);
		ivcntus = (Button) rootView.findViewById(R.id.ivcntus);
		ivhlp = (Button) rootView.findViewById(R.id.ivhlp);
		ivabtus.setOnClickListener(this);
		ivfdbck.setOnClickListener(this);
		ivcntus.setOnClickListener(this);
		ivhlp.setOnClickListener(this);

		int delay = 1000; // delay for 1 sec.

		int period = 2000; // repeat every 4 sec.
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.post(mUpdateResults);
			}
		}, delay, period);

		mUpdateResults = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					AnimateandSlideShow();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};

		return rootView;

	}

	protected void AnimateandSlideShow() {
		// TODO Auto-generated method stub
		try {
			img_1.setImageResource(IMAGE_IDS[currentimageindex
					% IMAGE_IDS.length]);

			currentimageindex++;
			Animation rotateimage = AnimationUtils.loadAnimation(getActivity()
					.getBaseContext().getApplicationContext(), R.anim.fade);
			img_1.startAnimation(rotateimage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.ivabtus:
			finish();
			Intent i = new Intent(getActivity().getApplicationContext(),
					Aboutus.class);
			getActivity().startActivity(i);

			break;
		case R.id.ivfdbck:
			finish();
			Intent j = new Intent(getActivity().getApplicationContext(),
					Feedback.class);
			getActivity().startActivity(j);

			break;
		case R.id.ivcntus:
			finish();
			Intent k = new Intent(getActivity().getApplicationContext(),
					Contactus.class);
			getActivity().startActivity(k);
			break;
		case R.id.ivhlp:
			finish();
			Intent l = new Intent(getActivity().getApplicationContext(),
					Help.class);
			startActivity(l);
			break;

		}
	}

	private void finish() {
		// TODO Auto-generated method stub

		// android.os.Process.killProcess(android.os.Process.myPid());
		try {
			getActivity().getFragmentManager().popBackStack();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}