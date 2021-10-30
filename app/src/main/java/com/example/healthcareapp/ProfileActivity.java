package com.example.healthcareapp;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.data_local.MyShareReference;
import com.example.healthcareapp.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.Calendar;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class ProfileActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private static  String img = "";
    Button btn_edit, btn_out;
    EditText EditName, EditDOB, EditEmail, EditPhone, EditAddress, EditGender, EditBHYT;
    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    TextView textViewID;
    ImageView imageViewBack,editImageView,datePick;
    private static final int PICK_IMAGE = 1;
    DatePickerDialog.OnDateSetListener setListener;


    ActivityResultLauncher<Intent> mIntentActivityResultLauncher =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        Users users = DataLocalManager.getUsers();
                        users.setAvatar(String.valueOf(uri));
                        users = new Users(users.getId(),users.getEmail(),users.getAvatar());
                        DataLocalManager.setUser(users);
                        img = String.valueOf(uri);
                        Glide.with(ProfileActivity.this).load(users.getAvatar()).error(R.drawable.girl).into(editImageView);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            editImageView.setImageBitmap(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn_edit = findViewById(R.id.btn_edit);
      //  btn_out = findViewById(R.id.btn_out);
        EditName = findViewById(R.id.EditName);
        EditDOB = findViewById(R.id.EditDOB);
        EditEmail = findViewById(R.id.EditEmail);
        EditPhone = findViewById(R.id.EditPhone);
        EditAddress = findViewById(R.id.EditAddress);
        EditGender = findViewById(R.id.EditGender);
        EditBHYT = findViewById(R.id.EditBHYT);
        textViewID = findViewById(R.id.textViewID);
        imageViewBack=findViewById(R.id.imageViewBack);
        editImageView = findViewById(R.id.editImageView);
        datePick = findViewById(R.id.datePick);


        Users users = DataLocalManager.getUsers();
        String email=users.getEmail();
        String id = users.getId();
        showUserInformation(email);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ProfileActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,year,month,day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)
                );
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month +=1;
                String date= dayOfMonth +"-"+month+"-"+year;
                EditDOB.setText(date);
            }
        };

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPermission();
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            MyShareReference myShareReference = new MyShareReference(ProfileActivity.this);
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent1);
                finish();
            }
        });

//        btn_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialog();
//            }
//        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IdPro = textViewID.getText().toString().trim();
                String name = EditName.getText().toString().trim();
                String dob = EditDOB.getText().toString().trim();
                String EMAIL = EditEmail.getText().toString().trim();
                String ADDRESS = EditAddress.getText().toString().trim();
                String GENDER = EditGender.getText().toString().trim();
                String PHONE = EditPhone.getText().toString().trim();
                String BHYTCODE = EditBHYT.getText().toString().trim();


                fb.collection("User").whereEqualTo("Email",email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    String avatar1 =img;

                                    DocumentReference contact = fb.collection("User").document(id);
                                    contact.update("Avatar",avatar1);
                                    contact.update("Name", name);
                                    contact.update("DOB", dob);
                                    contact.update("Email", EMAIL);
                                    contact.update("Address", ADDRESS);
                                    contact.update("Phone", PHONE);
                                    contact.update("Gender", GENDER);
                                    contact.update("BHYTCode", BHYTCODE)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(ProfileActivity.this, "Updated Successfully",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.w(TAG, "Error updating profile", e);
                                        }
                                    });
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
    }

    private void onClickPermission() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallary();
            return;
        }else if((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            openGallary();
        }else{
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length  > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallary();
            }
        }

    }

    private void openGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mIntentActivityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));

    }

    public void openDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Đăng Xuất")
                .setMessage("Xác Nhận Đăng Xuất?")
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                    }
                })
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        DataLocalManager.getInstance().myShareReference.putStringValue("fieldName","");
                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).show();
    }
    private void showUserInformation(String email) {
        Users users = DataLocalManager.getUsers();
        String avatar = users.getAvatar();
        fb.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("Email").equals(email)) {
                                    Glide.with(ProfileActivity.this).load(avatar).error(R.drawable.girl).into(editImageView);
                                    textViewID.setText(document.getString("UserID"));
                                    EditName.setText(document.getString("Name"));
                                    EditDOB.setText(document.getString("DOB"));
                                    EditEmail.setText(document.getString("Email"));
                                    EditPhone.setText(document.getString("Phone"));
                                    EditAddress.setText(document.getString("Address"));
                                    EditGender.setText(document.getString("Gender"));
                                    EditBHYT.setText(document.getString("BHYTCode"));

                                }

                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Have Not Information! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}