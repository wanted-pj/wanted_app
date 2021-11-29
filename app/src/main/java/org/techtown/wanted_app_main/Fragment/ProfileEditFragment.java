package org.techtown.wanted_app_main.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.wanted_app_main.Adapter.SearchAdapter;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Dto.PersonalChatDto;
import org.techtown.wanted_app_main.database.OuterApi.OuterData;
import org.techtown.wanted_app_main.database.Personal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileEditFragment extends Fragment {

    NavController navController;
    public Personal personal;
    private Long id;
    // 학년
    Spinner spinner_grade;
    Integer change_grade;
    // 나이
    Spinner spinner_age;
    Integer change_age;
    // 성별
    RadioGroup gender_group;
    RadioButton gender_male;
    RadioButton gender_female;
    Integer change_gender;
    // 이미지
    CheckBox r1, r2, r3, r4, r5, r6;
    String change_img;
    // 역량
    EditText et_career;
    // 등록
    Button btn_edit_done;


    // 학교
    Button profile_edit_school_search;
    EditText profile_edit_school;
    // 학과
    Button profile_edit_major_search;
    EditText profile_edit_major;
    // 지역
    Button register_address_search;
    EditText register_address;
    // 다이얼로그 뷰 컴포넌트
    private RecyclerView recyclerView_search;
    private ArrayList<String> searchItem;
    private SearchAdapter searchAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getLong("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        hideBottomNavigation(true);
        // 성별 위젯 가져오기
        gender_male = view.findViewById(R.id.gender_male);
        gender_female = view.findViewById(R.id.gender_female);
        gender_group = view.findViewById(R.id.gender_group);
        gender_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == gender_male.getId()) {
                    change_gender = 0;
                } else if (checkedId == gender_female.getId()) {
                    change_gender = 1;
                }
            }
        });
        //아이디값 가져오기
        //성학년,나이 spinner
        spinner_grade = view.findViewById(R.id.profile_edit_grade_spinner);
        setSpinner("grade");
        spinner_age = view.findViewById(R.id.profile_edit_age_spinner);
        setSpinner("age");
        //이미지
        r1 = view.findViewById(R.id.iv1);
        r2 = view.findViewById(R.id.iv2);
        r3 = view.findViewById(R.id.iv3);
        r4 = view.findViewById(R.id.iv4);
        r5 = view.findViewById(R.id.iv5);
        r6 = view.findViewById(R.id.iv6);
        selectMyImage();
        //역량
        et_career = view.findViewById(R.id.profile_edit_career_et);
        // 학과, 학과, 지역
        profile_edit_school_search = view.findViewById(R.id.profile_edit_school_search);
        profile_edit_school = view.findViewById(R.id.profile_edit_school);
        // 학과
        profile_edit_major_search = view.findViewById(R.id.profile_edit_major_search);
        profile_edit_major = view.findViewById(R.id.profile_edit_major);
        // 지역
        register_address_search = view.findViewById(R.id.register_address_search);
        register_address = view.findViewById(R.id.register_address);

        Button.OnClickListener schoolMajorRegionListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list;
                switch (v.getId()) {
                    case R.id.profile_edit_school_search:
                        list = OuterData.schoolList;
                        showSearchDialog(list, "school");
                        break;
                    case R.id.profile_edit_major_search:
                        list = OuterData.majorList;
                        showSearchDialog(list, "major");
                        break;
                    case R.id.register_address_search:
                        list = OuterData.regionList;
                        showSearchDialog(list, "region");
                        break;

                }
            }
        };
        profile_edit_school_search.setOnClickListener(schoolMajorRegionListener);
        profile_edit_major_search.setOnClickListener(schoolMajorRegionListener);
        register_address_search.setOnClickListener(schoolMajorRegionListener);

        //버튼
        btn_edit_done = view.findViewById(R.id.profile_edit_done);

        //원래 저장된 정보 가져오기
        get_basic_info();

        //수정완료 버튼 클릭시 서버에 정보변경
        btn_edit_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put_edited_info();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    public void showSearchDialog(List<String> list, String how) {
        // 다이얼로그
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_register_search, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        // 다이얼로그 내의 리사이클러 뷰에 Adapter 삽입
        searchAdapter = new SearchAdapter();
        recyclerView_search = alertDialog.findViewById(R.id.recyclerView_search);
        recyclerView_search.setAdapter(searchAdapter);
        recyclerView_search.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        // 데이터 채워줌
        searchItem = new ArrayList<>(list);
        searchAdapter.setSearchList(searchItem);

        // Adapter 의 listener 지정 (값 선택했을 때)
        searchAdapter.setOnItemClicklistener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String returnValue = searchItem.get(position);
                if (how.equals("school")) {
                    profile_edit_school.setText(returnValue);
                } else if (how.equals("major")) {
                    profile_edit_major.setText(returnValue);
                } else if (how.equals("region")) {
                    register_address.setText(returnValue);
                }
                alertDialog.dismiss();
            }
        });

        // 탐색 버튼 누르면 검색됨
        EditText input = alertDialog.findViewById(R.id.search_edittext);
        Button btnSearchDB = alertDialog.findViewById(R.id.search_btn);
        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItem.clear();  //기존 리싸이클러뷰 초기화
                String findString = input.getText().toString();
                for (String search : list) {
                    if (search.contains(findString)) {
                        searchItem.add(search);
                    }
                }
                searchAdapter.setSearchList(searchItem);
            }
        });
    }

    public void get_basic_info() {
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
                Type listType = new TypeToken<Personal>() {
                }.getType();

                personal = gson.fromJson(changeString, listType);
                System.out.println(personal);

                //이미지 설정
                if (personal.img.matches(".*1")) {
                    r1.setChecked(true);
                } else if (personal.img.matches(".*2")) {
                    r2.setChecked(true);
                } else if (personal.img.matches(".*3")) {
                    r3.setChecked(true);
                } else if (personal.img.matches(".*4")) {
                    r4.setChecked(true);
                } else if (personal.img.matches(".*5")) {
                    r5.setChecked(true);
                } else {
                    r6.setChecked(true);
                }
                //성별설정
                if (personal.gender == 0) {
                    gender_male.setChecked(true);
                } else {
                    gender_female.setChecked(true);
                }
                //학년설정
                spinner_grade.setSelection(personal.grade - 1);
                //나이설정
                spinner_age.setSelection(personal.age - 19);
                //역량설정
                et_career.setText(personal.career);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
    }

    public void put_edited_info() {

        String url = "http://13.125.214.178:8080/personal/" + id;
        Map map = new HashMap();
        map.put("img", change_img);
        map.put("school", profile_edit_school.getText().toString());
        map.put("major", profile_edit_major.getText().toString());
        map.put("grade", change_grade);
        map.put("age", change_age);
        map.put("gender", change_gender);
        map.put("address", register_address.getText().toString());
        map.put("career", et_career.getText().toString());
        JSONObject jsonObject = new JSONObject(map);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // 한글깨짐 해결 코드
                        System.out.println("response 출력: " + response);
                        System.out.println("프로필 수정이 완료되었다.");
                        Bundle bundle = new Bundle();
                        bundle.putLong("profileId", id);
                        navController.navigate(R.id.action_profile_edit_to_profile, bundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("register_Error", error.getMessage());
                    }
                }) {
        };
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(objectRequest);
    }


    public void setSpinner(String topic) { //spinner 설정 함수

        //학년
        if (topic.equals("grade")) {
            ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.grade, android.R.layout.simple_spinner_dropdown_item);
            genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_grade.setAdapter(genderAdapter);

            spinner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    change_grade = position + 1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } //나이
        else if (topic.equals("age")) {
            ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.age, android.R.layout.simple_spinner_dropdown_item);
            genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_age.setAdapter(genderAdapter);

            spinner_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    change_age = position + 19;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

    }

    public void selectMyImage() {  //이미지 선택
        r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    r2.setChecked(false);
                    r3.setChecked(false);
                    r4.setChecked(false);
                    r5.setChecked(false);
                    r6.setChecked(false);
                    change_img = "profile_basic1";
                }
            }
        });
        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    r1.setChecked(false);
                    r3.setChecked(false);
                    r4.setChecked(false);
                    r5.setChecked(false);
                    r6.setChecked(false);
                    change_img = "profile_basic2";
                }
            }
        });
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    r1.setChecked(false);
                    r2.setChecked(false);
                    r4.setChecked(false);
                    r5.setChecked(false);
                    r6.setChecked(false);
                    change_img = "profile_basic3";
                }
            }
        });
        r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    r1.setChecked(false);
                    r2.setChecked(false);
                    r3.setChecked(false);
                    r5.setChecked(false);
                    r6.setChecked(false);
                    change_img = "profile_basic4";
                }
            }
        });
        r5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    r1.setChecked(false);
                    r2.setChecked(false);
                    r3.setChecked(false);
                    r4.setChecked(false);
                    r6.setChecked(false);
                    change_img = "profile_basic5";
                }
            }
        });
        r6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    r1.setChecked(false);
                    r2.setChecked(false);
                    r3.setChecked(false);
                    r4.setChecked(false);
                    r5.setChecked(false);
                    change_img = "profile_basic6";
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideBottomNavigation(false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void hideBottomNavigation(Boolean bool) {
        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottomNavigation);
        if (bool == true)
            bottomNavigation.setVisibility(View.GONE);
        else
            bottomNavigation.setVisibility(View.VISIBLE);
    }

}