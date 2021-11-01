package com.example.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.healthcareapp.adapter.OrderListAdapter;
import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.model.Order;
import com.example.healthcareapp.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListOrderActivity extends AppCompatActivity {
    CollectionReference collectionReference;
    FirebaseFirestore firestore;
    ArrayList<Order> listOrder;
    Users user = DataLocalManager.getUsers();
    OrderListAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.listOrderView);

        listOrder = new ArrayList<>();
        getListOrder();
    }

    private void getListOrder() {
        firestore.collection("Order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            for (QueryDocumentSnapshot doc : snapshot) {
                                if (doc.get("orderCode").toString().contains(user.getId())) {
                                    listOrder.add(doc.toObject(Order.class));
                                }
                            }
                            adapter = new OrderListAdapter(listOrder);
                            recyclerView.setAdapter(adapter);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListOrderActivity.this, RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            Log.d("TAG", "onComplete: List size " + listOrder.size());
                        } else {
                            Log.d("TAG", "onComplete: TASK FALSE");
                        }
                    }
                });

    }
}