package com.example.versebe.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckRequest extends StringRequest{

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://hanjiyoon.dothome.co.kr/check_id.php";
    private Map<String, String> map;


    public CheckRequest( String getUserId, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        map = new HashMap<>();
        map.put("userId",getUserId);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }



}
