package org.techtown.wanted_app_main.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class CareerAdapter extends RecyclerView.Adapter<CareerAdapter.ViewHolder> {

    private ArrayList<Career> careerList = new ArrayList<Career>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_career, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Career career = careerList.get(position);
        viewHolder.setCareer(career);
    }

    public void setCareerList(ArrayList<Career> arrayList){
        this.careerList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return careerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_career;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_career = itemView.findViewById(R.id.career_content);
        }

        public void setCareer(Career career) {
            tv_career.setText(career.getContent());
        }
    }
}