package com.buka.amanah.view.users.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buka.amanah.R;
import com.buka.amanah.model.user.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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

import de.hdodenhof.circleimageview.CircleImageView;

import okhttp3.OkHttpClient;

public class UserProfile extends AppCompatActivity {

    String[] listItems = {"Ubah Profil", "Ubah Kata Sandi", "Logout"};
    public static String token = "";
    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView txtNama_Lengkap, txtNoHp, txtEmail, txtAlamat;
    CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Profil");

        mRequestQueue = Volley.newRequestQueue(this);

        //        Check Login
        sharedPreferences = getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString("token", "");

        imageView = findViewById(R.id.profile_image);
//        txtNama = findViewById(R.id.tv_nama);
        txtNama_Lengkap = findViewById(R.id.tv_nama_lengkap);
//        txtKTP = findViewById(R.id.tv_noKTP);
        txtNoHp = findViewById(R.id.tv_nomor_telepon);
        txtEmail = findViewById(R.id.tv_email);
        txtAlamat = findViewById(R.id.tv_alamat);

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

                            if (!dataUser.getData().getName().isEmpty()) {
                                txtNama_Lengkap.setText(dataUser.getData().getName());
                            } else {
                                txtNama_Lengkap.setText("- Belum ada Nama -");
                            }

//                            if (!dataUser.getData().getName().isEmpty()) {
//                                txtKTP.setText(dataUser.getData().get());
//                            } else {
//                                txtKTP.setText("- Belum ada Nomor KTP -");
//                            }

                            if (!dataUser.getData().getPhone().isEmpty()) {
                                txtNoHp.setText(dataUser.getData().getPhone());
                            } else {
                                txtNoHp.setText("- Belum ada Nomor Telepon -");
                            }

                            if (!dataUser.getData().getEmail().isEmpty()) {
                                txtEmail.setText(dataUser.getData().getEmail());
                            } else {
                                txtEmail.setText("- Belum ada Email -");
                            }

                            if (!dataUser.getData().getAddress().isEmpty()) {
                                txtAlamat.setText(dataUser.getData().getAddress());
                            } else {
                                txtAlamat.setText("- Belum ada Alamat -");
                            }

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
            }
            else if (!response.isSuccessful()) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
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
            case R.id.nav_menu:
                MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(UserProfile.this, R.style.ProfileMenu);

                builder.setItems(listItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            startActivity(new Intent(getApplicationContext(), UpdateUserProfile.class));
                        } else if (i == 1) {
                            startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Position: " + i + " Value: " + listItems[i], Toast.LENGTH_LONG).show();
                        }
                    }
                }).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
}