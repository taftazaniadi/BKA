package com.buka.amanah.view.pelanggan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.buka.amanah.model.customer_get.CustomerView;
import com.buka.amanah.view.transaksi.penerimaan.PenerimaanActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DetailPelanggan extends AppCompatActivity {

    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private TextView txtTgl, txtNama, txtHP, txtWA, txtAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pelanggan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Detail Pelanggan");

        txtTgl = findViewById(R.id.txtHariTgl);
        txtNama = findViewById(R.id.txtNamaPelanggan);
        txtHP = findViewById(R.id.txtHandphone);
        txtWA = findViewById(R.id.txtWhatsapp);
        txtAlamat = findViewById(R.id.txtAlamatPelanggan);

        mRequestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (getIntent().getStringExtra("method").equalsIgnoreCase("View")) {
            getDetailPelanggan(getIntent().getStringExtra("customerId"));
        }/* else {
            tvTgl.setText(getIntent().getStringExtra("tgl"));
            tvCust.setText(getIntent().getStringExtra("cust"));
            tvReceipt.setText(getIntent().getStringExtra("receipt"));
            tvAmount.setText(getIntent().getStringExtra("amount"));
            tvPrice.setText("Rp " + getIntent().getStringExtra("price"));
            tvTotal.setText("Rp " + getIntent().getStringExtra("total"));
        }*/
    }

    private void getDetailPelanggan(String id) {
        String url = getString(R.string.host_get_detail_customer);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("customerId", Integer.parseInt(id));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(DetailPelanggan.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    CustomerView message = gson.fromJson(response, CustomerView.class);

                    txtTgl.setText(message.getData()[0].getCreatedAt());
                    txtNama.setText(message.getData()[0].getName());
                    txtHP.setText(message.getData()[0].getPhone());
                    txtWA.setText(message.getData()[0].getWa());
                    txtAlamat.setText(message.getData()[0].getAddress());
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