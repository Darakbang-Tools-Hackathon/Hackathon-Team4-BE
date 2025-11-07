package com.emoji.mymoji.dto.emojiHistoryDto;

import java.time.LocalDateTime;

// (이모티콘, 5가지 특성치 점수, 생성 날짜)
public record EmojiHistoryResponse(
        String emoji,
        double attribute1,
        double attribute2,
        double attribute3,
        double attribute4,
        double attribute5,
        LocalDateTime createdAt
) {}
