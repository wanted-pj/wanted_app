package org.techtown.wanted_app_main.Fragment;
//
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class BoardDetailAdapter extends RecyclerView.Adapter<BoardDetailAdapter.ViewHolder> {
    static ArrayList<BoardDetail> items = new ArrayList<BoardDetail>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_board_detail, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        BoardDetail item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItem() {
        items.clear();
    }

    public void addItem(BoardDetail item) {
        items.add(item);
    }

    public void setItems(ArrayList<BoardDetail> items) {
        this.items = items;
    }

    public BoardDetail getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, BoardDetail item) {
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

        public void setItem(BoardDetail item) {
            tv_writer.setText(item.getWriter());
            iv.setImageResource(item.getImgRes());
        }

    }
}