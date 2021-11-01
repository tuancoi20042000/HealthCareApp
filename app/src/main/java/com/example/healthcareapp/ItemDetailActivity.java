package com.example.healthcareapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.model.Product;
import com.example.healthcareapp.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

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
    public boolean isExist = false;
    public int oldQuan;
    public static int NUM_TO_TOP_ORDER = 120;
    String docID;
    Users u = DataLocalManager.getUsers();
    FirebaseFirestore firestore;
    CollectionReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initView();
        getBundle();
        Users users = DataLocalManager.getUsers();
        Product p = (Product) getIntent().getSerializableExtra("object");
        //

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
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Cart");
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
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Product p = (Product) getIntent().getSerializableExtra("object");
                reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            for (QueryDocumentSnapshot doc : snapshot) {
                                oldQuan = 0;
                                Log.d("TAG", "onTuan: "+doc.getId());
                                Log.d("TAG", "onTuan2: "+u.getId()+p.getpID());
                                String id = doc.getId().trim();
                                String sId = u.getId().trim()+p.getpID().trim();
                                Log.d("TAG", "onTuan3:"+id.length());
                                Log.d("TAG", "onTuan4: "+sId.length());
                                boolean a = (id.equals(sId));
                                Log.d("TAG", "onTuan5: "+a);
                                if(id.equals(sId)){
                                    oldQuan = Integer.parseInt(doc.get("quantityP").toString());
                                    Log.d("TAG", "onTuan8: "+Integer.parseInt(doc.get("quantityP").toString()));


                                    break;
                                }
                            }
                            int quantity = numberOrder + oldQuan;
                            Map<String, Object> item = new HashMap<>();
                            item.put("userID", u.getId());
                            item.put("pID", p.getpID());
                            item.put("nameP", p.getpName());
                            item.put("priceP", p.getpPrice());
                            item.put("quantityP", quantity);
                            item.put("imageP", p.getpImage());
                            item.put("cartID", u.getId() + p.getpID());
                            reference.document(u.getId() + p.getpID()).set(item);
                            Toast.makeText(ItemDetailActivity.this, "Thêm vào giỏ hàng thành công ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ItemDetailActivity.this, "fail load cart", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onTuanFail: ");
                    }
                });
//                Intent intent = new Intent(ItemDetailActivity.this, ListItemActivity.class);
//                startActivity(intent);
            }
        });
    }

}
