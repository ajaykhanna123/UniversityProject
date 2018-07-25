package com.example.ajaykhanna.universityproject.Study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ajaykhanna.universityproject.R;

public class IstYearStudy extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ist_year_study);

        mToolbar=findViewById(R.id.istYearAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Ist year");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
