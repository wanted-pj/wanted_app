package org.techtown.wanted_app_main.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ProfileCareerAdapter extends RecyclerView.Adapter<ProfileCareerAdapter.ViewHolder> {

    private ArrayList<ProfileCareer> profileCareerList = new ArrayList<ProfileCareer>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_profile_career, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ProfileCareer profileCareer = profileCareerList.get(position);
        viewHolder.setCareer(profileCareer);
    }

    public void setProfileCareerList(ArrayList<ProfileCareer> arrayList){
        this.profileCareerList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return profileCareerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_career;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_career = itemView.findViewById(R.id.career_content);
        }

        public void setCareer(ProfileCareer profileCareer) {
            tv_career.setText(profileCareer.getContent());
        }
    }
}