package com.example.versebe.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentInputRequest extends StringRequest{

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://hanjiyoon.dothome.co.kr/comment_input.php";
    private Map<String, String> map;


    public CommentInputRequest(String article_num, String type, String content, String cur_user_id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("article_num", article_num);
        map.put("type",type);
        map.put("content", content);
        map.put("cur_user_id", cur_user_id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }



}

