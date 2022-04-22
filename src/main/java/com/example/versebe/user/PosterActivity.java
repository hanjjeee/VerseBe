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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PosterActivity extends AppCompatActivity {

    private String image_path;
    private String poster_num;
    private String cur_userId;

    private ImageView poster_image;
    private TextView poster_user_id;
    private ImageButton like_button;


    private ArrayList<CommentItem> items;
    private CommentItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        Intent intent = getIntent();


        //임시
        image_path = "아웃백홈페이지.jpg";
        image_path = "http://hanjiyoon.dothome.co.kr/app_image/"+image_path;

        poster_image = findViewById(R.id.poster_image);
        poster_user_id = findViewById(R.id.posterpage_user_id);

        like_button = findViewById(R.id.posterpage_like_button);

        like_button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                cur_userId = intent.getExtras().getString("cur_user_id");
                poster_num = intent.getExtras().getString("POSTER_NUM");

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            System.out.println("hanjiyoon"+ response);
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                Toast.makeText(getApplicationContext(),"포스터넘버: "+poster_num,Toast.LENGTH_SHORT).show();
                                return;

                            } else {

                                Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                // 서버로 Volley를 통해 연결
                LikeRequest likeRequest = new LikeRequest(cur_userId, poster_num, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PosterActivity.this);
                queue.add(likeRequest);

            }


        });


        Glide.with(this).load(image_path).into(poster_image);



        items = new ArrayList<CommentItem>();


        adapter = new CommentItemAdapter(getApplicationContext(), items);





    }
}
