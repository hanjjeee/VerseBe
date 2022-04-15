package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.versebe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MemberActivity extends AppCompatActivity {

    private String userId;
    private TextView memberId;
    private ImageView memberImage;
    private String image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberpage);

        Intent intent = getIntent();
        userId=intent.getExtras().getString("userId");
        image_path = intent.getExtras().getString("image_path");
        image_path = "http://hanjiyoon.dothome.co.kr/loadDB.php/"+image_path;
        memberId = findViewById(R.id.member_id);
        memberImage = findViewById(R.id.member_image_view);

        memberId.setText(userId);
        Glide.with(this).load(image_path).into(memberImage);

    }


}
