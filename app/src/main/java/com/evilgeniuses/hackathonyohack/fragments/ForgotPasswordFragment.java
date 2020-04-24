package com.evilgeniuses.hackathonyohack.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {

    private SwitchFragment switchFragment;

    TextInputLayout textInputLayoutEmailAddress;
    TextView textViewCheckEmail;
    EditText editTextEmailAddress;
    Button buttonResetPassword;
    Button buttonOK;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        textViewCheckEmail = rootView.findViewById(R.id.textViewCheckEmail);
        editTextEmailAddress = rootView.findViewById(R.id.editTextEmailAddress);
        textInputLayoutEmailAddress = rootView.findViewById(R.id.textInputLayoutEmailAddress);
        buttonResetPassword = rootView.findViewById(R.id.buttonResetPassword);
        buttonOK = rootView.findViewById(R.id.buttonOK);

        buttonOK.setOnClickListener(this);
        buttonResetPassword.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonResetPassword:
                if (editTextEmailAddress.getText().length() != 0) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(editTextEmailAddress.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                textInputLayoutEmailAddress.setVisibility(View.GONE);
                                buttonResetPassword.setVisibility(View.GONE);
                                textViewCheckEmail.setVisibility(View.VISIBLE);
                                buttonOK.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(getContext(), "Email not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "Ошибка: заполните поле \"Email Address\"", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonOK:
                switchFragment.setFragment(new LoginFragment(), "Login");
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchFragment) {
            switchFragment = (SwitchFragment) context;
        }
    }

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }
}