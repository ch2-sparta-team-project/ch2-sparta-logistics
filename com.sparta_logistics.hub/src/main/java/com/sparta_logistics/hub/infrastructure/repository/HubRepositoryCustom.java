package com.sparta_logistics.hub.infrastructure.repository;

import com.sparta_logistics.hub.presentation.request.HubSearchRequest;
import com.sparta_logistics.hub.presentation.response.HubReadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRepositoryCustom {
  Page<HubReadResponse> searchHubs(HubSearchRequest searchRequest, Pageable pageable);

}
