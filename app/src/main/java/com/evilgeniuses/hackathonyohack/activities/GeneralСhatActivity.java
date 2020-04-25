package com.evilgeniuses.hackathonyohack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.adapters.MessageAdapter;
import com.evilgeniuses.hackathonyohack.models.Message;
import com.evilgeniuses.hackathonyohack.models.Team;
import com.evilgeniuses.hackathonyohack.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneralСhatActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewBack;
    ImageView imageViewStatus;
    TextView textViewName;
    RecyclerView recyclerView;
    EditText editTextSendText;
    ImageView imageViewSend;

    Intent intent;
    String userID;


    FirebaseUser firebaseUser;

    boolean notify = false;

    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceStatus;

    List<Message> listMessage;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewStatus = findViewById(R.id.imageViewStatus);
        textViewName = findViewById(R.id.textViewName);
        recyclerView = findViewById(R.id.recyclerView);
        editTextSendText = findViewById(R.id.editTextSendText);
        imageViewSend = findViewById(R.id.imageViewSend);

        imageViewBack.setOnClickListener(this);
        imageViewSend.setOnClickListener(this);

        intent = getIntent();
        userID = intent.getStringExtra("ChatName");


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setStatus("online");

        databaseReference = FirebaseDatabase.getInstance().getReference("Teams").child(userID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Team team = dataSnapshot.getValue(Team.class);
                textViewName.setText(team.getTeamName());

                imageViewStatus.setVisibility(View.GONE);

                readMesagges(firebaseUser.getUid(), userID, team.getTeamProfileImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                finish();
                break;
            case R.id.imageViewSend:
                notify = true;
                String msg = editTextSendText.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(firebaseUser.getUid(), userID, msg);
                } else {
                    Toast.makeText(GeneralСhatActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                editTextSendText.setText("");
                break;
        }
    }

    private void sendMessage(String sender, final String receiver, String message) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);


        android.text.format.DateFormat df = new android.text.format.DateFormat();

        hashMap.put("time", df.format("hh:mm", new java.util.Date()));
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);


        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid()).child(userID);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    chatRef.child("id").setValue(userID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userID)
                .child(firebaseUser.getUid());
        chatRefReceiver.child("id").setValue(firebaseUser.getUid());

        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMesagges(final String myid, final String userid, final String imageurl) {
        listMessage = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMessage.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message.getReceiver().equals(myid) && message.getSender().equals(userid) ||
                            message.getReceiver().equals(userid) && message.getSender().equals(myid)) {
                        listMessage.add(message);
                    }
                    messageAdapter = new MessageAdapter(GeneralСhatActivity.this, listMessage, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setStatus(String status) {
        databaseReferenceStatus = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userStatus", status);
        databaseReferenceStatus.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setStatus("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        setStatus("offline");
    }
}