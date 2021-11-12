package org.techtown.wanted_app_main.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.Activity.PostingWriteActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Connect;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostingFragment extends Fragment {

    // 이전 프래그먼트에서 Posting 객체 받기
    private static Posting posting;

    // 이글이 내 글인가
    boolean itsMe;

    // Connect 리사이 클러뷰 설정
    private RecyclerView rvBoardRequest;
    private PostingDetailAdapter postingDetailAdapter;
    private ArrayList<Connect> connectItems = new ArrayList<>();

    // 뷰
    private TextView postingDetailDate, postingDetailTitle, postingDetailTeam, postingDetailName, postingDetailContent, postingDetailCategory;
    private ImageView postingDetailImage, edit_img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posting = getArguments().getParcelable("posting");
        connectItems = (ArrayList<Connect>) posting.connects;
        System.out.println("출력: " + posting);
        itsMe = (posting.personalId == MainActivity.me.getId());
        postingDetailAdapter = new PostingDetailAdapter(itsMe, posting.postingId, MainActivity.me.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posting, container, false);
        hideBottomNavigation(true);

        // 버튼 가져오기
        Button request = view.findViewById(R.id.posting_detail_request);
        TextView recruitingStatus = view.findViewById(R.id.board_detail_recruiting);

        if (posting.checkRecruiting) {
            recruitingStatus.setText("모집중");
        } else{
            recruitingStatus.setText("모집완료");
        }

        // 내 글
        if (itsMe) {
            // 버튼 텍스트 설정
            if (posting.checkRecruiting) {
                request.setText("모집중");
            } else {
                request.setText("모집완료");
            }

            // 뷰 컴포넌트
            edit_img = view.findViewById(R.id.board_retouch);

            // 모집완료 or 모집중 버튼 클릭
            request.setOnClickListener(v -> {
                System.out.println("버튼 클릭함");
                // 서버 호출
                String url = "http://13.125.214.178:8080/posting/recruiting/" + posting.postingId;

                Map map = new HashMap();
                map.put("checkRecruiting", !posting.checkRecruiting);
                JSONObject params = new JSONObject(map);
                System.out.println("여기1");
                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject obj) {
                                System.out.println("여기2");
                                posting.checkRecruiting = !posting.checkRecruiting;
                                if (posting.checkRecruiting) {
                                    request.setText("모집중");
                                } else {
                                    request.setText("모집완료");
                                }
                                if (posting.checkRecruiting) {
                                    recruitingStatus.setText("모집중");
                                } else{
                                    recruitingStatus.setText("모집완료");
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("posting_fix_Error", error.getMessage());
                            }
                        }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=UTF-8";
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(objectRequest);
            });

            // 글 수정 버튼 뜨도록 설정, 클릭시 글 수정
            edit_img.setImageResource(R.drawable.ic_write);
            edit_img.setOnClickListener(v -> {
                Intent intent = new Intent(getContext().getApplicationContext(), PostingWriteActivity.class);
                intent.putExtra("me", MainActivity.me);
                intent.putExtra("posting", posting);
                startActivity(intent);
                getActivity().finish();
            });

        } else { // 내 글 아님
            boolean checkMyRecruiting = false;
            for (Connect connect : posting.connects) {
                if (connect.senderId == MainActivity.me.id) {
                    checkMyRecruiting = true;
                    break;
                }
            }

            // 텍스트 지정
            if (checkMyRecruiting) {
                request.setText("신청완료");
                request.setEnabled(false);
            } else {
                request.setText("참가신청");
                // 참가 신청 버튼 클릭, Connect 생성되야함
                request.setOnClickListener(v -> {
                    // 서버 호출
                    String url = "http://13.125.214.178:8080/connect/" + posting.postingId + "/" + MainActivity.me.id;

                    Map map = new HashMap();
                    JSONObject params = new JSONObject(map);

                    System.out.println("여기1");
                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject obj) {
                                    try {
                                        Long id = obj.getLong("id");
                                        Long senderId = obj.getLong("senderId");
                                        String nickname = obj.getString("nickname");
                                        String img = obj.getString("img");
                                        Boolean result = obj.getBoolean("result");
                                        Connect connect = new Connect(id, senderId, nickname, img, result);
                                        connectItems.add(connect);
                                        postingDetailAdapter.setItems(connectItems);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("커넥트 성공");
                                    request.setText("신청완료");
                                    request.setEnabled(false);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("실패");
                                    Log.e("posting_fix_Error", error.getMessage());
                                }
                            }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=UTF-8";
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(objectRequest);
                });
            }
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