package com.example.healthcareapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapp.adapter.ProductAdapter;
import com.example.healthcareapp.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListItem_SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSearch;
    private ProductAdapter productAdapter;
    private ImageView btSortSearch;
    private ImageView btBackShop;
    private EditText editTextSearch;
    private TextView txtTextSearch;
    List<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item__search);
        editTextSearch = findViewById(R.id.editTextSearch);
        txtTextSearch = findViewById(R.id.txtTextSearch);
        btSortSearch = findViewById(R.id.btSortSearch);
        btBackShop = findViewById(R.id.btBackShop);
        // list product
        recyclerViewSearch = findViewById(R.id.listSearch);
        list = getListProduct();
        productAdapter = new ProductAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewSearch.setLayoutManager(gridLayoutManager);
        productAdapter.setmListProduct(list);
        recyclerViewSearch.setAdapter(productAdapter);
        btSortSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSort();
            }
        });
        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onFocusChangeText();
            }
        });

        btBackShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }

    public void onClickSort() {
        Collections.sort(list);
        productAdapter.setmListProduct(list);
        recyclerViewSearch.setAdapter(productAdapter);
        Toast.makeText(ListItem_SearchActivity.this, "Sort", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceAsColor")
    public void onFocusChangeText() {
        List<Product> listSearch = new ArrayList<>();
        if (editTextSearch.getText().toString().isEmpty()) {
            txtTextSearch.setText("");
            listSearch = getListProduct();
        } else {
            txtTextSearch.setText("'" + editTextSearch.getText() + "'");
            for (Product p : getListProduct()) {
                if (p.getpName().contains(editTextSearch.getText())) {
                    listSearch.add(p);
                }
            }
            if (listSearch.size() == 0) {
                txtTextSearch.setText("'Không có sản phẩm nào'");
                txtTextSearch.setTextColor(Color.RED);
            } else {
                txtTextSearch.setTextColor(Color.parseColor("#AEA7A7"));
            }
        }
        productAdapter.setmListProduct(listSearch);
        recyclerViewSearch.setAdapter(productAdapter);
    }

    public List<Product> getListProduct() {
        List<Product> lista = new ArrayList<>();
        lista = (List<Product>) getIntent().getSerializableExtra("list");
        return lista;
    }
}