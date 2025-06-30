package com.example.datvexe.presentation.adapter;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
import com.example.datvexe.domain.model.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> {
    private List<ChatMessage> messages = new ArrayList<>();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @SuppressLint("NotifyDataSetChanged")
    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages != null ? messages : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addMessage(ChatMessage message) {
        if (message != null) {
            messages.add(message);
            notifyItemInserted(messages.size() - 1);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageText;
        private TextView timeText;
        private TextView senderName;
        private LinearLayout messageContainer;
        private View messageBubble;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.tv_message);
            timeText = itemView.findViewById(R.id.tv_time);
            senderName = itemView.findViewById(R.id.tv_sender_name);
            messageContainer = itemView.findViewById(R.id.ll_message_container);
            messageBubble = itemView.findViewById(R.id.view_message_bubble);
        }

        public void bind(ChatMessage message) {
            messageText.setText(message.getMessage());

            // Format time
            try {
                if (message.getTimestamp() != null && !message.getTimestamp().isEmpty()) {
                    // Parse ISO timestamp
                    Date date = new Date(message.getTimestamp());
                    timeText.setText(timeFormat.format(date));
                } else {
                    timeText.setText("");
                }
            } catch (Exception e) {
                timeText.setText("");
            }

            // Xác định xem tin nhắn từ user hay admin
            boolean isUserMessage = message.getSender() != null &&
                    "USER".equals(message.getSender().getRole());

            if (isUserMessage) {
                // Tin nhắn từ user - hiển thị bên phải
                messageContainer.setGravity(Gravity.END);
                messageBubble.setBackground(ContextCompat.getDrawable(
                        itemView.getContext(), R.drawable.bg_message_user));
                messageText.setTextColor(ContextCompat.getColor(
                        itemView.getContext(), android.R.color.white));
                senderName.setVisibility(View.GONE);
            } else {
                // Tin nhắn từ admin - hiển thị bên trái
                messageContainer.setGravity(Gravity.START);
                messageBubble.setBackground(ContextCompat.getDrawable(
                        itemView.getContext(), R.drawable.bg_message_admin));
                messageText.setTextColor(ContextCompat.getColor(
                        itemView.getContext(), android.R.color.black));

                if (message.getSender() != null && message.getSender().getName() != null) {
                    senderName.setText(message.getSender().getName());
                    senderName.setVisibility(View.VISIBLE);
                } else {
                    senderName.setText("Admin");
                    senderName.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}