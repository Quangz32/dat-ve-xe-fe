package com.example.datvexe.presentation.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
import com.example.datvexe.domain.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<Notification> notifications = new ArrayList<>();

    public NotificationAdapter() {
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications != null ? notifications : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        holder.bind(notifications.get(position));
    }

    @Override
    public int getItemCount() {
        return notifications != null ? notifications.size() : 0;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivStatus;
        private final TextView tvTitle;
        private final TextView tvMessage;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStatus = itemView.findViewById(R.id.iv_status);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }

        public void bind(Notification notification) {
            tvTitle.setText(notification.getTitle());
            tvMessage.setText(notification.getMessage());
            if (notification.getTab().equals(
                    Notification.NotificationTab.PROMOTIONS.getValue()
            )) {
                ivStatus.setImageResource(R.drawable.baseline_card_giftcard_24);
            } else {
                if (notification.getType().equals("success")) {
                    ivStatus.setImageResource(R.drawable.baseline_check_circle_24);
                } else if (notification.getType().equals("cancel")) {
                    ivStatus.setImageResource(R.drawable.baseline_cancel_24);
                    ivStatus.setImageTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(ivStatus.getContext(), R.color.orangered)));
                }
            }


        }
    }
}
