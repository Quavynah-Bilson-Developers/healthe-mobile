package io.healthe.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;

import io.healthe.model.User;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Shared preferences class for storing user data locally
 */
public class HealthePrefs {
	//constants
	public static final String HEALTHE_PREFS = "HEALTHE_PREFS";
	public static final String KEY_USER_ID = "KEY_USER_ID";
	public static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
	public static final String KEY_USERNAME = "KEY_USERNAME";
	public static final String KEY_EMAIL = "KEY_EMAIL";
	private static final String KEY_WEIGHT = "KEY_WEIGHT";
	private static final String KEY_HEIGHT = "KEY_HEIGHT";
	private static final String KEY_DOB = "KEY_DOB";
	
	private HealtheService api;
	private Context context;
	private boolean isLoggedIn;
	private SharedPreferences prefs;
	private static volatile HealthePrefs singleton;
	
	//User fields to be stored
	private long id;
	@Nullable
	private String accessToken;
	@Nullable
	private String username;
	@Nullable
	private String email;
	@Nullable
	private String weight;
	@Nullable
	private String height;
	@Nullable
	private String dob;
	
	
	public static HealthePrefs get(Context context) {
		if (singleton == null) {
			synchronized (HealthePrefs.class) {
				singleton = new HealthePrefs(context);
			}
		}
		return singleton;
	}
	
	//Constructor
	private HealthePrefs(Context context) {
		this.context = context;
		init();
	}
	
	private void init() {
		//Init shared preferences
		prefs = context.getSharedPreferences(HEALTHE_PREFS, Context.MODE_PRIVATE);
		
		//Set prefs
		accessToken = prefs.getString(KEY_ACCESS_TOKEN, null);
		isLoggedIn = accessToken != null && !TextUtils.isEmpty(accessToken);
		if (isLoggedIn) {
			id = prefs.getLong(KEY_USER_ID, 0L);
			username = prefs.getString(KEY_USERNAME, null);
			email = prefs.getString(KEY_EMAIL, null);
			weight = prefs.getString(KEY_WEIGHT, null);
			height = prefs.getString(KEY_HEIGHT, null);
			dob = prefs.getString(KEY_DOB, null);
		}
	}
	
	public void setAccessToken(@NonNull String accessToken) {
		if (!TextUtils.isEmpty(accessToken)) {
			this.accessToken = accessToken;
			isLoggedIn = true;
			prefs.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
			createApi();
		}
	}
	
	public HealtheService getApi() {
		if (api == null) createApi();
		return api;
	}
	
	private void createApi() {
		Gson gson = new Gson();
		api = new Retrofit.Builder()
				.baseUrl(Utils.SERVER_IP)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build()
				.create(HealtheService.class);
	}
	
	public void setLoggedInUser(User user) {
		if (user != null) {
			id = user.id;
			username = user.name;
			email = user.email;
			weight = user.weight;
			height = user.height;
			dob = user.dob;
			SharedPreferences.Editor editor = prefs.edit();
			editor.putLong(KEY_USER_ID, id);
			editor.putString(KEY_USERNAME, username);
			editor.putString(KEY_EMAIL, email);
			editor.putString(KEY_DOB, dob);
			editor.putString(KEY_HEIGHT, height);
			editor.putString(KEY_WEIGHT, weight);
			editor.apply();
		}
	}
	
	public void logout() {
		isLoggedIn = false;
		accessToken = null;
		id = 0L;
		username = null;
		email = null;
		dob = null;
		height = null;
		weight = null;
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(KEY_USER_ID, id);
		editor.putString(KEY_USERNAME, username);
		editor.putString(KEY_EMAIL, email);
		editor.putString(KEY_DOB, dob);
		editor.putString(KEY_HEIGHT, height);
		editor.putString(KEY_WEIGHT, weight);
		editor.apply();
		createApi();
	}
	
	private boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	public long getId() {
		return id;
	}
	
	@Nullable
	public String getUserName() {
		return username;
	}
	
	@Nullable
	public String getEmail() {
		return email;
	}
	
	@Nullable
	public String getWeight() {
		return weight;
	}
	
	@Nullable
	public String getHeight() {
		return height;
	}
	
	@Nullable
	public String getDob() {
		return dob;
	}
	
	public User getUser() {
		return new User.Builder()
				.setEmail(email)
				.setUsername(username)
				.setPassword(accessToken)
				.setDob(dob)
				.setHeight(height)
				.setWeight(weight)
				.build();
	}
}
