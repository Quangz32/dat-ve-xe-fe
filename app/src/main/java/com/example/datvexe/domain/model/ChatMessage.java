package com.example.datvexe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String message;
    private Sender sender;
    private String timestamp;
    private boolean read;

    @Override
    public String toString() {
        return "ChatMessage{"
                + "message='" + message + '\''
                + ", sender=" + sender.name
                + '}';
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sender {
        private String id;
        private String name;
        private String role; // "USER" hoặc "ADMIN"
    }
}