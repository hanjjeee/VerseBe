package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.versebe.R;
import com.example.versebe.main_page.Fragment0;
import com.example.versebe.main_page.Fragment0_1;
import com.example.versebe.main_page.Fragment0_2;
import com.example.versebe.main_page.Fragment1;
import com.example.versebe.main_page.FragmentAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MemberActivity extends AppCompatActivity {

    private String userId;
    private String cur_user_id;

    private TextView memberId;
    private ImageView memberImage;
    private String image_path;


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberpage);

        Intent intent = getIntent();


        userId=intent.getExtras().getString("userId");
        cur_user_id=intent.getExtras().getString("cur_user_id");
        image_path = intent.getExtras().getString("image_path");


        tabLayout = (TabLayout) findViewById(R.id.tab2);
        TabItem tabItem1 = findViewById(R.id.memberpage_poster_tab);
        TabItem tabItem2 = findViewById(R.id.memberpage_layout_tab);

        viewPager = findViewById(R.id.memberpage_feed_frame);
        adapter = new FragmentAdapter(getSupportFragmentManager(),1);

        adapter.addFragment(new Fragment0_1(intent));
        adapter.addFragment(new Fragment0_2(intent));

        //ViewPager Fragment ??????
        viewPager.setAdapter(adapter);


        //ViewPager??? TabLayout ??????
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("POSTER");
        tabLayout.getTabAt(1).setText("LAYOUT");




        //??????

        image_path = "http://hanjiyoon.dothome.co.kr/app_image/"+userId+".jpg";

        memberId = findViewById(R.id.member_id);
        memberImage = findViewById(R.id.member_image_view);

        memberId.setText(userId);
        Glide.with(this).load(image_path).into(memberImage);

    }


}
