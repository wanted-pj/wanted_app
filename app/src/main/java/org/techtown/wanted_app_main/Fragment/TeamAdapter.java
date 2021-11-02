package org.techtown.wanted_app_main.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private ArrayList<Team> teamList = new ArrayList<Team>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_team, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Team team = teamList.get(position);
        viewHolder.setTeam(team);
    }

    public void setTeamList(ArrayList<Team> arrayList){
        this.teamList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_team;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_team = itemView.findViewById(R.id.team_name);
        }

        public void setTeam(Team team) {
            tv_team.setText(team.getName());
        }
    }
}