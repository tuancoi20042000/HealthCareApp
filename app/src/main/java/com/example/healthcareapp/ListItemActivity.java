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
import com.example.healthcareapp.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView2;
    private TopProductAdapter productAdapter2;
    BottomNavigationView bottomNavigationView;
    ImageView btSort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        initView();
        productAdapter.setmListProduct(getListProduct());
        recyclerView.setAdapter(productAdapter);
        //load top product
        productAdapter2.setmListProduct(getListProduct().subList(0,6));
        recyclerView2.setAdapter(productAdapter2);


    }
    public void initView(){
        btSort = findViewById(R.id.btSort);
        // list product
        recyclerView = findViewById(R.id.listProduct);
        productAdapter = new ProductAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        // list top product
        recyclerView2 = findViewById(R.id.listTopOrder);
        productAdapter2 = new TopProductAdapter(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
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
                                Toast.makeText(ListItemActivity.this,"Home",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_cart:
                                Toast.makeText(ListItemActivity.this,"Cart",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_search:
                                Intent intent = new Intent(ListItemActivity.this,ListItem_SearchActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
    }
    public   List<Product> getListProduct(){
        List<Product> list = new ArrayList<>();
        list.add(new Product("Băng gâu 3", 20,R.drawable.banggau+""));
        list.add(new Product("Bang gau 2", 21,R.drawable.mask5_large+""));
        list.add(new Product("Bang gau 3", 9,R.drawable.banggau+""));
        list.add(new Product("Băng gâu 3", 20,R.drawable.banggau+""));
        list.add(new Product("Bang gau 2", 21,R.drawable.mask5_large+""));
        list.add(new Product("Bang gau 3", 9,R.drawable.banggau+""));
        list.add(new Product("Băng gâu 3", 20,R.drawable.banggau+""));
        list.add(new Product("Bang gau 2", 21,R.drawable.mask5_large+""));
        list.add(new Product("Bang gau 3", 9,R.drawable.banggau+""));
        list.add(new Product("Băng gâu 3", 20,R.drawable.banggau+""));
        list.add(new Product("Bang gau 2", 21,R.drawable.mask5_large+""));
        list.add(new Product("Bang gau 3", 9,R.drawable.banggau+""));
        return list;
    }
    public void onClickSort(View view){
        ArrayList<Product> sortList = (ArrayList<Product>) getListProduct();
        Collections.sort(sortList);
        productAdapter.setmListProduct(sortList);
        recyclerView.setAdapter(productAdapter);
    }
}