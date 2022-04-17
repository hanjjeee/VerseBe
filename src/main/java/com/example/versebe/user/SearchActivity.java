package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.versebe.R;
import com.example.versebe.main_page.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText keyword;
    String get_keyword;

    ArrayList<FeedItem> items;
    FeedItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keyword = findViewById(R.id.searchpage_keyword);
        get_keyword = keyword.getText().toString();

        //그리드 리사이클러 뷰 불러오기
        RecyclerView recyclerView;


        items = new ArrayList<FeedItem>();


        recyclerView = (RecyclerView) findViewById(R.id.searchpage_recyclerview);
        adapter = new FeedItemAdapter(this, items);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);


        //서버주소
        String url = "http://hanjiyoon.dothome.co.kr/loadDB.php";


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

                        String id = jsonObject.getString("title");

                        String email = jsonObject.getString("detail");

                        String image_path = jsonObject.getString("image_path");

                        //임시
                        String article_num ="1";

                        //이미지 경로의 경우 서버 IP가 제외된 주소이므로(uploads/xxxx.jpg) 바로 사용 불가.
                        image_path = "http://hanjiyoon.dothome.co.kr/app_image/" + image_path;

                        items.add(0, new FeedItem(image_path, id, email, article_num)); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);






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





    }

    public void searchFilter(String searchText) {
        ArrayList<FeedItem>filteredList=new ArrayList<>();

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








