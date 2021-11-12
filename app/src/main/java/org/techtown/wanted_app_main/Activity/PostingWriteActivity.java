package org.techtown.wanted_app_main.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.wanted_app_main.Activity.Login.LoginActivity;
import org.techtown.wanted_app_main.Activity.Login.LoginRegisterActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostingWriteActivity extends AppCompatActivity {

    // layout
    private Button btn_contest, btn_study, btn_etc;
    private Button write_complete;
    private EditText write_title, write_team_name, write_content;
    private DatePicker write_date;

    Dialog dialog;

    // category -> 0 : contest, 1 : etc, 2 : etc
    private int category = -1;

    private Personal me;
    private Posting posting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_write);

        me = getIntent().getParcelableExtra("me");
        posting = getIntent().getParcelableExtra("posting");
        System.out.println("postingWriteActivity >> " + me);

        // 카테고리 설정 (기본값 : null)
        btn_contest = findViewById(R.id.btn_contest);
        btn_study = findViewById(R.id.btn_study);
        btn_etc = findViewById(R.id.btn_etc);
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_contest:
                        category = 0;
                        break;
                    case R.id.btn_study:
                        category = 1;
                        break;
                    case R.id.btn_etc:
                        category = 2;
                        break;
                }
                onCategoryClickedChangeButtonDesign(category);
            }
        };
        btn_contest.setOnClickListener(onClickListener);
        btn_study.setOnClickListener(onClickListener);
        btn_etc.setOnClickListener(onClickListener);

        write_title = findViewById(R.id.write_title);
        write_team_name =  findViewById(R.id.write_team_name);
        write_content = findViewById(R.id.write_content);
        write_date = findViewById(R.id.write_date);

        // 글 수정 버튼을 통해서 접근 시 값 설정
        if(posting != null) {
            if(posting.category.equals("공모전")) {
                category = 0;
            } else if (posting.category.equals("스터디")){
                category = 1;
            } else if (posting.category.equals("기타")) {
                category = 2;
            }
            onCategoryClickedChangeButtonDesign(category);
            write_title.setText(posting.title);
            write_team_name.setText(posting.teamName);
            write_content.setText(posting.content);
        }

        // 글 등록 버튼 클릭시
        write_complete = findViewById(R.id.write_complete);
        write_complete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String title = write_title.getText().toString();
                String teamName = write_team_name.getText().toString();
                System.out.println(teamName);
                String content = write_content.getText().toString();
                String endDate = String.format("%s-%s-%s 00:00:00", write_date.getYear(), write_date.getMonth() + 1, write_date.getDayOfMonth());
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd, HH:mm:ss");
                String writeTime = now.format(formatter);

                String str_category;
                if (category == 0) {
                    str_category = "공모전";
                } else if (category == 1) {
                    str_category = "스터디";
                } else {
                    str_category = "기타";
                }

                try {
                    if (check(title, teamName, content, endDate)) {
//                        RequestQueue requestQueue;
//                        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//                        Network network = new BasicNetwork(new HurlStack());
//                        requestQueue = new RequestQueue(cache, network);
//                        requestQueue.start();

                        String url = "http://13.125.214.178:8080/posting/" + me.id;

                        Map map = new HashMap();
                        map.put("category", str_category);
                        map.put("team_name", teamName);
                        map.put("title", title);
                        map.put("content", content);
                        map.put("end_time", endDate);

                        JSONObject params = new JSONObject(map);

                        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject obj) {
                                        dialog = new Dialog(PostingWriteActivity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.register_dialog);
                                        TextView dialogText = dialog.findViewById(R.id.text);
                                        dialogText.setText("글이 등록되었습니다!");
                                        dialog.show();
                                        Button cancel = dialog.findViewById(R.id.btnCancel5);
                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                intent.putExtra("me", me);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                        try {
                                            System.out.println(obj);
                                            Long posting_id = obj.getLong("id");

                                            String url2 = "http://13.125.214.178:8080/team/" + posting_id;

                                            Map map = new HashMap();
//                                            map.put("leader_id", me.id);
//                                            map.put("team_name", teamName);
//                                            map.put("posting_id", posting_id);

                                            JSONObject params = new JSONObject(map);

                                            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url2, params,
                                                    new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject obj) {
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                        }
                                                    }) {
                                                @Override
                                                public String getBodyContentType() {
                                                    return "application/json; charset=UTF-8";
                                                }
                                            };
                                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                            queue.add(objectRequest);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
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
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onCategoryClickedChangeButtonDesign(int index) {
        List<Button> buttons = Arrays.asList(btn_contest, btn_study, btn_etc);

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

    public void showCategoryWithChangeText(String text) {
        dialog = new Dialog(PostingWriteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.register_dialog);
        TextView dialogText = dialog.findViewById(R.id.text);
        dialogText.setText(text);
        dialog.show();
        Button cancel = dialog.findViewById(R.id.btnCancel5);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // 글 오류 검사
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean check(String title, String teamName, String content, String endDate) throws ParseException {

//        // 종료 날짜 비교 코드 (정상 실행 X -> 수정 필요)
//        LocalDateTime writeTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
//        String now = writeTime.format(formatter);

        if(category < 0) {
            showCategoryWithChangeText("카테고리를 지정해주세요");
            return false;
        } else if (title.length() <= 0) {
            showCategoryWithChangeText("제목을 입력해주세요");
            return false;
        } else if (teamName.length() <= 0) {
            showCategoryWithChangeText("팀명을 입력해주세요");
            return false;
        } else if (content.length() <= 0) {
            showCategoryWithChangeText("글을 입력해주세요");
            return false;
        }
        return true;
    }
}
