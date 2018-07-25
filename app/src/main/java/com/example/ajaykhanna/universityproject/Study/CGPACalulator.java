package com.example.ajaykhanna.universityproject.Study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ajaykhanna.universityproject.R;

public class CGPACalulator extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpacalulator);

        mToolbar=findViewById(R.id.cgpaAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("CGPA Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
