package com.emoji.mymoji.service;

import com.emoji.mymoji.domain.Question;
import com.emoji.mymoji.repository.QuestionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepo questionRepo;

    private static final int DAILY_QUESTION_COUNT = 5;

    /**
     * DB에서 무작위로 6개의 질문을 (엔티티) 리스트로 반환합니다.
     */
    @Transactional(readOnly = true)
    public List<Question> getDailyQuestions() {

        // 1. Repository의 네이티브 쿼리 호출
        // 2. 변환 과정 없이 엔티티 리스트를 그대로 반환
        return questionRepo.findRandomQuestions(DAILY_QUESTION_COUNT);
    }
}
