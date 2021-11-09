package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Posting;

import java.util.ArrayList;

public class PostingFragment extends Fragment {

    private RecyclerView rvBoardRequest;
    private PostingDetailAdapter postingDetailAdapter = new PostingDetailAdapter();
    private ArrayList<BoardDetail> boardDetailItems = new ArrayList<>();

    private static Posting posting;

    // 뷰
    private TextView postingDetailDate, postingDetailTitle, postingDetailTeam, postingDetailName, postingDetailContent, postingDetailCategory;
    private ImageView postingDetailImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posting = getArguments().getParcelable("posting");
        System.out.println("출력: " + posting);
        // me = getArguments().getParcelable("posting");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posting, container, false);
        hideBottomNavigation(true);

        // Connect 신청 Adapter 설정
        rvBoardRequest = view.findViewById(R.id.recyclerView_board_detail);
        rvBoardRequest.setAdapter(postingDetailAdapter);
        rvBoardRequest.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));

        // 뷰 컴포넌트 가져오기
        postingDetailCategory = view.findViewById(R.id.board_detail_category);
        postingDetailDate = view.findViewById(R.id.board_detail_date);
        postingDetailTitle = view.findViewById(R.id.board_detail_title);
        postingDetailTeam = view.findViewById(R.id.board_detail_team);
        postingDetailName = view.findViewById(R.id.board_detail_name);
        postingDetailContent = view.findViewById(R.id.board_detail_content);
        postingDetailImage = view.findViewById(R.id.board_detail_image);

        // 데이터 채우기
        postingDetailDate.setText(posting.postingTime);
        postingDetailTitle.setText(posting.title);
//        postingDetailTeam.setText(posting.t);
        postingDetailName.setText(posting.personal.nickname);
        postingDetailContent.setText(posting.content);
        postingDetailCategory.setText(posting.category);
        int image = getResources().getIdentifier(posting.personal.img, "drawable", MainActivity.mainActivity.getPackageName());
        postingDetailImage.setImageResource(image);


        boardDetailItems = new ArrayList<>();
        boardDetailItems.add(new BoardDetail("시미즈" + " ", getResources().getIdentifier("@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("리안" + " ", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("가비" + " ", getResources().getIdentifier("@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("피넛" + " ", getResources().getIdentifier("@drawable/profile_basic4", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("제인" + " ", getResources().getIdentifier("@drawable/profile_basic5", "drawable", getContext().getPackageName())));
        boardDetailItems.add(new BoardDetail("다니엘" + " ", getResources().getIdentifier("@drawable/profile_basic6", "drawable", getContext().getPackageName())));
        postingDetailAdapter.setItems(boardDetailItems);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideBottomNavigation(false);
    }

    public void hideBottomNavigation(Boolean bool) {
        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottomNavigation);
        if (bool == true)
            bottomNavigation.setVisibility(View.GONE);
        else
            bottomNavigation.setVisibility(View.VISIBLE);
    }
}