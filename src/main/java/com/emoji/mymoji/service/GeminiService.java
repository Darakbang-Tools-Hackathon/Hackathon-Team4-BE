package com.emoji.mymoji.service; // (패키지 경로는 본인에 맞게)


import com.emoji.mymoji.domain.Users;
import com.emoji.mymoji.dto.geminiDto.GeminiEmojiResponse;
import com.emoji.mymoji.dto.geminiDto.GeminiRequest;
import com.emoji.mymoji.dto.geminiDto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor // RestTemplate 주입을 위해
public class GeminiService {

    // 1. (ChatClient 대신) RestTemplate 주입
    private final RestTemplate restTemplate;

    // 2. Render 환경 변수에서 API 키 가져오기
    @Value("${SPRING_AI_GOOGLE_GEMINI_API_KEY}")
    private String geminiApiKey;

    // 3. Google AI API 엔드포인트
    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=";



    /**
     * Users 객체(5가지 특성치)를 받아 Gemini API로 이모티콘을 생성합니다.
     */
    public GeminiEmojiResponse getEmojiForAttributes(Users user) {

        // 5. API에 전달할 프롬프트(명령어) 생성 (동일)
        String prompt = buildPrompt(user);

        // 6. HTTP 헤더 설정 (API 키와 JSON 타입 명시)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // (참고: API 키를 URL에 ?key=로 붙였기 때문에 헤더에는 필요 없습니다.)

        // 7. HTTP 요청 본문(Body) 생성 (새 DTO 사용)
        GeminiRequest requestBody = GeminiRequest.fromPrompt(prompt);

        // 8. HTTP 요청 객체(Header + Body) 생성
        HttpEntity<GeminiRequest> entity = new HttpEntity<>(requestBody, headers);

        // 9. RestTemplate으로 POST 요청 실행 및 응답 받기
        try {
            GeminiResponse response = restTemplate.postForObject(
                    GEMINI_API_URL + geminiApiKey, // API URL + Key
                    entity,                      // 요청 객체
                    GeminiResponse.class         // 응답받을 DTO 클래스
            );

            // 10. API 응답에서 이모티콘만 깔끔하게 추출
            if (response != null && response.extractText() != null) {
                return parseGeminiResponse(response.extractText());
            } else {
                return GeminiEmojiResponse.fallback(); // 응답이 비었을 경우
            }
        } catch (Exception e) {
            // API 호출 실패 시, 원인 파악을 위해 RuntimeException으로 감싸서 던집니다.
            // 이렇게 하면 Spring Boot의 기본 예외 처리기가 스택 트레이스를 로그에 출력합니다.
            throw new RuntimeException("Gemini API 호출 실패: " + e.getMessage(), e);
        }
    }

    /**
     * Gemini API에 전달할 프롬프트를 생성하는 메소드 (동일)
     */
    private String buildPrompt(Users user) {
        return String.format(
                "당신은 사람의 5가지 성격 특성 점수(0~100)를 보고, 그 사람의 현재 상태를 가장 잘 나타내는 이모티콘 1개와, 그에 대한 1~2줄짜리 짧은 설명 및 감정 관리 팁을 제공하는 전문가입니다.\n" +
                        "\n" +
                        "다음은 사용자의 현재 점수입니다:\n" +
                        "- 정서 안정성 (낮을수록 불안): %.1f\n" +
                        "- 외향성 (높을수록 활기참): %.1f\n" +
                        "- 친화성 (높을수록 다정함): %.1f\n" +
                        "- 성실성 (높을수록 계획적): %.1f\n" +
                        "- 개방성 (높을수록 호기심 많음): %.1f\n\n" +
                        "반드시 다음 형식으로만 응답해야 합니다:\n" +
                        "Emoji: [선택한 이모티콘 1개]\n" +
                        "Description: [생성한 설명과 팁]",
                user.getAttribute1(), user.getAttribute2(), user.getAttribute3(),
                user.getAttribute4(), user.getAttribute5()
        );
    }

    /**
     *  Gemini의 응답("Emoji: ...\nDescription: ...")을 파싱하는 메소드
     */
    private GeminiEmojiResponse parseGeminiResponse(String response) {
        try {
            // "Emoji: 🤩"
            String emojiLine = response.split("\n")[0];
            String emoji = emojiLine.split("Emoji: ")[1].trim();

            // "Description: 들뜬 상태입니다..."
            String descLine = response.split("\n")[1];
            String description = descLine.split("Description: ")[1].trim();

            return new GeminiEmojiResponse(emoji, description);
        } catch (Exception e) {
            // 파싱 실패 (Gemini가 형식을 따르지 않았을 경우), 원인 파악을 위해 RuntimeException으로 감싸서 던집니다.
            throw new RuntimeException("Gemini API 응답 파싱 실패. 응답 내용: " + response, e);
        }
    }
}