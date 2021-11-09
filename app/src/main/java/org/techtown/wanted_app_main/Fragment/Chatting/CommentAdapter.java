package org.techtown.wanted_app_main.Fragment.Chatting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.Fragment.ChatList;
import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<ChatList> chatList = new ArrayList<ChatList>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_chat_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ChatList chatList = this.chatList.get(position);
        viewHolder.setChat(chatList);
    }

    public void setChatList(ArrayList<ChatList> arrayList){
        this.chatList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nickname;
        TextView tv_content;
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_nickname = itemView.findViewById(R.id.sender_txt_nickname);
            tv_content = itemView.findViewById(R.id.sender_txt_content);
            iv = itemView.findViewById(R.id.sender_img);
        }

        public void setChat(ChatList chatList) {
            tv_nickname.setText(chatList.getNickame());
            tv_content.setText(chatList.getContent());
            iv.setImageResource(chatList.getImgRes());
        }
    }
}