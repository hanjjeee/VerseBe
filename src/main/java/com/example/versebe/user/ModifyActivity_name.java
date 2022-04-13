package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.versebe.R;

public class ModifyActivity_name extends AppCompatActivity {

    private EditText new_name;
    private TextView cur_name;
    private Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_name);

        //현재 이름 연결
        Intent intent = getIntent();

        submit_button = findViewById(R.id.modify_submit_name);
        new_name = findViewById(R.id.modify_input_name);

        cur_name = findViewById(R.id.modify_cur_name);
        cur_name.setText(intent.getExtras().getString("userId"));
    }
}
