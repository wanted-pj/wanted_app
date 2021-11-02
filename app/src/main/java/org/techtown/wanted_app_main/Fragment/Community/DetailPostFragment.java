package org.techtown.wanted_app_main.Fragment.Community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

public class DetailPostFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_detail_post, container, false);


        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_request);
        recyclerView.setLayoutManager(layoutManager);
        DetailPostAdapter adapter = new DetailPostAdapter();
        adapter.clearItem();
        adapter.addItem(new DetailPost_item("dd","시미즈"));
        recyclerView.setAdapter(adapter);


        return root;
    }
}