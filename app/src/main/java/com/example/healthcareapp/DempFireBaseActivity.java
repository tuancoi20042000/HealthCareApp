package com.example.healthcareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcareapp.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DempFireBaseActivity extends AppCompatActivity {
    EditText editName, editQuan;
    Button btuAdd, btuList;
    ListView listView;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demp_fire_base);
        editName = findViewById(R.id.editName);
        editQuan = findViewById(R.id.editQuan);
        btuAdd = findViewById(R.id.buttonAdd);
        btuList = findViewById(R.id.buttonList);
        listView = findViewById(R.id.listView);
        firestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firestore.collection("items");
        btuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> item = new HashMap<>();
                item.put("name", editName.getText().toString());
                item.put("quantity",editQuan.getText().toString());
                reference.add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(DempFireBaseActivity.this, "Add Succcessfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DempFireBaseActivity.this, "Add Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot snapshot= task.getResult();
                            List<Item> list = new ArrayList<>();
                            for(QueryDocumentSnapshot doc: snapshot){
                                Item item = new Item();
                                item.setName(doc.get("name").toString());
                                item.setQuantity(Integer.parseInt(doc.get("quantity").toString()));
                                list.add(item);
                            }
                            ArrayAdapter<Item> adapter = new ArrayAdapter<>(DempFireBaseActivity.this, android.R.layout.simple_list_item_1,list);
                            listView.setAdapter(adapter);
                        }
                    }
                });
            }
        });
    }
}