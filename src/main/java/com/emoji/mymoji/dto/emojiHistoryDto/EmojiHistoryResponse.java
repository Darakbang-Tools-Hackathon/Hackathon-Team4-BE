package com.emoji.mymoji.dto.emojiHistoryDto;

import java.time.LocalDateTime;

// (이모티콘, 이모티콘 설명, 생성 날짜)
public record EmojiHistoryResponse(
        String emoji,
        String description,
        LocalDateTime createdAt
) {}
