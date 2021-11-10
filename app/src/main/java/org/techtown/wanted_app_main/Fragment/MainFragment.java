package org.techtown.wanted_app_main.Fragment;

import static org.techtown.wanted_app_main.Activity.MainActivity.setBtnNavIndex;
import static org.techtown.wanted_app_main.Activity.MainActivity.updateBottomMenu;

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

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.ServerRequest.GetPersonalsRequest;
import org.techtown.wanted_app_main.database.Dto.PersonalDtoInPosting;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Dto.PostingDtoInPersonal;
import org.techtown.wanted_app_main.database.Posting;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainFragment extends Fragment {
    // 커뮤니티의 보드
    private RecyclerView recyclerViewCommunity;
    private PostingAdapter postingAdapter = new PostingAdapter();
    private ArrayList<Posting> postingItems = new ArrayList<>();

    // 친구 보여주기
    private RecyclerView recyclerViewFriend;
    private FriendAdapter friendAdapter = new FriendAdapter();
    private ArrayList<Friend> friendItems = new ArrayList<>();

    // layout
    private Button btnSchool, btnMajor, btnAddress;

    // 가져온 Personal과 posting정보
    public ArrayList<Personal> personal_list;

    // category -> 0은 school, 1은 major, 2는 address
    private int friendsCategory = 0;

    private static NavController navController;

    private Personal me;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("MainFragment onCreate 출력:" + MainActivity.me);

        //서버 호출
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        // 모든 Personal 조회
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 한글깨짐 해결 코드
                String changeString = new String();
                try {
                    changeString = new String(response.getBytes("8859_1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<Personal>>() {
                }.getType();

                List<Personal> temp = gson.fromJson(changeString, listType);
                personal_list = new ArrayList<>(temp);

                // 사람을 통해 posting 데이터 접근
                for (Personal personal : personal_list) {
                    if (!personal.postings.isEmpty()) {
                        for (PostingDtoInPersonal posting : personal.postings) {
                            // 포스팅 넣기
                            // 포스팅 보여주기 위한
                            postingItems.add(new Posting(posting.id,
                                    new PersonalDtoInPosting(posting.personalId, personal.nickname, personal.img),
                                    posting.category, posting.title, posting.content, posting.connects, posting.postingTime, posting.teamName));
                        }
                    }
                }
                if (postingItems.size() > 1) {
                    Collections.sort(postingItems, (a, b) -> b.postingTime.compareTo(a.postingTime));
                }
                // 친구 채우기
                setCategory(friendsCategory);

                // 메인 화면에서 포스팅 설정
                postingAdapter.setPostingList(postingItems);

            }
        };
        requestQueue.add(new GetPersonalsRequest(responseListener));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //friendsCategoryList = getResources().getStringArray(R.array.friends_array);

        me = MainActivity.me;
        System.out.println("MainFragment onCreateView 출력:" + MainActivity.me);

        Bundle bundle = getArguments();
        //id = bundle.getInt("id");
//        Personal me = bundle.getParcelable("me");
//        id = 1;
//        Log.d("test_MainFragment", String.valueOf(me.id));


        // 커뮤니티 리사이 클러뷰 설정
        recyclerViewCommunity = view.findViewById(R.id.recyclerView_board);
        recyclerViewCommunity.setAdapter(postingAdapter);
        recyclerViewCommunity.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));

        // 친구 리사클러뷰 설정
        recyclerViewFriend = view.findViewById(R.id.recyclerView_friend);
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewFriend.setAdapter(friendAdapter);


        /*
        friendItems = new ArrayList<>();
        friendItems.add(new Friend("리헤이", "홍익대학교", "컴퓨터공학과", "수원시", getResources().getIdentifier("@drawable/profile_basic4", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("피넛", "홍익대학교", "경제학과", "성남시", getResources().getIdentifier("@drawable/profile_basic5", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("다니엘", "서강대학교", "수학교육과", "서울시", getResources().getIdentifier("@drawable/profile_basic6", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("엠마", "홍익대학교", "컴퓨터공학과", "김포시", getResources().getIdentifier("@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("스콧", "홍익대학교", "경제학과", "하남시", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        // 아래 두개는 display 안 됨 (item display 갯수제한 테스트용 -> DB 연동하고 필요 없어지면 지워주세요)
        friendItems.add(new Friend("다니엘", "홍익대학교", "경제학과", "성남시", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("다니엘", "홍익대학교", "경제학과", "성남시", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));

        friendAdapter.setFriendList(friendItems); */

        //        boardItems.add(new Board("공모전", "원티드 해커톤 같이 나가실 개발자 구해요!", "시미즈", getResources().getIdentifier("@drawable/profile_basic1", "drawable", getContext().getPackageName())));
