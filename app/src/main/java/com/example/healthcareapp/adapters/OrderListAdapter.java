package com.example.healthcareapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapp.R;
import com.example.healthcareapp.model.Order;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Order> orderArrayList;

    public OrderListAdapter(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemorder_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order order = orderArrayList.get(position);
//        holder.
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
//        TextView price;
        TextView status;
        TextView totalBill;
        Button confirm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.picCard);
            name = itemView.findViewById(R.id.title2Txt);
            status = itemView.findViewById(R.id.textView3);
            totalBill = itemView.findViewById(R.id.totalEachItem);
            confirm = itemView.findViewById(R.id.button);
        }
    }

}
