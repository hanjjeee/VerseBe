package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.versebe.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText register_email, register_pw, register_id, register_phone;
    private Button register_button, checkid_button;
    private boolean check_id_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        register_email = findViewById(R.id.register_input_email);
        register_pw = findViewById(R.id.register_input_pw);
        register_id = findViewById(R.id.register_input_name);
        register_phone = findViewById(R.id.register_input_phone);

        // 회원가입 버튼 클릭 시 수행
        register_button = findViewById(R.id.register_submit_button);
        checkid_button = findViewById(R.id.register_check_button);

        check_id_flag = false;

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check_id_flag == false)
                {
                    Toast.makeText(getApplicationContext(),"중복 확인을 해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                String getUserId = register_id.getText().toString();
                String getUserEmail = register_email.getText().toString();
                String getUserPhone = register_phone.getText().toString();
                String getUserPw = register_pw.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            System.out.println("hanjiyoon"+ response);
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                // 회원등록성공
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                // 회원등록실패
                                Toast.makeText(getApplicationContext(),"이미 존재하는 아이디 입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                // 서버로 Volley를 통해 연결
                RegisterRequest registerRequest = new RegisterRequest(getUserId,getUserEmail,getUserPhone,getUserPw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });


        checkid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String getUserEmail = register_id.getText().toString();



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            System.out.println("hanjiyoon"+ response);
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                // 회원등록성공
                                check_id_flag = true;
                                Toast.makeText(getApplicationContext(),"가능한 아이디 입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                // 회원등록실패
                                Toast.makeText(getApplicationContext(),"이미 존재하는 아이디 입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                // 서버로 Volley를 통해 연결
                CheckRequest checkRequest = new CheckRequest(getUserEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(checkRequest);

            }

        });



    }


}
