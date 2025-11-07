package com.emoji.mymoji.controller;

import com.emoji.mymoji.dto.emojiHistoryDto.EmojiHistoryResponse;
import com.emoji.mymoji.dto.userDto.SignUpRequest;
import com.emoji.mymoji.dto.userDto.UserResponse;
import com.emoji.mymoji.service.EmojiService;
import com.emoji.mymoji.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmojiService emojiService;

    //회원가입 api
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        try{
            UserResponse response=userService.signUp(signUpRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 특정 사용자의 전체 이모티콘 히스토리 조회 API
     */
    @GetMapping("/{uid}/history")
    public ResponseEntity<List<EmojiHistoryResponse>> handleGetEmojiHistory(@PathVariable String uid) {
        try {
            List<EmojiHistoryResponse> history = emojiService.getEmojiHistory(uid);
            return ResponseEntity.ok(history);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //특정 사용자의 가장 최근 이모티콘 조회 api
    @GetMapping("/{uid}/latestEmoji")
    public ResponseEntity<String> getLatestEmoji(@PathVariable String uid) {
        try {
            String emoji = emojiService.getLatestEmoji(uid);
            return ResponseEntity.ok(emoji);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
