package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class BoardDetailFragment extends Fragment {

    private RecyclerView rvBoardRequest;
    private BoardDetailAdapter boardDetailAdapter;
    private ArrayList<BoardDetail> boardDetailItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_detail, container, false);

        boardDetailAdapter = new BoardDetailAdapter();

        rvBoardRequest = view.findViewById(R.id.recyclerView_board_detail);
        rvBoardRequest.setAdapter(boardDetailAdapter);
        rvBoardRequest.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        boardDetailItems = new ArrayList<>();
        boardDetailItems.add(new BoardDetail("시미즈"+" ", getResources().getIdentifier("@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("리안"+" ", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("가비"+" ", getResources().getIdentifier("@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("피넛"+" ", getResources().getIdentifier("@drawable/profile_basic4", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("제인"+" ", getResources().getIdentifier("@drawable/profile_basic5", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("다니엘"+" ", getResources().getIdentifier("@drawable/profile_basic6", "drawable", getContext().getPackageName())));
        boardDetailAdapter.setItems(boardDetailItems);

        return view;
    }
}