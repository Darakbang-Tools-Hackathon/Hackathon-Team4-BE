# --- 1. 빌드(Build) 스테이지 ---
# Java 21과 Gradle을 포함한 이미지를 기반으로 시작합니다.
FROM gradle:8.5-jdk21 AS BUILD_STAGE

# 작업 디렉토리를 /app으로 설정합니다.
WORKDIR /app

# Gradle 빌드에 필요한 파일들을 먼저 복사합니다.
# (이렇게 하면 의존성이 변경되지 않았을 때 빌드 캐시를 사용해 속도가 빨라집니다.)
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
COPY src /app/src

# Gradle build 명령어를 실행하여 .jar 파일을 생성합니다.
RUN gradle build

# --- 2. 실행(Run) 스테이지 ---
# 실제 서버를 실행할 더 가볍고 효율적인 Java 21 런타임 이미지를 사용합니다.
FROM openjdk:21-jre-slim

# 작업 디렉토리를 /app으로 설정합니다.
WORKDIR /app

# 빌드 스테이지(BUILD_STAGE)에서 생성된 .jar 파일을 실행 스테이지로 복사합니다.
# .jar 파일 경로는 본인의 프로젝트에 맞게 확인하세요.
# (build/libs/mymoji-0.0.1-SNAPSHOT.jar 가 맞는지 확인)
COPY --from=BUILD_STAGE /app/build/libs/mymoji-0.0.1-SNAPSHOT.jar app.jar

# 컨테이너가 8080 포트를 외부에 노출하도록 설정합니다.
EXPOSE 8080

# 컨테이너가 시작될 때 실행할 명령어입니다.
ENTRYPOINT ["java", "-jar", "app.jar"]