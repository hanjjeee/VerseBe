package com.example.versebe.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ModifyRequest_email extends StringRequest{

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://hanjiyoon.dothome.co.kr/modify_email.php";
    private Map<String, String> map;


    public ModifyRequest_email( String curEmail, String getNewEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();

        map.put("curEmail",curEmail);
        map.put("newEmail",getNewEmail);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }



}

