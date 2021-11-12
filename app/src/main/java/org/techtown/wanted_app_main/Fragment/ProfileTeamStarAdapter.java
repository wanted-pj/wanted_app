package org.techtown.wanted_app_main.Fragment;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ProfileTeamStarAdapter extends RecyclerView.Adapter<ProfileTeamStarAdapter.ViewHolder> {

    static ArrayList<ProfileTeamStar> profileTeamStars = new ArrayList<ProfileTeamStar>();

    @NonNull
    @Override
    public ProfileTeamStarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_profile_team_star, viewGroup, false);

        return new ProfileTeamStarAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileTeamStarAdapter.ViewHolder viewHolder, int position) {
        ProfileTeamStar profileTeamStar = profileTeamStars.get(position);
        viewHolder.setProfileTeamStar(profileTeamStar);
    }

    public void setProfileTeamStars(ArrayList<ProfileTeamStar> arrayList){
        this.profileTeamStars = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return profileTeamStars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.star_name);
            iv = itemView.findViewById(R.id.star_img);
        }

        public void setProfileTeamStar(ProfileTeamStar profileTeamStar) {
            tv_name.setText(profileTeamStar.getName());
            iv.setImageResource(profileTeamStar.getImgRes());
        }
    }
}