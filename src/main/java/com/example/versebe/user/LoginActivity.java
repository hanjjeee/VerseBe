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
import com.example.versebe.main_page.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText login_id, login_pw;
    private Button login_button , register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        setContentView(R.layout.activity_login);

        login_id = findViewById( R.id.login_input_email);
        login_pw = findViewById( R.id.login_input_pw);

        register_button = findViewById( R.id.login_register_button);
        login_button = findViewById(R.id.login_submit_button);

        register_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity( intent );

            }
        });


        login_button.setOnClickListener( new View.OnClickListener() {


            @Override
            public void onClick(View view) {



                //로그인 구현
                String userId = login_id.getText().toString();
                String userPassword = login_pw.getText().toString();

                System.out.println("response listener 전");

                Response.Listener<String> responseListener;

                responseListener = response -> {

                    try {
                        System.out.println("on response 후, json 전");
                        System.out.println("hanjiyoon " + response);
                        JSONObject jsonObject = new JSONObject(response);

                        System.out.println("json 후, success 전");

                        boolean success = jsonObject.getBoolean( "success" );
                        System.out.println(response+"json 후");


                        if (success) {//로그인 성공시


                            String UserEmail = jsonObject.getString( "userEmail" );
                            String UserPassword = jsonObject.getString( "userPassword" );
                            String UserId = jsonObject.getString( "userId" );
                            String UserPhone = jsonObject.getString( "userPhone" );
                            String UserImage = jsonObject.getString( "image_path" );

                            Toast.makeText(getApplicationContext(), String.format("%s님 환영합니다.", UserId), Toast.LENGTH_SHORT).show();
                            //테스트
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);


                            intent.putExtra( "userEmail", UserEmail );
                            intent.putExtra( "userPassword", UserPassword);
                            intent.putExtra( "cur_user_id", UserId );
                            intent.putExtra( "userPhone", UserPhone );
                            intent.putExtra( "image_path", UserImage );


                            startActivity(intent);

                        }
                        else
                        {
                            //로그인 실패시
                            Toast.makeText(getApplicationContext(), "로그인에 실패하셨습니다. 회원가입 하셨나요?", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } catch (JSONException e) {

                        System.out.println("not try");
                        e.printStackTrace();
                    }

                };

                System.out.println("response listener 후");

                LoginRequest loginRequest = new LoginRequest( userId, userPassword, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );


            }//on click




        });//end of click

    }

}
