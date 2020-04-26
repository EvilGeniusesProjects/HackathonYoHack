package com.evilgeniuses.hackathonyohack.fragments.participant;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.activities.GeneralСhatActivity;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.evilgeniuses.hackathonyohack.models.Team;
import com.evilgeniuses.hackathonyohack.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class MyTeamFragment extends Fragment implements View.OnClickListener {

    SwitchFragment switchFragment;

    ImageView imageViewProfileImage;

    TextView textViewSetProfileImage;

    EditText editTextTeamName;
    EditText editTextTeamStatus;
    EditText editTextIdea;
    EditText editTextTeamPassword;

    Button buttonLogout;
    Button buttonTeamList;
    Button buttonTeamChat;
    Button buttonCheckpoint;
    Button buttonSendCheckpoint;


    EditText editTextTasksYouWork;
    EditText editTextCurrentProgress;
    EditText editTextWhatAreYouGoingToDo;
    EditText editTextWhatQuestionsDoYouHave;
    EditText editTextWhatKindOfMentorsDoYouNeed;






    LinearLayout linearlayoutCheckpoint;

    DatabaseReference myRef;
    DatabaseReference teamRef;

    private static final int IMAGE_REQUEST = 1;
    private Uri filePath;
    private StorageTask uploadTask;
    StorageReference storageReference;
    FirebaseStorage storage;
    FirebaseUser user;
    FirebaseDatabase database;

    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_team, container,false);

        mContext = getContext();
        imageViewProfileImage = rootView.findViewById(R.id.imageViewProfileImage);

        textViewSetProfileImage = rootView.findViewById(R.id.textViewSetProfileImage);

        editTextTeamName = rootView.findViewById(R.id.editTextTeamName);
        editTextTeamStatus = rootView.findViewById(R.id.editTextTeamStatus);
        editTextIdea = rootView.findViewById(R.id.editTextTeamIdea);
        editTextTeamPassword = rootView.findViewById(R.id.editTextTeamPassword);

        buttonLogout = rootView.findViewById(R.id.buttonLogout);
        buttonTeamList = rootView.findViewById(R.id.buttonTeamList);
        buttonTeamChat = rootView.findViewById(R.id.buttonTeamChat);
        buttonCheckpoint = rootView.findViewById(R.id.buttonCheckpoint);
        buttonSendCheckpoint = rootView.findViewById(R.id.buttonSendCheckpoint);



        editTextTasksYouWork = rootView.findViewById(R.id.editTextTasksYouWork);
        editTextCurrentProgress = rootView.findViewById(R.id.editTextCurrentProgress);
        editTextWhatAreYouGoingToDo = rootView.findViewById(R.id.editTextWhatAreYouGoingToDo);
        editTextWhatQuestionsDoYouHave = rootView.findViewById(R.id.editTextWhatQuestionsDoYouHave);
        editTextWhatKindOfMentorsDoYouNeed = rootView.findViewById(R.id.editTextWhatKindOfMentorsDoYouNeed);



        editTextTeamStatus.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                setTeamStatus(editTextTeamStatus.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editTextIdea.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                setTeamIdea(editTextIdea.getText().toString());


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        linearlayoutCheckpoint = rootView.findViewById(R.id.linearlayoutCheckpoint);

        textViewSetProfileImage.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonTeamList.setOnClickListener(this);
        buttonTeamChat.setOnClickListener(this);
        buttonCheckpoint.setOnClickListener(this);
        buttonSendCheckpoint.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();

        SharedPreferences SharedPreferencesID;
        SharedPreferencesID = getActivity().getSharedPreferences("UserID", Context.MODE_PRIVATE);
        String UserID = SharedPreferencesID.getString("UserID", String.valueOf(Context.MODE_PRIVATE));


        myRef = database.getReference("Users/" + UserID);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                getTeamData(value.userTeam);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });


        return rootView;

    }

    private void leaveTheTeam() {
        FirebaseUser  firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReferenceStatus = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userTeam", null);
        databaseReferenceStatus.updateChildren(hashMap);
        switchFragment.setFragment(TeamsListFragment.newInstance(), "");
    }


    private void getTeamData(String team){
        if (team != null) {
            teamRef = database.getReference("Teams/" + team);

            teamRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Team value = dataSnapshot.getValue(Team.class);
                    Glide.with(mContext).load(value.teamProfileImageURL).override(512, 512).into(imageViewProfileImage);
                    editTextTeamName.setText(value.teamName);
                    editTextTeamStatus.setText(value.teamStatus);
                    editTextIdea.setText(value.teamIdea);
                    editTextTeamPassword.setText(value.teamPassword);


                    editTextTasksYouWork.setText(value.hackathonTasksYouWork);
                    editTextCurrentProgress.setText(value.hackathonCurrentProgress);
                    editTextWhatAreYouGoingToDo.setText(value.hackathonWhatAreYouGoingToDo);
                    editTextWhatQuestionsDoYouHave.setText(value.hackathonWhatQuestionsDoYouHave);
                    editTextWhatKindOfMentorsDoYouNeed.setText(value.hackathonWhatKindOfMentorsDoYouNeed);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    //Log.w(TAG, "Не удалось прочитать значение", error.toException());
                }
            });
        }else {
            switchFragment.setFragment(TeamsListFragment.newInstance(), "");
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonTeamChat:
                Intent intent = new Intent(mContext, GeneralСhatActivity.class);
                intent.putExtra("ChatName", editTextTeamName.getText().toString());
                mContext.startActivity(intent);

                break;
            case R.id.buttonLogout:
                leaveTheTeam();
                break;

            case R.id.buttonTeamList:
                switchFragment.setFragment(MyTeamListFragment.newInstance(), "");
                break;

            case R.id.textViewSetProfileImage:
                SelectImage();
                break;

            case R.id.buttonCheckpoint:
                setlinearlayoutCheckpointVisibility();
                break;

            case R.id.buttonSendCheckpoint:
                sendCheckpoint();
                break;

        }
    }

    private void setTeamStatus(String status){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("teamStatus", status);
        teamRef.updateChildren(hashMap);
    }

    private void setTeamIdea(String status){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("teamIdea", status);
        teamRef.updateChildren(hashMap);
    }

    private void sendCheckpoint() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("hackathonTasksYouWork", editTextTasksYouWork.getText().toString());
        hashMap.put("hackathonCurrentProgress", editTextCurrentProgress.getText().toString());
        hashMap.put("hackathonWhatAreYouGoingToDo", editTextWhatAreYouGoingToDo.getText().toString());
        hashMap.put("hackathonWhatQuestionsDoYouHave", editTextWhatQuestionsDoYouHave.getText().toString());
        hashMap.put("hackathonWhatKindOfMentorsDoYouNeed", editTextWhatKindOfMentorsDoYouNeed.getText().toString());

        teamRef.updateChildren(hashMap);

        Toast.makeText(getContext(), "Данные чекпоинта успешно отправлены", Toast.LENGTH_SHORT).show();
        setlinearlayoutCheckpointVisibility();
    }


    private void setlinearlayoutCheckpointVisibility(){
        if(linearlayoutCheckpoint.getVisibility() == View.VISIBLE){
            linearlayoutCheckpoint.setVisibility(View.GONE);
            buttonLogout.setVisibility(View.VISIBLE);
        }else{
            linearlayoutCheckpoint.setVisibility(View.VISIBLE);
            buttonLogout.setVisibility(View.GONE);
        }
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageViewProfileImage.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Загрузка");
        pd.show();

        if (filePath != null) {
            final StorageReference fileReference = storageReference.child(UUID.randomUUID().toString() + "." + getFileExtension(filePath));

            uploadTask = fileReference.putFile(filePath);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("teamProfileImageURL", "" + mUri);
                        teamRef.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof SwitchFragment){
            switchFragment = (SwitchFragment) context;
        }
    }

    public static MyTeamFragment newInstance(){
        return new MyTeamFragment();
    }
}