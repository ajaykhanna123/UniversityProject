package com.example.ajaykhanna.universityproject.Notice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ajaykhanna.universityproject.R;

public class AddNoticeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Toolbar mToolbar;
    String[] edtCategory={"Btech(Cse)","Btech(Mec)","Btech(Ece)","Btech(Civil)"};
    private Spinner edtCategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        mToolbar=findViewById(R.id.addNoticeAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtCategorySpinner=findViewById(R.id.addCategory);



        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,edtCategory);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        edtCategorySpinner.setAdapter(aa);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
