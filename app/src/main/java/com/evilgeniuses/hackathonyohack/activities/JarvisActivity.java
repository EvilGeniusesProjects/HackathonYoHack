package com.evilgeniuses.hackathonyohack.activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.adapters.MessageAdapter;
import com.evilgeniuses.hackathonyohack.bot.Jarvis;
import com.evilgeniuses.hackathonyohack.dict.JarvisRequestDictionary;
import com.evilgeniuses.hackathonyohack.models.FaqQuestion;
import com.evilgeniuses.hackathonyohack.models.Message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class JarvisActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String JARVIS_IMAGE = "jarvis.jpeg";
    private static final String JARVIS = "jarvis";

    private RecyclerView recyclerView;
    private EditText editTextSendText;
    private String userId;
    private String jarvisImageUrl;
    private List<FaqQuestion> faqQestions;

    private Jarvis jarvis;

    private List<Message> listMessage;
    private MessageAdapter messageAdapter;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jarvis);

        ImageView imageViewBack = findViewById(R.id.imageViewBack);
        recyclerView = findViewById(R.id.recyclerView);
        editTextSendText = findViewById(R.id.editTextSendText);
        ImageView imageViewSend = findViewById(R.id.imageViewSend);

        imageViewBack.setOnClickListener(this);
        imageViewSend.setOnClickListener(this);

        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        faqQestions = new ArrayList<>();
        jarvis = Jarvis.getBotInstance(faqQestions);
        FirebaseStorage.getInstance().getReference(JARVIS_IMAGE).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                jarvisImageUrl = uri.toString();
                FirebaseDatabase.getInstance().getReference("FAQ").addChildEventListener(childEventListener);
                listMessage = new ArrayList<>();
                Message jarvisMessage = new Message(JARVIS, userId, jarvis.precessMessage(JarvisRequestDictionary.HELLO1.getText()), false, DateFormat.format("hh:mm", new Date()).toString());
                listMessage.add(jarvisMessage);
                messageAdapter = new MessageAdapter(JarvisActivity.this, listMessage, jarvisImageUrl);
                recyclerView.setAdapter(messageAdapter);
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                finish();
                break;
            case R.id.imageViewSend:
                String msg = editTextSendText.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(msg);
                } else {
                    Toast.makeText(JarvisActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                editTextSendText.setText("");
                break;
        }
    }

    private void sendMessage(String msg) {
        Message myMessage = new Message(userId, JARVIS, msg, false, DateFormat.format("hh:mm", new Date()).toString());
        Message jarvisMessage = new Message(JARVIS, userId, jarvis.precessMessage(msg), false, DateFormat.format("hh:mm", new Date()).toString());
        listMessage.add(myMessage);
        listMessage.add(jarvisMessage);
        messageAdapter = new MessageAdapter(JarvisActivity.this, listMessage, jarvisImageUrl);
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FaqQuestion value = dataSnapshot.getValue(FaqQuestion.class);
                faqQestions.add(value);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        childEventListener = null;
    }
}
