package com.emoji.mymoji.dto.dailyAnswerDto;


public record AnswerResponse(
        String emoji,
        String description
) {
    public static AnswerResponse fallback(){
        return new AnswerResponse("🤔", "현재 상태를 파악하기 어렵습니다.");
    }
}
