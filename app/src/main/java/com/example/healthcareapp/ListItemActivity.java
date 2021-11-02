package com.example.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapp.adapter.ProductAdapter;
import com.example.healthcareapp.adapter.TopProductAdapter;
import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.model.Product;
import com.example.healthcareapp.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.healthcareapp.ItemDetailActivity.NUM_TO_TOP_ORDER;

public class ListItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView2;
    private TopProductAdapter productAdapter2;
    private BottomNavigationView bottomNavigationView;
    private ImageView btSort;
    private ImageView btCart;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private List<Product> list = new ArrayList<>();
    private List<Product> listTopOrder = new ArrayList<>();
    Users user = DataLocalManager.getUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Product");
        initView();

        findViewById(R.id.action_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListItemActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        Product p = new Product();
                        p.setpID(doc.get("pID").toString());
                        p.setpName(doc.get("name").toString());
                        p.setpImage(doc.get("image").toString());
                        p.setpDescription(doc.get("descrip").toString());
                        p.setpPrice(Float.parseFloat(doc.get("price").toString()));
                        p.setpQuantity(Integer.parseInt(doc.get("quantity").toString()));
                        list.add(p);
                        if (p.getpQuantity() > NUM_TO_TOP_ORDER) {
                            listTopOrder.add(p);
                        }
                    }
                    productAdapter.setmListProduct(list);
                    recyclerView.setAdapter(productAdapter);
                    productAdapter2.setmListProduct(listTopOrder);
                    recyclerView2.setAdapter(productAdapter2);
                }
            }
        });


    }

    public void initView() {
        btSort = findViewById(R.id.btSort);
        btCart = findViewById(R.id.imageViewbtCart);
        // list product
        recyclerView = findViewById(R.id.listProduct);
        productAdapter = new ProductAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        // list top product
        recyclerView2 = findViewById(R.id.listTopOrder);
        productAdapter2 = new TopProductAdapter(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(linearLayout);
        // Bottom navigation
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                Toast.makeText(ListItemActivity.this, "Home", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_cart:
                                if (user != null) {
                                    Intent intent1 = new Intent(ListItemActivity.this, ListOrderActivity.class);
                                    startActivity(intent1);
                                } else {
                                    Toast.makeText(ListItemActivity.this, "Bạn phải đăng nhập để xem giỏ hàng :)", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.action_search:
                                Intent intent = new Intent(ListItemActivity.this, ListItem_SearchActivity.class);
                                intent.putExtra("list", (Serializable) list);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
        btCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListItemActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }


    public void onClickSort(View view) {
        Collections.sort(list);
        productAdapter.setmListProduct(list);
        recyclerView.setAdapter(productAdapter);
    }
}