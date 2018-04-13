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

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.healthe.R;
import io.healthe.util.HealthePrefs;
import io.healthe.util.Utils;

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
	@BindView(R.id.google_auth)
	SignInButton googleAuth;
	
	private FragmentActivity activity;
	private MaterialDialog loading;
	private HealthePrefs prefs;
	public static final int REQ_AUTH_CODE = 129;
	
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
			/*loading.show();
			String em = email.getText().toString();
			String pwd = password.getText().toString();
			prefs.getApi().getCurrentUser(em, pwd)
					.enqueue(new Callback<User>() {
						@Override
						public void onResponse(Call<User> call, Response<User> response) {
							if (response != null && response.isSuccessful()) {
								loading.dismiss();
								Utils.showMessage("User created", getContext());
							} else {
								loading.dismiss();
								if (response != null) {
									Utils.showMessage(response.message(), getContext());
								}
							}
						}
						
						@Override
						public void onFailure(Call<User> call, Throwable t) {
							loading.dismiss();
							Utils.showMessage(t.getLocalizedMessage(), getContext());
						}
					});*/
			activity.startActivity(new Intent(activity, HomeActivity.class));
			activity.finish();
		} else {
			Utils.showMessage("Please enter the right credentials", getContext());
		}
	}
	
	@OnClick(R.id.google_auth)
	void doGoogleAuth() {
		//Configure the sign in to request user details
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		GoogleSignInClient apiClient = GoogleSignIn.getClient(activity.getApplicationContext(), gso);
		startActivityForResult(apiClient.getSignInIntent(), REQ_AUTH_CODE);
	}
}
