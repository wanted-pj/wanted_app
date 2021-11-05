package org.techtown.wanted_app_main.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ProfileTeamAdapter extends RecyclerView.Adapter<ProfileTeamAdapter.ViewHolder> {

    private ArrayList<ProfileTeam> profileTeamList = new ArrayList<ProfileTeam>();

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
        viewHolder.setTeam(profileTeam);
    }

    public void setProfileTeamList(ArrayList<ProfileTeam> arrayList){
        this.profileTeamList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return profileTeamList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_team;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_team = itemView.findViewById(R.id.team_name);
        }

        public void setTeam(ProfileTeam profileTeam) {
            tv_team.setText(profileTeam.getName());
        }
    }
}