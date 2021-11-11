package org.techtown.wanted_app_main.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment {

    NavController navController;

   // private RecyclerView rvCareer;
   // private ProfileCareerAdapter profileCareerAdapter;
    //private ArrayList<ProfileCareer> profileCareerItems;

    private RecyclerView rvTeam;
    private ProfileTeamAdapter profileTeamAdapter;
    private ArrayList<ProfileTeam> profileTeamItems;
    Boolean done = false;
    public Personal personal;
    public Dialog dialog;

    public String string_career;

    private int id;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle bundle = getArguments();
        //id = bundle.getInt("id");
        id = 1;
        Log.d("test_MainFragment", String.valueOf(id));

        // 역량
       // profileCareerAdapter = new ProfileCareerAdapter();

       // rvCareer = view.findViewById(R.id.recyclerView_career);
        //rvCareer.setAdapter(profileCareerAdapter);
       // rvCareer.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

       // profileCareerItems = new ArrayList<>();
        //profileCareerItems.add(new ProfileCareer("백엔드 | SpringBoot, Django, Mysql"));
       // profileCareerItems.add(new ProfileCareer("자격증 | 정보처리기사, SQLD"));
        //profileCareerItems.add(new ProfileCareer("2021 사이버보안 AI·빅데이터 활용 경진대회 (최우수상)"));
        //profileCareerAdapter.setProfileCareerList(profileCareerItems);

        // 소속팀
        profileTeamAdapter = new ProfileTeamAdapter();

        rvTeam = view.findViewById(R.id.recyclerView_team);
        rvTeam.setAdapter(profileTeamAdapter);
        rvTeam.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        profileTeamItems = new ArrayList<>();
        profileTeamItems.add(new ProfileTeam("원티드 피우다팀"));
        profileTeamItems.add(new ProfileTeam("신림 모각코"));
        profileTeamAdapter.setProfileTeamList(profileTeamItems);

        profileTeamAdapter.setOnItemClicklistener(new ProfileTeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("test", "testmessage");
                navController.navigate(R.id.action_profile_to_profile_team, bundle);
            }
        });

        //기본정보
        ImageView img = view.findViewById(R.id.pf_img);
        TextView nick = view.findViewById(R.id.pf_nickname);
        EditText school = view.findViewById(R.id.pf_school);
        EditText major = view.findViewById(R.id.pf_major);
        EditText address = view.findViewById(R.id.pf_address);
        EditText grade = view.findViewById(R.id.pf_grade);
        EditText age = view.findViewById(R.id.pf_age);
        EditText gender = view.findViewById(R.id.pf_gender);
        TextView career = view.findViewById(R.id.pf_career);

        //프로필 편집
        ImageView btn_edit = view.findViewById(R.id.edit_btn);
        btn_edit.setOnClickListener( v-> {

            Bundle bundle1 = new Bundle();
            bundle1.putString("btnGoedit", "test");

            navController.navigate(R.id.action_profile_to_profile_edit, bundle);
        });

        // 별점 팝업
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_star);

        LinearLayout btnStar = view.findViewById(R.id.pf_star);
        btnStar.setOnClickListener(v -> {
            dialog.show();
        });

        //서버 호출
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "http://13.125.214.178:8080/personal" + "/" + id;

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}