package org.techtown.wanted_app_main.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;

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
import org.techtown.wanted_app_main.Activity.PostingWriteActivity;
import org.techtown.wanted_app_main.Adapter.PostingAdapter;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.ServerRequest.GetPostingsRequest;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PostingListFragment extends Fragment {
    private static NavController navController;
    private Button btnAll, btnContest, btnStudy, btnEtc, btnSearch;
    private ImageView btnMyPosting, btnWrite;
    private EditText searchText;

    // 사람과 포스팅
    public List<Posting> posting_list = new ArrayList<>();

    // 포스트 보여주기
    private RecyclerView recyclerViewPosting;
    private PostingAdapter postingAdapter = new PostingAdapter();
    private ArrayList<Posting> postingItems = new ArrayList<>();

    // 나 자신
    private Personal me;

    // communityCategory => 0전체, 1공모전 2스터디, 3기타
    int communityCategory;

    // 내글 보기 토큰
    boolean myPostingCheck = false;

    public PostingListFragment() {
        // Required empty public constructor
    }

    // test
    public static PostingListFragment newInstance(String param1, String param2) {
        PostingListFragment fragment = new PostingListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = MainActivity.me;
        System.out.println("postingListFragment >> " + me);

        // 카테고리의 기본값 설정은 (전체)
        communityCategory = 0;

        //서버 호출
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        // 모든 Posting 조회
        Response.Listener<String> postingResponseListener = new Response.Listener<String>() {
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

                List<Posting> temp = gson.fromJson(changeString, listType);
                posting_list = new ArrayList<>(temp);
                if (posting_list.size() > 2) {
                    Collections.sort(posting_list, (a, b) -> b.postingTime.compareTo(a.postingTime));
                }

                // 카테고리에 따라 boadItem채우기
                postingItems = new ArrayList<>(posting_list);
                setCommunityCategory(communityCategory, "");
            }
        };
        requestQueue.add(new GetPostingsRequest(postingResponseListener));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posting_list, container, false);

        // postings 보여주는 RecyclerView와 Adapter 설정
        recyclerViewPosting = view.findViewById(R.id.recyclerView_board_more);
        recyclerViewPosting.setAdapter(postingAdapter);
        recyclerViewPosting.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));

        // SearchText 설정
        searchText = view.findViewById(R.id.search);
        btnSearch = view.findViewById(R.id.searchButton);

        btnSearch.setOnClickListener(v -> {
                    String findingText = searchText.getText().toString();
                    setCommunityCategory(communityCategory, findingText);
                }
        );

        // 버튼
        // 버튼 뷰 컴포넌트 받기
        btnAll = view.findViewById(R.id.btn_all);
        btnContest = view.findViewById(R.id.btn_contest);
        btnStudy = view.findViewById(R.id.btn_study);
        btnEtc = view.findViewById(R.id.btn_etc);

        onCommunityCategoryClickedChangeButtonDesign(communityCategory); // 색 입히기

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_all:
                        communityCategory = 0;
                        break;
                    case R.id.btn_contest:
                        communityCategory = 1;
                        break;
                    case R.id.btn_study:
                        communityCategory = 2;
                        break;
                    case R.id.btn_etc:
                        communityCategory = 3;
                        break;
                }
                onCommunityCategoryClickedChangeButtonDesign(communityCategory);
                setCommunityCategory(communityCategory, searchText.getText().toString());
            }
        };
        btnAll.setOnClickListener(onClickListener);
        btnContest.setOnClickListener(onClickListener);
        btnStudy.setOnClickListener(onClickListener);
        btnEtc.setOnClickListener(onClickListener);

        // 내 글 보기 버튼 눌렀을 때
        btnMyPosting = view.findViewById(R.id.btn_myposting);
        btnMyPosting.setOnClickListener(v -> {
            if (myPostingCheck) {
                myPostingCheck = false;
                String findingText = searchText.getText().toString();
                postingItems = new ArrayList<>(posting_list);
                setCommunityCategory(communityCategory, findingText);
                // 아이콘 변경
            } else {
                myPostingCheck = true;
                String findingText = searchText.getText().toString();
                setFindMyPosting(communityCategory, findingText);
            }
        });


        // 포스팅 하나 클릭했을떄, [포스팅]으로 가는 코드
        postingAdapter.setOnItemClicklistener(new PostingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("me", me);
                bundle.putParcelable("posting", postingItems.get(position));
                navController.navigate(R.id.action_posting_list_to_posting, bundle);
            }
        });

        btnMyPosting = view.findViewById(R.id.btn_myposting);
        btnWrite = view.findViewById(R.id.btn_write);

        btnWrite.setOnClickListener(v -> {
            Intent intent = new Intent(getContext().getApplicationContext(), PostingWriteActivity.class);
            intent.putExtra("me", me);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    // 카테고리 눌렀을때, 버튼 색변환
    public void onCommunityCategoryClickedChangeButtonDesign(int communityCategory) {
        List<Button> buttons = Arrays.asList(btnAll, btnContest, btnStudy, btnEtc);

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);

            if (i == communityCategory) {
                button.setBackgroundResource(R.drawable.btn_teal);
                button.setTextColor(getResources().getColor(R.color.white));
            } else {
                button.setBackgroundResource(R.drawable.btn_teal_off);
                button.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    // 커뮤니티의 카테고리에 따라서, 카테고리가 일치하는 포스팅정보만 화면에 보여지게 설정
    public void setCommunityCategory(int communityCategory, String searchText) {
        ArrayList<Posting> tempList = new ArrayList<>();
        for (Posting posting : postingItems) {
            boolean check = false;
            if (communityCategory == 0 && (searchText.equals("") || posting.title.contains(searchText))) {
                check = true;
            } else if (communityCategory == 1 && posting.category.equals("공모전") && (searchText.equals("") || posting.title.contains(searchText))) {
                check = true;
            } else if (communityCategory == 2 && posting.category.equals("스터디") && (searchText.equals("") || posting.title.contains(searchText))) {
                check = true;
            } else if (communityCategory == 3 && posting.category.equals("기타") && (searchText.equals("") || posting.title.contains(searchText))) {
                check = true;
            }
            if (check) {
                tempList.add(posting);
            }
        }
        postingAdapter.setPostingList(tempList);
    }

    // 내글 보기 버튼을 눌렀을 때 내 글만 화면에 보여지게 설정
    public void setFindMyPosting(int communityCategory, String searchText) {
        postingItems.clear();
        for (Posting posting : posting_list) {
            if (me.id == posting.personalId) {
                postingItems.add(posting);
            }
        }
        postingAdapter.setPostingList(postingItems);
        setCommunityCategory(communityCategory, searchText);
    }
}