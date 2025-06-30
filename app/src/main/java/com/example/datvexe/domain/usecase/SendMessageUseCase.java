package com.example.datvexe.domain.usecase;

import com.example.datvexe.domain.model.ChatMessage;
import com.example.datvexe.domain.repository.ChatRepository;

import javax.inject.Inject;

public class SendMessageUseCase {
    private final ChatRepository chatRepository;

    @Inject
    public SendMessageUseCase(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void execute(String userId, String message, ChatMessage.Sender sender) {
        chatRepository.sendMessage(userId, message, sender);
    }
}