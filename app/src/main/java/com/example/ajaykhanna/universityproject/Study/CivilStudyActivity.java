package com.example.ajaykhanna.universityproject.Study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.example.ajaykhanna.universityproject.R;

public class CivilStudyActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_civil_study);

        mToolbar=findViewById(R.id.civilAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Civil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
