package org.techtown.wanted_app_main.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.wanted_app_main.R;
import java.util.ArrayList;

public class ProfileTeamStarFragment extends DialogFragment {

    private RecyclerView rvStar;
    private ProfileTeamStarAdapter starAdapter;
    private ArrayList<ProfileTeamStar> stars;

    public static final String TAG = "star_dialog";

    public ProfileTeamStarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_team_star, container, false);

        // 바텀내비게이션 숨기기
        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottomNavigation);
        bottomNavigation.setVisibility(View.GONE);

        starAdapter = new ProfileTeamStarAdapter();

        rvStar = view.findViewById(R.id.recyclerView_team_star);
        rvStar.setAdapter(starAdapter);
        rvStar.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        stars = new ArrayList<>();
        stars.add(new ProfileTeamStar("시미즈", getResources().getIdentifier("@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        stars.add(new ProfileTeamStar("리안", getResources().getIdentifier("@drawable/profile_basic4", "drawable", getContext().getPackageName())));
        stars.add(new ProfileTeamStar("다니엘", getResources().getIdentifier("@drawable/profile_basic5", "drawable", getContext().getPackageName())));
        stars.add(new ProfileTeamStar("스콧", getResources().getIdentifier("@drawable/profile_basic6", "drawable", getContext().getPackageName())));
        starAdapter.setProfileTeamStars(stars);

        Button btnCancel = view.findViewById(R.id.star_cancel);
        btnCancel.setOnClickListener(v -> dismiss());

        return view;
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
