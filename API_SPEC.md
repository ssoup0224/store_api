# API 명세서 (Store API)

이 문서는 Store API의 상세 엔드포인트, 요청/응답 스키마, 및 예시를 포함합니다.
서버 기본 주소: `http://localhost:8080`

> **참고**: Swagger UI는 `http://localhost:8080/swagger-ui/index.html`에서 접속 가능합니다.

---

## 1. 인증 (Authentication)
기본 URL: `/auth`

### 1.1 로그인
- **URL**: `POST /auth/login`
- **설명**: 이메일과 비밀번호를 사용하여 Access Token을 발급받습니다. Refresh Token은 HttpOnly 쿠키로 설정됩니다.
- **요청 Body**:
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```
  - `email`: (필수) 이메일 형식
  - `password`: (필수) 비밀번호
- **성공 응답 (200 OK)**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiJ9..." 
  }
  ```
- **실패 응답 (401 Unauthorized)**: 잘못된 자격 증명

### 1.2 토큰 갱신
- **URL**: `POST /auth/refresh`
- **설명**: `refreshToken` 쿠키를 사용하여 새로운 Access Token을 발급받습니다.
- **요청 헤더**: (없음, 쿠키 자동 전송)
- **응답 (200 OK)**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  }
  ```
- **실패 응답 (401 Unauthorized)**: Refresh Token 만료 또는 유효하지 않음

### 1.3 내 정보 조회
- **URL**: `GET /auth/me`
- **설명**: 현재 로그인된 사용자의 상세 정보를 반환합니다.
- **요청 헤더**: `Authorization: Bearer <AccessToken>`
- **응답 (200 OK)**:
  ```json
  {
    "id": 1,
    "name": "홍길동",
    "email": "user@example.com"
  }
  ```

---

## 2. 사용자 (User)
기본 URL: `/users`

### 2.1 회원가입
- **URL**: `POST /users`
- **설명**: 신규 사용자를 생성합니다.
- **요청 Body**:
  ```json
  {
    "name": "홍길동",
    "email": "newuser@example.com",
    "password": "password123"
  }
  ```
  - `name`: (필수) 최대 255자
  - `email`: (필수) 유효한 이메일 형식, 소문자로 저장됨
  - `password`: (필수) 6자 이상 25자 이하
- **응답 (201 Created)**:
  ```json
  {
    "id": 2,
    "name": "홍길동",
    "email": "newuser@example.com"
  }
  ```
- **실패 응답 (400 Bad Request)**: 이메일 중복 또는 유효성 검사 실패

### 2.2 전체 사용자 조회
- **URL**: `GET /users`
- **Query Parameters**:
  - `sort`: (선택) 정렬 기준 필드 (`name` 또는 `email`). 기본값 `name`.
- **응답 (200 OK)**:
  ```json
  [
    {
      "id": 1,
      "name": "김철수",
      "email": "kim@example.com"
    },
    ...
  ]
  ```

### 2.3 단일 사용자 조회
- **URL**: `GET /users/{id}`
- **응답 (200 OK)**:
  ```json
  {
    "id": 1,
    "name": "김철수",
    "email": "kim@example.com"
  }
  ```
- **실패 응답 (404 Not Found)**: 존재하지 않는 사용자 ID

### 2.4 사용자 정보 수정
- **URL**: `PATCH /users/{id}`
- **요청 Body**: (수정하려는 필드만 포함)
  ```json
  {
    "name": "김철수(수정)",
    "email": "newemail@example.com"
  }
  ```
- **응답 (200 OK)**: 수정된 사용자 정보

### 2.5 비밀번호 변경
- **URL**: `POST /users/{id}/change-password`
- **요청 Body**:
  ```json
  {
    "oldPassword": "currentPassword",
    "newPassword": "newPassword123"
  }
  ```
- **응답 (204 No Content)**: 성공 시 본문 없음
- **실패 응답 (401 Unauthorized)**: 기존 비밀번호 불일치

### 2.6 사용자 삭제
- **URL**: `DELETE /users/{id}`
- **응답 (204 No Content)**: 성공 시 본문 없음

---

## 3. 상품 (Product)
기본 URL: `/products`

### 3.1 상품 목록 조회
- **URL**: `GET /products`
- **Query Parameters**:
  - `categoryId`: (선택) 특정 카테고의 상품만 조회 시 사용 (Byte 타입)
- **응답 (200 OK)**:
  ```json
  [
    {
      "id": 10,
      "name": "무선 마우스",
      "price": 25000,
      "description": "인체공학 디자인",
      "categoryId": 1
    }
  ]
  ```

### 3.2 상품 생성
- **URL**: `POST /products`
- **요청 Body**:
  ```json
  {
    "name": "기계식 키보드",
    "price": 120000,
    "description": "적축 스위치",
    "categoryId": 1
  }
  ```
- **응답 (201 Created)**: 생성된 상품 정보

### 3.3 상품 수정
- **URL**: `PATCH /products/{id}`
- **요청 Body**:
  ```json
  {
    "price": 110000,
    "description": "할인 적용"
  }
  ```
- **응답 (200 OK)**: 수정된 상품 정보

### 3.4 상품 삭제
- **URL**: `DELETE /products/{id}`
- **응답 (204 No Content)**

---

## 4. 장바구니 (Cart)
기본 URL: `/carts`

### 4.1 장바구니 생성
- **URL**: `POST /carts`
- **설명**: 빈 장바구니를 생성합니다.
- **응답 (201 Created)**:
  ```json
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "items": [],
    "totalPrice": 0
  }
  ```

### 4.2 장바구니 조회
- **URL**: `GET /carts/{cartId}`
- **Path Variable**: `cartId` (UUID)
- **응답 (200 OK)**:
  ```json
  {
    "id": "550e8400-...",
    "items": [
      {
        "product": {
          "id": 10,
          "name": "무선 마우스",
          "price": 25000
        },
        "quantity": 2,
        "totalPrice": 50000
      }
    ],
    "totalPrice": 50000
  }
  ```

### 4.3 장바구니 아이템 추가
- **URL**: `POST /carts/{cartId}/items`
- **요청 Body**:
  ```json
  {
    "productId": 10
  }
  ```
  기본 수량은 비즈니스 로직에 따름(보통 1개).
- **응답 (201 Created)**: 추가된 아이템 정보 (CartItemResponse)

### 4.4 장바구니 아이템 수량 변경
- **URL**: `PATCH /carts/{cartId}/items/{productId}`
- **요청 Body**:
  ```json
  {
    "quantity": 3
  }
  ```
  - `quantity`: (필수) 1 이상의 정수
- **응답 (200 OK)**: 업데이트된 아이템 정보

### 4.5 장바구니 아이템 삭제
- **URL**: `DELETE /carts/{cartId}/items/{productId}`
- **응답 (204 No Content)**

### 4.6 장바구니 비우기
- **URL**: `DELETE /carts/{cartId}/items`
- **응답 (204 No Content)**

---

## 5. 체크아웃 및 주문 (Checkout & Order)

### 5.1 체크아웃 (주문 생성)
- **URL**: `POST /checkout`
- **요청 Body**:
  ```json
  {
    "cartId": "550e8400-e29b-41d4-a716-446655440000"
  }
  ```
- **응답 (200 OK)**:
  ```json
  {
    "orderId": 101
  }
  ```
- **에러**: 장바구니가 비어있거나 존재하지 않을 경우 400 Bad Request.

### 5.2 전체 주문 조회
- **URL**: `GET /orders`
- **응답 (200 OK)**:
  ```json
  [
    {
      "id": 101,
      "status": "COMPLETED",
      "createdAt": "2024-01-01T10:00:00",
      "totalPrice": 50000,
      "items": [...]
    }
  ]
  ```

### 5.3 주문 상세 조회
- **URL**: `GET /orders/{orderId}`
- **응답 (200 OK)**: 단일 주문 상세 정보 반환.
