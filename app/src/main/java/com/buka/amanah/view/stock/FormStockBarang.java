package com.buka.amanah.view.stock;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.buka.amanah.MainActivity;
import com.buka.amanah.R;
import com.buka.amanah.model.ResponseDefault;
import com.buka.amanah.model.cust_list.CustList;
import com.buka.amanah.model.receipt_get.ReceiptView;
import com.buka.amanah.model.receipt_list.DataReceipt;
import com.buka.amanah.model.stock_list.StockList;
import com.buka.amanah.model.stok_get.DataStok;
import com.buka.amanah.model.stok_get.StokView;
import com.buka.amanah.view.transaksi.penerimaan.FormPenerimaan;
import com.buka.amanah.view.transaksi.penerimaan.PenerimaanActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

public class FormStockBarang extends AppCompatActivity {

    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button btnBatal, btnStock;

    StokView stockView = new StokView();
    String id_barang;
    private EditText etJumlah, etHarga;
    AutoCompleteTextView actBarang;
    private String dataBarang;
    ArrayList<String> bebas1 = new ArrayList<>();
    LinkedHashSet<String> dataSet1 = new LinkedHashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_barang);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        etJumlah = findViewById(R.id.editTextJumlahBarang);
        etHarga = findViewById(R.id.editTextHargaBarang);
        actBarang = findViewById(R.id.actBarang);
        btnBatal = findViewById(R.id.btn_cancel_stock);
        btnStock = findViewById(R.id.btn_form_stock);

        // making notification bar transparent
        changeStatusBarColor();

        mRequestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (getIntent().getStringExtra("method").equalsIgnoreCase("Edit")) {
            actionbar.setTitle("Edit Stock");

            getDetailStock(getIntent().getStringExtra("stockId"));

            btnStock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateStock();
                }
            });
        } else {
            actionbar.setTitle("Tambah Stock");

            btnStock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addStock();
                }
            });
        }

        getStockList();
        actBarang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actBarang.showDropDown();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actBarang.getWindowToken(), 0);
                return false;
            }
        });
        actBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actBarang.showDropDown();
            }
        });
        actBarang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                getReceiptList();
                actBarang.showDropDown();

                String itemName = String.valueOf(adapterView.getItemAtPosition(i));

                for (DataStok pojo : stockView.getData()) {
                    if (pojo.getProductName().equals(itemName)) {
                        id_barang = pojo.getId(); // This is the correct ID
                        break; // No need to keep looping once you found it.
                    }
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finishAffinity();
            }
        });
    }

    private void getStockList() {
        String url = getString(R.string.host_get_stock_list);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        stockView = gson.fromJson(response, StokView.class);

                        for (int i = 0; i < stockView.getData().length; i++) {
                            // Remove Duplicate Data Adapter
                            dataSet1.addAll(Collections.singleton(stockView.getData()[i].getProductName()));
                            bebas1.clear();
                            bebas1.addAll(dataSet1);
                        }

                        // Creates the adapter and set it to the AutoCompleteTextView
                        ArrayAdapter adapter = new ArrayAdapter<String>(FormStockBarang.this, android.R.layout.simple_spinner_dropdown_item, bebas1);
                        actBarang.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormStockBarang.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(FormStockBarang.this);
        requestQueue.add(stringRequest);
    }

    public void addStock() {
        String url = getString(R.string.host_add_stock);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("productId", Integer.parseInt(id_barang));
            jsonBody.put("stock", Integer.parseInt(etJumlah.getText().toString()));
            jsonBody.put("price", Integer.parseInt(etHarga.getText().toString()));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormStockBarang.this);
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(FormStockBarang.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Data has been Saved")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("Selesai", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        //startActivity(i);
                                        //finishAffinity();

                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), FormStockBarang.class);
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

                        MaterialDialog mDialog = new MaterialDialog.Builder(FormStockBarang.this)
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

    public void updateStock() {
        String url = getString(R.string.host_update_stock);
        final String idBarang;

        if (id_barang == null || id_barang.isEmpty()) {
            idBarang = dataBarang;
        } else {
            idBarang = id_barang;
        }

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("stockId", Integer.parseInt(getIntent().getStringExtra("stockId")));
            jsonBody.put("productId", Integer.parseInt(idBarang));
            jsonBody.put("stock", Integer.parseInt(etJumlah.getText().toString()));
            jsonBody.put("price", Integer.parseInt(etHarga.getText().toString()));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormStockBarang.this);
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(FormStockBarang.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Data has been Updated")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("Selesai", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        //startActivity(i);
                                        //finishAffinity();
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), FormStockBarang.class);
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

                        MaterialDialog mDialog = new MaterialDialog.Builder(FormStockBarang.this)
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

    private void getDetailStock(String id) {
        String url = getString(R.string.host_stock_detail);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("stockId", Integer.parseInt(id));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormStockBarang.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    StokView message = gson.fromJson(response, StokView.class);

//                    if (message.getStatus().equalsIgnoreCase("success")) {
                    etJumlah.setText(message.getData()[0].getCreatedAt());
                    etHarga.setText(message.getData()[0].getPrice().replace(",", ""));
                    actBarang.setText(message.getData()[0].getProductName());

                    dataBarang = message.getData()[0].getProductId();

//                    }
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

}