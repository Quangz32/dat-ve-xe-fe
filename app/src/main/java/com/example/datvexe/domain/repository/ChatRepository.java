package com.example.datvexe.domain.repository;

import com.example.datvexe.domain.model.ChatMessage;

import java.util.List;

public interface ChatRepository {
    void connectToChat(String userId);

    void disconnectFromChat();

    void sendMessage(String userId, String message, ChatMessage.Sender sender);

    void setMessageListener(MessageListener listener);

    void setConversationListener(ConversationListener listener);

    void loadConversationHistory(String userId);

    boolean isConnected();

    interface MessageListener {
        void onNewMessage(ChatMessage message);

        void onError(String error);
    }

    interface ConversationListener {
        void onConversationLoaded(List<ChatMessage> messages);

        void onError(String error);
    }
}