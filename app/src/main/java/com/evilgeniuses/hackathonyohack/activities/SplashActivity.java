package com.evilgeniuses.hackathonyohack.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = null;

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            intent = new Intent(this, NavigationParticipantActivity.class);
        } else {
            intent = new Intent(this, AuthenticationActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
