package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.data.local.SharedPreferencesManager;
import com.example.datvexe.domain.model.ChatMessage;
import com.example.datvexe.domain.repository.ChatRepository;
import com.example.datvexe.domain.usecase.chat.ConnectToChatUseCase;
import com.example.datvexe.domain.usecase.chat.GetChatMessagesUseCase;
import com.example.datvexe.domain.usecase.chat.SendMessageUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatViewModel extends ViewModel {
    private final ConnectToChatUseCase connectToChatUseCase;
    private final SendMessageUseCase sendMessageUseCase;
    private final GetChatMessagesUseCase getChatMessagesUseCase;
    private final MutableLiveData<List<ChatMessage>> _messages = new MutableLiveData<>(new ArrayList<>());
    public final LiveData<List<ChatMessage>> messages = _messages;
    private final MutableLiveData<Boolean> _isConnected = new MutableLiveData<>(false);
    public final LiveData<Boolean> isConnected = _isConnected;
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;
    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public ChatViewModel(
            ConnectToChatUseCase connectToChatUseCase,
            SendMessageUseCase sendMessageUseCase,
            GetChatMessagesUseCase getChatMessagesUseCase) {
        this.connectToChatUseCase = connectToChatUseCase;
        this.sendMessageUseCase = sendMessageUseCase;
        this.getChatMessagesUseCase = getChatMessagesUseCase;

        setupListeners();
    }

    private void setupListeners() {
        // Lắng nghe tin nhắn mới
        getChatMessagesUseCase.setMessageListener(new ChatRepository.MessageListener() {
            @Override
            public void onNewMessage(ChatMessage message) {
                addNewMessage(message);
            }

            @Override
            public void onError(String error) {
                _error.setValue(error);
            }
        });

        // Lắng nghe lịch sử hội thoại
        getChatMessagesUseCase.setConversationListener(new ChatRepository.ConversationListener() {
            @Override
            public void onConversationLoaded(List<ChatMessage> messages) {
                _messages.setValue(messages);
                _isLoading.setValue(false);
            }

            @Override
            public void onError(String error) {
                _error.setValue(error);
                _isLoading.setValue(false);
            }
        });
    }

    public void connectToChat() {
        try {
            connectToChatUseCase.execute(sharedPreferencesManager.getUserId());
            _isConnected.setValue(true);
            _isLoading.setValue(true);
            getChatMessagesUseCase.loadConversationHistory(
                    sharedPreferencesManager.getUserId()
            );
//            loadConversationHistory();
        } catch (Exception e) {
            _error.setValue("Lỗi kết nối: " + e.getMessage());
            _isConnected.setValue(false);
        }
    }

//    public void loadConversationHistory() {
//        _isLoading.setValue(true);
//        getChatMessagesUseCase.loadConversationHistory(FIXED_USER_ID);
//    }

    public void sendMessage(String messageText) {
        if (messageText == null || messageText.trim().isEmpty()) {
            _error.setValue("Tin nhắn không được để trống");
            return;
        }

        try {
            // Tạo sender info cho user
            ChatMessage.Sender sender = new ChatMessage.Sender(
                    sharedPreferencesManager.getUserId(),
                    "Khách hàng", // Tên mặc định
                    "USER");

            sendMessageUseCase.execute(sharedPreferencesManager.getUserId(), messageText.trim(), sender);
        } catch (Exception e) {
            _error.setValue("Lỗi gửi tin nhắn: " + e.getMessage());
        }
    }

    private void addNewMessage(ChatMessage newMessage) {
        List<ChatMessage> currentMessages = _messages.getValue();
        if (currentMessages != null) {
            List<ChatMessage> updatedMessages = new ArrayList<>(currentMessages);
            updatedMessages.add(newMessage);
            _messages.setValue(updatedMessages);
        }
    }

    public void clearError() {
        _error.setValue(null);
    }
}