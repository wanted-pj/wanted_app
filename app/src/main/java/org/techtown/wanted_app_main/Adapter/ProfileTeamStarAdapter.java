package org.techtown.wanted_app_main.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.database.ProfileTeamStar;
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
        RadioGroup attend_rg;
        RadioGroup teamwork_rg;
        RadioGroup contribution_rg;
        int value_earnest;
        int value_teamwork;
        int value_contribution;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.star_name);
            iv = itemView.findViewById(R.id.star_img);
            attend_rg = itemView.findViewById(R.id.attend_rg);

            attend_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.attend_btn1:
                            value_earnest=1;
                            break;
                        case R.id.attend_btn2:
                            value_earnest=2;
                            break;
                        case R.id.attend_btn3:
                            value_earnest=3;
                            break;
                        case R.id.attend_btn4:
                            value_earnest=4;
                            break;
                        case R.id.attend_btn5:
                            value_earnest=5;
                            break;
                    }
                }
            });

            teamwork_rg = itemView.findViewById(R.id.teamwork_rg);

            teamwork_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.teamwork_btn1:
                            value_teamwork=1;
                            break;
                        case R.id.teamwork_btn2:
                            value_teamwork=2;
                            break;
                        case R.id.teamwork_btn3:
                            value_teamwork=3;
                            break;
                        case R.id.teamwork_btn4:
                            value_teamwork=4;
                            break;
                        case R.id.teamwork_btn5:
                            value_teamwork=5;
                            break;
                    }
                }
            });

            contribution_rg = itemView.findViewById(R.id.contriute_rg);

            contribution_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.contriute_btn1:
                            value_contribution=1;
                            break;
                        case R.id.contriute_btn2:
                            value_contribution=2;
                            break;
                        case R.id.contriute_btn3:
                            value_contribution=3;
                            break;
                        case R.id.contriute_btn4:
                            value_contribution=4;
                            break;
                        case R.id.contriute_btn5:
                            value_contribution=5;
                            break;
                    }
                }
            });
            //int position = getAdapterPosition();
            //ProfileTeamStarFragment.get_adapter_evaluation(position,value_earnest,value_teamwork,value_contribution);
        }

        public void setProfileTeamStar(ProfileTeamStar profileTeamStar) {
            tv_name.setText(profileTeamStar.getName());
            iv.setImageResource(profileTeamStar.getImgRes());
        }
    }
}