package com.example.ajaykhanna.universityproject.Login;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ajaykhanna.universityproject.MainActivity.MainActivity;
import com.example.ajaykhanna.universityproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private Button btnNext;
    private EditText edtName;
    private EditText edtpassword;
    private EditText edtConfirmPass;
    private EditText edtEmail;
    private EditText edtContact;
    private EditText edtDOB;
    private Spinner edtDept;
    private ProgressBar progressBar;
    String[] deptNames={"Btech(Cse)","Btech(Mec)","Btech(Ece)","Btech(Civil)"};
    FirebaseDatabase database;
    DatabaseReference myRefUsrId;
    ImageView datePickerButton;
    int day;
    int month;
    int year;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar=findViewById(R.id.register_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        edtConfirmPass=findViewById(R.id.edtConfirmPass);
        edtContact=findViewById(R.id.edtContact);
        edtDept=findViewById(R.id.multiAutoCompleteTextView);
        edtDOB=findViewById(R.id.edtDOB);
        edtName=findViewById(R.id.edtName);
        edtEmail=findViewById(R.id.edtEmail);
        edtpassword=findViewById(R.id.edtPassword);
        progressBar=findViewById(R.id.progressBar);
        btnNext=findViewById(R.id.btnNext);
        edtDept.setOnItemSelectedListener(this);
        database = FirebaseDatabase.getInstance();
        myRefUsrId=database.getReference().child("Users");
        datePickerButton=findViewById(R.id.datePickImgBtn);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,deptNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        edtDept.setAdapter(aa);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });





        // run once to disable if empty
        checkFieldsForEmptyValues();

        edtpassword.addTextChangedListener(mTextWatcher);
        edtName.addTextChangedListener(mTextWatcher);
        edtDOB.addTextChangedListener(mTextWatcher);
        edtEmail.addTextChangedListener(mTextWatcher);
        edtContact.addTextChangedListener(mTextWatcher);
        edtConfirmPass.addTextChangedListener(mTextWatcher);
        //dept --donot know

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtpassword.getText().toString();
                String confirmPassword=edtConfirmPass.getText().toString();
                if(confirmPassword.equals(password))
                {
                    logIn(email,password);
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"confirm password and password field are not the same"
                            ,Toast.LENGTH_LONG);
                }

            }
        });



    }
    public void logIn(String email,String password)
    {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String email = edtEmail.getText().toString();
                            String password = edtpassword.getText().toString();
                            String name=edtName.getText().toString();

                            String contactNo=edtContact.getText().toString();
                            String dob=edtDOB.getText().toString();
                            String dept=edtDept.getSelectedItem().toString();
                            String currentUser=mAuth.getCurrentUser().getUid().toString();
                            DatabaseReference currentUserDb=myRefUsrId.child(currentUser);
                            currentUserDb.child("name").setValue(name);
                            currentUserDb.child("email").setValue(email);
                            currentUserDb.child("password").setValue(password);
                            currentUserDb.child("contact no").setValue(contactNo);
                            currentUserDb.child("DOB").setValue(dob);
                            currentUserDb.child("department").setValue(dept);
                            currentUserDb.child("image").setValue("default_profile");
                            currentUserDb.child("image_thumb").setValue("default_image_thumb");

                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMain();
                            progressBar.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.GONE);

                        } else {
                            // If sign in fails, display a message to the user.
                            String exception=task.getException().toString();
                            Toast.makeText(RegisterActivity.this,exception,Toast.LENGTH_LONG).show();

                        }

                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    @SuppressLint("ResourceAsColor")
    void checkFieldsForEmptyValues(){

        String email = edtEmail.getText().toString();
        String password = edtpassword.getText().toString();
        String name=edtName.getText().toString();
        String confirmPassword=edtConfirmPass.getText().toString();
        String contactNo=edtContact.getText().toString();
        String dob=edtDOB.getText().toString();
        String dept=edtDept.toString();

        if(email.equals("")|| password.equals("") || confirmPassword.equals("") || name.equals("") || dept.equals("")
                || contactNo.equals("")|| dob.equals("") ){

            btnNext.setEnabled(false);
            btnNext.setBackgroundResource(R.drawable.rounded_btn1);
            int c=getResources().getColor(R.color.buttonDisabledColor);
            btnNext.setTextColor(c);
        } else {
            btnNext.setEnabled(true);
            btnNext.setBackgroundResource(R.drawable.rounded_btn);
            btnNext.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void goToMain()
    {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            edtDOB.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                    + selectedYear);
        }
    };
}
