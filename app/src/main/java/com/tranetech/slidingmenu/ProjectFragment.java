package com.tranetech.slidingmenu;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tranetech.tranetechmanagment.R;

public class ProjectFragment extends Fragment {
	
	public ProjectFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_project, container, false);
     
        if (isWifiAvailable() || isNetworkAvailable()) {
        	
        }else{
        	Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        
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
}
