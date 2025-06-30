package com.example.datvexe.data.mapper;

import com.example.datvexe.data.remote.dto.ChatMessageDto;
import com.example.datvexe.domain.model.ChatMessage;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatMessageMapper {

    @Inject
    public ChatMessageMapper() {
    }

    public ChatMessage toDomain(ChatMessageDto dto) {
        if (dto == null)
            return null;

        ChatMessage.Sender sender = null;
        if (dto.getSender() != null) {
            sender = new ChatMessage.Sender(
                    dto.getSender().getId(),
                    dto.getSender().getName(),
                    dto.getSender().getRole());
        }

        return new ChatMessage(
                dto.getMessage(),
                sender,
                dto.getTimestamp(),
                dto.isRead());
    }

    public ChatMessageDto toDto(ChatMessage domain) {
        if (domain == null)
            return null;

        ChatMessageDto.SenderDto senderDto = null;
        if (domain.getSender() != null) {
            senderDto = new ChatMessageDto.SenderDto(
                    domain.getSender().getId(),
                    domain.getSender().getName(),
                    domain.getSender().getRole());
        }

        return new ChatMessageDto(
                domain.getMessage(),
                senderDto,
                domain.getTimestamp(),
                domain.isRead());
    }
}