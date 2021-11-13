package org.techtown.wanted_app_main.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.ProfileCareer;

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
        EditText et_career;
        ImageView edit_img;
        Boolean isEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            isEdit = false;
            tv_career = itemView.findViewById(R.id.career_content);
            et_career = itemView.findViewById(R.id.career_content_edit);
            edit_img = itemView.findViewById(R.id.career_edit);
            edit_img.setOnClickListener( v-> {
                if(isEdit == false) {
                    isEdit = true;
                    String temp = (String)tv_career.getText();
                    et_career.setText(temp);
                    edit_img.setImageResource(R.drawable.ic_delete);
                    tv_career.setVisibility(View.INVISIBLE);
                    et_career.setVisibility(View.VISIBLE);
                } else {
                    isEdit = false;
                    String temp = et_career.getText().toString();
                    tv_career.setText(temp);
                    edit_img.setImageResource(R.drawable.ic_edit);
                    tv_career.setVisibility(View.VISIBLE);
                    et_career.setVisibility(View.INVISIBLE);
                }
            });
        }

        public void setCareer(ProfileCareer profileCareer) {
            tv_career.setText(profileCareer.getContent());
            et_career.setVisibility(View.INVISIBLE);
        }
    }
}