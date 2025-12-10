# Store API Project

Store API는 e-commerce 플랫폼을 위한 RESTful 백엔드 서비스입니다.  
사용자 인증부터 상품 검색, 장바구니 관리, 그리고 주문 결제까지의 전체 흐름을 지원합니다.

## 🛠 기술 스택 (Tech Stack)

| Category | Technology | Version |
|----------|------------|---------|
| **Language** | Java | 17 (LTS) |
| **Framework** | Spring Boot | 3.5.7 |
| **Database** | MySQL | 8.0+ |
| **ORM** | Spring Data JPA | - |
| **Migration** | Flyway | - |
| **Security** | Spring Security | 6.x |
| **Auth** | JWT (Json Web Token) | 0.12.5 |
| **Docs** | SpringDoc OpenAPI (Swagger) | 2.8.5 |
| **Build** | Gradle | - |

## 📂 프로젝트 구조 (Project Structure)

```
src/main/java/com/store_api
├── config          # Security, JWT 등 설정 클래스
├── controllers     # API 엔드포인트 처리 (REST Controller)
├── dtos            # 데이터 전송 객체 (Request/Response)
├── entities        # JPA 엔티티 (DB 테이블 매핑)
├── exceptions      # 커스텀 예외 및 핸들러
├── filters         # 서블릿 필터 (JWT 인증 필터 등)
├── mappers         # Entity <-> DTO 변환 (MapStruct)
├── repositories    # DB 접근 계층 (Repository)
└── services        # 비즈니스 로직 처리
```

## 🚀 시작 가이드 (Getting Started)

### 1. 사전 요구사항 (Prerequisites)
- **Java 17** JDK 설치 확인 (`java -version`)
- **MySQL** 서버 실행 중이어야 함
- **Gradle** (Wrapper가 포함되어 있어 별도 설치 불필요)

### 2. 설치 및 실행 (Installation & Run)

**Step 1: 프로젝트 클론**
```bash
git clone https://github.com/ssoup0224/store_api.git
cd store_api
```

**Step 2: 데이터베이스 설정**
`src/main/resources` 디렉토리에 `.env` 파일을 생성하거나 환경 변수를 설정해야 할 수 있습니다. (기본 설정이 `application.yml`에 있는지 확인 필요)
일반적으로 `application.yml`에서 DB 연결 정보를 확인하세요:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/store_db
    username: root
    password: password
```

**Step 3: 빌드 및 실행**
```bash
# Mac/Linux
./gradlew bootRun

# Windows
gradlew.bat bootRun
```
애플리케이션이 시작되면 Flyway가 자동으로 DB 스키마를 마이그레이션합니다.

### 3. API 문서 확인
서버가 정상적으로 실행되면 브라우저에서 아래 주소로 접속하여 API를 테스트할 수 있습니다.
- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 🔑 주요 기능 (Key Features)

1. **사용자 관리 (User Management)**
   - 회원가입 시 비밀번호 암호화 저장 (BCrypt)
   - JWT를 이용한 Stateless 인증 시스템
   - Access Token (Payload) 및 Refresh Token (HttpOnly Cookie) 보안 적용

2. **상품 및 카테고리 (Products)**
   - 카테고리 기반 상품 필터링
   - 관리자(또는 권한 있는 사용자)에 의한 상품 CRUD

3. **장바구니 (Shopping Cart)**
   - UUID 기반의 장바구니 식별 (비회원/회원 공용 설계 가능)
   - 상품 수량 조절 및 실시간 합계 계산

4. **주문 시스템 (Order System)**
   - 장바구니 내용을 기반으로 주문 생성 (Checkout)
   - 주문 생성 시 재고 확인(확장 예정) 및 상태 변경
   - 지난 주문 내역 조회

## 🧪 테스트 (Testing)
단위 테스트 및 통합 테스트를 실행하려면 아래 명령어를 사용하세요.
```bash
./gradlew test
```
