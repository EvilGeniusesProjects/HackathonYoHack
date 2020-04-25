package com.evilgeniuses.hackathonyohack.fragments.participant;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.adapters.TeamsAdapter;
import com.evilgeniuses.hackathonyohack.fragments.CreateTeamFragment;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.evilgeniuses.hackathonyohack.models.Team;
import com.evilgeniuses.hackathonyohack.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TeamFragment extends Fragment implements View.OnClickListener {

    SwitchFragment switchFragment;

    EditText editTextSearch;
    private RecyclerView recyclerView;

    private TeamsAdapter chatsAdapter;
    private List<Team> mTeams;

    DatabaseReference myRef;
    FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_teams, container, false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/" + currentFirebaseUser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                if(value.userTeam != null){
                    switchFragment.setFragment(MyTeamFragment.newInstance(), "");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });









        Button button = rootView.findViewById(R.id.buttonCreate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment.setFragment(CreateTeamFragment.newInstance(), "");
            }
        });


        editTextSearch = rootView.findViewById(R.id.editTextSearch);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mTeams = new ArrayList<>();

        readUsers();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }


    private void searchUsers(String username) {

        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("userUsernameSearch").startAt(username).endAt(username+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTeams.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Team team = snapshot.getValue(Team.class);

                    assert team != null;
                    assert fuser != null;
//                    if (!user.getUserID().equals(fuser.getUid())){
                        mTeams.add(team);
//                    }
                }
                chatsAdapter = new  TeamsAdapter(getContext(), mTeams, false, switchFragment);
                recyclerView.setAdapter(chatsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teams");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (editTextSearch.getText().toString().equals("")) {
                    mTeams.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Team user = snapshot.getValue(Team.class);

//                        if (!user.getUserID().equals(firebaseUser.getUid())) {
                            mTeams.add(user);
//                        }

                    }


                    chatsAdapter = new  TeamsAdapter(getContext(), mTeams, false, switchFragment);
                    recyclerView.setAdapter(chatsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof SwitchFragment){
            switchFragment = (SwitchFragment) context;
        }
    }

    public static TeamFragment newInstance() {
        return new TeamFragment();
    }
}