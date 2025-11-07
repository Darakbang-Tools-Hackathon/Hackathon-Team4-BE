package com.emoji.mymoji.repository;

import com.emoji.mymoji.domain.EmojiHistory;
import com.emoji.mymoji.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmojiHistoryRepo  extends JpaRepository<EmojiHistory, Long> {
    // 특정 사용자의 이모티콘 내역을 최신순으로 가져오는 메소드
    List<EmojiHistory> findByUserOrderByCreatedAtDesc(Users user);

    /**
     * (추가) 특정 사용자의 '가장 최신' 이모티콘 내역 1개만 가져옵니다.
     * @param user 조회할 사용자
     * @return 가장 최신 EmojiHistory 객체 (없을 수도 있으므로 Optional)
     */
    Optional<EmojiHistory> findFirstByUserOrderByCreatedAtDesc(Users user);
}
