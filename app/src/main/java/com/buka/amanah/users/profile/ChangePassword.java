package com.buka.amanah.users.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buka.amanah.R;
import com.buka.amanah.pojo.ResponseDefault;
import com.buka.amanah.users.login.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

public class ChangePassword extends AppCompatActivity {

    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText etOld, etNew, etReNew;
    TextInputLayout eiOld, eiNew, eiReNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.setLogo(R.drawable.ic_logo);
        actionbar.setTitle("Ganti Kata Sandi");

        sharedPreferences = getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mRequestQueue = Volley.newRequestQueue(this);

        etOld = findViewById(R.id.editTextOldPassword);
        etNew = findViewById(R.id.editTextNewPassword);
        etReNew = findViewById(R.id.editTextReNewPassword);

        eiOld = findViewById(R.id.textInputOldPassword);
        eiNew = findViewById(R.id.textInputNewPassword);
        eiReNew = findViewById(R.id.textInputReNewPassword);

        etOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 8) {
                    eiOld.setError("Min character length is 8");
                    eiOld.setCounterTextAppearance(R.style.CounterTextFalse);
                    eiOld.setCounterOverflowTextAppearance(R.style.CounterOverFlowFalse);
                } else {
                    eiOld.setError(null);
                    eiOld.setCounterTextAppearance(R.style.CounterTextTrue);
                    eiOld.setCounterOverflowTextAppearance(R.style.CounterOverFlowTrue);
                }
            }
        });
        etNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 8) {
                    eiNew.setError("Min character length is 8");
                    eiNew.setCounterTextAppearance(R.style.CounterTextFalse);
                    eiNew.setCounterOverflowTextAppearance(R.style.CounterOverFlowFalse);
                } else {
                    eiNew.setError(null);
                    eiNew.setCounterTextAppearance(R.style.CounterTextTrue);
                    eiNew.setCounterOverflowTextAppearance(R.style.CounterOverFlowTrue);
                }
            }
        });
        etReNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 8) {
                    eiReNew.setError("Min character length is 8");
                    eiReNew.setCounterTextAppearance(R.style.CounterTextFalse);
                    eiReNew.setCounterOverflowTextAppearance(R.style.CounterOverFlowFalse);
                } else {
                    eiReNew.setError(null);
                    eiReNew.setCounterTextAppearance(R.style.CounterTextTrue);
                    eiReNew.setCounterOverflowTextAppearance(R.style.CounterOverFlowTrue);
                }
            }
        });
    }

    public void updatePassword(View view) {
        String url = getString(R.string.host_update_password);

        // Request a string response from the provided URL.
//        if (email.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")) {
//            MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(UpdateUserProfile.this, R.style.AlertDialogTheme);
//            alertDialog.setTitle("Login Gagal");
//            alertDialog.setMessage("Mohon Input Username & Password terlebih dahulu !");
//            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            });
//            alertDialog.show();
//        } else {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("oldPassword", etOld.getText().toString());
            jsonBody.put("newPassword", etNew.getText().toString());
            jsonBody.put("newPasswordConfirmation", etReNew.getText().toString());

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    ResponseDefault message = gson.fromJson(response, ResponseDefault.class);

                    if (message.getStatus().equalsIgnoreCase("success")) {
                        MaterialDialog mDialog = new MaterialDialog.Builder(ChangePassword.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Change Password Success.\nSilakan Login kembali menggunakan Password Terbaru.")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("DONE", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        finishAffinity();
                                    }
                                })
                                .build();

                        // Show Dialog
                        mDialog.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    ResponseDefault message = gson.fromJson(new String(error.networkResponse.data, StandardCharsets.UTF_8), ResponseDefault.class);

                    if (message.getStatus().equalsIgnoreCase("validation error")) {
                        MaterialDialog mDialog = new MaterialDialog.Builder(ChangePassword.this)
                                .setTitle("Failed", TextAlignment.CENTER)
                                .setMessage("Change Password Failed.")
                                .setAnimation("6718-loading-fail-state.json")
                                .setPositiveButton("OK", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .build();

                        // Show Dialog
                        mDialog.show();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();

                    String access_token = sharedPreferences.getString("token", "");

                    headers.put("Authorization", "Bearer " + access_token);
                    headers.put("Content-Type", "application/json; charset=utf-8");

                    return headers;
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
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                }
                else {
                    super.onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
}