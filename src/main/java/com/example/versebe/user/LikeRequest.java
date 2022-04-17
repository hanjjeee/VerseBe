package com.example.versebe.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LikeRequest extends StringRequest{

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://hanjiyoon.dothome.co.kr/like.php";
    private Map<String, String> map;


    public LikeRequest( String userId, String poster_num,  Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();

        map.put("USER_ID",userId);
        map.put("POSTER_NUM", poster_num );

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }



}
