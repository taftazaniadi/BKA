package com.buka.amanah.users.resetpass;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.buka.amanah.R;

public class ForgotPasswordInputOTP extends AppCompatActivity {

    TextView emailSend;
    EditText et1,et2,et3,et4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_input_otp);

        /* Toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        ActionBar actionbar = getSupportActionBar();
//        actionbar.setIcon(R.drawable.ic_jitu);
        actionbar.setTitle("Lupa Kata Sandi");

        emailSend = findViewById(R.id.emailSend);

        emailSend.setText(/*Html.fromHtml("<b>" + */getIntent().getStringExtra("emailSend")/* + "</b>")*/);

        // making notification bar transparent
        changeStatusBarColor();

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);

        et1.requestFocus();

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et2.requestFocus();
                }
                else if(s.length()==0)
                {
                    et1.clearFocus();
                }
            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et3.requestFocus();
                }
                else if(s.length()==0)
                {
                    et1.requestFocus();
                }
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et4.requestFocus();
                }
                else if(s.length()==0)
                {
                    et2.requestFocus();
                }
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
//                    et5.requestFocus();
                }
                else if(s.length()==0)
                {
                    et3.requestFocus();
                }
            }
        });
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
        startActivity(new Intent(this, ForgotPasswordActivity.class));
        finish();
    }
}