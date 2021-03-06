package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.Adapter.FriendMoreAdapter;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Dto.PersonalDtoInTeam;
import org.techtown.wanted_app_main.database.Friend;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Team;

import java.util.ArrayList;

import static org.techtown.wanted_app_main.Activity.MainActivity.me;
import static org.techtown.wanted_app_main.Activity.MainActivity.setBtnNavIndex;
import static org.techtown.wanted_app_main.Activity.MainActivity.updateBottomMenu;

public class ProfileTeamFragment extends Fragment {
    //팀이름
    TextView team_title;

    //팀 멤버
    private RecyclerView rvMember;
    private FriendMoreAdapter memberAdapter;
    private ArrayList<Friend> members;

    // 이전 프래그먼트에서 team 객체 받기
    private static Team team;
    private ArrayList<PersonalDtoInTeam> memberInfo = new ArrayList<>();

    NavController navController;
    Boolean canevaluate = false;
    Personal personal;
    //팀해체 버튼
    Button btn_team_delete;

    public ProfileTeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        team = getArguments().getParcelable("team");
        personal = getArguments().getParcelable("personal");
        //connectItems = (ArrayList<Connect>) posting.connects;
        // System.out.println("출력: " + team.personals);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_team, container, false);
        hideBottomNavigation(true);

        //팀이름 설정
        team_title = view.findViewById(R.id.profile_team_title);
        team_title.setText(team.teamName);

        //팀멤버 설정
        memberAdapter = new FriendMoreAdapter();
        rvMember = view.findViewById(R.id.recyclerView_team_member);
        rvMember.setAdapter(memberAdapter);
        rvMember.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        members = new ArrayList<>();

        //내정보 담기
        //members.add(new Friend(personal.nickname,personal.school, personal.major, personal.address, getResources().getIdentifier(personal.img, "drawable", getContext().getPackageName())));
        //이전 프래그먼트에서 받은 team정보 활용
        memberInfo = new ArrayList<>(team.personals);
        for (PersonalDtoInTeam member : memberInfo) {
            members.add(new Friend(member.personalId, member.nickname, member.school, member.major, member.address, getResources().getIdentifier(member.img, "drawable", getContext().getPackageName())));
        }
        memberAdapter.setFriendList(members);


        // 팀해체 버튼 : 리더일시 버튼보이기-> 누르면 팀해체
        btn_team_delete = view.findViewById(R.id.profile_team_delete);
        if (team.leaderId == me.id) {
            btn_team_delete.setVisibility(View.VISIBLE);
        } else {
            btn_team_delete.setVisibility(View.GONE);
        }

        //팀해제하기 버튼 클릭하면 팀삭제
        btn_team_delete.setOnClickListener(v -> {
            deleteTeam();
        });

        // 별점 매기는 페이지로 이동
        TextView startext = view.findViewById(R.id.profile_team_startext);
        if (me.id == personal.id) {
            memberAdapter.setOnItemClicklistener(new FriendMoreAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ProfileTeamStarFragment dialog = new ProfileTeamStarFragment(memberInfo.get(position));
                    dialog.show(getActivity().getSupportFragmentManager(), "star_dialog");
                }
            });
        } else {
            startext.setVisibility(View.GONE);
            memberAdapter.setOnItemClicklistener(new FriendMoreAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    System.out.println("작동되면 안됨");
                }
            });
        }

        //친구 눌렀을 시 프로필페이지로 이동
        memberAdapter.setOnfriendClicklistener(new FriendMoreAdapter.OnfriendClickListener() {
            @Override
            public void onfriendClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putLong("profileId", memberInfo.get(position).personalId);
                navController.navigate(R.id.action_profile_team_to_profile, bundle);
                setBtnNavIndex(3);
                updateBottomMenu();
            }
        });
        return view;
    }

    public void deleteTeam() {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "http://13.125.214.178:8080/team/" + team.teamId;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                navController.navigate(R.id.action_profile_team_to_profile);
                setBtnNavIndex(3);
                updateBottomMenu();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
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