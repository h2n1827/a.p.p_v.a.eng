package voa.learning.english.special;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import voa.learning.english.special.adapter.CategoryAdapter;
import voa.learning.english.special.adapter.DrawerAdapter;
import voa.learning.english.special.common.DeveloperKey;
import voa.learning.english.special.common.PrivateService;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends FragmentActivity implements
		OnItemClickListener, OnQueryTextListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	SearchView mSearchView;
	static ArrayList<CategoryAdapter> listCategory;

	private static FragmentManager fragmentManager;
	static boolean checkHome = false;
	String mDrawerTitle = "English Listening";

	// private InterstitialAd interstitial;
	int count_adv = 0;
	static int oldposAds = 0;
	static int curposAds = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Obtain the shared Tracker instance.

		// Create the interstitial.
		// interstitial = new InterstitialAd(this);
		// interstitial.setAdUnitId("ca-app-pub-3221103252703564/4721817734");
		// loadInterstitial();
		// Set the AdListener.
		// interstitial.setAdListener(new AdListener() {
		// @Override
		// public void onAdLoaded() {
		// Log.d("load google", "onAdLoaded");
		// Toast.makeText(getApplicationContext(), "onAdLoaded",
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// @Override
		// public void onAdFailedToLoad(int errorCode) {
		// Log.d("load google", "onAdLoaded Failse");
		// Toast.makeText(getApplicationContext(), "onAdLoaded Failse",
		// Toast.LENGTH_SHORT).show();
		// }
		// });

		// initCategoryData();
		checkHome = false;
		fragmentManager = getSupportFragmentManager();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).build();
		ImageLoader.getInstance().init(config);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_navigation_drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.menu_frame_navigation);

		mDrawerList.setOnItemClickListener(this);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
			}
		};

		getActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		Log.e("Load URL", "onAdLoaded");

		new DownloadCategory()
				.execute(new String[] { DeveloperKey.ETAVIRPLRU });
		LoadingFragment initFragment = new LoadingFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame_navigation, initFragment).commit();

	}

	// Invoke displayInterstitial() when you are ready to display an
	// interstitial.
	// public void loadInterstitial() {
	// AdRequest adRequest = new AdRequest.Builder().build();
	// // Begin loading your interstitial.
	// interstitial.loadAd(adRequest);
	// }
	//
	// public void displayInterstitial() {
	// if (interstitial.isLoaded()) {
	// interstitial.show();
	// }
	// }



	public static void setAboutUsFragment() {
		AboutUsFragment aboutfragment = new AboutUsFragment();

		fragmentManager.beginTransaction()
				.replace(R.id.content_frame_navigation, aboutfragment).commit();
	}

	public void setHomeFragment() {
		HomeFragment homefragment = new HomeFragment();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame_navigation, homefragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		mSearchView = (SearchView) menu.findItem(R.id.menu_item_search)
				.getActionView();
		mSearchView.setOnQueryTextListener(this);
		return true;
	}

	boolean mIsShowActionBar = true;

	public void hideActionBar() {
		if (!mIsShowActionBar)
			return;

		mIsShowActionBar = false;
		getActionBar().hide();
	}

	public void showActionBar() {
		if (mIsShowActionBar)
			return;
		mIsShowActionBar = true;
		getActionBar().show();
	}

	// public void initCategoryData() {
	// listCategory = new ArrayList<CategoryAdapter>();
	//
	// CategoryAdapter catergory = new CategoryAdapter();
	// catergory.setTitle("De che");
	// catergory.setDrawerIcon(R.drawable.deche);
	// catergory.setLink("http://netdepviet.vn/video/deche.php");
	// CategoryAdapter catergory2 = new CategoryAdapter();
	// catergory2.setTitle("halflife");
	// catergory2.setDrawerIcon(R.drawable.halflife);
	// catergory2.setLink("http://netdepviet.vn/video/halflife.php");
	// CategoryAdapter catergory3 = new CategoryAdapter();
	// catergory3.setTitle("starcarft");
	// catergory3.setDrawerIcon(R.drawable.starcarft);
	// catergory3.setLink("http://netdepviet.vn/video/starcraft.php");
	//
	// listCategory.add(catergory);
	// listCategory.add(catergory2);
	// listCategory.add(catergory3);
	// }

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		count_adv = count_adv + 1;
		if (count_adv == 1) {
			// displayInterstitial();
		}
		mDrawerLayout.closeDrawer(mDrawerList);
		checkHome = true;
		if (count_adv % 4 == 0) {
			// loadInterstitial();
			// interstitial.setAdListener(new AdListener() {
			// @Override
			// public void onAdLoaded() {
			// displayInterstitial();
			// }
			// });
		}
		// check link extent : about me
		String titleGet = listCategory.get(position).getTitle();
		if (titleGet.equalsIgnoreCase("About Me")) {
			setAboutUsFragment();
			checkHome = false;
		} else if (titleGet.equalsIgnoreCase("Save Offline")) {
			//setSaveOfflineFragment();
			checkHome = false;
		}

		mDrawerTitle = titleGet;

	}

	private class DownloadCategory extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();

				// encrytion code webservice
				PrivateService security = new PrivateService();
				long time = System.currentTimeMillis();

				String keyprivate = security.PRIVATE_KEY;
				String url_encry = url + keyprivate + time;
				String en_url_encry = security.securityPrivate(url_encry);

				HttpGet httpGet = new HttpGet(url + "?encry=" + en_url_encry
						+ "&time=" + time);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			if (mDestroy)
				return;
			ArrayList<CategoryAdapter> categorys = new ArrayList<CategoryAdapter>();

			try {
				JSONObject json = new JSONObject(result);
				JSONArray array = json.getJSONArray("categories");

				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonCategory = array.getJSONObject(i);
					CategoryAdapter category = new CategoryAdapter();
					category.setTitle(jsonCategory.getString("title"));
					category.setLink(jsonCategory.getString("link"));
					category.setIcon(jsonCategory.getString("img"));
					categorys.add(category);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// ADD about us bottom list menu
			CategoryAdapter category = new CategoryAdapter();

			category.setTitle("Save Offline");
			category.setLink("saveoffline");
			category.setIcon(DeveloperKey.ERRORNIMG + "/savemp3.png");
			categorys.add(category);

			CategoryAdapter category1 = new CategoryAdapter();
			category1.setTitle("About Me");
			category1.setLink("aboutlink");
			category1.setIcon(DeveloperKey.ERRORNIMG + "/aboutme.png");
			categorys.add(category1);

			// add list category
			listCategory = categorys;
			finishLoadCategory();
		}
	}

	public void finishLoadCategory() {
		DrawerAdapter drawerAdapter = new DrawerAdapter(this,
				R.layout.drawer_item, listCategory);
		mDrawerList.setAdapter(drawerAdapter);
		if (!isOnline()) {
			if (listCategory.size() != 0) {
				//setSaveOfflineFragment();`
				checkHome = false;
				displayAlert();
			}
		} else {
			if (listCategory.size() != 0) {
				// setActiveFragment(0);
				setHomeFragment();
				checkHome = false;
			}
		}

	}

	boolean mDestroy = false;

	public void onDestroy() {
		super.onDestroy();
		mDestroy = true;

	}

	private void toggleDrawer() {
		if (mDrawerLayout.isDrawerOpen(this.mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			mDrawerLayout.openDrawer(mDrawerList);
			// this.menuFrame.getView()
		}
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggleDrawer();
			break;
		case R.id.menu_item_search:
			mSearchView.setIconified(false);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onQueryTextChange(String arg0) {

		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onBackPressed() {

		new AlertDialog.Builder(this).setTitle("Really Exit?")
				.setMessage("Do you want to rate us 5 star?")
				// .setNegativeButton("No", null)
				.setPositiveButton("Yes, Go to rate.", new OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						// Try Google play
						intent.setData(Uri
								.parse("market://details?id=start.listening.english"));
						if (!MyStartActivity(intent)) {
							// Market (Google play) app seems not
							// installed, let's try to open a webbrowser
							intent.setData(Uri
									.parse("https://play.google.com/store/apps/details?id=start.listening.english"));
							if (!MyStartActivity(intent)) {
								// Well if this also fails, we have run
								// out of options, inform the user.
								Toast.makeText(
										getApplicationContext(),
										"Could not open Android market, please install the market app.",
										Toast.LENGTH_SHORT).show();
							}
						}
					}
				}).setNeutralButton("No", new OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

						MainActivity.super.onBackPressed();
					}

				}).create().show();

	}

	private boolean MyStartActivity(Intent aIntent) {
		try {
			startActivity(aIntent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public void displayAlert() {
		new AlertDialog.Builder(this)
				.setMessage(
						"Please Check Your Internet Connection and Try Again")
				.setTitle("Network Error")
				.setCancelable(true)
				.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

							}
						}).show();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
