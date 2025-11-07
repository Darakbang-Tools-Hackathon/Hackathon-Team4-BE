package com.emoji.mymoji.dto.geminiDto;

import java.util.List;

// 요청 본문
public record GeminiRequest(List<Content> contents) {
    public static GeminiRequest fromPrompt(String prompt) {
        return new GeminiRequest(List.of(new Content(List.of(new Part(prompt)))));
    }
}

// 내부 객체들
record Content(List<Part> parts) {}
record Part(String text) {}