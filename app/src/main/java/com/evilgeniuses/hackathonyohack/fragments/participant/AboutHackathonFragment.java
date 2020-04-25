package com.evilgeniuses.hackathonyohack.fragments.participant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.fragments.CleanFragment;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.evilgeniuses.hackathonyohack.models.Hackathon;
import com.evilgeniuses.hackathonyohack.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class AboutHackathonFragment extends Fragment implements View.OnClickListener {
    SwitchFragment switchFragment;

    final int[] belltime = {30600, 36300, 36900, 42600, 44400, 50100, 50700, 56400, 57600, 63300, 63900, 69600, 70200, 82800};

    ImageView imageViewHackathonLogo;
    TextView textViewHackathonName;
    TextView textViewHackathonDate;
    TextView textViewIHackathonAbout;


    TextView textViewHours;
    TextView textViewMinutes;
    TextView textViewSeconds;

    Context mContext;
    private Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_hackathon, container,false);


        mContext = getContext();

        imageViewHackathonLogo = rootView.findViewById(R.id.imageViewHackathonLogo);
        textViewHackathonName = rootView.findViewById(R.id.textViewHackathonName);
        textViewHackathonDate = rootView.findViewById(R.id.textViewHackathonDate);
        textViewIHackathonAbout = rootView.findViewById(R.id.textViewIHackathonAbout);


        textViewHours = rootView.findViewById(R.id.textViewHours);
        textViewMinutes = rootView.findViewById(R.id.textViewMinutes);
        textViewSeconds = rootView.findViewById(R.id.textViewSeconds);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;

        myRef = database.getReference("Hackathon/YoHack");




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Hackathon value = dataSnapshot.getValue(Hackathon.class);


                textViewHackathonName.setText(value.hackathonName);
                textViewHackathonDate.setText(value.hackathonDate);
                textViewIHackathonAbout.setText(value.hackathonAbout);
                Glide.with(mContext).load(value.hackathonLogo).override(512, 512).into(imageViewHackathonLogo);

//                editTextUsername.setText(value.userUsername);
//                editTextEmail.setText(value.userEmail);
//                editTextName.setText(value.userName);
//                editTextLastname.setText(value.userLastname);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });


        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handlerUpdate();
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        };
        mHandler.sendEmptyMessage(0);


        return rootView;
    }


    public void handlerUpdate(){

        DateTime datetime = DateTime.now();

        int currentTime;
        int timeToLecture;

        int timeToLectureHour;
        int timeToLectureMin;
        int timeToLectureSec;

        int hour = datetime.getHourOfDay();
        int min = datetime.getMinuteOfHour();
        int sec = datetime.getSecondOfMinute();

        int belltimeToLecture = 0;

        int Lecture = 0;

        currentTime = hour * 60 * 60 + min * 60 + sec;

        for (int i = 0; i < 14; i++) {

            if (i % 2 == 0) {
                Lecture++;
                //textViewLecture.setText("До начала " + Lecture + " пары:");
            }else{
                //textViewLecture.setText("До конца " + Lecture + " пары:");
            }


            if (belltime[i] > currentTime) {
                belltimeToLecture = belltime[i];
                break;
            }

            if(i == 13){
               // textViewLecture.setText("Пары закончены");
                textViewHackathonDate.setVisibility(View.GONE);
            }else{
                textViewHackathonDate.setVisibility(View.VISIBLE);
            }
        }

        timeToLecture = belltimeToLecture - currentTime;

        timeToLectureHour = timeToLecture / 3600;
        timeToLectureMin = timeToLecture / 60 - timeToLectureHour * 60;
        timeToLectureSec = timeToLecture - timeToLectureHour * 3600 - timeToLectureMin * 60;

        String StringTimeToLectureHour;
        String StringTimeToLectureMin;
        String StringTimeToLectureSec;

        if (timeToLectureHour < 10) {
            StringTimeToLectureHour = "0" + timeToLectureHour;
        } else {
            StringTimeToLectureHour = String.valueOf(timeToLectureHour);
        }
        if (timeToLectureMin < 10) {
            StringTimeToLectureMin = "0" + timeToLectureMin;
        } else {
            StringTimeToLectureMin = String.valueOf(timeToLectureMin);
        }
        if (timeToLectureSec < 10) {
            StringTimeToLectureSec = "0" + timeToLectureSec;
        } else {
            StringTimeToLectureSec = String.valueOf(timeToLectureSec);
        }

        if (StringTimeToLectureHour.equals("00 ") && StringTimeToLectureMin.equals("00 ")) {
            textViewHours.setText("00");
            textViewMinutes.setText("00");
            textViewSeconds.setText(StringTimeToLectureSec);
        } else {
            if (StringTimeToLectureHour.equals("00 ")) {
                textViewHours.setText("00");
                textViewMinutes.setText(StringTimeToLectureMin);
                textViewSeconds.setText(StringTimeToLectureSec);
            } else {
                textViewHours.setText(StringTimeToLectureHour);
                textViewMinutes.setText(StringTimeToLectureMin);
                textViewSeconds.setText(StringTimeToLectureSec);
            }
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof SwitchFragment){
            switchFragment = (SwitchFragment) context;
        }
    }

    public static AboutHackathonFragment newInstance(){
        return new AboutHackathonFragment();
    }
}
