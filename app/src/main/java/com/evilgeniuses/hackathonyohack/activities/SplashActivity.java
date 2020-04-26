package com.evilgeniuses.hackathonyohack.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.evilgeniuses.hackathonyohack.databases.TinyDB;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = null;



        TinyDB tinyDB = new TinyDB(this);



        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String savedText = tinyDB.getString("UserCategories");
            switch (savedText){
                case "Организатор":
                    intent = new Intent(this, NavigationOrganizerActivity.class);
                    break;
                case "Ментор":
                    intent = new Intent(this, NavigationMentorActivity.class);
                    break;
                case "Волонтер":
                    intent = new Intent(this, NavigationVolunteerActivity.class);
                    break;
                default:
                    intent = new Intent(this, NavigationParticipantActivity.class);
                    break;
            }

        } else {
            intent = new Intent(this, AuthenticationActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
