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

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class FriendMoreFragment extends Fragment {

    private static final String argParam1 = "friendsCategory";
    private String friendsCategory;
    private static NavController navController;
    private RecyclerView recyclerViewSchool;
    private FriendMoreAdapter friendMoreAdapter;
    private ArrayList<Friend> friendItems;

    public FriendMoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // main에서 전달된 데이터 받기
        if (getArguments() != null) {
            friendsCategory = getArguments().getString(argParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_more, container, false);

        friendMoreAdapter = new FriendMoreAdapter();
        recyclerViewSchool = view.findViewById(R.id.recyclerView_friend_more);
        recyclerViewSchool.setAdapter(friendMoreAdapter);
        recyclerViewSchool.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),2));

        friendItems = new ArrayList<>();
        friendItems.add(new Friend("가비", "홍익대학교", "컴퓨터공학과", "수원시", getResources().getIdentifier( "@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("피넛", "홍익대학교", "경제학과", "성남시", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("시미즈", "서강대학교", "수학교육과", "김포시", getResources().getIdentifier( "@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("리안", "덕성여자대학교", "컴퓨터공학과", "하남시", getResources().getIdentifier( "@drawable/profile_basic4", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("다니엘", "경기대학교", "경영학과", "청주시", getResources().getIdentifier( "@drawable/profile_basic5", "drawable", getContext().getPackageName())));
        friendItems.add(new Friend("스콧", "수원대학교", "컴퓨터공학과", "시흥시", getResources().getIdentifier( "@drawable/profile_basic6", "drawable", getContext().getPackageName())));
        friendMoreAdapter.setFriendList(friendItems);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }
}