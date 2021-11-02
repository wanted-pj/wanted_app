package org.techtown.wanted_app_main.Fragment.Community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

//
public class Contest extends Fragment {

    private static NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contest, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.postre);

        recyclerView.setLayoutManager(layoutManager);
        PostAdapter adapter = new PostAdapter();
        adapter.clearItem();
        adapter.addItem(new Post("dd","원티드 해커톤","개발자 구합니다"));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClicklistener(new PostAdapter.OnPostItemClickListener() {
            @Override
            public void onItemClick(PostAdapter.ViewHolder holder, View view, ArrayList<Post> items, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("test", "testmessage");
                navController.navigate(R.id.action_contest_to_detailpost, bundle);
            }
        });



        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}