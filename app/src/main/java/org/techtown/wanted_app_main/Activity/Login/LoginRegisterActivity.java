package org.techtown.wanted_app_main.Activity.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.techtown.wanted_app_main.Adapter.SearchAdapter;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.OuterApi.OuterData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginRegisterActivity extends AppCompatActivity {

    //성별
    RadioGroup radio_gender;
    RadioButton gender_male;
    RadioButton gender_female;
    Integer value_gender;
    //학년
    Spinner spinner_grade;
    Integer value_grade;
    //나이
    Spinner spinner_age;
    Integer value_age;
    //비밀번호+
    EditText et_pwd, et_pwdcheck;
    TextView pwd_checkmes;
    //아이디
    EditText et_id;
    Button id_dupcheck;
    TextView id_checkmes;
    //이미지
    CheckBox r1, r2, r3, r4, r5, r6;
    String imageName;
    //닉네임
    EditText et_nickname;
    TextView nickname_checkmes;
    Button nickname_dupcheck;
    //역량
    EditText et_career;
    //등록
    Button post_person;
    //Dialog
    Dialog dialog;
    //학교
    EditText et_school;
    //학과
    EditText et_major;
    //지역
    EditText et_region;

    // 검색
    Button btnSchoolSearch, btnMajorSearch, btnRegionSearch;
    private RecyclerView rvSearch;
    private SearchAdapter searchAdapter;
    private ArrayList<String> searchItem;

    boolean idValidation = false;
    boolean pwValidation = false;
    boolean nicknameValidation = false;


    String returnValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        //성별
        radio_gender = findViewById(R.id.gender_group);
        gender_male = findViewById(R.id.gender_male);
        gender_female = findViewById(R.id.gender_female);
        radio_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == gender_male.getId()) {
                    value_gender = 0;
                } else if (checkedId == gender_female.getId()) {
                    value_gender = 1;
                }
            }
        });

        //학년, 나이 spinner 지정
        spinner_grade = findViewById(R.id.register_grade_spinner);
        setSpinner("grade");
        spinner_age = findViewById(R.id.register_age_spinner);
        setSpinner("age");

        // 학교
        et_school = findViewById(R.id.register_school);
        et_major = findViewById(R.id.register_major);
        et_region = findViewById(R.id.register_address);

        btnSchoolSearch = findViewById(R.id.register_school_search);
        btnMajorSearch = findViewById(R.id.register_major_search);
        btnRegionSearch = findViewById(R.id.register_address_search);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list;
                switch (v.getId()) {
                    case R.id.register_school_search:
                        list = OuterData.schoolList;
                        showSearchDialog(list, "school");
                        break;
                    case R.id.register_major_search:
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

        btnSchoolSearch.setOnClickListener(onClickListener);
        btnMajorSearch.setOnClickListener(onClickListener);
        btnRegionSearch.setOnClickListener(onClickListener);

        //id
        et_id = findViewById(R.id.register_id);
        id_checkmes = findViewById(R.id.register_id_check_txt);
        id_dupcheck = findViewById(R.id.register_id_check_btn);
        id_dupcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_id.getText().toString().equals("")) {
                    id_checkmes.setText("아이디를 입력해주세요.");
                } else {
                    checkId();     //id중복확인
                }
            }
        });


        //비밀번호
        et_pwd = findViewById(R.id.register_pwd);
        et_pwdcheck = findViewById(R.id.register_pwdcheck);
        pwd_checkmes = findViewById(R.id.register_pwdcheck_txt);

        et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_pwdcheck.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        checkPwd();  //비밀번호확인: 비밀번호 재입력창 비밀번호랑 다를경우

        //이미지
        r1 = findViewById(R.id.iv1);
        r2 = findViewById(R.id.iv2);
        r3 = findViewById(R.id.iv3);
        r4 = findViewById(R.id.iv4);
        r5 = findViewById(R.id.iv5);
        r6 = findViewById(R.id.iv6);
        r1.setChecked(true);
        imageName = "profile_basic1";//이미지 기본설정
        selectMyImage(); //이미지 선택

        //닉네임
        et_nickname = findViewById(R.id.register_nickname);
        nickname_checkmes = findViewById(R.id.register_nickname_check_txt);
        nickname_dupcheck = findViewById(R.id.register_nickname_check_btn);
        nickname_dupcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_nickname.getText().toString().equals("")) {
                    nickname_checkmes.setText("닉네임을 입력해주세요.");
                } else {
                    checkNickname();     //id중복확인
                }
            }
        });

        //학과
        btnMajorSearch = findViewById(R.id.profile_edit_major_search);

        //역량
        et_career = findViewById(R.id.register_career);

        //동록 버튼 클릭시
        post_person = findViewById(R.id.resup);
        post_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postid = et_id.getText().toString();
                String postpwd = et_pwd.getText().toString();
                String postnickname = et_nickname.getText().toString();
                String school = et_school.getText().toString();
                String major = et_major.getText().toString();
                String address = et_region.getText().toString();
                String postcareer = et_career.getText().toString();
                Integer postgender = value_gender;
                Integer postgrade = value_grade;
                Integer postage = value_age;
                String postimage = imageName; //선택된 이미지url 가져오기
                //Integer postage = Integer.valueOf(et_age.getText().toString());

                dialog = new Dialog(LoginRegisterActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_register);
                TextView textView = dialog.findViewById(R.id.text);
                Button cancel = dialog.findViewById(R.id.btnCancel);

                //제대로 입력안했을 시
                if ((postid.length() <= 0) || (postpwd.length() <= 0) || (postnickname.length() <= 0)
                        || !idValidation || !pwValidation) {

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    if (postid.length() == 0) {
                        textView.setText("아이디를 입력해주세요.");
                        dialog.show();
                        return;
                    }
                    if (postpwd.length() == 0) {
                        textView.setText("비밀번호를 입력해주세요.");
                        dialog.show();
                        return;
                    }
                    if (postnickname.length() == 0) {
                        textView.setText("닉네임을 입력해주세요.");
                        dialog.show();
                        return;
                    }
                    if (!idValidation) {
                        textView.setText("아이디 중복 확인해주세요.");
                        dialog.show();
                        return;
                    }
                    if (!pwValidation) {
                        textView.setText("비밀번호가 일치하지 않습니다.");
                        dialog.show();
                        return;
                    }
                    if (!nicknameValidation) {
                        textView.setText("닉네임 중복 확인해주세요.");
                        dialog.show();
                        return;
                    }

                } else {  //제대로 입력했을 시 서버에 post 후 LoginActivity로 이동

                    String url = "http://13.125.214.178:8080/personal";
                    Map map = new HashMap();
                    map.put("stringId", postid);
                    map.put("pwd", postpwd);
                    map.put("nickname", postnickname);
                    map.put("img", postimage);
                    map.put("school", school);
                    map.put("major", major);
                    map.put("grade", postgrade);
                    map.put("career", postcareer);
                    map.put("age", postage);
                    map.put("gender", postgender);
                    map.put("address", address);

                    JSONObject params = new JSONObject(map);

                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject obj) {

                                    textView.setText("회원가입이 완료되었습니다!");
                                    dialog.show();

                                    Button cancel = dialog.findViewById(R.id.btnCancel);
                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("회원가입 실패");
                                    Log.e("register_Error", error.getMessage());
                                }
                            }) {

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=UTF-8";
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(objectRequest);
                }
            }
        });


    }


    public void setSpinner(String topic) { //spinner 설정 함수
        //topic에 따른 spinner설정
        //학년
        if (topic.equals("grade")) {
            ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_dropdown_item);
            genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_grade.setAdapter(genderAdapter);

            spinner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    value_grade = position + 1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        //나이
        else if (topic.equals("age")) {
            ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(this, R.array.age, android.R.layout.simple_spinner_dropdown_item);
            genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_age.setAdapter(genderAdapter);

            spinner_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    value_age = position + 19;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public void checkId() { //아이디 중복검사

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url1 = "http://13.125.214.178:8080/personal/stringId/" + et_id.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                id_checkmes.setText("사용 가능한 아이디입니다.");
                idValidation = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                id_checkmes.setText("이미 아이디가 존재합니다.");
                idValidation = false;
            }
        });
        requestQueue.add(stringRequest);
    }

    public void checkNickname() { //아이디 중복검사

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url1 = "http://13.125.214.178:8080/personal/nickname/" + et_nickname.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                nickname_checkmes.setText("사용 가능한 닉네임입니다.");
                nicknameValidation = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nickname_checkmes.setText("이미 해당 닉네임이 존재합니다.");
                nicknameValidation = false;
            }
        });
        requestQueue.add(stringRequest);
    }


    public void checkPwd() { //비밀번호와 비밀번호확인 일치체크
        et_pwdcheck.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_pwdcheck.isFocusable() && s.toString().equals(et_pwd.getText().toString())) {
                    pwd_checkmes.setText("비밀번호를 올바르게 입력했습니다.");
                    pwValidation = true;
                } else {
                    pwd_checkmes.setText("비밀번호를 다시 확인해주세요.");
                    pwValidation = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
                    imageName = "profile_basic1";
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
                    imageName = "profile_basic2";
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
                    imageName = "profile_basic3";
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
                    imageName = "profile_basic4";
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
                    imageName = "profile_basic5";
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
                    imageName = "profile_basic6";
                }
            }
        });
    }

    public void showSearchDialog(List<String> list, String how) {

        //다이얼로그
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginRegisterActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_register_search, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        searchAdapter = new SearchAdapter();

        rvSearch = dialogView.findViewById(R.id.recyclerView_search);
        rvSearch.setAdapter(searchAdapter);
        rvSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

        // 데이터 채워줌
        searchItem = new ArrayList<>(list);
        searchAdapter.setSearchList(searchItem);

        // Adapter 의 listener 지정 (값 선택했을 때)
        searchAdapter.setOnItemClicklistener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                returnValue = searchItem.get(position);
                if (how.equals("school")) {
                    et_school.setText(returnValue);
                } else if (how.equals("major")) {
                    et_major.setText(returnValue);
                } else if (how.equals("region")) {
                    et_region.setText(returnValue);
                }
                alertDialog.dismiss();
            }
        });

        // 탐색 버튼 누르면 검색됨
        EditText input = dialogView.findViewById(R.id.search_edittext);
        Button btnSearchDB = dialogView.findViewById(R.id.search_btn);
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

    ;
}