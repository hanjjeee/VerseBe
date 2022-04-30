package com.example.versebe.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://hanjiyoon.dothome.co.kr/comments.php";

    private Map<String, Integer> map;

    public CommentRequest(int article_num, String type, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ARTICLE_NUM", article_num);


    }



}