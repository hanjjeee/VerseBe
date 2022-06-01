package com.example.versebe.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.versebe.R;
import com.example.versebe.user.CommentActivity;
import com.example.versebe.user.CommentInputRequest;
import com.example.versebe.user.CommentRequest;
import com.example.versebe.user.FeedItem;
import com.example.versebe.user.FeedItemAdapter;
import com.example.versebe.user.FollowItem;
import com.example.versebe.user.FollowItemAdapter;
import com.example.versebe.user.MemberActivity;
import com.example.versebe.user.MyscrapRequest;
import com.example.versebe.user.OnFeedItemClickListener;
import com.example.versebe.user.PosterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment0_3 extends Fragment {

    private Intent intent;
    private String cur_user_id;

    public View view;

    private RecyclerView recyclerView;
    private FeedItemAdapter adapter;
    private ArrayList<FeedItem> items;
    private  GridLayoutManager layoutManager;

    //json
    private String type;
    private int poster_num;
    private String thumb_image;
    private String user_id;
    private String update_date;
    private String last_date;
    private int article_num;
    private String hash_tag;
    private String poster_image;
    private String title;
    private String content;
    private int scrap;





    public Fragment0_3(Intent intent) {

        this.intent = intent;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.mainpage_main_feed1, container, false);
        cur_user_id = intent.getExtras().getString("cur_user_id");

        //그리드 리사이클러 뷰 불러오기
        items = new ArrayList<FeedItem>();

        recyclerView = (RecyclerView) view.findViewById(R.id.mainpage_recyclerview);

        adapter = new FeedItemAdapter(getContext(), items);

        recyclerView.setAdapter(adapter);

        layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);


        //서버주소
        //test
        //String url = "http://hanjiyoon.dothome.co.kr/posters.php";
        String url = "http://hanjiyoon.dothome.co.kr/myscrap.php";

        //결과를 JsonArray 로 받음
        //JsonArrayRequest를 이용
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {

                //db 연결 확인용
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();


                //파라미터로 응답받은 결과 JsonArray를 분석
                items.clear();
                adapter.notifyDataSetChanged();

                try {

                    for (int i = 0; i < response.length(); i++) {


                        JSONObject jsonObject = response.getJSONObject(i);

                        type = jsonObject.getString("TYPE");
                        poster_num = jsonObject.getInt("POSTER_NUM");
                        thumb_image = jsonObject.getString("THUMBNAIL");
                        user_id = jsonObject.getString("USER_ID");
                        update_date = jsonObject.getString("UPDATE_DATE");
                        last_date = jsonObject.getString("LAST_DATE");
                        article_num = jsonObject.getInt("ARTICLE_NUM");
                        hash_tag = jsonObject.getString("HASH_TAG");
                        title = jsonObject.getString("TITLE");
                        poster_image = jsonObject.getString("POSTER_IMAGE");
                        content = jsonObject.getString("CONTENT");
                        scrap = jsonObject.getInt("SCRAP");


                        //test
                        //image_path = "http://hanjiyoon.dothome.co.kr/app_image/" + image_path;
                        thumb_image = "http://hanjiyoon.dothome.co.kr/posters/" + thumb_image;
                        poster_image = "http://hanjiyoon.dothome.co.kr/posters/" + article_num;

                        items.add(0, new FeedItem(type, thumb_image, user_id, update_date, last_date,
                                poster_image, hash_tag, article_num, title, content, scrap));

                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };


        //
        String URL = "http://hanjiyoon.dothome.co.kr/myscrap.php";
        Map mMap = new HashMap();
        mMap.put("cur_user_id", cur_user_id);
        MyscrapRequest myscrapRequest =
                new MyscrapRequest(URL, responseListener, null, mMap);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(myscrapRequest);



        //클릭 리스너
        adapter.setOnItemClicklistener(new OnFeedItemClickListener() {

            @Override
            public void onItemClick(FeedItemAdapter.VH holder, View view, int position) {

                FeedItem item = adapter.getItem(position);

                Toast.makeText(getContext(),"아이템 선택 " + item.getUser_id(), Toast.LENGTH_LONG).show();

                Intent feed_intent = new Intent(getContext(), PosterActivity.class);

                feed_intent.putExtra( "cur_user_id", cur_user_id);
                feed_intent.putExtra( "ARTICLE_NUM", poster_num);

                startActivity(feed_intent);
            }
        });

        return view;

    }
}
