package io.healthe.ui;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.healthe.R;
import io.healthe.model.User;
import io.healthe.util.DatePickerFragment;
import io.healthe.util.HealthePrefs;
import io.healthe.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple registration {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
	@BindView(R.id.username_float_label)
	TextInputLayout usernameFloatLabel;
	@BindView(R.id.username_edt)
	TextInputEditText username;
	@BindView(R.id.email_float_label)
	TextInputLayout emailFloatLabel;
	@BindView(R.id.email_edt)
	TextInputEditText email;
	@BindView(R.id.password_float_label)
	TextInputLayout passwordFloatLabel;
	@BindView(R.id.password_edt)
	TextInputEditText password;
	@BindView(R.id.height_edt)
	TextInputEditText height;
	@BindView(R.id.weight_edt)
	TextInputEditText weight;
	@BindView(R.id.pick_dob)
	Button pickDOB;
	@BindView(R.id.get_started)
	Button register;
	
	private FragmentActivity activity;
	private MaterialDialog loading;
	private HealthePrefs prefs;
	
	public RegisterFragment() {
	}
	
	@NonNull
	public static RegisterFragment getInstance() {
		return new RegisterFragment();
	}
	
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_register, container, false);
		//Bind data
		ButterKnife.bind(this, view);
		return view;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//Setup activity
		this.activity = getActivity();
		this.prefs = HealthePrefs.get(activity);
		this.loading = Utils.getMaterialDialog(getContext());
		
	}
	
	@OnClick(R.id.get_started)
	protected void doRegistration() {
		//Verify all fields
		String _email = email.getText().toString();
		String _password = password.getText().toString();
		String _weight = weight.getText().toString();
		String _height = height.getText().toString();
		String _username = username.getText().toString();
		String dob = pickDOB.getText().toString();
		
		if (Utils.hasValidFields(email, true) &&
				Utils.hasValidFields(password, false) &&
				Utils.hasValidFields(weight, false) &&
				Utils.hasValidFields(height, false) &&
				Utils.hasValidFields(username, false) &&
				!dob.isEmpty()) {
			prefs.getApi().createUser(_username, dob, _weight, _height, _email, _password)
					.enqueue(new Callback<User>() {
						@Override
						public void onResponse(Call<User> call, Response<User> response) {
							if (response != null && response.isSuccessful()) {
								User user = response.body();
								prefs.setAccessToken(String.valueOf(user.id));
								prefs.setLoggedInUser(user);
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
		}
		
	}
	
	@OnClick(R.id.pick_dob)
	protected void pickDOB() {
		DatePickerFragment fragment = new DatePickerFragment();
		if (getFragmentManager() != null) {
			fragment.show(getFragmentManager(), DatePickerFragment.TAG);
		}
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
		pickDOB.setText(String.format("%d-%d-%d", year, month, dayOfMonth));
	}
	
	//Shows the error to the user as a toast message
	private void showMessage(CharSequence message) {
		if (loading.isShowing()) loading.dismiss();
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}
}
