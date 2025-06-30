package com.example.datvexe.data.remote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private String message;
    private SenderDto sender;
    private String timestamp;
    private boolean read;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SenderDto {
        private String id;
        private String name;
        private String role;
    }
}