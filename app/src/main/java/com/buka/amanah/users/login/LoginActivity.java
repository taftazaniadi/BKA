package com.buka.amanah.users.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.buka.amanah.R;
import com.buka.amanah.home.MainActivity;
import com.buka.amanah.users.register.RegisterActivity;
import com.buka.amanah.users.resetpass.ForgotPasswordActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        ActionBar actionbar = getSupportActionBar();
//        actionbar.setIcon(R.drawable.ic_jitu);
        actionbar.setTitle("Masuk");

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

    public void userRegistration(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }

    public void forgotPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
        finish();
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}