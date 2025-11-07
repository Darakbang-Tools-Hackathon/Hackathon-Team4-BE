package com.emoji.mymoji.dto.geminiDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// 응답 본문 (필요한 필드 외에는 무시)
@JsonIgnoreProperties(ignoreUnknown = true)
public record GeminiResponse(List<Candidate> candidates) {

    // 응답 텍스트를 추출하는 헬퍼 메소드
    public String extractText() {
        try {
            return this.candidates.get(0).content().parts().get(0).text();
        } catch (Exception e) {
            return null; // 오류 발생 시 null 반환
        }
    }
}

// 내부 객체들
@JsonIgnoreProperties(ignoreUnknown = true)
record Candidate(ResponseContent content) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record ResponseContent(List<ResponsePart> parts) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record ResponsePart(String text) {}
