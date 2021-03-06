package org.techtown.wanted_app_main.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.DrawableContainer;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
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

import org.json.JSONObject;
import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.Adapter.ProfileTeamAdapter;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.ServerRequest.GetTeamsRequest;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.ProfileTeam;
import org.techtown.wanted_app_main.database.Team;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.techtown.wanted_app_main.Activity.MainActivity.setBtnNavIndex;
import static org.techtown.wanted_app_main.Activity.MainActivity.updateBottomMenu;

public class ProfileFragment extends Fragment {

    NavController navController;

    //?????????
    public Personal personal;
    public List<Team> team_list = new ArrayList<>();
    private RecyclerView rvTeam;
    private ProfileTeamAdapter profileTeamAdapter;
    private ArrayList<ProfileTeam> profileTeamItems; //????????????????????? ???item
    private ArrayList<Team> teamInfo = new ArrayList<>(); //????????????????????? ?????????

    //?????????
    private Long profileId;
    private Long loginId;

    // ????????? ??????
    private boolean itsMyProfile;

    //????????????
    ImageView img, img_message, btn_edit;
    TextView nick, profile_title;
    EditText school;
    EditText major;
    EditText address;
    EditText grade;
    EditText age;
    EditText gender;
    TextView career;

    //??????
    Double getearnest;
    Double getteamwork;
    Double getcontribution;
    ImageView pf_star1;
    ImageView pf_star2;
    ImageView pf_star3;
    ImageView pf_star4;
    ImageView pf_star5;

    public Dialog dialog;
    TextView my_earnest;
    TextView my_teamwork;
    TextView my_contribution;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //????????????:????????????
        loginId = MainActivity.me.id;

