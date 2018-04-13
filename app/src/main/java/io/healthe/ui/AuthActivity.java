package io.healthe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.healthe.R;
import io.healthe.model.User;
import io.healthe.util.HealthePrefs;
import io.healthe.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
	
	private FragmentManager fragmentManager = null;
	
	
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == LoginFragment.REQ_AUTH_CODE) {
			if (resultCode == RESULT_OK) {
				if (data != null) {
					Task<GoogleSignInAccount> intent = GoogleSignIn.getSignedInAccountFromIntent(data);
					try {
						GoogleSignInAccount account = intent.getResult(ApiException.class);
						updateUI(account);
						Utils.showMessage(account.getDisplayName(), getApplicationContext());
					} catch (ApiException e) {
						Utils.showMessage(e.getLocalizedMessage(), getApplicationContext());
					}
				}
			} else {
				Utils.showMessage("Google sign in failed", getApplicationContext());
			}
		}
	}
	
	private void updateUI(GoogleSignInAccount account) {
		//Init prefs
		HealthePrefs prefs = HealthePrefs.get(this);
		//Init loading dialog
		MaterialDialog loading = Utils.getMaterialDialog(this);
		
		//Create user instance
		User user = new User(account.getDisplayName(), account.getEmail(), String.valueOf(account.getPhotoUrl()));
		
		//Send data to database
		prefs.getApi().createUser(user)
				.enqueue(new Callback<Void>() {
					@Override
					public void onResponse(Call<Void> call, Response<Void> response) {
						if (response != null && response.isSuccessful()) {
							loading.dismiss();
							Utils.showMessage("User created", getApplicationContext());
						} else {
							loading.dismiss();
							if (response != null) {
								Utils.showMessage(response.message(), getApplicationContext());
							}
						}
					}
					
					@Override
					public void onFailure(Call<Void> call, Throwable t) {
						loading.dismiss();
						Utils.showMessage(t.getLocalizedMessage(), getApplicationContext());
					}
				});
	}
}
