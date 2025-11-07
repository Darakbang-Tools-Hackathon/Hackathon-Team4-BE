package com.emoji.mymoji.repository;

import com.emoji.mymoji.domain.Question;
import com.emoji.mymoji.domain.WeightingLogic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WeightingRepo extends JpaRepository<WeightingLogic, Long> {

    // (필수) 질문과 점수를 기반으로 모든 가중치 로직을 찾는 메소드
    // 예: 1번 질문에 5점을 줬을 때의 로직들(성실성+0.5, 안정성+0.3...)을 가져옴
    List<WeightingLogic> findByQuestionAndLogicScore(Question question, int logicScore);

    // (대안) Question의 ID로도 찾을 수 있게 오버로딩하면 편리할 수 있습니다.
    List<WeightingLogic> findByQuestion_QuestionIdAndLogicScore(Long questionId, int logicScore);
}

