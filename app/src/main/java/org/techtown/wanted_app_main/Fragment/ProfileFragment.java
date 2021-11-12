package org.techtown.wanted_app_main.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.ServerRequest.GetPostingsRequest;
import org.techtown.wanted_app_main.ServerRequest.GetTeamsRequest;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;
import org.techtown.wanted_app_main.database.Team;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.techtown.wanted_app_main.Activity.MainActivity.setBtnNavIndex;
import static org.techtown.wanted_app_main.Activity.MainActivity.updateBottomMenu;

public class ProfileFragment extends Fragment {

    NavController navController;

    //소속팀
    public Personal personal;
    public List<Team> team_list = new ArrayList<>();
    private RecyclerView rvTeam;
    private ProfileTeamAdapter profileTeamAdapter;
    private ArrayList<ProfileTeam> profileTeamItems; //프로필페이지에 팀item
    private ArrayList<Team> teamInfo=new ArrayList<>(); //팀상세페이지에 넘길때

    //아이디
    private Long getid;
    private Long thisid;

    //기본정보
    ImageView img, img_message;
    TextView nick;
    EditText school;
    EditText major;
    EditText address;
    EditText grade;
    EditText age;
    EditText gender;
    TextView career;

    //별점
    Double getearnest;
    Double getteamwork;
    Double getcontribution;

    public Dialog dialog;
    TextView my_earnest;
    TextView my_teamwork;
    TextView my_contribution;
    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView btn_edit = view.findViewById(R.id.edit_btn);

        //기본설정:내아이디
       thisid = MainActivity.me.id;

        Bundle bundle = getArguments();
        if(bundle != null) {
            //이전 fragment에서 prifilefragment로 들어온 id값
            getid=getArguments().getLong("id");
            if(getid!=null){ //bundle로 넘긴 id값이 있을경우=타인일 경우, thisid 변경
                thisid=getid;
                btn_edit.setVisibility(View.GONE);}
        }

        //기본정보
        img = view.findViewById(R.id.pf_img);
        img_message = view.findViewById(R.id.message);
        nick = view.findViewById(R.id.pf_nickname);
        school = view.findViewById(R.id.pf_school);
        major = view.findViewById(R.id.pf_major);
        address = view.findViewById(R.id.pf_address);
        grade = view.findViewById(R.id.pf_grade);
        age = view.findViewById(R.id.pf_age);
        gender = view.findViewById(R.id.pf_gender);
        career = view.findViewById(R.id.pf_career);

        //기본정보 가져오기
        getBasicInfo();

        //프로필 편집 바로가기
        btn_edit.setOnClickListener( v-> {

            Bundle bundle1 = new Bundle();
            bundle1.putString("btnGoedit", "test");

            navController.navigate(R.id.action_profile_to_profile_edit, bundle1);
        });

        //쪽지하기
        if(MainActivity.me.id != thisid) {
            img_message.setOnClickListener(v->{
                String url = "http://13.125.214.178:8080/room?senderId=" + MainActivity.me.id + "&receiverId=" + thisid;

                Map map = new HashMap();

                JSONObject params = new JSONObject(map);

                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject obj) {
                                System.out.println("success");
                                // 해당 채팅방으로 화면 이동
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {


                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=UTF-8";
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
                queue.add(objectRequest);
            });
        }

        //내가 속한 팀이름 가져오기
        getMyTeam();

        //팀 어탭터 설정
        profileTeamAdapter = new ProfileTeamAdapter();
        profileTeamItems = new ArrayList<>();
        rvTeam = view.findViewById(R.id.recyclerView_team);
        rvTeam.setAdapter(profileTeamAdapter);
        rvTeam.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        //팀상세페이지로 이동
        profileTeamAdapter.setOnItemClicklistener(new ProfileTeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putLong("me",thisid);
                bundle.putParcelable("team", teamInfo.get(position));
                bundle.putParcelable("personal",personal);
                setBtnNavIndex(1);
                updateBottomMenu();
                navController.navigate(R.id.action_profile_to_profile_team, bundle);
            }
        });


        // 별점 팝업
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_star);
        LinearLayout btnStar = view.findViewById(R.id.pf_star);
        btnStar.setOnClickListener(v -> {
            dialog.show();
            my_earnest = dialog.findViewById(R.id.my_earnest);
            my_teamwork = dialog.findViewById(R.id.my_teamwork);
            my_contribution = dialog.findViewById(R.id.my_contribution);
            if(personal.evaluation!=null){
                getearnest = personal.evaluation.earnest;
                my_earnest.setText(getearnest.toString());

                getteamwork = personal.evaluation.teamwork;
                my_teamwork.setText(getteamwork.toString());

                getcontribution = personal.evaluation.contribution;
                my_contribution.setText(getcontribution.toString());
            } else {
                my_earnest.setText("0.0");
                my_teamwork.setText("0.0");
                my_contribution.setText("0.0");
            }
        });
        //평균 계산해서 별그림바꾸기
        //Double average = (getearnest+getcontribution+getteamwork)/personal.evaluation.count;
        //int star_value = average.intValue();
      /*  switch (star_value){
            case 1:

            case 2:

                case3:

                case4

                        case5
        }*/
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }


    public void getMyTeam(){ //소속팀 서버에서 정보가져오기

        //서버 호출
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        // 모든 team 조회
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
                Type listType = new TypeToken<ArrayList<Team>>() {
                }.getType();

                List<Team> temp = gson.fromJson(changeString, listType);
                team_list = new ArrayList<>(temp);
                for (Team team :  team_list) {
                    profileTeamItems.add(new ProfileTeam(team.teamName));
                    teamInfo.add(team);
                }
                profileTeamAdapter.setProfileTeamList(profileTeamItems);
            }
        };
        requestQueue.add(new GetTeamsRequest(postingResponseListener,thisid));
    }

    public void getBasicInfo(){ //기본정보 서버에서 가져오기
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "http://13.125.214.178:8080/personal" + "/" + thisid;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                Type listType = new TypeToken<Personal>() {}.getType();

                personal = gson.fromJson(changeString, listType);

                int image = getResources().getIdentifier(personal.img , "drawable", getContext().getPackageName());
                img.setImageResource(image);
                nick.setText(personal.nickname);
                school.setText(personal.school);
                major.setText(personal.major);
                address.setText(personal.address);
                grade.setText(String.valueOf(personal.grade));
                age.setText(String.valueOf(personal.age));
                if(personal.gender == 0) {
                    gender.setText("남");
                } else if(personal.gender == 1) {
                    gender.setText("여");
                }
                career.setText(personal.career);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}