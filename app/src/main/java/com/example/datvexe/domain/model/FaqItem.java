package com.example.datvexe.domain.model;

public class FaqItem {
    private final String question;
    private final String answer;

    public FaqItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
} 