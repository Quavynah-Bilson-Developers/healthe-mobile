package io.healthe.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

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
 * A simple login {@link Fragment}.
 */
public class LoginFragment extends Fragment {
	@BindView(R.id.email_float_label)
	TextInputLayout emailFloatLabel;
	@BindView(R.id.email_edt)
	TextInputEditText email;
	@BindView(R.id.password_float_label)
	TextInputLayout passwordFloatLabel;
	@BindView(R.id.password_edt)
	TextInputEditText password;
	@BindView(R.id.login)
	Button login;
	
	private FragmentActivity activity;
	private MaterialDialog loading;
	private HealthePrefs prefs;
	
	public LoginFragment() {
		// Required empty public constructor
	}
	
	@NonNull
	public static LoginFragment getInstance() {
		return new LoginFragment();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		//Bind data
		ButterKnife.bind(this, view);
		return view;
	}
	
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//Setup activity
		this.activity = getActivity();
		this.loading = Utils.getMaterialDialog(getContext());
		this.prefs = HealthePrefs.get(getContext());
		
	}
	
	@OnClick(R.id.login)
	void doLogin() {
		if (Utils.hasValidFields(email, true) && Utils.hasValidFields(password, false)) {
			loading.show();
			String em = email.getText().toString();
			String pwd = password.getText().toString();
			
			//Send data to server for authentication
			prefs.getApi().loginUser(em, pwd).enqueue(new Callback<User>() {
				@Override
				public void onResponse(Call<User> call, Response<User> response) {
					if (response != null && response.isSuccessful()) {
						//Returns user as a json object
						User user = response.body();
						//Set user login state to be true
						prefs.setAccessToken(String.valueOf(user.id));
						
						//Store user data locally
						prefs.setLoggedInUser(user);
						
						//Dismiss loading dialog
						loading.dismiss();
						
						//Send user to home screen
						activity.startActivity(new Intent(activity, HomeActivity.class));
						activity.finish();
					} else {
						showMessage(response.message());
					}
				}
				
				@Override
				public void onFailure(Call<User> call, Throwable t) {
					showMessage(t.getLocalizedMessage());
				}
			});
		} else {
			Utils.showMessage("Please enter the right credentials", getContext());
		}
	}
	
	//Shows the error to the user as a toast message
	private void showMessage(CharSequence message) {
		if (loading.isShowing()) loading.dismiss();
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}
}
