package io.healthe.ui;


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
import io.healthe.util.Utils;

/**
 * A simple registration {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
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
	@BindView(R.id.get_started)
	Button register;
	
	private FragmentActivity activity;
	private MaterialDialog loading;
	
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
		this.loading = Utils.getMaterialDialog(getContext());
		
	}
	
	@OnClick(R.id.get_started)
	void doRegistration() {
	
	}
}
