package com.buka.amanah.view.users.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buka.amanah.R;
import com.buka.amanah.MainActivity;
import com.buka.amanah.model.login.BukaAuth;
import com.buka.amanah.model.user.User;
import com.buka.amanah.view.users.register.RegisterActivity;
import com.buka.amanah.view.users.resetpass.ForgotPasswordActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        ActionBar actionbar = getSupportActionBar();
//        actionbar.setIcon(R.drawable.ic_jitu);
        actionbar.setTitle("Masuk");

        sharedPreferences = getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        email = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);

        mRequestQueue = Volley.newRequestQueue(this);

        // making notification bar transparent
        changeStatusBarColor();

//        checkLogin();
    }

    public void login(View view) {
        String destination_host = getString(R.string.host_auth);
        String url = destination_host;

        // Request a string response from the provided URL.
        if (email.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")) {
            MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(LoginActivity.this, R.style.AlertDialogTheme);
            alertDialog.setTitle("Login Gagal");
            alertDialog.setMessage("Mohon Input Username & Password terlebih dahulu !");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
        } else {
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("username", email.getText().toString());
                jsonBody.put("password", password.getText().toString());
                final String mRequestBody = jsonBody.toString();

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Loading ...");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG_VOLLEY", response);

                        progressDialog.dismiss();

                        Gson gson = new Gson();
                        BukaAuth auth = gson.fromJson(response, BukaAuth.class);

                        if (auth.getStatus().equalsIgnoreCase("success")) {
                            if (!auth.getToken().isEmpty()) {
                                editor.putString("token", auth.getToken());
                                editor.commit();

                                progressDialog.dismiss();

                                getUserData();
                            }
                        } else {
                            progressDialog.dismiss();

                            MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(LoginActivity.this, R.style.AlertDialogTheme);
                            alertDialog.setTitle("Login Gagal");
                            alertDialog.setMessage("Username/Password Salah. Silakan cek kembali inputan.");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LOG_VOLLEY", error.toString());

                        progressDialog.dismiss();

                        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(LoginActivity.this, R.style.AlertDialogTheme);
                        alertDialog.setTitle("Login Gagal");
                        alertDialog.setMessage("Username/Password Salah. Silakan cek kembali inputan.");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return mRequestBody == null ? null : mRequestBody.getBytes(StandardCharsets.UTF_8);
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {

                            String responseData = new String(response.data, StandardCharsets.UTF_8);

                            responseString = responseData;

                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                // Add the request to the RequestQueue.
                mRequestQueue.add(stringRequest);
                System.out.println("RESPONSE API >>> " + stringRequest.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void checkLogin() {
        if (!sharedPreferences.getString("token", "").equalsIgnoreCase("")) {
            try {
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Loading ...");
                progressDialog.show();

                String url = getString(R.string.host_auth);

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    System.out.println(">>>> RESPONSE : " + response);

                                    Gson gson = new Gson();
                                    BukaAuth auth = gson.fromJson(response, BukaAuth.class);

                                    /* jika gagal */
                                    if (!auth.getToken().isEmpty()) {

                                        editor.putString("token", auth.getToken());
                                        editor.commit();

                                        progressDialog.dismiss();

                                        getUserData();
                                    } else {
                                        progressDialog.dismiss();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("grant_type", "refresh_token");
                        params.put("client_id", "ibnmobile");
                        params.put("refresh_token", sharedPreferences.getString("refresh_token", ""));

                        System.out.println("Params : " + params);

                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        return headers;
                    }
                };

                stringRequest.setTag("Login");
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        60000,
                        1,
                        2
                ));

                // Add the request to the RequestQueue.
                mRequestQueue.add(stringRequest);

                System.out.println("RESPONSE AUTH >>> " + stringRequest.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getUserData() {
        String url = getString(R.string.host_profile);

//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
//        progressDialog.setMessage("Loading ...");
//        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Gson gson = new Gson();
                            User dataUser = gson.fromJson(response, User.class);

//                            progressDialog.dismiss();

                            if (dataUser.getStatus().equalsIgnoreCase("success")) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
//                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();

                String token = sharedPreferences.getString("token", "");

                headers.put("Authorization", "Bearer " + token);

                return headers;
            }
        };

        stringRequest.setTag("Login");
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void userRegistration(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }

    public void forgotPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
        finish();
    }

//    public void login(View view) {
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        finish();
//    }
}