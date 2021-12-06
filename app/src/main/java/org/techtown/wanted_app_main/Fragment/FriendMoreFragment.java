package org.techtown.wanted_app_main.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.Adapter.FriendAdapter;
import org.techtown.wanted_app_main.Adapter.FriendMoreAdapter;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Friend;
import org.techtown.wanted_app_main.database.OuterApi.OuterData;
import org.techtown.wanted_app_main.database.Personal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.techtown.wanted_app_main.Activity.MainActivity.setBtnNavIndex;
import static org.techtown.wanted_app_main.Activity.MainActivity.updateBottomMenu;

public class FriendMoreFragment extends Fragment {

    private static final String argParam1 = "friendsCategory";
    private int friendsCategory;
    private static NavController navController;
    private RecyclerView recyclerViewSchool;
    private FriendMoreAdapter friendMoreAdapter;

    public ArrayList<Personal> personal_list;
    private ArrayList<Friend> allFriends, filteredFriends;
    private ArrayList<Long> friendIds = new ArrayList<>();

    private Button btnSchool, btnMajor, btnAddress;
    private String spinnerString;
    private Spinner friendsSpinner;

    // 임시데이터 -> 내 계정 정보 오면 지워도됨
    private String mySchool;
    private String myMajor;
    private String myAddress;

    public FriendMoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // main에서 전달된 데이터 받기
        if (getArguments() != null) {
            friendsCategory = getArguments().getInt(argParam1);
            personal_list = getArguments().getParcelableArrayList("personal_list");
            allFriends = new ArrayList<>();
            for (Personal personal : personal_list) {
                if (personal.id == MainActivity.me.id) {
                    continue;
                }
                int image = MainActivity.mainActivity.getResources().getIdentifier(personal.img, "drawable", MainActivity.mainActivity.getPackageName());
                allFriends.add(new Friend(personal.id, personal.nickname, personal.school, personal.major, personal.address, image));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_more, container, false);

        // 방문한 나 자신 예시 데이터
        mySchool = MainActivity.me.school;
        myMajor = MainActivity.me.major;
        myAddress = MainActivity.me.address;

        // 클릭되어진 친구버튼 설정

        // 초기 카테고리 버튼 설정
        btnSchool = view.findViewById(R.id.btn_school_more);
        btnMajor = view.findViewById(R.id.btn_major_more);
        btnAddress = view.findViewById(R.id.btn_address_more);
        onCategoryClickedChangeButtonDesign(friendsCategory);

        // 카테고리 버튼 클릭 이벤트 설정
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_school_more:
                        friendsCategory = 0;
                        break;
                    case R.id.btn_major_more:
                        friendsCategory = 1;
                        break;
                    case R.id.btn_address_more:
                        friendsCategory = 2;
                        break;
                }
                onCategoryClickedChangeButtonDesign(friendsCategory); // 카테고리 버튼 색 설정
                onCategoryClickedFillSpinnerData(friendsCategory); // 카테고리에 따른 스피너 데이터 설정
            }
        };
        btnSchool.setOnClickListener(onClickListener);
        btnMajor.setOnClickListener(onClickListener);
        btnAddress.setOnClickListener(onClickListener);

        // 스피너 초기값 설정
        friendsSpinner = view.findViewById(R.id.spinner);
        onCategoryClickedFillSpinnerData(friendsCategory); // 값 채워넣고

        // 스피너 이벤트 반응 -> 선택시 필터링
        friendsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerString = friendsSpinner.getSelectedItem().toString();
                List<Friend> tempList = null;
                switch (friendsCategory) { // 학교 or 전공 or 지역
                    case 0:
                        tempList = allFriends.stream()
                                .filter(f -> spinnerString.equals(f.getSchool()))
                                .collect(Collectors.toList());
                        break;
                    case 1:
                        tempList = allFriends.stream()
                                .filter(f -> spinnerString.equals(f.getMajor()))
                                .collect(Collectors.toList());
                        break;
                    case 2:
                        tempList = allFriends.stream()
                                .filter(f -> spinnerString.equals(f.getAddress()))
                                .collect(Collectors.toList());
                        break;
                    default:
                        System.out.println("==== 스피너 오류 ====");
                }
                filteredFriends = (ArrayList<Friend>) tempList;
                friendMoreAdapter.setFriendList(filteredFriends);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                spinnerString = "학교친구";
                System.out.println("아무것도 선택안됨");
            }
        });

        // 친구 보여주는 RecyclerView, Adapter 설정
        friendMoreAdapter = new FriendMoreAdapter();
        recyclerViewSchool = view.findViewById(R.id.recyclerView_friend_more);
        recyclerViewSchool.setAdapter(friendMoreAdapter);
        recyclerViewSchool.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        friendMoreAdapter.setFriendList(allFriends);

        //친구 눌렀을 시 프로필페이지로 이동
        friendMoreAdapter.setOnfriendClicklistener(new FriendMoreAdapter.OnfriendClickListener() {
            @Override
            public void onfriendClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putLong("profileId", friendMoreAdapter.friendList.get(position).id);
                setBtnNavIndex(3);
                updateBottomMenu();
                navController.navigate(R.id.action_friend_to_profile, bundle);
            }
        });

        return view;
    }

    // 카테고리 버튼 클릭시 함수
    public void onCategoryClickedChangeButtonDesign(int index) {
        List<Button> buttons = Arrays.asList(btnSchool, btnMajor, btnAddress);

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);

            if (i == index) {
                button.setBackgroundResource(R.drawable.btn_teal);
                button.setTextColor(getResources().getColor(R.color.white));
            } else {
                button.setBackgroundResource(R.drawable.btn_teal_off);
                button.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    public void onCategoryClickedFillSpinnerData(int index) {
        ArrayList<String> spinnerArrayList = null;

        switch (index) {
            case 0:
                spinnerArrayList = (ArrayList<String>) OuterData.schoolList;
                break;
            case 1:
                spinnerArrayList = (ArrayList<String>) OuterData.majorList;
                break;
            case 2:
                spinnerArrayList = (ArrayList<String>) OuterData.regionList;
                break;
        }

        // 스피너 어댑터 설정(재설정)
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArrayList); // 데이터 넣기
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // 드롭다운 클릭시 선택창 방식
        friendsSpinner.setAdapter(spinnerAdapter);
        spinnerInit();
    }

    // 카테 고리 클릭시 자신에게 맞는 스피너 데이터 클릭되어 있도록
    public void spinnerInit() {
        int index = 0;
        switch (friendsCategory) {
            case 0:
                index = OuterData.schoolList.indexOf(mySchool);
                break;
            case 1:
                index = OuterData.majorList.indexOf(myMajor);
                break;
            case 2:
                index = OuterData.regionList.indexOf(myAddress);
                break;
        }
        friendsSpinner.setSelection(index);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}