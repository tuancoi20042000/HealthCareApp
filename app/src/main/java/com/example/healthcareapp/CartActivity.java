package com.example.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapp.adapter.CartAdapter;
import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.model.Cart;
import com.example.healthcareapp.model.Product;
import com.example.healthcareapp.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerViewListCart;
    private CartAdapter cartAdapter;
    private TextView btMuahang;
    public TextView sumMoney;
    private CheckBox chonAll;
    private EditText txtDiaChi;
    private ImageView btBackCart;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private List<Cart> AlllistCart = new ArrayList<>();
    private List<Cart> listCart = new ArrayList<>();
    Users u = DataLocalManager.getUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        getBundle();

    }

    public void initView() {
        recyclerViewListCart = findViewById(R.id.listCart);
        sumMoney = findViewById(R.id.sumMoney);
        btMuahang = findViewById(R.id.btMuahang);
        chonAll = findViewById(R.id.checkBox);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        btBackCart = findViewById(R.id.btBackCart);
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Cart");
    }

    public void getBundle() {
        btMuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "size: " + listCart.size(), Toast.LENGTH_SHORT).show();
            }
        });
        btBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        chonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chonAll.isChecked()) {
                    getSumMon();

                } else {
                    Toast.makeText(CartActivity.this,"Bạn chưa chọn sản phẩm nào.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btMuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chonAll.isChecked() && listCart.size()>0) {
                    getSumMon();
                    Intent intent = new Intent(CartActivity.this, OrderDetailActivity.class);
                    intent.putExtra("ShipAddress", txtDiaChi.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(CartActivity.this,"Bạn chưa chọn sản phẩm nào.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    AlllistCart = new ArrayList<>();
                    listCart = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        Cart c = new Cart();
                        Users us = new Users();
                        Product pr = new Product();
                        us.setId(doc.get("userID").toString());
                        pr.setpPrice(Float.parseFloat(doc.get("quantityP").toString()));
                        pr.setpImage(doc.get("imageP").toString());
                        pr.setpName(doc.get("nameP").toString());
                        pr.setpID(doc.get("pID").toString());
                        c.setUser(us);
                        c.setProduct(pr);
                        c.setCartID(doc.get("cartID").toString());
                        c.setNumOfQuan(Integer.parseInt(doc.get("quantityP").toString()));
                        AlllistCart.add(c);
                    }
                    for (Cart crt : AlllistCart) {
                        if (crt.getUser().getId().equals(u.getId())) {
                            listCart.add(crt);
                        }
                    }
                    for (int i = 0; i < listCart.size(); i++) {
                        for (int j = i + 1; j < listCart.size(); j++) {
                            if (listCart.get(i).getProduct().getpName().equalsIgnoreCase(listCart.get(j).getProduct().getpName())) {
                                Cart ca = listCart.get(i);
                                ca.setNumOfQuan(ca.getNumOfQuan() + listCart.get(j).getNumOfQuan());
                                listCart.remove(i);
                                listCart.add(i, ca);
                                listCart.remove(j);
                            }
                        }
                    }
                    CartAdapter cartAdapter = new CartAdapter(listCart, CartActivity.this);
                    recyclerViewListCart.setAdapter(cartAdapter);
                    DocumentReference dc = reference.document("0f412JoQaERcK9afPRrj");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CartActivity.this, "fail load cart", Toast.LENGTH_SHORT).show();
            }
        });
        //getCart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false);
        recyclerViewListCart.setLayoutManager(linearLayoutManager);


    }

    public void getSumMon() {
        float sumMon = 0;
        for (Cart ca : listCart) {
            sumMon += ca.getNumOfQuan() * ca.getProduct().getpPrice();
        }
        sumMoney.setText(sumMon + "");
    }


}
