package org.techtown.wanted_app_main.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Posting;

import java.util.ArrayList;

public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ViewHolder> {

    static ArrayList<Posting> postingList = new ArrayList<>();
    static PostingAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClicklistener(PostingAdapter.OnItemClickListener listener){
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
        Posting posting = postingList.get(position);
        viewHolder.setPosting(posting);
    }

    public void setPostingList(ArrayList<Posting> arrayList){
        this.postingList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return postingList.size();
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

        public void setPosting(Posting posting) {
            tv_category.setText(posting.category);
            tv_title.setText(posting.title);
            tv_writer.setText(String.valueOf(posting.personal.nickname));
            int image = MainActivity.mainActivity.getResources().getIdentifier(posting.personal.img, "drawable", MainActivity.mainActivity.getPackageName());
            iv.setImageResource(image);
        }
    }
}