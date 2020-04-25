package com.evilgeniuses.hackathonyohack.fragments.participant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.activities.JarvisActivity;
import com.evilgeniuses.hackathonyohack.adapters.ExpandableFaqListAdapter;
import com.evilgeniuses.hackathonyohack.models.FaqQuestion;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FaqFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<FaqQuestion> faqQuestionList;
    private ChildEventListener childEventListener;
    private ExpandableFaqListAdapter expandableFaqListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_faq, container, false);
        recyclerView = inflate.findViewById(R.id.recyclerView);
        inflate.findViewById(R.id.btnJarvis).setOnClickListener(this);
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

    public static FaqFragment newInstance() {
        return new FaqFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnJarvis:
                startActivity(new Intent(getContext(), JarvisActivity.class));
                break;
            case R.id.buttonOrgans:

                break;
            case R.id.buttonMentors:

                break;
            case R.id.buttonVolunteers:

                break;


        }
    }

    @Override
    public void onPause() {
        super.onPause();
        childEventListener = null;
    }
}
