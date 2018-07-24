package com.example.ajaykhanna.universityproject.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ajaykhanna.universityproject.R;

public class OtpActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mToolbar=findViewById(R.id.otp_App_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Otp");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
