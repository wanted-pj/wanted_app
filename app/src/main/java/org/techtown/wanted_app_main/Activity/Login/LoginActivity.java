package org.techtown.wanted_app_main.Activity.Login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.ServerRequest.GetMajorRegionSchoolRequest;
import org.techtown.wanted_app_main.database.OuterApi.Major;
import org.techtown.wanted_app_main.database.OuterApi.OuterData;
import org.techtown.wanted_app_main.database.OuterApi.Region;
import org.techtown.wanted_app_main.database.OuterApi.School;
import org.techtown.wanted_app_main.database.Personal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText getId, getPwd;
    private TextView btnLogin, btnRegister;
    private Personal me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 외부 데이터 가져오기
        // 서버 호출
        Cache cache = new DiskBasedCache(getApplicationContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        //학교 server데이터 가져오기
        String schoolURL = "http://13.125.214.178:8080/school";
        Response.Listener<String> schoolResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String changeString = new String();
                try {
                    changeString = new String(response.getBytes("8859_1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<School>>() {
                }.getType();

                List<School> temp = gson.fromJson(changeString, listType);
                OuterData.schoolList = new ArrayList<>();
                for (School school : temp) {
                    OuterData.schoolList.add(school.getSchool_name());
                }

            }
        };


        //학과 server데이터 가져오기
        String majorURL = "http://13.125.214.178:8080/major";
        Response.Listener<String> majorResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String changeString = new String();
                try {
                    changeString = new String(response.getBytes("8859_1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<Major>>() {
                }.getType();

                List<Major> temp = gson.fromJson(changeString, listType);
                OuterData.majorList = new ArrayList<>();
                for (Major major : temp) {
                    OuterData.majorList.add(major.getMajor_name());
                }
            }
        };

        //사는곳 server데이터 가져오기
        String regionURL = "http://13.125.214.178:8080/region";
        Response.Listener<String> regionResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String changeString = new String();
                try {
                    changeString = new String(response.getBytes("8859_1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<Region>>() {
                }.getType();

                List<Region> temp = gson.fromJson(changeString, listType);
                OuterData.regionList = new ArrayList<>();
                for (Region region : temp) {
                    OuterData.regionList.add(region.getRegion_name());
                }
            }
        };
        requestQueue.add(new GetMajorRegionSchoolRequest(regionURL, regionResponseListener));
        requestQueue.add(new GetMajorRegionSchoolRequest(majorURL, majorResponseListener));
        requestQueue.add(new GetMajorRegionSchoolRequest(schoolURL, schoolResponseListener));

        // 로그인 시작

        getId = findViewById(R.id.login_id);
        getPwd = findViewById(R.id.login_pwd);
        getPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        btnLogin = findViewById(R.id.login_login);
        btnRegister = findViewById(R.id.login_register);

        //register 클릭시
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                startActivity(intent);
            }});

        //login버튼 클릭시
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringId = getId.getText().toString();
                String pwd = getPwd.getText().toString();

                // 다이얼로그 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_register, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // 다이얼로그 뷰 컴포넌트
                TextView dialogMessageView = view.findViewById(R.id.text);
                Button cancel = view.findViewById(R.id.btnCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                if (stringId.length() <= 0) { //아이디 미입력시
                    dialogMessageView.setText("아이디를 입력해주세요.");
                    alertDialog.show();
                } else if (pwd.length() <= 0) { //비번 미입력시
                    dialogMessageView.setText("비밀번호를 입력해주세요.");
                    alertDialog.show();
                } else { //비정상입력시 재입력, 정상입력시 mainfragment로 이동
                    RequestQueue requestQueue;
                    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                    Network network = new BasicNetwork(new HurlStack());
                    requestQueue = new RequestQueue(cache, network);
                    requestQueue.start();

                    String url1 = "http://13.125.214.178:8080/personal/login/" + stringId;

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
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
                            me = gson.fromJson(changeString, (Type) Personal.class);
                            System.out.println(me);

                            if (!me.getPwd().equals(pwd)) { //아이디 비번 정확히 입력x ->재입력하라는 dialog 창이 뜸
                                dialogMessageView.setText("비밀번호를\n정확히 입력해주세요.");
                                alertDialog.show();
                            } else { //올바른 입력시 mainactivity로 이동
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("me", me);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { // 아이디가 존재하지 않는 경우
                           dialogMessageView.setText("존재하지 않는 아이디 입니다.");
                           alertDialog.show();
                        }
                    });
                    requestQueue.add(stringRequest);


                }    //else 닫기

            }
        });

    }

}