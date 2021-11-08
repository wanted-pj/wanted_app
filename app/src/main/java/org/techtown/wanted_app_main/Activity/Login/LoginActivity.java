package org.techtown.wanted_app_main.Activity.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.Fragment.Board;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private ArrayList<Personal> list = new ArrayList<>();
    private EditText getId, getPwd;
    private TextView btnLogin, btnRegister;
    private boolean check = false;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getId = findViewById(R.id.login_id);
        getPwd = findViewById(R.id.login_pwd);
        btnLogin = findViewById(R.id.login_login);
        btnRegister = findViewById(R.id.login_register);

        //register 클릭시
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                startActivity(intent);
                finish();
            }});

        //login버튼 클릭시
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getId.getText().toString();
                String Pwd = getPwd.getText().toString();

                if (Id.length() <= 0) { //아이디 미입력시
                    dialog = new Dialog(LoginActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.id_dialog);
                    dialog.show();
                    Button cancel1 = dialog.findViewById(R.id.btnCancel5);
                    cancel1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                } else if (Pwd.length() <= 0) { //비번 미입력시
                    dialog = new Dialog(LoginActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.pwd_dialog);
                    dialog.show();
                    Button cancel = dialog.findViewById(R.id.btnCancel5);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                } else { //비정상입력시 재입력, 정상입력시 mainfragment로 이동
                    RequestQueue requestQueue;
                    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                    Network network = new BasicNetwork(new HurlStack());
                    requestQueue = new RequestQueue(cache, network);
                    requestQueue.start();

                    String url1 = "http://13.125.214.178:8080/personal";

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
                            Type listType = new TypeToken<ArrayList<Personal>>() {
                            }.getType();
                            list = gson.fromJson(changeString, listType);


                            for (int i = 0; i < list.size(); i++) {  //id와 pwd비교
                                if (Id.compareTo(list.get(i).stringId) == 0) {
                                    if (Pwd.compareTo(list.get(i).pwd) == 0) {
                                        check = true;
                                    }
                                    break;
                                }
                            }

                            if (check == false) { //아이디 비번 정확히 입력x ->재입력하라는 dialog 창이 뜸
                                dialog = new Dialog(LoginActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.incorrect_id_pwd_dialog);
                                dialog.show();
                                Button cancel = dialog.findViewById(R.id.btnCancel4);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                            } else { //올바른 입력시 mainactivity로 이동
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("ID", Id);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {//서버오류시

                            Log.e("Dd", error.getMessage());
                        }
                    });
                    requestQueue.add(stringRequest);


                }    //else 닫기

            }
        });

        //forgot password클릭시


    }
}