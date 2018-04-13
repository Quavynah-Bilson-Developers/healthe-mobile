package io.healthe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.healthe.R;

public class SplashActivity extends AppCompatActivity {
	@BindView(R.id.splash_app_name)
	TextView appName;
	@BindView(R.id.splash_app_desc)
	TextView appDesc;
	@BindView(R.id.container)
	ViewGroup container;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ButterKnife.bind(this);
		
		//Delay screen for 1.5 secs and then show app description text
		new Handler().postDelayed(this::showDescription, 1500);
	}
	
	private void showDescription() {
		TransitionManager.beginDelayedTransition(container);
		appDesc.setVisibility(View.VISIBLE);
		new Handler().postDelayed(() -> {
			startActivity(new Intent(SplashActivity.this, AuthActivity.class));
			finish();
		}, 1500);
	}
}
