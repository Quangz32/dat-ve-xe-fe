package com.example.datvexe.data.repository;

import android.util.Log;

import com.example.datvexe.data.remote.service.ChatSocketService;
import com.example.datvexe.domain.model.ChatMessage;
import com.example.datvexe.domain.repository.ChatRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.socket.client.Socket;

@Singleton
public class ChatRepositoryImpl implements ChatRepository {
    private final ChatSocketService chatSocketService;

    @Inject
    public ChatRepositoryImpl(ChatSocketService chatSocketService) {
        this.chatSocketService = chatSocketService;
    }

    @Override
    public void connectToChat(String userId) {
        if (!chatSocketService.isConnected()) {
            Log.d("ChatRepositoryImpl", "Socket connecting.");
            chatSocketService.connect().on(Socket.EVENT_CONNECT, args -> {
                Log.d("TAG", "Socket connected");
                chatSocketService.joinChat(userId);
            });
        } else {
            chatSocketService.joinChat(userId);
        }


    }

    @Override
    public void disconnectFromChat() {
        chatSocketService.disconnect();
    }

    @Override
    public void sendMessage(String userId, String message, ChatMessage.Sender sender) {
        chatSocketService.sendMessage(userId, message, sender);
    }

    @Override
    public void setMessageListener(MessageListener listener) {
        chatSocketService.setMessageListener(listener);
    }

    @Override
    public void setConversationListener(ConversationListener listener) {
        chatSocketService.setConversationListener(listener);
    }

    @Override
    public void loadConversationHistory(String userId) {
        //nếu chưa Kết nối thì kết nối
        if (!chatSocketService.isConnected()) {
            chatSocketService.connect().on(Socket.EVENT_CONNECT, args -> {
                Log.d("TAG", "Socket connected");
                chatSocketService.getConversationHistory(userId);
            });
        } else {
            chatSocketService.getConversationHistory(userId);
        }
    }

    @Override
    public boolean isConnected() {
        return chatSocketService.isConnected();
    }
}