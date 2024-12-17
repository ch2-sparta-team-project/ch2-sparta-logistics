# 🗃Company

## Info

Company 애플리케이션은 업체 도메인의 생성,조회,수정,삭제를 관리하는 기능을 제공합니다.

- Hub Application 기능
    - 업체 CRUD
    - 업체 검색 및 정렬

## ERD

![ch2-sparta-logistics-company](https://github.com/user-attachments/assets/3605398b-f48a-4e61-94e5-ace4c7826512)

## End Point

| 기능       | Method | URI                           | 참고                       |
|----------|--------|-------------------------------|--------------------------|
| 업체 생성    | POST   | /api/v1/companies             |                          |
| 업체 조회    | GET    | /api/v1/companies/{companyId} |                          |
| 업체 목록 조회 | GET    | /api/v1/companies             | reqeust param API 명세서 참고 |
| 업체 수정    | PUT    | /api/v1/companies/{companyId} |                          |
| 업체 삭제    | DELETE | /api/v1/companies/{companyId} |                          |
| 업체 복원    | PATCH  | /api/v1/companies/{companyId} |                          |

## Table 명세서

### Company

| 키           | 논리         | 물리            | 타입       | Null 허용 | 코멘트                |
|-------------|------------|---------------|----------|---------|--------------------|
| PK	         | id	        | id            | 	UUID    | 	N      |
| FK (p_user) | 	업체 담당자    | id            | 	user_id | 	UUID   | 	N                 |
| FK (p_hub)  | 	등록된 허브 id | 	hub_id       | 	UUID    | 	N      |
|             | 	업체 이름     | 	company_name | VARCHAR  | 	N      |
|             | address    | 	address	     | VARCHAR  | 	N      |
|             | 업체 타입      | 	role	        | VARCHAR  | 	N	     | PRODUCER, CONSUMER |
|             | 위도         | latitude      | DOUBLE   | N       |                    |
|             | 경도         | longtitude    | DOUBLE   | N       |                    |
|             | 전화번호	      | phone	        | VARCHAR  | 	N	     |

## Link

[API_명세서](https://www.notion.so/cfb5b37f6580488ebb1cac5903333a56?pvs=21)

[Company_Swagger](http://localhost:19095/api/v1/companies/swagger-ui/index.html)

## 실행 방법

### Config Server 적용 안 하는 경우

```yaml
spring:
  application:
    name: company-service
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
  port: 19095

eureka:
  client:
    service-url:
      defaultZone: http://localhost:19090/eureka/
service:
  header:
    id: userId #FeignClient 호출 시 추가할 헤더 값(어드민) 환경변수 처리
    username: username
    role: role

management:
  zipkin:
    tracing:
      endpoint: "http://localhost:9411/api/v2/spans"
  tracing:
    sampling:
      probability: 1.0

springdoc:
  api-docs:
    path: /api/v1/companies/v3/api-docs
  swagger-ui:
    path: /api/v1/companies/swagger-ui.html
```

위 내용을 Company Application application.yml에 작성 후 실행

### Config Server 적용

```yaml
spring:
  application:
    name: company-service #이름 지정
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

위 내용을 Company Application application.yml에 작성

Config Server config-repo 디렉토리 안 `company-service-local.yml` YML 설정 코드 작성 후 Config Server와 같이 실행