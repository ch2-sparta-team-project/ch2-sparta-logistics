package com.sparta_logistics.hub.infrastructure.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta_logistics.hub.domain.model.Hub;
import com.sparta_logistics.hub.presentation.request.HubSearchRequest;
import com.sparta_logistics.hub.presentation.response.HubReadResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.sparta_logistics.hub.domain.model.QHub.hub;

@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  // 이름, 주소, 중심 허브 여부, 검색한 허브를 중심 허브로 갖는 허브
  @Override
  public Page<HubReadResponse> searchHubs(HubSearchRequest searchRequest, Pageable pageable){
    List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

    QueryResults<Hub> results = queryFactory
        .selectFrom(hub)
        .where(
            nameContains(searchRequest.name()),
            addressContains(searchRequest.address()),
            isCenterTrue(searchRequest.isCenter()),
            centerHubContains(searchRequest.centerHubName()),
            hub.deletedAt.isNull()
        )
        .orderBy(orders.toArray(new OrderSpecifier[0]))
        .offset(pageable.getOffset()) // 몇 페이지인지
        .limit(pageable.getPageSize()) // 한 페이지에 들어갈 Hub 개수
        .fetchResults();
    List<HubReadResponse> content = results.getResults()
        .stream() // QueryResult<Product> -> getResults() -> List<Product> -> stream
        .map(HubReadResponse::buildResponseByEntity)
        .toList();
    long total = results.getTotal(); // result의 총합

    return new PageImpl<>(content, pageable, total);
  }
  private BooleanExpression nameContains(String name){
    return name != null ? hub.name.contains(name) : null;
  }
  private BooleanExpression addressContains(String address){
    return address != null ? hub.address.contains(address) : null;
  }
  private BooleanExpression isCenterTrue(Boolean isCenter){
    return isCenter != null ? hub.isCenter.isTrue() : null;
  }
  private BooleanExpression centerHubContains(String centerHubName){
    return (hub.centerHub != null && centerHubName != null) ? hub.centerHub.name.contains(centerHubName) : null;
  }
  private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
    List<OrderSpecifier<?>> orders = new ArrayList<>();
    if (pageable.getSort() != null) {
      for (Sort.Order sortOrder : pageable.getSort()) {
        com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
        switch (sortOrder.getProperty()) {
          case "name" -> orders.add(new OrderSpecifier<>(direction, hub.name));
          case "address" -> orders.add(new OrderSpecifier<>(direction, hub.address));
          case "isCenter" -> orders.add(new OrderSpecifier<>(direction, hub.isCenter));
          case "centerHubName" -> orders.add(new OrderSpecifier<>(direction, hub.centerHub.name));
          default -> {
          }
        }
      }
    }

    return orders;
  }
}
