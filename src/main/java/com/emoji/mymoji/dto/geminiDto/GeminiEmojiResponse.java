package com.emoji.mymoji.dto.geminiDto;


/**
 * GeminiService가 반환할 결과 (이모티콘 + 설명)
 */
public record GeminiEmojiResponse(
        String emoji,
        String description
) {
    // Gemini 파싱 실패 시 사용할 기본값
    public static GeminiEmojiResponse fallback() {
        return new GeminiEmojiResponse("🤔", "현재 상태를 파악하기 어렵습니다.");
    }
}
