package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment {

    private RecyclerView recyclerViewCommunity;
    private BoardAdapter boardAdapter;
    private ArrayList<Board> boardItems;

    private RecyclerView recyclerViewFriend;
    private FriendAdapter friendAdapter = new FriendAdapter();
    private ArrayList<Friend> friendItems = new ArrayList<>();

    private Button btnSchool, btnMajor, btnAddress;

    public List<Personal> personal_list = new ArrayList<>();
    public List<Personal> personal_list_app = new ArrayList<>();
    public List<Posting> posting_list = new ArrayList<>();
    public List<Posting> posting_list_app = new ArrayList<>();

    private static NavController navController;
    private static String spinnerString = "학교친구";
    private static String[] friendsCategoryList;

    private int id;

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

        Bundle bundle = getArguments();
        //id = bundle.getInt("id");
        id = 1;
        Log.d("test_MainFragment", String.valueOf(id));

        //friendAdapter = new FriendAdapter();
        boardAdapter = new BoardAdapter();

        // 커뮤니티
        recyclerViewCommunity = view.findViewById(R.id.recyclerView_board);
        recyclerViewCommunity.setAdapter(boardAdapter);
        recyclerViewCommunity.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));

        //서버 호출
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url1 = "http://13.125.214.178:8080/personal";
        String url2 = "http://13.125.214.178:8080/posting";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
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

                personal_list = gson.fromJson(changeString, listType);

                for (int i = 0; i < personal_list.size(); i++) {
                    personal_list_app.add(new Personal(personal_list.get(i).id, personal_list.get(i).string_id, personal_list.get(i).pwd,
                            personal_list.get(i).nickname, personal_list.get(i).school, personal_list.get(i).major, personal_list.get(i).grade,
                            personal_list.get(i).age, personal_list.get(i).address, personal_list.get(i).career, personal_list.get(i).gender, personal_list.get(i).img));
                }

                setCategory("school");

                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
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
                        Type listType = new TypeToken<ArrayList<Posting>>() {
                        }.getType();

                        posting_list = gson.fromJson(changeString, listType);

                        for (int i = 0; i < posting_list.size(); i++) {
                            posting_list_app.add(new Posting(posting_list.get(i).posting_id, posting_list.get(i).personal, posting_list.get(i).category, posting_list.get(i).title,
                                    posting_list.get(i).content, posting_list.get(i).connects, posting_list.get(i).team, posting_list.get(i).postingTime));
                        }

                        boardItems = new ArrayList<>();

                        String writer = null;
                        String string_image = null;

                        for (int i = 0; i < posting_list.size(); i++) {
                            Long writer_num = posting_list.get(i).personal.id;
                            for (int j = 0; j < personal_list.size(); j++) {
                                if (writer_num == personal_list.get(j).id) {
                                    writer = personal_list.get(i).nickname;
                                    string_image = personal_list.get(i).img;
                                }
                            }
                            int image = getResources().getIdentifier(string_image, "drawable", getContext().getPackageName());
                            boardItems.add(new Board(posting_list.get(i).category, posting_list.get(i).title, writer, image));
                        }

                        boardAdapter.setBoardList(boardItems);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                requestQueue.add(stringRequest2);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);

        boardItems = new ArrayList<>();
        boardItems.add(new Board("공모전", "원티드 해커톤 같이 나가실 개발자 구해요!", "시미즈", getResources().getIdentifier("@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        boardItems.add(new Board("스터디", "열품타 스터디원 충원합니다", "리안", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        boardItems.add(new Board("기타", "광명에서 카공하실 분!", "가비", getResources().getIdentifier("@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        boardAdapter.setBoardList(boardItems);

        // 친구
        recyclerViewFriend = view.findViewById(R.id.recyclerView_friend);
        recyclerViewFriend.setAdapter(friendAdapter);
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false));

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
                switch (v.getId()) {
                    case R.id.btn_school:
                        onButtonClicked(0);
                        setCategory("school");
                        break;
                    case R.id.btn_major:
                        onButtonClicked(1);
                        setCategory("major");
                        break;
                    case R.id.btn_address:
                        onButtonClicked(2);
                        setCategory("address");
                        break;
                }
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
            navController.navigate(R.id.action_mainFragment_to_boardFragment, bundle);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    public void onButtonClicked(int index) {
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

    public void setCategory(String category) {
        friendItems.clear();
        for (int i = 0; i < personal_list_app.size(); i++) {
            if (id == personal_list_app.get(i).id) {
                String temp = null;
                String temp2 = null;
                if (category.equals("school")) {
                    temp = personal_list_app.get(i).school;
                } else if (category.equals("major")) {
                    temp = personal_list_app.get(i).major;
                } else if (category.equals("address")) {
                    temp = personal_list_app.get(i).address;
                }
                for (int j = 0; j < personal_list_app.size(); j++) {
                    if (category.equals("school")) {
                        temp2 = personal_list_app.get(j).school;
                    } else if (category.equals("major")) {
                        temp2 = personal_list_app.get(j).major;
                    } else if (category.equals("address")) {
                        temp2 = personal_list_app.get(j).address;
                    }
                    if (id != personal_list_app.get(j).id && temp.equals(temp2)) {
                        int image = getResources().getIdentifier(personal_list_app.get(j).img, "drawable", getContext().getPackageName());
                        friendItems.add(new Friend(personal_list_app.get(j).nickname, personal_list_app.get(j).school, personal_list_app.get(j).major, personal_list_app.get(j).address, image));
                    }
                }
            }
            break;
        }
        friendAdapter.setFriendList(friendItems);
    }
}