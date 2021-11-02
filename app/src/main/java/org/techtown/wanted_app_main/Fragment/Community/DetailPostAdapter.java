package org.techtown.wanted_app_main.Fragment.Community;
//
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class DetailPostAdapter extends RecyclerView.Adapter<DetailPostAdapter.ViewHolder> {
    static ArrayList<DetailPost_item> items = new ArrayList<DetailPost_item>();

    /*static OnPostItemClickListener listener;

    public interface OnPostItemClickListener {
        public void onItemClick(ViewHolder holder, View view, ArrayList<Post> items, int position); }

    public void setOnItemClicklistener(OnPostItemClickListener listener){ this.listener = listener; }


    public void onItemClick(ViewHolder holder, View view, ArrayList<Post> items, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,items,position); } }*/



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.request_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        DetailPost_item item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItem() {
        items.clear();
    }

    public void addItem(DetailPost_item item) {
        items.add(item);
    }

    public void setItems(ArrayList<DetailPost_item> items) {
        this.items = items;
    }

    public DetailPost_item getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, DetailPost_item item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.requestid);
//            textView3 = itemView.findViewById(R.id.requestimage);
/*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, v, items, position);
                    } } });*/
        }

        public void setItem(DetailPost_item item) {
            textView.setText(item.getId());
            //textView3.setText(item.getImage());
        }

    }
}