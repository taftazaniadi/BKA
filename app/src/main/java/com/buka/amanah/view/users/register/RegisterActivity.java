package com.buka.amanah.view.users.register;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

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
import com.buka.amanah.model.ResponseDefault;
import com.buka.amanah.model.branch_list.BranchList;
import com.buka.amanah.model.branch_list.DataBranch;
import com.buka.amanah.model.district_list.DistrictList;
import com.buka.amanah.view.users.login.LoginActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

public class RegisterActivity extends AppCompatActivity {

    EditText etNamaLengkap, etPassword, etTelepon, etEmail;
    AutoCompleteTextView actCabang, actKecamatan;

    ArrayList<String> bebas1 = new ArrayList<>();
    ArrayList<String> bebas = new ArrayList<>();
    LinkedHashSet<String> dataSet1 = new LinkedHashSet<>();
    LinkedHashSet<String> dataSet = new LinkedHashSet<>();
    BranchList branchList = new BranchList();
    DistrictList districtList = new DistrictList();
    String id_branch;

    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* Toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Pendaftar");

        mRequestQueue = Volley.newRequestQueue(this);

        etNamaLengkap = findViewById(R.id.editTextNamaLengkap);
        etPassword = findViewById(R.id.editTextPassword);
        etTelepon = findViewById(R.id.editTextTelepon);
        etEmail = findViewById(R.id.editTextEmail);
        actCabang = findViewById(R.id.actCabang);
        actKecamatan = findViewById(R.id.actKecamatan);

        getBranchList();
        actCabang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actCabang.showDropDown();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actCabang.getWindowToken(), 0);
                return false;
            }
        });
        actCabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getCustList();
                actCabang.showDropDown();
            }
        });
        actCabang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                getCustList();
                actCabang.showDropDown();

                String itemName = String.valueOf(adapterView.getItemAtPosition(i));

                getDistrictList(itemName);

                for (DataBranch pojo : branchList.getData()) {
                    if (pojo.getBranch().equals(itemName)) {
                        id_branch = pojo.getId(); // This is the correct ID
                        break; // No need to keep looping once you found it.
                    }
                }
            }
        });

        actKecamatan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actKecamatan.showDropDown();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actKecamatan.getWindowToken(), 0);
                return false;
            }
        });
        actKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getCustList();
                actKecamatan.showDropDown();
            }
        });

        // making notification bar transparent
        changeStatusBarColor();
    }

    public void register(View view) {

        String url = getString(R.string.host_register);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", etNamaLengkap.getText().toString());
            jsonBody.put("password", etPassword.getText().toString());
            jsonBody.put("address", "");
            jsonBody.put("email", etEmail.getText().toString());
            jsonBody.put("phone", etTelepon.getText().toString());
            jsonBody.put("branch", Integer.parseInt(id_branch));
            jsonBody.put("district", actKecamatan.getText().toString());
            jsonBody.put("role", "User");
            final String mRequestBody = jsonBody.toString();

            final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    ResponseDefault register = gson.fromJson(response, ResponseDefault.class);

                    if (register.getStatus().equalsIgnoreCase("success")) {
                        MaterialDialog mDialog = new MaterialDialog.Builder(RegisterActivity.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Data has been Saved")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("Selesai", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(i);
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
                        String data;

                        if (message.getResponse_message().contains("|")) {
                            data = message.getResponse_message().replace("|", "\n");
                        } else {
                            data = message.getResponse_message();
                        }

                        MaterialDialog mDialog = new MaterialDialog.Builder(RegisterActivity.this)
                                .setTitle("Failed", TextAlignment.CENTER)
                                .setMessage(data)
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

    private void getBranchList() {
        String url = getString(R.string.host_get_branch_list);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        branchList = gson.fromJson(response, BranchList.class);

                        for (int i = 0; i < branchList.getData().length; i++) {
                            // Remove Duplicate Data Adapter
                            dataSet1.addAll(Collections.singleton(branchList.getData()[i].getBranch()));
                            bebas1.clear();
                            bebas1.addAll(dataSet1);
                        }

                        // Creates the adapter and set it to the AutoCompleteTextView
                        ArrayAdapter adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, bebas1);
                        actCabang.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getDistrictList(String cabang) {
        String url = getString(R.string.host_get_kecamatan_list) + "=" + cabang;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        districtList = gson.fromJson(response, DistrictList.class);

                        for (int i = 0; i < districtList.getDistrict().length; i++) {
                            // Remove Duplicate Data Adapter
                            dataSet.addAll(Collections.singleton(districtList.getDistrict()[i]));
                            bebas.clear();
                            bebas.addAll(dataSet);
                        }

                        // Creates the adapter and set it to the AutoCompleteTextView
                        ArrayAdapter adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, bebas);
                        actKecamatan.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
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

    public void userLogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}