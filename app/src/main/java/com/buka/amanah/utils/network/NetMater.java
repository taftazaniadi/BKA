package com.buka.amanah.utils.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

//
// Created by OwlDev23
//

public class NetMater {

    private final RequestQueue requestQueue;

    public NetMater(Context context){
        requestQueue = Volley.newRequestQueue(context);

    }

    public <T> void getData(String token, String url, Class<T> parse, ResponseCallback<T> callback){
        callback.onLoading(true);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onLoading(false);
                Type collectionType = new TypeToken<T>(){}.getType();
                callback.onSuccess(new Gson().fromJson(response, collectionType));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onLoading(false);
                callback.onError(error.networkResponse.toString(), error.networkResponse.statusCode);
            }
        });
        requestQueue.add(request);
    }

    public <T> void postData(String token, String url, JSONObject body, Class<T> parse, ResponseCallback<T> callback){
        callback.onLoading(true);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener(){
            @Override
            public void onResponse(Object response) {
                callback.onLoading(false);
                Type collectionType = new TypeToken<T>(){}.getType();
                callback.onSuccess(new Gson().fromJson(response.toString(), collectionType));
            }
        },new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onLoading(false);

            }
        });
        requestQueue.add(request);
    }
}
