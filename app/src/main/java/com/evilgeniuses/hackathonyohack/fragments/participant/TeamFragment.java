package com.evilgeniuses.hackathonyohack.fragments.participant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;


public class TeamFragment extends Fragment implements View.OnClickListener {
    SwitchFragment switchFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teams, container,false);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof SwitchFragment){
            switchFragment = (SwitchFragment) context;
        }
    }

    public static TeamFragment newInstance(){
        return new TeamFragment();
    }
}
