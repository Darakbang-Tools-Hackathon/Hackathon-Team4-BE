package com.emoji.mymoji.repository;

import com.emoji.mymoji.domain.DailyAnswer;
import com.emoji.mymoji.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface DailyAnswerRepo extends JpaRepository<DailyAnswer, Long> {
    // (선택 사항) 특정 사용자의 특정 날짜 범위 답변을 찾는 메소드 (나중에 통계용)
    List<DailyAnswer> findByUserAndCreatedAtBetween(Users user, LocalDateTime start, LocalDateTime end);
    /**
     * 특정 사용자의 모든 답변을 시간순(오래된 순, 오름차순)으로 정렬하여 가져옵니다.
     * @param user 조회할 사용자
     * @return 시간순으로 정렬된 답변 목록
     */
    List<DailyAnswer> findByUserOrderByCreatedAt(Users user);
}
