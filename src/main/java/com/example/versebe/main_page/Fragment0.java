package com.example.versebe.main_page;

import static com.example.versebe.R.layout.mainpage_main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.versebe.R;
import com.example.versebe.user.ChoiceLayout;
import com.example.versebe.user.FeedItem;
import com.example.versebe.user.FeedItemAdapter;
import com.example.versebe.user.FollowItem;
import com.example.versebe.user.FollowItemAdapter;
import com.example.versebe.user.LoginActivity;
import com.example.versebe.user.MakePosterActivity;
import com.example.versebe.user.RegisterActivity;
import com.example.versebe.user.SearchActivity;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment0 extends Fragment {

    private Intent intent;

    public View view;

    private TextView mainpage_id;

    private TabLayout tabLayout2;
    private TabItem tabItem2_1;
    private TabItem tabItem2_2;
    private TabItem tabItem2_3;

    private ViewPager viewPager2;
    private FragmentAdapter adapter2;
    private Button search_button;
    private Button add_button;

    //포스터 생성 연결 make 버튼




    public Fragment0(Intent intent) {

        this.intent = intent;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(mainpage_main, container, false);

        mainpage_id = view.findViewById(R.id.mainpage_name);
        mainpage_id.setText(intent.getExtras().getString("cur_user_id"));

        search_button = view.findViewById(R.id.search_button);
        add_button = view.findViewById(R.id.mainpage_add_button);


        search_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent search_intent = new Intent( getActivity(), SearchActivity.class);
                startActivity( search_intent );

            }
        });

        add_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final PopupMenu popupMenu = new PopupMenu(getContext(), view);
                getActivity().getMenuInflater().inflate(R.menu.menu1, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId() == R.id.poster) {
                            Toast.makeText(getActivity(), "poster 클릭", Toast.LENGTH_SHORT).show();

                            //포스터 생성 인텐트로 넘어가기
                            Intent poster_intent = new Intent(getContext(), ChoiceLayout.class);
                            startActivity(poster_intent);


                        } else if (menuItem.getItemId() == R.id.layout) {
                            Toast.makeText(getActivity(), "layout 클릭", Toast.LENGTH_SHORT).show();


                            ////포스터 생성 인텐트로 넘어가기


                        }

                        return false;
                    }
                });

                popupMenu.show();
            }

        });

                ////내부 프레그먼트
                //getFragmentManager()가 아닌, getChildFragmentManager() 를 사용
                tabLayout2 = (TabLayout) view.findViewById(R.id.main_tab);


                tabItem2_1 = view.findViewById(R.id.mainpage_poster_tab);
                tabItem2_2 = view.findViewById(R.id.mainpage_layout_tab);
                tabItem2_3 = view.findViewById(R.id.mainpage_my_tab);


                viewPager2 = view.findViewById(R.id.feed_frame);

                adapter2 = new FragmentAdapter(getChildFragmentManager(), 1);

                adapter2.addFragment(new Fragment0_1(intent));
                adapter2.addFragment(new Fragment0_2(intent));
                adapter2.addFragment(new Fragment0_3(intent));

                viewPager2.setAdapter(adapter2);


                tabLayout2.setupWithViewPager(viewPager2);

                tabLayout2.getTabAt(0).setText("POSTER");
                tabLayout2.getTabAt(1).setText("LAYOUT");
                tabLayout2.getTabAt(2).setText("MY");


                return view;


            }



}



