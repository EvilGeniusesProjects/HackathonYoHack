package com.evilgeniuses.hackathonyohack.fragments.participant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.activities.JarvisActivity;

public class FaqFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_faq, container, false);
        inflate.findViewById(R.id.btnJarvis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), JarvisActivity.class));
            }
        });
        return inflate;

    }

    public static FaqFragment newInstance() {
        return new FaqFragment();
    }

}
