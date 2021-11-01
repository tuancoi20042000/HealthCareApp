package com.example.healthcareapp.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.HomeActivity;
import com.example.healthcareapp.ListOrderActivity;
import com.example.healthcareapp.OrderDetailActivity;
import com.example.healthcareapp.R;
import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    ArrayList<Order> orderArrayList;

    public OrderListAdapter(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemorder_card, parent, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderArrayList.get(position);

        if (order == null) {
            return;
        }

        Glide.with(holder.img.getContext()).load(order.getpImage()).into(holder.img);
        if (order.getpName().length() > 15) {
            holder.name.setText(order.getpName().substring(0, 15));
        } else {
            holder.name.setText(order.getpName());
        }

        if (order.getStatus() == 0) {
            holder.status.setText("Đang giao");
            holder.orderAction.setText("Hủy đơn hàng");
        } else {
            holder.status.setText("Đã giao");
            holder.orderAction.setText("Đã nhận hàng");
            holder.orderAction.setEnabled(false);
        }
        holder.amount.setText("$" + order.getAmount());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView status;
        TextView amount;
        Button orderAction;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.picCard1);
            name = itemView.findViewById(R.id.txtNCart);
            status = itemView.findViewById(R.id.textView3);
            amount = itemView.findViewById(R.id.txtQCart);
            orderAction = itemView.findViewById(R.id.button);


            orderAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Bạn chắc chắn muốn xóa đơn hàng này?");
//                    builder.setView(R.layout.confirm_delete_order_layout);
                    builder.setPositiveButton("Xác Nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String orderCode = orderArrayList.get(getAdapterPosition()).getOrderCode();
                            CollectionReference reference = FirebaseFirestore.getInstance().collection("Order");

                            reference.get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                QuerySnapshot snapshot = task.getResult();
                                                for (QueryDocumentSnapshot doc : snapshot) {
                                                    if (doc.get("orderCode").equals(orderCode)) {
                                                        reference.document(doc.getId()).delete();
                                                        orderArrayList.remove(getAdapterPosition());
                                                        notifyDataSetChanged();
                                                        Toast.makeText(view.getContext(),"Xóa đơn hàng thành công!",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        }
                                    });
                        }
                    });
                    builder.setNegativeButton("Hủy Bỏ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(view.getContext(),"Đã hủy xóa đơn hàng!",Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }


    }

}
