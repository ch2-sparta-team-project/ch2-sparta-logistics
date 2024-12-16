package com.sparta_logistics.company.infrastructure.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta_logistics.company.application.dto.CompanyReadResponse;
import com.sparta_logistics.company.domain.model.CompanyType;
import com.sparta_logistics.company.presentation.request.CompanySearchRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import static com.sparta_logistics.company.domain.model.QCompany.company;

@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepositoryCustom{


  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<CompanyReadResponse> searchCompanies(CompanySearchRequest companySearchRequest,
      Pageable pageable) {

    List<OrderSpecifier<?>> sortOrders = getSortOrders(pageable.getSort());
    List<CompanyReadResponse> results = jpaQueryFactory
        .select(Projections.constructor(
            CompanyReadResponse.class,
            company.id.as("companyId"),
            company.userId.as("userId"),
            company.hubId.as("hubId"),
            company.name.as("companyName"),
            company.address.as("companyAddress"),
            company.companyType.as("companyType"),
            company.latitude.as("latitude"),
            company.longitude.as("longitude"),
            company.phone.as("phone"),
            company.username.as("username")
        ))
        .from(company)
        .where(
            // 위 필드 중 위도, 경도, 핸드폰 제외 모든 조건.
            companyIdEq(companySearchRequest.id()),
            companyUserIdEq(companySearchRequest.userId()),
            companyHubIdEq(companySearchRequest.hubId()),
            companyNameContains(companySearchRequest.name()),
            companyAddressContains(companySearchRequest.address()),
            companyTypeEq(companySearchRequest.companyType()),
            companyUsernameContains(companySearchRequest.username()),
            company.deletedAt.isNull()
        )
        .orderBy(sortOrders.toArray(new OrderSpecifier<?>[0]))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(company.count())
        .from(company)
        .where(
            company.deletedAt.isNull()
        );
    return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
  }

  private BooleanExpression companyUsernameContains(String username) {
    if (username != null && !username.trim().isEmpty()) {
      // 공백 제거 및 검색어 분리
      String[] keywords = username.trim().split("\\s+");

      // 각 키워드로 조건 생성
      BooleanExpression condition = null;
      for (String keyword : keywords) {
        BooleanExpression keywordCondition = company.username.containsIgnoreCase(keyword);
        condition = (condition == null) ? keywordCondition : condition.and(keywordCondition);
      }

      return condition;
    }
    return null;
  }

  private BooleanExpression companyIdEq(UUID id) {
    return id != null ? company.id.eq(id) : null;
  }

  private BooleanExpression companyUserIdEq(UUID userId) {
    return userId != null ? company.userId.eq(userId) : null;
  }

  private BooleanExpression companyHubIdEq(UUID hubId) {
    return hubId != null ? company.hubId.eq(hubId) : null;
  }

  private BooleanExpression companyNameContains(String name) {
    if (name != null && !name.trim().isEmpty()) {
      // 공백 제거 및 검색어 분리
      String[] keywords = name.trim().split("\\s+");

      // 각 키워드로 조건 생성
      BooleanExpression condition = null;
      for (String keyword : keywords) {
        BooleanExpression keywordCondition = company.name.containsIgnoreCase(keyword);
        condition = (condition == null) ? keywordCondition : condition.and(keywordCondition);
      }

      return condition;
    }
    return null;
  }

  private BooleanExpression companyAddressContains(String address) {
    return address != null ? company.address.in(address) : null;
  }

  private BooleanExpression companyTypeEq(CompanyType companyType) {
    return companyType != null ? company.companyType.eq(companyType) : null;
  }

  private List<OrderSpecifier<?>> getSortOrders(Sort sort) {
    List<OrderSpecifier<?>> orders = new ArrayList<>();

    for (Sort.Order order : sort) {
      String property = order.getProperty(); // 정렬 필드
      Sort.Direction direction = order.getDirection(); // ASC 또는 DESC

      ComparableExpressionBase<?> sortField;

      // 정렬 필드 매핑
      if ("updatedAt".equalsIgnoreCase(property)) {
        sortField = company.updatedAt;
      } else {
        sortField = company.createdAt;
      }

      // 정렬 방향 적용
      if (direction == Sort.Direction.ASC) {
        orders.add(sortField.asc());
      } else {
        orders.add(sortField.desc());
      }
    }
    return orders;
  }
}
