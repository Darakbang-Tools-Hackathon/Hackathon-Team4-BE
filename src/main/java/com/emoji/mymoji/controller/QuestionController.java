package com.emoji.mymoji.controller;

import com.emoji.mymoji.domain.Question;
import com.emoji.mymoji.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    /**
     * 오늘의 무작위 질문 목록을 반환하는 API
     */
    @GetMapping("/daily")
    public ResponseEntity<List<Question>> GetDailyQuestions() {
        List<Question> dailyQuestions = questionService.getDailyQuestions();
        return ResponseEntity.ok(dailyQuestions);
    }
}
