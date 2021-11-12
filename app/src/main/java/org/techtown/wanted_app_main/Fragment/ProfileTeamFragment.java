package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ProfileTeamFragment extends Fragment {

    private RecyclerView rvMember;
    private FriendMoreAdapter memberAdapter;
    private ArrayList<Friend> members;

    NavController navController;

    public ProfileTeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_team, container, false);
        hideBottomNavigation(true);

        memberAdapter = new FriendMoreAdapter();

        rvMember = view.findViewById(R.id.recyclerView_team_member);
        rvMember.setAdapter(memberAdapter);
        rvMember.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));

        members = new ArrayList<>();
        members.add(new Friend("시미즈", "홍익대학교", "컴퓨터공학과", "수원시", getResources().getIdentifier("@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        members.add(new Friend("리안", "덕성여자대학교", "컴퓨터공학과", "시흥시", getResources().getIdentifier("@drawable/profile_basic4", "drawable", getContext().getPackageName())));
        members.add(new Friend("다니엘", "경기대학교", "경영학과", "청주시", getResources().getIdentifier("@drawable/profile_basic5", "drawable", getContext().getPackageName())));
        members.add(new Friend("스콧", "수원대학교", "컴퓨터공학과", "시흥시", getResources().getIdentifier("@drawable/profile_basic6", "drawable", getContext().getPackageName())));
        memberAdapter.setFriendList(members);

        // 별점 매기는 페이지로 이동
        TextView goStar = view.findViewById(R.id.profile_team_go_star);
        goStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileTeamStarFragment dialog = new ProfileTeamStarFragment();
                dialog.show(getActivity().getSupportFragmentManager(),"star_dialog");
            }
        });

        return view;
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