package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.versebe.R;

public class ModifyActivity_phone extends AppCompatActivity {

    private EditText new_phone;
    private TextView cur_phone;
    private Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);

        //현재 휴대폰 번호 연결
        Intent intent = getIntent();

        submit_button = findViewById(R.id.modify_submit_phone);
        new_phone = findViewById(R.id.modify_input_phone);

        cur_phone = findViewById(R.id.modify_cur_phone);
        cur_phone.setText(intent.getExtras().getString("userPhone"));


    }
}
