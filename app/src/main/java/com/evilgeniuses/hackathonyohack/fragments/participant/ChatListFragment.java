package com.evilgeniuses.hackathonyohack.fragments.participant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.activities.GeneralСhatActivity;
import com.evilgeniuses.hackathonyohack.adapters.ChatsAdapter;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.evilgeniuses.hackathonyohack.models.Chatlist;
import com.evilgeniuses.hackathonyohack.models.Team;
import com.evilgeniuses.hackathonyohack.models.Token;
import com.evilgeniuses.hackathonyohack.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;

    private ChatsAdapter userAdapter;
    private List<User> mUsers;
    private List<Team> mTeam;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private List<Chatlist> usersList;

    Context mContext;

    Button buttonLoginToGeneralChat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chats_list, container, false);
        mContext = getContext();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();


        buttonLoginToGeneralChat = rootView.findViewById(R.id.buttonLoginToGeneralChat);
        buttonLoginToGeneralChat.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }
                chatList();
                chatListTeams();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, GeneralСhatActivity.class);
        intent.putExtra("ChatName", "YoHack");
        mContext.startActivity(intent);
    }


    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);
    }

    private void chatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (Chatlist chatlist : usersList){
                        if (user.getUserID().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new ChatsAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void chatListTeams() {
        mTeam = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Teams");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTeam.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Team team = snapshot.getValue(Team.class);
                    User user = new User();
                    for (Chatlist chatlist : usersList){
                        if (team.getTeamName().equals(chatlist.getId())){
                            mTeam.add(team);
                            user.userID = team.teamName;
                            user.userProfileImageURL = team.teamProfileImageURL;
                            user.userStatus = "online";
                            user.userName = team.teamName;
                            user.userUsername = team.teamName;
                            user.userLastname = "";
                            user.generalСhatActivity = true;
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new ChatsAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }
}