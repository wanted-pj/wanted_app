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

    private RecyclerView recyclerViewSchool;
    private RecyclerView recyclerViewCommunity;
    private FriendAdapter friendAdapter;
    private PostingAdapter postingAdapter;
    private ArrayList<Friend> friendItems;
    private ArrayList<Posting> postingItems;
    private Button btnSchool, btnMajor, btnAddress;

    private static NavController navController;
    private static String spinnerString = "학교친구";
    private static String[] friendsCategoryList;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //friendsCategoryList = getResources().getStringArray(R.array.friends_array);

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
        recyclerViewSchool = view.findViewById(R.id.recyclerView_school);
        recyclerViewSchool.setAdapter(friendAdapter);
        recyclerViewSchool.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL,false));

        friendItems = new ArrayList<>();
        friendItems.add(new Friend("가비", "홍익대학교", "컴퓨터공학과", getResources().getIdentifier( "@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("피넛", "홍익대학교", "경제학과", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("시미즈", "서강대학교", "수학교육과", getResources().getIdentifier( "@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        friendAdapter.setFriendList(friendItems);

        btnSchool = view.findViewById(R.id.btnSchool);
        btnMajor = view.findViewById(R.id.btnMajor);
        btnAddress = view.findViewById(R.id.btnAddress);

        // 처음 btn 지정
        btnSchool.setBackgroundResource(R.drawable.btn_teal);
        btnSchool.setTextColor(getResources().getColor(R.color.white));

        // btn 클릭 이벤트
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.btnSchool:
                        onButtonClicked(0);
                        break;
                    case R.id.btnMajor:
                        onButtonClicked(1);
                        break;
                    case R.id.btnAddress:
                        onButtonClicked(2);
                        break;
                }
            }
        };

        btnSchool.setOnClickListener(onClickListener);
        btnMajor.setOnClickListener(onClickListener);
        btnAddress.setOnClickListener(onClickListener);

//        // 스피너 값 값 가져오기
//        Spinner friends_category = view.findViewById(R.id.main_spinner_category);
//        ArrayAdapter<String> friendsAdapter = new ArrayAdapter<>(
//                getActivity(), android.R.layout.simple_spinner_item, friendsCategoryList); // resource 데이터 넣기
//
//        friendsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // 드롭다운 클릭시 선택창 방식
//        friends_category.setAdapter(friendsAdapter);
//
//        // 스피너 이벤트 반응
//        friends_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                spinnerString = friends_category.getSelectedItem().toString();
//                System.out.println("친구 선택됨" + spinnerString);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                spinnerString = "학교친구";
//                System.out.println("아무것도 선택안됨");
//            }
//        });
//
//        // 버튼클릭시 친구리스트 조회
//        Button btnGoFriend = view.findViewById(R.id.main_btn_goFriend);
//
//        btnGoFriend.setOnClickListener(view1 -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("friendsCategory", spinnerString);
//            navController.navigate(R.id.action_mainFragment_to_showFriendsFragment, bundle);
//        });


        Button btnGoFriend = view.findViewById(R.id.arrow_showfriends);

       btnGoFriend.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("friendsCategory", "test");
          navController.navigate(R.id.action_mainFragment_to_showFriendsFragment, bundle);
      });
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