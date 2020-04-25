package com.evilgeniuses.hackathonyohack.activities;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.adapters.MessageAdapter;
import com.evilgeniuses.hackathonyohack.bot.Jarvis;
import com.evilgeniuses.hackathonyohack.dict.JarvisRequestDictionary;
import com.evilgeniuses.hackathonyohack.models.FaqQuestion;
import com.evilgeniuses.hackathonyohack.models.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JarvisActivity extends AppCompatActivity implements View.OnClickListener  {

    private static final String JARVIS_IMAGE_URL = "https://media-exp1.licdn.com/dms/image/C560BAQEUmi6a4YBYLw/company-logo_200_200/0?e=2159024400&v=beta&t=lBniASMYfg6B3hy5Ffg6XmUW4OSwn2SVPS1Hy6xRNEk";
    private static final String JARVIS = "jarvis";

    private ImageView imageViewBack;
    private RecyclerView recyclerView;
    private EditText editTextSendText;
    private ImageView imageViewSend;
    private String userId;

    private Jarvis jarvis;

    private List<Message> listMessage;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jarvis);

        imageViewBack = findViewById(R.id.imageViewBack);
        recyclerView = findViewById(R.id.recyclerView);
        editTextSendText = findViewById(R.id.editTextSendText);
        imageViewSend = findViewById(R.id.imageViewSend);

        imageViewBack.setOnClickListener(this);
        imageViewSend.setOnClickListener(this);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FaqQuestion faqQestion1 = new FaqQuestion(
                "Сроки хакатона",
                "дата,даты,период,время",
                "Хакатон продлится с 20.04.2020 по 22.04.2020",
                0);
        FaqQuestion faqQestion2 = new FaqQuestion(
                "Награды хакатона",
                "награда,кэш,приз,подарок,подарки",
                "Призовой фонд 1000000$",
                0
        );
        FaqQuestion faqQestion3 = new FaqQuestion(
                "Кому я могу обратиться за помощью?",
                "помощь,ментор,куратор,help,помагите",
                "Аллабердин Азат Уралович",
                0
        );

        List<FaqQuestion> faqQestions = Arrays.asList(faqQestion1, faqQestion2, faqQestion3);
        jarvis = Jarvis.getBotInstance(faqQestions);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        listMessage = new ArrayList<>();
        DateFormat dateFormat = new DateFormat();
        Message jarvisMessage = new Message(JARVIS, userId, jarvis.precessMessage(JarvisRequestDictionary.HELLO1.getText()), false, dateFormat.format("hh:mm", new Date()).toString());
        listMessage.add(jarvisMessage);
        messageAdapter = new MessageAdapter(JarvisActivity.this, listMessage, JARVIS_IMAGE_URL);
        recyclerView.setAdapter(messageAdapter);
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
        DateFormat dateFormat = new DateFormat();
        Message myMessage = new Message(userId, JARVIS, msg, false, dateFormat.format("hh:mm", new Date()).toString());
        Message jarvisMessage = new Message(JARVIS, userId, jarvis.precessMessage(msg), false, dateFormat.format("hh:mm", new Date()).toString());
        listMessage.add(myMessage);
        listMessage.add(jarvisMessage);
        messageAdapter = new MessageAdapter(JarvisActivity.this, listMessage, JARVIS_IMAGE_URL);
        recyclerView.setAdapter(messageAdapter);
    }
}
