package com.evilgeniuses.hackathonyohack.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.fragments.participant.AboutHackathonFragment;
import com.evilgeniuses.hackathonyohack.fragments.participant.ChatListFragment;
import com.evilgeniuses.hackathonyohack.fragments.participant.FaqFragment;
import com.evilgeniuses.hackathonyohack.fragments.participant.ParticipantProfileFragment;
import com.evilgeniuses.hackathonyohack.fragments.participant.TeamsListFragment;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationMentorActivity extends AppCompatActivity implements SwitchFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_mentor);
        setFragment(AboutHackathonFragment.newInstance(), "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_1:
                        setFragment(AboutHackathonFragment.newInstance(), "");
                        break;
                    case R.id.tab_2:
                        setFragment(FaqFragment.newInstance(), "");
                        break;
                    case R.id.tab_3:
                        setFragment(ChatListFragment.newInstance(), "");
                        break;
                    case R.id.tab_4:
                        setFragment(TeamsListFragment.newInstance(), "");
                        break;
                    case R.id.tab_5:
                        setFragment(ParticipantProfileFragment.newInstance(), "");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void setFragment(final Fragment fragment, String fragmentTitle) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.container, fragment)
                        .commit();
            }
        });
    }
}