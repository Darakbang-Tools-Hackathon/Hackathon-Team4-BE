package com.emoji.mymoji.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 2. 이 클래스를 Spring 설정으로 등록
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 3. /api/ 로 시작하는 모든 경로에 적용
                .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173") // 4. 로컬 프론트 주소 허용
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 5. 허용할 HTTP 메소드
                .allowedHeaders("*") // 6. 모든 HTTP 헤더 허용
                .allowCredentials(true) // 7. 쿠키/인증 헤더 허용
                .maxAge(3600); // 8. Preflight 요청 캐시 시간 (1시간)
    }
}
