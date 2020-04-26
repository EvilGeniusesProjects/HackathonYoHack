package com.evilgeniuses.hackathonyohack.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.evilgeniuses.hackathonyohack.activities.NavigationMentorActivity;
import com.evilgeniuses.hackathonyohack.activities.NavigationOrganizerActivity;
import com.evilgeniuses.hackathonyohack.activities.NavigationParticipantActivity;
import com.evilgeniuses.hackathonyohack.activities.NavigationVolunteerActivity;
import com.evilgeniuses.hackathonyohack.databases.TinyDB;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.evilgeniuses.hackathonyohack.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private SwitchFragment switchFragment;

    //UI
    EditText editTextEmail;
    EditText editTextPassword;
    TextView textViewForgotPassword;
    Button buttonLogIn;
    Button buttonSignUp;

    //Firebase
    private FirebaseAuth mAuth;
    String AuthenticationID;

    TinyDB tinydb;

    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        mContext = getContext();

        editTextEmail = rootView.findViewById(R.id.editTextEmail);
        editTextPassword = rootView.findViewById(R.id.editTextPassword);
        textViewForgotPassword = rootView.findViewById(R.id.textViewForgotPassword);
        buttonLogIn = rootView.findViewById(R.id.buttonLogin);
        buttonSignUp = rootView.findViewById(R.id.buttonSignUp);

        textViewForgotPassword.setOnClickListener(this);
        buttonLogIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                checkField();
                break;

            case R.id.textViewForgotPassword:
                switchFragment.setFragment(new ForgotPasswordFragment(), "Forgot Password");
                break;

            case R.id.buttonSignUp:
                switchFragment.setFragment(new RegistrationFragment(), "Registration");
                break;
        }
    }

    public void checkField() {
        if ((editTextEmail.getText().length() != 0)) {
            if ((editTextPassword.getText().length() != 0)) {
                signIn(String.valueOf(editTextEmail.getText()), String.valueOf(editTextPassword.getText()));
            } else {
                Toast.makeText(getContext(), "Ошибка: заполните поле \"Пароль\"", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Ошибка: заполните поле \"Email\"", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Login();
                } else {
                    Toast.makeText(getContext(), "Ошибка: некорректный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Login() {
        FirebaseUser user = mAuth.getCurrentUser();
        AuthenticationID = user.getUid();
        SharedPreferences sharedPreferencesID = getActivity().getSharedPreferences("UserID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesID.edit();
        editor.putString("UserID", AuthenticationID).apply();






        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + AuthenticationID);

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Загрузка");
        pd.show();


        tinydb = new TinyDB(mContext);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);

                Intent intent = null;

                String text = value.getUserCategory();
                tinydb.putString("UserCategories", text);
                Toast.makeText(getActivity(), "Text = " + text, Toast.LENGTH_SHORT).show();


                switch (text){
                    case "Организатор":
                        intent = new Intent(getActivity(), NavigationOrganizerActivity.class);
                        break;
                    case "Ментор":
                        intent = new Intent(getActivity(), NavigationMentorActivity.class);
                        break;
                    case "Волонтер":
                        intent = new Intent(getActivity(), NavigationVolunteerActivity.class);
                        break;
                    default:
                        intent = new Intent(getActivity(), NavigationParticipantActivity.class);
                        break;
                }
                startActivity(intent);
                getActivity().finish();
                pd.dismiss();






            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });











    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchFragment) {
            switchFragment = (SwitchFragment) context;
        }
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }
}