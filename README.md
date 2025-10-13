# BookStore Assignment

Spring Boot 기반의 온라인 서점 프로젝트입니다. 알라딘 API를 활용한 도서 정보 관리와 기본적인 쇼핑몰 기능을 제공합니다.

## 프로젝트 개요

이 프로젝트는 **알라딘 API를 통해 도서 정보를 조회하고 상품으로 등록**하는 기능을 중심으로 개발되었습니다.
처음 의도는 알라딘 API와의 완전한 통합을 통해 도서 데이터를 편리하게 관리하는 시스템을 구축하는 것이었으나,
**토이 프로젝트와 병행하면서 시간적 제약**으로 인해 기본적인 CRUD 기능과 장바구니 시스템을 중심으로 구현하게 되었습니다.

## 기술 스택

- **Java**: 21
- **Spring Boot**: 3.5.5
- **Spring Data JPA**: 데이터베이스 접근
- **Spring WebFlux**: 알라딘 API 호출
- **Thymeleaf**: 서버 사이드 템플릿 엔진
- **H2 Database**: 인메모리 데이터베이스
- **Lombok**: 보일러플레이트 코드 제거
- **Gradle**: 빌드 도구

## 주요 기능

### 1. 상품 관리
- 상품 목록 조회 (페이지네이션)
- 상품 상세 정보 조회
- 관리자 상품 등록 (이미지 업로드 지원)

### 2. 알라딘 API 연동
- 도서 검색 기능
- 베스트셀러 목록 조회
- ISBN 기반 도서 정보 조회
- 알라딘 도서를 상품으로 등록

### 3. 장바구니
- 장바구니에 상품 추가
- 수량 변경
- 개별/선택 상품 삭제
- 장바구니 전체 비우기
- 총 금액 계산

### 4. 사용자 관리
- 회원가입
- 로그인 (기본 기능만 구현)

## API 엔드포인트

### 상품 (Products)

| Method | Endpoint | Description | 비고 |
|--------|----------|-------------|------|
| GET | `/` | 상품 목록 조회 | 페이지네이션 지원 (기본 30개) |
| GET | `/products/{id}` | 상품 상세 조회 | - |
| GET | `/admin/product/register` | 상품 등록 페이지 | 관리자 전용 |
| POST | `/admin/product/register` | 상품 등록 처리 | 이미지 업로드 지원 |

### 알라딘 연동 (Aladin)

| Method | Endpoint | Description | 비고 |
|--------|----------|-------------|------|
| GET | `/admin/aladin/search` | 알라딘 검색 페이지 | 관리자 전용 |
| GET | `/admin/aladin/api/search` | 도서 검색 API | AJAX, 키워드 기반 검색 |
| GET | `/admin/aladin/api/bestsellers` | 베스트셀러 조회 API | AJAX |
| GET | `/admin/aladin/select` | 도서 선택 및 등록 폼 | ISBN 파라미터 필요 |
| POST | `/admin/aladin/import` | 알라딘 도서 등록 | ISBN, 재고, 크기 정보 필요 |

### 장바구니 (Cart)

| Method | Endpoint | Description | 비고 |
|--------|----------|-------------|------|
| GET | `/cart` | 장바구니 페이지 | - |
| POST | `/cart/add` | 장바구니 추가 | productId, qty 필요 |
| POST | `/cart/update/{cartItemId}` | 수량 변경 | - |
| POST | `/cart/remove/{cartItemId}` | 개별 상품 삭제 | - |
| POST | `/cart/remove-selected` | 선택 상품 삭제 | cartItemIds 배열 필요 |
| POST | `/cart/clear` | 장바구니 전체 비우기 | - |
| GET | `/cart/count` | 장바구니 개수 조회 | AJAX용 |

### 사용자 (User)

| Method | Endpoint | Description | 비고 |
|--------|----------|-------------|------|
| GET | `/user/signup` | 회원가입 페이지 | - |
| POST | `/user/signUp` | 회원가입 처리 | - |
| GET | `/user/login` | 로그인 페이지 | - |
| POST | `/user/login` | 로그인 처리 | 기본 구현만 완료 |

## 프로젝트 구조

