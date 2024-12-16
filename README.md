# Sparta Logistics
### 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼 개발
## 👨‍👨‍👧‍👦 Our Team
| 정광호 | 홍예석 | 김지수 | 강찬욱 |
| --- | --- | --- | --- |
| 😀 Member | 😀 Member | 👾 Leader | 😀 Sub-Leader |
| [@jabberwocker04](https://github.com/chanwookK) | [@yshong1998](https://github.com/yshong1998) | [@strongcookdas](https://github.com/strongcookdas) | [@chanwookK](https://github.com/chanwookK) |
| `Gateway` </br> `User` </br> `Slack`| `Hub` </br> `HubRoute` </br> `Company` | `Product` </br> `AI` </br> `Config`|`Order`</br>`Delivery`</br>`DeliveryRoute`|
<br>

## 📎Link

### [프로젝트 노션](https://www.notion.so/7-88272b37206441d0a3b479469f2d4341?pvs=21)

### [API 명세서](https://www.notion.so/API-cfb5b37f6580488ebb1cac5903333a56?pvs=21)

### [테이블 명세서](https://www.notion.so/1488dd0b2d654f74a3527705f6f93c0f?pvs=21)

### [Git Hub](https://github.com/ch2-sparta-team-project)

<br>

## 프로젝트 개요

> MSA 기반 국내 물류 관리 배송 시스템 개발
B2B 물류 관리 및 배송 시스템으로 각 지역에 허브 센터를 가지고 있으며 각 허브 센터는 여러 업체의 물건을 보관합니다.
상품의 배송 요청이 들어오면 목적지 허브로 물품을 이동 시켜 목적지에 배송합니다.
>

<br>

## ❓프로젝트 개요

> MSA 기반 국내 물류 관리 배송 시스템 개발
B2B 물류 관리 및 배송 시스템으로 각 지역에 허브 센터를 가지고 있으며 각 허브 센터는 여러 업체의 물건을 보관합니다.
상품의 배송 요청이 들어오면 목적지 허브로 물품을 이동 시켜 목적지에 배송합니다.
>

## ✅ 프로젝트 기능

### Hub

- 17개의 허브 정보를 관리합니다.
- 허브 간 이동 정보는 모델링을 통해 허브 간 경로가 매핑 되어 있습니다.

### Delivery

- 업체 배송, 허브 배송 담당자로 구분되어 있으며 각 허브 별로 10명의 업체 배송 담당자와 물류 시스템 전체에서 10명의 허브 배송 담당자가 관리됩니다.
- 배송 및 배송 경로 기록이 주문 생성 시 함께 관리됩니다.

### Order

- 주문이 생성되면 관련된 상품의 재고가 감소하며, 주문이 취소되면 해당 수량이 복원됩니다.

### Company

- 모든 업체는 특정 허브에 소속되며 생산 업체와 수령 업체로 구분됩니다.

### Product

- 모든 상품은 특정 업체와 허브에서 관리됩니다.

### Slack

- 주문과 배송에 관한 정보를 슬랙을 통해 사용자에게 전달됩니다.

### User

- 사용자 정보 관리 기능을 제공합니다.
<br>

## 📁ERD
[ERD_dbdiagram_link](https://dbdiagram.io/d/ch2-sparta-logistics-v2-6760a660e763df1f002158ed)
- 최종 수정 후 이미지 추가

## 🌐Infra Architecture

- 최종 수정 후 이미지 추가

<br>

## 🛠️적용 라이브러리

### Eureka

> 마이크로서비스 아키텍처에서 각 서비스의 위치를 동적으로 관리하기 위해 도입했습니다.
>

### FeignClient

> Spring Cloud에서 제공하는 HTTP 클라이언트로, 선언적 RESTFul 웹 서비스를 호출하여 다른 서비스와 통신하기 위해 도입했습니다.
>

### Resilience4j

> Fallback으로 호출 실패 시 대체 로직을 제공하여 시스템 안정성을 제공할 수 있었습니다.
>

### Spring Cloud Gateway

> 요청을 특정 서비스로 라우팅하고 인증을 통해 외부 요청으로부터 애플리케이션을 보호하기 위해 도입했습니다.
>

### Spring Cloud Config

> 서비스 애플리케이션의 YAML 파일들을 중앙 집중식으로 관리하기 위해 도입했습니다.
>

### Zipkin

> 분산 추적 시스템으로, 마이크로서비스 아키텍처에서 요청 흐름을 추적하고 성능 문제를 분석하는 데 사용하기 위해 적용했습니다.
>

### Redis

> 고성능 인메모리 데이터 저장소로, 빠른 읽기 쓰기 성능을 제공하기에 캐싱 기능의 저장소로 적합하여 도입했습니다.
>

### QueryDSL

> 정렬, 필터, 검색 등에 따른 동작 쿼리 작성을 위해 QueryDSL을 도입했습니다.
>
<br>

## 🧰기술 스택

<div align=center>

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"/> <img src="https://img.shields.io/badge/JSONWebToken-000000?style=for-the-badge&logo=JSONWebTokens&logoColor=white"/>

<img src="https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white"> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"/> <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black"/> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/>

<img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=for-the-badge&logo=IntelliJIDEA&logoColor=white"/>  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/>  <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/>  <img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"/>

</div>
<br>

## 🌲프로젝트 구조

```
project/
│
├── .github/                               # GitHub 관련 설정 및 PR 템플릿
├── com.sparta_logistics.ai/               # AI Service
├── com.sparta_logistics.auth/             # Auth Service
├── com.sparta_logistics.company/          # Company Service
├── com.sparta_logistics.config/           # Config Server
├── com.sparta_logistics.delivery/         # Delivery Service
├── com.sparta_logistics.gateway/          # Gateway
├── com.sparta_logistics.hub/              # Hub Service
├── com.sparta_logistics.order/            # Order Serivce
├── com.sparta_logistics.product/          # Product Service
├── com.sparta_logistics.slack/            # Slack Service
├── com.sparta_logistics/                  # Eureka Servcer
└── README.md                              

```

## API Docs
- swagger 연동 후 작성
