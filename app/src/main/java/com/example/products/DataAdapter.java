package com.example.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Produkt> produkts;

    DataAdapter(Context context, List<Produkt> produkts){
        this.produkts = produkts;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        final Produkt produkt = produkts.get(position);
        holder.productView.setText(produkt.getProductName());
        holder.countView.setText(produkt.getCount());
        holder.typeView.setText(produkt.getProductType());
    }

    @Override
    public int getItemCount() {
        return produkts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView productView;
        final TextView countView;
        final TextView typeView;
        ViewHolder(View view) {
            super(view);
            productView = view.findViewById(R.id.tvProductName);
            countView = view.findViewById(R.id.tvCount);
            typeView = view.findViewById(R.id.tvProductType);

        }
    }
}
