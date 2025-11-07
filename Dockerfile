# ============================
# 1️⃣ Build Stage
# ============================
FROM gradle:8.5-jdk21 AS build

# 빌드 캐시 활용을 위해 작업 디렉토리 설정
WORKDIR /app

# Gradle wrapper와 설정 파일만 먼저 복사 (의존성 캐시 최적화)
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Gradle 의존성만 미리 다운로드 (빌드 캐시 유지)
RUN ./gradlew dependencies --no-daemon || return 0

# 소스 코드 복사
COPY src src

# 실제 빌드 수행
RUN ./gradlew clean build -x test --no-daemon --parallel

# ============================
# 2️⃣ Runtime Stage
# ============================
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# build stage에서 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/mymoji-*-SNAPSHOT.jar app.jar

# 포트 노출 (Spring Boot 기본)
EXPOSE 8080

# Render는 ENTRYPOINT 보다 CMD 선호 → 유지보수 및 오버라이드 용이
CMD ["java", "-jar", "app.jar"]