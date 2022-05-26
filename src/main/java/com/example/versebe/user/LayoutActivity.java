package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.versebe.R;
import com.example.versebe.main_page.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LayoutActivity extends AppCompatActivity {

    private String type;
    private int layout_num;
    private String thumb_image;
    private String user_id;
    private String update_date;
    private String last_date;
    private int article_num;
    private String hash_tag;
    private String layout_image;
    private String title;
    private String content;
    private int scrap;

    private String cur_user_id;

    private ImageView layout_image_view;
    private TextView layout_user_id;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        Intent intent = getIntent();
        cur_user_id = intent.getExtras().getString("cur_user_id");

        type = intent.getExtras().getString("TYPE");
        layout_num = intent.getExtras().getInt("LAYOUT_NUM");;
        thumb_image= intent.getExtras().getString("THUMBNAIL");;
        user_id = intent.getExtras().getString("USER_ID");;
        update_date= intent.getExtras().getString("UPDATE_DATE");;
        last_date = intent.getExtras().getString("LAST_DATE");;
        article_num = intent.getExtras().getInt("ARTICLE_NUM");;
        hash_tag = intent.getExtras().getString("HASH_TAG");;
        layout_image = intent.getExtras().getString("LAYOUT_IMAGE");
        title = intent.getExtras().getString("TITLE");




        layout_image_view = findViewById(R.id.poster_image);
        layout_user_id = findViewById(R.id.posterpage_user_id);



        Glide.with(this).load(layout_image).into(layout_image_view);
        layout_user_id.setText(user_id);




    }
}

