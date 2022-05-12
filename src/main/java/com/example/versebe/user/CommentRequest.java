package com.example.versebe.user;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CommentRequest extends Request {


    private Map mMap;
    private Response.Listener mListener;


    public CommentRequest(String url, Response.Listener listener, Response.ErrorListener errorListener, Map map) {
        super(Request.Method.POST, url, errorListener);
        mListener=listener;
        mMap=map;

        // TODO Auto-generated constructor stub
    }
    @Override
    protected Map getParams() throws AuthFailureError {
        // TODO Auto-generated method stub
        return mMap;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        mListener.onResponse(response);
    }



    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

