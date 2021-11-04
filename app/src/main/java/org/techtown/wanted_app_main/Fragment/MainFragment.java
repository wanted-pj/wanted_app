package org.techtown.wanted_app_main.Fragment;

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
import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment {

    private PostingAdapter postingAdapter;
    private ArrayList<Posting> postingItems;
    private RecyclerView recyclerViewCommunity;

    private FriendAdapter friendAdapter;
    private ArrayList<Friend> friendItems;
    private RecyclerView recyclerViewFriend;

    private Button btnSchool, btnMajor, btnAddress;

    private static NavController navController;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        friendAdapter = new FriendAdapter();
        postingAdapter = new PostingAdapter();

        // 커뮤니티
        recyclerViewCommunity = view.findViewById(R.id.recyclerView_community);
        recyclerViewCommunity.setAdapter(postingAdapter);
        recyclerViewCommunity.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        postingItems = new ArrayList<>();
        postingItems.add(new Posting("공모전", "원티드 해커톤 같이 나가실 개발자 구해요!", "시미즈", getResources().getIdentifier( "@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        postingItems.add(new Posting("스터디", "열품타 스터디원 충원합니다", "리안", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        postingItems.add(new Posting("대외활동", "KT상상유니브 팀원 구합니다", "가비", getResources().getIdentifier( "@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        postingAdapter.setPostingList(postingItems);

        // 친구
        recyclerViewFriend = view.findViewById(R.id.recyclerView_friend);
        recyclerViewFriend.setAdapter(friendAdapter);
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL,false));

        friendItems = new ArrayList<>();
        friendItems.add(new Friend("리헤이", "홍익대학교", "컴퓨터공학과", "수원시", getResources().getIdentifier( "@drawable/profile_basic4", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("피넛", "홍익대학교", "경제학과", "성남시", getResources().getIdentifier( "@drawable/profile_basic5", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("다니엘", "서강대학교", "수학교육과", "서울시", getResources().getIdentifier( "@drawable/profile_basic6", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("엠마", "홍익대학교", "컴퓨터공학과", "김포시", getResources().getIdentifier( "@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("스콧", "홍익대학교", "경제학과", "하남시", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        // 아래 두개는 display 안 됨 (item display 갯수제한 테스트용 -> DB 연동하고 필요 없어지면 지워주세요)
        friendItems.add(new Friend("다니엘", "홍익대학교", "경제학과", "성남시", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("다니엘", "홍익대학교", "경제학과", "성남시", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));

        friendAdapter.setFriendList(friendItems);

        btnSchool = view.findViewById(R.id.btn_school);
        btnMajor = view.findViewById(R.id.btn_major);
        btnAddress = view.findViewById(R.id.btn_address);

        // 처음 btn 지정
        btnSchool.setBackgroundResource(R.drawable.btn_teal);
        btnSchool.setTextColor(getResources().getColor(R.color.white));

        // btn 클릭 이벤트
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.btn_school:
                        onButtonClicked(0);
                        break;
                    case R.id.btn_major:
                        onButtonClicked(1);
                        break;
                    case R.id.btn_address:
                        onButtonClicked(2);
                        break;
                }
            }
        };

        btnSchool.setOnClickListener(onClickListener);
        btnMajor.setOnClickListener(onClickListener);
        btnAddress.setOnClickListener(onClickListener);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    public void onButtonClicked (int index) {
        List<Button> buttons = Arrays.asList(btnSchool, btnMajor, btnAddress);

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);

            if (i == index) {
                button.setBackgroundResource(R.drawable.btn_teal);
                button.setTextColor(getResources().getColor(R.color.white));
            }
            else {
                button.setBackgroundResource(R.drawable.btn_teal_off);
                button.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }

    }
}