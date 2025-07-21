package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
import com.example.datvexe.domain.model.Discount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
    private List<Discount> discounts = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts != null ? discounts : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discount, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discount discount = discounts.get(position);
        holder.tvTitle.setText("CODE: " + discount.getCode());
        holder.tvCode.setText(discount.getDescription());
        holder.tvPercent.setText("-" + discount.getPercent() + "%");
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvCode;
        TextView tvPercent;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_discount_title);
            tvCode = itemView.findViewById(R.id.tv_discount_code);
            tvPercent = itemView.findViewById(R.id.tv_discount_percent);
        }
    }
} 