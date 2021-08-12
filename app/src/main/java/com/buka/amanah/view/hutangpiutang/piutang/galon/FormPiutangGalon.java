package com.buka.amanah.view.hutangpiutang.piutang.galon;

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
import android.text.Editable;
import android.text.TextWatcher;
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
import com.buka.amanah.model.cust_list.CustList;
import com.buka.amanah.model.cust_list.DataCust;
import com.buka.amanah.model.receipt_get.ReceiptView;
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

public class FormPiutangGalon extends AppCompatActivity {
    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final Calendar myCalendar = Calendar.getInstance();

    AutoCompleteTextView actPelanggan;
    ImageButton etPickTgl;
    Button btnBatal, btnPiutangGalon;
    private EditText etTgl, etJumlah, etHarga, etTotal;
    int a, b;
    CustList custList = new CustList();
    ArrayList<String> bebas = new ArrayList<>();
    LinkedHashSet<String> dataSet = new LinkedHashSet<>();
    String id_cust;
    private String dataCust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_piutang_galon);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        etTgl = findViewById(R.id.editTextTglPiutangGalon);
        etPickTgl = (ImageButton) findViewById(R.id.buttonPickTglPiutangGalon);
        etJumlah = findViewById(R.id.editTextJumlahPiutangGalon);
        etHarga = findViewById(R.id.editTextHargaPiutangGalon);
        etTotal = findViewById(R.id.editTextTotal);
        etTotal.setEnabled(false);
        actPelanggan = (AutoCompleteTextView) findViewById(R.id.actPelanggan);
        btnBatal = findViewById(R.id.btn_cancel_piutang_galon);
        btnPiutangGalon = findViewById(R.id.btn_form_piutang_galon);

        // making notification bar transparent
        changeStatusBarColor();

        mRequestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (getIntent().getStringExtra("method").equalsIgnoreCase("Edit")) {
            actionbar.setTitle("Edit Piutang Galon");

            getDetailPiutangGalon(getIntent().getStringExtra("debtId"));

            btnPiutangGalon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updatePiutangGalon();
                }
            });
        } else {
            actionbar.setTitle("Tambah Piutang Galon");

            btnPiutangGalon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addPiutangGalon();
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
                new DatePickerDialog(FormPiutangGalon.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        etJumlah.addTextChangedListener(new TextChangedListener() {

            @Override
            public void numberEntered(int number) {
                a = number;
                updateTotal();
            }
        });

        etHarga.addTextChangedListener(new TextChangedListener() {

            @Override
            public void numberEntered(int number) {
                b = number;
                updateTotal();
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

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PiutangGalonActivity.class));
                finish();
            }
        });
    }

    private void updateTotal() {
        int total = a * b; // This is where you apply your function
        etTotal.setText("" + total); // need to do that otherwise int will
        // be treated as res id.
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etTgl.setText(sdf.format(myCalendar.getTime()));
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
                            dataSet.addAll(Collections.singleton(custList.getData()[i].getCustomerName()));
                            bebas.clear();
                            bebas.addAll(dataSet);
                        }

                        // Creates the adapter and set it to the AutoCompleteTextView
                        ArrayAdapter adapter = new ArrayAdapter<String>(FormPiutangGalon.this, android.R.layout.simple_spinner_dropdown_item, bebas);
                        actPelanggan.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormPiutangGalon.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(FormPiutangGalon.this);
        requestQueue.add(stringRequest);
    }

    public void addPiutangGalon() {
        String url = getString(R.string.host_credit_gallon_add);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("date", etTgl.getText().toString() + " 00:00:00");
            jsonBody.put("customerId", Integer.parseInt(id_cust));
            jsonBody.put("debtCategory", 2);
            jsonBody.put("gallonAmount", Integer.parseInt(etJumlah.getText().toString()));
            jsonBody.put("gallonPrice", Integer.parseInt(etHarga.getText().toString()));
            jsonBody.put("amount", Integer.parseInt(etTotal.getText().toString()));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormPiutangGalon.this);
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(FormPiutangGalon.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Data has been Saved")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("Selesai", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), PiutangGalonActivity.class);
                                        startActivity(i);
                                        finishAffinity();
                                    }
                                })
                                .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), FormPiutangGalon.class);
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

                        MaterialDialog mDialog = new MaterialDialog.Builder(FormPiutangGalon.this)
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

    public void updatePiutangGalon() {
        String url = getString(R.string.host_credit_gallon_update);
        final String idCust;

        if (id_cust == null || id_cust.isEmpty()) {
            idCust = dataCust;
        } else {
            idCust = id_cust;
        }

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("debtId", Integer.parseInt(getIntent().getStringExtra("debtId")));
            jsonBody.put("date", etTgl.getText().toString() + " 00:00:00");
            jsonBody.put("customerId", Integer.parseInt(idCust));
            jsonBody.put("gallonAmount", Integer.parseInt(etJumlah.getText().toString()));
            jsonBody.put("amount", Integer.parseInt(etHarga.getText().toString()));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormPiutangGalon.this);
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(FormPiutangGalon.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Data has been Updated")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("Selesai", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), PiutangGalonActivity.class);
                                        startActivity(i);
                                        finishAffinity();
                                    }
                                })
                                .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        Intent i = new Intent(getApplicationContext(), FormPiutangGalon.class);
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

                        MaterialDialog mDialog = new MaterialDialog.Builder(FormPiutangGalon.this)
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

    private void getDetailPiutangGalon(String id) {
        String url = getString(R.string.host_credit_gallon_detail);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("creditId", Integer.parseInt(id));

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(FormPiutangGalon.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    ReceiptView message = gson.fromJson(response, ReceiptView.class);

                    etTgl.setText(message.getData()[0].getCreatedAtRaw().substring(0, 10));
                    actPelanggan.setText(message.getData()[0].getCustomerName());
                    etJumlah.setText(message.getData()[0].getAmount());
                    etHarga.setText(message.getData()[0].getPrice().replace(",", ""));
                    etTotal.setText(message.getData()[0].getTotal().replace(",", ""));

                    dataCust = message.getData()[0].getCustomerId();
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
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        super.onBackPressed();
        startActivity(new Intent(this, PiutangGalonActivity.class));
        finish();
    }

    private abstract class TextChangedListener implements TextWatcher {

        public abstract void numberEntered(int number);

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            try {
                int parsedInt = Integer.parseInt(text);
                numberEntered(parsedInt);
            } catch (NumberFormatException e) {
                Log.w(getPackageName(), "Could not parse '" + text + "' as a number", e);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}