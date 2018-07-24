package com.example.ajaykhanna.universityproject.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajaykhanna.universityproject.MainActivity.MainActivity;
import com.example.ajaykhanna.universityproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog mLoginProgress;
    private TextInputLayout edtLoginUserName;
    private TextInputLayout edtLoginPassword;
    private Button btnLogin;
    private TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //buttons
        edtLoginUserName=(TextInputLayout) findViewById(R.id.edtEmailId);
        edtLoginPassword=(TextInputLayout) findViewById(R.id.edtPassword);
        txtRegister=findViewById(R.id.txtRegister);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        btnLogin=(Button)findViewById(R.id.btnLogin);

        edtLoginPassword.getEditText().addTextChangedListener(mTextWatcher);
        edtLoginUserName.getEditText().addTextChangedListener(mTextWatcher);


        //run once to disable if empty
        checkFieldsForEmptyValues();
        mAuth=FirebaseAuth.getInstance();



        mLoginProgress=new ProgressDialog(LoginActivity.this);
        //when user log in
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=edtLoginUserName.getEditText().getText().toString().trim();
                String password=edtLoginPassword.getEditText().getText().toString();
                if(!TextUtils.isEmpty(userName) || !TextUtils.isEmpty(password))
                {
                    loginUser(userName,password);
                    mLoginProgress.setTitle("Logging In");
                    mLoginProgress.setMessage("Please wait while we check your credentials");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                }

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
    void loginUser(String uId,String pass) {
        mAuth.signInWithEmailAndPassword(uId, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mLoginProgress.dismiss();
                if (task.isSuccessful()) {
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                } else {
                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @SuppressLint("ResourceAsColor")
    void checkFieldsForEmptyValues(){

        String email = edtLoginPassword.getEditText().getText().toString();
        String password = edtLoginUserName.getEditText().getText().toString();

        if(email.equals("")|| password.equals("")){
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.rounded_btn1);
            int c=getResources().getColor(R.color.buttonDisabledColor);
            btnLogin.setTextColor(c);
        } else {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.rounded_btn);
            btnLogin.setTextColor(Color.WHITE);
        }
    }


}
