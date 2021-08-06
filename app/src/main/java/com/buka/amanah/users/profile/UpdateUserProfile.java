package com.buka.amanah.users.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
//import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.buka.amanah.pojo.ResponseDefault;
import com.buka.amanah.pojo.login.BukaAuth;
import com.buka.amanah.pojo.user.User;
import com.buka.amanah.users.login.LoginActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import dev.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import dev.shreyaspatil.MaterialDialog.interfaces.OnShowListener;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class UpdateUserProfile extends AppCompatActivity {

    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText etNama_lengkap, etKTP, etNoHp, etEmail, etAlamat;
    CircleImageView imageView;

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            if (hostname.equalsIgnoreCase("bukukas-api.herokuapp.com")) {
                                return true;
                            } else
                                return true;
                        }
                    }).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Ubah Profil");

        sharedPreferences = getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mRequestQueue = Volley.newRequestQueue(this);

        imageView = findViewById(R.id.profile_image);
        etNama_lengkap = findViewById(R.id.editTextNamaLengkap);
//        etKTP = findViewById(R.id.editTextNoKTP);
        etNoHp = findViewById(R.id.editTextNomorTelepon);
        etEmail = findViewById(R.id.editTextEmail);
        etAlamat = findViewById(R.id.editTextAlamat);

        showUserProfile();
    }

    public void showUserProfile() {
        String destination_host = getString(R.string.host_profile);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, destination_host,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Gson gson = new Gson();
                            User dataUser = gson.fromJson(response, User.class);

                            if (dataUser.getData().getProfilePicture() != null && dataUser.getData().getProfilePicture() != "") {
                                final String imageUrl = dataUser.getData().getProfilePicture();
                                System.out.println("Data Photo : " + imageUrl);
                                new DownloadImageTask(imageView).execute(imageUrl);
                            } else {
                                System.out.println("Photo Profil Should be Default");
                            }

                            etNama_lengkap.setText(dataUser.getData().getName());
                            etNoHp.setText(dataUser.getData().getPhone());
                            etEmail.setText(dataUser.getData().getEmail());
                            etAlamat.setText(dataUser.getData().getAddress());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "error: " + error.toString(), Toast.LENGTH_SHORT).show();
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

        stringRequest.setTag("User Profile");
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                1,
                2
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void updateProfile(View view) {
        String url = getString(R.string.host_update_profile);

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
            jsonBody.put("name", etNama_lengkap.getText().toString());
            jsonBody.put("address", etAlamat.getText().toString());
            jsonBody.put("phone", etNoHp.getText().toString());
//            jsonBody.put("email", etEmail.getText().toString());
//                jsonBody.put("", etKTP.getText().toString());
            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            final ProgressDialog progressDialog = new ProgressDialog(UpdateUserProfile.this);
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(UpdateUserProfile.this)
                                .setTitle("Success", TextAlignment.CENTER)
                                .setMessage("Update Profile Success.")
                                .setAnimation("6717-loading-passed-state.json")
                                .setPositiveButton("DONE", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                                        finish();
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
                        MaterialDialog mDialog = new MaterialDialog.Builder(UpdateUserProfile.this)
                                .setTitle("Failed", TextAlignment.CENTER)
                                .setMessage("Update Profile Failed.")
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
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {

            final OkHttpClient client = getUnsafeOkHttpClient();
            client.connectTimeoutMillis();
            client.readTimeoutMillis();
            client.writeTimeoutMillis();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(urls[0])
                    .build();

            okhttp3.Response response = null;
            Bitmap mIcon11 = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.isSuccessful()) {
                try {
                    mIcon11 = BitmapFactory.decodeStream(response.body().byteStream());
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            } else if (!response.isSuccessful()) {
                Picasso.with(getParent()).load(response.toString())
                        .placeholder(R.drawable.default_picture)
                        .into(bmImage);
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}