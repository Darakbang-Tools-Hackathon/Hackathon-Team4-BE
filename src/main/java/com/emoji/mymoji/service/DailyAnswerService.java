package com.emoji.mymoji.service;


import com.emoji.mymoji.domain.*;
import com.emoji.mymoji.dto.dailyAnswerDto.AnswerRequestDto;
import com.emoji.mymoji.dto.dailyAnswerDto.AnswerResponse;
import com.emoji.mymoji.dto.geminiDto.GeminiEmojiResponse;
import com.emoji.mymoji.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DailyAnswerService {

    // 1. 필요한 모든 리포지토리와 서비스 주입
    private final UserRepo usersRepository;
    private final QuestionRepo questionRepository;
    private final DailyAnswerRepo dailyAnswerRepository;
    private final WeightingRepo weightingLogicRepository;
    private final EmojiHistoryRepo emojiHistoryRepository;
    private final GeminiService geminiService;

    /**
     * 사용자가 제출한 하루치 답변을 처리하는 메인 로직
     * @param uid 답변을 제출한 사용자의 Firebase UID
     * @param answerDtos 프론트에서 받은 [ {questionId, score} ... ] 리스트
     * @return 오늘 생성된 이모티콘과 그 이모티콘에 대한 설명
     */
    @Transactional // 이 메소드 전체를 하나의 트랜잭션으로 묶음
    public AnswerResponse processDailyAnswers(String uid, List<AnswerRequestDto> answerDtos){

        // 2. 사용자 찾기
        Users user = usersRepository.findByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + uid));

        // 3. 5대 특성치의 '변동값'을 누적할 Map 생성
        Map<String, Double> scoreChanges = new HashMap<>();
        scoreChanges.put("정서안정성", 0.0);
        scoreChanges.put("외향성", 0.0);
        scoreChanges.put("친화성", 0.0);
        scoreChanges.put("성실성", 0.0);
        scoreChanges.put("개방성", 0.0);

        // 4. (중요) DB에서 Question 엔티티들을 미리 조회 (성능 향상)
        List<Long> questionIds = answerDtos.stream().map(AnswerRequestDto::questionId).toList();
        List<Question> questions = questionRepository.findAllById(questionIds);
        Map<Long, Question> questionMap = new HashMap<>();
        for (Question q : questions) {
            questionMap.put(q.getQuestionId(), q);
        }

        // 5. 모든 답변을 순회하며 로직 처리
        for (AnswerRequestDto dto : answerDtos) {
            // 5-1. DailyAnswer 객체 생성 및 저장
            Question question = questionMap.get(dto.questionId());
            if (question != null) {
                DailyAnswer da = DailyAnswer.builder()
                        .user(user)
                        .question(question)
                        .score(dto.score())
                        .build();
                dailyAnswerRepository.save(da); // (저장)
            }

            // 5-2. 가중치 로직 조회 및 '변동값' 누적
            List<WeightingLogic> logics = weightingLogicRepository
                    .findByQuestion_QuestionIdAndLogicScore(dto.questionId(), dto.score());

            for (WeightingLogic logic : logics) {
                String attrName = logic.getAttributeName();
                double value = logic.getValue();
                scoreChanges.merge(attrName, value, Double::sum); // (누적)
            }
        }

        // 6. 누적된 변동값을 Users 엔티티에 적용 및 저장
        user.applyScoreChanges(scoreChanges); // (Users 엔티티에 이 메소드 추가 필요 - 4단계 참고)
        Users updatedUser = usersRepository.save(user); // (업데이트)

        // 7. [Gemini 호출] 업데이트된 User 객체로 GeminiService 호출
        GeminiEmojiResponse geminiResponse = geminiService.getEmojiForAttributes(updatedUser);

        // 8. [History 저장] 오늘의 최종 결과를 EmojiHistory에 스냅샷으로 저장
        EmojiHistory history = EmojiHistory.builder()
                .user(updatedUser)
                .emoji(geminiResponse.emoji()) // .emoji() 사용
                .description(geminiResponse.description()) // [추가]
                .attribute1(updatedUser.getAttribute1())
                .attribute2(updatedUser.getAttribute2())
                .attribute3(updatedUser.getAttribute3())
                .attribute4(updatedUser.getAttribute4())
                .attribute5(updatedUser.getAttribute5())
                .build();

        EmojiHistory savedHistory = emojiHistoryRepository.save(history); // (저장)

        // 9. 프론트에 오늘 생성된 이모티콘과 점수를 반환
        return new AnswerResponse(
                savedHistory.getEmoji(),
                savedHistory.getDescription()
        );
    }
}
