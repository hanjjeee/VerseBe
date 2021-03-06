package com.example.versebe.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.versebe.R;
import com.example.versebe.user.FeedItem;
import com.example.versebe.user.FollowItem;
import com.example.versebe.user.FollowItemAdapter;
import com.example.versebe.user.LoginActivity;
import com.example.versebe.user.MemberActivity;
import com.example.versebe.user.OnMemberItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    private Intent intent;
    public View view;
    private String cur_user_id;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private EditText keyword;
    private TextView mainpage_name;
    private ImageView my_image;
    private String my_image_path;

    private ArrayList<FollowItem> items;
    private FollowItemAdapter adapter;

    //json 추가
    private String title;
    private String email;
    private String image_path;

    public Fragment2(Intent intent) {

        this.intent = intent;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.mainpage_follow, container, false);
        mainpage_name = view.findViewById(R.id.followpage_id);


        cur_user_id = intent.getExtras().getString("cur_user_id");
        mainpage_name.setText(cur_user_id);

        keyword = view.findViewById(R.id.followpage_search);

        my_image = view.findViewById(R.id.followpage_image_view);
        my_image_path="http://hanjiyoon.dothome.co.kr/profile/"+cur_user_id+".jpg";
        my_image = view.findViewById(R.id.followpage_image_view);

        //캐시x
        Glide.with(this).load(my_image_path).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(my_image);


        items = new ArrayList<FollowItem>();


        recyclerView = (RecyclerView) view.findViewById(R.id.follow_recyclerview);
        adapter = new FollowItemAdapter(getContext(), items);




        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        //서버주소
        //String url = "http://hanjiyoon.dothome.co.kr/loadDB.php";
        String url = "http://hanjiyoon.dothome.co.kr/user_list.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용

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

                        title = jsonObject.getString("userId");

                        email = jsonObject.getString("userEmail");

                        image_path = jsonObject.getString("image_path");


                        //이미지 경로의 경우 서버 IP가 제외된 주소이므로(uploads/xxxx.jpg) 바로 사용 불가.
                        image_path = "http://hanjiyoon.dothome.co.kr/profile/" + image_path;

                        items.add(0, new FollowItem(image_path, title, email)); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                        adapter.notifyItemInserted(0);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);

        //////end of server

        recyclerView.setAdapter(adapter);


        //클릭 리스너
        adapter.setOnItemClicklistener(new OnMemberItemClickListener() {
            @Override
            public void onItemClick(FollowItemAdapter.VH holder, View view, int position) {
                FollowItem item = adapter.getItem(position);
                Toast.makeText(getContext(),"아이템 선택 " + item.getId(), Toast.LENGTH_LONG).show();
                Intent follow_intent = new Intent(getContext(), MemberActivity.class);

                follow_intent.putExtra( "userId", item.getId());
                follow_intent.putExtra("cur_user_id", cur_user_id);

                startActivity(follow_intent);
            }
        });

        //검색
        //바로 나오도록
        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String searchText = keyword.getText().toString();
                searchFilter(searchText);

            }
        });


        return view;

    }

    public void searchFilter(String searchText) {

        ArrayList<FollowItem>filteredList=new ArrayList<>();

        filteredList.clear();

        for (int i = 0; i < items.size(); i++) {

            if (items.get(i).getId().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(items.get(i));
            }
            else if (items.get(i).getDetail().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(items.get(i));
            }
            else if (items.get(i).getImage_path().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(items.get(i));
            }
        }

        adapter.filterList(filteredList);




    }

}



