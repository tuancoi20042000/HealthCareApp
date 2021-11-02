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

import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    public EditText editTextName, editTextPassword;
    private Button btn_login;
    private TextView viewText_Register;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewText_Register = findViewById(R.id.textView_Register);
        btn_login = findViewById(R.id.btn_login);
        editTextName = findViewById(R.id.editTextPassWord);
        editTextPassword = findViewById(R.id.editTextRePassword);

        dialog = new ProgressDialog(this);


        intitListener();


        Intent intent = new Intent(this, RegisterActivity.class);
        viewText_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        });

    }

    private void intitListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextName.getText().toString().trim().trim();
                String passWord = editTextPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    editTextName.setError("Vui lòng điền email");
                    return;
                } else if (TextUtils.isEmpty(passWord)) {
                    editTextPassword.setError("Vui lòng điền mật khẩu");
                    return;
                } else {
                    dialog.show();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    db.collection("User").whereEqualTo("Email", email)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    dialog.dismiss();
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (!(document.getString("PassWord").equals(passWord))) {
                                                Toast.makeText(LoginActivity.this, "Sai mật khẩu! ", Toast.LENGTH_SHORT).show();
                                                return;
                                            } else if (document.getString("Email").equals(email) && (document.getString("PassWord").equals(passWord))) {
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                                                Users users = new Users(document.getId(), document.getString("Email"), document.getString("Avatar"));
                                                DataLocalManager.setUser(users);

                                                startActivity(intent);
                                                finish();
                                                break;
                                            }
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Email or PassWord incorrect! ", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                }
            }
        });
    }
}