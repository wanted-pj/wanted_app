package org.techtown.wanted_app_main.Fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private ArrayList<Friend> friendList = new ArrayList<Friend>();
    // 추가
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_friend, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Friend friend = friendList.get(position);
        viewHolder.setFriend(friend);
    }

    public void setFriendList(ArrayList<Friend> arrayList){
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
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_school = itemView.findViewById(R.id.tv_school);
            tv_major = itemView.findViewById(R.id.tv_major);
            iv = itemView.findViewById(R.id.iv);
        }

        public void setFriend(Friend friend) {
            tv_name.setText(friend.getName());
            tv_school.setText(friend.getSchool());
            tv_major.setText(String.valueOf(friend.getMajor()));
            iv.setImageResource(friend.getImgRes());
        }
    }
}