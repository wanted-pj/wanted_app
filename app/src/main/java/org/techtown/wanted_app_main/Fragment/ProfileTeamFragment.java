package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.wanted_app_main.Activity.MainActivity;
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}