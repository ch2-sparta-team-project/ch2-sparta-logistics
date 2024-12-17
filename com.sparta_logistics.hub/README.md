
# 🗃Hub

## Info

Hub 애플리케이션은 허브 도메인의 생성,조회,수정,삭제를 관리하는 기능을 제공합니다.

- Hub Application 기능
    - 허브 CRUD
    - 허브 검색 및 정렬
    - 중앙 허브 할당, 해제
    - 중앙 허브, 인접 허브 연결
    - 허브 루트 CRUD

## ERD

![ch2-sparta-logistics-hub](https://github.com/user-attachments/assets/96c5681b-b40f-4b50-83a6-7e2aea978e3d)

## End Point

| 기능                | Method | URI           | 참고 |
|-------------------|--------|---------------| --- |
| 허브 생성             | POST   | /api/v1/hubs  |  |
| 허브 조회             | GET    | /api/v1/hubs/{hubId} |  |
| 허브 목록 조회          | GET    | /api/v1/hubs  | reqeust param API 명세서 참고 |
| 허브 수정             | PUT    | /api/v1/hubs/{hubId} |  |
| 허브 삭제             | DELETE | /api/v1/hubs/{hubId} |  |
| 허브 복원             | PATCH  | /api/v1/hubs/{hubId} |  |
| 중심 허브에 인접 허브 추가   | POST   | /api/v1/hubs/center/{hubId} |  |
| 중심 허브의 인접 허브 제거   | DELETE | /api/v1/hubs/center/{hubId} |  |
| 중심 허브 설정 활성화/비활성화 | PATCH  | /api/v1/hubs/center/{hubId} |  |
| 중심 허브 변경          | PUT    | /api/v1/hubs/center/{hubId} |  |
| 허브 경로 생성          | POST   | /api/v1/hubs/hub_route |  |
| 허브 경로 조회          | GET    | /api/v1/hubs/hub_route |  |
| 허브 경로 수정          | PUT    | /api/v1/hubs/hub_route |  |
| 허브 경로 삭제          | DELETE | /api/v1/hubs/hub_route |  |

## Table 명세서

### Hub

| 키 | 논리       | 물리        | 타입 | Null 허용 | 코멘트 |
|---|----------|-----------| --- | --- | --- |
| PK | id       | hub_id    | UUID | N |  |
| FK (p_user) | 허브 관리자   | user_id   | UUID | N |  |
|   | 이름       | name      | VARCHAR | N |  |
|   | 주소       | address   | VARCHAR | N |  |
|   | 위도       | latitude  | DOUBLE | N |  |
|   | 경도       | longtitude | DOUBLE | N |  |
|   | 중앙 허브 여부 | is_center     | BOOLEAN | N |  |

### Hub Route

| 키 | 논리       | 물리           | 타입      | Null 허용 | 코멘트 |
|---|----------|--------------|---------| --- | --- |
| PK | id       | hub_route_id | UUID    | N |  |
|FK (p_hub) | 출발 허브 id   | source_hub_id    | UUID    | N |  |
|FK (p_hub)   | 도착 허브 id     | destination_hub_id       | UUID    | N |  |
|   | 소요 시간       | duration      | Time    | N |  |
|   | 이동 거리(km)      | distance    | DOUBLE  | N |  |

## Link

[API_명세서](https://www.notion.so/cfb5b37f6580488ebb1cac5903333a56?pvs=21)

[Hub_Swagger](http://localhost:19094/api/v1/hubs/swagger-ui/index.html)

## 실행 방법

### Config Server 적용 안 하는 경우

```yaml
spring:
  application:
    name: hub-service
  data:
    redis:
      host: localhost
      port: 6379
      password: systempass
  datasource:
    url: jdbc:postgresql://localhost:5432/sparta_logistics_product
    username: postgres
    password: password
    driver-class-name: org.postgresql.Driver
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 19094

eureka:
  client:
    service-url:
      defaultZone: http://localhost:19090/eureka/
service:
  header:
    id: userId #FeignClient 호출 시 추가할 헤더 값(어드민) 환경변수 처리
    username: username
    role : role

management:
  zipkin:
    tracing:
      endpoint: "http://localhost:9411/api/v2/spans"
  tracing:
    sampling:
      probability: 1.0

springdoc:
  api-docs:
    path: /api/v1/hubs/v3/api-docs
  swagger-ui:
    path: /api/v1/hubs/swagger-ui.html
```

위 내용을 hub Application application.yml에 작성 후 실행

### Config Server 적용

```yaml
spring:
  application:
    name: hub-service #이름 지정
  profiles:
    active: local #Config YML dev, local.... 선택
  config:
    import: "configserver:" #Config YML Import 지정
  cloud:
    config:
      discovery:
        enabled: true
        service-id: config-server #Config Server 이름

# http://localhost:19094/actuator/refresh 사용을 위한 설정
# 코드 내 @RefreshScope 적용 필요
management:
  endpoints:
    web:
      exposure:
        include: refresh

eureka:
  client:
    service-url:
      defaultZone: http://localhost:19090/eureka/
```

위 내용을 Product Application application.yml에 작성

Config Server config-repo 디렉토리 안 `hub-service-local.yml` YML 설정 코드 작성 후 Config Server와 같이 실행