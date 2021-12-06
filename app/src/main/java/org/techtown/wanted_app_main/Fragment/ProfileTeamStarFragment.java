package org.techtown.wanted_app_main.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;
import org.techtown.wanted_app_main.Activity.Login.LoginActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Dto.PersonalDtoInTeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileTeamStarFragment extends DialogFragment {

    private PersonalDtoInTeam memberInfo;
    public TextView tv_name;
    public ImageView iv;
    public RadioGroup attend_rg;
    public RadioGroup teamwork_rg;
    public  RadioGroup contribution_rg;
    public int value_earnest;
    public int value_teamwork;
    public int value_contribution;
    public static final String TAG = "star_dialog";

    public ProfileTeamStarFragment(PersonalDtoInTeam memberInfo) {
        this.memberInfo=memberInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_team_star, container, false);


        // 바텀내비게이션 숨기기
        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottomNavigation);
        bottomNavigation.setVisibility(View.GONE);

/*
        //이전 프래그먼트에서 멤버 정보 가져오기
        for (PersonalDtoInTeam member : memberInfo) {
            stars.add(new ProfileTeamStar(member.nickname, getResources().getIdentifier(member.img, "drawable", getContext().getPackageName())));
            memberId.add(member.personalId);
        }
        starAdapter.setProfileTeamStars(stars);

*/

        tv_name = view.findViewById(R.id.star_name);
        iv = view.findViewById(R.id.star_img);
        attend_rg = view.findViewById(R.id.attend_rg);
        tv_name.setText(memberInfo.nickname);
        iv.setImageResource(getResources().getIdentifier(memberInfo.img, "drawable", getContext().getPackageName()));
        attend_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.attend_btn1:
                        value_earnest=1;
                        break;
                    case R.id.attend_btn2:
                        value_earnest=2;
                        break;
                    case R.id.attend_btn3:
                        value_earnest=3;
                        break;
                    case R.id.attend_btn4:
                        value_earnest=4;
                        break;
                    case R.id.attend_btn5:
                        value_earnest=5;
                        break;
                }
            }
        });

        teamwork_rg = view.findViewById(R.id.teamwork_rg);

        teamwork_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.teamwork_btn1:
                        value_teamwork=1;
                        break;
                    case R.id.teamwork_btn2:
                        value_teamwork=2;
                        break;
                    case R.id.teamwork_btn3:
                        value_teamwork=3;
                        break;
                    case R.id.teamwork_btn4:
                        value_teamwork=4;
                        break;
                    case R.id.teamwork_btn5:
                        value_teamwork=5;
                        break;
                }
            }
        });

        contribution_rg = view.findViewById(R.id.contriute_rg);

        contribution_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.contriute_btn1:
                        value_contribution=1;
                        break;
                    case R.id.contriute_btn2:
                        value_contribution=2;
                        break;
                    case R.id.contriute_btn3:
                        value_contribution=3;
                        break;
                    case R.id.contriute_btn4:
                        value_contribution=4;
                        break;
                    case R.id.contriute_btn5:
                        value_contribution=5;
                        break;
                }
            }
        });


        //완료버튼
        Button btnComplete = view.findViewById(R.id.star_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put_evaluation();
            }
        });
       //취소버튼
        Button btnCancel = view.findViewById(R.id.star_cancel);
        btnCancel.setOnClickListener(v -> dismiss());

        return view;
    }

    public void put_evaluation(){
      String url = "http://13.125.214.178:8080/evaluation/"+memberInfo.personalId;
        Map map = new HashMap();
        map.put("earnest", Double.valueOf(value_earnest));
        map.put("teamwork", Double.valueOf(value_teamwork));
        map.put("contribution", Double.valueOf(value_contribution));

        JSONObject params = new JSONObject(map);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {

                        dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("register_Error", error.getMessage());
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF-8";
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(objectRequest);
    }


    @Override
    public void onResume() {
        super.onResume();

        // 팝업 사이즈 조정
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        getDialog().getWindow().setLayout(width, height);

        // 팝업 배경 지정
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
