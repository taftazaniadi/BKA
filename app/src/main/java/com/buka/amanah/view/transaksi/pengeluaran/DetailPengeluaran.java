package com.buka.amanah.view.transaksi.pengeluaran;

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
import com.buka.amanah.model.disbursement_get.DisbursementView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DetailPengeluaran extends AppCompatActivity {

    TextView tvTgl, tvBiaya, tvCategory, tvKet, tvTotal;
//    Button btnSelesai;

    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengeluaran);

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
        tvBiaya = findViewById(R.id.txtBiayaPengeluaran);
        tvCategory = findViewById(R.id.txtDetailBiaya);
        tvKet = findViewById(R.id.txtKeterangan);
        tvTotal = findViewById(R.id.txtTotalPengeluaran);
//        btnSelesai = findViewById(R.id.btnSelesai);

        if (getIntent().getStringExtra("method").equalsIgnoreCase("View")) {
            getDetailPengeluaran(getIntent().getStringExtra("disbursementId"));
        } else {
            tvTgl.setText(getIntent().getStringExtra("tgl"));
            tvBiaya.setText(getIntent().getStringExtra("biaya"));
            tvCategory.setText(getIntent().getStringExtra("category"));
            tvKet.setText(getIntent().getStringExtra("detail"));
            tvTotal.setText("Rp " + getIntent().getStringExtra("total"));
        }

//        btnSelesai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MaterialDialog mDialog = new MaterialDialog.Builder(DetailPengeluaran.this)
//                        .setTitle("Success", TextAlignment.CENTER)
//                        .setMessage("Data has been Saved")
//                        .setAnimation("6717-loading-passed-state.json")
//                        .setPositiveButton("Done", new MaterialDialog.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                                dialogInterface.dismiss();
//
//                                startActivity(new Intent(getApplicationContext(), PengeluaranActivity.class));
//                                finish();
//                            }
//                        })
//                        .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                                dialogInterface.dismiss();
//
//                                startActivity(new Intent(getApplicationContext(), FormPengeluaran.class));
//                                finish();
//                            }
//                        })
//                        .build();
//
//                // Show Dialog
//                mDialog.show();
//            }
//        });
    }

    private void getDetailPengeluaran(String id) {
        String url = getString(R.string.host_disbursement_detail);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("disbursementId", Integer.parseInt(id));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(DetailPengeluaran.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    DisbursementView message = gson.fromJson(response, DisbursementView.class);

                    tvTgl.setText(message.getData()[0].getCreatedAt());
                    tvBiaya.setText(message.getData()[0].getType());
                    tvCategory.setText(message.getData()[0].getCategory());
                    tvKet.setText(message.getData()[0].getDetail());
                    tvTotal.setText("Rp " + message.getData()[0].getTotal());
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