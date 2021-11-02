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

    private RecyclerView recyclerViewSchool;
    private RecyclerView recyclerViewCommunity;
    private FriendAdapter friendAdapter;
    private BoardAdapter boardAdapter;
    private ArrayList<Friend> friendItems;
    private ArrayList<Board> boardItems;
    private Button btnSchool, btnMajor, btnAddress;

    public List<Personal> personal_list = new ArrayList<>();
    public List<Posting> posting_list = new ArrayList<>();

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
        friendsCategoryList = getResources().getStringArray(R.array.friends_array);

        friendAdapter = new FriendAdapter();
        boardAdapter = new BoardAdapter();

        // 커뮤니티
        recyclerViewCommunity = view.findViewById(R.id.recyclerView_community);
        recyclerViewCommunity.setAdapter(boardAdapter);
        recyclerViewCommunity.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

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
                Type listType = new TypeToken<ArrayList<Personal>>() {}.getType();

                personal_list = gson.fromJson(changeString, listType);

                friendItems = new ArrayList<>();

                for(int i=0; i<personal_list.size(); i++) {
                    int image = getResources().getIdentifier(personal_list.get(i).img , "drawable", getContext().getPackageName());
                    friendItems.add(new Friend(personal_list.get(i).nickname, personal_list.get(i).school, personal_list.get(i).major, image));
                }

                friendAdapter.setFriendList(friendItems);

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
                        Type listType = new TypeToken<ArrayList<Personal>>() {}.getType();

                        posting_list = gson.fromJson(changeString, listType);

                        boardItems = new ArrayList<>();

                        String writer = null;
                        String string_image = null;

                        for(int i = 0; i< posting_list.size(); i++) {
                            int writer_num = posting_list.get(i).personal_id;
                            for(int j=0; j< personal_list.size(); j++) {
                                if(writer_num == personal_list.get(j).personal_id) {
                                    writer = personal_list.get(i).nickname;
                                    string_image = personal_list.get(i).img;
                                }
                            }
                            int image = getResources().getIdentifier(string_image , "drawable", getContext().getPackageName());
                            boardItems.add(new Board(posting_list.get(i).category, posting_list.get(i).title, writer, image));
                        }

                        boardAdapter.setPostingList(boardItems);

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

        // 친구
        recyclerViewSchool = view.findViewById(R.id.recyclerView_school);
        recyclerViewSchool.setAdapter(friendAdapter);
        recyclerViewSchool.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL,false));

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