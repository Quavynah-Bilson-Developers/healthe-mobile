package io.healthe.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;

import io.healthe.BuildConfig;
import io.healthe.R;

/**
 * Utility class
 */
public class Utils {
	private static final String TAG = "Utils";
	
	//Material dialog for simulating background task
	public static MaterialDialog getMaterialDialog(Context context) {
		return new Builder(context)
				.progress(true, 0)
				.canceledOnTouchOutside(false)
				.content(R.string.loading_text)
				.build();
	}
	
	//validates the fields passed in
	public static boolean hasValidFields(TextInputEditText v, boolean isEmail) {
		String s = v.getText().toString();
		if (isEmail) {
			if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
				return false;
			}
		}
		return !s.isEmpty() && s.length() > 6;
	}
	
	//Logs and/or toasts a message
	public static void showMessage(String msg, Context context) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		if (BuildConfig.DEBUG) {
			Log.d(TAG, "showMessage: " + msg);
		}
	}
	
	//Checks internet connection
	public static boolean isConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
		}
		return false;
	}
	
	//Server url
//	static final String SERVER_IP = "http://10.0.2.2/serwaa/shopping_guide1/api/";
	static final String SERVER_IP = "http://10.0.2.2:8080/serwaa/shopping_guide1/";
	
	//Database tables
	public static final String USERS = "users";
	public static final String PRODUCTS = "products";
	public static final String NUTRITION = "nutrition";
}
