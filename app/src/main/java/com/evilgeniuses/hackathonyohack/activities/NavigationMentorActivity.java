package com.evilgeniuses.hackathonyohack.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.fragments.participant.ChatListFragment;
import com.evilgeniuses.hackathonyohack.fragments.participant.MentorFaqFragment;
import com.evilgeniuses.hackathonyohack.fragments.participant.MentorParticipantProfileFragment;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationMentorActivity extends AppCompatActivity implements SwitchFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_mentor);
        setFragment(MentorFaqFragment.newInstance(), "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_2:
                        setFragment(MentorFaqFragment.newInstance(), "");
                        break;
                    case R.id.tab_3:
                        setFragment(ChatListFragment.newInstance(), "");
                        break;
                    case R.id.tab_5:
                        setFragment(MentorParticipantProfileFragment.newInstance(), "");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void setFragment(final Fragment fragment, final String fragmentTitle) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.container, fragment)
                        .addToBackStack(fragmentTitle)
                        .commit();
            }
        });
    }
}