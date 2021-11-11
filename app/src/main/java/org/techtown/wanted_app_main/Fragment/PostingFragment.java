package org.techtown.wanted_app_main.Fragment;

import android.content.Intent;
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
import org.techtown.wanted_app_main.Activity.PostingWriteActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Connect;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;

import java.util.ArrayList;
import java.util.List;

public class PostingFragment extends Fragment {

    // 이전 프래그먼트에서 Posting 객체 받기
    private static Posting posting;

    // Connect 리사이 클러뷰 설정
    private RecyclerView rvBoardRequest;
    private PostingDetailAdapter postingDetailAdapter = new PostingDetailAdapter();
    private ArrayList<Connect> connectItems = new ArrayList<>();


    // 뷰
    private TextView postingDetailDate, postingDetailTitle, postingDetailTeam, postingDetailName, postingDetailContent, postingDetailCategory;
    private ImageView postingDetailImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posting = getArguments().getParcelable("posting");
        connectItems = (ArrayList<Connect>) posting.connects;
        System.out.println("출력: " + posting);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posting, container, false);
        hideBottomNavigation(true);

        // 자신의 글인지 확인
        if (posting.personalId.equals(MainActivity.me.id)) { // 내 글
            // 뷰 컴포넌트
            Button request = view.findViewById(R.id.board_detail_request);
            ImageView edit_img = view.findViewById(R.id.board_retouch);

            // 텍스트 바꾸기
            if (posting.checkRecruiting) {
                request.setText("모집중");
            }

            // 글 수정 버튼 뜨도록 설정, 클릭시 글 수정
            edit_img.setImageResource(R.drawable.ic_write);
            edit_img.setOnClickListener(v -> {
                Intent intent = new Intent(getContext().getApplicationContext(), PostingWriteActivity.class);
                intent.putExtra("me", MainActivity.me);
                intent.putExtra("posting", posting);
                startActivity(intent);
                getActivity().finish();
            });

            // 모집중 버튼 클릭시 -> Posting변경 (모집중 -> 모집완료)
            request.setOnClickListener( v ->{

            });

        } else { // 내 글 아님

        }


        // Connect 신청 Adapter 설정
        rvBoardRequest = view.findViewById(R.id.recyclerView_board_detail);
        rvBoardRequest.setAdapter(postingDetailAdapter);
        rvBoardRequest.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));
        postingDetailAdapter.setItems(connectItems);

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
        postingDetailTeam.setText(posting.teamName);
        postingDetailName.setText(posting.nickname);
        postingDetailContent.setText(posting.content);
        postingDetailCategory.setText(posting.category);
        int image = getResources().getIdentifier(posting.img, "drawable", MainActivity.mainActivity.getPackageName());
        postingDetailImage.setImageResource(image);


//        connectItems.add(new Connect("시미즈" + " ", profile_basic1", "drawable", getContext().getPackageName())));
//        connectItems.add(new Connect("리안" + " ", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
//        connectItems.add(new Connect("가비" + " ", getResources().getIdentifier("@drawable/profile_basic3", "drawable", getContext().getPackageName())));
//        connectItems.add(new Connect("피넛" + " ", getResources().getIdentifier("@drawable/profile_basic4", "drawable", getContext().getPackageName())));


        // 참가 신청 버튼을 눌렀을 때, Connect가 생성되도록 코드 짜기 (Connect post)
        Button request = view.findViewById(R.id.board_detail_request);
        request.setOnClickListener(v -> {
//            if(request.getText().equals("참가 신청")) {
//                int temp_image = getResources().getIdentifier(me.img, "drawable", MainActivity.mainActivity.getPackageName());
//                boardDetailItems.add(new BoardDetail(me.nickname + " ", temp_image));
//                postingDetailAdapter.setItems(boardDetailItems);
//            } else if(request.getText().equals("모집 완료")) {
//
//            }

//            connectItems.add(new Connect());
            postingDetailAdapter.notifyDataSetChanged();
            request.setText("신청 완료");
        });

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