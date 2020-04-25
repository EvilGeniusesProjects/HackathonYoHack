package com.evilgeniuses.hackathonyohack.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.activities.ChatActivity;
import com.evilgeniuses.hackathonyohack.models.Team;
import com.evilgeniuses.hackathonyohack.models.User;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder>{

    private Context mContext;
    private List<Team> mTeams;
    private boolean ischat;

    public TeamsAdapter(Context mContext, List<Team> teams, boolean ischat){
        this.mTeams = teams;
        this.mContext = mContext;
        this.ischat = ischat;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mentor, parent, false);
        return new TeamsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Team team = mTeams.get(position);

        if (team.getTeamProfileImageURL().equals("STANDARD")){
            holder.imageViewProfileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(team.teamProfileImageURL).override(256, 256).into(holder.imageViewProfileImage);
        }

            holder.textViewLastMessage.setVisibility(View.GONE);

            holder.imageViewStatus.setVisibility(View.GONE);


        holder.textViewName.setText(team.getTeamName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, ChatActivity.class); /активити для входа
//                intent.putExtra("userID", team.getUserID());
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

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
