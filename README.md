# Mymoji (마이모지)

> 사용자의 하루를 묻고, AI를 통해 오늘의 감정 이모지를 만들어주는 프로젝트

## 📝 프로젝트 소개

Mymoji는 매일 주어지는 질문에 대한 사용자의 답변을 기반으로 5가지 성격 특성(정서안정성, 외향성, 친화성, 성실성, 개방성)을 분석합니다. 분석된 특성치를 Google Gemini AI에 전달하여 사용자의 현재 상태를 가장 잘 나타내는 이모지와 감정 관리 팁을 생성하고, 이를 기록하여 사용자의 감정 변화를 추적할 수 있도록 돕습니다.

## ✨ 주요 기능

*   **✍️ 오늘의 질문**: 매일 무작위로 제공되는 질문에 답변합니다.
*   **📊 5대 성격 특성 분석**: 답변을 기반으로 사용자의 5가지 성격 특성 점수를 업데이트합니다.
*   **🤖 AI 기반 이모지 생성**: 업데이트된 특성 점수를 Google Gemini API에 전송하여 오늘의 이모지와 설명을 생성합니다.
*   **🗂️ 이모지 히스토리**: 사용자의 일별 이모지, 특성 점수, AI의 설명을 기록하고 조회할 수 있습니다.
*   **🔒 사용자 인증**: Firebase Authentication을 이용한 안전한 회원가입 및 인증을 제공합니다.

## 🛠️ 기술 스택

*   **Backend**: Java, Spring Boot
*   **Database**: PostgreSQL
*   **ORM**: Spring Data JPA (Hibernate)
*   **Authentication**: Firebase Authentication
*   **AI**: Google Gemini API
*   **Build Tool**: Gradle
*   **Etc**: Lombok

## 🚀 API 엔드포인트

| Method | URL | 설명 |
| --- | --- | --- |
| `POST` | `/api/users/signup` | 회원가입 |
| `GET` | `/api/users/{uid}/history` | 특정 사용자의 전체 이모지 히스토리 조회 |
| `GET` | `/api/users/{uid}/latestEmoji` | 특정 사용자의 가장 최근 이모지 조회 |
| `GET` | `/api/questions/daily` | 오늘의 질문 목록 조회 (6개) |
| `POST` | `/api/answers/{uid}` | 특정 사용자의 하루치 답변 제출 및 오늘 이모지 생성 |

## ⚙️ 실행 방법

1.  **리포지토리 클론**
    ```bash
    git clone https://github.com/your-username/mymoji.git
    cd mymoji
    ```

2.  **데이터베이스 설정**
    *   PostgreSQL 데이터베이스를 생성합니다.

3.  **환경 변수 설정**
    `src/main/resources/application.properties` 파일이 참조하는 다음 환경 변수를 설정해야 합니다. 또한, Firebase Admin SDK 설정을 위한 `GOOGLE_APPLICATION_CREDENTIALS` 환경 변수도 필요합니다.

    *   `DB_URL`: PostgreSQL 데이터베이스 URL (e.g., `jdbc:postgresql://localhost:5432/mymoji`)
    *   `DB_USERNAME`: 데이터베이스 사용자 이름
    *   `DB_PASSWORD`: 데이터베이스 비밀번호
    *   `SPRING_AI_GOOGLE_GEMINI_API_KEY`: Google Gemini API 키
    *   `GOOGLE_APPLICATION_CREDENTIALS`: Firebase Admin SDK용 서비스 계정 키 (JSON 파일 경로)

4.  **애플리케이션 실행**
    ```bash
    ./gradlew bootRun
    ```
