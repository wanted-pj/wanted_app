package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static NavController navController;
    private final int limit = 5; // item 디스플레이 갯수 제한
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    private ArrayList<Friend> friendList = new ArrayList<>();

    // 뷰타입에 따라 사용할 뷰홀더 리턴
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        navController = Navigation.findNavController(parent);

        // viweType은 getItemViewType의 리턴값
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_footer, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
            return new ItemViewHolder(view);
        }
        else
            return null;
    }

    // 뷰홀더 종류에 따라 수행할 동작 구현 (footer일 때는 동작 X, item일 때만 동작 O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        }
        else {
            // item을 하나하나 보여줌 (bind 시킴)
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Log.d("test_List" , String.valueOf(friendList.size()));
            Friend item = friendList.get(position);
            itemViewHolder.setFriend(item);
        }
    }

    public void setFriendList(ArrayList<Friend> arrayList){
        this.friendList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(friendList.size() < limit) {
            return friendList.size() + 1;
        } else {
            return limit + 1;
        }
    }

    // 포지션에 따라 item의 뷰타입 정의
    @Override
    public int getItemViewType(int position) {
        // item 갯수 제한
        if (friendList.size() <= limit) {
            if (position == friendList.size())
                return TYPE_FOOTER;
            else
                return TYPE_ITEM;
        }
        else {
            if (position == limit)
                return TYPE_FOOTER;
            else if (position < limit)
                return TYPE_ITEM;
            else
                return 0;
        }
    }

    // footer 뷰홀더 클래스 정의
    class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View footerView) {
            super(footerView);

            // footer 클릭이벤트
            footerView.setOnClickListener(view1 -> {
                Bundle bundle = new Bundle();
                bundle.putString("friendsCategory", "test");
                navController.navigate(R.id.action_mainFragment_to_showFriendsFragment, bundle);
            });
        }
    }

    // item 뷰홀더 클래스 정의
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_school;
        TextView tv_major;
        TextView tv_address;
        ImageView iv;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_school = itemView.findViewById(R.id.tv_school);
            tv_major = itemView.findViewById(R.id.tv_major);
            tv_address = itemView.findViewById(R.id.tv_address);
            iv = itemView.findViewById(R.id.iv);

            // item 클릭이벤트
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Go Profile
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