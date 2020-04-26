package com.evilgeniuses.hackathonyohack.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.models.FaqQuestion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFaqActivity extends AppCompatActivity {
    ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faq);

        final DatabaseReference faq = FirebaseDatabase.getInstance().getReference("FAQ");
        final EditText editQuestion = findViewById(R.id.editQuestion);
        final EditText editAnswer = findViewById(R.id.editAnswer);
        final EditText editTags = findViewById(R.id.editTags);

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIsEditIsNotEmpty(editQuestion) && checkIsEditIsNotEmpty(editAnswer) && checkIsEditIsNotEmpty(editTags)){
                    DatabaseReference push = faq.push();
                    push.child("question").setValue(editQuestion.getText().toString());
                    push.child("answer").setValue(editAnswer.getText().toString());
                    push.child("tags").setValue(editTags.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddFaqActivity.this, "Вопрос добавлен", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                } else {
                    Toast.makeText(AddFaqActivity.this, "Нужно заполнить все поля!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.imageViewBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean checkIsEditIsNotEmpty(EditText editText){
        return !editText.getText().toString().trim().isEmpty();
    }
}
