package io.healthe.ui;

import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.healthe.R;

/**
 * User authentication screen
 */
public class AuthActivity extends AppCompatActivity {
	//Widgets
	@BindView(R.id.container)
	ViewGroup container;
	@BindView(R.id.header_container)
	ViewGroup headerContainer;
	@BindView(R.id.frame_container)
	ViewGroup frameContainer;
	@BindView(R.id.auth_action_login)
	TextView loginButton;
	@BindView(R.id.auth_action_register)
	TextView registerButton;
	@BindView(R.id.auth_actions_container)
	ViewGroup actionsContainer;
	
	private FragmentManager fragmentManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		ButterKnife.bind(this);
		
		//Get fragmentManager in order to manipulate the fragments
		fragmentManager = getSupportFragmentManager();
		setupFragments(fragmentManager);
		
	}
	
	private static void setupFragments(FragmentManager fragmentManager) {
		//Set default page
		fragmentManager.beginTransaction().replace(R.id.auth_frame, RegisterFragment.getInstance(),
				RegisterFragment.class.getCanonicalName()).commit();
	}
	
	/**
	 * Action for login text
	 */
	@OnClick(R.id.auth_action_register)
	protected void replaceLoginFragment() {
		//1. TransitionManager handles transition with animation
		//2. Change text color
		//3. Change text size
		//4. Change text fragment
		TransitionManager.beginDelayedTransition(container);
		//colors
		loginButton.setTextColor(getResources().getColor(R.color.text_tertiary_dark));
		registerButton.setTextColor(getResources().getColor(R.color.text_primary_dark));
		//sizes
		loginButton.setTextSize(14.0f);
		registerButton.setTextSize(22.0f);
		//fragment
		fragmentManager.beginTransaction().replace(R.id.auth_frame, RegisterFragment.getInstance(),
				RegisterFragment.class.getCanonicalName()).commit();
	}
	
	/**
	 * Action for registration text
	 */
	@OnClick(R.id.auth_action_login)
	protected void replaceRegFragment() {
		//1. TransitionManager handles transition with animation
		//2. Change text color
		//3. Change text size
		//4. Change text fragment
		TransitionManager.beginDelayedTransition(container);
		//colors
		registerButton.setTextColor(getResources().getColor(R.color.text_tertiary_dark));
		loginButton.setTextColor(getResources().getColor(R.color.text_primary_dark));
		//sizes
		registerButton.setTextSize(14.0f);
		loginButton.setTextSize(22.0f);
		//fragment
		fragmentManager.beginTransaction().replace(R.id.auth_frame, LoginFragment.getInstance(),
				LoginFragment.class.getCanonicalName()).commit();
		
	}
	
}
