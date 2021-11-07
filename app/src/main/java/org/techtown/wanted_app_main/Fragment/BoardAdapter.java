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

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    static ArrayList<Board> boardList = new ArrayList<Board>();
    static BoardAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClicklistener(BoardAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public void onItemClick(View view, int position) {
        if(listener != null)
            listener.onItemClick(view, position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_board, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Board board = boardList.get(position);
        viewHolder.setBoard(board);
    }

    public void setBoardList(ArrayList<Board> arrayList){
        this.boardList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_category;
        TextView tv_title;
        TextView tv_writer;
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_category = itemView.findViewById(R.id.tv_category);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_writer = itemView.findViewById(R.id.tv_writer);
            iv = itemView.findViewById(R.id.iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null)
                        listener.onItemClick(v, position);
                }
            });
        }

        public void setBoard(Board board) {
            tv_category.setText(board.getCategory());
            tv_title.setText(board.getTitle());
            tv_writer.setText(String.valueOf(board.getWriter()));
            iv.setImageResource(board.getImgRes());
        }
    }
}