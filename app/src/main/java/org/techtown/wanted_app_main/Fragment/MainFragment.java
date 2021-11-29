package org.techtown.wanted_app_main.Fragment;

import static org.techtown.wanted_app_main.Activity.MainActivity.setBtnNavIndex;
import static org.techtown.wanted_app_main.Activity.MainActivity.updateBottomMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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
import org.techtown.wanted_app_main.Adapter.FriendAdapter;
import org.techtown.wanted_app_main.Adapter.PostingAdapter;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.ServerRequest.GetPersonalsRequest;
import org.techtown.wanted_app_main.database.Dto.PostingDtoInPersonal;
import org.techtown.wanted_app_main.database.Friend;
import org.techtown.wanted_app_main.database.Personal;
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
    Personal another;
    private ArrayList<Long> friendIds = new ArrayList<>();
    // layout
    private Button btnSchool, btnMajor, btnAddress;

    // 가져온 Personal과 posting정보
    public static ArrayList<Personal> personal_list;

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

                int count = 0;
                // 사람을 통해 posting 데이터 접근
                for (Personal personal : personal_list) {
                    if (!personal.postings.isEmpty()) {
                        for (PostingDtoInPersonal posting : personal.postings) {
                            // 포스팅 넣기
                            if (count < 4) {
                                postingItems.add(new Posting(posting.postingId, personal.id,
                                        posting.category, posting.title, posting.content, posting.connects, posting.postingTime, posting.endTime, posting.teamName, personal.nickname, personal.img, posting.checkRecruiting));
                                count += 1;
                            }
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

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        me = MainActivity.me;
        System.out.println("MainFragment onCreateView 출력:" + MainActivity.me);

        // 커뮤니티 리사이 클러뷰 설정
        recyclerViewCommunity = view.findViewById(R.id.recyclerView_board);
        recyclerViewCommunity.setAdapter(postingAdapter);
        recyclerViewCommunity.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));

        // 친구 리사클러뷰 설정
        recyclerViewFriend = view.findViewById(R.id.recyclerView_friend);
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewFriend.setAdapter(friendAdapter);

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
        LinearLayout btnGoCommunity = view.findViewById(R.id.arrow_community);
        btnGoCommunity.setOnClickListener(view1 -> {
            navController.navigate(R.id.action_main_to_posting_list);
            setBtnNavIndex(1);
            updateBottomMenu();
        });

        // 포스팅 글로 이동
        postingAdapter.setOnItemClicklistener(new PostingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("posting", postingItems.get(position));
                setBtnNavIndex(1);
                updateBottomMenu();
                navController.navigate(R.id.action_main_to_posting, bundle);
            }
        });

        //친구 눌렀을 시 프로필페이지로 이동
        friendAdapter.setOnItemClicklistener(new FriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putLong("profileId", friendIds.get(position));
                setBtnNavIndex(3);
                updateBottomMenu();
                navController.navigate(R.id.action_main_to_profile, bundle);
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
        friendIds.clear();
        for (int i = 0; i < personal_list.size(); i++) {
            another = personal_list.get(i);
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
                friendItems.add(new Friend(another.getId(), another.nickname, another.school, another.major, another.address, image));
                friendIds.add(another.id);
            }
        }
        friendAdapter.setFriendList(friendItems);
        friendAdapter.setFriendsCategory(friendsCategory);
    }

}