package org.techtown.wanted_app_main.Fragment;
//

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.wanted_app_main.Fragment.Community.Club;
import org.techtown.wanted_app_main.Fragment.Community.Contest;
import org.techtown.wanted_app_main.Fragment.Community.FragmentAdapter;
import org.techtown.wanted_app_main.Fragment.Community.Outdoor_activities;
import org.techtown.wanted_app_main.Fragment.Community.Study;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommunityFragment extends Fragment {
    private Button btnContest, btnClub, btnOut, btnStudy;

    private static NavController navController;

    public List<Personal> personal_list = new ArrayList<>();
    public List<Personal> personal_list_app = new ArrayList<>();
    public List<Posting> posting_list = new ArrayList<>();
    public List<Posting> posting_list_app = new ArrayList<>();
    private ArrayList<Board> boardItems = new ArrayList<>();;

    private BoardAdapter boardAdapter;


    public CommunityFragment() {
        // Required empty public constructor
    }

    // test
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
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
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        boardAdapter = new BoardAdapter();

        btnContest = view.findViewById(R.id.btnContest);
        btnClub = view.findViewById(R.id.btnClub);
        btnOut = view.findViewById(R.id.btnOut);
        btnStudy = view.findViewById(R.id.btnStudy);

        btnContest.setBackgroundResource(R.drawable.btn_teal);
        btnContest.setTextColor(getResources().getColor(R.color.white));

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

                for(int i=0; i<personal_list.size(); i++) {
                    personal_list_app.add(new Personal(personal_list.get(i).id, personal_list.get(i).string_id, personal_list.get(i).pwd,
                            personal_list.get(i).nickname, personal_list.get(i).school, personal_list.get(i).major, personal_list.get(i).grade,
                            personal_list.get(i).age, personal_list.get(i).address, personal_list.get(i).carrer, personal_list.get(i).gender, personal_list.get(i).img));
                }

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

                        for(int i=0; i<posting_list.size(); i++) {
                            posting_list_app.add(new Posting(posting_list.get(i).posting_id, posting_list.get(i).category, posting_list.get(i).title,
                                    posting_list.get(i).content, posting_list.get(i).personal_id));
                        }

                        boardItems.clear();

                        String writer = null;
                        String string_image = null;

                        for(int i=0; i<posting_list.size(); i++) {
                            if(posting_list.get(i).category.equals("공모전")) {
                                int writer_num = posting_list.get(i).personal_id;
                                for(int j=0; j<personal_list.size(); j++) {
                                    if(writer_num == personal_list.get(j).id) {
                                        writer = personal_list.get(i).nickname;
                                        string_image = personal_list.get(i).img;
                                    }
                                }
                                int image = getResources().getIdentifier(string_image , "drawable", getContext().getPackageName());
                                boardItems.add(new Board(posting_list.get(i).category, posting_list.get(i).title, writer, image));
                            }
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

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnContest:
                        onButtonClicked(0);

                        boardItems.clear();

                        String writer = null;
                        String string_image = null;

                        for(int i=0; i<posting_list_app.size(); i++) {
                            if(posting_list_app.get(i).category.equals("공모전")) {
                                int writer_num = posting_list_app.get(i).personal_id;
                                for(int j=0; j<personal_list_app.size(); j++) {
                                    if(writer_num == personal_list_app.get(j).id) {
                                        writer = personal_list_app.get(i).nickname;
                                        string_image = personal_list_app.get(i).img;
                                    }
                                }
                                int image = getResources().getIdentifier(string_image , "drawable", getContext().getPackageName());
                                boardItems.add(new Board(posting_list_app.get(i).category, posting_list_app.get(i).title, writer, image));
                            }
                        }
                        boardAdapter.setPostingList(boardItems);
                        break;
                    case R.id.btnClub:
                        onButtonClicked(1);
                        boardItems.clear();

                        writer = null;
                        string_image = null;

                        for(int i=0; i<posting_list_app.size(); i++) {
                            if(posting_list_app.get(i).category.equals("동아리")) {
                                int writer_num = posting_list_app.get(i).personal_id;
                                for(int j=0; j<personal_list_app.size(); j++) {
                                    if(writer_num == personal_list_app.get(j).id) {
                                        writer = personal_list_app.get(i).nickname;
                                        string_image = personal_list_app.get(i).img;
                                    }
                                }
                                int image = getResources().getIdentifier(string_image , "drawable", getContext().getPackageName());
                                boardItems.add(new Board(posting_list_app.get(i).category, posting_list_app.get(i).title, writer, image));
                            }
                        }
                        boardAdapter.setPostingList(boardItems);

                        break;
                    case R.id.btnOut:
                        onButtonClicked(2);
                        boardItems.clear();

                        writer = null;
                        string_image = null;

                        for(int i=0; i<posting_list_app.size(); i++) {
                            if(posting_list_app.get(i).category.equals("대외활동")) {
                                int writer_num = posting_list_app.get(i).personal_id;
                                for(int j=0; j<personal_list_app.size(); j++) {
                                    if(writer_num == personal_list_app.get(j).id) {
                                        writer = personal_list_app.get(i).nickname;
                                        string_image = personal_list_app.get(i).img;
                                    }
                                }
                                int image = getResources().getIdentifier(string_image , "drawable", getContext().getPackageName());
                                boardItems.add(new Board(posting_list_app.get(i).category, posting_list_app.get(i).title, writer, image));
                            }
                        }
                        boardAdapter.setPostingList(boardItems);

                        break;
                    case R.id.btnStudy:
                        onButtonClicked(3);
                        boardItems.clear();

                        writer = null;
                        string_image = null;

                        for(int i=0; i<posting_list_app.size(); i++) {
                            if(posting_list_app.get(i).category.equals("스터디")) {
                                int writer_num = posting_list_app.get(i).personal_id;
                                for(int j=0; j<personal_list_app.size(); j++) {
                                    if(writer_num == personal_list_app.get(j).id) {
                                        writer = personal_list_app.get(i).nickname;
                                        string_image = personal_list_app.get(i).img;
                                    }
                                }
                                int image = getResources().getIdentifier(string_image , "drawable", getContext().getPackageName());
                                boardItems.add(new Board(posting_list_app.get(i).category, posting_list_app.get(i).title, writer, image));
                            }
                        }
                        boardAdapter.setPostingList(boardItems);
                        break;
                }
            }
        };

        btnContest.setOnClickListener(onClickListener);
        btnClub.setOnClickListener(onClickListener);
        btnOut.setOnClickListener(onClickListener);
        btnStudy.setOnClickListener(onClickListener);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    public void onButtonClicked(int index) {
        List<Button> buttons = Arrays.asList(btnContest, btnClub, btnOut, btnStudy);

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
}