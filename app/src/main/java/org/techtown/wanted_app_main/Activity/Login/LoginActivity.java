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
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Personal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private ArrayList<Personal> list = new ArrayList<>();
    private EditText getId;
    private EditText getPwd;
    private TextView registerbtn;
    ViewGroup loginbtn;

    private boolean check = false;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getId = findViewById(R.id.loginid);
        getPwd = findViewById(R.id.loginpwd);
        loginbtn = findViewById(R.id.loginbtn);
        registerbtn = findViewById(R.id.goregister);


        //login버튼 클릭시
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getId.getText().toString();
                String Pwd = getPwd.getText().toString();

                if (Id.length() <= 0) { //아이디 미입력시
                    dialog = new Dialog(LoginActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.id_dialog);
                    dialog.show();
                    Button cancel = dialog.findViewById(R.id.btnCancel5);
                    cancel.setOnClickListener(new View.OnClickListener() {
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
                } else if (Pwd.length() <= 0) { //아이디와 비밀번호 잘못입력시
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
                } else { //정상입력시 mainfragment로 이동
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("ID", Id);
                    startActivity(intent);
                    finish();

                }


            }});

        //forgot password클릭시
        //register 클릭시
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("ID", Id);
                startActivity(intent);
                finish();
            }});



    }
}