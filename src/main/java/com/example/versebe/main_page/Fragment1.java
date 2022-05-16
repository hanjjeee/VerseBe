package com.example.versebe.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.versebe.R;
import com.example.versebe.user.ModifyActivity_email;
import com.example.versebe.user.ModifyActivity_name;
import com.example.versebe.user.ModifyActivity_password;
import com.example.versebe.user.ModifyActivity_phone;

public class Fragment1 extends Fragment {

    private Intent intent;
    private TextView my_name, my_email;
    private TextView cur_name, cur_email, cur_phone, cur_password;
    private ImageView my_image;
    private Button cur_name_edit_button, cur_email_edit_button,cur_phone_edit_button,cur_password_edit_button;

    private String my_image_path;
    private String cur_user_id;

    public Fragment1(Intent intent) {

        this.intent = intent;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_mypage, container, false);

        cur_user_id = intent.getExtras().getString("cur_user_id");

        my_name = view.findViewById(R.id.my_name);
        cur_name = view.findViewById(R.id.cur_name);
        my_name.setText(cur_user_id);
        cur_name.setText(cur_user_id);

        my_email = view.findViewById(R.id.my_email);
        cur_email = view.findViewById(R.id.cur_email);
        my_email.setText(intent.getExtras().getString("userEmail"));
        cur_email.setText(intent.getExtras().getString("userEmail"));

        cur_phone = view.findViewById(R.id.cur_phone);
        cur_phone.setText(intent.getExtras().getString("userPhone"));


        cur_name_edit_button = view.findViewById(R.id.cur_name_edit_button);
        cur_email_edit_button = view.findViewById(R.id.cur_email_edit_button);
        cur_phone_edit_button = view.findViewById(R.id.cur_phone_edit_button);
        cur_password_edit_button = view.findViewById(R.id.cur_password_edit_button);

        my_image_path="http://hanjiyoon.dothome.co.kr/profile/"+cur_user_id+".jpg";
        my_image = view.findViewById(R.id.my_image);
        Glide.with(this).load(my_image_path).into(my_image);

        cur_name_edit_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent_email = new Intent(getActivity(), ModifyActivity_name.class);

                intent_email.putExtra( "userEmail", intent.getExtras().getString("userEmail") );

                startActivity( intent_email );

            }
        });

        cur_email_edit_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent_email = new Intent(getActivity(),ModifyActivity_email.class);

                intent_email.putExtra( "userEmail", intent.getExtras().getString("userEmail") );

                startActivity( intent_email );

            }
        });

        cur_phone_edit_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent_phone = new Intent(getActivity(),ModifyActivity_phone.class);

                intent_phone.putExtra( "userPhone", intent.getExtras().getString("userPhone") );

                startActivity( intent_phone );

            }
        });

        cur_password_edit_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent_password = new Intent(getActivity(),ModifyActivity_password.class);
                intent_password.putExtra( "userPassword", intent.getExtras().getString("userPassword") );

                startActivity( intent_password );

            }
        });


        return view;
    }
}