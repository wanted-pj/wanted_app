package org.techtown.wanted_app_main.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public final int TYPE_SENDER = 0;
    public final int TYPE_RECEIVER = 1;
    private ArrayList<Chat> chats = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_SENDER) {
            View view = inflater.inflate(R.layout.item_chat_sender, parent, false);
            return new SenderViewHolder(view);
        } else if (viewType == TYPE_RECEIVER){
            View view = inflater.inflate(R.layout.item_chat_receiver, parent, false);
            return new ReceiverViewHolder(view);
        }
        else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SenderViewHolder){
            SenderViewHolder itemViewHolder = (SenderViewHolder) holder;
            Chat chat = chats.get(position);
            itemViewHolder.setChat(chat);
        } else {
            ReceiverViewHolder itemViewHolder = (ReceiverViewHolder) holder;
            Chat chat = chats.get(position);
            itemViewHolder.setChat(chat);
        }
    }

    public void setChats(ArrayList<Chat> arrayList){
        this.chats = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chats.get(position).getType();
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView tv_content;
        TextView tv_time;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.chat_item_content);
            tv_time = itemView.findViewById(R.id.chat_item_time);
        }

        public void setChat(Chat chat) {
            tv_content.setText(chat.getContent());
            tv_time.setText(chat.getTime());
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView tv_content;
        TextView tv_time;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.chat_item_content);
            tv_time = itemView.findViewById(R.id.chat_item_time);
        }

        public void setChat(Chat chat) {
            tv_content.setText(chat.getContent());
            tv_time.setText(chat.getTime());
        }
    }

}
