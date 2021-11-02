package org.techtown.wanted_app_main.Fragment;

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
import android.widget.TextView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ShowFriendsFragment extends Fragment {

    private static final String argParam1 = "friendsCategory";
    private String friendsCategory;
    private static NavController navController;
    private RecyclerView recyclerViewSchool;
    private Detail_FriendAdapter friendAdapter;
    private ArrayList<Detail_Friend> friendItems;

    public ShowFriendsFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_friends, container, false);
        //TextView viewById = view.findViewById(R.id.category_sample);
       // viewById.setText(friendsCategory);

        friendAdapter = new Detail_FriendAdapter();
        recyclerViewSchool = view.findViewById(R.id.recyclerView_sch);
        recyclerViewSchool.setAdapter(friendAdapter);
        recyclerViewSchool.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        friendItems = new ArrayList<>();
        friendItems.add(new Detail_Friend("가비", "홍익대학교", "컴퓨터공학과","4학년","여", getResources().getIdentifier( "@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        friendItems.add(new Detail_Friend("피넛", "홍익대학교", "경제학과","3학년","여", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        friendItems.add(new Detail_Friend("시미즈", "서강대학교", "수학교육과","2학년","남", getResources().getIdentifier( "@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        friendAdapter.setFriendList(friendItems);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }
}