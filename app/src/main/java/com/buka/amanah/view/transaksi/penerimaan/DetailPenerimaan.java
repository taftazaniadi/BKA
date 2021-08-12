package com.buka.amanah.view.transaksi.penerimaan;

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
import com.buka.amanah.R;
import com.buka.amanah.model.receipt_get.ReceiptView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DetailPenerimaan extends AppCompatActivity {

    TextView tvTgl, tvCust, tvReceipt, tvAmount, tvPrice, tvTotal;
//    Button btnSelesai;

    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penerimaan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Detail Penerimaan");

        mRequestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tvTgl = findViewById(R.id.txtHariTgl);
        tvCust = findViewById(R.id.txtPelangganPenerimaan);
        tvReceipt = findViewById(R.id.txtPenerimaan);
        tvAmount = findViewById(R.id.txtJumlahPenerimaan);
        tvPrice = findViewById(R.id.txtHargaPenerimaan);
        tvTotal = findViewById(R.id.txtTotalPenerimaan);
//        btnSelesai = findViewById(R.id.btnSelesai);

        if (getIntent().getStringExtra("method").equalsIgnoreCase("View")) {
            getDetailPenerimaan(getIntent().getStringExtra("receiptId"));
        } else {
            tvTgl.setText(getIntent().getStringExtra("tgl"));
            tvCust.setText(getIntent().getStringExtra("cust"));
            tvReceipt.setText(getIntent().getStringExtra("receipt"));
            tvAmount.setText(getIntent().getStringExtra("amount"));
            tvPrice.setText("Rp " + getIntent().getStringExtra("price"));
            tvTotal.setText("Rp " + getIntent().getStringExtra("total"));
        }

//        btnSelesai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), PenerimaanActivity.class));
//                finishAffinity();
//            }
//        });
    }

    private void getDetailPenerimaan(String id) {
        String url = getString(R.string.host_receipt_detail);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("receiptId", Integer.parseInt(id));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(DetailPenerimaan.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    ReceiptView message = gson.fromJson(response, ReceiptView.class);

//                    if (message.getStatus().equalsIgnoreCase("success")) {
                        tvTgl.setText(message.getData()[0].getCreatedAt());
                        tvCust.setText(message.getData()[0].getCustomerName());
                        tvReceipt.setText(message.getData()[0].getType());
                        tvAmount.setText(message.getData()[0].getAmount());
                        tvPrice.setText("Rp " + message.getData()[0].getPrice());
                        tvTotal.setText("Rp " + message.getData()[0].getTotal());
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