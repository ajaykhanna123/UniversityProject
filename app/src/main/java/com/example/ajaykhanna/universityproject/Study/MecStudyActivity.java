package com.example.ajaykhanna.universityproject.Study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ajaykhanna.universityproject.R;

public class MecStudyActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mec_study);

        mToolbar=findViewById(R.id.mecAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Mechanical");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
