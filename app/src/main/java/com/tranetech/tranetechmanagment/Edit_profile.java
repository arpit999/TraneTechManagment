package com.tranetech.tranetechmanagment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.tranetech.slidingmenu.MainActivity;
import com.tranetech.slidingmenu.ProfileFragment;
import com.tranetech.tranetechmanagment.Feedback.ShowDialogAsyncTask;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Edit_profile extends Activity implements OnClickListener {

	int serverResponseCode = 0;
	ProgressDialog dialog = null;

	String upLoadServerUri = null;

	/********** File Path *************/

	final String uploadFilePath = "/mnt/sdcard/";
	final String uploadFileName = ".jpg";
	String picturePath;
	public static String URL;
	public static String URLmessage, success;
	ProgressDialog mprocessingdialog;
	public static Bitmap bmp, thumbnail, bitmap;
	public static ImageView img_profile;
	EditText etaddress, etphone, etpcname, etpcpassword, etdoc, etacc;
	Button btn_upload, btn_save;
	Spinner spin_location;
	TextView ussername_edit, phone, address, pcname, pcpassword, docid, ac;
	String str_id, str_emailid, str_mobile_no, str_address, str_pcname,
			str_pcpwd, str_doc_id, str_bank_ac_no, str_usertype, str_location,
			str_position, selecteditem;
	String str_etaddress, str_etphone, str_etpcname, str_etpcpassword,
			str_etdoc, str_etacc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);
		// get action bar
		ActionBar action = getActionBar();

		// Enabling Up / Back navigation
		action.setDisplayHomeAsUpEnabled(true);

		initialization();
		Set_text_from_web();
		/************* Php script path ****************/
		upLoadServerUri = "http://www.androidexample.com/media/UploadToServer.php";
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

	private void Set_text_from_web() {
		// TODO Auto-generated method stub
		ussername_edit.setText(LoginDetails.ussername);
		etphone.setText(LoginDetails.contect_no);
		etaddress.setText(LoginDetails.address);
		etpcname.setText(LoginDetails.pc_name);
		etpcpassword.setText(LoginDetails.pc_psw);
		etdoc.setText(LoginDetails.document_id);
		etacc.setText(LoginDetails.bank_ac);
	}

	private void Get_text_from_edittext() {
		// TODO Auto-generated method stub

		str_id = LoginDetails.r_id;
		str_etphone = etphone.getText().toString();
		str_etaddress = etaddress.getText().toString();
		str_etpcname = etpcname.getText().toString();
		str_etpcpassword = etpcpassword.getText().toString();
		str_etdoc = etdoc.getText().toString();
		str_etacc = etacc.getText().toString();
	}

	private void initialization() {
		// TODO Auto-generated method stub

		phone = (TextView) findViewById(R.id.phone);
		ussername_edit = (TextView) findViewById(R.id.tv_ussername_edit);
		img_profile = (ImageView) findViewById(R.id.img_profile);
		address = (TextView) findViewById(R.id.address);
		pcname = (TextView) findViewById(R.id.pcname);
		pcpassword = (TextView) findViewById(R.id.pcpassword);
		docid = (TextView) findViewById(R.id.tv_docID);
		etdoc = (EditText) findViewById(R.id.etdoc);
		ac = (TextView) findViewById(R.id.ac);
		etacc = (EditText) findViewById(R.id.etacc);
		btn_upload = (Button) findViewById(R.id.btn_upload);
		etaddress = (EditText) findViewById(R.id.etaddress);
		etphone = (EditText) findViewById(R.id.etphone);
		etpcname = (EditText) findViewById(R.id.etpcname);
		etpcpassword = (EditText) findViewById(R.id.etpcpassword);
		btn_save = (Button) findViewById(R.id.btn_save);
		spin_location = (Spinner) findViewById(R.id.spin_location);
		spin_location.setOnItemSelectedListener(new OnItemSelectedListener() {

			@SuppressLint("DefaultLocale")
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				selecteditem = spin_location.getSelectedItem().toString();
				selecteditem = selecteditem.equals("All Types") ? selecteditem
						.replaceAll(" ", "%20").toLowerCase() : selecteditem;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		img_profile.setOnClickListener(this);
		btn_upload.setOnClickListener(this);
		btn_save.setOnClickListener(this);

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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.img_profile:

			selectImage();
			overridePendingTransition(0, 0);
			break;
		case R.id.btn_upload:

			dialog = ProgressDialog.show(Edit_profile.this, "",
					"Uploading file...", true);

//			Intent intent = new Intent();
//			intent.setType("image/*");
//			intent.setAction(Intent.ACTION_GET_CONTENT);
//		startActivityForResult(
//					Intent.createChooser(intent, "Select Picture"), 1);

			new Thread(new Runnable() {
				public void run() {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"uploading started....."+uploadFilePath+""+picturePath, Toast.LENGTH_SHORT).show();
						}
					});

					uploadFile(uploadFilePath + "" + picturePath);

				}
			}).start();
			break;
		case R.id.btn_save:

			if (isWifiAvailable()||isNetworkAvailable()) {
				if (etphone.getText().toString().equals("")) {
					etphone.setError("Enter the phone number");
					etphone.requestFocus();
				} else if (etaddress.getText().toString().equals("")) {
					etaddress.setError("Enter the Address");

					etaddress.requestFocus();
				} else if (etpcname.getText().toString().equals("")) {
					etpcname.setError("Enter the PC name");
					etpcname.requestFocus();
				} else if (etpcpassword.getText().toString().equals("")) {
					etpcpassword.setError("Enter PC Password");
					etpcpassword.requestFocus();
				} else if (etdoc.getText().toString().equals("")) {
					etdoc.setError("Enter DOC ID");
					etdoc.requestFocus();
				} else if (etacc.getText().toString().equals("")) {
					etacc.setError("Enter Bank A/C Number");
					etacc.requestFocus();
				} else {
					Get_text_from_edittext();
					try {
						URL = "http://openspace.tranetech.com/mis/profile.php?"
								+ "R_id=" + str_id + "&R_mono="
								+ str_etphone.replaceAll(" ", "")
								+ "&R_address="
								+ str_etaddress.replaceAll(" ", "%20")
								+ "&R_pcname="
								+ str_etpcname.replaceAll(" ", "%20")
								+ "&R_pcpwd="
								+ str_etpcpassword.replaceAll(" ", "")
								+ "&R_docid=" + str_etdoc.replaceAll(" ", "")
								+ "&R_bno=" + str_etacc.replaceAll(" ", "")
								+ "&R_location=" + selecteditem;

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
						"Please Check Internet..", Toast.LENGTH_LONG).show();
			}

		default:
			break;
		}
	}
	public int uploadFile(String sourceFileUri) {

		String fileName = sourceFileUri;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			dialog.dismiss();

			Log.e("uploadFile", "Source File not exist :" + uploadFilePath + "" 
					+ picturePath);

			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(),
							"Source File not exist :", Toast.LENGTH_SHORT).show();
				}
			});

			return 0;

		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name= uploaded_file"
						+ fileName + "" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "
						+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {

					runOnUiThread(new Runnable() {
						public void run() {

							String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
									+ " http://www.androidexample.com/media/uploads/"
									+ picturePath;

							Toast.makeText(Edit_profile.this,
									"File Upload Complete.", Toast.LENGTH_SHORT)
									.show();
						
							

						}
					});
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {

				dialog.dismiss();
				ex.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(Edit_profile.this,
								"MalformedURLException Exception : check script url.", Toast.LENGTH_SHORT)
								.show();
					}
				});

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				dialog.dismiss();
				e.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(Edit_profile.this,
								"Got Exception : see logcat ",
								Toast.LENGTH_SHORT).show();
					}
				});
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}
			dialog.dismiss();
			return serverResponseCode;

		} // End else block
	}
	public class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mprocessingdialog = new ProgressDialog(Edit_profile.this);
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
				// Log.d("JSON data MESSAGE FROM URL", URLmessage);
				success = jobj.getString("success").toString();
				Log.d("MESSAGE FROM URL(sucess ? )", success);
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

				Intent to_profile = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(to_profile);
				Toast.makeText(getApplicationContext(), " " + URLmessage,
						Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(getApplicationContext(), "" + URLmessage,
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	private void selectImage() {

		final CharSequence[] options = { "Take Photo", "Choose from Gallery",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(Edit_profile.this);

		builder.setTitle("Add Photo!");

		builder.setItems(options, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (options[item].equals("Take Photo"))

				{

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");

					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

					startActivityForResult(intent, 1);

				}

				else if (options[item].equals("Choose from Gallery"))

				{

					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 2);

				}

				else if (options[item].equals("Cancel")) {

					dialog.dismiss();

				}

			}

		});

		builder.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			if (requestCode == 1) {

				File f = new File(Environment.getExternalStorageDirectory()
						.toString());

				for (File temp : f.listFiles()) {

					if (temp.getName().equals("temp.jpg")) {

						f = temp;

						break;

					}

				}

				try {

					Bitmap bitmap;

					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),

					bitmapOptions);

					img_profile.setImageBitmap(bitmap);

					String path = android.os.Environment

					.getExternalStorageDirectory()

					+ File.separator

					+ "Phoenix" + File.separator + "default";

					f.delete();

					OutputStream outFile = null;

					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");

					try {

						outFile = new FileOutputStream(file);

						bitmap.compress(Bitmap.CompressFormat.PNG, 100, outFile);

						outFile.flush();

						outFile.close();

						bitmap.recycle();
						bitmap = null;
					} catch (FileNotFoundException e) {

						e.printStackTrace();

					} catch (IOException e) {

						e.printStackTrace();

					} catch (Exception e) {

						e.printStackTrace();

					}

				} catch (Exception e) {

					e.printStackTrace();

				}

			} else if (requestCode == 2) {

				Uri selectedImage = data.getData();

				String[] filePath = { MediaStore.Images.Media.DATA };

				Cursor c = getContentResolver().query(selectedImage, filePath,
						null, null, null);

				c.moveToFirst();

				int columnIndex = c.getColumnIndex(filePath[0]);

				picturePath = c.getString(columnIndex);

				c.close();

				thumbnail = (BitmapFactory.decodeFile(picturePath));

				Log.d("path of image from gallery......******************.........",
						picturePath + "");

				img_profile.setImageBitmap(thumbnail);

				// tvimgpath.setText("" + picturePath);

			}

		}

	}
}
