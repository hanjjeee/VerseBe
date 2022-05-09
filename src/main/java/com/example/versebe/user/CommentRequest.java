package com.example.versebe.user;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class CommentRequest extends JsonArrayRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://hanjiyoon.dothome.co.kr/comments.php";

    private Map<String, String> map;

    public CommentRequest(String article_num, Response.Listener<JSONArray> listener) {

        super(URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error.getLocalizedMessage());
                System.out.println("뮹");
            }
        });

        map = new HashMap<>();
        map.put("ARTICLE_NUM", article_num);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}

