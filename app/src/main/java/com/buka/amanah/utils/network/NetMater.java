package com.buka.amanah.utils.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.buka.amanah.model.ResponseFailed;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//
// Created by OwlDev23
//

public class NetMater {

    private final RequestQueue requestQueue;

    public NetMater(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public <T> void getData(String token, String url, Class<T> parse, ResponseCallback<T> callback){
        Log.e("NetMaster", "Url : " + url);
        callback.onLoading(true);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.e("NetMaster", "Success : " + response.toString());
                callback.onLoading(false);
                callback.onSuccess(new Gson().fromJson(response.toString(), parse));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("NetMaster", "Error : " + error.toString());
                callback.onLoading(false);
                if (error.networkResponse == null){
                    callback.onError(error.getLocalizedMessage(), 0);
                }else {
                    ResponseFailed failed = new Gson().fromJson(new String(error.networkResponse.data, StandardCharsets.UTF_8), ResponseFailed.class);
                    if (failed != null){
                        callback.onError(failed.getError(), error.networkResponse.statusCode);
                    }else {
                        callback.onError(error.toString(), error.networkResponse.statusCode);
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        requestQueue.add(request);
    }

    public <T> void postData(String token, String url, JSONObject body, Class<T> parse, ResponseCallback<T> callback){
        Log.e("NetMaster", "Url : " + url);
        callback.onLoading(true);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener(){
            @Override
            public void onResponse(Object response) {
                Log.e("NetMaster", "Success : " + response.toString());
                callback.onLoading(false);
                callback.onSuccess(new Gson().fromJson(response.toString(), parse));
            }
        },new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("NetMaster", "Error : " + error.toString());
                callback.onLoading(false);
                if (error.networkResponse == null){
                    callback.onError(error.getLocalizedMessage(), 0);
                }else {
                    ResponseFailed failed = new Gson().fromJson(new String(error.networkResponse.data, StandardCharsets.UTF_8), ResponseFailed.class);
                    if (failed != null){
                        callback.onError(failed.getError(), error.networkResponse.statusCode);
                    }else {
                        callback.onError(error.toString(), error.networkResponse.statusCode);
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
