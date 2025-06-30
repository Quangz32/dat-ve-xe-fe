package com.example.datvexe.domain.usecase.chat;

import com.example.datvexe.domain.repository.ChatRepository;

import javax.inject.Inject;

public class GetChatMessagesUseCase {
    private final ChatRepository chatRepository;

    @Inject
    public GetChatMessagesUseCase(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void setMessageListener(ChatRepository.MessageListener listener) {
        chatRepository.setMessageListener(listener);
    }

    public void setConversationListener(ChatRepository.ConversationListener listener) {
        chatRepository.setConversationListener(listener);
    }

    public void loadConversationHistory(String userId) {

        chatRepository.loadConversationHistory(userId);
    }
}