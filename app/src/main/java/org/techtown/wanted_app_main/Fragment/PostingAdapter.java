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

public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ViewHolder> {

    private ArrayList<Posting> postingList = new ArrayList<Posting>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_posting, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Posting posting = postingList.get(position);
        viewHolder.setPost(posting);
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
        }

        public void setPost(Posting posting) {
            tv_category.setText(posting.getCategory());
            tv_title.setText(posting.getTitle());
            tv_writer.setText(String.valueOf(posting.getWriter()));
            iv.setImageResource(posting.getImgRes());
        }
    }
}