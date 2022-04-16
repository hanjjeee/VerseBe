package com.example.versebe.user;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.versebe.R;

public class PosterActivity extends AppCompatActivity {

    private String image_path;
    public ImageView poster_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);


        //임시
        image_path = "cat.jpg";
        image_path = "http://hanjiyoon.dothome.co.kr/app_image/"+image_path;

        poster_image = findViewById(R.id.poster_image);

        Glide.with(this).load(image_path).into(poster_image);

    }
}
