package com.buka.amanah.users.resetpass;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.buka.amanah.R;
import com.buka.amanah.users.login.LoginActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        /* Toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        ActionBar actionbar = getSupportActionBar();
//        actionbar.setIcon(R.drawable.ic_jitu);
        actionbar.setTitle("Lupa Kata Sandi");

        emailSend = findViewById(R.id.editTextEmailSend);

        // making notification bar transparent
        changeStatusBarColor();

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

    public void processOTP(View view) {
        Intent i = new Intent(this, ForgotPasswordInputOTP.class);
        i.putExtra("emailSend", emailSend.getText().toString());
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}