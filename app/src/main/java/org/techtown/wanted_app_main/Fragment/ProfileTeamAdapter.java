package org.techtown.wanted_app_main.Fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ProfileTeamAdapter extends RecyclerView.Adapter<ProfileTeamAdapter.ViewHolder> {

    static ArrayList<ProfileTeam> profileTeamList = new ArrayList<ProfileTeam>();
    static ProfileTeamAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClicklistener(ProfileTeamAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public void onItemClick(View view, int position) {
        if(listener != null)
            listener.onItemClick(view, position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_profile_team, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ProfileTeam profileTeam = profileTeamList.get(position);
        viewHolder.setProfileTeam(profileTeam);
    }

    public void setProfileTeamList(ArrayList<ProfileTeam> arrayList){
        this.profileTeamList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return profileTeamList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.team_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null)
                        listener.onItemClick(v, position);
                }
            });
        }

        public void setProfileTeam(ProfileTeam profileTeam) {
            tv_name.setText(profileTeam.getName());
        }
    }
}