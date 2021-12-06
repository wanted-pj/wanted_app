package org.techtown.wanted_app_main.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<String> searchList = new ArrayList<>();
    static SearchAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClicklistener(SearchAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_register_search, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String search = this.searchList.get(position);
        viewHolder.setSearch(search);
    }

    public void setSearchList(ArrayList<String> searchList) {
        this.searchList = searchList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.search_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null)
                        listener.onItemClick(v, position);
                }
            });
        }

        public void setSearch(String search) {
            tv_name.setText(search);
        }
    }
}