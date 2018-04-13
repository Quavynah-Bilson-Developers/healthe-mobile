package io.healthe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog.Builder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.healthe.BuildConfig;
import io.healthe.R;
import io.healthe.model.User;
import io.healthe.model.UserQuery;
import io.healthe.util.HealthePrefs;
import io.healthe.util.adapter.UserQueryAdapter;

public class HomeActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	
	private static final String TAG = "HomeActivity";
	
	//Bind data
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.fab)
	FloatingActionButton fab;
	@BindView(R.id.drawer_layout)
	DrawerLayout drawer;
	@BindView(R.id.nav_view)
	NavigationView navigationView;
	@BindView(R.id.grid_prev_scan)
	RecyclerView grid;
	@BindView(R.id.swipe_grid)
	SwipeRefreshLayout refreshLayout;
	
	private UserQueryAdapter adapter;
	private HealthePrefs prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//Add data binding call
		ButterKnife.bind(this);
		
		//Init prefs
		prefs = HealthePrefs.get(this);
		
		//Setup toolbar support
		setSupportActionBar(toolbar);
		
		//By default, the drawer toggle button on the toolbar is white in color, so we need to
		// change it to fit our color scheme
		//Set action for toolbar navigation icon
		toolbar.setNavigationOnClickListener(v -> {
			if (drawer.isDrawerOpen(GravityCompat.START)) {
				drawer.closeDrawer(GravityCompat.START);
			} else {
				drawer.openDrawer(GravityCompat.START);
			}
		});
		
		//Setup recyclerview
		List<UserQuery> queries = new ArrayList<>(0);
		adapter = new UserQueryAdapter(this, queries);
		grid.setAdapter(adapter);
		GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
		grid.setLayoutManager(layoutManager);
		grid.setHasFixedSize(true);
		grid.setItemAnimator(new DefaultItemAnimator());
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				Toast.makeText(HomeActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
			}
		});
		
		//Get navigation header
		navigationView.setNavigationItemSelectedListener(this);
		View headerView = navigationView.getHeaderView(0);
		setupHeader(headerView);
	}
	
	private void setupHeader(View headerView) {
		if (headerView == null) return;
		
		//Retrieve the children in the headerView
//		ImageView profile = headerView.findViewById(R.id.user_profile);
		TextView username = headerView.findViewById(R.id.user_name);
		TextView email = headerView.findViewById(R.id.user_email);
		
		User user = prefs.getUser();
		if (user != null) {
			//Setup user
			username.setText(user.name);
			email.setText(user.email);
			
		}
	}
	
	@Override
	public void onBackPressed() {
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch (id) {
			case R.id.action_search:
				startActivity(new Intent(HomeActivity.this, SearchActivity.class));
				return true;
			case R.id.action_sort:
				CharSequence[] arrayOfOptions = {"Date", "Location"};
				new Builder(HomeActivity.this)
						.title(R.string.sort_text)
						.negativeText("Cancel")
						.items(arrayOfOptions)
						.onNegative((dialog, which) -> dialog.dismiss())
						.itemsCallbackSingleChoice(0, (dialog, itemView, which, text) -> {
							doSort(text);
							return true;
						})
						.build().show();
				return true;
			case R.id.action_profile:
				startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
				return true;
			case R.id.action_help:
				return true;
			case R.id.action_about:
				startActivity(new Intent(HomeActivity.this, AboutActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	private void doSort(CharSequence text) {
		if (BuildConfig.DEBUG) {
			Log.d(TAG, "doSort() called with: text = [" + text + "]");
		}
		// TODO: 3/16/2018 Do sort by text value
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		
		switch (id) {
			case R.id.nav_share:
				break;
			case R.id.nav_feedback:
				break;
		}
		
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	@OnClick(R.id.fab)
	protected void startScanActivity() {
		startActivity(new Intent(HomeActivity.this, ScanImageActivity.class));
	}
}
