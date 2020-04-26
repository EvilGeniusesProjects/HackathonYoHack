package com.evilgeniuses.hackathonyohack.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.fragments.participant.ChatListFragment;
import com.evilgeniuses.hackathonyohack.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String userID;
    DatabaseReference myRef;
    StorageReference storageReference;
    FirebaseStorage storage;

    ImageView imageViewProfileImage;
    ImageView imageViewBack;

    TextView textViewName;


    EditText editTextEmail;
    EditText editTextAbilities;

    Button buttonSend;

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        intent = getIntent();
        userID = intent.getStringExtra("userID");

        mContext = getApplicationContext();

        imageViewBack = findViewById(R.id.imageViewBack);
        textViewName = findViewById(R.id.textViewName);

        imageViewProfileImage = findViewById(R.id.imageViewProfileImage);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAbilities = findViewById(R.id.editTextAbilities);

        buttonSend = findViewById(R.id.buttonSend);

        imageViewBack.setOnClickListener(this);
        buttonSend.setOnClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/" + userID);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);

                if(value.userProfileImageURL.equals("STANDARD")){
                    imageViewProfileImage.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(mContext).load(value.userProfileImageURL).override(512, 512).into(imageViewProfileImage);
                }
                textViewName.setText(value.getUserName() + " " + value.getUserLastname());

                editTextEmail.setText(value.userEmail);
                if(!value.getUserAbilities().equals(null)) {
                    editTextAbilities.setText((value.getUserAbilities()));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                finish();
                break;
        }

        switch (v.getId()) {
            case R.id. buttonSend:

                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("userID", userID);
                this.startActivity(intent);

                break;
        }


    }
}