```
src/main/java/org/bookstore/bookstore/
├── cart/                      # 장바구니 도메인
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
├── products/                  # 상품 도메인
│   ├── controller/
│   │   ├── create/           # 상품 등록
│   │   ├── test/             # 테스트용 컨트롤러
│   │   ├── AladinController.java
│   │   └── ProductsController.java
│   ├── dto/
│   │   ├── aladin/           # 알라딘 API DTO
│   │   ├── request/
│   │   └── response/
│   ├── repository/
│   └── service/
│       ├── external/         # 외부 API 서비스
│       └── ...
├── user/                      # 사용자 도메인
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   └── service/
├── category/                  # 카테고리 및 공통 코드
├── config/                    # 설정 클래스
└── exception/                 # 전역 예외 처리
```

## 실행 방법

### 1. 프로젝트 클론
```bash
git clone <repository-url>
cd BookStore-Assignment
```

### 2. 빌드 및 실행
```bash
# Gradle을 사용한 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. 접속
브라우저에서 `http://localhost:8080` 접속

## 주요 특징

### 알라딘 API 통합
- **WebFlux 기반 비동기 API 호출**: Spring WebFlux를 활용한 효율적인 외부 API 통신
- **도서 정보 자동 매핑**: 알라딘 API 응답을 Product 엔티티로 자동 변환
- **ISBN 중복 체크**: 동일한 도서가 중복 등록되지 않도록 검증

### 장바구니 시스템
- **Facade 패턴**: CartFacadeService를 통한 복잡한 비즈니스 로직 캡슐화
- **DTO 기반 데이터 전송**: 엔티티 노출을 방지하고 필요한 정보만 전달
- **재고 검증**: 장바구니 추가 시 재고 수량 자동 확인

### 파일 업로드
- **이미지 파일 검증**: 확장자 기반 이미지 파일만 업로드 허용
- **로컬 파일 시스템 저장**: `uploads/` 디렉토리에 이미지 저장
- **알라딘 URL 우선**: 알라딘 도서는 외부 이미지 URL 활용

## 한계 및 개선 사항

### 현재 미구현 기능
- **인증/인가**: Spring Security 미적용, 세션 관리 없음
- **주문/결제**: 장바구니까지만 구현, 실제 주문 프로세스 미구현
- **상품 수정/삭제**: 등록 기능만 구현
- **검색 및 필터링**: 상품 목록의 고급 검색 기능 부재
- **리뷰 시스템**: ProductReviews 엔티티는 있으나 기능 미구현

### 알라딘 API 관련
- API 키 하드코딩 (환경변수 또는 설정 파일로 분리 필요)
- 에러 처리 개선 필요
- 캐싱 전략 미적용

### 기술적 개선 사항
- 테스트 코드 부족
- 로깅 전략 개선 필요
- API 응답 표준화 (현재 혼재된 형태)
- 예외 처리 고도화

## 프로젝트 회고

이 프로젝트는 **알라딘 API를 활용한 편리한 도서 등록 시스템**을 목표로 시작했습니다.
알라딘에서 제공하는 풍부한 도서 정보를 활용하면 관리자가 일일이 도서 정보를 입력할 필요 없이
검색만으로 상품을 등록할 수 있을 것이라 기대했습니다.

하지만 **토이 프로젝트와 병행하는 과정에서 시간 관리의 어려움**을 겪었고,
결과적으로 기본적인 CRUD와 장바구니 기능 중심으로 범위를 축소하게 되었습니다.

### 배운 점
- 외부 API 통합 경험 (WebFlux를 통한 비동기 처리)
- 도메인 중심 설계 및 Facade 패턴 활용
- 프로젝트 범위 관리의 중요성

### 아쉬운 점
- 인증/인가 시스템 미구현으로 인한 기능 제약
- 주문 프로세스까지 완성하지 못한 점
- 테스트 코드 작성 부족

향후 시간 여유가 생긴다면 Spring Security를 적용하고,
주문/결제 기능을 추가하여 완전한 서점 시스템으로 발전시키고 싶습니다.

## 라이센스

이 프로젝트는 학습 목적의 토이 프로젝트입니다.

## 개발자

- **Author**: chan
- **Version**: 0.0.1-SNAPSHOT