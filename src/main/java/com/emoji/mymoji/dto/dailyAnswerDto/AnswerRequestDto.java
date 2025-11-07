package com.emoji.mymoji.dto.dailyAnswerDto;

// 프론트에서 받을 개별 답변 1개의 형식
public record AnswerRequestDto(
        Long questionId,
        int score
) {}
