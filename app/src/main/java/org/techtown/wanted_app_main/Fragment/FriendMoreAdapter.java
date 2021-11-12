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

public class FriendMoreAdapter extends RecyclerView.Adapter<FriendMoreAdapter.ViewHolder> {

    private ArrayList<Friend> friendList = new ArrayList<Friend>();
    static FriendMoreAdapter.OnItemClickListener listener;
    static FriendMoreAdapter.OnfriendClickListener listener1;
static Boolean can=false;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClicklistener(FriendMoreAdapter.OnItemClickListener listener){
        this.listener = listener;

    }

    public void onItemClick(View view, int position) {
        if(listener != null)
            listener.onItemClick(view, position);
    }

    public interface OnfriendClickListener {
        void onfriendClick(View view, int position);
    }

    public void setOnfriendClicklistener(FriendMoreAdapter.OnfriendClickListener listener1){
        this.listener1 = listener1;

    }

    public void onfriendClick(View view, int position) {
        if(listener1 != null)
            listener1.onfriendClick(view, position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_friend_more, viewGroup, false);

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
        TextView tv_address;
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_school = itemView.findViewById(R.id.tv_school);
            tv_major = itemView.findViewById(R.id.tv_major);
            tv_address = itemView.findViewById(R.id.tv_address);
            iv = itemView.findViewById(R.id.iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener1 != null)
                        listener1.onfriendClick(v, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null)
                        listener.onItemClick(v, position);
                    return false;
                }
            });
        }

        public void setFriend(Friend friend) {
            tv_name.setText(friend.getName());
            tv_school.setText(friend.getSchool());
            tv_major.setText(friend.getMajor());
            tv_address.setText(friend.getAddress());
            iv.setImageResource(friend.getImgRes());
        }
    }
}