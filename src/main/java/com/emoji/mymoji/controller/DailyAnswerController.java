package com.emoji.mymoji.controller;

import com.emoji.mymoji.dto.dailyAnswerDto.AnswerRequestDto;
import com.emoji.mymoji.dto.dailyAnswerDto.AnswerResponse;
import com.emoji.mymoji.service.DailyAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class DailyAnswerController {

    private final DailyAnswerService dailyAnswerService;

    /**
     * 사용자의 하루치 답변 목록을 제출받아 처리하는 API
     * @param uid (보안 적용 전) 사용자의 Firebase UID
     * @param answerDtos [{questionId, score}, ...] 형식의 JSON 리스트
     * @return 오늘 생성된 이모티콘과 그 당시의 특성치 스냅샷
     */
    @PostMapping("/{uid}")
    public ResponseEntity<AnswerResponse> submitAnswers(
            @PathVariable String uid,
            @RequestBody List<AnswerRequestDto> answerDtos) {
        try {
            AnswerResponse response = dailyAnswerService.processDailyAnswers(uid, answerDtos);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 사용자가 없을 경우
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 기타 서버 오류
        }
    }
}
