package com.example.healthcareapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.ItemDetailActivity;
import com.example.healthcareapp.R;
import com.example.healthcareapp.model.Product;

import java.util.List;

import static com.example.healthcareapp.R.layout.product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    public Context mContext;
    public List<Product> mListProduct;

    public ProductAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmListProduct(List<Product> mListProduct) {
        this.mListProduct = mListProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product==null){
            return;
        }
        Glide.with(holder.itemView.getContext())
                .load(product.getpImage())
                .into(holder.pImage);
        if(product.getpName().length()>15) {
            holder.pName.setText(product.getpName().substring(0, 15));
        }
        else {
            holder.pName.setText(product.getpName());
        }
        holder.pPrice.setText("$"+product.getpPrice());
    }


    @Override
    public int getItemCount() {
        if (mListProduct!= null){
            return mListProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView pImage;
        private TextView pName;
        private TextView pPrice;
        private CardView productCardView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.pImage);
            pName = itemView.findViewById(R.id.pName);
            pPrice = itemView.findViewById(R.id.pPrice);
            productCardView = itemView.findViewById(R.id.product);
            productCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product p = mListProduct.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, ItemDetailActivity.class);
                    intent.putExtra("object", p);
                   mContext.startActivity(intent);
                }
            });
        }

    }
}




