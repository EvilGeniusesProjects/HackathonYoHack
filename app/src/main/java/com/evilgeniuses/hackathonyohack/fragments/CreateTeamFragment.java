package com.evilgeniuses.hackathonyohack.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.fragments.participant.TeamsFragment;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.evilgeniuses.hackathonyohack.models.Team;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class CreateTeamFragment extends Fragment implements View.OnClickListener {

    private SwitchFragment switchFragment;

    //UI
    ImageView imageViewProfileImage;
    TextView textViewSetProfileImage;
    EditText editTextUsername;
    EditText editTextName;
    EditText editTextPassword;
    Button buttonSignUp;

    //Firebase
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageReference;
    String authenticationID;

    //Add Image
    private static final int IMAGE_REQUEST = 1;
    private Uri filePath;
    String imageProfileRef = "STANDARD";
    private StorageTask uploadTask;
    Boolean imagePick = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_team, container, false);

        imageViewProfileImage = rootView.findViewById(R.id.imageViewProfileImage);
        textViewSetProfileImage = rootView.findViewById(R.id.textViewSetProfileImage);
        editTextUsername = rootView.findViewById(R.id.editTextUsername);
        editTextName = rootView.findViewById(R.id.editTextName);
        editTextPassword = rootView.findViewById(R.id.editTextPassword);
        buttonSignUp = rootView.findViewById(R.id.buttonSignUp);

        textViewSetProfileImage.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewSetProfileImage:
                SelectImage();
                break;
            case R.id.buttonSignUp:
                checkField();
                break;
        }
    }

    public void checkField() {
        if ((editTextUsername.getText().length() != 0)) {
            if ((editTextName.getText().length() != 0)) {
                if ((editTextPassword.getText().length() != 0)) {
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    onCreateTeam();
                } else {
                    Toast.makeText(getContext(), "Ошибка: заполните поле \"Пароль\"", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Ошибка: заполните поле \"Идеи\"", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Ошибка: заполните поле \"Название команды\"", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCreateTeam() {
                    if (imagePick) {
                        uploadImage();
                    } else {
                        Login();
                    }
    }

    private void writeNewTeam() {
        Team team = new Team();

        team.teamProfileImageURL = imageProfileRef;
        team.teamName = String.valueOf(editTextUsername.getText());
        team.teamIdea = String.valueOf(editTextName.getText());
        team.teamPassword = String.valueOf(editTextPassword.getText());


        myRef.child("Teams/" + team.teamName).setValue(team);
    }

    private void SelectImage() {
        imagePick = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
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
                        imageProfileRef = mUri;
                        Login();
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

    public void Login() {
        writeNewTeam();
        switchFragment.setFragment(TeamsFragment.newInstance(), "");
        Toast.makeText(getContext(), "Команда создана", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                Glide.with(getContext()).load(bitmap).override(512, 512).into(imageViewProfileImage);
                imageViewProfileImage.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchFragment) {
            switchFragment = (SwitchFragment) context;
        }
    }

    public static CreateTeamFragment newInstance() {
        return new CreateTeamFragment();
    }
}