//        boardItems.add(new Board("스터디", "열품타 스터디원 충원합니다", "리안", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
//        boardItems.add(new Board("기타", "광명에서 카공하실 분!", "가비", getResources().getIdentifier("@drawable/profile_basic3", "drawable", getContext().getPackageName())));


        // btn 클릭 이벤트
        btnSchool = view.findViewById(R.id.btn_school);
        btnMajor = view.findViewById(R.id.btn_major);
        btnAddress = view.findViewById(R.id.btn_address);
        onCategoryClickedChangeButtonDesign(friendsCategory);
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_school:
                        friendsCategory = 0;
                        break;
                    case R.id.btn_major:
                        friendsCategory = 1;
                        break;
                    case R.id.btn_address:
                        friendsCategory = 2;
                        break;
                }
                onCategoryClickedChangeButtonDesign(friendsCategory);
                setCategory(friendsCategory);
            }
        };
        btnSchool.setOnClickListener(onClickListener);
        btnMajor.setOnClickListener(onClickListener);
        btnAddress.setOnClickListener(onClickListener);

        //커뮤니티로 이동
        Button btnGoCommunity = view.findViewById(R.id.arrow_community);

        btnGoCommunity.setOnClickListener(view1 -> {
            Bundle bundle1 = new Bundle();
            bundle1.putString("btnGoCommunity", "test");

            navController.navigate(R.id.action_main_to_posting_list, bundle);
            setBtnNavIndex(1);
            updateBottomMenu();
        });

        // 포스팅 글로 이동
        postingAdapter.setOnItemClicklistener(new PostingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("posting", postingItems.get(position));
                bundle.putParcelable("me", me);
                navController.navigate(R.id.action_main_to_posting, bundle);
                setBtnNavIndex(1);
                updateBottomMenu();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    // 카테고리 눌렀을때, 버튼 색변환
    public void onCategoryClickedChangeButtonDesign(int index) {
        List<Button> buttons = Arrays.asList(btnSchool, btnMajor, btnAddress);

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);

            if (i == index) {
                button.setBackgroundResource(R.drawable.btn_teal);
                button.setTextColor(getResources().getColor(R.color.white));
            } else {
                button.setBackgroundResource(R.drawable.btn_teal_off);
                button.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    // 카테고리에 따라서, 나와 일치하는 정보를 가진 사람들의 데이터를 FriendAdapter에 채워줌
    public void setCategory(int friendsCategory) {
        friendItems.clear();
        for (int i = 0; i < personal_list.size(); i++) {
            Personal another = personal_list.get(i);
            if (MainActivity.me.id != another.id) {
                switch (friendsCategory) {
                    case 0:
                        if (!MainActivity.me.school.equals(another.school)) {
                            continue;
                        }
                        break;
                    case 1:
                        if (!MainActivity.me.major.equals(another.major)) {
                            continue;
                        }
                        break;
                    case 2:
                        if (!MainActivity.me.address.equals(another.address)) {
                            continue;
                        }
                        break;
                }
                int image = getResources().getIdentifier(another.img, "drawable", getContext().getPackageName());
                friendItems.add(new Friend(another.nickname, another.school, another.major, another.address, image));
            }
        }
        friendAdapter.setFriendList(friendItems);
        friendAdapter.setFriendsCategory(friendsCategory);
    }

}