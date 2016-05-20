package com.tranetech.tranetechmanagment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		StartAnimations();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// This method will be executed once the timer is over
				// Start your app main activity

				Intent i = new Intent(SplashActivity.this, LoginDetails.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);

	}

	private void StartAnimations() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
		l.clearAnimation();
		l.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		anim.reset();

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();

	}
}
