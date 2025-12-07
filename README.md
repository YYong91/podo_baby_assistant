# 🍇 Podo Baby Assistant

Podo Baby Assistant는 “포도야”라고 부르면  
아기 상태 기록, 책·놀이 추천, 육아 Q&A를 음성으로 제공하는 **AI 육아 어시스턴트**입니다.

---

## ✨ 주요 기능

####  (개발중)
- **육아일지 기록(BabyLog)**  
  수면·식사·배변·건강·놀이 상황을 음성으로 바로 기록

- **육아 Q&A / 대화(Interaction)**

- **아기 관련된 대화만 자동 저장**  
  GPT가 intent/type/store 여부를 판단

#### (개발 예정)
- **나이 기반 책/놀이/활동 추천(Content)(예정)**

---

## 🧠 전체 아키텍처

```mermaid
graph TD
    User[👨‍👩‍👧 User] --> Speaker[🔊 Podo Speaker Client]
    Speaker --> STT[Whisper STT]
    STT --> API[/POST /api/v1/orchestrate/]
    API --> Orchestrator[🧠 MessageOrchestrator (core)]
    Orchestrator --> Brain[🤖 BrainClient (GPT)]
    Orchestrator --> Mediator[🔀 Mediator (shared)]
    Mediator --> BabyLog[📘 BabyLog BC]
    Mediator --> Content[📚 Content BC]
    Mediator --> Interaction[💬 Interaction BC]
    BabyLog --> DB[(PostgreSQL)]
    Content --> DB
    Interaction --> DB
    Orchestrator --> Speaker
    Speaker --> User
```

---

## 📦 패키지 구조

```
com.podo.babyassistant
  ├─ core/
  │    ├─ orchestrator/
  │    │     ├─ MessageOrchestrator.java
  │    │     ├─ BrainClient.java
  │    │     ├─ BrainResult.java
  │    │     ├─ IntentType.java
  │    │     └─ OrchestratorResult.java
  │    └─ api/
  │          └─ OrchestratorController.java
  │
  ├─ modules/
  │    ├─ babylog/
  │    │     ├─ application/
  │    │     ├─ domain/
  │    │     └─ infrastructure/
  │    ├─ content/
  │    └─ interaction/
  │
  ├─ shared/
  │    ├─ kernel/                ← DDD Shared Kernel
  │    │     ├─ domain/
  │    │     │     ├─ DomainEvent.java
  │    │     │     ├─ EntityBase.java
  │    │     │     └─ ValueObjectBase.java
  │    │     └─ application/
  │    │           └─ UnitOfWork.java    ← 유즈케이스 트랜잭션 경계
  │    │
  │    ├─ mediator/              ← 기술적 패턴 (CQRS/Mediator)
  │    │     ├─ Mediator.java
  │    │     ├─ Request.java
  │    │     ├─ RequestHandler.java
  │    │     └─ SpringMediator.java
  │    │
  │    └─ infrastructure/        ← Spring 기반 구현체
  │          ├─ SpringUnitOfWork.java
  │          └─ NoOpTransientMessagePublisher.java
  │
  └─ PodoApplication.java
```


## 🌐 주요 API

### **POST /api/v1/orchestrate**

텍스트(Whisper 결과)를 받아 intent 분석 + 유즈케이스 실행 후 답변 반환.

**Request**
```json
{
  "userId": "user-123",
  "babyId": "baby-001",
  "deviceId": "abc",
  "conversationId": "conv-001",
  "text": "오늘 채이는 소고기를 처음 먹었어",
  "locale": "ko-KR",
  "timestamp": "2025-01-01T10:00:00Z"
}
```

**Response**
```json
{
  "conversationId": "conv-001",
  "replyText": "채이가 소고기를 처음 먹었군요! 일지에 기록해둘게요.",
  "intent": "RECORD_BABY_LOG",
  "isAboutBaby": true,
  "shouldStoreConversation": true
}
```

---

## ⚙️ 환경 변수

| 변수명 | 설명 |
|-------|------|
| `OPENAI_API_KEY` | GPT 호출 |
| `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` | RDS |
| `PODO_BABY_NAME` | 응답 커스터마이징 |
| `PODO_ENV` | 실행 환경 |

---

## 🚀 CI/CD 개요

- GitHub Actions  
  - Gradle build/test  
  - Docker build → ECR push  
  - ECS Service update  

---
