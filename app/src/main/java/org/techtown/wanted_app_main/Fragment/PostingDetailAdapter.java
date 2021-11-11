package org.techtown.wanted_app_main.Fragment;
//
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Connect;

import java.util.ArrayList;

public class PostingDetailAdapter extends RecyclerView.Adapter<PostingDetailAdapter.ViewHolder> {
    static ArrayList<Connect> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_board_detail, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Connect item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItem() {
        items.clear();
    }

    public void addItem(Connect item) {
        items.add(item);
    }

    public void setItems(ArrayList<Connect> items) {
        this.items = items;
    }

    public Connect getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Connect item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_writer;
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_writer = itemView.findViewById(R.id.board_detail_request_writer);
            iv = itemView.findViewById(R.id.board_detail_request_image);
        }

        public void setItem(Connect item) {
            tv_writer.setText(item.nickname);
            int image = MainActivity.mainActivity.getResources().getIdentifier(item.img, "drawable", MainActivity.mainActivity.getPackageName());
            iv.setImageResource(image);
        }

    }
}