package com.example.healthcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_Register;
    private EditText editTextEmail , editTextPassWord , editTextRePassword , editTextPhone;
    private ProgressDialog dialog ;
    private TextView editText_Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_Register =  findViewById(R.id.btn_Register);
        editTextEmail= findViewById(R.id.editTextEmail);
        editTextPassWord= findViewById(R.id.editTextPassWord);
        editTextRePassword=findViewById(R.id.editTextRePassword);
        editTextPhone=findViewById(R.id.editTextPhone);
        editText_Login=findViewById(R.id.textView_Login);

        dialog = new ProgressDialog(this);

        editText_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String passWord = editTextPassWord.getText().toString().trim();
                String rePassWord = editTextRePassword.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                if (TextUtils.isEmpty(email) || !(email.matches("^[a-z0-9]+@[a-z]{3,5}.[a-z]{2,3}$"))) {
                    editTextEmail.setError("Email is Required!");
                    return;
                } else if (TextUtils.isEmpty(passWord)) {
                    editTextPassWord.setError("PassWord is Required!");
                    return;
                } else if(!passWord.equals(rePassWord)){
                    editTextRePassword.setError("Not match PassWord!");
                }else if(phone.length() < 10){
                    editTextPhone.setError("Phone must 10 digits!");
                }else{
                    dialog.show();
                    FirebaseFirestore fb = FirebaseFirestore.getInstance();
                    Map<String, Object> user = new HashMap<>();
                    Random random =new Random();
                    int number =random.nextInt()+1;
                    String ID="HE"+number;
                    user.put("UserID",ID);
                    user.put("Email", email);
                    user.put("PassWord", passWord);
                    user.put("Phone", phone);
                    user.put("Avatar","");

                    fb.collection("User")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    dialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Register Successful! ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Register Unsuccessful! ", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}