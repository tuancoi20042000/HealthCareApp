package com.example.healthcareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.model.Product;

public class ItemDetailActivity extends AppCompatActivity {
    private ImageView pImage;
    private ImageView btAdd;
    private ImageView btAddtoCart;
    private ImageView btSub;
    private TextView pDes;
    private TextView pPrice;
    private TextView pName;
    private TextView pQuan;
    private TextView textViewNum;
    private TextView textView10;
    private int numberOrder = 1;
    private Product object;
    private ImageView btBack;
    public static int NUM_TO_TOP_ORDER = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initView();
        getBundle();
    }

    public void initView() {
        btAdd = findViewById(R.id.btAdd);
        btAddtoCart = findViewById(R.id.btAddtoCart);
        btSub = findViewById(R.id.btSubtract);
        pDes = findViewById(R.id.detailDes);
        pName = findViewById(R.id.detailName);
        pPrice = findViewById(R.id.detailPrice);
        pQuan = findViewById(R.id.detailQuan);
        pImage = findViewById(R.id.detailImage);
        textViewNum = findViewById(R.id.textViewNum);
        textView10 = findViewById(R.id.textView10);
        btBack = findViewById(R.id.imageView5);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getBundle() {
        object = (Product) getIntent().getSerializableExtra("object");
        pName.setText(object.getpName());
        pPrice.setText(object.getpPrice() + "$");
        pDes.setText(object.getpDescription());
        pQuan.setText(object.getpQuantity() + "");
        Glide.with(this)
                .load(object.getpImage())
                .into(pImage);
        // Check product is top order or not
        if (object.getpQuantity() > NUM_TO_TOP_ORDER) {
            textView10.setVisibility(View.VISIBLE);
        } else {
            textView10.setVisibility(View.INVISIBLE);
        }

        // Add to cart
        btAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Increase number to buy
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder++;
                textViewNum.setText(String.valueOf(numberOrder));
                pPrice.setText("$" + (object.getpPrice() * numberOrder));
            }
        });
        // Decrease number to buy
        btSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder--;
                    textViewNum.setText(String.valueOf(numberOrder));
                    pPrice.setText("$" + (object.getpPrice()) * numberOrder);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}