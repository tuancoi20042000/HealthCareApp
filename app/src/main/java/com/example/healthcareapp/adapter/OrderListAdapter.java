package com.example.healthcareapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapp.R;
import com.example.healthcareapp.model.Order;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    ArrayList<Order> orderArrayList;

    public OrderListAdapter(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemorder_card,parent,false);

        return new OrderViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderArrayList.get(position);

        if(order == null){
            return;
        }

//        holder.img.setImageResource(order.getProduct().getpImage());
        holder.name.setText(order.getProduct().getpName());
        holder.status.setText(order.getStatus());
//        holder.totalBill.setText(order.getTotalBill());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        //        TextView price;
        TextView status;
        TextView totalBill;
//        Button confirm;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.picCard);
            name = itemView.findViewById(R.id.txtNameCart);
            status = itemView.findViewById(R.id.textView3);
            totalBill = itemView.findViewById(R.id.txtMoneyCart);
//            confirm = itemView.findViewById(R.id.button);
        }
    }

}
