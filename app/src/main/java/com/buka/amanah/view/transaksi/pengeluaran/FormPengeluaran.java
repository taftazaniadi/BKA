package com.buka.amanah.view.transaksi.pengeluaran;

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
import com.buka.amanah.model.biaya_list.Data;
import com.buka.amanah.model.biaya_list.BiayaList;
import com.buka.amanah.model.detail_biaya.DataDetail;
import com.buka.amanah.model.detail_biaya.DetailBiaya;
import com.buka.amanah.model.disbursement_get.DisbursementView;
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

public class FormPengeluaran extends AppCompatActivity {

    public static final Calendar myCalendar = Calendar.getInstance();
    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AutoCompleteTextView actBiaya, actDetailBiaya;
    ImageButton etPickTgl;
    Button btnBatal, btnPengeluaran;
    int a, b;
    BiayaList biayaList = new BiayaList();
    DetailBiaya detailBiayaList = new DetailBiaya();
    ArrayList<String> bebas = new ArrayList<>();
    ArrayList<String> bebas1 = new ArrayList<>();
    LinkedHashSet<String> dataSet = new LinkedHashSet<>();
    LinkedHashSet<String> dataSet1 = new LinkedHashSet<>();
    String id_biaya, id_detail;
    private EditText etTgl, etTotal, etKeterangan;
    private String dataBiaya, dataDetailBiaya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pengeluaran);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        etTgl = findViewById(R.id.editTextTglPengeluaran);
        etPickTgl = (ImageButton) findViewById(R.id.buttonPickTglPengeluaran);
        etTotal = findViewById(R.id.editTextTotalPengeluaran);
        etKeterangan = findViewById(R.id.editTextKeteranganPengeluaran);
        actBiaya = (AutoCompleteTextView) findViewById(R.id.actBiaya);
        actDetailBiaya = (AutoCompleteTextView) findViewById(R.id.actDetailBiaya);
        btnBatal = findViewById(R.id.btn_cancel_pengeluaran);
        btnPengeluaran = findViewById(R.id.btn_form_pengeluaran);

        // making notification bar transparent
        changeStatusBarColor();

        mRequestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (getIntent().getStringExtra("method").equalsIgnoreCase("Edit")) {
            actionbar.setTitle("Edit Pengeluaran");

            getDetailPengeluaran(getIntent().getStringExtra("disbursementId"));

            btnPengeluaran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updatePengeluaran();
                }
            });
        } else {
            actionbar.setTitle("Tambah Pengeluaran");

            btnPengeluaran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addPengeluaran();
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
                new DatePickerDialog(FormPengeluaran.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

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
//                getBiayaList();
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
//                getCustList();
                actDetailBiaya.showDropDown();
            }
        });
        actDetailBiaya.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                getBiayaList();
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
                startActivity(new Intent(getApplicationContext(), PengeluaranActivity.class));
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
                            dataSet.addAll(Collections.singleton(biayaList.getData()[i].getName()));
                            bebas.clear();
                            bebas.addAll(dataSet);
                        }

                        // Creates the adapter and set it to the AutoCompleteTextView
                        ArrayAdapter adapter = new ArrayAdapter<String>(FormPengeluaran.this, android.R.layout.simple_spinner_dropdown_item, bebas);
                        actBiaya.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormPengeluaran.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(FormPengeluaran.this);
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
                            dataSet1.addAll(Collections.singleton(detailBiayaList.getData()[i].getName()));
                            bebas1.clear();
                            bebas1.addAll(dataSet1);
                        }

                        // Creates the adapter and set it to the AutoCompleteTextView
                        ArrayAdapter adapter = new ArrayAdapter<String>(FormPengeluaran.this, android.R.layout.simple_spinner_dropdown_item, bebas1);
                        actDetailBiaya.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormPengeluaran.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(FormPengeluaran.this);
        requestQueue.add(stringRequest);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etTgl.setText(sdf.format(myCalendar.getTime()));
    }

    private void getDetailPengeluaran(String id) {
        String url = getString(R.string.host_disbursement_detail);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("disbursementId", Integer.parseInt(id));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormPengeluaran.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    DisbursementView message = gson.fromJson(response, DisbursementView.class);

                    etTgl.setText(message.getData()[0].getCreatedAtRaw().substring(0, 10));
                    etTotal.setText(message.getData()[0].getTotal().replace(",", ""));
                    etKeterangan.setText(message.getData()[0].getDetail());
                    actBiaya.setText(message.getData()[0].getType());
                    actDetailBiaya.setText(message.getData()[0].getCategory());

                    dataBiaya = message.getData()[0].getTypeId();
                    dataDetailBiaya = message.getData()[0].getCategoryId();
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

    public void addPengeluaran() {
        String url = getString(R.string.host_disbursement);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("date", etTgl.getText().toString() + " 00:00:00");
            jsonBody.put("type", Integer.parseInt(id_biaya));
            jsonBody.put("category", Integer.parseInt(id_detail));
            jsonBody.put("total", Integer.parseInt(etTotal.getText().toString()));
            jsonBody.put("detail", etKeterangan.getText().toString());

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormPengeluaran.this);
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(FormPengeluaran.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Data has been Saved")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("Selesai", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), PengeluaranActivity.class);
//                                        i.putExtra("tgl", etTgl.getText().toString());
//                                        i.putExtra("biaya", actBiaya.getText().toString());
//                                        i.putExtra("category", actDetailBiaya.getText().toString());
//                                        i.putExtra("total", etTotal.getText().toString());
//                                        i.putExtra("detail", etKeterangan.getText().toString());
                                        startActivity(i);
                                        finishAffinity();
                                    }
                                })
                                .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), FormPengeluaran.class);
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

                        MaterialDialog mDialog = new MaterialDialog.Builder(FormPengeluaran.this)
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
//        }
    }

    public void updatePengeluaran() {
        String url = getString(R.string.host_update_disbursement);
        final String idDetailBiaya, idBiaya;

        if (id_biaya == null || id_biaya.isEmpty()) {
            idBiaya = dataBiaya;
        } else {
            idBiaya = id_biaya;
        }

        if (id_detail == null || id_detail.isEmpty()) {
            idDetailBiaya = dataDetailBiaya;
        } else {
            idDetailBiaya = id_detail;
        }

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("disbursementId", Integer.parseInt(getIntent().getStringExtra("disbursementId")));
            jsonBody.put("date", etTgl.getText().toString() + " 00:00:00");
            jsonBody.put("type", Integer.parseInt(idBiaya));
            jsonBody.put("category", Integer.parseInt(idDetailBiaya));
            jsonBody.put("total", Integer.parseInt(etTotal.getText().toString()));
            jsonBody.put("detail", etKeterangan.getText().toString());

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormPengeluaran.this);
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(FormPengeluaran.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Data has been Saved")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("Selesai", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), PengeluaranActivity.class);
//                                        i.putExtra("method", "Update");
//                                        i.putExtra("tgl", etTgl.getText().toString());
//                                        i.putExtra("cust", actPelanggan.getText().toString());
//                                        i.putExtra("receipt", actPenerimaan.getText().toString());
//                                        i.putExtra("amount", etJumlah.getText().toString());
//                                        i.putExtra("price", etHarga.getText().toString());
//                                        i.putExtra("total", etTotal.getText().toString());
                                        startActivity(i);
                                        finishAffinity();
                                    }
                                })
                                .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), FormPengeluaran.class);
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

                        MaterialDialog mDialog = new MaterialDialog.Builder(FormPengeluaran.this)
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
//        }
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
        startActivity(new Intent(this, PengeluaranActivity.class));
        finish();
    }
}