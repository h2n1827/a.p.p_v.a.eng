package voa.learning.english.special;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import voa.learning.english.special.common.DeveloperKey;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class HomeFragment extends Fragment {
	public static final int POS_BASIC = 3;
	public static final int POS_ADVAN = 4;

	private final Handler handler = new Handler();
	TextView title, title1, title2, title3;
	ImageView bannerh3;
	int mFlipping = 0; // Initially flipping is off
	LinearLayout box_about_1, box_about_2, saveOff;
	Button Home;
	RelativeLayout voaEng, ListenNews, EasyLis, EngCartoons, EngBasic,
			EngAdvan;

	private final static String STORETEXT = "storeEnglish.txt";

	private boolean MyStartActivity(Intent aIntent) {
		try {
			startActivity(aIntent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.homeboxfragment, container, false);

		ViewFlipper flipper = (ViewFlipper) view.findViewById(R.id.flipper1);
		if (mFlipping == 0) {
			/** Start Flipping */
			flipper.startFlipping();
			mFlipping = 1;
		}

		box_about_1 = (LinearLayout) view.findViewById(R.id.box_about_1);
		box_about_2 = (LinearLayout) view.findViewById(R.id.box_about_2);
		
		Home = (Button) view.findViewById(R.id.buttonstart);

		Home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//MainActivity.setActiveFragment(0);
				MainActivity.checkHome = true;
			}
		});

		saveOff = (LinearLayout) view.findViewById(R.id.box_home_bottom);

		saveOff.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				ListSaveOfflineFragment saveofflinefragment = new ListSaveOfflineFragment();
//
//				getFragmentManager().beginTransaction()
//						.replace(R.id.content_frame_navigation, saveofflinefragment)
//						.commit();
//				MainActivity.checkHome = false;
			}
		});

		box_about_1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MainActivity.setAboutUsFragment();
				MainActivity.checkHome = false;
			}
		});
		box_about_2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(Intent.ACTION_VIEW);
				// Try Google play
				intent.setData(Uri
						.parse("market://details?id=start.listening.english"));
				if (!MyStartActivity(intent)) {
					// Market (Google play) app seems not installed, let's try
					// to open a webbrowser
					intent.setData(Uri
							.parse("https://play.google.com/store/apps/details?id=start.listening.english"));
					if (!MyStartActivity(intent)) {
						// Well if this also fails, we have run out of options,
						// inform the user.
						Toast.makeText(
								getActivity(),
								"Could not open Android market, please install the market app.",
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});

		Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
		String Lang = readFileInternalStorage(STORETEXT, getActivity());
		//Log.e("LOGL", "doc chon: " + Lang);
		if (Lang.contentEquals(""))
			Lang = "VietNam";
		DeveloperKey.languageCode = Lang;
		String compareValue = DeveloperKey.languageCode;
		
		for (int i = 0; i < spinner.getCount(); i++) 
		{
			if(spinner.getItemAtPosition(i).toString().equals(compareValue)){
				spinner.setSelection(i);
				break;
			}
		}

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String text = parent.getSelectedItem().toString();
				//Log.e("LOGL", "chon: " + text);
				writeFileInternalStorage(text, getActivity(), STORETEXT);
				DeveloperKey.languageCode = text;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		return view;
	}

	public static String readFileInternalStorage(String fileName,
			Context context) {
		String stringToReturn = " ";
		try {
			if (isSdReadable()) // isSdReadable()e method is define at bottom of
								// the post
			{
				String sfilename = fileName;
				InputStream inputStream = context.openFileInput(sfilename);

				if (inputStream != null) {
					InputStreamReader inputStreamReader = new InputStreamReader(
							inputStream);
					BufferedReader bufferedReader = new BufferedReader(
							inputStreamReader);
					String receiveString = "";
					StringBuilder stringBuilder = new StringBuilder();

					while ((receiveString = bufferedReader.readLine()) != null) {
						stringBuilder.append(receiveString);
					}
					inputStream.close();
					stringToReturn = stringBuilder.toString();
				}
			}
		} catch (FileNotFoundException e) {
			Log.e("TAG", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("TAG", "Can not read file: " + e.toString());
		}

		return stringToReturn;
	}

	public static void writeFileInternalStorage(String strWrite,
			Context context, String fileName) {
		try {
			// Check if Storage is Readable
			if (isSdReadable()) // isSdReadable()e method is define at bottom of
								// the post
			{
				String smsfilename = fileName;
				FileOutputStream fos = context.openFileOutput(smsfilename,
						Context.MODE_PRIVATE);
				fos.write(strWrite.getBytes());
				fos.flush();
				fos.close();

			}
		} catch (Exception e) {
			// Your Code
		}
	}

	public static boolean isSdReadable() {
		boolean mExternalStorageAvailable = false;
		try {
			String state = Environment.getExternalStorageState();

			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// We can read and write the media
				mExternalStorageAvailable = true;
			} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				// We can only read the media
				mExternalStorageAvailable = true;
			} else {
				// Something else is wrong. It may be one of many other
				// states, but all we need to know is we can neither read nor
				// write
				mExternalStorageAvailable = false;
			}
		} catch (Exception ex) {

		}
		return mExternalStorageAvailable;
	}

}
