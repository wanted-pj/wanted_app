package org.techtown.wanted_app_main.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class Detail_FriendAdapter extends RecyclerView.Adapter<Detail_FriendAdapter.ViewHolder> {

    private ArrayList<Detail_Friend> friendList = new ArrayList<Detail_Friend>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_detail_friend, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Detail_Friend friend = friendList.get(position);
        viewHolder.setFriend(friend);
    }

    public void setFriendList(ArrayList<Detail_Friend> arrayList){
        this.friendList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_school;
        TextView tv_major;
        TextView tv_gender;
        TextView tv_grade;
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_school = itemView.findViewById(R.id.tv_school);
            tv_major = itemView.findViewById(R.id.tv_major);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            tv_grade = itemView.findViewById(R.id.tv_grade);
            iv = itemView.findViewById(R.id.iv);
        }

        public void setFriend(Detail_Friend friend) {
            tv_name.setText(friend.getName());
            tv_school.setText(friend.getSchool());
            tv_major.setText(String.valueOf(friend.getMajor()));
            tv_grade.setText(String.valueOf(friend.getGender()));
            tv_gender.setText(String.valueOf(friend.getGrade()));
            iv.setImageResource(friend.getImgRes());
        }
    }
}