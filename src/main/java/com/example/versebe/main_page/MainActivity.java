package com.example.versebe.main_page;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.versebe.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;



public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private FragmentAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        tabLayout = (TabLayout) findViewById(R.id.tab1);


        TabItem tabItem1 = findViewById(R.id.mainpage_main_tab);
        TabItem tabItem2 = findViewById(R.id.mainpage_mypage_tab);
        TabItem tabItem3 = findViewById(R.id.mainpage_follow_tab);



        viewPager = findViewById(R.id.frame);


        adapter = new FragmentAdapter(getSupportFragmentManager(),1);


        //FragmentAdapter에 컬렉션 담기
        adapter.addFragment(new Fragment0(intent));
        adapter.addFragment(new Fragment1(intent));
        adapter.addFragment(new Fragment2(intent));



        //ViewPager Fragment 연결
        viewPager.setAdapter(adapter);


        //ViewPager과 TabLayout 연결
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("MAIN");
        tabLayout.getTabAt(1).setText("MY PAGE");
        tabLayout.getTabAt(2).setText("FOLLOW");




    }



}
