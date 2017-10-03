package com.recyclerviewstaggeredgridview;

import android.os.*;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.*;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by yuvaraj on 3/4/16.
 */

public class MainActivity extends ActionBarActivity {

	private static final String[] colors = new String[] {"red", "blue", "black", "white", "yellow"};
	private static final String[] locations = new String[] {"seattle", "sf", "LA", "NYC"};

	MyGridAdapter adapter;
	RecyclerView gridView;
	private AdView mAdView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MobileAds.initialize(getApplicationContext(),"ca-app-pub-3940256099942544~3347511713");

		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("F7B6AD9CD33159F83F5DAF6465E0E4EA")
				.build();
		mAdView.loadAd(adRequest);

		adapter = new MyGridAdapter(this);
		gridView = (RecyclerView) findViewById(R.id.grid_view);
		gridView.setHasFixedSize(true);
		StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
		gridView.setLayoutManager(sglm);
		gridView.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String color = colors[((int) (colors.length * Math.random()))];
		String location = locations[((int) (locations.length * Math.random()))];
		String term = (String) item.getTitle();
		String[] tags = new String[]{color,location,term};
		getImagesForTag(tags);
		return true;
	}

	private void getImagesForTag(String[] tag) {
		LoadFlickerFeed task = new LoadFlickerFeed() {
			@Override
			public void onPostExecute(String[] imageUrls) {
				synchronized(adapter) {
					int startPosition = adapter.getItemCount();
					for(String url : imageUrls) {
						int width = 150;
						int height = (int) (50 + Math.random() * 450);

						ImageModel imageModel=new ImageModel();
						imageModel.setUrl(url);
						imageModel.setWidth(width);
						imageModel.setHeight(height);
						adapter.addDrawable(imageModel);
						imageModel=null;
					}
					adapter.notifyItemRangeInserted(startPosition, imageUrls.length);
				}
			}
		};
		task.execute(tag);
	}

	/** Called when leaving the activity */
	@Override
	public void onPause() {
		if (mAdView != null) {
			mAdView.pause();
		}
		super.onPause();
	}

	/** Called when returning to the activity */
	@Override
	public void onResume() {
		super.onResume();
		if (mAdView != null) {
			mAdView.resume();
		}
	}

	/** Called before the activity is destroyed */
	@Override
	public void onDestroy() {
		if (mAdView != null) {
			mAdView.destroy();
		}
		super.onDestroy();
	}
}
