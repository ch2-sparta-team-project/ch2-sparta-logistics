
# 🗃️Product

## Info

Product 애플리케이션은 상품을 등록 및 정보를 관리하는 기능을 제공합니다.

- Product Application 기능
    - 상품 CRUD
    - 상품 검색 및 정렬

## ERD
![ch2-sparta-logistics-product](https://github.com/user-attachments/assets/6ba64be2-9cf8-4fbe-af04-a12ca333c301)

## End Point

| 기능 | Method | URI | 참고 |
| --- | --- | --- | --- |
| 상품 생성 | POST | /api/v1/proudcts |  |
| 상품 조회 | GET | /api/v1/products/{productId} |  |
| 상품 목록 조회 | GET | /api/v1/products | reqeust param API 명세서 참고 |
| 상품 수정 | PUT | /api/v1/products/{productId} |  |
| 상품 삭제 | DELETE | /api/v1/products/{productId} |  |

## Table 명세서

| 키 | 논리 | 물리 | 타입 | Null 허용 | 코멘트 |
| --- | --- | --- | --- | --- | --- |
| PK | id | id | UUID | N |  |
| FK (p_company) | 업체 id | company_id | UUID | N |  |
| FK (p_hub) | 허브 id | hub_id | UUID | N |  |
|  | 이름 | name | VARCHAR | N |  |
|  | 재고 | stock | INTEGER | N |  |
|  | 상품 이미지 | image_url | VARCHAR | N |  |
|  | 가격 | price | BIGINT | N |  |

## Link

[API_명세서](https://www.notion.so/cfb5b37f6580488ebb1cac5903333a56?pvs=21)

[Product_Swagger](http://localhost:19097/api/v1/products/swagger-ui/index.html)

## 실행 방법

### Config Server 적용 안 하는 경우

```yaml
spring:
  application:
    name: product-service
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
  port: 19097

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
    path: /api/v1/products/v3/api-docs
  swagger-ui:
    path: /api/v1/products/swagger-ui.html
```

위 내용을 Product Application application.yml에 작성 후 실행

### Config Server 적용

```yaml
spring:
  application:
    name: product-service #이름 지정
  profiles:
    active: local #Config YML dev, local.... 선택
  config:
    import: "configserver:" #Config YML Import 지정
  cloud:
    config:
      discovery:
        enabled: true
        service-id: config-server #Config Server 이름

# http://localhost:19097/actuator/refresh 사용을 위한 설정
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

Config Server config-repo 디렉토리 안 `product-service-local.yml` YML 설정 코드 작성 후 Config Server와 같이 실행