package com.example.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapp.adapter.CartOrderAdapter;
import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.model.Cart;
import com.example.healthcareapp.model.Product;
import com.example.healthcareapp.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity {
    private CollectionReference reference;
    private FirebaseFirestore firestore;
    private List<Cart> AlllistCart = new ArrayList<>();
    private List<Cart> listCart = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView btConfirm, orderCodeView, txtTime, txtUserName, txtUserPhone, txtUserAddress, txtSumMoney;
    private String shipTime, shipAddress, orderCode, userPhoneNumber, userName;
    private Double orderAmount;
    String newOrderAmount;
    Users user = DataLocalManager.getUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        shipAddress = getIntent().getStringExtra("shipAddress");
       // orderAmount = Double.parseDouble(getIntent().getStringExtra("sumMoney"));
        newOrderAmount = getIntent().getStringExtra("sumMoney");
        orderCode = user.getId() + String.valueOf(System.currentTimeMillis());

        firestore = FirebaseFirestore.getInstance();
        innitView();
        getUserData();
        setTransportInfo();
        getCartData();

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionReference collectionOrder = firestore.collection("Order");
                CollectionReference collectionCart = firestore.collection("Cart");
                for (Cart c : listCart) {
                    Map<String, Object> order = new HashMap<>();
                    order.put("pID", c.getProduct().getpID());
                    order.put("amount", String.format("%.2f", c.getProduct().getpPrice() * c.getNumOfQuan()));
                    order.put("pImage", c.getProduct().getpImage());
                    order.put("pName", c.getProduct().getpName());
                    order.put("status", 0);// 0 - not yet, 1 - done, 2 - cancel
                    order.put("shipDate", shipTime);
                    order.put("shipAddress", shipAddress);
                    order.put("orderCode", c.getProduct().getpID()+ orderCode);
                    order.put("quantity",c.getNumOfQuan());
                    order.put("phone",userPhoneNumber);
                    order.put("userName",userName);
                    collectionOrder.add(order);
                    collectionCart.document(c.getCartID()).delete();
                }
                Toast.makeText(OrderDetailActivity.this,"Bạn đã đặt hàng thành công",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(OrderDetailActivity.this,ListItemActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getCartData() {
        reference = firestore.collection("Cart");
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
                        pr.setpPrice(Float.parseFloat(doc.get("priceP").toString()));
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
                        if (crt.getUser().getId().equals(user.getId())) {
                            listCart.add(crt);
                        }
                    }

                    CartOrderAdapter cartOrderAdapter = new CartOrderAdapter(listCart, OrderDetailActivity.this);
                    recyclerView.setAdapter(cartOrderAdapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OrderDetailActivity.this, "fail load cart", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetailActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getUserData() {
        firestore.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("Email").equals(user.getEmail())) {
                                    userName = document.getString("Name");
                                    txtUserName.setText(userName);
                                    userPhoneNumber = document.getString("Phone");
                                    txtUserPhone.setText(userPhoneNumber);
                                }
                            }
                        } else {
                            Toast.makeText(OrderDetailActivity.this, "Have Not Information! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void innitView() {
        recyclerView = findViewById(R.id.cartDetailRecycle);
        btConfirm = findViewById(R.id.btConfirmOrder);
        orderCodeView = findViewById(R.id.txtMaDonHang);
        txtTime = findViewById(R.id.txtThoiGianGiaoHang);
        txtUserName = findViewById(R.id.txtUserName);
        txtUserPhone = findViewById(R.id.txtUserPhone);
        txtUserAddress = findViewById(R.id.txtUserAddress);
        txtSumMoney = findViewById(R.id.txtTongSoTien);

        txtUserAddress.setText(shipAddress);
        //txtSumMoney.setText(orderAmount.toString());
        txtSumMoney.setText(newOrderAmount);
        orderCodeView.setText(orderCode);
    }

    private void setTransportInfo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 4);
        Date shipDate = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        shipTime = formatter.format(shipDate);
        txtTime.setText("Dự kiến nhận hàng vào " + shipTime);

    }
}