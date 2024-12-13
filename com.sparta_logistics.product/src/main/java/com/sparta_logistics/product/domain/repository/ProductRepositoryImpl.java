package com.sparta_logistics.product.domain.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta_logistics.product.domain.model.QProduct;
import com.sparta_logistics.product.presentation.dto.ProductReadResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<ProductReadResponse> findAll(List<UUID> ids, String name, Boolean outOfStock,
      Long minPrice, Long maxPrice, Pageable pageable) {
    QProduct product = QProduct.product;

    List<OrderSpecifier<?>> sortOrders = getSortOrders(pageable.getSort());

    // 데이터 페이징 조회
    List<ProductReadResponse> results = jpaQueryFactory
        .select(Projections.constructor(
            ProductReadResponse.class,
            product.id.as("productId"),
            product.companyId.as("companyId"),
            product.hubId.as("hubId"),
            product.name.as("productName"),
            product.stock.as("productStock"),
            product.imageUrl.as("productImageUrl"),
            product.price.as("productPrice")
        ))
        .from(product)
        .where(
            isNotDeleted(),
            nameEq(name),
            containsIds(ids),
            isOutOfStock(outOfStock),
            priceBetween(minPrice, maxPrice)
        )
        .orderBy(sortOrders.toArray(new OrderSpecifier<?>[0]))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    // 전체 개수 서브쿼리로 계산
    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(product.count())
        .from(product)
        .where(
            isNotDeleted(),
            nameEq(name),
            containsIds(ids)
        );

    return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
  }

  private BooleanExpression nameEq(String name) {
    QProduct product = QProduct.product;

    if (name != null && !name.trim().isEmpty()) {
      // 공백 제거 및 검색어 분리
      String[] keywords = name.trim().split("\\s+");

      // 각 키워드로 조건 생성
      BooleanExpression condition = null;
      for (String keyword : keywords) {
        BooleanExpression keywordCondition = product.name.containsIgnoreCase(keyword);
        condition = (condition == null) ? keywordCondition : condition.and(keywordCondition);
      }

      return condition;
    }
    return null;
  }

  private BooleanExpression isNotDeleted() {
    QProduct product = QProduct.product;
    return product.deletedAt.isNull();
  }

  private BooleanExpression containsIds(List<UUID> ids) {
    QProduct product = QProduct.product;

    if (ids != null && !ids.isEmpty()) {
      return product.id.in(ids);
    }

    return null;
  }

  private BooleanExpression isOutOfStock(Boolean outOfStock) {
    QProduct product = QProduct.product;

    if (outOfStock != null) {
      return outOfStock ? product.stock.eq(0) : product.stock.gt(0);
    }
    return null;
  }

  private BooleanExpression priceBetween(Long minPrice, Long maxPrice) {
    QProduct product = QProduct.product;

    BooleanExpression condition = null;

    if (minPrice != null) {
      condition = product.price.goe(minPrice); // 최소 가격 조건
    }
    if (maxPrice != null) {
      condition = (condition == null) ? product.price.loe(maxPrice)
          : condition.and(product.price.loe(maxPrice)); // 최대 가격 조건
    }

    return condition;
  }

  private List<OrderSpecifier<?>> getSortOrders(Sort sort) {
    QProduct product = QProduct.product;
    List<OrderSpecifier<?>> orders = new ArrayList<>();

    for (Sort.Order order : sort) {
      String property = order.getProperty(); // 정렬 필드
      Sort.Direction direction = order.getDirection(); // ASC 또는 DESC

      ComparableExpressionBase<?> sortField;

      // 정렬 필드 매핑
      if ("updatedAt".equalsIgnoreCase(property)) {
        sortField = product.updatedAt;
      } else {
        sortField = product.createdAt;
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
