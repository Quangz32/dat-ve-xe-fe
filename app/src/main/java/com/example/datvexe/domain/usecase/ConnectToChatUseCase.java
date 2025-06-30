package com.example.datvexe.domain.usecase;

import com.example.datvexe.domain.repository.ChatRepository;

import javax.inject.Inject;

public class ConnectToChatUseCase {
    private final ChatRepository chatRepository;

    @Inject
    public ConnectToChatUseCase(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void execute(String userId) {
        chatRepository.connectToChat(userId);
    }
}