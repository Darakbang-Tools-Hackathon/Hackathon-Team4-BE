package com.emoji.mymoji.service;

import com.emoji.mymoji.domain.Users;
import com.emoji.mymoji.dto.geminiDto.GeminiEmojiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiService {

    /**
     * Users 객체(5가지 특성치)를 받아 규칙 기반으로 이모티콘과 설명을 생성합니다.
     * @param user 특성치를 포함한 Users 객체
     * @return 생성된 이모티콘과 설명을 담은 응답 DTO
     */
    public GeminiEmojiResponse getEmojiForAttributes(Users user) {
        double stability = user.getAttribute1();
        double extraversion = user.getAttribute2();
        double agreeableness = user.getAttribute3();
        double conscientiousness = user.getAttribute4();
        double openness = user.getAttribute5();

        String emoji;
        String description;

        // 규칙 기반 로직
        if (extraversion > 80 && agreeableness > 70) {
            emoji = "🥳";
            description = "활기차고 친화적인, 파티의 주인공 같은 날이네요!";
        } else if (stability < 20) {
            emoji = "😵‍💫";
            description = "조금 불안하고 어지러운 날인가요? 잠시 휴식이 필요해 보여요.";
        } else if (conscientiousness > 85) {
            emoji = "🦾";
            description = "계획대로 착착! 강철 같은 의지로 모든 것을 해내는 날입니다.";
        } else if (openness > 85) {
            emoji = "🚀";
            description = "새로운 아이디어가 샘솟고, 호기심이 우주를 향해 뻗어나가는 날!";
        } else if (agreeableness > 80) {
            emoji = "🐶";
            description = "다정하고 따뜻한 마음씨가 주변 사람들을 행복하게 만들어요.";
        } else if (extraversion < 20 && stability > 70) {
            emoji = "🪴";
            description = "고요하고 안정적인 하루. 혼자만의 시간을 즐기며 에너지를 충전해요.";
        } else if (openness < 20 && conscientiousness > 70) {
            emoji = "🗿";
            description = "묵묵히 당신의 자리를 지키는 당신, 듬직하고 믿음직스러워요.";
        } else if (extraversion > 70) {
            emoji = "🤩";
            description = "세상이 즐겁고 에너지가 넘치는 하루! 무엇이든 할 수 있을 것 같아요.";
        } else if (agreeableness < 20) {
            emoji = "🌵";
            description = "오늘은 조금 예민한 날. 다른 사람과 거리를 두는 것도 좋은 방법이에요.";
        } else if (conscientiousness < 20) {
            emoji = "🫠";
            description = "모든 게 녹아내리는 기분. 오늘은 아무것도 하지 않고 쉬어도 괜찮아요.";
        } else if (openness > 70) {
            emoji = "💡";
            description = "번뜩이는 아이디어가 떠오르는 날! 메모하는 것을 잊지 마세요.";
        } else {
            emoji = "☕️";
            description = "차분하고 평온한 하루. 커피 한 잔의 여유를 즐겨보세요.";
        }

        return new GeminiEmojiResponse(emoji, description);
    }
}