package com.evilgeniuses.hackathonyohack.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.activities.ChatActivity;
import com.evilgeniuses.hackathonyohack.fragments.participant.MyTeamFragment;
import com.evilgeniuses.hackathonyohack.interfaces.SwitchFragment;
import com.evilgeniuses.hackathonyohack.models.Team;
import com.evilgeniuses.hackathonyohack.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    private Context mContext;
    private List<Team> mTeams;
    private boolean ischat;
    SwitchFragment switchFragment;

    public TeamsAdapter(Context mContext, List<Team> teams, boolean ischat, SwitchFragment switchFragment) {
        this.mTeams = teams;
        this.mContext = mContext;
        this.ischat = ischat;
        this.switchFragment = switchFragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mentor, parent, false);
        return new TeamsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Team team = mTeams.get(position);

        if (team.getTeamProfileImageURL().equals("STANDARD")) {
            holder.imageViewProfileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(team.teamProfileImageURL).override(256, 256).into(holder.imageViewProfileImage);
        }


        if(team.teamStatus != null){
            holder.textViewLastMessage.setVisibility(View.VISIBLE);
            holder.textViewLastMessage.setText(team.teamStatus);
        }else{
            holder.textViewLastMessage.setVisibility(View.GONE);
        }






        holder.imageViewStatus.setVisibility(View.GONE);


        holder.textViewName.setText(team.getTeamName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, ChatActivity.class); /активити для входа
//                intent.putExtra("userID", team.getUserID());
//                mContext.startActivity(intent);



                setName(team.getTeamName());

//                FirebaseUser  firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                DatabaseReference databaseReferenceStatus;
//                databaseReferenceStatus = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
//                HashMap<String, Object> hashMap = new HashMap<>();
//                hashMap.put("userTeam", team.getTeamName());
//                databaseReferenceStatus.updateChildren(hashMap);
//                switchFragment.setFragment(MyTeamFragment.newInstance(), "");
            }
        });
    }

    public void setName(final String name) {
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.dialog_password, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(mContext);
        mDialogBuilder.setView(promptsView);



        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                FirebaseUser  firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference databaseReferenceStatus;
                                databaseReferenceStatus = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("userTeam", name);
                                databaseReferenceStatus.updateChildren(hashMap);
                                switchFragment.setFragment(MyTeamFragment.newInstance(), "");


                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();
    }




















    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewLastMessage;
        public TextView textViewTime;
        public ImageView imageViewProfileImage;
        public ImageView imageViewStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewProfileImage = itemView.findViewById(R.id.imageViewProfileImage);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLastMessage = itemView.findViewById(R.id.textViewLastMessage);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }
}
