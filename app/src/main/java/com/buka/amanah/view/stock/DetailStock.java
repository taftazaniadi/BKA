package com.buka.amanah.view.stock;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import com.buka.amanah.model.receipt_get.ReceiptView;
import com.buka.amanah.model.stok_get.StokView;
import com.buka.amanah.view.transaksi.penerimaan.DetailPenerimaan;
import com.buka.amanah.view.transaksi.penerimaan.PenerimaanActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DetailStock extends AppCompatActivity {

    TextView tvNama, tvJumlah, tvHarga;

    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stock);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Detail Stok Barang");

        mRequestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tvNama = findViewById(R.id.txtNamaBarang);
        tvJumlah = findViewById(R.id.txtJumlahBarang);
        tvHarga = findViewById(R.id.txtHargaBarang);

        if (getIntent().getStringExtra("method").equalsIgnoreCase("View")) {
            getDetailStock(getIntent().getStringExtra("stockId"));
        }/* else {
            tvNama.setText(getIntent().getStringExtra("tgl"));
            tvJumlah.setText(getIntent().getStringExtra("cust"));
            tvHarga.setText(getIntent().getStringExtra("receipt"));
            tvAmount.setText(getIntent().getStringExtra("amount"));
            tvPrice.setText("Rp " + getIntent().getStringExtra("price"));
            tvTotal.setText("Rp " + getIntent().getStringExtra("total"));
        }*/

    }

    private void getDetailStock(String id) {
        String url = getString(R.string.host_stock_detail);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("stockId", Integer.parseInt(id));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(DetailStock.this);
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
                    tvNama.setText(message.getData()[0].getProductName());
                    tvJumlah.setText(message.getData()[0].getStock());
                    tvHarga.setText("Rp " + message.getData()[0].getPrice());

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


}