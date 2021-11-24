package org.techtown.wanted_app_main.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Search;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<Search> search = new ArrayList<Search>();
    static SearchAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClicklistener(SearchAdapter.OnItemClickListener listener) {
        this.listener = listener;

    }

    public void onItemClick(View view, int position) {
        if (listener != null)
            listener.onItemClick(view, position);
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
        Search search = this.search.get(position);
        viewHolder.setSearch(search);
    }

    public void setSearch(ArrayList<Search> arrayList) {
        this.search = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return search.size();
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

        public void setSearch(Search search) {
            tv_name.setText(search.getName());
        }
    }
}