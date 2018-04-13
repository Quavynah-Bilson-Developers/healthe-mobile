package io.healthe.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;


/**
 * Required GlideModule for v4 API
 */
@GlideModule
public class HealtheGlideModule extends AppGlideModule {
	@Override
	public boolean isManifestParsingEnabled() {
		return false;
	}
	
	@SuppressLint("CheckResult")
	@Override
	public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
		RequestOptions defaultOptions = new RequestOptions();
		//Prefer higher image quality on low ram devices
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (activityManager != null) {
			if (activityManager.isLowRamDevice())
				defaultOptions.format(DecodeFormat.PREFER_RGB_565);
			else defaultOptions.format(DecodeFormat.PREFER_ARGB_8888);
		}
		defaultOptions.disallowHardwareConfig();
		builder.setDefaultRequestOptions(defaultOptions);
	}
}
