package com.example.healthcareapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.R;
import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.model.Cart;
import com.example.healthcareapp.model.Product;
import com.example.healthcareapp.model.Users;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartOrderAdapter extends RecyclerView.Adapter<CartOrderAdapter.CartHolder> {
    List<Cart> listCart;
    Context context;
    Users u = DataLocalManager.getUsers();

    public CartOrderAdapter(List<Cart> listCart, Context context) {
        this.listCart = listCart;
        this.context = context;

    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemcart_card_confirm_order, parent, false);
        return new CartHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Cart cart = listCart.get(position);
        Product product = cart.getProduct();
        Glide.with(holder.itemView.getContext()).load(product.getpImage()).into(holder.picCart);
        holder.txtNameCart.setText(product.getpName().substring(0, 14));
        holder.txtnumOfQuan.setText(cart.getNumOfQuan() + "");
        holder.txtmoneyCart.setText(cart.getNumOfQuan() * product.getpPrice() + "");
    }

    @Override
    public int getItemCount() {
        if (listCart != null) {
            return listCart.size();
        }
        return 0;
    }


    public class CartHolder extends RecyclerView.ViewHolder {
        public ImageView picCart;
        public TextView txtNameCart, txtmoneyCart, txtnumOfQuan;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            picCart = itemView.findViewById(R.id.picCard1);
            txtNameCart = itemView.findViewById(R.id.txtNCart);
            txtmoneyCart = itemView.findViewById(R.id.txtMCart);
            txtnumOfQuan = itemView.findViewById(R.id.txtQCart);
        }

    }
}

