package com.emoji.mymoji.service;

import com.emoji.mymoji.domain.EmojiHistory;
import com.emoji.mymoji.domain.Users;
import com.emoji.mymoji.dto.emojiHistoryDto.EmojiHistoryResponse;
import com.emoji.mymoji.repository.EmojiHistoryRepo;
import com.emoji.mymoji.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmojiService {
    private final EmojiHistoryRepo emojiHistoryRepo;
    private final UserRepo userRepo;

    /**
     * 특정 사용자(uid)의 가장 최신 이모티콘을 반환합니다.
     */
    @Transactional(readOnly = true)
    public String getLatestEmoji(String uid) {
        // 1. uid로 Users 객체를 찾습니다.
        Users user = userRepo.findByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. Repository에서 가장 최신 내역을 찾습니다.
        Optional<EmojiHistory> latestHistory =
                emojiHistoryRepo.findFirstByUserOrderByCreatedAtDesc(user);

        // 3. 내역이 존재하면(ifPresent) 이모티콘을,
        //    존재하지 않으면(orElse) 기본값을 반환합니다.
        return latestHistory
                .map(EmojiHistory::getEmoji) // EmojiHistory 객체에서 emoji 문자열만 추출
                .orElse("🤔"); // 👈 아직 기록이 없는 사용자를 위한 기본 이모티콘
    }

    /**
     * (추가) 특정 사용자의 전체 이모티콘 변화 내역을 최신순으로 가져옵니다.
     * @param uid 조회할 사용자의 Firebase UID
     * @return 이모티콘 변화 내역 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<EmojiHistoryResponse> getEmojiHistory(String uid) {

        // 1. UID로 Users 객체를 찾습니다.
        Users user = userRepo.findByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + uid));

        // 2. 요청하신 리포지토리 메소드를 호출합니다. (엔티티 리스트 반환)
        List<EmojiHistory> historyEntities = emojiHistoryRepo.findByUserOrderByCreatedAtDesc(user);

        // 3. 엔티티 리스트(List<EmojiHistory>)를 DTO 리스트(List<EmojiHistoryResponse>)로 변환합니다.
        return historyEntities.stream()
                .map(history -> new EmojiHistoryResponse(
                        history.getEmoji(),
                        history.getDescription(),
                        history.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }


}