        Bundle bundle = getArguments();
        if (bundle != null) { // ?????? ????????? ??????
            //?????? fragment?????? prifilefragment??? ????????? id???
            profileId = getArguments().getLong("profileId");
            if (profileId != null) { //bundle??? ?????? id?????? ????????????=????????? ??????, profileId ??????
                itsMyProfile = false;
            }
        } else { // ??? ????????? ??????
            profileId = loginId;
            itsMyProfile = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //???????????? ????????????
        getBasicInfo();

        btn_edit = view.findViewById(R.id.edit_btn);
        img_message = view.findViewById(R.id.message);
        profile_title = view.findViewById(R.id.profile_title);

        if (!itsMyProfile) { // ??????
            btn_edit.setVisibility(View.GONE); // ?????? ?????????
            img_message.setVisibility(View.VISIBLE); // ?????? ??????
        } else { // ??? ??????
            btn_edit.setVisibility(View.VISIBLE);
            img_message.setVisibility(View.GONE);
            profile_title.setText("?????? ?????????");
        }

        //????????????
        img = view.findViewById(R.id.pf_img);
        nick = view.findViewById(R.id.pf_nickname);
        school = view.findViewById(R.id.pf_school);
        major = view.findViewById(R.id.pf_major);
        address = view.findViewById(R.id.pf_address);
        grade = view.findViewById(R.id.pf_grade);
        age = view.findViewById(R.id.pf_age);
        gender = view.findViewById(R.id.pf_gender);
        career = view.findViewById(R.id.pf_career);

        //????????? ?????? ????????????
        btn_edit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id", loginId);
            navController.navigate(R.id.action_profile_to_profile_edit, bundle);
        });

        //????????????
        if (!itsMyProfile) {
            img_message.setOnClickListener(v -> {
                String url = "http://13.125.214.178:8080/room?senderId=" + MainActivity.me.id + "&receiverId=" + profileId;

                Map map = new HashMap();

                JSONObject params = new JSONObject(map);

                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject obj) {
                                System.out.println("success");
                                // ?????? ??????????????? ?????? ??????
                                // Bundle bundle = new Bundle();
                                // bundle.putParcelable("participants", participants.get(position));
                                // navController.navigate(R.id.action_chat_to_chat, bundle);
                                // ?????? ?????? ???????????? ??????
                                setBtnNavIndex(1);
                                updateBottomMenu();
                                navController.navigate(R.id.action_global_chatListFragment);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // ?????? ??????????????? ?????? ??????
                                // Bundle bundle = new Bundle();
                                // bundle.putParcelable("participants", participants.get(position));
                                // navController.navigate(R.id.action_chat_to_chat, bundle);
                                setBtnNavIndex(2);
                                updateBottomMenu();
                                navController.navigate(R.id.action_global_chatListFragment);
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

        //?????? ?????? ????????? ????????????
        getMyTeam();

        //??? ????????? ??????
        profileTeamAdapter = new ProfileTeamAdapter();
        profileTeamItems = new ArrayList<>();
        rvTeam = view.findViewById(R.id.recyclerView_team);
        rvTeam.setAdapter(profileTeamAdapter);
        rvTeam.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));

        //????????????????????? ??????
        profileTeamAdapter.setOnItemClicklistener(new ProfileTeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("team", teamInfo.get(position));
                bundle.putParcelable("personal", personal);
                setBtnNavIndex(1);
                updateBottomMenu();
                navController.navigate(R.id.action_profile_to_profile_team, bundle);
            }
        });

        //???????????????
        pf_star1 = view.findViewById(R.id.pf_star1);
        pf_star2 = view.findViewById(R.id.pf_star2);
        pf_star3 = view.findViewById(R.id.pf_star3);
        pf_star4 = view.findViewById(R.id.pf_star4);
        pf_star5 = view.findViewById(R.id.pf_star5);


        // ?????? ?????????
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
            my_earnest.setText(String.format("%.2f", getearnest));
            my_teamwork.setText(String.format("%.2f", getteamwork));
            my_contribution.setText(String.format("%.2f", getcontribution));
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }


    public void getMyTeam() { //????????? ???????????? ??????????????????

        //?????? ??????
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        // ?????? team ??????
        Response.Listener<String> postingResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // ???????????? ?????? ??????
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
                for (Team team : team_list) {
                    profileTeamItems.add(new ProfileTeam(team.teamName));
                    teamInfo.add(team);
                }
                profileTeamAdapter.setProfileTeamList(profileTeamItems);
            }
        };
        requestQueue.add(new GetTeamsRequest(postingResponseListener, profileId));
    }

    public void getBasicInfo() { //???????????? ???????????? ????????????
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "http://13.125.214.178:8080/personal" + "/" + profileId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // ???????????? ?????? ??????
                String changeString = new String();
                try {
                    changeString = new String(response.getBytes("8859_1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<Personal>() {
                }.getType();

                personal = gson.fromJson(changeString, listType);

                int image = getResources().getIdentifier(personal.img, "drawable", getContext().getPackageName());
                img.setImageResource(image);
                nick.setText(personal.nickname);
                school.setText(personal.school);
                major.setText(personal.major);
                address.setText(personal.address);
                grade.setText(String.valueOf(personal.grade));
                age.setText(String.valueOf(personal.age));
                if (personal.gender == 0) {
                    gender.setText("???");
                } else if (personal.gender == 1) {
                    gender.setText("???");
                }
                career.setText(personal.career);
                if (!itsMyProfile) { // ??????
                    profile_title.setText(personal.nickname + "?????? ?????????");
                }
                if (personal.evaluation != null) {
                    getearnest = personal.evaluation.earnest;
                    getteamwork = personal.evaluation.teamwork;
                    getcontribution = personal.evaluation.contribution;
                    //?????? ???????????? ??????????????????
                    changeStarImage();

                } else {
                    getearnest = 0.0;
                    getteamwork = 0.0;
                    getcontribution = 0.0;
                    //?????? ???????????? ??????????????????
                    changeStarImage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void changeStarImage() { //??????????????? ??????

        Double average = (getearnest + getteamwork + getcontribution) / 3;
        int star_value = average.intValue();

        if (star_value == 0) {
            pf_star1.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star2.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star3.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star4.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star5.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
        } else if (star_value == 1) {
            pf_star1.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star2.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star3.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star4.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star5.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
        } else if (star_value == 2) {
            pf_star1.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star2.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star3.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star4.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star5.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
        } else if (star_value == 3) {
            pf_star1.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star2.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star3.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star4.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
            pf_star5.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
        } else if (star_value == 4) {
            pf_star1.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star2.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star3.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star4.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star5.setImageResource(getResources().getIdentifier("profile_star_gray", "drawable", getContext().getPackageName()));
        } else {
            pf_star1.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star2.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star3.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star4.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
            pf_star5.setImageResource(getResources().getIdentifier("profile_star", "drawable", getContext().getPackageName()));
        }

    }
}