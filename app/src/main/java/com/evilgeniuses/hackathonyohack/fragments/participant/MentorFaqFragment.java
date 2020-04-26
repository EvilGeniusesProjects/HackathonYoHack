package com.evilgeniuses.hackathonyohack.fragments.participant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.activities.AddFaqActivity;
import com.evilgeniuses.hackathonyohack.activities.JarvisActivity;
import com.evilgeniuses.hackathonyohack.adapters.ExpandableFaqListAdapter;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.evilgeniuses.hackathonyohack.models.FaqQuestion;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MentorFaqFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<FaqQuestion> faqQuestionList;
    private ChildEventListener childEventListener;
    private ExpandableFaqListAdapter expandableFaqListAdapter;

    SwitchFragment switchFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.mentor_fragment_faq, container, false);
        recyclerView = inflate.findViewById(R.id.recyclerView);
        inflate.findViewById(R.id.btnAddQuestion).setOnClickListener(this);

        Button buttonOrgans = inflate.findViewById(R.id.buttonOrgans);
        Button buttonMentors = inflate.findViewById(R.id.buttonMentors);
        Button buttonVolunteers = inflate.findViewById(R.id.buttonVolunteers);
        Button buttonTeams = inflate.findViewById(R.id.buttonTeams);
        Button buttonParticipant = inflate.findViewById(R.id.buttonParticipant);

        buttonOrgans.setOnClickListener(this);
        buttonMentors.setOnClickListener(this);
        buttonVolunteers.setOnClickListener(this);
        buttonParticipant.setOnClickListener(this);
        buttonTeams.setOnClickListener(this);

        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        faqQuestionList = new ArrayList<>();
        expandableFaqListAdapter = new ExpandableFaqListAdapter(getContext(), faqQuestionList);
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                faqQuestionList.add(dataSnapshot.getValue(FaqQuestion.class));
                expandableFaqListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        FirebaseDatabase.getInstance().getReference("FAQ").addChildEventListener(childEventListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(expandableFaqListAdapter);
    }

    public static MentorFaqFragment newInstance() {
        return new MentorFaqFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddQuestion:
                startActivity(new Intent(getContext(), AddFaqActivity.class));
                break;
            case R.id.buttonOrgans:
                switchFragment.setFragment(OrganizerListFragment.newInstance(), "");
                break;
            case R.id.buttonMentors:
                switchFragment.setFragment(MentorListFragment.newInstance(), "");
                break;
            case R.id.buttonVolunteers:
                switchFragment.setFragment(VolunteersListFragment.newInstance(), "");
                break;
            case R.id.buttonTeams:
                switchFragment.setFragment(TeamsListFragment.newInstance(), "");
                break;
            case R.id.buttonParticipant:
                switchFragment.setFragment(VolunteersListFragment.newInstance(), "");
                break;


        }
    }

    @Override
    public void onPause() {
        super.onPause();
        childEventListener = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchFragment) {
            switchFragment = (SwitchFragment) context;
        }
    }

}
