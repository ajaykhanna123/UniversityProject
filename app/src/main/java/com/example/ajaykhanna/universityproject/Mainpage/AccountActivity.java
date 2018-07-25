package com.example.ajaykhanna.universityproject.Mainpage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ajaykhanna.universityproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class AccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private Button btnNext;
    private EditText edtName;

    private EditText edtEmail;
    private EditText edtContact;
    private EditText edtDOB;
    private Spinner edtDept;
    String[] deptNames={"Btech(Cse)","Btech(Mec)","Btech(Ece)","Btech(Civil)"};
    private FirebaseDatabase database;
    private DatabaseReference myRefUsrId;
    private ImageView datePickerButton;
    private ProgressDialog progressDialog;
    int day;
    int month;
    int year;
    private FloatingActionButton floatAddPhoto;
    private Bitmap bitmap_thumb;
    private StorageReference storageReference;
    private StorageReference thumbImageRef;
    private int gallery_pick=001;
    private ImageView imgProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        mToolbar=findViewById(R.id.account_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Your Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        //firebase working

        thumbImageRef=FirebaseStorage.getInstance().getReference().child("thumb_profile_images");



        edtContact=findViewById(R.id.edtContactAccount);
        edtDept=findViewById(R.id.multiAutoCompleteTextViewAccount);
        edtDOB=findViewById(R.id.edtDOBAccount);
        edtName=findViewById(R.id.edtNameAccount);
        edtEmail=findViewById(R.id.edtEmailAccount);
        progressDialog=new ProgressDialog(this);
        floatAddPhoto=findViewById(R.id.floatAddPhoto);
        imgProfile=findViewById(R.id.imgProfile);


        btnNext=findViewById(R.id.btnSave);
        edtDept.setOnItemSelectedListener(this);
        database = FirebaseDatabase.getInstance();
        myRefUsrId=database.getReference().child("Users");
        datePickerButton=findViewById(R.id.datePickImgBtnAccount);

        // run once to disable if empty
        checkFieldsForEmptyValues();

        edtName.addTextChangedListener(mTextWatcher);
        edtDOB.addTextChangedListener(mTextWatcher);
        edtEmail.addTextChangedListener(mTextWatcher);
        edtContact.addTextChangedListener(mTextWatcher);
        //dept --donot know
        myRefUsrId.keepSynced(true);

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


        retrive();

        floatAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,gallery_pick);
            }
        });
        //storage
        storageReference= FirebaseStorage.getInstance().getReference().child("user_profile_images");






    }

    public void retrive()
    {
        progressDialog.setTitle("Account Data");
        progressDialog.setMessage("Please Wait while we are loading data for you");
        progressDialog.show();
        String currentUser=mAuth.getCurrentUser().getUid();
        myRefUsrId=FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
        myRefUsrId.keepSynced(true);


        myRefUsrId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
               String email=dataSnapshot.child("email").getValue().toString();
               String contact=dataSnapshot.child("contact no").getValue().toString();
               String dob=dataSnapshot.child("DOB").getValue().toString();
               String dept=dataSnapshot.child("department").getValue().toString();
                final String image=dataSnapshot.child("image").getValue().toString();
                final String thumb_image=dataSnapshot.child("image_thumb").getValue().toString();

               edtName.setText(name);
               edtContact.setText(contact);
               edtEmail.setText(email);
               edtDOB.setText(dob);

                if(!(image.equals("default_profile")))
                {
                    Picasso.get()
                            .load(image)
                            .placeholder(R.mipmap.default_image)
                            .resize(100, 100)
                            .centerCrop()
                            .into(imgProfile, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get()
                                            .load(image)
                                            .placeholder(R.mipmap.default_image)
                                            .resize(100, 100)
                                            .centerCrop()
                                            .networkPolicy(NetworkPolicy.OFFLINE)
                                            .into(imgProfile);
                                }
                            });



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message=databaseError.getMessage().toString();
                Toast.makeText(AccountActivity.this,"Data not retrived:"+message,Toast.LENGTH_LONG).show();

            }
        });
        progressDialog.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

        String name=edtName.getText().toString();
        String contactNo=edtContact.getText().toString();
        String dob=edtDOB.getText().toString();
        String dept=edtDept.toString();

        if(email.equals("") || name.equals("") || dept.equals("")
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(gallery_pick == requestCode && resultCode==RESULT_OK && data!=null)
        {
            Uri imageUri=data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                progressDialog.setTitle("updating for data");
                progressDialog.setMessage("Please wait while we update your profile credentials");
                progressDialog.show();
                Uri resultUri = result.getUri();

                File thumb_filePathUri=new File(resultUri.getPath());
                String current_user=mAuth.getCurrentUser().getUid();

                try
                {
                    bitmap_thumb=new Compressor(this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(50)
                            .compressToBitmap(thumb_filePathUri);
                }
                catch (IOException e)
                {
                    Toast.makeText(AccountActivity.this,"Erroe occured: "+e.getMessage().toString()
                            ,Toast.LENGTH_LONG).show();

                }
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap_thumb.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                final byte[] thumb_byte=byteArrayOutputStream.toByteArray();

                final StorageReference thumb_file_path=thumbImageRef.child(current_user+".jpg");



                final StorageReference filepath=storageReference.child(current_user+".jpg");
                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri downloadUri)
                            {

                                UploadTask uploadTask=thumb_file_path.putBytes(thumb_byte);

                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        thumb_file_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri thumburi) {
                                                Map update_user_data = new HashMap();
                                                update_user_data.put("image", downloadUri.toString());
                                                update_user_data.put("image_thumb",thumburi.toString());


                                                myRefUsrId.updateChildren(update_user_data)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful())
                                                                {
                                                                    Toast.makeText(AccountActivity.this,
                                                                            "image updated to dataabse",Toast.LENGTH_LONG)
                                                                            .show();

                                                                }
                                                                else
                                                                {
                                                                    Toast.makeText(AccountActivity.this
                                                                            ,"error occured: "+task.getException().getMessage(),
                                                                            Toast.LENGTH_LONG).show();
                                                                }
                                                                progressDialog.dismiss();
                                                            }

                                                        });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AccountActivity.this,
                                                        "Error Ocuured at thumb image: "
                                                                +e.getMessage().toString(),Toast.LENGTH_LONG).show();

                                                progressDialog.dismiss();
                                            }
                                        });


                                    }
                                });



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AccountActivity.this,"Error occured:"+e.getMessage()
                                        .toString(),Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                });




            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


}
