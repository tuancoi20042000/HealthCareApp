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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    List<Cart> listCart;
    Context context;
    private ArrayList<Cart> listUpdated;
    private boolean updateStatus;
    FirebaseFirestore firestore;
    Users u = DataLocalManager.getUsers();
    CollectionReference reference;
    public CartAdapter(List<Cart> listCart, Context context) {
        this.listCart = listCart;
        this.context = context;
        listUpdated = (ArrayList<Cart>) listCart;
        updateStatus = false;
        firestore= FirebaseFirestore.getInstance();
        reference =firestore.collection("Cart");
    }

    public CartAdapter(Context context) {
        this.context = context;
    }

    public void setListCart(List<Cart> listCart) {
        this.listCart = listCart;
        this.listUpdated = (ArrayList<Cart>) listCart;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemcart_card, parent, false);
        return new CartHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Cart cart = listCart.get(position);
        Product product = cart.getProduct();
        Glide.with(holder.itemView.getContext()).load(product.getpImage()).into(holder.picCart);
        holder.txtNameCart.setText(product.getpName().substring(0,14));
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

    public ArrayList<Cart> getListUpdated() {
        if (listUpdated != null)
            return listUpdated;
        else
            return new ArrayList<Cart>();
    }

    public boolean getUpdateStatus() {
        return updateStatus;
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        public ImageView picCart, btDelCart;
        public TextView txtNameCart, txtmoneyCart, txtnumOfQuan;
        public CheckBox cbCart;
        public ImageView minusCart, plusCart;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            picCart = itemView.findViewById(R.id.picCard);
            txtNameCart = itemView.findViewById(R.id.txtNameCart);
            txtmoneyCart = itemView.findViewById(R.id.txtMoneyCart);
            txtnumOfQuan = itemView.findViewById(R.id.txtnumOfQuan);
            btDelCart = itemView.findViewById(R.id.btDelCart);
            minusCart = itemView.findViewById(R.id.minusCardBtn);
            plusCart = itemView.findViewById(R.id.plusCardBtn);

            minusCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int numofQuan = updateQuantity(getAdapterPosition(), false);
                    txtnumOfQuan.setText(numofQuan + "");
                    txtmoneyCart.setText("" + numofQuan * listCart.get(getAdapterPosition()).getProduct().getpPrice());
                    updateStatus = true;
                }
            });
            plusCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int numofQuan = updateQuantity(getAdapterPosition(), true);
                    txtnumOfQuan.setText(numofQuan + "");
                    txtmoneyCart.setText("" + numofQuan * listCart.get(getAdapterPosition()).getProduct().getpPrice());
                    updateStatus = true;
                }
            });
            btDelCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAt(getAdapterPosition());
                }
            });

        }
    }

    public void removeAt(int position) {
        if (position == 0 && listUpdated.size() == 0) {
            listCart = new ArrayList<>();
            notifyDataSetChanged();
        } else {
            DocumentReference doc = reference.document(u.getId() + listCart.get(position).getProduct().getpID());
            doc.delete();
            listCart.remove(position);
            notifyItemRangeRemoved(position, listCart.size());
            notifyItemRangeChanged(position, listCart.size());
            notifyDataSetChanged();
        }
    }

    public int updateQuantity(int postion, boolean status) {
        if (status == true) {
            int number = listCart.get(postion).getNumOfQuan() + 1;
            listUpdated.get(postion).setNumOfQuan(number);
            updateFireBase(postion,number);
            notifyItemChanged(postion);
            return number;
        } else {
            int number =listUpdated.get(postion).getNumOfQuan();
            if (number <= 1) {
                number = 1;
            }
            else{
                 number = listUpdated.get(postion).getNumOfQuan() - 1;
            }
            updateFireBase(postion,number);

            listUpdated.get(postion).setNumOfQuan(number);

            notifyItemChanged(postion);
            return number;
        }
    }
    public void updateFireBase(int postion, int number){
        Map<String, Object> item = new HashMap<>();
        item.put("userID", u.getId());
        item.put("nameP",  listCart.get(postion).getProduct().getpName());
        item.put("pID", listCart.get(postion).getProduct().getpID());
        item.put("priceP", listCart.get(postion).getProduct().getpPrice());
        item.put("quantityP", number);
        item.put("imageP", listCart.get(postion).getProduct().getpImage());
        item.put("cartID", u.getId() + listCart.get(postion).getProduct().getpID());
        reference.document(u.getId() + listCart.get(postion).getProduct().getpID()).set(item);
    }

}

