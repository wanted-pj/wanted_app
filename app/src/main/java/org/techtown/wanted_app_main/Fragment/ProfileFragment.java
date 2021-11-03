package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private RecyclerView rvCareer;
    private CareerAdapter careerAdapter;
    private ArrayList<Career> careerItems;

    private RecyclerView rvTeam;
    private TeamAdapter teamAdapter;
    private ArrayList<Team> teamItems;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // 역량
        careerAdapter = new CareerAdapter();

        rvCareer = view.findViewById(R.id.recyclerView_career);
        rvCareer.setAdapter(careerAdapter);
        rvCareer.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        careerItems = new ArrayList<>();
        careerItems.add(new Career("백엔드 | SpringBoot, Django, Mysql"));
        careerItems.add(new Career("자격증 | 정보처리기사, SQLD"));
        careerItems.add(new Career("2021 사이버보안 AI·빅데이터 활용 경진대회 (최우수상)"));
        careerAdapter.setCareerList(careerItems);

        // 소속팀
        teamAdapter = new TeamAdapter();

        rvTeam = view.findViewById(R.id.recyclerView_team);
        rvTeam.setAdapter(teamAdapter);
        rvTeam.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        teamItems = new ArrayList<>();
        teamItems.add(new Team("원티드 피우다팀"));
        teamItems.add(new Team("신림 모각코"));
        teamAdapter.setTeamList(teamItems);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}