package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.versebe.R;
import com.example.versebe.main_page.Fragment1;
import com.example.versebe.main_page.MainActivity;
import com.google.android.material.badge.BadgeUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ModifyActivity_email extends AppCompatActivity {

    private EditText new_email;
    private TextView cur_email;
    private Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_email);

        //현재 아이디 연결
        Intent intent = getIntent();

        submit_button = findViewById(R.id.modify_submit_email);
        new_email = findViewById(R.id.modify_input_email);
        cur_email = findViewById(R.id.modify_cur_email);

        cur_email.setText(intent.getExtras().getString("userEmail"));

        submit_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String getNewEmail = new_email.getText().toString();
                String curEmail = cur_email.getText().toString();

                Response.Listener<String> responseListener;

                responseListener = response -> {

                    try {
                        System.out.println("on response 후, json 전");
                        System.out.println("hanjiyoon " + response);
                        JSONObject jsonObject = new JSONObject(response);

                        System.out.println("json 후, success 전");

                        boolean success = jsonObject.getBoolean( "success" );
                        System.out.println(response+"json 후");


                        if (success) {

                            Toast.makeText(getApplicationContext(), "변경에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                            return;

                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(), "변경에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } catch (JSONException e) {

                        System.out.println("not try");
                        e.printStackTrace();
                    }

                };

                System.out.println("response listener 후");
                ModifyRequest_email modifyRequest_email = new ModifyRequest_email( curEmail,getNewEmail, responseListener );
                RequestQueue queue = Volley.newRequestQueue( ModifyActivity_email.this );
                queue.add( modifyRequest_email );

            }//end of on click

        });//end of submit button

    }
}
