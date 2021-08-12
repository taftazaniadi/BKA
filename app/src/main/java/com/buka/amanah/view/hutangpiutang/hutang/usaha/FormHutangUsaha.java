package com.buka.amanah.view.hutangpiutang.hutang.usaha;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.buka.amanah.model.biaya_list.BiayaList;
import com.buka.amanah.model.biaya_list.Data;
import com.buka.amanah.model.cust_list.CustList;
import com.buka.amanah.model.cust_list.DataCust;
import com.buka.amanah.model.detail_biaya.DataDetail;
import com.buka.amanah.model.detail_biaya.DetailBiaya;
import com.buka.amanah.model.hutang_usaha.HutangUsaha;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

public class FormHutangUsaha extends AppCompatActivity {

    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final Calendar myCalendar = Calendar.getInstance();

    private EditText etTgl, etCatatan, etTotal;
    AutoCompleteTextView actPelanggan, actBiaya, actDetailBiaya;
    ImageButton etPickTgl;
    Button btnBatal, btnHutangUsaha;
    String id_biaya, id_cust, id_detail;
    int a, b;
    CustList custList = new CustList();
    BiayaList biayaList = new BiayaList();
    DetailBiaya detailBiayaList = new DetailBiaya();
    ArrayList<String> bebas = new ArrayList<>();
    ArrayList<String> bebas1 = new ArrayList<>();
    ArrayList<String> bebas2 = new ArrayList<>();
    LinkedHashSet<String> dataSet = new LinkedHashSet<>();
    LinkedHashSet<String> dataSet1 = new LinkedHashSet<>();
    LinkedHashSet<String> dataSet2 = new LinkedHashSet<>();
    private String dataCust, dataBiaya, dataDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hutang_usaha);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        etTgl = findViewById(R.id.editTextTglHutangUsaha);
        etPickTgl = (ImageButton) findViewById(R.id.buttonPickTglHutangUsaha);
        etTotal = findViewById(R.id.editTextTotal);
        etCatatan = findViewById(R.id.editTextCatatanHutangUsaha);
        actPelanggan = (AutoCompleteTextView) findViewById(R.id.actPelanggan);
        actBiaya = (AutoCompleteTextView) findViewById(R.id.actBiaya);
        actDetailBiaya = (AutoCompleteTextView) findViewById(R.id.actDetailBiaya);
        btnBatal = findViewById(R.id.btn_cancel_hutang_usaha);
        btnHutangUsaha = findViewById(R.id.btn_form_hutang_usaha);

        // making notification bar transparent
        changeStatusBarColor();

        mRequestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (getIntent().getStringExtra("method").equalsIgnoreCase("Edit")) {
            actionbar.setTitle("Edit Hutang Usaha");

            getDetailHutangUsaha(getIntent().getStringExtra("debtId"));

            btnHutangUsaha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateHutangUsaha();
                }
            });
        } else {
            actionbar.setTitle("Tambah Hutang Usaha");

            btnHutangUsaha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addHutangUsaha();
                }
            });
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etPickTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(FormHutangUsaha.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        getCustList();
        actPelanggan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actPelanggan.showDropDown();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actPelanggan.getWindowToken(), 0);
                return false;
            }
        });
        actPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actPelanggan.showDropDown();
            }
        });
        actPelanggan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getCustList();
                actPelanggan.showDropDown();

                String itemName = String.valueOf(adapterView.getItemAtPosition(i));

                for (DataCust pojo : custList.getData()) {
                    if (pojo.getCustomerName().equals(itemName)) {
                        id_cust = pojo.getId(); // This is the correct ID
                        break; // No need to keep looping once you found it.
                    }
                }
            }
        });

        getBiayaList();
        actBiaya.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actBiaya.showDropDown();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actBiaya.getWindowToken(), 0);
                return false;
            }
        });
        actBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getReceiptList();
                actBiaya.showDropDown();
            }
        });
        actBiaya.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getBiayaList();
                actBiaya.showDropDown();

                String itemName = String.valueOf(adapterView.getItemAtPosition(i));

                for (Data pojo : biayaList.getData()) {
                    if (pojo.getName().equals(itemName)) {
                        id_biaya = pojo.getId(); // This is the correct ID
                        break; // No need to keep looping once you found it.
                    }
                }
            }
        });

        getDetailBiayaList();
        actDetailBiaya.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actDetailBiaya.showDropDown();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actDetailBiaya.getWindowToken(), 0);
                return false;
            }
        });
        actDetailBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actDetailBiaya.showDropDown();
            }
        });
        actDetailBiaya.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getBiayaList();
                actDetailBiaya.showDropDown();

                String itemName = String.valueOf(adapterView.getItemAtPosition(i));

                for (DataDetail pojo : detailBiayaList.getData()) {
                    if (pojo.getName().equals(itemName)) {
                        id_detail = pojo.getId(); // This is the correct ID
                        break; // No need to keep looping once you found it.
                    }
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HutangUsahaActivity.class));
                finish();
            }
        });
    }

    private void getBiayaList() {
        String url = getString(R.string.host_get_disbursement_list);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        biayaList = gson.fromJson(response, BiayaList.class);

                        for (int i = 0; i < biayaList.getData().length; i++) {
                            // Remove Duplicate Data Adapter
                            dataSet2.addAll(Collections.singleton(biayaList.getData()[i].getName()));
                            bebas2.clear();
                            bebas2.addAll(dataSet2);
                        }

                        // Creates the adapter and set it to the AutoCompleteTextView
                        ArrayAdapter adapter = new ArrayAdapter<String>(FormHutangUsaha.this, android.R.layout.simple_spinner_dropdown_item, bebas2);
                        actBiaya.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormHutangUsaha.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(FormHutangUsaha.this);
        requestQueue.add(stringRequest);
    }

    private void getDetailBiayaList() {
        String url = getString(R.string.host_get_detail_disbursement_list);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        detailBiayaList = gson.fromJson(response, DetailBiaya.class);

                        for (int i = 0; i < detailBiayaList.getData().length; i++) {
                            // Remove Duplicate Data Adapter
                            dataSet.addAll(Collections.singleton(detailBiayaList.getData()[i].getName()));
                            bebas.clear();
                            bebas.addAll(dataSet);
                        }

                        // Creates the adapter and set it to the AutoCompleteTextView
                        ArrayAdapter adapter = new ArrayAdapter<String>(FormHutangUsaha.this, android.R.layout.simple_spinner_dropdown_item, bebas);
                        actDetailBiaya.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormHutangUsaha.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(FormHutangUsaha.this);
        requestQueue.add(stringRequest);
    }

    private void getCustList() {
        String url = getString(R.string.host_get_customer_list);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        custList = gson.fromJson(response, CustList.class);

                        for (int i = 0; i < custList.getData().length; i++) {
                            // Remove Duplicate Data Adapter
                            dataSet1.addAll(Collections.singleton(custList.getData()[i].getCustomerName()));
                            bebas1.clear();
                            bebas1.addAll(dataSet1);
                        }

                        // Creates the adapter and set it to the AutoCompleteTextView
                        ArrayAdapter adapter = new ArrayAdapter<String>(FormHutangUsaha.this, android.R.layout.simple_spinner_dropdown_item, bebas1);
                        actPelanggan.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormHutangUsaha.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(FormHutangUsaha.this);
        requestQueue.add(stringRequest);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etTgl.setText(sdf.format(myCalendar.getTime()));
    }

    private void getDetailHutangUsaha(String id) {
        String url = getString(R.string.host_debt_bussiness_detail);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("debtId", Integer.parseInt(id));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormHutangUsaha.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    HutangUsaha message = gson.fromJson(response, HutangUsaha.class);

                    etTgl.setText(message.getData()[0].getCreatedAt());
                    etTotal.setText(message.getData()[0].getDebtAmount().replace(",", ""));
                    etCatatan.setText(message.getData()[0].getDetail());
                    actPelanggan.setText(message.getData()[0].getCustomerName());
                    actBiaya.setText(message.getData()[0].getDebtTypeName());
                    actDetailBiaya.setText(message.getData()[0].getDebtTypeOptName());

                    dataCust = message.getData()[0].getCustomerId();
                    dataBiaya = message.getData()[0].getDebtType();
                    dataDetail = message.getData()[0].getDebtTypeOpt();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());

                    progressDialog.dismiss();
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
    }

    public void addHutangUsaha() {
        String url = getString(R.string.host_debt_bussiness_add);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("date", etTgl.getText().toString() + " 00:00:00");
            jsonBody.put("customerId", Integer.parseInt(id_cust));
            jsonBody.put("debtType", Integer.parseInt(id_biaya));
            jsonBody.put("debtTypeOpt", Integer.parseInt(id_detail));
            jsonBody.put("amount", Integer.parseInt(etTotal.getText().toString()));
            jsonBody.put("detail", etCatatan.getText().toString());

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormHutangUsaha.this);
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(FormHutangUsaha.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Data has been Saved")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("Selesai", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), HutangUsahaActivity.class);
                                        startActivity(i);
                                        finishAffinity();
                                    }
                                })
                                .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), FormHutangUsaha.class);
                                        i.putExtra("method", "Add");
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

                        MaterialDialog mDialog = new MaterialDialog.Builder(FormHutangUsaha.this)
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
    }

    public void updateHutangUsaha() {
        String url = getString(R.string.host_debt_bussiness_update);
        final String idCust, idBiaya, idDetail;

        if (id_cust == null || id_cust.isEmpty()) {
            idCust = dataCust;
        } else {
            idCust = id_cust;
        }

        if (id_biaya == null || id_biaya.isEmpty()) {
            idBiaya = dataBiaya;
        } else {
            idBiaya = id_biaya;
        }

        if (id_detail == null || id_biaya.isEmpty()) {
            idDetail = dataDetail;
        } else {
            idDetail = id_detail;
        }

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("debtId", Integer.parseInt(getIntent().getStringExtra("debtId")));
            jsonBody.put("date", etTgl.getText().toString() + " 00:00:00");
            jsonBody.put("customerId", Integer.parseInt(idCust));
            jsonBody.put("debtType", Integer.parseInt(idBiaya));
            jsonBody.put("debtTypeOpt", Integer.parseInt(idDetail));
            jsonBody.put("amount", Integer.parseInt(etCatatan.getText().toString()));
            jsonBody.put("detail", etCatatan.getText().toString());

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormHutangUsaha.this);
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(FormHutangUsaha.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Data has been Updated")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("Selesai", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), HutangUsahaActivity.class);
                                        startActivity(i);
                                        finishAffinity();
                                    }
                                })
                                .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), FormHutangUsaha.class);
                                        i.putExtra("method", "Add");
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

                        MaterialDialog mDialog = new MaterialDialog.Builder(FormHutangUsaha.this)
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

    @Override
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        super.onBackPressed();
        startActivity(new Intent(this, HutangUsahaActivity.class));
        finish();
    }
}