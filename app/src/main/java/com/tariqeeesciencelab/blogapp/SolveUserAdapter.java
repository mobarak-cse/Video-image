package com.tariqeeesciencelab.blogapp;





import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class SolveUserAdapter extends RecyclerView.Adapter<SolveUserAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<SolveDatabase> productList;

    public SolveUserAdapter(Context mCtx, List<SolveDatabase> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_post, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        SolveDatabase product = productList.get(position);

        holder.textViewTitle.setText(product.getTitle());
        holder.textViewDate.setText("Last Update : " + product.getDate());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle, textViewDate;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewDate = itemView.findViewById(R.id.textview_date);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            SolveDatabase product = productList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, SolveUserView.class);
            intent.putExtra("Question_Video_Solve", product);
            mCtx.startActivity(intent);
        }
    }
}


